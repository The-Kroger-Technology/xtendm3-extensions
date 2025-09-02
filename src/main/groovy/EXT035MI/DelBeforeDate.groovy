/**
 * @Name: EXT035MI.DelBeforeDate
 * @Description: Delete record on table EXTCCL by Date
 * @Authors: Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date      User    	Description
 *  1.0.0     20250902  JTAPANG     Initial Release
 *
 */

public class DelBeforeDate extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  
  private int inCONO, maxRecords;
  private String inRGDT;
  
  public DelBeforeDate(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
    this.mi = mi;
    this.utility = utility;
    this.logger = logger;
    this.program = program;
    this.miCaller = miCaller;
    this.database = database;
  }
  
  public void main() {
    inCONO = mi.in.get("CONO") == null ? program.LDAZD.CONO as int : mi.in.get("CONO") as int;
    inRGDT = mi.inData.get("RGDT") == null ? "" : mi.inData.get("RGDT").trim() as String;
    maxRecords = mi.getMaxRecords() <= 0 || mi.getMaxRecords() >= 10000 ? 10000: mi.getMaxRecords();
    
    if (!isValidInput()) {
      return;
    }
    
    ExpressionFactory exp = database.getExpressionFactory("EXTCCL");
    exp = exp.eq("EXCONO", inCONO.toString());
    
    if (!inRGDT.isBlank()) {
      exp = exp.and(exp.le("EXRGDT", inRGDT));
    }
    
    DBAction queryEXTCCL = database.table("EXTCCL").index("00").matching(exp).selection().build();
        
    DBContainer containerEXTCCL = queryEXTCCL.getContainer();
    containerEXTCCL.set("EXCONO", inCONO);
    
    Closure<?> resultHandlerEXTCCL = { DBContainer data ->
      DBAction action = database.table("EXTCCL").index("00").build();
      action.readLock(data, { LockedResult lockedResult ->
        lockedResult.delete();
      });
    }
    
    if (queryEXTCCL.readAll(containerEXTCCL, 1, maxRecords, resultHandlerEXTCCL) <= 0) {
      mi.error("No valid record found - EXTCCL");
      return;
    }
  }
  
  /**
   * Validate input fields
   */
  boolean isValidInput() {
    if (inRGDT.isBlank()) {
      mi.error("Delete before Date is required.");
      return false;
    }
    
    return true;
  }
}