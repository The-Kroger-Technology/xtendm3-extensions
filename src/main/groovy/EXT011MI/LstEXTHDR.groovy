/**
 * @Name: LstEXTHDR.EXTHDR
 * @Description: List record on table EXTHDR
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     250704   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class LstEXTHDR extends ExtendM3Transaction {
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
  final int MAX_RECORDS = mi.getMaxRecords() == 0 ? 10000 : mi.getMaxRecords();
  
  public LstEXTHDR(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    
    DBAction query = database.table("EXTHDR").index("00").selectAllFields().build();
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
    
    query.readAll(container, index, MAX_RECORDS, {
      DBContainer data ->
      mi.outData.put("CONO", data.get("EXCONO").toString());
      mi.outData.put("DIVI", data.get("EXDIVI").toString());
      mi.outData.put("WHLO", data.get("EXWHLO").toString());
      mi.outData.put("LDID", data.get("EXLDID").toString());
      mi.outData.put("PTID", data.get("EXPTID").toString());
      mi.outData.put("LADT", data.get("EXLADT").toString());
      mi.outData.put("LATM", data.get("EXLATM").toString());
      mi.outData.put("CTTP", data.get("EXCTTP").toString());
      mi.outData.put("MKTP", data.get("EXMKTP").toString());
      mi.outData.put("RFNO", data.get("EXRFNO").toString());
      mi.outData.put("RTNO", data.get("EXRTNO").toString());
      mi.outData.put("STAT", data.get("EXSTAT").toString());
      mi.outData.put("TPND", data.get("EXTPND").toString());
      mi.outData.put("WVAR", data.get("EXWVAR").toString());
      mi.outData.put("MKGD", data.get("EXMKGD").toString());
      mi.outData.put("STNM", data.get("EXSTNM").toString());
      mi.outData.put("STCD", data.get("EXSTCD").toString());
      mi.outData.put("SDNM", data.get("EXSDNM").toString());
      mi.outData.put("SDCD", data.get("EXSDCD").toString());
      mi.outData.put("ARDT", data.get("EXARDT").toString());
      mi.outData.put("ARTM", data.get("EXARTM").toString());
      mi.outData.put("RJDT", data.get("EXRJDT").toString());
      mi.outData.put("RJTM", data.get("EXRJTM").toString());
      mi.write();
    });
  }
}