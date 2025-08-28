/**
 * @Name: LstEXTSGD.EXTSGD
 * @Description: List record on table EXTSGD
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date      User    Description
 *  1.0.0     20250604  ADY     Initial Release
 *  1.0.1     20250826  ADY     Fixed variable names, set nbrOfKeys to 1
 *
 */

public class LstEXTSGD extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  
  private int inCONO, pageSize;
  private String inSDST, inSTRG, inCUNO;
  
  public LstEXTSGD(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
    this.mi = mi;
    this.utility = utility;
    this.logger = logger;
    this.program = program;
    this.miCaller = miCaller;
    this.database = database;
  }
  
  public void main() {
    inCONO = mi.in.get("CONO") == null ? program.LDAZD.CONO as int : mi.in.get("CONO") as int;
    inSDST = mi.inData.get("SDST") == null ? "" : mi.inData.get("SDST").trim() as String;
    inSTRG = mi.inData.get("STRG") == null ? "" : mi.inData.get("STRG").trim() as String;
    inCUNO = mi.inData.get("CUNO") == null ? "" : mi.inData.get("CUNO").trim() as String;
    pageSize = mi.getMaxRecords() <= 0 || mi.getMaxRecords() >= 10000? 10000: mi.getMaxRecords();
    
    ExpressionFactory exp = database.getExpressionFactory("EXTSGD");
    exp = exp.eq("EXCONO", inCONO.toString());
    
    if (!inSDST.isBlank()) {
      exp = exp.and(exp.eq("EXSDST", inSDST));
    }
    
    if (!inSTRG.isBlank()) {
      exp = exp.and(exp.eq("EXSTRG", inSTRG));
    }
    
    if (!inCUNO.isBlank()) {
      exp = exp.and(exp.eq("EXCUNO", inCUNO));
    }
    
    DBAction queryEXTSGD = database.table("EXTSGD").index("00").matching(exp).selectAllFields().build();
    DBContainer containerEXTSGD = queryEXTSGD.getContainer();
    containerEXTSGD.set("EXCONO", inCONO);
    
    queryEXTSGD.readAll(containerEXTSGD, 1, pageSize, { DBContainer data ->
      mi.outData.put("CONO", data.get("EXCONO").toString());
      mi.outData.put("SDST", data.get("EXSDST").toString());
      mi.outData.put("STRG", data.get("EXSTRG").toString());
      mi.outData.put("CUNO", data.get("EXCUNO").toString());
      mi.write();
    });
  }
}