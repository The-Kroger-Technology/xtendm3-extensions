/**
 * @Name: GetEXTDLS.EXTDLS
 * @Description: Get record on table EXTDLS
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class GetEXTDLS extends ExtendM3Transaction {
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
  private String inSPNO;
  
  public GetEXTDLS(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inSPNO = mi.inData.get("SPNO") == null ? "" : mi.inData.get("SPNO").trim();
    
    DBAction query = database.table("EXTDLS").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    container.set("EXDSEQ", inDSEQ);
    container.set("EXSPNO", inSPNO);
    
    if (!query.read(container)) {
      mi.error("Record does not exists");
      return;
    }
    
    mi.outData.put("CONO", container.get("EXCONO").toString());
    mi.outData.put("DIVI", container.get("EXDIVI").toString());
    mi.outData.put("WHLO", container.get("EXWHLO").toString());
    mi.outData.put("LDID", container.get("EXLDID").toString());
    mi.outData.put("DSEQ", container.get("EXDSEQ").toString());
    mi.outData.put("SPNO", container.get("EXSPNO").toString());
    mi.outData.put("DESC", container.get("EXDESC").toString());
    mi.outData.put("POST", container.get("EXPOST").toString());
    mi.outData.put("CMPT", container.get("EXCMPT").toString());
    mi.outData.put("EVDT", container.get("EXEVDT").toString());
    mi.outData.put("EVTM", container.get("EXEVTM").toString());
    mi.write();
  }
}