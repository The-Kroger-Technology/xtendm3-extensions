/**
 * @Name: LstEXTSTR.EXTSTR
 * @Description: List record on table EXTSTR
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     20240408  JHC     Initial Release - Generated from XtendM3 CRUD Generator, additional changes
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class LstEXTSTR extends ExtendM3Transaction {
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
  private String inYEVA;
  public LstEXTSTR(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inYEVA = mi.inData.get("YEVA") == null ? "" : mi.inData.get("YEVA").trim();
    DBAction query = database.table("EXTSTR").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    int index = 1;
    if (!inDIVI.isBlank()) {
      container.set("EXDIVI", inDIVI);
      index++;
    }
    if (!inWHLO.isBlank()) {
      container.set("EXWHLO", inWHLO);
      index++;
    }
    if (!inSTIS.isBlank()) {
      container.set("EXSTIS", inSTIS);
      index++;
    }
    if (!inYEIS.isBlank()) {
      container.set("EXYEIS", inYEIS as int);
      index++;
    }
    if (!inMOIS.isBlank()) {
      container.set("EXMOIS", inMOIS as int);
      index++;
    }
    query.readAll(container, index, 10000, {
      DBContainer data ->
      mi.outData.put("CONO", data.get("EXCONO").toString());
      mi.outData.put("DIVI", data.get("EXDIVI").toString());
      mi.outData.put("WHLO", data.get("EXWHLO").toString());
      mi.outData.put("STIS", data.get("EXSTIS").toString());
      mi.outData.put("YEIS", data.get("EXYEIS").toString());
      mi.outData.put("MOIS", data.get("EXMOIS").toString());
      mi.outData.put("YEVA", data.get("EXYEVA").toString());
      mi.write();
    });
  }
}