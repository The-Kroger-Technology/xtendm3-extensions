/**
 * @Name: AddEXTTKS.EXTTKS
 * @Description: Add record on table EXTTKS
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddEXTTKS extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO, inCHNO, inRGDT, inRGTM, inLMDT;
  private String inDIVI;
  private String inWHLO;
  private String inLDID;
  private String inTKID;
  private String inSNUM;
  private String inSLOC;
  private String inSDES;
  private String inSSTA;
  private String inRDES;
  private String inPRSL;
  private String inPRSD;
  private String inPRSS;
  private String inPRRD;
  private String inPTSV;
  private String inCHID;

  public AddEXTTKS(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inSNUM = mi.inData.get("SNUM") == null ? "" : mi.inData.get("SNUM").trim();
    inSLOC = mi.inData.get("SLOC") == null ? "" : mi.inData.get("SLOC").trim();
    inSDES = mi.inData.get("SDES") == null ? "" : mi.inData.get("SDES").trim();
    inSSTA = mi.inData.get("SSTA") == null ? "" : mi.inData.get("SSTA").trim();
    inRDES = mi.inData.get("RDES") == null ? "" : mi.inData.get("RDES").trim();
    inPRSL = mi.inData.get("PRSL") == null ? "" : mi.inData.get("PRSL").trim();
    inPRSD = mi.inData.get("PRSD") == null ? "" : mi.inData.get("PRSD").trim();
    inPRSS = mi.inData.get("PRSS") == null ? "" : mi.inData.get("PRSS").trim();
    inPRRD = mi.inData.get("PRRD") == null ? "" : mi.inData.get("PRRD").trim();
    inPTSV = mi.inData.get("PTSV") == null ? "" : mi.inData.get("PTSV") as char;

    if (!isValidInput()) {
      return;
    }
    
    DBAction query = database.table("EXTTKS").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    container.set("EXTKID", inTKID);
    container.set("EXSNUM", inSNUM);

    if (query.read(container)) {
      mi.error("Record already exists");
      return;
    }

    LocalDateTime dateTime = LocalDateTime.now();
    int entryDate = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toInteger();
    int entryTime = dateTime.format(DateTimeFormatter.ofPattern("HHmmss")).toInteger();
    inCHID = program.getUser();
    inCHNO = 1;
    inRGDT = entryDate;
    inRGTM = entryTime;
    inLMDT = entryDate;

    container.set("EXSLOC", inSLOC);
    container.set("EXSDES", inSDES);
    container.set("EXSSTA", inSSTA);
    container.set("EXRDES", inRDES);
    container.set("EXPRSL", inPRSL);
    container.set("EXPRSD", inPRSD);
    container.set("EXPRSS", inPRSS);
    container.set("EXPRRD", inPRRD);
    container.set("EXPTSV", inPTSV.toUpperCase());
    container.set("EXCHID", inCHID);
    container.set("EXCHNO", inCHNO);
    container.set("EXRGDT", inRGDT);
    container.set("EXRGTM", inRGTM);
    container.set("EXLMDT", inLMDT);
    container.set("EXLMTM", entryTime);

    query.insert(container);
  }

  boolean isValidInput() {
    // Check WHLO
    if (!inWHLO.isBlank()) {
      if (!this.checkWHLO()) {
        mi.error("Warehouse ${inWHLO} does not exist");
        return false;
      }
    }
    
    //Check inPTSV
    if (!inPTSV.isBlank()) {
      if (!utility.call("CommonUtil", "isValidFlag", inPTSV, "Y", "N")) {
        mi.error("Invalid input value for ${inPTSV}. Should be Y or N.");
        return false;
      }
    }

    return true;
  }
  
  /**
  * Validate WHLO from MITWHL
  */
  boolean checkWHLO() {
    DBAction MITWHL_query = database.table("MITWHL").index("00").build();
    DBContainer MITWHL = MITWHL_query.getContainer();
    MITWHL.set("MWCONO", inCONO);
    MITWHL.set("MWWHLO", inWHLO);
  
    if (!MITWHL_query.read(MITWHL)) {
      return false;
    } else {
      return true;
    }
  }

}