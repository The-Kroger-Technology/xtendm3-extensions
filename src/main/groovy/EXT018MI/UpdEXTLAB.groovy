/**
 * @Name: UpdEXTLAB.EXTLAB
 * @Description: Update record on table EXTLAB
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UpdEXTLAB extends ExtendM3Transaction {
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
  private String inLRID;
  private String inTKID;
  private String inLRIT;
  private String inLRNM;
  private String inTSEQ;
  private String inCPID;
  private String inLRMN;
  private String inLRMX;
  private String inLABR;
  private String inLBRS;
  private String inLBMN;
  private String inLCFM;
  private String inCHID;
  private String[] validItemType = ["M"]; // M=MILK

  public UpdEXTLAB(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inLRID = mi.inData.get("LRID") == null ? "" : mi.inData.get("LRID").trim();
    inLRIT = mi.inData.get("LRIT") == null ? "" : mi.inData.get("LRIT").trim();
    inLRNM = mi.inData.get("LRNM") == null ? "" : mi.inData.get("LRNM").trim();
    inTSEQ = mi.inData.get("TSEQ") == null ? "" : mi.inData.get("TSEQ").trim();
    inCPID = mi.inData.get("CPID") == null ? "" : mi.inData.get("CPID").trim();
    inTKID = mi.inData.get("TKID") == null ? "" : mi.inData.get("TKID").trim();
    inLRMN = mi.inData.get("LRMN") == null ? "" : mi.inData.get("LRMN").trim();
    inLRMX = mi.inData.get("LRMX") == null ? "" : mi.inData.get("LRMX").trim();
    inLABR = mi.inData.get("LABR") == null ? "0.0" : mi.inData.get("LABR").trim();
    inLBRS = mi.inData.get("LBRS") == null ? "" : mi.inData.get("LBRS").trim();
    inLBMN = mi.inData.get("LBMN") == null ? "" : mi.inData.get("LBMN") as char;
    inLCFM = mi.inData.get("LCFM") == null ? "" : mi.inData.get("LCFM") as char;

    if (!isValidInput()) {
      return;
    }

    DBAction query = database.table("EXTLAB").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    container.set("EXTKID", inTKID);
    container.set("EXLRIT", inLRIT);
    container.set("EXLRID", inLRID as int);

    if (!query.read(container)) {
      mi.error("Record does not exists");
      return;
    }

    LocalDateTime dateTime = LocalDateTime.now();
    int changedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toInteger();
    int changedTime = dateTime.format(DateTimeFormatter.ofPattern("HHmmss")).toInteger();

    query.readLock(container, {
      LockedResult lockedResult ->
      if (!inLRNM.isBlank()) {
        lockedResult.set("EXLRNM", inLRNM.equals("?") ? "" : inLRNM);
      }
      if (!inTSEQ.isBlank()) {
        lockedResult.set("EXTSEQ", inTSEQ.equals("?") ? "" : inTSEQ as int);
      }
      if (!inCPID.isBlank()) {
        lockedResult.set("EXCPID", inCPID.equals("?") ? "" : inCPID);
      }
      if (!inLRMN.isBlank()) {
        lockedResult.set("EXLRMN", inLRMN.equals("?") ? "" : inLRMN as double);
      }
      if (!inLRMX.isBlank()) {
        lockedResult.set("EXLRMX", inLRMX.equals("?") ? "" : inLRMX as double);
      }
      if (!inLABR.isBlank()) {
        lockedResult.set("EXLABR", inLABR.equals("?") ? 0.0 : inLABR as double);
      }
      if (!inLBRS.isBlank()) {
        lockedResult.set("EXLBRS", inLBRS.equals("?") ? "" : inLBRS);
      }
      if (!inLBMN.isBlank()) {
        lockedResult.set("EXLBMN", inLBMN.equals("?") ? "" : inLBMN.toUpperCase());
      }
      if (!inLCFM.isBlank()) {
        lockedResult.set("EXLCFM", inLCFM.equals("?") ? "" : inLCFM.toUpperCase());
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
    if (!inWHLO.isBlank()) {
      if (!this.checkWHLO()) {
        mi.error("Warehouse ${inWHLO} does not exist");
        return false;
      }
    }
    
    //Check inLBMN
    if (!inLBMN.isBlank()) {
      if (!utility.call("CommonUtil", "isValidFlag", inLBMN, "Y", "N")) {
        mi.error("Invalid input value for ${inLBMN}. Must be Y or N.");
        return false;
      }
    }
    
    //Check inLCFM
    if (!inLCFM.isBlank()) {
      if (!utility.call("CommonUtil", "isValidFlag", inLCFM, "Y", "N")) {
        mi.error("Invalid input value for ${inLCFM}. Must be Y or N.");
        return false;
      }
    }
    
    if (!validItemType.contains(inLRIT)) {
      mi.error("Invalid item type. Allowed item type are ${Arrays.toString(validItemType)}");
      return false;
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