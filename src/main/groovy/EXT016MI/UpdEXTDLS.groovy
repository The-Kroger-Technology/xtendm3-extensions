/**
 * @Name: UpdEXTDLS.EXTDLS
 * @Description: Update record on table EXTDLS
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UpdEXTDLS extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO, inDSEQ, inEVDT;
  private String inDIVI;
  private String inWHLO;
  private String inLDID;
  private String inSPNO;
  private String inDESC;
  private String inPOST;
  private String inCMPT;
  private String inEVTM;

  public UpdEXTDLS(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inSPNO = mi.inData.get("SPNO") == null ? "" : mi.inData.get("SPNO").trim();
    inDESC = mi.inData.get("DESC") == null ? "" : mi.inData.get("DESC").trim();
    inPOST = mi.inData.get("POST") == null ? "" : mi.inData.get("POST").trim();
    inCMPT = mi.inData.get("CMPT") == null ? "" : mi.inData.get("CMPT").trim();
    inEVDT = mi.in.get("EVDT") == null ? 0 : mi.in.get("EVDT") as Integer;
    inEVTM = mi.inData.get("EVTM") == null ? "0" : mi.inData.get("EVTM").trim();

    if (!isValidInput()) {
      return;
    }

    DBAction query = database.table("EXTDLS").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    container.set("EXDSEQ", inDSEQ);
    container.set("EXSPNO", inSPNO);

    if (!query.read(container)) {
      mi.error("Record does not exists");
      return;
    }

    LocalDateTime dateTime = LocalDateTime.now();
    int changedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toInteger();
    int changedTime = dateTime.format(DateTimeFormatter.ofPattern("HHmmss")).toInteger();

    query.readLock(container, {
      LockedResult lockedResult ->
      if (!inDESC.isBlank()) {
        lockedResult.set("EXDESC", inDESC.equals("?") ? "" : inDESC);
      }
      if (!inPOST.isBlank()) {
        lockedResult.set("EXPOST", inPOST.equals("?") ? "" : inPOST);
      }
      if (!inCMPT.isBlank()) {
        lockedResult.set("EXCMPT", inCMPT.equals("?") ? "" : inCMPT);
      }
      if (inEVDT != 0) {
        lockedResult.set("EXEVDT", inEVDT.equals("?") ? 0 : inEVDT);
      }
      if (!inEVTM.isBlank()) {
        lockedResult.set("EXEVTM", inEVTM.equals("?") ? 0 : inEVTM as int);
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