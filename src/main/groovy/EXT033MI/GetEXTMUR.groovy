/**
 * @Name: GetEXTMUR.EXTMUR
 * @Description: Get record on table EXTMUR
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     20250723 ADY      Initial Release
 *
 */

public class GetEXTMUR extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  
  private int inCONO;
  private String inDIVI, inWHLO, inDATE, inMFNO, inPRNO;
  
  public GetEXTMUR(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    
    DBAction EXTMUR_query = database.table("EXTMUR").index("00").selectAllFields().build();
    DBContainer EXTMUR = EXTMUR_query.getContainer();
    EXTMUR.set("EXCONO", inCONO);
    EXTMUR.set("EXDIVI", inDIVI);
    EXTMUR.set("EXWHLO", inWHLO);
    EXTMUR.set("EXDATE", inDATE as int);
    EXTMUR.set("EXMFNO", inMFNO);
    EXTMUR.set("EXPRNO", inPRNO);
    
    if (!EXTMUR_query.read(EXTMUR)) {
      mi.error("Record does not exist");
      return;
    }
    
    mi.outData.put("CONO", EXTMUR.get("EXCONO").toString());
    mi.outData.put("DIVI", EXTMUR.get("EXDIVI").toString());
    mi.outData.put("WHLO", EXTMUR.get("EXWHLO").toString());
    mi.outData.put("DATE", EXTMUR.get("EXDATE").toString());
    mi.outData.put("MFNO", EXTMUR.get("EXMFNO").toString());
    mi.outData.put("PRNO", EXTMUR.get("EXPRNO").toString());
    mi.outData.put("MKCL", EXTMUR.get("EXMKCL").toString());
    mi.outData.put("CMDT", EXTMUR.get("EXCMDT").toString());
    mi.outData.put("SCMD", EXTMUR.get("EXSCMD").toString());
    mi.outData.put("MFQT", EXTMUR.get("EXMFQT").toString());
    mi.outData.put("UNMS", EXTMUR.get("EXUNMS").toString());
    mi.outData.put("AMQT", EXTMUR.get("EXAMQT").toString());
    mi.outData.put("SMQT", EXTMUR.get("EXSMQT").toString());
    mi.outData.put("ABQT", EXTMUR.get("EXABQT").toString());
    mi.outData.put("SBQT", EXTMUR.get("EXSBQT").toString());
    mi.outData.put("AOMQ", EXTMUR.get("EXAOMQ").toString());
    mi.outData.put("SOMQ", EXTMUR.get("EXSOMQ").toString());
    mi.outData.put("AOBQ", EXTMUR.get("EXAOBQ").toString());
    mi.outData.put("SOBQ", EXTMUR.get("EXSOBQ").toString());
    mi.write();
  }
}