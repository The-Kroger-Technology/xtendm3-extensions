/**
 * @Name: UpdEXTLTD.EXTLTD
 * @Description: Update record on table EXTLTD
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     20240408  JHC     Initial Release - Generated from XtendM3 CRUD Generator, additional changes
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UpdEXTLTD extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO;
  private String inDIVI;
  private String inWHLO;
  private String inTLIT;
  private String inLTID;
  private String inTSEQ;
  private String inTSNM;
  private String inRGMN;
  private String inRGMX;
  private String inPLMN;
  private String inPLMX;
  private String inPLUS;
  private String inCOUS;
  private String inCOTS;
  private String inACTV;
  
  private String[] validLTIT = ["M", "O", "C", "X"]; //M=Milk, O=Orange Juice,C=Cyder, X=Expense

  public UpdEXTLTD(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inTLIT = mi.inData.get("TLIT") == null ? "" : mi.inData.get("TLIT").trim();
    inLTID = mi.inData.get("LTID") == null ? "" : mi.inData.get("LTID").trim();
    inTSEQ = mi.inData.get("TSEQ") == null ? "" : mi.inData.get("TSEQ").trim();
    inTSNM = mi.inData.get("TSNM") == null ? "" : mi.inData.get("TSNM").trim();
    inRGMN = mi.inData.get("RGMN") == null ? "" : mi.inData.get("RGMN").trim();
    inRGMX = mi.inData.get("RGMX") == null ? "" : mi.inData.get("RGMX").trim();
    inPLMN = mi.inData.get("PLMN") == null ? "" : mi.inData.get("PLMN").trim();
    inPLMX = mi.inData.get("PLMX") == null ? "" : mi.inData.get("PLMX").trim();
    inPLUS = mi.inData.get("PLUS") == null ? "" : mi.inData.get("PLUS").trim();
    inCOUS = mi.inData.get("COUS") == null ? "" : mi.inData.get("COUS").trim();
    inCOTS = mi.inData.get("COTS") == null ? "" : mi.inData.get("COTS").trim();
    inACTV = mi.inData.get("ACTV") == null ? "" : mi.inData.get("ACTV").trim();

    DBAction query = database.table("EXTLTD").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXTLIT", inTLIT.toUpperCase());
    container.set("EXLTID", inLTID as int);

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
      if (!inTSEQ.isBlank()) {
        lockedResult.set("EXTSEQ", inTSEQ.equals("?") ? 0 : inTSEQ as int);
      }
      if (!inTSNM.isBlank()) {
        lockedResult.set("EXTSNM", inTSNM.equals("?") ? "" : inTSNM);
      }
      if (!inRGMN.isBlank()) {
        lockedResult.set("EXRGMN", inRGMN.equals("?") ? 0 : inRGMN as double);
      }
      if (!inRGMX.isBlank()) {
        lockedResult.set("EXRGMX", inRGMX.equals("?") ? 0 : inRGMX as double);
      }
      if (!inPLMN.isBlank()) {
        lockedResult.set("EXPLMN", inPLMN.equals("?") ? 0 : inPLMN as double);
      }
      if (!inPLMX.isBlank()) {
        lockedResult.set("EXPLMX", inPLMX.equals("?") ? 0 : inPLMX as double);
      }
      if (!inPLUS.isBlank()) {
        lockedResult.set("EXPLUS", inPLUS.equals("?") ? "" : inPLUS.toUpperCase());
      }
      if (!inCOUS.isBlank()) {
        lockedResult.set("EXCOUS", inCOUS.equals("?") ? "" : inCOUS.toUpperCase());
      }
      if (!inCOTS.isBlank()) {
        lockedResult.set("EXCOTS", inCOTS.equals("?") ? 0 : inCOTS as int);
      }
      if (!inACTV.isBlank()) {
        lockedResult.set("EXACTV", inACTV.equals("?") ? "" : inACTV.toUpperCase());
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
    
    //Check inPLUS
    if (!inPLUS.isBlank()) {
      if (!utility.call("CommonUtil", "isValidFlag", inPLUS, "Y", "N")) {
        mi.error("Invalid input value for ${inPLUS}. Must be Y or N.");
        return false;
      }
    }
    
    //Check inCOUS
    if (!inCOUS.isBlank()) {
      if (!utility.call("CommonUtil", "isValidFlag", inCOUS, "Y", "N")) {
        mi.error("Invalid input value for ${inCOUS}. Must be Y or N.");
        return false;
      }
    }
    
    //Check inACTV
    if (!inACTV.isBlank()) {
      if (!utility.call("CommonUtil", "isValidFlag", inACTV, "Y", "N")) {
        mi.error("Invalid input value for ${inACTV}. Must be Y or N.");
        return false;
      }
    }
    
    if (!validLTIT.contains(inTLIT)) {
      mi.error("Invalid item type. Allowed item type are ${Arrays.toString(validLTIT)}");
      return false;
    }
  
    return true;
  }

}