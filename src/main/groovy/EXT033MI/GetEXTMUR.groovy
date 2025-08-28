/**
 * @Name: GetEXTMUR.EXTMUR
 * @Description: Get record on table EXTMUR
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date      User    Description
 *  1.0.0     20250723  ADY     Initial Release
 *  1.0.1     20250814  ADY     Added outputs ANFQ, SNFQ
 *  1.0.2     20250826  ADY     Fixed variable names
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
    
    DBAction queryEXTMUR = database.table("EXTMUR").index("00").selectAllFields().build();
    DBContainer containerEXTMUR = queryEXTMUR.getContainer();
    containerEXTMUR.set("EXCONO", inCONO);
    containerEXTMUR.set("EXDIVI", inDIVI);
    containerEXTMUR.set("EXWHLO", inWHLO);
    containerEXTMUR.set("EXDATE", inDATE as int);
    containerEXTMUR.set("EXMFNO", inMFNO);
    containerEXTMUR.set("EXPRNO", inPRNO);
    
    if (!queryEXTMUR.read(containerEXTMUR)) {
      mi.error("Record does not exist");
      return;
    }
    
    mi.outData.put("CONO", containerEXTMUR.get("EXCONO").toString());
    mi.outData.put("DIVI", containerEXTMUR.get("EXDIVI").toString());
    mi.outData.put("WHLO", containerEXTMUR.get("EXWHLO").toString());
    mi.outData.put("DATE", containerEXTMUR.get("EXDATE").toString());
    mi.outData.put("MFNO", containerEXTMUR.get("EXMFNO").toString());
    mi.outData.put("PRNO", containerEXTMUR.get("EXPRNO").toString());
    mi.outData.put("MKCL", containerEXTMUR.get("EXMKCL").toString());
    mi.outData.put("CMDT", containerEXTMUR.get("EXCMDT").toString());
    mi.outData.put("SCMD", containerEXTMUR.get("EXSCMD").toString());
    mi.outData.put("MFQT", containerEXTMUR.get("EXMFQT").toString());
    mi.outData.put("UNMS", containerEXTMUR.get("EXUNMS").toString());
    mi.outData.put("AMQT", containerEXTMUR.get("EXAMQT").toString());
    mi.outData.put("SMQT", containerEXTMUR.get("EXSMQT").toString());
    mi.outData.put("ABQT", containerEXTMUR.get("EXABQT").toString());
    mi.outData.put("SBQT", containerEXTMUR.get("EXSBQT").toString());
    mi.outData.put("AOMQ", containerEXTMUR.get("EXAOMQ").toString());
    mi.outData.put("SOMQ", containerEXTMUR.get("EXSOMQ").toString());
    mi.outData.put("AOBQ", containerEXTMUR.get("EXAOBQ").toString());
    mi.outData.put("SOBQ", containerEXTMUR.get("EXSOBQ").toString());
    mi.outData.put("ANFQ", containerEXTMUR.get("EXANFQ").toString());
    mi.outData.put("SNFQ", containerEXTMUR.get("EXSNFQ").toString());
    mi.write();
  }
}