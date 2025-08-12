/**
 * @Name: LstEXTBCT.EXTBCT
 * @Description: List record on table EXTBCT
 * @Authors: Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class LstEXTBCT extends ExtendM3Transaction {
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
  final int MAX_RECORDS = mi.getMaxRecords() == 0 ? 10000 : mi.getMaxRecords();
  
  public LstEXTBCT(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    //inBYID = mi.inData.get("BYID") == null ? "" : mi.inData.get("BYID").trim();
    
    DBAction query = database.table("EXTBCT").index("00").selectAllFields().build();
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
/*    if (!inBYID.isBlank()) {
      container.set("EXBYID", inBYID);
      index++;
    }*/
    query.readAll(container, index, MAX_RECORDS, {
      DBContainer data ->
      puno = "";
      suno = "";
      sunm = "";
      pdst = "";
      ldid = (data.get("EXLDID") ?: "").toString().trim();
      if(!ldid.isBlank() && ldid != "0") {
        Map<String, String> params = [ "SQRY":"CONO:${inCONO} AND WHLO:'${inWHLO}' AND UCA1:'${ldid}' AND ORTY:'${defORTY}' AND PUSL:${pusl}".toString() ]; // toString is needed to convert from gstring to string
        logger.info("params  = ${params}");
        Closure<?> callback = {
          Map<String, String> response ->
            logger.info("Response  = ${response}");
            if(response.PUNO != null) {
              puno = response.PUNO;
              suno = response.SUNO;
              sunm = response.SUNM;
            }
        }
      
        miCaller.setListMaxRecords(1);
        miCaller.call("PPS200MI","SearchLine", params, callback);
      } 
      
      DBAction queryEXTDL1 = database.table("EXTDL1").index("00").selection("EXPDST").build();
      DBContainer conEXTDL1 = queryEXTDL1.getContainer();
      conEXTDL1.set("EXCONO", data.get("EXCONO") as int);
      conEXTDL1.set("EXDIVI", data.get("EXDIVI").toString());
      conEXTDL1.set("EXWHLO", data.get("EXWHLO").toString());
      conEXTDL1.set("EXLDID", ldid);
      conEXTDL1.set("EXDSEQ", 1);
      if (queryEXTDL1.read(conEXTDL1)) {
        pdst = conEXTDL1.get("EXPDST").toString();
      }
    
      mi.outData.put("CONO", data.get("EXCONO").toString());
      mi.outData.put("DIVI", data.get("EXDIVI").toString());
      mi.outData.put("WHLO", data.get("EXWHLO").toString());
      mi.outData.put("BYID", data.get("EXBYID").toString());
      mi.outData.put("BYDS", data.get("EXBYDS").toString());
      mi.outData.put("BYML", data.get("EXBYML").toString());
      mi.outData.put("LDID", data.get("EXLDID").toString());
      mi.outData.put("PUNO", puno.toString());
      mi.outData.put("SUNO", suno.toString());
      mi.outData.put("SUNM", sunm.toString());
      mi.outData.put("PDST", pdst.toString());
      mi.write();
    });
  }
}