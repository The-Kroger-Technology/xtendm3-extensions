/**
 * @Name: EXT005MI.LstEXTRAW
 * @Description: List record on table EXTRAW
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date      User    Description
 *  1.0.0     20250325  ADY     Initial Release
 *
 */

public class LstEXTRAW extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  
  private int inCONO;
  private String inFACI, inMTNO, inITNO, inITCL, inFDAT, inTDAT;
  
  public LstEXTRAW(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    final int MAX_RECORDS = mi.getMaxRecords() == 0 ? 10000 : mi.getMaxRecords();
    
    ExpressionFactory EXTRAW_exp = database.getExpressionFactory("EXTRAW");
    EXTRAW_exp = EXTRAW_exp.eq("EXCONO", inCONO.toString());
    
    if (!inFACI.isBlank()) {
      EXTRAW_exp = EXTRAW_exp.and(EXTRAW_exp.eq("EXFACI", inFACI));
    }
    
    if (!inMTNO.isBlank()) {
      EXTRAW_exp = EXTRAW_exp.and(EXTRAW_exp.eq("EXMTNO", inMTNO));
    }
    
    if (!inITNO.isBlank()) {
      EXTRAW_exp = EXTRAW_exp.and(EXTRAW_exp.eq("EXITNO", inITNO));
    }
    
    if (!inITCL.isBlank()) {
      EXTRAW_exp = EXTRAW_exp.and(EXTRAW_exp.eq("EXITCL", inITCL));
    }
    
    if (!inFDAT.isBlank()) {
      EXTRAW_exp = EXTRAW_exp.and(EXTRAW_exp.ge("EXFDAT", inFDAT));
    }
    
    if (!inTDAT.isBlank()) {
      EXTRAW_exp = EXTRAW_exp.and(EXTRAW_exp.le("EXTDAT", inTDAT));
    }
    
    DBAction EXTRAW_query = database.table("EXTRAW").index("00").matching(EXTRAW_exp).selectAllFields().build();
    DBContainer EXTRAW = EXTRAW_query.getContainer();
    
    EXTRAW_query.readAll(EXTRAW, 0, MAX_RECORDS, { DBContainer data ->
      mi.outData.put("CONO", data.get("EXCONO").toString());
      mi.outData.put("FACI", data.get("EXFACI").toString());
      mi.outData.put("MTNO", data.get("EXMTNO").toString());
      mi.outData.put("ITNO", data.get("EXITNO").toString());
      mi.outData.put("ITCL", data.get("EXITCL").toString());
      mi.outData.put("FDAT", data.get("EXFDAT").toString());
      mi.outData.put("TDAT", data.get("EXTDAT").toString());
      mi.outData.put("TRQT", data.get("EXTRQT").toString());
      mi.outData.put("TAMT", data.get("EXTAMT").toString());
      mi.outData.put("PCTG", data.get("EXPCTG").toString());
      mi.write();
    });
  }
}