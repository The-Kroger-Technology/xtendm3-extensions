/**
 * @Name: AddEXTPLP.EXTPLP
 * @Description: Add record on table EXTPLP
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddEXTPLP extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO;
  private String inDIVI;
  private String inWHLO;
  private String inITNO;
  private String inBDRN;
  private String inYEAR;
  private String inWENU;
  private String inRETY;
  private String inBFPR;
  private String inPRPR;
  private String inSNPR;
  private String inTSPR;
  private String inLDPC;
  private String inCHID;
  private int inCHNO;
  private int inRGDT;
  private int inRGTM;
  private int inLMDT;
  private int inLMTM;

  public AddEXTPLP(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inITNO = mi.inData.get("ITNO") == null ? "" : mi.inData.get("ITNO").trim();
    inBDRN = mi.inData.get("BDRN") == null ? "" : mi.inData.get("BDRN").trim();
    inYEAR = mi.inData.get("YEAR") == null ? "" : mi.inData.get("YEAR").trim();
    inWENU = mi.inData.get("WENU") == null ? "" : mi.inData.get("WENU").trim();
    inRETY = mi.inData.get("RETY") == null ? "" : mi.inData.get("RETY").trim();
    inBFPR = mi.inData.get("BFPR") == null ? "" : mi.inData.get("BFPR").trim();
    inPRPR = mi.inData.get("PRPR") == null ? "" : mi.inData.get("PRPR").trim();
    inSNPR = mi.inData.get("SNPR") == null ? "" : mi.inData.get("SNPR").trim();
    inTSPR = mi.inData.get("TSPR") == null ? "" : mi.inData.get("TSPR").trim();
    inLDPC = mi.inData.get("LDPC") == null ? "" : mi.inData.get("LDPC").trim();

    DBAction query = database.table("EXTPLP").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXWHLO", inWHLO);
    container.set("EXITNO", inITNO);
    container.set("EXBDRN", inBDRN);
    container.set("EXYEAR", inYEAR as int);
    container.set("EXWENU", inWENU as int);
    container.set("EXRETY", inRETY);

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
    inRGTM = entryTime;
    inLMDT = entryDate;
    inLMTM = entryTime;

    container.set("EXDIVI", inDIVI);
    container.set("EXBFPR", !inBFPR.isBlank() ? inBFPR as double : 0);
    container.set("EXPRPR", !inPRPR.isBlank() ? inPRPR as double : 0);
    container.set("EXSNPR", !inSNPR.isBlank() ? inSNPR as double : 0);
    container.set("EXTSPR", !inTSPR.isBlank() ? inTSPR as double : 0);
    container.set("EXLDPC", !inLDPC.isBlank() ? inLDPC as int : 0);
    container.set("EXCHID", inCHID);
    container.set("EXCHNO", inCHNO);
    container.set("EXRGDT", inRGDT);
    container.set("EXRGTM", inRGTM);
    container.set("EXLMDT", inLMDT);
    container.set("EXLMTM", inLMTM);

    query.insert(container);
  }
  
  boolean isValidInput() {
    // Check WHLO
    if (inWHLO != null && !inWHLO.isEmpty()) {
      if (!checkWHLO()) {
        mi.error("WHLO not found in MITWHL");
        return false;
      }
    }
  
    // Check ITNO
    if (inITNO != null && !inITNO.isEmpty()) {
      if (!checkITNO()) {
        mi.error("ITNO not found in MITMAS");
        return false;
      }
    }
  
    // Check RETY
    if (inRETY != null && !inRETY.isEmpty()) {
      if (!checkRETY()) {
        mi.error("RETY value is invalid");
        return false;
      }
    }
  
    return true;
  }
  
  boolean checkWHLO() {
    DBAction query = database.table("MITWHL").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("MWCONO", inCONO);
    container.set("MWWHLO", inWHLO);
    return query.read(container);
  }
  
  boolean checkITNO() {
    DBAction query = database.table("MITMAS").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("MMCONO", inCONO);
    container.set("MMITNO", inITNO);
    return query.read(container);
  }
  
  boolean checkRETY() {
    String value = inRETY.toUpperCase();
    return value.equals("C") || value.equals("M");
  }
}