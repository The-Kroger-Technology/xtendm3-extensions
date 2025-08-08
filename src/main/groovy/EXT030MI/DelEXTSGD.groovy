/**
 * @Name: DelEXTSGD.EXTSGD
 * @Description: Delete record on table EXTSGD
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     20250604 ADY      Initial Release
 *
 */

public class DelEXTSGD extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  
  private int inCONO;
  private String inSDST, inSTRG, inCUNO;

  public DelEXTSGD(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    
    DBAction EXTSGD_query = database.table("EXTSGD").index("00").build();
    DBContainer EXTSGD = EXTSGD_query.getContainer();
    EXTSGD.set("EXCONO", inCONO);
    EXTSGD.set("EXSDST", inSDST);
    EXTSGD.set("EXSTRG", inSTRG);
    EXTSGD.set("EXCUNO", inCUNO);
    
    if (!EXTSGD_query.read(EXTSGD)) {
      mi.error("Record does not exist");
      return;
    }
    
    EXTSGD_query.readLock(EXTSGD, { LockedResult lockedResult ->
      lockedResult.delete();
    });
  }
}