/**
 * @Name: DelEXTMUR.EXTMUR
 * @Description: Delete record on table EXTMUR
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date      User    Description
 *  1.0.0     20250723  ADY     Initial Release
 *  1.0.1     20250826  ADY     Fixed variable names
 *
 */

public class DelEXTMUR extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  
  private int inCONO;
  private String inDIVI, inWHLO, inDATE, inMFNO, inPRNO;

  public DelEXTMUR(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
    this.mi = mi;
    this.utility = utility;
    this.logger = logger;
    this.program = program;
    this.miCaller = miCaller;
    this.database = database;
  }
  
  public void main() {
    inCONO = mi.in.get("CONO") == null ? program.LDAZD.CONO as int : mi.in.get("CONO") as int;
    inDIVI = mi.inData.get("DIVI") == null ? "" : mi.inData.get("DIVI").trim() as String;
    inWHLO = mi.inData.get("WHLO") == null ? "" : mi.inData.get("WHLO").trim() as String;
    inDATE = mi.inData.get("DATE") == null ? "" : mi.inData.get("DATE").trim() as String;
    inMFNO = mi.inData.get("MFNO") == null ? "" : mi.inData.get("MFNO").trim() as String;
    inPRNO = mi.inData.get("PRNO") == null ? "" : mi.inData.get("PRNO").trim() as String;
    
    DBAction queryEXTMUR = database.table("EXTMUR").index("00").build();
    DBContainer containerEXTMUR = queryEXTMUR.getContainer();
    containerEXTMUR.set("EXCONO", inCONO);
    containerEXTMUR.set("EXDIVI", inDIVI);
    containerEXTMUR.set("EXWHLO", inWHLO);
    containerEXTMUR.set("EXDATE", inDATE as int);
    containerEXTMUR.set("EXMFNO", inMFNO);
    containerEXTMUR.set("EXPRNO", inPRNO);
    
    if (!queryEXTMUR.read(containerEXTMUR)) {
      mi.error("Record does not exist");
      return;
    }
    
    queryEXTMUR.readLock(containerEXTMUR, { LockedResult lockedResult ->
      lockedResult.delete();
    });
  }
}