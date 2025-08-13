/**
 * @Name: UpdEXTROT.EXTROT
 * @Description: Update record on table EXTROT
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UpdEXTROT extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO;
  private String inDIVI;
  private String inWHLO;
  private String inRNBR;
  private String inRHID;
  private String inROHN;
  private String inRODE;
  private String inHPRT;
  private String inSTAT;
  private String inDBFP;

  public UpdEXTROT(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inRNBR = mi.inData.get("RNBR") == null ? "" : mi.inData.get("RNBR").trim();
    inRHID = mi.inData.get("RHID") == null ? "" : mi.inData.get("RHID").trim();
    inROHN = mi.inData.get("ROHN") == null ? "" : mi.inData.get("ROHN").trim();
    inRODE = mi.inData.get("RODE") == null ? "" : mi.inData.get("RODE").trim();
    inHPRT = mi.inData.get("HPRT") == null ? "" : mi.inData.get("HPRT").trim();
    inSTAT = mi.inData.get("STAT") == null ? "" : mi.inData.get("STAT").trim();
    inDBFP = mi.inData.get("DBFP") == null ? "" : mi.inData.get("DBFP").trim();

    DBAction query = database.table("EXTROT").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXRNBR", inRNBR);
    container.set("EXRHID", inRHID);

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
      if (!inROHN.isBlank()) {
        lockedResult.set("EXROHN", inROHN.equals("?") ? "" : inROHN);
      }
      if (!inRODE.isBlank()) {
        lockedResult.set("EXRODE", inRODE.equals("?") ? "" : inRODE);
      }
      if (!inHPRT.isBlank()) {
        lockedResult.set("EXHPRT", inHPRT.equals("?") ? "" : inHPRT);
      }
      if (!inSTAT.isBlank()) {
        lockedResult.set("EXSTAT", inSTAT.equals("?") ? "" : inSTAT);
      }
      if (!inDBFP.isBlank()) {
        lockedResult.set("EXDBFP", inDBFP.equals("?") ? 0 : inDBFP as double);
      }
      lockedResult.set("EXCHNO", lockedResult.getInt("EXCHNO") + 1);
      lockedResult.set("EXLMDT", changedDate);
      lockedResult.set("EXLMTM", changedTime);
      lockedResult.set("EXCHID", program.getUser());
      lockedResult.update();
    });
  }
  
  boolean checkWHLO() {
    DBAction query = database.table("MITWHL").index("00").build();
    DBContainer container = query.getContainer();
    container.set("MWCONO", inCONO);
    container.set("MWWHLO", inWHLO);
  
    return query.read(container);
  }


  boolean isValidInput() {
    // Check WHLO
    if (!inWHLO.isBlank()) {
      if (!checkWHLO()) {
        mi.error("Warehouse " + inWHLO + " does not exist");
        return false;
      }
    }
  
    // Check RHID (Yes/No)
    if (inRHID != null && !inRHID.trim().isEmpty()) {
      String val = inRHID.toUpperCase();
      if (!val.equals("Y") && !val.equals("N")) {
        mi.error("RHID value is invalid. Must be Y or N.");
        return false;
      }
    }
  
    // Check STAT (A, S, or X)
    if (inSTAT != null && !inSTAT.trim().isEmpty()) {
      String val = inSTAT.toUpperCase();
      if (!val.equals("A") && !val.equals("S") && !val.equals("X")) {
        mi.error("STAT value is invalid. Must be A, S, or X.");
        return false;
      }
    }
  
    return true;
  }

}