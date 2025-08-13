/**
 * @Name: LstEXTPCS.EXTPCS
 * @Description: List record on table EXTPCS
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     20240408  JHC     Initial Release - Generated from XtendM3 CRUD Generator, additional changes
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class LstEXTPCS extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  private int inCONO;
  private String inDIVI;
  private String inWHLO;
  private String inREGN;
  private String inLDNU;
  private String inLRNU;
  private String inNBRB;
  private String inSCLE;
  private String inSCIN;
  private String inFLMT;
  private String inESCH;
  private String inEMAN;
  private String inDBFP;
  private String inDPRP;
  private String inDSOP;
  private String inURPC;
  public LstEXTPCS(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inREGN = mi.inData.get("REGN") == null ? "" : mi.inData.get("REGN").trim();
    inLDNU = mi.inData.get("LDNU") == null ? "" : mi.inData.get("LDNU").trim();
    inLRNU = mi.inData.get("LRNU") == null ? "" : mi.inData.get("LRNU").trim();
    inNBRB = mi.inData.get("NBRB") == null ? "" : mi.inData.get("NBRB").trim();
    inSCLE = mi.inData.get("SCLE") == null ? "" : mi.inData.get("SCLE").trim();
    inSCIN = mi.inData.get("SCIN") == null ? "" : mi.inData.get("SCIN").trim();
    inFLMT = mi.inData.get("FLMT") == null ? "" : mi.inData.get("FLMT").trim();
    inESCH = mi.inData.get("ESCH") == null ? "" : mi.inData.get("ESCH").trim();
    inEMAN = mi.inData.get("EMAN") == null ? "" : mi.inData.get("EMAN").trim();
    inDBFP = mi.inData.get("DBFP") == null ? "" : mi.inData.get("DBFP").trim();
    inDPRP = mi.inData.get("DPRP") == null ? "" : mi.inData.get("DPRP").trim();
    inDSOP = mi.inData.get("DSOP") == null ? "" : mi.inData.get("DSOP").trim();
    inURPC = mi.inData.get("URPC") == null ? "" : mi.inData.get("URPC").trim();
    DBAction query = database.table("EXTPCS").index("00").selectAllFields().build();
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
    query.readAll(container, index, 10000, {
      DBContainer data ->
      mi.outData.put("CONO", data.get("EXCONO").toString());
      mi.outData.put("DIVI", data.get("EXDIVI").toString());
      mi.outData.put("WHLO", data.get("EXWHLO").toString());
      mi.outData.put("REGN", data.get("EXREGN").toString());
      mi.outData.put("LDNU", data.get("EXLDNU").toString());
      mi.outData.put("LRNU", data.get("EXLRNU").toString());
      mi.outData.put("NBRB", data.get("EXNBRB").toString());
      mi.outData.put("SCLE", data.get("EXSCLE").toString());
      mi.outData.put("SCIN", data.get("EXSCIN").toString());
      mi.outData.put("FLMT", data.get("EXFLMT").toString());
      mi.outData.put("ESCH", data.get("EXESCH").toString());
      mi.outData.put("EMAN", data.get("EXEMAN").toString());
      mi.outData.put("DBFP", data.get("EXDBFP").toString());
      mi.outData.put("DPRP", data.get("EXDPRP").toString());
      mi.outData.put("DSOP", data.get("EXDSOP").toString());
      mi.outData.put("URPC", data.get("EXURPC").toString());
      mi.write();
    });
  }
}