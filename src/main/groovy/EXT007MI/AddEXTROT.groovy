/**
 * @Name: AddEXTROT.EXTROT
 * @Description: Add record on table EXTROT
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddEXTROT extends ExtendM3Transaction {
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
  private String inCHID;
  private int inCHNO;
  private int inRGDT;
  private int inRGTM;
  private int inLMDT;
  private int inLMTM;

  public AddEXTROT(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    container.set("EXRNBR", inRNBR.isEmpty() ? '\u0000' : inRNBR.charAt(0));
    container.set("EXRHID", inRHID);

    if (query.read(container)) {
      mi.error("Record already exists");
      return;
    }

    if (!isValidInput()) {
      return;
    }

    LocalDateTime dateTime = LocalDateTime.now();
    int entryDate = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toInteger();
    int entryTime = dateTime.format(DateTimeFormatter.ofPattern("HHmmss")).toInteger();
    inCHID = program.getUser();
    inCHNO = 1;
    inRGDT = entryDate;
    inRGTM = entryTime;inLMTM = entryTime;
    inLMDT = entryDate;
    inLMTM = entryTime;

    container.set("EXROHN", inROHN);
    container.set("EXRODE", inRODE);
    container.set("EXHPRT", inHPRT);
    container.set("EXSTAT", inSTAT);
    container.set("EXDBFP", !inDBFP.isBlank() ? inDBFP as double : 0);
    container.set("EXCHID", inCHID);
    container.set("EXCHNO", inCHNO);
    container.set("EXRGDT", inRGDT);
    container.set("EXRGTM", inRGTM);
    container.set("EXLMDT", inLMDT);
    container.set("EXLMTM", inLMTM);

    query.insert(container);
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