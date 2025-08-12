/**
 * @Name: LstEXTTNK.EXTTNK
 * @Description: List record on table EXTTNK
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class LstEXTTNK extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  
  private int inCONO;
  private String inDIVI;
  private String inWHLO;
  private String inLDID;
  private String inTKID;
  final int MAX_RECORDS = mi.getMaxRecords() == 0 ? 10000 : mi.getMaxRecords();

  public LstEXTTNK(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
    this.mi = mi;
    this.utility = utility;
    this.logger = logger;
    this.program = program;
    this.miCaller = miCaller;
    this.database = database;
  }
  public void main() {
    inCONO = mi.in.get("CONO") == null ? program.LDAZD.CONO as int : mi.in.get("CONO") as Integer;
    inDIVI = mi.inData.get("DIVI") == null ? "" : mi.inData.get("DIVI").trim();
    inWHLO = mi.inData.get("WHLO") == null ? "" : mi.inData.get("WHLO").trim();
    inLDID = mi.inData.get("LDID") == null ? "" : mi.inData.get("LDID").trim();
    inTKID = mi.inData.get("TKID") == null ? "" : mi.inData.get("TKID").trim();
    
    DBAction query = database.table("EXTTNK").index("00").selectAllFields().build();
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
    if (!inLDID.isBlank()) {
      container.set("EXLDID", inLDID);
      index++;
    }
    if (!inTKID.isBlank()) {
      container.set("EXTKID", inTKID);
      index++;
    }
    query.readAll(container, index, MAX_RECORDS, {
      DBContainer data ->
      mi.outData.put("CONO", data.get("EXCONO").toString());
      mi.outData.put("DIVI", data.get("EXDIVI").toString());
      mi.outData.put("WHLO", data.get("EXWHLO").toString());
      mi.outData.put("LDID", data.get("EXLDID").toString());
      mi.outData.put("TKID", data.get("EXTKID").toString());
      mi.outData.put("TPOS", data.get("EXTPOS").toString());
      mi.outData.put("LTWL", data.get("EXLTWL").toString());
      mi.outData.put("WTDT", data.get("EXWTDT").toString());
      mi.outData.put("WTTM", data.get("EXWTTM").toString());
      mi.outData.put("WEDT", data.get("EXWEDT").toString());
      mi.outData.put("WETM", data.get("EXWETM").toString());
      mi.outData.put("PLWL", data.get("EXPLWL").toString());
      mi.outData.put("PWTD", data.get("EXPWTD").toString());
      mi.outData.put("PWTT", data.get("EXPWTT").toString());
      mi.outData.put("PWDT", data.get("EXPWDT").toString());
      mi.outData.put("PWTM", data.get("EXPWTM").toString());
      mi.outData.put("PMID", data.get("EXPMID").toString());
      mi.outData.put("MTNO", data.get("EXMTNO").toString());
      mi.outData.put("TEMP", data.get("EXTEMP").toString());
      mi.outData.put("TDWT", data.get("EXTDWT").toString());
      mi.outData.put("TCWT", data.get("EXTCWT").toString());
      mi.outData.put("RGST", data.get("EXRGST").toString());
      mi.outData.put("RGYE", data.get("EXRGYE").toString());
      mi.outData.put("RGMO", data.get("EXRGMO").toString());
      mi.write();
    });
  }
}