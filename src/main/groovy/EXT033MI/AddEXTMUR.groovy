/**
 * @Name: AddEXTMUR.EXTMUR
 * @Description: Add record on table EXTMUR
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date      User    Description
 *  1.0.0     20250723  ADY     Initial Release
 *  1.0.1     20250814  ADY     Added inputs ANFQ, SNFQ
 *  1.0.2     20250826  ADY     Added Javadoc comments, fixed variable names, set nbrOfKeys to 1
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

  private int inCONO, inDATE, inCHNO, inRGDT, inRGTM, inLMDT, inLMTM;
  private String inDIVI, inWHLO, inMFNO, inPRNO, inMKCL, inCMDT, inSCMD, inUNMS, inCHID;
  private double inMFQT, inAMQT, inSMQT, inABQT, inSBQT, inAOMQ, inSOMQ, inAOBQ, inSOBQ, inANFQ, inSNFQ;

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
    inANFQ = mi.in.get("ANFQ") == null ? 0 : mi.in.get("ANFQ") as double;
    inSNFQ = mi.in.get("SNFQ") == null ? 0 : mi.in.get("SNFQ") as double;

    if (!isValidInput()) {
      return;
    }
    
    DBAction queryEXTMUR = database.table("EXTMUR").index("00").build();
    DBContainer containerEXTMUR = queryEXTMUR.getContainer();
    containerEXTMUR.set("EXCONO", inCONO);
    containerEXTMUR.set("EXDIVI", inDIVI);
    containerEXTMUR.set("EXWHLO", inWHLO);
    containerEXTMUR.set("EXDATE", inDATE as int);
    containerEXTMUR.set("EXMFNO", inMFNO);
    containerEXTMUR.set("EXPRNO", inPRNO);

    if (queryEXTMUR.read(containerEXTMUR)) {
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

    containerEXTMUR.set("EXMKCL", inMKCL);
    containerEXTMUR.set("EXCMDT", inCMDT);
    containerEXTMUR.set("EXSCMD", inSCMD);
    containerEXTMUR.set("EXMFQT", inMFQT);
    containerEXTMUR.set("EXUNMS", inUNMS);
    containerEXTMUR.set("EXAMQT", inAMQT);
    containerEXTMUR.set("EXSMQT", inSMQT);
    containerEXTMUR.set("EXABQT", inABQT);
    containerEXTMUR.set("EXSBQT", inSBQT);
    containerEXTMUR.set("EXAOMQ", inAOMQ);
    containerEXTMUR.set("EXSOMQ", inSOMQ);
    containerEXTMUR.set("EXAOBQ", inAOBQ);
    containerEXTMUR.set("EXSOBQ", inSOBQ);
    containerEXTMUR.set("EXANFQ", inANFQ);
    containerEXTMUR.set("EXSNFQ", inSNFQ);
    containerEXTMUR.set("EXCHID", inCHID);
    containerEXTMUR.set("EXCHNO", inCHNO);
    containerEXTMUR.set("EXRGDT", inRGDT);
    containerEXTMUR.set("EXRGTM", inRGTM);
    containerEXTMUR.set("EXLMDT", inLMDT);
    containerEXTMUR.set("EXLMTM", inLMTM);

    queryEXTMUR.insert(containerEXTMUR);
  }

  /**
   * Validate input fields
   */
  boolean isValidInput() {
    // Check WHLO
    if (!inWHLO.isBlank()) {
      if (!this.checkWHLO()) {
        mi.error("Warehouse ${inWHLO} does not exist");
        return false;
      }
    }
    
    // Check DATE
    if (inDATE != 0) {
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
    DBAction queryMITWHL = database.table("MITWHL").index("00").build();
    DBContainer containerMITWHL = queryMITWHL.getContainer();
    containerMITWHL.set("MWCONO", inCONO);
    containerMITWHL.set("MWWHLO", inWHLO);
  
    if (!queryMITWHL.read(containerMITWHL)) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * Validate PRNO from MITMAS
   */
  boolean checkPRNO() {
    DBAction queryMITMAS = database.table("MITMAS").index("00").build();
    DBContainer containerMITMAS = queryMITMAS.getContainer();
    containerMITMAS.set("MMCONO", inCONO);
    containerMITMAS.set("MMITNO", inPRNO);
  
    if (!queryMITMAS.read(containerMITMAS)) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * Validate MFNO from MWOHED
   */
  boolean checkMFNO() {
    ExpressionFactory expMWOHED = database.getExpressionFactory("MWOHED");
    expMWOHED = expMWOHED.eq("VHMFNO", inMFNO);
    
    DBAction queryMWOHED = database.table("MWOHED").index("00").matching(expMWOHED).build();
    DBContainer containerMWOHED = queryMWOHED.getContainer();
    containerMWOHED.set("VHCONO", inCONO);
    
    if (queryMWOHED.readAll(containerMWOHED, 1, 1, {}) <= 0) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * Validate MKCL from MATDAM
   */
  boolean checkMKCL() {
    ExpressionFactory expMATDAM = database.getExpressionFactory("MATDAM");
    expMATDAM = expMATDAM.eq("AKDEVA", inMKCL);
    
    DBAction queryMATDAM = database.table("MATDAM").index("00").matching(expMATDAM).build();
    DBContainer containerMATDAM = queryMATDAM.getContainer();
    containerMATDAM.set("AKCONO", inCONO);
    
    if (queryMATDAM.readAll(containerMATDAM, 1, 1, {}) <= 0) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * Validate from CSYTAB
   */
  boolean checkCSYTAB(String stco, String stky) {
    DBAction queryCSYTAB = database.table("CSYTAB").index("00").build();
    DBContainer containerCSYTAB = queryCSYTAB.getContainer();
    containerCSYTAB.set("CTCONO", inCONO);
    containerCSYTAB.set("CTDIVI", "");
    containerCSYTAB.set("CTSTCO", stco);
    containerCSYTAB.set("CTSTKY", stky);
    containerCSYTAB.set("CTLNCD", "");
    
    if (!queryCSYTAB.read(containerCSYTAB)) {
      return false;
    } else {
      return true;
    }
  }

}