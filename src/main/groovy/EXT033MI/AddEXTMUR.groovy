/**
 * @Name: AddEXTMUR.EXTMUR
 * @Description: Add record on table EXTMUR
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     20250723 ADY      Initial Release
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddEXTMUR extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO, inDATE, inCHNO, inRGDT, inRGTM, inLMDT, inLMTM, pageSize;
  private String inDIVI, inWHLO, inMFNO, inPRNO, inMKCL, inCMDT, inSCMD, inUNMS, inCHID;
  private double inMFQT, inAMQT, inSMQT, inABQT, inSBQT, inAOMQ, inSOMQ, inAOBQ, inSOBQ;

  public AddEXTMUR(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
    this.mi = mi;
    this.utility = utility;
    this.logger = logger;
    this.program = program;
    this.miCaller = miCaller;
    this.database = database;
  }

  public void main() {
    inCONO = mi.in.get("CONO") == null ? program.LDAZD.CONO as int : mi.in.get("CONO") as int;
    inDIVI = mi.inData.get("DIVI") == null ? program.LDAZD.CONO as String : mi.inData.get("DIVI").trim() as String;
    inWHLO = mi.inData.get("WHLO") == null ? "" : mi.inData.get("WHLO").trim() as String;
    inDATE = mi.in.get("DATE") == null ? 0 : mi.in.get("DATE") as int;
    inMFNO = mi.inData.get("MFNO") == null ? "" : mi.inData.get("MFNO").trim() as String;
    inPRNO = mi.inData.get("PRNO") == null ? "" : mi.inData.get("PRNO").trim() as String;
    inMKCL = mi.inData.get("MKCL") == null ? "" : mi.inData.get("MKCL").trim() as String;
    inCMDT = mi.inData.get("CMDT") == null ? "" : mi.inData.get("CMDT").trim() as String;
    inSCMD = mi.inData.get("SCMD") == null ? "" : mi.inData.get("SCMD").trim() as String;
    inMFQT = mi.in.get("MFQT") == null ? 0 : mi.in.get("MFQT") as double;
    inUNMS = mi.inData.get("UNMS") == null ? "" : mi.inData.get("UNMS").trim() as String;
    inAMQT = mi.in.get("AMQT") == null ? 0 : mi.in.get("AMQT") as double;
    inSMQT = mi.in.get("SMQT") == null ? 0 : mi.in.get("SMQT") as double;
    inABQT = mi.in.get("ABQT") == null ? 0 : mi.in.get("ABQT") as double;
    inSBQT = mi.in.get("SBQT") == null ? 0 : mi.in.get("SBQT") as double;
    inAOMQ = mi.in.get("AOMQ") == null ? 0 : mi.in.get("AOMQ") as double;
    inSOMQ = mi.in.get("SOMQ") == null ? 0 : mi.in.get("SOMQ") as double;
    inAOBQ = mi.in.get("AOBQ") == null ? 0 : mi.in.get("AOBQ") as double;
    inSOBQ = mi.in.get("SOBQ") == null ? 0 : mi.in.get("SOBQ") as double;
    pageSize = mi.getMaxRecords() <= 0 || mi.getMaxRecords() >= 10000? 10000: mi.getMaxRecords();

    if (!isValidInput()) {
      return;
    }
    
    DBAction EXTMUR_query = database.table("EXTMUR").index("00").build();
    DBContainer EXTMUR = EXTMUR_query.getContainer();
    EXTMUR.set("EXCONO", inCONO);
    EXTMUR.set("EXDIVI", inDIVI);
    EXTMUR.set("EXWHLO", inWHLO);
    EXTMUR.set("EXDATE", inDATE as int);
    EXTMUR.set("EXMFNO", inMFNO);
    EXTMUR.set("EXPRNO", inPRNO);

    if (EXTMUR_query.read(EXTMUR)) {
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

    EXTMUR.set("EXMKCL", inMKCL);
    EXTMUR.set("EXCMDT", inCMDT);
    EXTMUR.set("EXSCMD", inSCMD);
    EXTMUR.set("EXMFQT", inMFQT);
    EXTMUR.set("EXUNMS", inUNMS);
    EXTMUR.set("EXAMQT", inAMQT);
    EXTMUR.set("EXSMQT", inSMQT);
    EXTMUR.set("EXABQT", inABQT);
    EXTMUR.set("EXSBQT", inSBQT);
    EXTMUR.set("EXAOMQ", inAOMQ);
    EXTMUR.set("EXSOMQ", inSOMQ);
    EXTMUR.set("EXAOBQ", inAOBQ);
    EXTMUR.set("EXSOBQ", inSOBQ);
    EXTMUR.set("EXCHID", inCHID);
    EXTMUR.set("EXCHNO", inCHNO);
    EXTMUR.set("EXRGDT", inRGDT);
    EXTMUR.set("EXRGTM", inRGTM);
    EXTMUR.set("EXLMDT", inLMDT);
    EXTMUR.set("EXLMTM", inLMTM);

    EXTMUR_query.insert(EXTMUR);
  }

  boolean isValidInput() {
    // Check WHLO
    if (!inWHLO.isBlank()) {
      if (!this.checkWHLO()) {
        mi.error("Warehouse ${inWHLO} does not exist");
        return false;
      }
    }
    
    // Check DATE
    if (!inDATE != 0) {
      if (!utility.call("DateUtil", "isDateValid", inDATE.toString(), "yyyyMMdd")) {
        mi.error("Invalid DATE input for DATE");
        return;
      }
    }
    
    // Check PRNO
    if (!inPRNO.isBlank()) {
      if (!this.checkPRNO()) {
        mi.error("Product ${inPRNO} does not exist");
        return false;
      }
    }
    
    // Check MFNO
    if (!inMFNO.isBlank()) {
      if (!this.checkMFNO()) {
        mi.error("Manufacturing order number ${inMFNO} does not exist");
        return false;
      }
    }
    
    // Check MKCL
    if (!inMKCL.isBlank()) {
      if (!this.checkMKCL()) {
        mi.error("Milk class ${inMKCL} does not exist");
        return false;
      }
    }
    
    // Check CMDT
    if (!inCMDT.isBlank()) {
      if (!this.checkCSYTAB("CFI3", inCMDT)) {
        mi.error("Commodity ${inCMDT} does not exist");
        return false;
      }
    }
    
    // Check SCMD
    if (!inSCMD.isBlank()) {
      if (!this.checkCSYTAB("CFI1", inSCMD)) {
        mi.error("Sub-commodity ${inSCMD} does not exist");
        return false;
      }
    }
    
    // Check UNMS
    if (!inUNMS.isBlank()) {
      if (!this.checkCSYTAB("UNIT", inUNMS)) {
        mi.error("Basic unit of measure ${inUNMS} does not exist");
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
  
  /**
   * Validate PRNO from MITMAS
   */
  boolean checkPRNO() {
    DBAction MITMAS_query = database.table("MITMAS").index("00").build();
    DBContainer MITMAS = MITMAS_query.getContainer();
    MITMAS.set("MMCONO", inCONO);
    MITMAS.set("MMITNO", inPRNO);
  
    if (!MITMAS_query.read(MITMAS)) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * Validate MFNO from MWOHED
   */
  boolean checkMFNO() {
    ExpressionFactory MWOHED_exp = database.getExpressionFactory("MWOHED");
    MWOHED_exp = MWOHED_exp.eq("VHMFNO", inMFNO);
    
    DBAction MWOHED_query = database.table("MWOHED").index("00").matching(MWOHED_exp).build();
    DBContainer MWOHED = MWOHED_query.getContainer();
    
    if (MWOHED_query.readAll(MWOHED, 0, pageSize, {}) <= 0) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * Validate MKCL from MATDAM
   */
  boolean checkMKCL() {
    ExpressionFactory MATDAM_exp = database.getExpressionFactory("MATDAM");
    MATDAM_exp = MATDAM_exp.eq("AKDEVA", inMKCL);
    
    DBAction MATDAM_query = database.table("MATDAM").index("00").matching(MATDAM_exp).build();
    DBContainer MATDAM = MATDAM_query.getContainer();
    
    if (MATDAM_query.readAll(MATDAM, 0, pageSize, {}) <= 0) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * Validate from CSYTAB
   */
  boolean checkCSYTAB(String STCO, String STKY) {
    DBAction CSYTAB_query = database.table("CSYTAB").index("00").build();
    DBContainer CSYTAB = CSYTAB_query.getContainer();
    CSYTAB.set("CTCONO", inCONO);
    CSYTAB.set("CTDIVI", "");
    CSYTAB.set("CTSTCO", STCO);
    CSYTAB.set("CTSTKY", STKY);
    CSYTAB.set("CTLNCD", "");
    
    if (!CSYTAB_query.read(CSYTAB)) {
      return false;
    } else {
      return true;
    }
  }

}