/**
 * @Name: EXT005MI.LstEXTRAW
 * @Description: List record on table EXTRAW
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date      User    Description
 *  1.0.0     20250325  ADY     Initial Release
 *  1.0.1     20250826  ADY     Fixed variable names, set nbrOfKeys to 1
 *
 */

public class LstEXTRAW extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  
  private int inCONO, pageSize;
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
    pageSize = mi.getMaxRecords() <= 0 || mi.getMaxRecords() >= 10000? 10000: mi.getMaxRecords();
    
    ExpressionFactory exp = database.getExpressionFactory("EXTRAW");
    exp = exp.eq("EXCONO", inCONO.toString());
    
    if (!inFACI.isBlank()) {
      exp = exp.and(exp.eq("EXFACI", inFACI));
    }
    
    if (!inMTNO.isBlank()) {
      exp = exp.and(exp.eq("EXMTNO", inMTNO));
    }
    
    if (!inITNO.isBlank()) {
      exp = exp.and(exp.eq("EXITNO", inITNO));
    }
    
    if (!inITCL.isBlank()) {
      exp = exp.and(exp.eq("EXITCL", inITCL));
    }
    
    if (!inFDAT.isBlank()) {
      exp = exp.and(exp.ge("EXFDAT", inFDAT));
    }
    
    if (!inTDAT.isBlank()) {
      exp = exp.and(exp.le("EXTDAT", inTDAT));
    }
    
    DBAction queryEXTRAW = database.table("EXTRAW").index("00").matching(exp).selectAllFields().build();
    DBContainer containerEXTRAW = queryEXTRAW.getContainer();
    containerEXTRAW.set("EXCONO", inCONO);
    
    queryEXTRAW.readAll(containerEXTRAW, 1, pageSize, { DBContainer data ->
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