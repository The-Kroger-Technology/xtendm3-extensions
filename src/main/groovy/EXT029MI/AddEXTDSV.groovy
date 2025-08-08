/**
 * @Name: AddEXTDSV.EXTDSV
 * @Description: Add record on table EXTDSV
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     20250604 ADY      Initial Release
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;

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
    
    DBAction EXTDSV_query = database.table("EXTDSV").index("00").build();
    DBContainer EXTDSV = EXTDSV_query.getContainer();
    EXTDSV.set("EXCONO", inCONO);
    EXTDSV.set("EXCUDT", inCUDT);
    EXTDSV.set("EXFACI", inFACI);
    EXTDSV.set("EXSDST", inSDST);
    EXTDSV.set("EXCUNO", inCUNO);
    EXTDSV.set("EXITNO", inITNO);
    
    if (EXTDSV_query.read(EXTDSV)) {
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

    EXTDSV.set("EXORQT", inORQT);
    EXTDSV.set("EXCHID", inCHID);
    EXTDSV.set("EXCHNO", inCHNO);
    EXTDSV.set("EXRGDT", inRGDT);
    EXTDSV.set("EXRGTM", inRGTM);
    EXTDSV.set("EXLMDT", inLMDT);
    EXTDSV.set("EXLMTM", inLMTM);

    EXTDSV_query.insert(EXTDSV);
  }

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
   * Validate SDST from CSYTAB
   */
  boolean checkSDST() {
    DBAction CSYTAB_query = database.table("CSYTAB").index("00").build();
    DBContainer CSYTAB = CSYTAB_query.getContainer();
    CSYTAB.set("CTCONO", inCONO);
    CSYTAB.set("CTDIVI", "");
    CSYTAB.set("CTSTCO", "SDST");
    CSYTAB.set("CTSTKY", inSDST);
    CSYTAB.set("CTLNCD", "");
    
    if (!CSYTAB_query.read(CSYTAB)) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * Validate CUNO from OCUSMA
   */
  boolean checkCUNO() {
    DBAction OCUSMA_query = database.table("OCUSMA").index("00").build();
    DBContainer OCUSMA = OCUSMA_query.getContainer();
    OCUSMA.set("OKCONO", inCONO);
    OCUSMA.set("OKCUNO", inCUNO);
  
    if (!OCUSMA_query.read(OCUSMA)) {
      mi.error("Customer ${inCUNO} does not exist");
      return false;
    } else {
      if (!inSDST.isBlank()) {
        ExpressionFactory OCUSMA_exp = database.getExpressionFactory("OCUSMA");
        OCUSMA_exp = OCUSMA_exp.eq("OKSDST", inSDST);
        OCUSMA_query = database.table("OCUSMA").index("00").matching(OCUSMA_exp).build();
      
        if (!OCUSMA_query.read(OCUSMA)) {
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
    DBAction MITMAS_query = database.table("MITMAS").index("00").build();
    DBContainer MITMAS = MITMAS_query.getContainer();
    MITMAS.set("MMCONO", inCONO);
    MITMAS.set("MMITNO", inITNO);
  
    if (!MITMAS_query.read(MITMAS)) {
      return false;
    } else {
      return true;
    }
  }

}