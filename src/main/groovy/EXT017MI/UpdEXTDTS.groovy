/**
 * @Name: UpdEXTDTS.EXTDTS
 * @Description: Update record on table EXTDTS
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UpdEXTDTS extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO, inDSEQ;
  private String inDIVI;
  private String inWHLO;
  private String inLDID;
  private String inSNUM;
  private String inLOCA;
  private String inSTAT;
  private String inRRSN;
  private String inPSRL;
  private String inPSRS;
  private String inPSRR;
  private String inPSVR;

  public UpdEXTDTS(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inDSEQ = mi.in.get("DSEQ") == null ? 0 : mi.in.get("DSEQ") as Integer;
    inSNUM = mi.inData.get("SNUM") == null ? "" : mi.inData.get("SNUM").trim();
    inLOCA = mi.inData.get("LOCA") == null ? "" : mi.inData.get("LOCA").trim();
    inSTAT = mi.inData.get("STAT") == null ? "" : mi.inData.get("STAT").trim();
    inRRSN = mi.inData.get("RRSN") == null ? "" : mi.inData.get("RRSN").trim();
    inPSRL = mi.inData.get("PSRL") == null ? "" : mi.inData.get("PSRL").trim();
    inPSRS = mi.inData.get("PSRS") == null ? "" : mi.inData.get("PSRS").trim();
    inPSRR = mi.inData.get("PSRR") == null ? "" : mi.inData.get("PSRR").trim();
    inPSVR = mi.inData.get("PSVR") == null ? "" : mi.inData.get("PSVR") as char;

    if (!isValidInput()) {
      return;
    }

    DBAction query = database.table("EXTDTS").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    container.set("EXDSEQ", inDSEQ);
    container.set("EXSNUM", inSNUM);

    if (!query.read(container)) {
      mi.error("Record does not exists");
      return;
    }

    LocalDateTime dateTime = LocalDateTime.now();
    int changedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toInteger();
    int changedTime = dateTime.format(DateTimeFormatter.ofPattern("HHmmss")).toInteger();

    query.readLock(container, {
      LockedResult lockedResult ->
      if (!inLOCA.isBlank()) {
        lockedResult.set("EXLOCA", inLOCA.equals("?") ? "" : inLOCA);
      }
      if (!inSTAT.isBlank()) {
        lockedResult.set("EXSTAT", inSTAT.equals("?") ? "" : inSTAT);
      }
      if (!inRRSN.isBlank()) {
        lockedResult.set("EXRRSN", inRRSN.equals("?") ? "" : inRRSN);
      }
      if (!inPSRL.isBlank()) {
        lockedResult.set("EXPSRL", inPSRL.equals("?") ? "" : inPSRL);
      }
      if (!inPSRS.isBlank()) {
        lockedResult.set("EXPSRS", inPSRS.equals("?") ? "" : inPSRS);
      }
      if (!inPSRR.isBlank()) {
        lockedResult.set("EXPSRR", inPSRR.equals("?") ? "" : inPSRR);
      }
       if (!inPSVR.isBlank()) {
        lockedResult.set("EXPSVR", inPSVR.equals("?") ? "" : inPSVR.toUpperCase());
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
    
    //Check inPSVR
    if (!inPSVR.isBlank()) {
      if (!utility.call("CommonUtil", "isValidFlag", inPSVR, "Y", "N")) {
        mi.error("Invalid input value for ${inPSVR}. Should be Y or N.");
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