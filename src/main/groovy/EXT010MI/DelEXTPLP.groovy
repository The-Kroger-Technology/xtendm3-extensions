/**
 * @Name: DelEXTPLP.EXTPLP
 * @Description: Delete record on table EXTPLP
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class DelEXTPLP extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  private int inCONO;
  private String inDIVI;
  private String inWHLO;
  private String inITNO;
  private String inBDRN;
  private String inYEAR;
  private String inWENU;
  private String inRETY;
  public DelEXTPLP(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inITNO = mi.inData.get("ITNO") == null ? "" : mi.inData.get("ITNO").trim();
    inBDRN = mi.inData.get("BDRN") == null ? "" : mi.inData.get("BDRN").trim();
    inYEAR = mi.inData.get("YEAR") == null ? "" : mi.inData.get("YEAR").trim();
    inWENU = mi.inData.get("WENU") == null ? "" : mi.inData.get("WENU").trim();
    inRETY = mi.inData.get("RETY") == null ? "" : mi.inData.get("RETY").trim();
    DBAction query = database.table("EXTPLP").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXWHLO", inWHLO);
    container.set("EXITNO", inITNO);
    container.set("EXBDRN", inBDRN);
    container.set("EXYEAR", inYEAR as int);
    container.set("EXWENU", inWENU as int);
    container.set("EXRETY", inRETY);
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