/**
 * @Name: AddEXTCCL.EXTCCL
 * @Description: Add record on table EXTCCL
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddEXTCCL extends ExtendM3Transaction {
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
  private String inCHID;
  private int inCHNO;
  private int inRGDT;
  private int inRGTM;
  private int inLMDT;

  public AddEXTCCL(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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

    DBAction query = database.table("EXTCCL").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXLINE", inLINE as int);
    container.set("EXDLIX", inDLIX as int);
    container.set("EXINOU", inINOU as int);

    if (query.read(container)) {
      mi.error("Record already exists");
      return;
    }

    if (!isValidInput()) {
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
    
    container.set("EXORNO", inORNO);
    container.set("EXLINB", inLINB);
    container.set("EXPONR", !inPONR.isBlank() ? inPONR as int : 0);
    container.set("EXITNO", inITNO);
    container.set("EXTEDS", inTEDS);
    container.set("EXDPLO", inDPLO);
    container.set("EXORQT", !inORQT.isBlank() ? inORQT as int : 0);
    container.set("EXUNMS", inUNMS);
    container.set("EXCHID", inCHID);
    container.set("EXCHNO", inCHNO);
    container.set("EXRGDT", inRGDT);
    container.set("EXRGTM", inRGTM);
    container.set("EXLMDT", inLMDT);

    query.insert(container);
  }

  /**
   * Validate input fields
   */
  boolean isValidInput() {
    // Check ORNO
    if (!inORNO.isBlank()) {
      if (!checkOOHEAD()) {
         mi.error("Order number ${inORNO} does not exist in OOHEAD");
        return false;
      }
    }
    
    // Check ITNO
    if (!inITNO.isBlank()) {
      if (!checkMITMAS()) {
         mi.error("Item number ${inITNO} does not exist in MITMAS");
        return false;
      }
    }
    
    return true;
  }
  
  /**
  * Validate ORNO from OOHEAD
  */
  boolean checkOOHEAD() {
    DBAction OOHEAD_query = database.table("OOHEAD").index("00").selectAllFields().build();
    DBContainer OOHEAD = OOHEAD_query.getContainer();
    OOHEAD.set("OACONO", inCONO);
    OOHEAD.set("OAORNO", inORNO);
    return OOHEAD_query.read(OOHEAD);
  }
  
  /**
  * Validate ITNO from MITMAS
  */
  boolean checkMITMAS() {
    DBAction MITMAS_query = database.table("MITMAS").index("00").build();
    DBContainer MITMAS = MITMAS_query.getContainer();
    MITMAS.set("MMCONO", inCONO);
    MITMAS.set("MMITNO", inITNO);
    return MITMAS_query.read(MITMAS)
    
  }

}