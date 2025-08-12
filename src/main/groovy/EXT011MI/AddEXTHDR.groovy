/**
 * @Name: AddEXTHDR.EXTHDR
 * @Description: Add record on table EXTHDR
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     250704   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddEXTHDR extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO, inLADT, inTPND, inWVAR, inARDT, inRJDT, inCHNO, inRGDT, inRGTM, inLMDT;
  private String inDIVI;
  private String inWHLO;
  private String inLDID;
  private String inPTID;
  private String inCTTP;
  private String inMKTP;
  private String inRFNO;
  private String inRTNO;
  private String inSTAT;
  private String inMKGD;
  private String inSTNM;
  private String inSTCD;
  private String inSDNM;
  private String inSDCD;
  private String inLATM;
  private String inARTM;
  private String inRJTM;
  private String inCHID;

  public AddEXTHDR(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
    this.mi = mi;
    this.utility = utility;
    this.logger = logger;
    this.program = program;
    this.miCaller = miCaller;
    this.database = database;
  }

  public void main() {
    inCONO = mi.in.get("CONO") == null ? program.LDAZD.CONO as int : mi.in.get("CONO") as Integer;
    inDIVI = mi.inData.get("DIVI") == null ? "" : mi.inData.get("DIVI").trim();
    inWHLO = mi.inData.get("WHLO") == null ? "" : mi.inData.get("WHLO").trim();
    inLDID = mi.inData.get("LDID") == null ? "" : mi.inData.get("LDID").trim();
    inPTID = mi.inData.get("PTID") == null ? "" : mi.inData.get("PTID").trim();
    inLADT = mi.in.get("LADT") == null ? 0 : mi.in.get("LADT") as Integer;
    inLATM = mi.inData.get("LATM") == null ? "0" : mi.inData.get("LATM").trim();
    inCTTP = mi.inData.get("CTTP") == null ? "" : mi.inData.get("CTTP").trim();
    inMKTP = mi.inData.get("MKTP") == null ? "" : mi.inData.get("MKTP").trim();
    inRFNO = mi.inData.get("RFNO") == null ? "" : mi.inData.get("RFNO").trim();
    inRTNO = mi.inData.get("RTNO") == null ? "" : mi.inData.get("RTNO").trim();
    inSTAT = mi.inData.get("STAT") == null ? "" : mi.inData.get("STAT").trim();
    inTPND = mi.in.get("TPND") == null ? 0 : mi.in.get("TPND") as Integer;
    inWVAR = mi.in.get("WVAR") == null ? 0 : mi.in.get("WVAR") as Integer;
    inMKGD = mi.inData.get("MKGD") == null ? "" : mi.inData.get("MKGD").trim();
    inSTNM = mi.inData.get("STNM") == null ? "" : mi.inData.get("STNM").trim();
    inSTCD = mi.inData.get("STCD") == null ? "" : mi.inData.get("STCD").trim();
    inSDNM = mi.inData.get("SDNM") == null ? "" : mi.inData.get("SDNM").trim();
    inSDCD = mi.inData.get("SDCD") == null ? "" : mi.inData.get("SDCD").trim();
    inARDT = mi.in.get("ARDT") == null ? 0 : mi.in.get("ARDT") as Integer;
    inARTM = mi.inData.get("ARTM") == null ? "0" : mi.inData.get("ARTM").trim();
    inRJDT = mi.in.get("RJDT") == null ? 0 : mi.in.get("RJDT") as Integer;
    inRJTM = mi.inData.get("RJTM") == null ? "0" : mi.inData.get("RJTM").trim();
    
    // Check Company
    if(inCONO == 0){
      inCONO = (Integer) program.LDAZD.CONO;
    }
    
    // Validate inputs
    if (!this.isValidInput()) {
      return;
    }
    
    DBAction query = database.table("EXTHDR").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);

    if (query.read(container)) {
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

    container.set("EXPTID", inPTID);
    container.set("EXLADT", inLADT);
    container.set("EXLATM", inLATM.isBlank() ? 0 : inLATM as int);
    container.set("EXCTTP", inCTTP);
    container.set("EXMKTP", inMKTP);
    container.set("EXRFNO", inRFNO);
    container.set("EXRTNO", inRTNO);
    container.set("EXSTAT", inSTAT);
    container.set("EXTPND", inTPND);
    container.set("EXWVAR", inWVAR);
    container.set("EXMKGD", inMKGD);
    container.set("EXSTNM", inSTNM);
    container.set("EXSTCD", inSTCD);
    container.set("EXSDNM", inSDNM);
    container.set("EXSDCD", inSDCD);
    container.set("EXARDT", inARDT);
    container.set("EXARTM", inARTM.isBlank() ? 0 : inARTM as int);
    container.set("EXRJDT", inRJDT);
    container.set("EXRJTM", inRJTM.isBlank() ? 0 : inRJTM as int);
    container.set("EXCHID", inCHID);
    container.set("EXCHNO", inCHNO);
    container.set("EXRGDT", inRGDT);
    container.set("EXRGTM", inRGTM);
    container.set("EXLMDT", inLMDT);
    container.set("EXLMTM", entryTime);

    query.insert(container);
  }
  
  boolean isValidInput() {
    // Check WHLO
    if (!inWHLO.isBlank()) {
      if (!this.checkWHLO()) {
        mi.error("Warehouse ${inWHLO} does not exist");
        return false;
      }
    }
    
    // Check inLADT
    if (inLADT != 0) {
      if (!utility.call("DateUtil", "isDateValid", inLADT.toString(), "yyyyMMdd")) {
        mi.error("Invalid DATE input for ${inLADT}. It must be in the format yyyyMMdd.");
        return false;
      }
    }
    
    // Check inLATM
    if (!inLATM.isBlank()) {
      if (!utility.call("CommonUtil", "isValidTime", inLATM.padLeft(6, '0'))) {
        mi.error("Invalid TIME input for ${inLATM}. It must be in the format HHmmss.");
        return false;
      }
    }
    
    // Check inARDT
    if (inARDT != 0) {
      if (!utility.call("DateUtil", "isDateValid", inARDT.toString(), "yyyyMMdd")) {
        mi.error("Invalid DATE input for ${inARDT}. It must be in the format yyyyMMdd.");
        return false;
      }
    }
    
    // Check inARTM
    if (!inARTM.isBlank()) {
      if (!utility.call("CommonUtil", "isValidTime", inARTM.padLeft(6, '0'))) {
        mi.error("Invalid TIME input for ${inARTM}. It must be in the format HHmmss.");
        return false;
      }
    }
    
    // Check inRJDT
    if (inRJDT != 0) {
      if (!utility.call("DateUtil", "isDateValid", inRJDT.toString(), "yyyyMMdd")) {
        mi.error("Invalid DATE input for ${inRJDT}. It must be in the format yyyyMMdd.");
        return false;
      }
    }
    
    // Check inRJTM
    if (!inRJTM.isBlank()) {
      if (!utility.call("CommonUtil", "isValidTime", inRJTM.padLeft(6, '0'))) {
        mi.error("Invalid TIME input for ${inRJTM}. It must be in the format HHmmss.");
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

}