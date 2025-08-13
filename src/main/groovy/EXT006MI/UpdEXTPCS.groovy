/**
 * @Name: UpdEXTPCS.EXTPCS
 * @Description: Update record on table EXTPCS
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     20240408  JHC     Initial Release - Generated from XtendM3 CRUD Generator, additional changes
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UpdEXTPCS extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO;
  private String inDIVI;
  private String inWHLO;
  private String inREGN;
  private String inLDNU;
  private String inLRNU;
  private String inNBRB;
  private String inSCLE;
  private String inSCIN;
  private String inFLMT;
  private String inESCH;
  private String inEMAN;
  private String inDBFP;
  private String inDPRP;
  private String inDSOP;
  private String inURPC;

  public UpdEXTPCS(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inREGN = mi.inData.get("REGN") == null ? "" : mi.inData.get("REGN").trim();
    inLDNU = mi.inData.get("LDNU") == null ? "" : mi.inData.get("LDNU").trim();
    inLRNU = mi.inData.get("LRNU") == null ? "" : mi.inData.get("LRNU").trim();
    inNBRB = mi.inData.get("NBRB") == null ? "" : mi.inData.get("NBRB").trim();
    inSCLE = mi.inData.get("SCLE") == null ? "" : mi.inData.get("SCLE").trim();
    inSCIN = mi.inData.get("SCIN") == null ? "" : mi.inData.get("SCIN").trim();
    inFLMT = mi.inData.get("FLMT") == null ? "" : mi.inData.get("FLMT").trim();
    inESCH = mi.inData.get("ESCH") == null ? "" : mi.inData.get("ESCH").trim();
    inEMAN = mi.inData.get("EMAN") == null ? "" : mi.inData.get("EMAN").trim();
    inDBFP = mi.inData.get("DBFP") == null ? "" : mi.inData.get("DBFP").trim();
    inDPRP = mi.inData.get("DPRP") == null ? "" : mi.inData.get("DPRP").trim();
    inDSOP = mi.inData.get("DSOP") == null ? "" : mi.inData.get("DSOP").trim();
    inURPC = mi.inData.get("URPC") == null ? "" : mi.inData.get("URPC").trim();

    DBAction query = database.table("EXTPCS").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);

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
      if (!inREGN.isBlank()) {
        lockedResult.set("EXREGN", inREGN.equals("?") ? "" : inREGN);
      }
      if (!inLDNU.isBlank()) {
        lockedResult.set("EXLDNU", inLDNU.equals("?") ? 0 : inLDNU as int);
      }
      if (!inLRNU.isBlank()) {
        lockedResult.set("EXLRNU", inLRNU.equals("?") ? 0 : inLRNU as int);
      }
      if (!inNBRB.isBlank()) {
        lockedResult.set("EXNBRB", inNBRB.equals("?") ? 0 : inNBRB as int);
      }
      if (!inSCLE.isBlank()) {
        lockedResult.set("EXSCLE", inSCLE.equals("?") ? "" : inSCLE);
      }
      if (!inSCIN.isBlank()) {
        lockedResult.set("EXSCIN", inSCIN.equals("?") ? "" : inSCIN);
      }
      if (!inFLMT.isBlank()) {
        lockedResult.set("EXFLMT", inFLMT.equals("?") ? "" : inFLMT);
      }
      if (!inESCH.isBlank()) {
        lockedResult.set("EXESCH", inESCH.equals("?") ? "" : inESCH);
      }
      if (!inEMAN.isBlank()) {
        lockedResult.set("EXEMAN", inEMAN.equals("?") ? "" : inEMAN);
      }
      if (!inDBFP.isBlank()) {
        lockedResult.set("EXDBFP", inDBFP.equals("?") ? 0 : inDBFP as double);
      }
      if (!inDPRP.isBlank()) {
        lockedResult.set("EXDPRP", inDPRP.equals("?") ? 0 : inDPRP as double);
      }
      if (!inDSOP.isBlank()) {
        lockedResult.set("EXDSOP", inDSOP.equals("?") ? 0 : inDSOP as double);
      }
      if (!inURPC.isBlank()) {
        lockedResult.set("EXURPC", inURPC.equals("?") ? "" : inURPC);
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
      if (!this.checkWHLO()) {
        mi.error("Warehouse " + inWHLO + " does not exist");
        return false;
      }
    }
  
    // Check SCLE
    if (inSCLE != null && !inSCLE.trim().isEmpty()) {
      if (!inSCLE.equalsIgnoreCase("Y") && !inSCLE.equalsIgnoreCase("N")) {
        mi.error("SCLE value is invalid. Must be Y or N.");
        return false;
      }
    }
  
    // Check SCIN
    if (inSCIN != null && !inSCIN.trim().isEmpty()) {
      if (!inSCIN.equalsIgnoreCase("Y") && !inSCIN.equalsIgnoreCase("N")) {
        mi.error("SCIN value is invalid. Must be Y or N.");
        return false;
      }
    }
  
    // Check FLMT
    if (inFLMT != null && !inFLMT.trim().isEmpty()) {
      if (!inFLMT.equalsIgnoreCase("Y") && !inFLMT.equalsIgnoreCase("N")) {
        mi.error("FLMT value is invalid. Must be Y or N.");
        return false;
      }
    }
  
    // Check ESCH
    if (inESCH != null && !inESCH.trim().isEmpty()) {
      if (!inESCH.equalsIgnoreCase("Y") && !inESCH.equalsIgnoreCase("N")) {
        mi.error("ESCH value is invalid. Must be Y or N.");
        return false;
      }
    }
  
    // Check EMAN
    if (inEMAN != null && !inEMAN.trim().isEmpty()) {
      if (!inEMAN.equalsIgnoreCase("Y") && !inEMAN.equalsIgnoreCase("N")) {
        mi.error("EMAN value is invalid. Must be Y or N.");
        return false;
      }
    }
  
    return true;
  }

}