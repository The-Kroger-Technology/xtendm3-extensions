/**
 * @Name: LstEXTMUR.EXTMUR
 * @Description: List record on table EXTMUR
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     20250723 ADY      Initial Release
 *
 */

public class LstEXTMUR extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  
  private int inCONO;
  private String inDIVI, inWHLO, inDATE, inMFNO, inPRNO;
  
  public LstEXTMUR(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
    this.mi = mi;
    this.utility = utility;
    this.logger = logger;
    this.program = program;
    this.miCaller = miCaller;
    this.database = database;
  }
  
  public void main() {
    inCONO = mi.in.get("CONO") == null ? program.LDAZD.CONO as int : mi.in.get("CONO") as int;
    inDIVI = mi.inData.get("DIVI") == null ? "" : mi.inData.get("DIVI").trim() as String;
    inWHLO = mi.inData.get("WHLO") == null ? "" : mi.inData.get("WHLO").trim() as String;
    inDATE = mi.inData.get("DATE") == null ? "" : mi.inData.get("DATE").trim() as String;
    inMFNO = mi.inData.get("MFNO") == null ? "" : mi.inData.get("MFNO").trim() as String;
    inPRNO = mi.inData.get("PRNO") == null ? "" : mi.inData.get("PRNO").trim() as String;
    final int MAX_RECORDS = mi.getMaxRecords() == 0 ? 10000 : mi.getMaxRecords();
    
    ExpressionFactory EXTMUR_exp = database.getExpressionFactory("EXTMUR");
    EXTMUR_exp = EXTMUR_exp.eq("EXCONO", inCONO.toString());
    
    if (!inDIVI.isBlank()) {
      EXTMUR_exp = EXTMUR_exp.and(EXTMUR_exp.eq("EXDIVI", inDIVI));
    }
    
    if (!inWHLO.isBlank()) {
      EXTMUR_exp = EXTMUR_exp.and(EXTMUR_exp.eq("EXWHLO", inWHLO));
    }
    
    if (!inDATE.isBlank()) {
      EXTMUR_exp = EXTMUR_exp.and(EXTMUR_exp.eq("EXDATE", inDATE));
    }
    
    if (!inMFNO.isBlank()) {
      EXTMUR_exp = EXTMUR_exp.and(EXTMUR_exp.eq("EXMFNO", inMFNO));
    }
    
    if (!inPRNO.isBlank()) {
      EXTMUR_exp = EXTMUR_exp.and(EXTMUR_exp.eq("EXPRNO", inPRNO));
    }
    
    DBAction EXTMUR_query = database.table("EXTMUR").index("00").matching(EXTMUR_exp).selectAllFields().build();
    DBContainer EXTMUR = EXTMUR_query.getContainer();
    
    EXTMUR_query.readAll(EXTMUR, 0, MAX_RECORDS, { DBContainer data ->
      mi.outData.put("CONO", data.get("EXCONO").toString());
      mi.outData.put("DIVI", data.get("EXDIVI").toString());
      mi.outData.put("WHLO", data.get("EXWHLO").toString());
      mi.outData.put("DATE", data.get("EXDATE").toString());
      mi.outData.put("MFNO", data.get("EXMFNO").toString());
      mi.outData.put("PRNO", data.get("EXPRNO").toString());
      mi.outData.put("MKCL", data.get("EXMKCL").toString());
      mi.outData.put("CMDT", data.get("EXCMDT").toString());
      mi.outData.put("SCMD", data.get("EXSCMD").toString());
      mi.outData.put("MFQT", data.get("EXMFQT").toString());
      mi.outData.put("UNMS", data.get("EXUNMS").toString());
      mi.outData.put("AMQT", data.get("EXAMQT").toString());
      mi.outData.put("SMQT", data.get("EXSMQT").toString());
      mi.outData.put("ABQT", data.get("EXABQT").toString());
      mi.outData.put("SBQT", data.get("EXSBQT").toString());
      mi.outData.put("AOMQ", data.get("EXAOMQ").toString());
      mi.outData.put("SOMQ", data.get("EXSOMQ").toString());
      mi.outData.put("AOBQ", data.get("EXAOBQ").toString());
      mi.outData.put("SOBQ", data.get("EXSOBQ").toString());
      mi.write();
    });
  }
}