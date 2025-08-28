/**
 * @Name: EXT005MI.AddEXTRAW
 * @Description: Add record on table EXTRAW
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date      User    Description
 *  1.0.0     20250324  ADY     Initial Release
 *  1.0.1     20250826  ADY     Added Javadoc comments, fixed variable names, removed SimpleDateFormat
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

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
    
    DBAction queryEXTRAW = database.table("EXTRAW").index("00").build();
    DBContainer containerEXTRAW = queryEXTRAW.getContainer();
    containerEXTRAW.set("EXCONO", inCONO);
    containerEXTRAW.set("EXFACI", inFACI);
    containerEXTRAW.set("EXMTNO", inMTNO);
    containerEXTRAW.set("EXITNO", inITNO);
    containerEXTRAW.set("EXITCL", inITCL);
    containerEXTRAW.set("EXFDAT", inFDAT);
    containerEXTRAW.set("EXTDAT", inTDAT);

    if (queryEXTRAW.read(containerEXTRAW)) {
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

    containerEXTRAW.set("EXTRQT", inTRQT);
    containerEXTRAW.set("EXTAMT", inTAMT);
    containerEXTRAW.set("EXPCTG", inPCTG);
    containerEXTRAW.set("EXFDAT", inFDAT);
    containerEXTRAW.set("EXTDAT", inTDAT);
    containerEXTRAW.set("EXCHID", inCHID);
    containerEXTRAW.set("EXCHNO", inCHNO);
    containerEXTRAW.set("EXRGDT", inRGDT);
    containerEXTRAW.set("EXRGTM", inRGTM);
    containerEXTRAW.set("EXLMDT", inLMDT);
    containerEXTRAW.set("EXLMTM", inLMTM);

    queryEXTRAW.insert(containerEXTRAW);
  }

  /**
   * Validate input fields
   */
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
      LocalDate ldFDAT = LocalDate.parse(inFDAT.toString(), DateTimeFormatter.ofPattern("yyyyMMdd"));
      LocalDate ldTDAT = LocalDate.parse(inTDAT.toString(), DateTimeFormatter.ofPattern("yyyyMMdd"));
    
      if(ldFDAT.isAfter(ldTDAT)) {
        mi.error("From date is greater than To date");
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
    DBAction queryCFACIL = database.table("CFACIL").index("00").build();
    DBContainer containerCFACIL = queryCFACIL.getContainer();
    containerCFACIL.set("CFCONO", inCONO);
    containerCFACIL.set("CFFACI", inFACI);
  
    if (!queryCFACIL.read(containerCFACIL)) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * Validate ITNO from MITMAS
   */
  boolean checkITNO(String itno) {
    DBAction queryMITMAS = database.table("MITMAS").index("00").build();
    DBContainer containerMITMAS = queryMITMAS.getContainer();
    containerMITMAS.set("MMCONO", inCONO);
    containerMITMAS.set("MMITNO", itno);
  
    if (!queryMITMAS.read(containerMITMAS)) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * Validate ITCL from CSYTAB
   */
  boolean checkITCL() {
    DBAction queryCSYTAB = database.table("CSYTAB").index("00").build();
    DBContainer containerCSYTAB = queryCSYTAB.getContainer();
    containerCSYTAB.set("CTCONO", inCONO);
    containerCSYTAB.set("CTDIVI", "");
    containerCSYTAB.set("CTSTCO", "ITCL");
    containerCSYTAB.set("CTSTKY", inITCL);
    containerCSYTAB.set("CTLNCD", "");
    
    if (!queryCSYTAB.read(containerCSYTAB)) {
      return false;
    } else {
      return true;
    }
  }
}