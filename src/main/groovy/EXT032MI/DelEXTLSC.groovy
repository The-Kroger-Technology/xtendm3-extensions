/**
 * @Name: DelEXTLSC.EXTLSC
 * @Description: Delete record on table EXTLSC
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date      User                Description
 *  1.0.0     20250701  Job Hanrhee Cuta    Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class DelEXTLSC extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  private int inCONO;
  private String inDIVI;
  private String inWHLO;
  private String inWHSL;
  private String inITNO;
  private String inBANO;
  private String inCNDT;
  public DelEXTLSC(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inCNDT = mi.inData.get("CNDT") == null ? "" : mi.inData.get("CNDT").trim();
    inWHLO = mi.inData.get("WHLO") == null ? "" : mi.inData.get("WHLO").trim();
    inWHSL = mi.inData.get("WHSL") == null ? "" : mi.inData.get("WHSL").trim();
    inBANO = mi.inData.get("BANO") == null ? "" : mi.inData.get("BANO").trim();
    
    DBAction query = database.table("EXTLSC").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXCNDT", inCNDT);
    container.set("EXWHLO", inWHLO);
    container.set("EXWHSL", inWHSL);
    container.set("EXITNO", inITNO);
    container.set("EXBANO", inBANO);
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