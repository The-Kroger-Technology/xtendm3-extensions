/**
 * @Name: AddEXTTNK.EXTTNK
 * @Description: Add record on table EXTTNK
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddEXTTNK extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO, inWTDT, inWEDT, inPWTD, inPWDT, inCHNO, inRGDT, inRGTM, inLMDT;
  private String inDIVI;
  private String inWHLO;
  private String inLDID;
  private String inTKID;
  private String inTPOS;
  private String inLTWL;
  private String inPLWL;
  private String inPMID;
  private String inMTNO;
  private String inTEMP;
  private String inTDWT;
  private String inTCWT;
  private String inCHID;
  private String inWTTM;
  private String inWETM;
  private String inPWTT;
  private String inPWTM;
  private String inRGST;
  private String inRGYE;
  private String inRGMO;
  

  public AddEXTTNK(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inTPOS = mi.inData.get("TPOS") == null ? "" : mi.inData.get("TPOS").trim();
    inLTWL = mi.inData.get("LTWL") == null ? "" : mi.inData.get("LTWL").trim();
    inWTDT = mi.in.get("WTDT") == null ? 0 : mi.in.get("WTDT") as Integer;
    inWTTM = mi.inData.get("WTTM") == null ? "0" : mi.inData.get("WTTM").trim();
    inWEDT = mi.in.get("WEDT") == null ? 0 : mi.in.get("WEDT") as Integer;
    inWETM = mi.inData.get("WETM") == null ? "0" : mi.inData.get("WETM").trim();
    inPLWL = mi.inData.get("PLWL") == null ? "" : mi.inData.get("PLWL").trim();
    inPWTD = mi.in.get("PWTD") == null ? 0 : mi.in.get("PWTD") as Integer;
    inPWTT = mi.inData.get("PWTT") == null ? "0" : mi.inData.get("PWTT").trim();
    inPWDT = mi.in.get("PWDT") == null ? 0 : mi.in.get("PWDT") as Integer;
    inPWTM = mi.inData.get("PWTM") == null ? "0" : mi.inData.get("PWTM").trim();
    inPMID = mi.inData.get("PMID") == null ? "" : mi.inData.get("PMID").trim();
    inMTNO = mi.inData.get("MTNO") == null ? "" : mi.inData.get("MTNO").trim();
    inTEMP = mi.inData.get("TEMP") == null ? "" : mi.inData.get("TEMP").trim();
    inTDWT = mi.inData.get("TDWT") == null ? "0.0" : mi.inData.get("TDWT").trim();
    inTCWT = mi.inData.get("TCWT") == null ? "0.0" : mi.inData.get("TCWT").trim();
    inRGST = mi.inData.get("RGST") == null ? "" : mi.inData.get("RGST").trim();
    inRGYE = mi.inData.get("RGYE") == null ? "0" : mi.inData.get("RGYE").trim();
    inRGMO = mi.inData.get("RGMO") == null ? "0" : mi.inData.get("RGMO").trim();

    if (!isValidInput()) {
      return;
    }
    
    DBAction query = database.table("EXTTNK").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    container.set("EXTKID", inTKID);

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

    container.set("EXTPOS", inTPOS);
    container.set("EXLTWL", inLTWL);
    container.set("EXWTDT", inWTDT);
    container.set("EXWTTM", inWTTM.isBlank() ? 0 : inWTTM as int);
    container.set("EXWEDT", inWEDT);
    container.set("EXWETM", inWETM.isBlank() ? 0 : inWETM as int);
    container.set("EXPLWL", inPLWL);
    container.set("EXPWTD", inPWTD);
    container.set("EXPWTT", inPWTT.isBlank() ? 0 : inPWTT as int);
    container.set("EXPWDT", inPWDT);
    container.set("EXPWTM", inPWTM.isBlank() ? 0 : inPWTM as int);
    container.set("EXPMID", inPMID);
    container.set("EXMTNO", inMTNO);
    container.set("EXTEMP", inTEMP);
    container.set("EXTDWT", inTDWT.isBlank() ? 0.0 : inTDWT as double);
    container.set("EXTCWT", inTCWT.isBlank() ? 0.0 : inTCWT as double);
    container.set("EXRGST", inRGST);
    container.set("EXRGYE", inRGYE.isBlank() ? 0 : inRGYE as int);
    container.set("EXRGMO", inRGMO.isBlank() ? 0 : inRGMO as int);
    container.set("EXCHID", inCHID);
    container.set("EXCHNO", inCHNO);
    container.set("EXRGDT", inRGDT);
    container.set("EXRGTM", inRGTM);
    container.set("EXLMDT", inLMDT);

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

    // Check inWTDT
    if (inWTDT != 0) {
      if (!utility.call("DateUtil", "isDateValid", inWTDT.toString(), "yyyyMMdd")) {
        mi.error("Invalid DATE input for ${inWTDT}. It must be in the format yyyyMMdd.");
        return false;
      }
    }
    
    // Check inWTTM
    if (!inWTTM.isBlank()) {
      if (!utility.call("DateUtil", "isTimeValid", inWTTM.padLeft(6, '0'), "HHmmss")) {
        mi.error("Invalid TIME input for ${inWTTM}. It must be in the format HHmmss.");
        return false;
      }
    }
    
    // Check inWEDT
    if (inWEDT != 0) {
      if (!utility.call("DateUtil", "isDateValid", inWEDT.toString(), "yyyyMMdd")) {
        mi.error("Invalid DATE input for ${inWEDT}. It must be in the format yyyyMMdd.");
        return false;
      }
    }
    
    // Check inWETM
    if (!inWETM.isBlank()) {
      if (!utility.call("DateUtil", "isTimeValid", inWETM.padLeft(6, '0'), "HHmmss")) {
        mi.error("Invalid TIME input for ${inWETM}. It must be in the format HHmmss.");
        return false;
      }
    }
    
    // Check inPWTD
    if (inPWTD != 0) {
      if (!utility.call("DateUtil", "isDateValid", inPWTD.toString(), "yyyyMMdd")) {
        mi.error("Invalid DATE input for ${inPWTD}. It must be in the format yyyyMMdd.");
        return false;
      }
    }
    
    // Check inPWTT
    if (!inPWTT.isBlank()) {
      if (!utility.call("DateUtil", "isTimeValid", inPWTT.padLeft(6, '0'), "HHmmss")) {
        mi.error("Invalid TIME input for ${inPWTT}. It must be in the format HHmmss.");
        return false;
      }
    }
    
    // Check inPWDT
    if (inPWDT != 0) {
      if (!utility.call("DateUtil", "isDateValid", inPWDT.toString(), "yyyyMMdd")) {
        mi.error("Invalid DATE input for ${inPWDT}. It must be in the format yyyyMMdd.");
        return false;
      }
    }
    
    // Check inPWTM
    if (!inPWTM.isBlank()) {
      if (!utility.call("DateUtil", "isTimeValid", inPWTM.padLeft(6, '0'), "HHmmss")) {
        mi.error("Invalid TIME input for ${inPWTM}. It must be in the format HHmmss.");
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