/**
 * @Name: EXT005MI.AddEXTRAW
 * @Description: Add record on table EXTRAW
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date      User    Description
 *  1.0.0     20250324  ADY     Initial Release
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;

public class AddEXTRAW extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO, inFDAT, inTDAT, inCHNO, inRGDT, inRGTM, inLMDT, inLMTM;
  private String inFACI, inMTNO, inITNO, inITCL, inCHID;
  private double inTRQT, inTAMT, inPCTG;

  public AddEXTRAW(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
    this.mi = mi;
    this.utility = utility;
    this.logger = logger;
    this.program = program;
    this.miCaller = miCaller;
    this.database = database;
  }

  public void main() {
    inCONO = mi.in.get("CONO") == null ? program.LDAZD.CONO as int : mi.in.get("CONO") as int;
    inFACI = mi.inData.get("FACI") == null ? "" : mi.inData.get("FACI").trim() as String;
    inMTNO = mi.inData.get("MTNO") == null ? "" : mi.inData.get("MTNO").trim() as String;
    inITNO = mi.inData.get("ITNO") == null ? "" : mi.inData.get("ITNO").trim() as String;
    inITCL = mi.inData.get("ITCL") == null ? "" : mi.inData.get("ITCL").trim() as String;
    inFDAT = mi.inData.get("FDAT") == null ? 0 : mi.inData.get("FDAT").trim() as int;
    inTDAT = mi.inData.get("TDAT") == null ? 0 : mi.inData.get("TDAT").trim() as int;
    inTRQT = mi.inData.get("TRQT") == null ? 0 : mi.inData.get("TRQT").trim() as double;
    inTAMT = mi.inData.get("TAMT") == null ? 0 : mi.inData.get("TAMT").trim() as double;
    inPCTG = mi.inData.get("PCTG") == null ? 0 : mi.inData.get("PCTG").trim() as double;

    if (!isValidInput()) {
      return;
    }
    
    DBAction EXTRAW_query = database.table("EXTRAW").index("00").build();
    DBContainer EXTRAW = EXTRAW_query.getContainer();
    EXTRAW.set("EXCONO", inCONO);
    EXTRAW.set("EXFACI", inFACI);
    EXTRAW.set("EXMTNO", inMTNO);
    EXTRAW.set("EXITNO", inITNO);
    EXTRAW.set("EXITCL", inITCL);
    EXTRAW.set("EXFDAT", inFDAT);
    EXTRAW.set("EXTDAT", inTDAT);

    if (EXTRAW_query.read(EXTRAW)) {
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

    EXTRAW.set("EXTRQT", inTRQT);
    EXTRAW.set("EXTAMT", inTAMT);
    EXTRAW.set("EXPCTG", inPCTG);
    EXTRAW.set("EXFDAT", inFDAT);
    EXTRAW.set("EXTDAT", inTDAT);
    EXTRAW.set("EXCHID", inCHID);
    EXTRAW.set("EXCHNO", inCHNO);
    EXTRAW.set("EXRGDT", inRGDT);
    EXTRAW.set("EXRGTM", inRGTM);
    EXTRAW.set("EXLMDT", inLMDT);
    EXTRAW.set("EXLMTM", inLMTM);

    EXTRAW_query.insert(EXTRAW);
  }

  boolean isValidInput() {
    // Check FDAT
    if (inFDAT != 0) {
      if (!utility.call("DateUtil", "isDateValid", inFDAT.toString(), "yyyyMMdd")) {
        mi.error("Invalid DATE input for FDAT");
        return false;
      }
    }
    
    // Check TDAT
    if (inTDAT != 0) {
      if (!utility.call("DateUtil", "isDateValid", inTDAT.toString(), "yyyyMMdd")) {
        mi.error("Invalid DATE input for TDAT");
        return false;
      }
    }
    
    // Check FDAT and TDAT
    if (inFDAT != 0 && inTDAT != 0) {
      String strFDAT = inFDAT.toString();
      String strTDAT = inTDAT.toString()
      String dtFormat = "yyyyMMdd";
      
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dtFormat);
      Date dfFDAT = simpleDateFormat.parse(strFDAT);
      Date dfTDAT = simpleDateFormat.parse(strTDAT);
    
      if(dfFDAT.after(dfTDAT)) {
        mi.error("From date is greater than To date");
        return false;
      }
      
      if(dfTDAT.before(dfFDAT)) {
        mi.error("To date is less than From date");
        return false;
      }
    }
    
    // Check FACI
    if (!inFACI.isBlank()) {
      if (!this.checkFACI()) {
        mi.error("Facility ${inFACI} does not exist");
        return false;
      }
    }
    
    // Check MTNO
    if (!inMTNO.isBlank()) {
      if (!this.checkITNO(inMTNO)) {
        mi.error("Item number ${inMTNO} does not exist");
        return false;
      }
    }
    
    // Check ITNO
    if (!inITNO.isBlank()) {
      if (!this.checkITNO(inITNO)) {
        mi.error("Item number ${inITNO} does not exist");
        return false;
      }
    }
    
    // Check ITCL
    if (!inITCL.isBlank()) {
      if (!this.checkITCL()) {
        mi.error("Product group ${inITCL} does not exist");
        return false;
      }
    }
    
    return true;
  }
  
  /**
   * Validate FACI from CFACIL
   */
  boolean checkFACI() {
    DBAction CFACIL_query = database.table("CFACIL").index("00").build();
    DBContainer CFACIL = CFACIL_query.getContainer();
    CFACIL.set("CFCONO", inCONO);
    CFACIL.set("CFFACI", inFACI);
  
    if (!CFACIL_query.read(CFACIL)) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * Validate ITNO from MITMAS
   */
  boolean checkITNO(String ITNO) {
    DBAction MITMAS_query = database.table("MITMAS").index("00").build();
    DBContainer MITMAS = MITMAS_query.getContainer();
    MITMAS.set("MMCONO", inCONO);
    MITMAS.set("MMITNO", ITNO);
  
    if (!MITMAS_query.read(MITMAS)) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * Validate ITCL from CSYTAB
   */
  boolean checkITCL() {
    DBAction CSYTAB_query = database.table("CSYTAB").index("00").build();
    DBContainer CSYTAB = CSYTAB_query.getContainer();
    CSYTAB.set("CTCONO", inCONO);
    CSYTAB.set("CTDIVI", "");
    CSYTAB.set("CTSTCO", "ITCL");
    CSYTAB.set("CTSTKY", inITCL);
    CSYTAB.set("CTLNCD", "");
    
    if (!CSYTAB_query.read(CSYTAB)) {
      return false;
    } else {
      return true;
    }
  }

}