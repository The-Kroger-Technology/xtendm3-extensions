/**
 * @Name: AddEXTLTD.EXTLTD
 * @Description: Add record on table EXTLTD
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     20240408  JHC     Initial Release - Generated from XtendM3 CRUD Generator, additional changes
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddEXTLTD extends ExtendM3Transaction {
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
  private String inCHID;
  private int inCHNO;
  private int inRGDT;
  private int inRGTM;
  private int inLMDT;
  private int inLMTM;
  private String[] validLTIT = ["M", "O", "C", "X"]; //M=Milk, O=Orange Juice,C=Cyder, X=Expense
  
  public AddEXTLTD(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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

    if (!isValidInput()) {
      return;
    }
    
    DBAction query = database.table("EXTLTD").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXTLIT", inTLIT);
    container.set("EXLTID", inLTID as int);

    if (query.read(container)) {
      mi.error("Record already exists");
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

    container.set("EXTSEQ", !inTSEQ.isBlank() ? inTSEQ as int : 0);
    container.set("EXTSNM", inTSNM);
    container.set("EXRGMN", !inRGMN.isBlank() ? inRGMN as double : 0);
    container.set("EXRGMX", !inRGMX.isBlank() ? inRGMX as double : 0);
    container.set("EXPLMN", !inPLMN.isBlank() ? inPLMN as double : 0);
    container.set("EXPLMX", !inPLMX.isBlank() ? inPLMX as double : 0);
    container.set("EXPLUS", inPLUS.isEmpty() ? "" : inPLUS.toUpperCase() as char);
    container.set("EXCOUS", inCOUS.isEmpty() ? "" : inCOUS.toUpperCase() as char);
    container.set("EXCOTS", !inCOTS.isBlank() ? inCOTS as int : 0);
    container.set("EXACTV", inACTV.isEmpty() ? "" : inACTV.toUpperCase() as char);
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