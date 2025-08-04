/**
 * @Name: UpdEXTTPT.EXTTPT
 * @Description: Update record on table EXTTPT
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

public class UpdEXTTPT extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO;
  private String inDIVI;
  private String inWHLO;
  private String inLDID;
  private String inPSEQ;
  private String inTRLS;
  private String inFPTI;
  private String inEVDT;
  private String inEVTM;
  private String inCOMP;
  private String inESWE;

  public UpdEXTTPT(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inPSEQ = mi.inData.get("PSEQ") == null ? "" : mi.inData.get("PSEQ").trim();
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
    container.set("EXPSEQ", inPSEQ as int);
    container.set("EXTRLS", inTRLS);
    container.set("EXFPTI", inFPTI);

    if (!query.read(container)) {
      mi.error("Record does not exists");
      return;
    }

    if (!isValidInput()) {
      return;
    }

    LocalDateTime dateTime = LocalDateTime.now();
    int changedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toInteger();
    int changedTime = dateTime.format(DateTimeFormatter.ofPattern("HHmmss")).toInteger();

    query.readLock(container, {
      LockedResult lockedResult ->
      if (!inEVDT.isBlank()) {
        lockedResult.set("EXEVDT", inEVDT.equals("?") ? 0 : inEVDT as int);
      }
      if (!inEVTM.isBlank()) {
        lockedResult.set("EXEVTM", inEVTM.equals("?") ? 0 : inEVTM as int);
      }
      if (!inCOMP.isBlank()) {
        lockedResult.set("EXCOMP", inCOMP.equals("?") ? "" : inCOMP);
      }
      if (!inESWE.isBlank()) {
        lockedResult.set("EXESWE", inESWE.equals("?") ? "" : inESWE);
      }
      lockedResult.set("EXCHNO", lockedResult.getInt("EXCHNO") + 1);
      lockedResult.set("EXLMDT", changedDate);
      lockedResult.set("EXLMTM", changedTime);
      lockedResult.set("EXCHID", program.getUser());
      lockedResult.update();
    });
  }
  
  boolean isValidInput() {
    if (inWHLO != null && !inWHLO.isEmpty()) {
      if (!checkWHLO()) {
        mi.error("WHLO not found in MITWHL");
        return false;
      }
    }
    
    if (inEVTM != 0) {
      if (!utility.call("DateUtil", "isTimeValid", inEVTM.toString(), "HHmmss")) {
        mi.error("Invalid TIME input for ${inEVTM}. It must be in the format HHmmss.");
        return false;
      }
    } 
  }
  
  boolean checkWHLO() {
    DBAction query = database.table("MITWHL").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("MWCONO", inCONO);
    container.set("MWWHLO", inWHLO);
    return query.read(container);
  }

}