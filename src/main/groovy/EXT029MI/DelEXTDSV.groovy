/**
 * @Name: DelEXTDSV.EXTDSV
 * @Description: Delete record on table EXTDSV
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     20250604 ADY      Initial Release
 *
 */

public class DelEXTDSV extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  
  private int inCONO;
  private String inCUDT, inFACI, inSDST, inCUNO, inITNO;

  public DelEXTDSV(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
    this.mi = mi;
    this.utility = utility;
    this.logger = logger;
    this.program = program;
    this.miCaller = miCaller;
    this.database = database;
  }
  public void main() {
    inCONO = mi.in.get("CONO") == null ? program.LDAZD.CONO as int : mi.in.get("CONO") as int;
    inCUDT = mi.inData.get("CUDT") == null ? "" : mi.inData.get("CUDT").trim() as String;
    inFACI = mi.inData.get("FACI") == null ? "" : mi.inData.get("FACI").trim() as String;
    inSDST = mi.inData.get("SDST") == null ? "" : mi.inData.get("SDST").trim() as String;
    inCUNO = mi.inData.get("CUNO") == null ? "" : mi.inData.get("CUNO").trim() as String;
    inITNO = mi.inData.get("ITNO") == null ? "" : mi.inData.get("ITNO").trim() as String;
    
    DBAction EXTDSV_query = database.table("EXTDSV").index("00").build();
    DBContainer EXTDSV = EXTDSV_query.getContainer();
    EXTDSV.set("EXCONO", inCONO);
    EXTDSV.set("EXCUDT", inCUDT as int);
    EXTDSV.set("EXFACI", inFACI);
    EXTDSV.set("EXSDST", inSDST);
    EXTDSV.set("EXCUNO", inCUNO);
    EXTDSV.set("EXITNO", inITNO);
    
    if (!EXTDSV_query.read(EXTDSV)) {
      mi.error("Record does not exist");
      return;
    }
    
    EXTDSV_query.readLock(EXTDSV, { LockedResult lockedResult ->
      lockedResult.delete();
    });
  }
}