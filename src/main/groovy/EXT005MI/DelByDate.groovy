/**
 * @Name: EXT005MI.DelByDate
 * @Description: Delete record on table EXTRAW by Date
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date      User    Description
 *  1.0.0     20250415  ADY     Initial Release
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
  private String inFDAT, inTDAT;
  
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
    inFDAT = mi.inData.get("FDAT") == null ? "" : mi.inData.get("FDAT").trim() as String;
    inTDAT = mi.inData.get("TDAT") == null ? "" : mi.inData.get("TDAT").trim() as String;
    pageSize = mi.getMaxRecords() <= 0 || mi.getMaxRecords() >= 10000? 10000: mi.getMaxRecords();
    
    if (!isValidInput()) {
      return;
    }
    
    ExpressionFactory exp = database.getExpressionFactory("EXTRAW");
    exp = exp.eq("EXCONO", inCONO.toString());
    
    if (!inFDAT.isBlank()) {
      exp = exp.and(exp.ge("EXFDAT", inFDAT));
    }
    
    if (!inTDAT.isBlank()) {
      exp = exp.and(exp.le("EXTDAT", inTDAT)); 
    }
    
    DBAction EXTRAW_query = database.table("EXTRAW")
        .index("00")
        .matching(exp)
        .selection()
        .build();

    DBContainer EXTRAW = EXTRAW_query.getContainer();
    
    Closure<?> resultHandlerEXTRAW = { DBContainer data ->
      DBAction action = database.table("EXTRAW").index("00").build();
      action.readLock(data, { LockedResult lockedResult ->
        lockedResult.delete();
      });
    }
    
    if (EXTRAW_query.readAll(EXTRAW, 0, pageSize, resultHandlerEXTRAW) <= 0) {
      mi.error("No valid record found - EXTRAW");
      return;
    }
  }
  
  boolean isValidInput() {
    if (inFDAT.isBlank() && inTDAT.isBlank()) {
      mi.error("At least one field - either FDAT or TDAT - is required.");
      return false;
    }
    
    return true;
  }
}