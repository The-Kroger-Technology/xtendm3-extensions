/**
 * @Name: LstEXTSGD.EXTSGD
 * @Description: List record on table EXTSGD
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     20250604 ADY      Initial Release
 *
 */

public class LstEXTSGD extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  
  private int inCONO;
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
    final int MAX_RECORDS = mi.getMaxRecords() == 0 ? 10000 : mi.getMaxRecords();
    
    ExpressionFactory EXTSGD_exp = database.getExpressionFactory("EXTSGD");
    EXTSGD_exp = EXTSGD_exp.eq("EXCONO", inCONO.toString());
    
    if (!inSDST.isBlank()) {
      EXTSGD_exp = EXTSGD_exp.and(EXTSGD_exp.eq("EXSDST", inSDST));
    }
    
    if (!inSTRG.isBlank()) {
      EXTSGD_exp = EXTSGD_exp.and(EXTSGD_exp.eq("EXSTRG", inSTRG));
    }
    
    if (!inCUNO.isBlank()) {
      EXTSGD_exp = EXTSGD_exp.and(EXTSGD_exp.eq("EXCUNO", inCUNO));
    }
    
    DBAction EXTSGD_query = database.table("EXTSGD").index("00").matching(EXTSGD_exp).selectAllFields().build();
    DBContainer EXTSGD = EXTSGD_query.getContainer();
    
    EXTSGD_query.readAll(EXTSGD, 0, MAX_RECORDS, { DBContainer data ->
      mi.outData.put("CONO", data.get("EXCONO").toString());
      mi.outData.put("SDST", data.get("EXSDST").toString());
      mi.outData.put("STRG", data.get("EXSTRG").toString());
      mi.outData.put("CUNO", data.get("EXCUNO").toString());
      mi.write();
    });
  }
}