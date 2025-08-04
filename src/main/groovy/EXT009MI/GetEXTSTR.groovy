/**
 * @Name: GetEXTSTR.EXTSTR
 * @Description: Get record on table EXTSTR
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     20240408  JHC     Initial Release - Generated from XtendM3 CRUD Generator, additional changes
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class GetEXTSTR extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  private int inCONO;
  private String inDIVI;
  private String inWHLO;
  private String inSTIS;
  private String inYEIS;
  private String inMOIS;
  public GetEXTSTR(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inSTIS = mi.inData.get("STIS") == null ? "" : mi.inData.get("STIS").trim();
    inYEIS = mi.inData.get("YEIS") == null ? "" : mi.inData.get("YEIS").trim();
    inMOIS = mi.inData.get("MOIS") == null ? "" : mi.inData.get("MOIS").trim();
    
    DBAction query = database.table("EXTSTR").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXSTIS", inSTIS);
    container.set("EXYEIS", inYEIS as int);
    container.set("EXMOIS", inMOIS as int);
    if (!query.read(container)) {
      mi.error("Record does not exists");
      return;
    }
    mi.outData.put("CONO", container.get("EXCONO").toString());
    mi.outData.put("DIVI", container.get("EXDIVI").toString());
    mi.outData.put("WHLO", container.get("EXWHLO").toString());
    mi.outData.put("STIS", container.get("EXSTIS").toString());
    mi.outData.put("YEIS", container.get("EXYEIS").toString());
    mi.outData.put("MOIS", container.get("EXMOIS").toString());
    mi.outData.put("YEVA", container.get("EXYEVA").toString());
    mi.write();
  }
}