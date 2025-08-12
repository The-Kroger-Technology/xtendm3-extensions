/**
 * @Name: GetEXTBCT.EXTBCT
 * @Description: Get record on table EXTBCT
 * @Authors: Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class GetEXTBCT extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  private int inCONO;
  private String inDIVI;
  private String inWHLO;
  private String inBYID;
  private String ldid;
  private String puno;
  private String suno;
  private String sunm;
  private String defORTY = 'K51';
  private String pusl = '[35 TO 75]';
  private String pdst;
  
  public GetEXTBCT(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inBYID = mi.inData.get("BYID") == null ? "" : mi.inData.get("BYID").trim();
    
    DBAction query = database.table("EXTBCT").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXBYID", inBYID);
    
    if (!query.read(container)) {
      mi.error("Record does not exists");
      return;
    }

    ldid = (container.get("EXLDID") ?: "").toString().trim();
    Map<String, String> params = [ "SQRY":"CONO:${inCONO} AND WHLO:${inWHLO} AND UCA1:${ldid} AND ORTY:'${defORTY}' AND PUSL:${pusl}".toString() ]; // toString is needed to convert from gstring to string
    
    Closure<?> callback = {
      Map<String, String> response ->
        logger.info("Response = ${response}");
        puno = response.PUNO;
        suno = response.SUNO;
        sunm = response.SUNM;
    }
    
    miCaller.setListMaxRecords(1);
    miCaller.call("PPS200MI","SearchLine", params, callback);
    
    DBAction queryEXTDL1 = database.table("EXTDL1").index("00").selection("EXPDST").build();
    DBContainer conEXTDL1 = queryEXTDL1.getContainer();
    conEXTDL1.set("EXCONO", inCONO);
    conEXTDL1.set("EXDIVI", inDIVI);
    conEXTDL1.set("EXWHLO", inWHLO);
    conEXTDL1.set("EXLDID", ldid);
    conEXTDL1.set("EXDSEQ", 1);
    if (queryEXTDL1.read(conEXTDL1)) {
        pdst = conEXTDL1.get("EXPDST").toString();
      }
    
    mi.outData.put("CONO", container.get("EXCONO").toString());
    mi.outData.put("DIVI", container.get("EXDIVI").toString());
    mi.outData.put("WHLO", container.get("EXWHLO").toString());
    mi.outData.put("BYID", container.get("EXBYID").toString());
    mi.outData.put("BYDS", container.get("EXBYDS").toString());
    mi.outData.put("BYML", container.get("EXBYML").toString());
    mi.outData.put("LDID", container.get("EXLDID").toString());
    mi.outData.put("PUNO", puno == null ? "" : puno.toString());
    mi.outData.put("SUNO", suno == null ? "" : suno.toString());
    mi.outData.put("SUNM", sunm == null ? "" : sunm.toString());
    mi.outData.put("PDST", pdst == null ? "" : pdst.toString());
    mi.write();
  }
}