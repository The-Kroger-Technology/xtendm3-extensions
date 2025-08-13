/**
 * @Name: LstEXTDSV.EXTDSV
 * @Description: List record on table EXTDSV
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     20250604 ADY      Initial Release
 *
 */

public class LstEXTDSV extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  
  private int inCONO;
  private String inCUDT, inFACI, inSDST, inCUNO, inITNO;

  public LstEXTDSV(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    final int MAX_RECORDS = mi.getMaxRecords() == 0 ? 10000 : mi.getMaxRecords();
    
    ExpressionFactory EXTDSV_exp = database.getExpressionFactory("EXTDSV");
    EXTDSV_exp = EXTDSV_exp.eq("EXCONO", inCONO.toString());
    
    if (!inCUDT.isBlank()) {
      EXTDSV_exp = EXTDSV_exp.and(EXTDSV_exp.eq("EXCUDT", inCUDT));
    }
    
    if (!inFACI.isBlank()) {
      EXTDSV_exp = EXTDSV_exp.and(EXTDSV_exp.eq("EXFACI", inFACI));
    }
    
    if (!inSDST.isBlank()) {
      EXTDSV_exp = EXTDSV_exp.and(EXTDSV_exp.eq("EXSDST", inSDST));
    }
    
    if (!inCUNO.isBlank()) {
      EXTDSV_exp = EXTDSV_exp.and(EXTDSV_exp.eq("EXCUNO", inCUNO));
    }
    
    if (!inITNO.isBlank()) {
      EXTDSV_exp = EXTDSV_exp.and(EXTDSV_exp.eq("EXITNO", inITNO));
    }
    
    DBAction EXTDSV_query = database.table("EXTDSV").index("00").matching(EXTDSV_exp).selectAllFields().build();
    DBContainer EXTDSV = EXTDSV_query.getContainer();
    
    EXTDSV_query.readAll(EXTDSV, 0, MAX_RECORDS, { DBContainer data ->
      mi.outData.put("CONO", data.get("EXCONO").toString());
      mi.outData.put("CUDT", data.get("EXCUDT").toString());
      mi.outData.put("FACI", data.get("EXFACI").toString());
      mi.outData.put("SDST", data.get("EXSDST").toString());
      mi.outData.put("CUNO", data.get("EXCUNO").toString());
      mi.outData.put("ITNO", data.get("EXITNO").toString());
      mi.outData.put("ORQT", data.get("EXORQT").toString());
      mi.write();
    });
  }
}