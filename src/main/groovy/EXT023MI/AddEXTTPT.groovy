/**
 * @Name: AddEXTTPT.EXTTPT
 * @Description: Add record on table EXTTPT
 * @Authors:
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class AddEXTTPT extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO, inPSEQ, inCHNO, inRGDT, inRGTM, inLMDT, inLMTM;
  private String inDIVI;
  private String inWHLO;
  private String inLDID;
  private String inTRLS;
  private String inFPTI;
  private String inEVDT;
  private String inEVTM;
  private String inCOMP;
  private String inESWE;
  private String inCHID;

  public AddEXTTPT(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inEVDT = mi.inData.get("EVDT") == null ? "" : mi.inData.get("EVDT").trim();
    inEVTM = mi.inData.get("EVTM") == null ? "" : mi.inData.get("EVTM").trim();
    inCOMP = mi.inData.get("COMP") == null ? "" : mi.inData.get("COMP").trim();
    inESWE = mi.inData.get("ESWE") == null ? "" : mi.inData.get("ESWE").trim();

    DBAction query = database.table("EXTTPT").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    container.set("EXPSEQ", inPSEQ);
    container.set("EXTRLS", inTRLS);
    container.set("EXFPTI", inFPTI);

    if (query.read(container)) {
      mi.error("Record already exists");
      return;
    }

    if (!isValidInput()) {
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
    inLMTM = entryTime;

    container.set("EXEVDT", !inEVDT.isBlank() ? inEVDT as int : 0);
    container.set("EXEVTM", !inEVTM.isBlank() ? inEVTM as int : 0);
    container.set("EXCOMP", inCOMP);
    container.set("EXESWE", inESWE);
    container.set("EXCHID", inCHID);
    container.set("EXCHNO", inCHNO);
    container.set("EXRGDT", inRGDT);
    container.set("EXRGTM", inRGTM);
    container.set("EXLMDT", inLMDT);
    container.set("EXLMTM", inLMTM);

    query.insert(container);
  }
  
  boolean checkWHLO() {
    DBAction query = database.table("MITWHL").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("MWCONO", inCONO);
    container.set("MWWHLO", inWHLO);
    return query.read(container);
  }

  boolean isValidInput() {
    if (inWHLO != null && !inWHLO.isEmpty()) {
      if (!checkWHLO()) {
        mi.error("WHLO not found in MITWHL");
        return false;
      }
    }
        // Check inEVTM
    if (inEVTM != 0) {
      if (!utility.call("DateUtil", "isTimeValid", inEVTM.toString(), "HHmmss")) {
        mi.error("Invalid TIME input for ${inEVTM}. It must be in the format HHmmss.");
        return false;
      }
    } 
  }
}