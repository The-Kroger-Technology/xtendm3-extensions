/**
 * @Name: AddEXTDSV.EXTDSV
 * @Description: Add record on table EXTDSV
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date      User    Description
 *  1.0.0     20250604  ADY     Initial Release
 *  1.0.1     20250826  ADY     Added Javadoc comments, fixed variable names, removed query for SDST, removed SimpleDateFormat
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddEXTDSV extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO, inCUDT, inCHNO, inRGDT, inRGTM, inLMDT, inLMTM;
  private String inFACI, inSDST, inCUNO, inITNO, inCHID;
  private double inORQT;

  public AddEXTDSV(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
    this.mi = mi;
    this.utility = utility;
    this.logger = logger;
    this.program = program;
    this.miCaller = miCaller;
    this.database = database;
  }

  public void main() {
    inCONO = mi.in.get("CONO") == null ? program.LDAZD.CONO as int : mi.in.get("CONO") as int;
    inCUDT = mi.in.get("CUDT") == null ? 0 : mi.in.get("CUDT") as int;
    inFACI = mi.inData.get("FACI") == null ? "" : mi.inData.get("FACI").trim() as String;
    inSDST = mi.inData.get("SDST") == null ? "" : mi.inData.get("SDST").trim() as String;
    inCUNO = mi.inData.get("CUNO") == null ? "" : mi.inData.get("CUNO").trim() as String;
    inITNO = mi.inData.get("ITNO") == null ? "" : mi.inData.get("ITNO").trim() as String;
    inORQT = mi.in.get("ORQT") == null ? 0 : mi.in.get("ORQT") as double;

    if (!isValidInput()) {
      return;
    }
    
    DBAction queryEXTDSV = database.table("EXTDSV").index("00").build();
    DBContainer containerEXTDSV = queryEXTDSV.getContainer();
    containerEXTDSV.set("EXCONO", inCONO);
    containerEXTDSV.set("EXCUDT", inCUDT);
    containerEXTDSV.set("EXFACI", inFACI);
    containerEXTDSV.set("EXSDST", inSDST);
    containerEXTDSV.set("EXCUNO", inCUNO);
    containerEXTDSV.set("EXITNO", inITNO);
    
    if (queryEXTDSV.read(containerEXTDSV)) {
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

    containerEXTDSV.set("EXORQT", inORQT);
    containerEXTDSV.set("EXCHID", inCHID);
    containerEXTDSV.set("EXCHNO", inCHNO);
    containerEXTDSV.set("EXRGDT", inRGDT);
    containerEXTDSV.set("EXRGTM", inRGTM);
    containerEXTDSV.set("EXLMDT", inLMDT);
    containerEXTDSV.set("EXLMTM", inLMTM);

    queryEXTDSV.insert(containerEXTDSV);
  }

  /**
   * Validate input fields
   */
  boolean isValidInput() {
    //Check CUDT
    if (inCUDT != 0) {
      if (!utility.call("DateUtil", "isDateValid", inCUDT.toString(), "yyyyMMdd")) {
        mi.error("Invalid DATE input for CUDT");
        return;
      }
    }
    
    // Check FACI
    if (!inFACI.isBlank()) {
      if (!this.checkFACI()) {
        mi.error("Facility ${inFACI} does not exist");
        return false;
      }
    }
    
    // Check SDST
    if (!inSDST.isBlank()) {
      if (!this.checkSDST()) {
        mi.error("District ${inSDST} does not exist");
        return false;
      }
    }
    
    // Check CUNO
    if (!inCUNO.isBlank()) {
      if (!this.checkCUNO()) {
        return false;
      }
    }
    
    // Check ITNO
    if (!inITNO.isBlank()) {
      if (!this.checkITNO()) {
        mi.error("Item number ${inITNO} does not exist");
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
   * Validate SDST from CSYTAB
   */
  boolean checkSDST() {
    DBAction queryCSYTAB = database.table("CSYTAB").index("00").build();
    DBContainer containerCSYTAB = queryCSYTAB.getContainer();
    containerCSYTAB.set("CTCONO", inCONO);
    containerCSYTAB.set("CTDIVI", "");
    containerCSYTAB.set("CTSTCO", "SDST");
    containerCSYTAB.set("CTSTKY", inSDST);
    containerCSYTAB.set("CTLNCD", "");
    
    if (!queryCSYTAB.read(containerCSYTAB)) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * Validate CUNO from OCUSMA
   */
  boolean checkCUNO() {
    DBAction queryOCUSMA = database.table("OCUSMA").index("00").selection("OKSDST").build();
    DBContainer containerOCUSMA = queryOCUSMA.getContainer();
    containerOCUSMA.set("OKCONO", inCONO);
    containerOCUSMA.set("OKCUNO", inCUNO);
  
    if (!queryOCUSMA.read(containerOCUSMA)) {
      mi.error("Customer ${inCUNO} does not exist");
      return false;
    } else {
      if (!inSDST.isBlank()) {
        if (inSDST != containerOCUSMA.get("OKSDST").toString()) {
          mi.error("Customer ${inCUNO} does not belong to District ${inSDST}");
          return false;
        } else {
          return true;
        }
      } else {
        return true;
      }
    }
  }
  
  /**
   * Validate ITNO from MITMAS
   */
  boolean checkITNO() {
    DBAction queryMITMAS = database.table("MITMAS").index("00").build();
    DBContainer containerMITMAS = queryMITMAS.getContainer();
    containerMITMAS.set("MMCONO", inCONO);
    containerMITMAS.set("MMITNO", inITNO);
  
    if (!queryMITMAS.read(containerMITMAS)) {
      return false;
    } else {
      return true;
    }
  }
}