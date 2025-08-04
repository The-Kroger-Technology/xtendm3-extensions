/**
 * @Name: GetEXTPCS.EXTPCS
 * @Description: Get record on table EXTPCS
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     20240408  JHC     Initial Release - Generated from XtendM3 CRUD Generator, additional changes
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class GetEXTPCS extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  private int inCONO;
  private String inDIVI;
  private String inWHLO;
  
  public GetEXTPCS(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
    this.mi = mi;
    this.utility = utility;
    this.logger = logger;
    this.program = program;
    this.miCaller = miCaller;
    this.database = database;
  }
  public void main() {
    inCONO = mi.in.get("CONO") == null ? program.LDAZD.CONO as int : mi.in.get("CONO") as int;
    inDIVI = mi.inData.get("DIVI") == null ? "" : mi.inData.get("DIVI").trim();
    inWHLO = mi.inData.get("WHLO") == null ? "" : mi.inData.get("WHLO").trim();

    DBAction query = database.table("EXTPCS").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    if (!query.read(container)) {
      mi.error("Record does not exists");
      return;
    }
    mi.outData.put("CONO", container.get("EXCONO").toString());
    mi.outData.put("DIVI", container.get("EXDIVI").toString());
    mi.outData.put("WHLO", container.get("EXWHLO").toString());
    mi.outData.put("REGN", container.get("EXREGN").toString());
    mi.outData.put("LDNU", container.get("EXLDNU").toString());
    mi.outData.put("LRNU", container.get("EXLRNU").toString());
    mi.outData.put("NBRB", container.get("EXNBRB").toString());
    mi.outData.put("SCLE", container.get("EXSCLE").toString());
    mi.outData.put("SCIN", container.get("EXSCIN").toString());
    mi.outData.put("FLMT", container.get("EXFLMT").toString());
    mi.outData.put("ESCH", container.get("EXESCH").toString());
    mi.outData.put("EMAN", container.get("EXEMAN").toString());
    mi.outData.put("DBFP", container.get("EXDBFP").toString());
    mi.outData.put("DPRP", container.get("EXDPRP").toString());
    mi.outData.put("DSOP", container.get("EXDSOP").toString());
    mi.outData.put("URPC", container.get("EXURPC").toString());
    mi.write();
  }
}