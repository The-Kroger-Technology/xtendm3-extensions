/**
 * @Name: GetEXTTNK.EXTTNK
 * @Description: Get record on table EXTTNK
 * @Authors:   Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class GetEXTTNK extends ExtendM3Transaction {
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

  public GetEXTTNK(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    container.set("EXTKID", inTKID);
    
    if (!query.read(container)) {
      mi.error("Record does not exists");
      return;
    }
    mi.outData.put("CONO", container.get("EXCONO").toString());
    mi.outData.put("DIVI", container.get("EXDIVI").toString());
    mi.outData.put("WHLO", container.get("EXWHLO").toString());
    mi.outData.put("LDID", container.get("EXLDID").toString());
    mi.outData.put("TKID", container.get("EXTKID").toString());
    mi.outData.put("TPOS", container.get("EXTPOS").toString());
    mi.outData.put("LTWL", container.get("EXLTWL").toString());
    mi.outData.put("WTDT", container.get("EXWTDT").toString());
    mi.outData.put("WTTM", container.get("EXWTTM").toString());
    mi.outData.put("WEDT", container.get("EXWEDT").toString());
    mi.outData.put("WETM", container.get("EXWETM").toString());
    mi.outData.put("PLWL", container.get("EXPLWL").toString());
    mi.outData.put("PWTD", container.get("EXPWTD").toString());
    mi.outData.put("PWTT", container.get("EXPWTT").toString());
    mi.outData.put("PWDT", container.get("EXPWDT").toString());
    mi.outData.put("PWTM", container.get("EXPWTM").toString());
    mi.outData.put("PMID", container.get("EXPMID").toString());
    mi.outData.put("MTNO", container.get("EXMTNO").toString());
    mi.outData.put("TEMP", container.get("EXTEMP").toString());
    mi.outData.put("TDWT", container.get("EXTDWT").toString());
    mi.outData.put("TCWT", container.get("EXTCWT").toString());
    mi.outData.put("RGST", container.get("EXRGST").toString());
    mi.outData.put("RGYE", container.get("EXRGYE").toString());
    mi.outData.put("RGMO", container.get("EXRGMO").toString());
    mi.write();
  }
}