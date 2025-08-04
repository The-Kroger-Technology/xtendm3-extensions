/**
 * @Name: DelEXTROT.EXTROT
 * @Description: Delete record on table EXTROT
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class DelEXTROT extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  private int inCONO;
  private String inDIVI;
  private String inWHLO;
  private String inRNBR;
  private String inRHID;
  
  public DelEXTROT(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
    this.mi = mi;
    this.utility = utility;
    this.logger = logger;
    this.program = program;
    this.miCaller = miCaller;
    this.database = database;
  }
  public void main() {
    inCONO = mi.in.get("CONO") == null ? program.LDAZD.CONO as int : mi.in.get("CONO") as int;
    inDIVI = mi.inData.get("DIVI") == null ? "" : mi.inData.get("DIVI").trim();
    inWHLO = mi.inData.get("WHLO") == null ? "" : mi.inData.get("WHLO").trim();
    inRNBR = mi.inData.get("RNBR") == null ? "" : mi.inData.get("RNBR").trim();
    inRHID = mi.inData.get("RHID") == null ? "" : mi.inData.get("RHID").trim();
    DBAction query = database.table("EXTROT").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXRNBR", inRNBR);
    container.set("EXRHID", inRHID);
    if (!query.read(container)) {
      mi.error("Record does not exists");
      return;
    }
    query.readLock(container, {
      LockedResult lockedResult ->
      lockedResult.delete();
    });
  }
}