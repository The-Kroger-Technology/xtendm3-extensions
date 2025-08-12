/**
 * @Name: UpdEXTTPP.EXTTPP
 * @Description: Update record on table EXTTPP
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UpdEXTTPP extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO, inPSEQ, inTPLS, inEVDT, inCHNO, inRGDT, inRGTM, inLMDT;
  private String inDIVI;
  private String inWHLO;
  private String inLDID;
  private String inDESC;
  private String inBRMJ;
  private String inBRMN;
  private String inNOTI;
  private String inEVTM;
  private String inBGWT;
  private String inERMJ;
  private String inERMN;
  private String inENDW;
  private String inFINW;
  private String inGALS;
  private String inPTEM;
  private String inHTEM;
  private String inLSTM;
  private String inULTM;
  private String inRMET;
  private String inNOTE;
  private String inTKNO;
  private String inTRUM;
  private String inTMKG;
  private String inTMKT;
  private String inCHID;

  public UpdEXTTPP(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inTPLS = mi.in.get("TPLS") == null ? 0 : mi.in.get("TPLS") as Integer;
    inEVDT = mi.in.get("EVDT") == null ? 0 : mi.in.get("EVDT") as Integer;
    inEVTM = mi.inData.get("EVTM") == null ? "" : mi.inData.get("EVTM").trim();
    inDESC = mi.inData.get("DESC") == null ? "" : mi.inData.get("DESC").trim();
    inBRMJ = mi.inData.get("BRMJ") == null ? "" : mi.inData.get("BRMJ").trim();
    inBRMN = mi.inData.get("BRMN") == null ? "" : mi.inData.get("BRMN").trim();
    inNOTI = mi.inData.get("NOTI") == null ? "" : mi.inData.get("NOTI").trim();
    inBGWT = mi.inData.get("BGWT") == null ? "" : mi.inData.get("BGWT").trim();
    inERMJ = mi.inData.get("ERMJ") == null ? "" : mi.inData.get("ERMJ").trim();
    inERMN = mi.inData.get("ERMN") == null ? "" : mi.inData.get("ERMN").trim();
    inENDW = mi.inData.get("ENDW") == null ? "0.0" : mi.inData.get("ENDW").trim();
    inFINW = mi.inData.get("FINW") == null ? "0.0" : mi.inData.get("FINW").trim();
    inGALS = mi.inData.get("GALS") == null ? "" : mi.inData.get("GALS").trim();
    inPTEM = mi.inData.get("PTEM") == null ? "" : mi.inData.get("PTEM").trim();
    inHTEM = mi.inData.get("HTEM") == null ? "" : mi.inData.get("HTEM").trim();
    inLSTM = mi.inData.get("LSTM") == null ? "" : mi.inData.get("LSTM").trim();
    inULTM = mi.inData.get("ULTM") == null ? "" : mi.inData.get("ULTM").trim();
    inRMET = mi.inData.get("RMET") == null ? "" : mi.inData.get("RMET").trim();
    inNOTE = mi.inData.get("NOTE") == null ? "" : mi.inData.get("NOTE").trim();
    inTKNO = mi.inData.get("TKNO") == null ? "" : mi.inData.get("TKNO").trim();
    inTRUM = mi.inData.get("TRUM") == null ? "" : mi.inData.get("TRUM").trim();
    inTMKG = mi.inData.get("TMKG") == null ? "" : mi.inData.get("TMKG").trim();
    inTMKT = mi.inData.get("TMKT") == null ? "" : mi.inData.get("TMKT").trim();

    if (!isValidInput()) {
      return;
    }

    DBAction query = database.table("EXTTPP").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    container.set("EXPSEQ", inPSEQ);
    container.set("EXTPLS", inTPLS);

    if (!query.read(container)) {
      mi.error("Record does not exists");
      return;
    }
    
    LocalDateTime dateTime = LocalDateTime.now();
    int changedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toInteger();
    int changedTime = dateTime.format(DateTimeFormatter.ofPattern("HHmmss")).toInteger();

    query.readLock(container, {
      LockedResult lockedResult ->
      if (inEVDT != 0) {
        lockedResult.set("EXEVDT", inEVDT.equals("?") ? 0 : inEVDT);
      }
      if (!inEVTM.isBlank()) {
        lockedResult.set("EXEVTM", inEVTM.equals("?") ? 0 : inEVTM as int);
      }
      if (!inDESC.isBlank()) {
        lockedResult.set("EXDESC", inDESC.equals("?") ? "" : inDESC);
      }
      if (!inBRMJ.isBlank()) {
        lockedResult.set("EXBRMJ", inBRMJ.equals("?") ? "" : inBRMJ);
      }
      if (!inBRMN.isBlank()) {
        lockedResult.set("EXBRMN", inBRMN.equals("?") ? "" : inBRMN);
      }
      if (!inNOTI.isBlank()) {
        lockedResult.set("EXNOTI", inNOTI.equals("?") ? "" : inNOTI);
      }
      if (!inBGWT.isBlank()) {
        lockedResult.set("EXBGWT", inBGWT.equals("?") ? "" : inBGWT);
      }
      if (!inERMJ.isBlank()) {
        lockedResult.set("EXERMJ", inERMJ.equals("?") ? "" : inERMJ);
      }
      if (!inERMN.isBlank()) {
        lockedResult.set("EXERMN", inERMN.equals("?") ? "" : inERMN);
      }
      if (!inENDW.isBlank()) {
        lockedResult.set("EXENDW", inENDW.equals("?") ? 0.0 : inENDW as double);
      }
      if (!inFINW.isBlank()) {
        lockedResult.set("EXFINW", inFINW.equals("?") ? 0.0 : inFINW as double);
      }
      if (!inGALS.isBlank()) {
        lockedResult.set("EXGALS", inGALS.equals("?") ? "" : inGALS);
      }
      if (!inPTEM.isBlank()) {
        lockedResult.set("EXPTEM", inPTEM.equals("?") ? "" : inPTEM);
      }
      if (!inHTEM.isBlank()) {
        lockedResult.set("EXHTEM", inHTEM.equals("?") ? "" : inHTEM);
      }
      if (!inLSTM.isBlank()) {
        lockedResult.set("EXLSTM", inLSTM.equals("?") ? "" : inLSTM);
      }
      if (!inULTM.isBlank()) {
        lockedResult.set("EXULTM", inULTM.equals("?") ? "" : inULTM);
      }
      if (!inRMET.isBlank()) {
        lockedResult.set("EXRMET", inRMET.equals("?") ? "" : inRMET);
      }
      if (!inNOTE.isBlank()) {
        lockedResult.set("EXNOTE", inNOTE.equals("?") ? "" : inNOTE);
      }
      if (!inTKNO.isBlank()) {
        lockedResult.set("EXTKNO", inTKNO.equals("?") ? "" : inTKNO);
      }
      if (!inTRUM.isBlank()) {
        lockedResult.set("EXTRUM", inTRUM.equals("?") ? "" : inTRUM);
      }
      if (!inTMKG.isBlank()) {
        lockedResult.set("EXTMKG", inTMKG.equals("?") ? "" : inTMKG);
      }
      if (!inTMKT.isBlank()) {
        lockedResult.set("EXTMKT", inTMKT.equals("?") ? "" : inTMKT);
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
    
    // Check inEVDT
    if (inEVDT != 0) {
      if (!utility.call("DateUtil", "isDateValid", inEVDT.toString(), "yyyyMMdd")) {
        mi.error("Invalid DATE input for ${inEVDT}. It must be in the format yyyyMMdd.");
        return false;
      }
    }
    
    // Check inEVTM
    if (!inEVTM.isBlank() && !inEVTM.equals("?")) {
      if (!utility.call("CommonUtil", "isValidTime", inEVTM.padLeft(6, '0'))) {
        mi.error("Invalid TIME input for ${inEVTM}. It must be in the format HHmmss.");
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