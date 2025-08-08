/**
 * @Name: EXT005MI.DelEXTRAW
 * @Description: Delete record on table EXTRAW
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date      User    Description
 *  1.0.0     20250325  ADY     Initial Release
 *
 */

public class DelEXTRAW extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  
  private int inCONO;
  private String inFACI, inMTNO, inITNO, inITCL, inFDAT, inTDAT;
  
  public DelEXTRAW(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
    this.mi = mi;
    this.utility = utility;
    this.logger = logger;
    this.program = program;
    this.miCaller = miCaller;
    this.database = database;
  }
  
  public void main() {
    inCONO = mi.in.get("CONO") == null ? program.LDAZD.CONO as int : mi.in.get("CONO") as int;
    inFACI = mi.inData.get("FACI") == null ? "" : mi.inData.get("FACI").trim() as String;
    inMTNO = mi.inData.get("MTNO") == null ? "" : mi.inData.get("MTNO").trim() as String;
    inITNO = mi.inData.get("ITNO") == null ? "" : mi.inData.get("ITNO").trim() as String;
    inITCL = mi.inData.get("ITCL") == null ? "" : mi.inData.get("ITCL").trim() as String;
    inFDAT = mi.inData.get("FDAT") == null ? "" : mi.inData.get("FDAT").trim() as String;
    inTDAT = mi.inData.get("TDAT") == null ? "" : mi.inData.get("TDAT").trim() as String;
    
    DBAction EXTRAW_query = database.table("EXTRAW").index("00").build();
    DBContainer EXTRAW = EXTRAW_query.getContainer();
    EXTRAW.set("EXCONO", inCONO);
    EXTRAW.set("EXFACI", inFACI);
    EXTRAW.set("EXMTNO", inMTNO);
    EXTRAW.set("EXITNO", inITNO);
    EXTRAW.set("EXITCL", inITCL);
    EXTRAW.set("EXFDAT", inFDAT as int);
    EXTRAW.set("EXTDAT", inTDAT as int);
    
    if (!EXTRAW_query.read(EXTRAW)) {
      mi.error("Record does not exist");
      return;
    }
    
    EXTRAW_query.readLock(EXTRAW, { LockedResult lockedResult ->
      lockedResult.delete();
    });
  }
}