/**
 * @Name: UpdEXTTPO.EXTTPO
 * @Description: Update record on table EXTTPO
 * @Authors:
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UpdEXTTPO extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO, inPSEQ;
  private String inDIVI;
  private String inWHLO;
  private String inLDID;
  private String inLOID;
  private String inSANU;
  private String inLOFN;
  private String inLOLN;
  private String inLOTY;
  private String inLOPN;

  public UpdEXTTPO(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inLOID = mi.inData.get("LOID") == null ? "" : mi.inData.get("LOID").trim();
    inSANU = mi.inData.get("SANU") == null ? "" : mi.inData.get("SANU").trim();
    inLOFN = mi.inData.get("LOFN") == null ? "" : mi.inData.get("LOFN").trim();
    inLOLN = mi.inData.get("LOLN") == null ? "" : mi.inData.get("LOLN").trim();
    inLOTY = mi.inData.get("LOTY") == null ? "" : mi.inData.get("LOTY").trim();
    inLOPN = mi.inData.get("LOPN") == null ? "" : mi.inData.get("LOPN").trim();

    DBAction query = database.table("EXTTPO").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    container.set("EXPSEQ", inPSEQ);
    container.set("EXLOID", inLOID);

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
      if (!inSANU.isBlank()) {
        lockedResult.set("EXSANU", inSANU.equals("?") ? "" : inSANU);
      }
      if (!inLOFN.isBlank()) {
        lockedResult.set("EXLOFN", inLOFN.equals("?") ? "" : inLOFN);
      }
      if (!inLOLN.isBlank()) {
        lockedResult.set("EXLOLN", inLOLN.equals("?") ? "" : inLOLN);
      }
      if (!inLOTY.isBlank()) {
        lockedResult.set("EXLOTY", inLOTY.equals("?") ? "" : inLOTY);
      }
      if (!inLOPN.isBlank()) {
        lockedResult.set("EXLOPN", inLOPN.equals("?") ? "" : inLOPN);
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
  }
  
  boolean checkWHLO() {
    DBAction query = database.table("MITWHL").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("MWCONO", inCONO);
    container.set("MWWHLO", inWHLO);
    return query.read(container);
  }

}