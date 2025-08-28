/**
 * @Name: UpdEXTMUR.EXTMUR
 * @Description: Update record on table EXTMUR
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

public class UpdEXTMUR extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO;
  private String inDIVI, inWHLO, inDATE, inMFNO, inPRNO, inMKCL, inCMDT, inSCMD, inMFQT, inUNMS, inAMQT, inSMQT, inABQT, inSBQT, inAOMQ, inSOMQ, inAOBQ, inSOBQ, inANFQ, inSNFQ;

  public UpdEXTMUR(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
    this.mi = mi;
    this.utility = utility;
    this.logger = logger;
    this.program = program;
    this.miCaller = miCaller;
    this.database = database;
  }

  public void main() {
    inCONO = mi.in.get("CONO") == null ? program.LDAZD.CONO as int : mi.in.get("CONO") as int;
    inDIVI = mi.inData.get("DIVI") == null ? "" : mi.inData.get("DIVI").trim() as String;
    inWHLO = mi.inData.get("WHLO") == null ? "" : mi.inData.get("WHLO").trim() as String;
    inDATE = mi.inData.get("DATE") == null ? "" : mi.inData.get("DATE").trim() as String;
    inMFNO = mi.inData.get("MFNO") == null ? "" : mi.inData.get("MFNO").trim() as String;
    inPRNO = mi.inData.get("PRNO") == null ? "" : mi.inData.get("PRNO").trim() as String;
    inMKCL = mi.inData.get("MKCL") == null ? "" : mi.inData.get("MKCL").trim() as String;
    inCMDT = mi.inData.get("CMDT") == null ? "" : mi.inData.get("CMDT").trim() as String;
    inSCMD = mi.inData.get("SCMD") == null ? "" : mi.inData.get("SCMD").trim() as String;
    inMFQT = mi.inData.get("MFQT") == null ? "" : mi.inData.get("MFQT").trim() as String;
    inUNMS = mi.inData.get("UNMS") == null ? "" : mi.inData.get("UNMS").trim() as String;
    inAMQT = mi.inData.get("AMQT") == null ? "" : mi.inData.get("AMQT").trim() as String;
    inSMQT = mi.inData.get("SMQT") == null ? "" : mi.inData.get("SMQT").trim() as String;
    inABQT = mi.inData.get("ABQT") == null ? "" : mi.inData.get("ABQT").trim() as String;
    inSBQT = mi.inData.get("SBQT") == null ? "" : mi.inData.get("SBQT").trim() as String;
    inAOMQ = mi.inData.get("AOMQ") == null ? "" : mi.inData.get("AOMQ").trim() as String;
    inSOMQ = mi.inData.get("SOMQ") == null ? "" : mi.inData.get("SOMQ").trim() as String;
    inAOBQ = mi.inData.get("AOBQ") == null ? "" : mi.inData.get("AOBQ").trim() as String;
    inSOBQ = mi.inData.get("SOBQ") == null ? "" : mi.inData.get("SOBQ").trim() as String;
    inANFQ = mi.inData.get("ANFQ") == null ? "" : mi.inData.get("ANFQ").trim() as String;
    inSNFQ = mi.inData.get("SNFQ") == null ? "" : mi.inData.get("SNFQ").trim() as String;

    if (!isValidInput()) {
      return;
    }
    
    DBAction queryEXTMUR = database.table("EXTMUR").index("00").selectAllFields().build();
    DBContainer containerEXTMUR = queryEXTMUR.getContainer();
    containerEXTMUR.set("EXCONO", inCONO);
    containerEXTMUR.set("EXDIVI", inDIVI);
    containerEXTMUR.set("EXWHLO", inWHLO);
    containerEXTMUR.set("EXDATE", inDATE as int);
    containerEXTMUR.set("EXMFNO", inMFNO);
    containerEXTMUR.set("EXPRNO", inPRNO);

    if (!queryEXTMUR.read(containerEXTMUR)) {
      mi.error("Record does not exist");
      return;
    }

    LocalDateTime dateTime = LocalDateTime.now();
    int changedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toInteger();
    int changedTime = dateTime.format(DateTimeFormatter.ofPattern("HHmmss")).toInteger();

    queryEXTMUR.readLock(containerEXTMUR, { LockedResult lockedResult ->
      if (!inMKCL.isBlank()) {
        lockedResult.set("EXMKCL", inMKCL.equals("?") ? "" : inMKCL);
      }
      if (!inCMDT.isBlank()) {
        lockedResult.set("EXCMDT", inCMDT.equals("?") ? "" : inCMDT);
      }
      if (!inSCMD.isBlank()) {
        lockedResult.set("EXSCMD", inSCMD.equals("?") ? "" : inSCMD);
      }
      if (!inMFQT.isBlank()) {
        lockedResult.set("EXMFQT", inMFQT.equals("?") ? 0 : inMFQT as double);
      }
      if (!inUNMS.isBlank()) {
        lockedResult.set("EXUNMS", inUNMS.equals("?") ? "" : inUNMS);
      }
      if (!inAMQT.isBlank()) {
        lockedResult.set("EXAMQT", inAMQT.equals("?") ? 0 : inAMQT as double);
      }
      if (!inSMQT.isBlank()) {
        lockedResult.set("EXSMQT", inSMQT.equals("?") ? 0 : inSMQT as double);
      }
      if (!inABQT.isBlank()) {
        lockedResult.set("EXABQT", inABQT.equals("?") ? 0 : inABQT as double);
      }
      if (!inSBQT.isBlank()) {
        lockedResult.set("EXSBQT", inSBQT.equals("?") ? 0 : inSBQT as double);
      }
      if (!inAOMQ.isBlank()) {
        lockedResult.set("EXAOMQ", inAOMQ.equals("?") ? 0 : inAOMQ as double);
      }
      if (!inSOMQ.isBlank()) {
        lockedResult.set("EXSOMQ", inSOMQ.equals("?") ? 0 : inSOMQ as double);
      }
      if (!inAOBQ.isBlank()) {
        lockedResult.set("EXAOBQ", inAOBQ.equals("?") ? 0 : inAOBQ as double);
      }
      if (!inSOBQ.isBlank()) {
        lockedResult.set("EXSOBQ", inSOBQ.equals("?") ? 0 : inSOBQ as double);
      }
      if (!inANFQ.isBlank()) {
        lockedResult.set("EXANFQ", inANFQ.equals("?") ? 0 : inANFQ as double);
      }
      if (!inSNFQ.isBlank()) {
        lockedResult.set("EXSNFQ", inSNFQ.equals("?") ? 0 : inSNFQ as double);
      }
      lockedResult.set("EXCHNO", lockedResult.getInt("EXCHNO") + 1);
      lockedResult.set("EXLMDT", changedDate);
      lockedResult.set("EXLMTM", changedTime);
      lockedResult.set("EXCHID", program.getUser());
      lockedResult.update();
    });
  }
  
  /**
   * Validate input fields
   */
  boolean isValidInput() {
    // Check MKCL
    if (!inMKCL.isBlank() && inMKCL != "?") {
      if (!this.checkMKCL()) {
        mi.error("Milk class ${inMKCL} does not exist");
        return false;
      }
    }
    
    // Check CMDT
    if (!inCMDT.isBlank() && inCMDT != "?") {
      if (!this.checkCSYTAB("CFI3", inCMDT)) {
        mi.error("Commodity ${inCMDT} does not exist");
        return false;
      }
    }
    
    // Check SCMD
    if (!inSCMD.isBlank() && inSCMD != "?") {
      if (!this.checkCSYTAB("CFI1", inSCMD)) {
        mi.error("Sub-commodity ${inSCMD} does not exist");
        return false;
      }
    }
    
    // Check UNMS
    if (!inUNMS.isBlank() && inUNMS != "?") {
      if (!this.checkCSYTAB("UNIT", inUNMS)) {
        mi.error("Basic unit of measure ${inUNMS} does not exist");
        return false;
      }
    }
    
    return true;
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