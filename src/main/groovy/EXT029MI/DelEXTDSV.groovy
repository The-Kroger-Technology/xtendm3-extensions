/**
 * @Name: DelEXTDSV.EXTDSV
 * @Description: Delete record on table EXTDSV
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date      User    Description
 *  1.0.0     20250604  ADY     Initial Release
 *  1.0.1     20250826  ADY     Fixed variable names
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
    
    DBAction queryEXTDSV = database.table("EXTDSV").index("00").build();
    DBContainer containerEXTDSV = queryEXTDSV.getContainer();
    containerEXTDSV.set("EXCONO", inCONO);
    containerEXTDSV.set("EXCUDT", inCUDT as int);
    containerEXTDSV.set("EXFACI", inFACI);
    containerEXTDSV.set("EXSDST", inSDST);
    containerEXTDSV.set("EXCUNO", inCUNO);
    containerEXTDSV.set("EXITNO", inITNO);
    
    if (!queryEXTDSV.read(containerEXTDSV)) {
      mi.error("Record does not exist");
      return;
    }
    
    queryEXTDSV.readLock(containerEXTDSV, { LockedResult lockedResult ->
      lockedResult.delete();
    });
  }
}