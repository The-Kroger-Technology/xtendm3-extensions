/**
 * @Name: UpdEXTPLP.EXTPLP
 * @Description: Update record on table EXTPLP
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UpdEXTPLP extends ExtendM3Transaction {
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

  public UpdEXTPLP(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    container.set("EXWHLO", inWHLO);
    container.set("EXITNO", inITNO);
    container.set("EXBDRN", inBDRN);
    container.set("EXYEAR", inYEAR as int);
    container.set("EXWENU", inWENU as int);
    container.set("EXRETY", inRETY);

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
      if (!inDIVI.isBlank()) {
        lockedResult.set("EXDIVI", inDIVI.equals("?") ? "" : inDIVI);
      }
      if (!inBFPR.isBlank()) {
        lockedResult.set("EXBFPR", inBFPR.equals("?") ? 0 : inBFPR as double);
      }
      if (!inPRPR.isBlank()) {
        lockedResult.set("EXPRPR", inPRPR.equals("?") ? 0 : inPRPR as double);
      }
      if (!inSNPR.isBlank()) {
        lockedResult.set("EXSNPR", inSNPR.equals("?") ? 0 : inSNPR as double);
      }
      if (!inTSPR.isBlank()) {
        lockedResult.set("EXTSPR", inTSPR.equals("?") ? 0 : inTSPR as double);
      }
      if (!inLDPC.isBlank()) {
        lockedResult.set("EXLDPC", inLDPC.equals("?") ? 0 : inLDPC as int);
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
  
    // Check ITNO
    if (inITNO != null && !inITNO.isEmpty()) {
      if (!checkITNO()) {
        mi.error("ITNO not found in MITMAS");
        return false;
      }
    }
  
    // Check RETY
    if (inRETY != null && !inRETY.isEmpty()) {
      if (!checkRETY()) {
        mi.error("RETY value is invalid");
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
  
  boolean checkITNO() {
    DBAction query = database.table("MITMAS").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("MMCONO", inCONO);
    container.set("MMITNO", inITNO);
    return query.read(container);
  }
  
  boolean checkRETY() {
    String value = inRETY.toUpperCase();
    return value.equals("C") || value.equals("M");
  }
}