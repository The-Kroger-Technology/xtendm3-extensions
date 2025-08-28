/**
 * @Name: AddEXTSGD.EXTSGD
 * @Description: Add record on table EXTSGD
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date      User    Description
 *  1.0.0     20250604  ADY     Initial Release
 *  1.0.1     20250826  ADY     Added Javadoc comments, fixed variable names, removed query for SDST
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
    
    DBAction queryEXTSGD = database.table("EXTSGD").index("00").build();
    DBContainer containerEXTSGD = queryEXTSGD.getContainer();
    containerEXTSGD.set("EXCONO", inCONO);
    containerEXTSGD.set("EXSDST", inSDST);
    containerEXTSGD.set("EXSTRG", inSTRG);
    containerEXTSGD.set("EXCUNO", inCUNO);
    
    if (queryEXTSGD.read(containerEXTSGD)) {
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

    containerEXTSGD.set("EXCHID", inCHID);
    containerEXTSGD.set("EXCHNO", inCHNO);
    containerEXTSGD.set("EXRGDT", inRGDT);
    containerEXTSGD.set("EXRGTM", inRGTM);
    containerEXTSGD.set("EXLMDT", inLMDT);
    containerEXTSGD.set("EXLMTM", inLMTM);

    queryEXTSGD.insert(containerEXTSGD);
  }

  /**
   * Validate input fields
   */
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

}