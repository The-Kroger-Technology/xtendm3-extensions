/**
 * @Name: UpdEXTSTR.EXTSTR
 * @Description: Update record on table EXTSTR
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     20240408  JHC     Initial Release - Generated from XtendM3 CRUD Generator, additional changes
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UpdEXTSTR extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO;
  private String inDIVI;
  private String inWHLO;
  private String inSTIS;
  private String inYEIS;
  private String inMOIS;
  private String inYEVA;

  public UpdEXTSTR(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inSTIS = mi.inData.get("STIS") == null ? "" : mi.inData.get("STIS").trim();
    inYEIS = mi.inData.get("YEIS") == null ? "" : mi.inData.get("YEIS").trim();
    inMOIS = mi.inData.get("MOIS") == null ? "" : mi.inData.get("MOIS").trim();
    inYEVA = mi.inData.get("YEVA") == null ? "" : mi.inData.get("YEVA").trim();

    DBAction query = database.table("EXTSTR").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXSTIS", inSTIS);
    container.set("EXYEIS", inYEIS as int);
    container.set("EXMOIS", inMOIS as int);

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
      if (!inYEVA.isBlank()) {
        lockedResult.set("EXYEVA", inYEVA.equals("?") ? 0 : inYEVA as int);
      }
      lockedResult.set("EXCHNO", lockedResult.getInt("EXCHNO") + 1);
      lockedResult.set("EXLMDT", changedDate);
      lockedResult.set("EXLMTM", changedTime);
      lockedResult.set("EXCHID", program.getUser());
      lockedResult.update();
    });
  }

  boolean checkWHLO() {
    DBAction query = database.table("MITWHL").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("MWCONO", inCONO);
    container.set("MWWHLO", inWHLO);
  
    return query.read(container);
  }
  
  boolean isValidInput() {
    // Check WHLO
    if (!checkWHLO()) {
      mi.error("WHLO not found in MITWHL");
      return false;
    }
  
    // Check MOIS
    if (inMOIS != null && !inMOIS.trim().isEmpty()) {
      int mois = Integer.parseInt(inMOIS);
      if (mois < 0 || mois > 12) {
        mi.error("MOIS is an invalid value");
        return false;
      }
    }
  
    return true;
  }
}