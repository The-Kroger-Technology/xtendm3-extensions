/**
 * @Name: AddEXTTPP.EXTTPP
 * @Description: Add record on table EXTTPP
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddEXTTPP extends ExtendM3Transaction {
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
  private String inBGWT;
  private String inEVTM;
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

  public AddEXTTPP(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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

    container.set("EXEVDT", inEVDT);
    container.set("EXEVTM", inEVTM.isBlank() ? 0 : inEVTM as int);
    container.set("EXDESC", inDESC);
    container.set("EXBRMJ", inBRMJ);
    container.set("EXBRMN", inBRMN);
    container.set("EXNOTI", inNOTI);
    container.set("EXBGWT", inBGWT);
    container.set("EXERMJ", inERMJ);
    container.set("EXERMN", inERMN);
    container.set("EXENDW", inENDW.isBlank() ? 0.0 : inENDW as double);
    container.set("EXFINW", inFINW.isBlank() ? 0.0 : inFINW as double);
    container.set("EXGALS", inGALS);
    container.set("EXPTEM", inPTEM);
    container.set("EXHTEM", inHTEM);
    container.set("EXLSTM", inLSTM);
    container.set("EXULTM", inULTM);
    container.set("EXRMET", inRMET);
    container.set("EXNOTE", inNOTE);
    container.set("EXTKNO", inTKNO);
    container.set("EXTRUM", inTRUM);
    container.set("EXTMKG", inTMKG);
    container.set("EXTMKT", inTMKT);
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
    
    // Check inEVDT
    if (inEVDT != 0) {
      if (!utility.call("DateUtil", "isDateValid", inEVDT.toString(), "yyyyMMdd")) {
        mi.error("Invalid DATE input for ${inEVDT}. It must be in the format yyyyMMdd.");
        return false;
      }
    }
    
    // Check inEVTM
    if (!inEVTM.isBlank()) {
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