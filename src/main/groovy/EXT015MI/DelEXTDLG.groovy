/**
 * @Name: DelEXTDLG.EXTDLG
 * @Description: Delete record on table EXTDL1 and EXTDL2
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class DelEXTDLG extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  private int inCONO, inDSEQ;
  private String inDIVI;
  private String inWHLO;
  private String inLDID;
  
  public DelEXTDLG(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
    this.mi = mi;
    this.utility = utility;
    this.logger = logger;
    this.program = program;
    this.miCaller = miCaller;
    this.database = database;
  }
  
  public void main() {
    inCONO = mi.in.get("CONO") == null ? program.LDAZD.CONO as int : mi.in.get("CONO") as Integer;
    inDIVI = mi.inData.get("DIVI") == null ? "" : mi.inData.get("DIVI").trim();
    inWHLO = mi.inData.get("WHLO") == null ? "" : mi.inData.get("WHLO").trim();
    inLDID = mi.inData.get("LDID") == null ? "" : mi.inData.get("LDID").trim();
    inDSEQ = mi.in.get("DSEQ") == null ? 0 : mi.in.get("DSEQ") as Integer;
    
    DBAction query = database.table("EXTDL1").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    container.set("EXDSEQ", inDSEQ);
    
    if (!query.read(container)) {
      mi.error("Record does not exists  in EXTDL1");
      return;
    }
    
    query.readLock(container, { LockedResult lockedResult ->
      lockedResult.delete();
    });
    
    DBAction query1 = database.table("EXTDL2").index("00").selectAllFields().build();
    DBContainer container1 = query1.getContainer();
    container1.set("EXCONO", inCONO);
    container1.set("EXDIVI", inDIVI);
    container1.set("EXWHLO", inWHLO);
    container1.set("EXLDID", inLDID);
    container1.set("EXDSEQ", inDSEQ);
    
    if (!query1.read(container1)) {
      mi.error("Record does not exists in EXTDL2");
      return;
    }
    
    query1.readLock(container1, { LockedResult lockedResult ->
      lockedResult.delete();
    });
    
  }
}