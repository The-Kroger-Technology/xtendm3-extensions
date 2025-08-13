/**
 * @Name: EXT005MI.DelByDate
 * @Description: Delete previous records on table EXTDSV by Date
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date      User    Description
 *  1.0.0     20250604  ADY     Initial Release
 *
 */

public class DelByDate extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  
  private int inCONO, pageSize;
  private String inCUDT;
  
  public DelByDate(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    pageSize = mi.getMaxRecords() <= 0 || mi.getMaxRecords() >= 10000? 10000: mi.getMaxRecords();
    
    if (!isValidInput()) {
      return;
    }
    
    ExpressionFactory exp = database.getExpressionFactory("EXTDSV");
    exp = exp.eq("EXCONO", inCONO.toString());
    
    if (!inCUDT.isBlank()) {
      exp = exp.and(exp.lt("EXCUDT", inCUDT));
    }
    
    DBAction EXTDSV_query = database.table("EXTDSV")
        .index("00")
        .matching(exp)
        .selection()
        .build();

    DBContainer EXTDSV = EXTDSV_query.getContainer();
    
    Closure<?> resultHandlerEXTDSV = { DBContainer data ->
      DBAction action = database.table("EXTDSV").index("00").build();
      action.readLock(data, { LockedResult lockedResult ->
        lockedResult.delete();
      });
    }
    
    if (EXTDSV_query.readAll(EXTDSV, 0, pageSize, resultHandlerEXTDSV) <= 0) {
      mi.error("No valid record found - EXTDSV");
      return;
    }
  }
  
  boolean isValidInput() {
    if (inCUDT.isBlank()) {
      mi.error("CUDT is required.");
      return false;
    }
    
    return true;
  }
}