/**
 * @Name: AddEXTSGD.EXTSGD
 * @Description: Add record on table EXTSGD
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     20250604 ADY      Initial Release
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddEXTSGD extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO;
  private String inSDST, inSTRG, inCUNO, inCHID;
  private int inCHNO, inRGDT, inRGTM, inLMDT, inLMTM;

  public AddEXTSGD(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
    this.mi = mi;
    this.utility = utility;
    this.logger = logger;
    this.program = program;
    this.miCaller = miCaller;
    this.database = database;
  }

  public void main() {
    inCONO = mi.in.get("CONO") == null ? program.LDAZD.CONO as int : mi.in.get("CONO") as int;
    inSDST = mi.inData.get("SDST") == null ? "" : mi.inData.get("SDST").trim() as String;
    inSTRG = mi.inData.get("STRG") == null ? "" : mi.inData.get("STRG").trim() as String;
    inCUNO = mi.inData.get("CUNO") == null ? "" : mi.inData.get("CUNO").trim() as String;

    if (!isValidInput()) {
      return;
    }
    
    DBAction EXTSGD_query = database.table("EXTSGD").index("00").build();
    DBContainer EXTSGD = EXTSGD_query.getContainer();
    EXTSGD.set("EXCONO", inCONO);
    EXTSGD.set("EXSDST", inSDST);
    EXTSGD.set("EXSTRG", inSTRG);
    EXTSGD.set("EXCUNO", inCUNO);
    
    if (EXTSGD_query.read(EXTSGD)) {
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

    EXTSGD.set("EXCHID", inCHID);
    EXTSGD.set("EXCHNO", inCHNO);
    EXTSGD.set("EXRGDT", inRGDT);
    EXTSGD.set("EXRGTM", inRGTM);
    EXTSGD.set("EXLMDT", inLMDT);
    EXTSGD.set("EXLMTM", inLMTM);

    EXTSGD_query.insert(EXTSGD);
  }

  boolean isValidInput() {
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
    
    return true;
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
        OCUSMA_exp = OCUSMA_exp.eq("SDST", inSDST);
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

}