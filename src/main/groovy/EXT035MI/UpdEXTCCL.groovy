/**
 * @Name: UpdEXTCCL.EXTCCL
 * @Description: Update record on table EXTCCL
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date        User        Description
 *  1.0.0     20250818    JTAPANG     Initial Release - Generated from XtendM3 CRUD Generator. Add Validations and modifications
 *  1.1.0     20250911    JTAPANG     Add XtendM3 review comments.(Standard field validations, handling numeric exception, remove unused codes, Fix naming and variables)
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UpdEXTCCL extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO;
  private String inLINE;
  private String inDLIX;
  private String inINOU;
  private String inLINB;
  private String inORNO;
  private String inPONR;
  private String inITNO;
  private String inTEDS;
  private String inDPLO;
  private String inORQT;
  private String inUNMS;
  private String whlo = "";

  public UpdEXTCCL(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
    this.mi = mi;
    this.utility = utility;
    this.logger = logger;
    this.program = program;
    this.miCaller = miCaller;
    this.database = database;
  }

  public void main() {
    inCONO = mi.in.get("CONO") == null ? program.LDAZD.CONO as int : mi.in.get("CONO") as int;
    inLINE = mi.inData.get("LINE") == null ? "" : mi.inData.get("LINE").trim();
    inDLIX = mi.inData.get("DLIX") == null ? "" : mi.inData.get("DLIX").trim();
    inINOU = mi.inData.get("INOU") == null ? "" : mi.inData.get("INOU").trim();
    inLINB = mi.inData.get("LINB") == null ? "" : mi.inData.get("LINB").trim();
    inORNO = mi.inData.get("ORNO") == null ? "" : mi.inData.get("ORNO").trim();
    inPONR = mi.inData.get("PONR") == null ? "" : mi.inData.get("PONR").trim();
    inITNO = mi.inData.get("ITNO") == null ? "" : mi.inData.get("ITNO").trim();
    inTEDS = mi.inData.get("TEDS") == null ? "" : mi.inData.get("TEDS").trim();
    inDPLO = mi.inData.get("DPLO") == null ? "" : mi.inData.get("DPLO").trim();
    inORQT = mi.inData.get("ORQT") == null ? "" : mi.inData.get("ORQT").trim();
    inUNMS = mi.inData.get("UNMS") == null ? "" : mi.inData.get("UNMS").trim();

    DBAction query = database.table("EXTCCL").index("00").build();
    DBContainer container = query.getContainer();
    
    container.set("EXCONO", inCONO);
    container.set("EXLINE", inLINE as int);
    container.set("EXDLIX", inDLIX as long);
    container.set("EXINOU", inINOU as int);

    if (!isValidInput()) {
      return;
    }

    LocalDateTime dateTime = LocalDateTime.now();
    int changedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toInteger();
    int changedTime = dateTime.format(DateTimeFormatter.ofPattern("HHmmss")).toInteger();

    if(!query.readLock(container, {
      LockedResult lockedResult ->
      
      if (!inORNO.isBlank()) {
        lockedResult.set("EXORNO", inORNO.equals("?") ? "" : inORNO);
      }
      if (!inLINB.isBlank()) {
        lockedResult.set("EXLINB", inLINB.equals("?") ? "" : inLINB);
      }
      if (!inPONR.isBlank()) {
        lockedResult.set("EXPONR", inPONR.equals("?") ? 0 : inPONR as int);
      }
      if (!inITNO.isBlank()) {
        lockedResult.set("EXITNO", inITNO.equals("?") ? "" : inITNO);
      }
      if (!inTEDS.isBlank()) {
        lockedResult.set("EXTEDS", inTEDS.equals("?") ? "" : inTEDS);
      }
      if (!inDPLO.isBlank()) {
        lockedResult.set("EXDPLO", inDPLO.equals("?") ? "" : inDPLO);
      }
      if (!inORQT.isBlank()) {
        lockedResult.set("EXORQT", inORQT.equals("?") ? 0 : inORQT as int);
      }
      if (!inUNMS.isBlank()) {
        lockedResult.set("EXUNMS", inUNMS.equals("?") ? "" : inUNMS);
      }
      lockedResult.set("EXCHNO", lockedResult.getInt("EXCHNO") + 1);
      lockedResult.set("EXLMDT", changedDate);
      lockedResult.set("EXCHID", program.getUser());
      lockedResult.update();
    })) {
      mi.error("Record does not exists");
      return;
    }
  }

  /**
   * Validate input fields
   */
  boolean isValidInput() {
    // Check Company
    if (inCONO != 0) {
      if (!this.checkCONO()) {
        mi.error("Company ${inCONO} does not exist");
        return false;
      }
    } else {
        mi.error("Company ${inCONO} is invalid");
        return false;
    }
    
    // Check DLIX in MHDISH
    if (!checkMHDISH()) {
      mi.error("DLIX not found in MHDISH");
      return false;
    }
    
    // Check ORNO
    if (!inORNO.isBlank()) {
      if (!checkOOHEAD()) {
         mi.error("Order number ${inORNO} does not exist in OOHEAD");
        return false;
      }
    }
    
    // Check PONR
    if (!inPONR.isBlank()) {
      if (!checkPONR()) {
         mi.error("Order line number ${inPONR} does not exist in OOLINE");
        return false;
      }
    }
    
    // Check ITNO
    if (!inITNO.isBlank() && !inITNO.equals("?")) {
      if (!checkMITMAS()) {
         mi.error("Item number ${inITNO} does not exist in MITMAS");
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
    
    // Check DPLO
    if (!inDPLO.isBlank()) {
      if (!this.checkDPLO()) {
        mi.error("Stock location ${inDPLO} does not exist");
        return false;
      }
    }
    
    //inLINE
    if (!inLINE.isBlank()) {
      if (!utility.call("CommonUtil", "isValidPositiveInt", inLINE)) {
        mi.error("Invalid number value for ${inLINE}.");
        return false;
      }
    }
    
    //inPONR
    if (!inPONR.isBlank()) {
      if (!utility.call("CommonUtil", "isValidPositiveInt", inPONR)) {
        mi.error("Invalid number value for ${inPONR}.");
        return false;
      }
    }
    
    //inDLIX
    if (!inDLIX.isBlank()) {
      if (!utility.call("CommonUtil", "isValidPositiveInt", inDLIX)) {
        mi.error("Invalid number value for ${inDLIX}.");
        return false;
      }
    }
    
    return true;
  }
  
  /**
  * Validate ORNO from OOHEAD
  */
  boolean checkOOHEAD() {
    DBAction queryOOHEAD = database.table("OOHEAD").index("00").build();
    DBContainer OOHEAD = queryOOHEAD.getContainer();
    OOHEAD.set("OACONO", inCONO);
    OOHEAD.set("OAORNO", inORNO);
    return queryOOHEAD.read(OOHEAD);
  }
  
  /**
  * Validate ITNO from MITMAS
  */
  boolean checkMITMAS() {
    DBAction queryMITMAS = database.table("MITMAS").index("00").build();
    DBContainer MITMAS = queryMITMAS.getContainer();
    MITMAS.set("MMCONO", inCONO);
    MITMAS.set("MMITNO", inITNO);
    return queryMITMAS.read(MITMAS) 
  }
  
  /**
   * Validate CONO from CMNCMP
   */
  boolean checkCONO() {
    DBAction queryCMNCMP = database.table("CMNCMP").index("00").build();
    DBContainer conCMNCMP = queryCMNCMP.getContainer();
    conCMNCMP.set("JICONO", inCONO);
  
    if (!queryCMNCMP.read(conCMNCMP)) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * Validate DLIX from MHDISH
   */
  boolean checkMHDISH() {
    DBAction query = database.table("MHDISH").index("00").selection("OQWHLO").build();
    DBContainer container = query.getContainer();
    container.set("OQCONO", inCONO);
    container.set("OQINOU", Integer.parseInt(inINOU));
    container.set("OQDLIX", Long.parseLong(inDLIX));
    
    if (!query.read(container)) {
      return false;
    } else {
      whlo = container.get("OQWHLO").toString();
      return true;
    }
  }

  /**
   * Validate PNLI from OOLINE
   */
  boolean checkPONR() {
    DBAction query = database.table("OOLINE").index("00").build();
    DBContainer OOLINE = query.getContainer();
    OOLINE.set("OBCONO", inCONO);
    OOLINE.set("OBORNO", inORNO);
    OOLINE.set("OBPONR", Integer.parseInt(inPONR));
    OOLINE.set("OBPOSX", 0);
    
    if (!query.read(OOLINE)) {
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
  
  /**
   * Validate DPLO from MITPCE
   */
  boolean checkDPLO() {
    DBAction query = database.table("MITPCE").index("00").build();
    DBContainer container = query.getContainer();
    container.set("MSCONO", inCONO);
    container.set("MSWHLO", whlo);
    container.set("MSWHSL", inDPLO);
    if (!query.read(container)) {
      return false;
    } else {
      return true;
    }
  }
  
}
