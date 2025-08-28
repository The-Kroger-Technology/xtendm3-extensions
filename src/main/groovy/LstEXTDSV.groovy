/**
 * @Name: LstEXTDSV.EXTDSV
 * @Description: List record on table EXTDSV
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date      User    Description
 *  1.0.0     20250604  ADY     Initial Release
 *  1.0.1     20250826  ADY     Fixed variable names, set nbrOfKeys to 1
 *
 */

public class LstEXTDSV extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  
  private int inCONO, pageSize;
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
    pageSize = mi.getMaxRecords() <= 0 || mi.getMaxRecords() >= 10000? 10000: mi.getMaxRecords();
    
    ExpressionFactory exp = database.getExpressionFactory("EXTDSV");
    exp = exp.eq("EXCONO", inCONO.toString());
    
    if (!inCUDT.isBlank()) {
      exp = exp.and(exp.eq("EXCUDT", inCUDT));
    }
    
    if (!inFACI.isBlank()) {
      exp = exp.and(exp.eq("EXFACI", inFACI));
    }
    
    if (!inSDST.isBlank()) {
      exp = exp.and(exp.eq("EXSDST", inSDST));
    }
    
    if (!inCUNO.isBlank()) {
      exp = exp.and(exp.eq("EXCUNO", inCUNO));
    }
    
    if (!inITNO.isBlank()) {
      exp = exp.and(exp.eq("EXITNO", inITNO));
    }
    
    DBAction queryEXTDSV = database.table("EXTDSV").index("00").matching(exp).selectAllFields().build();
    DBContainer containerEXTDSV = queryEXTDSV.getContainer();
    containerEXTDSV.set("EXCONO", inCONO);
    
    queryEXTDSV.readAll(containerEXTDSV, 1, pageSize, { DBContainer data ->
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