/**
 * @Name: GetEXTSGD.EXTSGD
 * @Description: Get record on table EXTSGD
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date      User    Description
 *  1.0.0     20250604  ADY     Initial Release
 *  1.0.1     20250826  ADY     Fixed variable names
 *
 */

public class GetEXTSGD extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  
  private int inCONO;
  private String inSDST, inSTRG, inCUNO;

  public GetEXTSGD(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    
    DBAction queryEXTSGD = database.table("EXTSGD").index("00").selectAllFields().build();
    DBContainer containerEXTSGD = queryEXTSGD.getContainer();
    containerEXTSGD.set("EXCONO", inCONO);
    containerEXTSGD.set("EXSDST", inSDST);
    containerEXTSGD.set("EXSTRG", inSTRG);
    containerEXTSGD.set("EXCUNO", inCUNO);
    
    if (queryEXTSGD.read(containerEXTSGD)) {
      mi.outData.put("CONO", containerEXTSGD.get("EXCONO").toString());
      mi.outData.put("SDST", containerEXTSGD.get("EXSDST").toString());
      mi.outData.put("STRG", containerEXTSGD.get("EXSTRG").toString());
      mi.outData.put("CUNO", containerEXTSGD.get("EXCUNO").toString());
      mi.write();
    } else {
      mi.error("Record does not exist");
      return;
    }
  }
}