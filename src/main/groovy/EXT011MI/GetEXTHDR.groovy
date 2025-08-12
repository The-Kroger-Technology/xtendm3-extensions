/**
 * @Name: GetEXTHDR.EXTHDR
 * @Description: Get record on table EXTHDR
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     250704   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class GetEXTHDR extends ExtendM3Transaction {
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
  
  public GetEXTHDR(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    
    if (!query.read(container)) {
      mi.error("Record does not exists");
      return;
    }
    
    mi.outData.put("CONO", container.get("EXCONO").toString());
    mi.outData.put("DIVI", container.get("EXDIVI").toString());
    mi.outData.put("WHLO", container.get("EXWHLO").toString());
    mi.outData.put("LDID", container.get("EXLDID").toString());
    mi.outData.put("PTID", container.get("EXPTID").toString());
    mi.outData.put("LADT", container.get("EXLADT").toString());
    mi.outData.put("LATM", container.get("EXLATM").toString());
    mi.outData.put("CTTP", container.get("EXCTTP").toString());
    mi.outData.put("MKTP", container.get("EXMKTP").toString());
    mi.outData.put("RFNO", container.get("EXRFNO").toString());
    mi.outData.put("RTNO", container.get("EXRTNO").toString());
    mi.outData.put("STAT", container.get("EXSTAT").toString());
    mi.outData.put("TPND", container.get("EXTPND").toString());
    mi.outData.put("WVAR", container.get("EXWVAR").toString());
    mi.outData.put("MKGD", container.get("EXMKGD").toString());
    mi.outData.put("STNM", container.get("EXSTNM").toString());
    mi.outData.put("STCD", container.get("EXSTCD").toString());
    mi.outData.put("SDNM", container.get("EXSDNM").toString());
    mi.outData.put("SDCD", container.get("EXSDCD").toString());
    mi.outData.put("ARDT", container.get("EXARDT").toString());
    mi.outData.put("ARTM", container.get("EXARTM").toString());
    mi.outData.put("RJDT", container.get("EXRJDT").toString());
    mi.outData.put("RJTM", container.get("EXRJTM").toString());
    mi.write();
  }
}