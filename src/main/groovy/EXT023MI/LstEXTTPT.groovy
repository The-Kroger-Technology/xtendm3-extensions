/**
 * @Name: LstEXTTPT.EXTTPT
 * @Description: List record on table EXTTPT
 * @Authors:
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class LstEXTTPT extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  private int inCONO, inPSEQ;
  private String inDIVI;
  private String inWHLO;
  private String inLDID;
  private String inTRLS;
  private String inFPTI;
  
  public LstEXTTPT(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
    this.mi = mi;
    this.utility = utility;
    this.logger = logger;
    this.program = program;
    this.miCaller = miCaller;
    this.database = database;
  }
  public void main() {
    inCONO = mi.in.get("CONO") == null ? program.LDAZD.CONO as Integer : mi.in.get("CONO") as Integer;
    inDIVI = mi.inData.get("DIVI") == null ? "" : mi.inData.get("DIVI").trim();
    inWHLO = mi.inData.get("WHLO") == null ? "" : mi.inData.get("WHLO").trim();
    inLDID = mi.inData.get("LDID") == null ? "" : mi.inData.get("LDID").trim();
    inPSEQ = mi.in.get("PSEQ") == null ? 0 : mi.in.get("PSEQ") as Integer;
    inTRLS = mi.inData.get("TRLS") == null ? "" : mi.inData.get("TRLS").trim();
    inFPTI = mi.inData.get("FPTI") == null ? "" : mi.inData.get("FPTI").trim();
    
    DBAction query = database.table("EXTTPT").index("00").selectAllFields().build();
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
    if (inPSEQ != 0) {
      container.set("EXPSEQ", inPSEQ);
      index++;
    }
    if (!inTRLS.isBlank()) {
      container.set("EXTRLS", inTRLS);
      index++;
    }
    if (!inFPTI.isBlank()) {
      container.set("EXFPTI", inFPTI);
      index++;
    }
    query.readAll(container, index, 10000, {
      DBContainer data ->
      mi.outData.put("CONO", data.get("EXCONO").toString());
      mi.outData.put("DIVI", data.get("EXDIVI").toString());
      mi.outData.put("WHLO", data.get("EXWHLO").toString());
      mi.outData.put("LDID", data.get("EXLDID").toString());
      mi.outData.put("PSEQ", data.get("EXPSEQ").toString());
      mi.outData.put("TRLS", data.get("EXTRLS").toString());
      mi.outData.put("FPTI", data.get("EXFPTI").toString());
      mi.outData.put("EVDT", data.get("EXEVDT").toString());
      mi.outData.put("EVTM", data.get("EXEVTM").toString());
      mi.outData.put("COMP", data.get("EXCOMP").toString());
      mi.outData.put("ESWE", data.get("EXESWE").toString());
      mi.write();
    });
  }
}