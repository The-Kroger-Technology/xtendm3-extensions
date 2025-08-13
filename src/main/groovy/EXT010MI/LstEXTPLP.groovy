/**
 * @Name: LstEXTPLP.EXTPLP
 * @Description: List record on table EXTPLP
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class LstEXTPLP extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  private int inCONO;
  private String inDIVI;
  private String inWHLO;
  private String inITNO;
  private String inBDRN;
  private String inYEAR;
  private String inWENU;
  private String inRETY;
  private String inBFPR;
  private String inPRPR;
  private String inSNPR;
  private String inTSPR;
  private String inLDPC;
  public LstEXTPLP(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inITNO = mi.inData.get("ITNO") == null ? "" : mi.inData.get("ITNO").trim();
    inBDRN = mi.inData.get("BDRN") == null ? "" : mi.inData.get("BDRN").trim();
    inYEAR = mi.inData.get("YEAR") == null ? "" : mi.inData.get("YEAR").trim();
    inWENU = mi.inData.get("WENU") == null ? "" : mi.inData.get("WENU").trim();
    inRETY = mi.inData.get("RETY") == null ? "" : mi.inData.get("RETY").trim();
    inBFPR = mi.inData.get("BFPR") == null ? "" : mi.inData.get("BFPR").trim();
    inPRPR = mi.inData.get("PRPR") == null ? "" : mi.inData.get("PRPR").trim();
    inSNPR = mi.inData.get("SNPR") == null ? "" : mi.inData.get("SNPR").trim();
    inTSPR = mi.inData.get("TSPR") == null ? "" : mi.inData.get("TSPR").trim();
    inLDPC = mi.inData.get("LDPC") == null ? "" : mi.inData.get("LDPC").trim();
    DBAction query = database.table("EXTPLP").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    int index = 1;
    if (!inWHLO.isBlank()) {
      container.set("EXWHLO", inWHLO);
      index++;
    }
    if (!inITNO.isBlank()) {
      container.set("EXITNO", inITNO);
      index++;
    }
    if (!inBDRN.isBlank()) {
      container.set("EXBDRN", inBDRN);
      index++;
    }
    if (!inYEAR.isBlank()) {
      container.set("EXYEAR", inYEAR as int);
      index++;
    }
    if (!inWENU.isBlank()) {
      container.set("EXWENU", inWENU as int);
      index++;
    }
    if (!inRETY.isBlank()) {
      container.set("EXRETY", inRETY);
      index++;
    }
    query.readAll(container, index, 10000, {
      DBContainer data ->
      mi.outData.put("CONO", data.get("EXCONO").toString());
      mi.outData.put("DIVI", data.get("EXDIVI").toString());
      mi.outData.put("WHLO", data.get("EXWHLO").toString());
      mi.outData.put("ITNO", data.get("EXITNO").toString());
      mi.outData.put("BDRN", data.get("EXBDRN").toString());
      mi.outData.put("YEAR", data.get("EXYEAR").toString());
      mi.outData.put("WENU", data.get("EXWENU").toString());
      mi.outData.put("RETY", data.get("EXRETY").toString());
      mi.outData.put("BFPR", data.get("EXBFPR").toString());
      mi.outData.put("PRPR", data.get("EXPRPR").toString());
      mi.outData.put("SNPR", data.get("EXSNPR").toString());
      mi.outData.put("TSPR", data.get("EXTSPR").toString());
      mi.outData.put("LDPC", data.get("EXLDPC").toString());
      mi.write();
    });
  }
}