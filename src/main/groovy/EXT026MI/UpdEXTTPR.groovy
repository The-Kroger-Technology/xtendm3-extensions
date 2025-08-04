/**
 * @Name: UpdEXTTPR.EXTTPR
 * @Description: Update record on table EXTTPR
 * @Authors:
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UpdEXTTPR extends ExtendM3Transaction {
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
  private String inINDN;
  private String inREAS;
  private String inEVDT;
  private String inEVTM;
  private String inPLCL;
  private String inPLID;
  private String inPLNA;
  private String inPDRM;
  private String inPSIR;
  private String inDISP;
  private String inCOMM;

  public UpdEXTTPR(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inLDID = mi.inData.get("LDID") == null ? "" : mi.inData.get("LDID").trim();
    inINDN = mi.inData.get("INDN") == null ? "" : mi.inData.get("INDN").trim();
    inREAS = mi.inData.get("REAS") == null ? "" : mi.inData.get("REAS").trim();
    inEVDT = mi.inData.get("EVDT") == null ? "" : mi.inData.get("EVDT").trim();
    inEVTM = mi.inData.get("EVTM") == null ? "" : mi.inData.get("EVTM").trim();
    inPLCL = mi.inData.get("PLCL") == null ? "" : mi.inData.get("PLCL").trim();
    inPLID = mi.inData.get("PLID") == null ? "" : mi.inData.get("PLID").trim();
    inPLNA = mi.inData.get("PLNA") == null ? "" : mi.inData.get("PLNA").trim();
    inPDRM = mi.inData.get("PDRM") == null ? "" : mi.inData.get("PDRM").trim();
    inPSIR = mi.inData.get("PSIR") == null ? "" : mi.inData.get("PSIR").trim();
    inDISP = mi.inData.get("DISP") == null ? "" : mi.inData.get("DISP").trim();
    inCOMM = mi.inData.get("COMM") == null ? "" : mi.inData.get("COMM").trim();

    DBAction query = database.table("EXTTPR").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    container.set("EXINDN", inINDN as int);
    container.set("EXREAS", inREAS);

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
        lockedResult.set("EXEVDT", inEVDT.equals("?") ? "" : inEVDT);
      }
      if (!inEVTM.isBlank()) {
        lockedResult.set("EXEVTM", inEVTM.equals("?") ? "" : inEVTM);
      }
      if (!inPLCL.isBlank()) {
        lockedResult.set("EXPLCL", inPLCL.equals("?") ? "" : inPLCL);
      }
      if (!inPLID.isBlank()) {
        lockedResult.set("EXPLID", inPLID.equals("?") ? "" : inPLID);
      }
      if (!inPLNA.isBlank()) {
        lockedResult.set("EXPLNA", inPLNA.equals("?") ? "" : inPLNA);
      }
      if (!inPDRM.isBlank()) {
        lockedResult.set("EXPDRM", inPDRM.equals("?") ? "" : inPDRM);
      }
      if (!inPSIR.isBlank()) {
        lockedResult.set("EXPSIR", inPSIR.equals("?") ? "" : inPSIR);
      }
      if (!inDISP.isBlank()) {
        lockedResult.set("EXDISP", inDISP.equals("?") ? "" : inDISP);
      }
      if (!inCOMM.isBlank()) {
        lockedResult.set("EXCOMM", inCOMM.equals("?") ? "" : inCOMM);
      }
      lockedResult.set("EXCHNO", lockedResult.getInt("EXCHNO") + 1);
      lockedResult.set("EXLMDT", changedDate);
      lockedResult.set("EXLMTM", changedTime);
      lockedResult.set("EXCHID", program.getUser());
      lockedResult.update();
    });
  }

  boolean isValidInput() {
    // Check WHLO
    if (inWHLO != null && !inWHLO.isEmpty()) {
      if (!checkWHLO()) {
        mi.error("WHLO not found in MITWHL");
        return false;
      }
    }
  
    // Check EVDT (Event Date)
    if (inEVDT != null && !inEVDT.trim().isEmpty()) {
      if (!checkEVDT()) {
        mi.error("EVDT is invalid");
        return false;
      }
    }
  
    // Check EVTM (Event Time)
    if (inEVTM != null && !inEVTM.trim().isEmpty()) {
      if (!checkEVTM()) {
        mi.error("EVTM is invalid");
        return false;
      }
    }
  
    return true;
  }
  
  boolean checkWHLO() {
    DBAction query = database.table("MITWHL").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("MWCONO", inCONO);
    container.set("MWWHLO", inWHLO);
    return query.read(container);
  }
  
  boolean checkEVDT() {
    return utility.call("DateUtil", "isDateValid", inEVDT, "yyyyMMdd");
  }
  
  boolean checkEVTM() {
    return utility.call("DateUtil", "isTimeValid", inEVTM, "HHmmss");
  }
}