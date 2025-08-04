/**
 * @Name: UpdEXTCCO.EXTCCO
 * @Description: Update record on table EXTCCO
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYMMdd   User     Initial Release - Generated from XtendM3 CRUD Generator
 *  1.1.0     250618   JHC      Added fields DIST, STOR, PRDY, PRTM
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdEXTCCO extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO;
  private String inDIVI;
  private String inDLIX;
  private String inINOU;
  private String inUCA8;
  private String inDIST;
  private String inSTOR;
  private String inPRDY;
  private String inPRTM;
  private String inORNO;
  private String inROUT;
  private String inLINB;
  private String inDSDT;

  public UpdEXTCCO(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inDLIX = mi.inData.get("DLIX") == null ? "" : mi.inData.get("DLIX").trim();
    inINOU = mi.inData.get("INOU") == null ? "" : mi.inData.get("INOU").trim();
    inUCA8 = mi.inData.get("UCA8") == null ? "" : mi.inData.get("UCA8").trim();
    inDIST = mi.inData.get("DIST") == null ? "" : mi.inData.get("DIST").trim();
    inSTOR = mi.inData.get("STOR") == null ? "0" : mi.inData.get("STOR").trim();
    inPRDY = mi.inData.get("PRDY") == null ? "0" : mi.inData.get("PRDY").trim();
    inPRTM = mi.inData.get("PRTM") == null ? "" : mi.inData.get("PRTM").trim();
    inORNO = mi.inData.get("ORNO") == null ? "" : mi.inData.get("ORNO").trim();
    inROUT = mi.inData.get("ROUT") == null ? "" : mi.inData.get("ROUT").trim();
    inLINB = mi.inData.get("LINB") == null ? "" : mi.inData.get("LINB").trim();
    inDSDT = mi.inData.get("DSDT") == null ? "" : mi.inData.get("DSDT").trim();
    
    DBAction query = database.table("EXTCCO").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDLIX", inDLIX as int);
    container.set("EXINOU", inINOU as int);

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
      if (!inUCA8.isBlank()) {
        lockedResult.set("EXUCA8", inUCA8.equals("?") ? "" : inUCA8);
      }
      if (!inDIST.isBlank()) {
        lockedResult.set("EXDIST", inDIST.equals("?") ? "" : inDIST);
      }
      
      if (!inSTOR.isBlank()) {
        lockedResult.set("EXSTOR", inSTOR.equals("?") ? 0 : inSTOR as int);
      }
      
      if (!inPRDY.isBlank()) {
        lockedResult.set("EXPRDY", inPRDY.equals("?") ? 0 : inPRDY as int);
      }
      
      if (!inPRTM.isBlank()) {
        lockedResult.set("EXPRTM", inPRTM.equals("?") ? "" : inPRTM);
      }
      if (!inORNO.isBlank()) {
        lockedResult.set("EXORNO", inORNO.equals("?") ? "" : inORNO);
      }
      if (!inROUT.isBlank()) {
        lockedResult.set("EXROUT", inROUT.equals("?") ? "" : inROUT);
      }
      if (!inLINB.isBlank()) {
        lockedResult.set("EXLINB", inLINB.equals("?") ? 0 : inLINB as int);
      }
      if (!inDSDT.isBlank()) {
        lockedResult.set("EXDSDT", inDSDT.equals("?") ? 0 : inDSDT as int);
      }
      lockedResult.set("EXCHNO", lockedResult.getInt("EXCHNO") + 1);
      lockedResult.set("EXLMDT", changedDate);
      lockedResult.set("EXLMTM", changedTime);
      lockedResult.set("EXCHID", program.getUser());
      lockedResult.update();
    });
  }

  boolean isValidInput() {
    // Check MHDISH
    if (!checkMHDISH()) {
      mi.error("DLIX not found in MHDISH");
      return false;
    }
  
    // Check ORNO (optional)
    if (inORNO != null && !inORNO.trim().isEmpty()) {
      if (!checkOOHEAD()) {
        mi.error("ORNO not found in OOHEAD");
        return false;
      }
    }
  
    // Check DSDT date format (optional)
    if (inDSDT != null && !inDSDT.trim().isEmpty()) {
      if (!checkDSDT()) {
        mi.error("DSDT is invalid");
        return false;
      }
    }
  
    return true;
  }
  
  boolean checkMHDISH() {
    DBAction query = database.table("MHDISH").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("OQCONO", inCONO);
    container.set("OQINOU", Integer.parseInt(inINOU));
    container.set("OQDLIX", Integer.parseInt(inDLIX));
    return query.read(container);
  }
  
  boolean checkOOHEAD() {
    DBAction query = database.table("OOHEAD").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("OACONO", inCONO);
    container.set("OAORNO", inORNO);
    return query.read(container);
  }
  
  boolean checkDSDT() {
    return utility.call("DateUtil", "isDateValid", inDSDT, "yyyyMMdd");
  }
}