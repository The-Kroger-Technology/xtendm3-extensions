/**
 * @Name: LstEXTROT.EXTROT
 * @Description: List record on table EXTROT
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class LstEXTROT extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  private int inCONO;
  private String inDIVI;
  private String inWHLO;
  private String inRNBR;
  private String inRHID;
  private String inROHN;
  private String inRODE;
  private String inHPRT;
  private String inSTAT;
  private String inDBFP;
  
  public LstEXTROT(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inRNBR = mi.inData.get("RNBR") == null ? "" : mi.inData.get("RNBR").trim();
    inRHID = mi.inData.get("RHID") == null ? "" : mi.inData.get("RHID").trim();
    inROHN = mi.inData.get("ROHN") == null ? "" : mi.inData.get("ROHN").trim();
    inRODE = mi.inData.get("RODE") == null ? "" : mi.inData.get("RODE").trim();
    inHPRT = mi.inData.get("HPRT") == null ? "" : mi.inData.get("HPRT").trim();
    inSTAT = mi.inData.get("STAT") == null ? "" : mi.inData.get("STAT").trim();
    inDBFP = mi.inData.get("DBFP") == null ? "" : mi.inData.get("DBFP").trim();
    DBAction query = database.table("EXTROT").index("00").selectAllFields().build();
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
    if (!inRNBR.isBlank()) {
      container.set("EXRNBR", inRNBR);
      index++;
    }
    if (!inRHID.isBlank()) {
      container.set("EXRHID", inRHID);
      index++;
    }
    query.readAll(container, index, 10000, {
      DBContainer data ->
      mi.outData.put("CONO", data.get("EXCONO").toString());
      mi.outData.put("DIVI", data.get("EXDIVI").toString());
      mi.outData.put("WHLO", data.get("EXWHLO").toString());
      mi.outData.put("RNBR", data.get("EXRNBR").toString());
      mi.outData.put("RHID", data.get("EXRHID").toString());
      mi.outData.put("ROHN", data.get("EXROHN").toString());
      mi.outData.put("RODE", data.get("EXRODE").toString());
      mi.outData.put("HPRT", data.get("EXHPRT").toString());
      mi.outData.put("STAT", data.get("EXSTAT").toString());
      mi.outData.put("DBFP", data.get("EXDBFP").toString());
      mi.write();
    });
  }
}