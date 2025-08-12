/**
 * @Name: UpdEXTTNK.EXTTNK
 * @Description: Update record on table EXTTNK
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UpdEXTTNK extends ExtendM3Transaction {
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
  private String inRGST;
  private String inCHID;
  private String inWTTM;
  private String inWETM;
  private String inPWTT;
  private String inPWTM;
  private String inRGYE;
  private String inRGMO;

  public UpdEXTTNK(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inWTTM = mi.inData.get("WTTM") == null ? "0" :mi.inData.get("WTTM").trim();
    inWEDT = mi.in.get("WEDT") == null ? 0 : mi.in.get("WEDT") as Integer;
    inWETM = mi.in.get("WETM") == null ? "0" : mi.inData.get("WETM").trim();
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
    inRGYE = mi.inData.get("RGYE") == null ? "" : mi.inData.get("RGYE").trim();
    inRGMO = mi.inData.get("RGMO") == null ? "" : mi.inData.get("RGMO").trim();
    
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

    if (!query.read(container)) {
      mi.error("Record does not exists");
      return;
    }
    
    LocalDateTime dateTime = LocalDateTime.now();
    int changedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toInteger();
    int changedTime = dateTime.format(DateTimeFormatter.ofPattern("HHmmss")).toInteger();

    query.readLock(container, {
      LockedResult lockedResult ->
      if (!inTPOS.isBlank()) {
        lockedResult.set("EXTPOS", inTPOS.equals("?") ? "" : inTPOS);
      }
      if (!inLTWL.isBlank()) {
        lockedResult.set("EXLTWL", inLTWL.equals("?") ? "" : inLTWL);
      }
      if (inWTDT != 0) {
        lockedResult.set("EXWTDT", inWTDT.equals("?") ? 0 : inWTDT);
      }
      if (!inWTTM.isBlank()) {
        lockedResult.set("EXWTTM", inWTTM.equals("?") ? 0 : inWTTM as int);
      }
      if (inWEDT != 0) {
        lockedResult.set("EXWEDT", inWEDT.equals("?") ? 0 : inWEDT);
      }
      if (!inWETM.isBlank()) {
        lockedResult.set("EXWETM", inWETM.equals("?") ? 0 : inWETM as int);
      }
      if (inPLWL.isBlank()) {
        lockedResult.set("EXPLWL", inPLWL.equals("?") ? "" : inPLWL);
      }
      if (inPWTD != 0) {
        lockedResult.set("EXPWTD", inPWTD.equals("?") ? 0 : inPWTD);
      }
      if (!inPWTT.isBlank()) {
        lockedResult.set("EXPWTT", inPWTT.equals("?") ? 0 : inPWTT as int);
      }
      if (inPWDT != 0) {
        lockedResult.set("EXPWDT", inPWDT.equals("?") ? 0 : inPWDT);
      }
      if (!inPWTM.isBlank()) {
        lockedResult.set("EXPWTM", inPWTM.equals("?") ? 0 : inPWTM as int);
      }
      if (!inPMID.isBlank()) {
        lockedResult.set("EXPMID", inPMID.equals("?") ? "" : inPMID);
      }
      if (!inMTNO.isBlank()) {
        lockedResult.set("EXMTNO", inMTNO.equals("?") ? "" : inMTNO);
      }
      if (!inTEMP.isBlank()) {
        lockedResult.set("EXTEMP", inTEMP.equals("?") ? "" : inTEMP);
      }
      if (!inTDWT.isBlank()) {
        lockedResult.set("EXTDWT", inTDWT.equals("?") ? 0.0 : inTDWT as double);
      }
      if (!inTCWT.isBlank()) {
        lockedResult.set("EXTCWT", inTCWT.equals("?") ? 0.0 : inTCWT as double);
      }
      if (!inRGST.isBlank()) {
        lockedResult.set("EXRGST", inRGST.equals("?") ? "" : inRGST);
      }
      if (!inRGYE.isBlank()) {
        lockedResult.set("EXRGYE", inRGYE.equals("?") ? 0 : inRGYE as int);
      }
      if (!inRGMO.isBlank()) {
        lockedResult.set("EXRGMO", inRGMO.equals("?") ? 0 : inRGMO as int);
      }
      lockedResult.set("EXCHNO", lockedResult.getInt("EXCHNO") + 1);
      lockedResult.set("EXLMDT", changedDate);
      lockedResult.set("EXCHID", program.getUser());
      lockedResult.update();
    });
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
    if (!inWTTM.isBlank() && !inWTTM.equals("?")) {
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
    if (!inWETM.isBlank() && !inWETM.equals("?")) {
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
    if (!inPWTT.isBlank() && !inPWTT.equals("?")) {
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
    if (!inPWTM.isBlank() && !inPWTM.equals("?")) {
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