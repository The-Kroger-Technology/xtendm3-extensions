/**
 * @Name: AddEXTDLG.EXTDLG
 * @Description: Add record on table EXTDL1 and EXTDL2
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddEXTDLG extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  
  private int inCONO, inDSEQ, inPRSD, inPRED, inBODO, inENDT, inEODO, inSTDT, inEVDT,
    inARDT, inNOWT, inDPDT, inDTEM, inDEID, inSIRD, inBNSC, inCDAT, inACDT, inGFAD, inCHNO, inRGDT, inRGTM, inLMDT;
  private double inGALS;
  private String inDIVI;
  private String inWHLO;
  private String inLDID;
  private String inPDST;
  private String inPRST;
  private String inPRET;
  private String inENTM;
  private String inSTTM;
  private String inEVTM;
  private String inACTD;
  private String inARTM;
  private String inNETW;
  private String inDPTM;    
  private String inCTIM;    
  private String inACTM;    
  private String inGFAT;
  private String inDECL;
  private String inDENM;
  private String inDRMD;
  private String inSTNO;
  private String inPRMD;
  private String inLTID;
  private String inHAID;
  private String inHANI;
  private String inHANM;
  private String inTRID;
  private String inTRFN;
  private String inTRLN;
  private String inTRPN;
  private String inTRSN;
  private String inMLOC;
  private String inPV01;
  private String inPV02;
  private String inPV03;
  private String inPV04;
  private String inPV05;
  private String inPV06;
  private String inPV07;
  private String inPV08;
  private String inPV09;
  private String inPV10;
  private String inCHID;

  public AddEXTDLG(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inDSEQ = mi.in.get("DSEQ") == null ? 0 : mi.in.get("DSEQ") as Integer;
    inPDST = mi.inData.get("PDST") == null ? "" : mi.inData.get("PDST").trim();
    inPRSD = mi.in.get("PRSD") == null ? 0 : mi.in.get("PRSD") as Integer;
    inPRST = mi.inData.get("PRST") == null ? "0" : mi.inData.get("PRST").trim();
    inPRET = mi.inData.get("PRET") == null ? "0" : mi.inData.get("PRET").trim();
    inENTM = mi.inData.get("ENTM") == null ? "0" : mi.inData.get("ENTM").trim();
    inSTTM = mi.inData.get("STTM") == null ? "0" : mi.inData.get("STTM").trim();
    inEVTM = mi.inData.get("EVTM") == null ? "0" : mi.inData.get("EVTM").trim();
    inARTM = mi.inData.get("ARTM") == null ? "0" : mi.inData.get("ARTM").trim();
    inDPTM = mi.inData.get("DPTM") == null ? "0" : mi.inData.get("DPTM").trim();
    inCTIM = mi.inData.get("CTIM") == null ? "0" : mi.inData.get("CTIM").trim();
    inACTM = mi.inData.get("ACTM") == null ? "0" : mi.inData.get("ACTM").trim();
    inGFAT = mi.inData.get("GFAT") == null ? "0" : mi.inData.get("GFAT").trim();
    inPRED = mi.in.get("PRED") == null ? 0 : mi.in.get("PRED") as Integer;
    inBODO = mi.in.get("BODO") == null ? 0 : mi.in.get("BODO") as Integer;
    inENDT = mi.in.get("ENDT") == null ? 0 : mi.in.get("ENDT") as Integer;
    inEODO = mi.in.get("EODO") == null ? 0 : mi.in.get("EODO") as Integer;
    inSTDT = mi.in.get("STDT") == null ? 0 : mi.in.get("STDT") as Integer;
    inEVDT = mi.in.get("EVDT") == null ? 0 : mi.in.get("EVDT") as Integer;
    inACTD = mi.inData.get("ACTD") == null ? "" : mi.inData.get("ACTD").trim();
    inARDT = mi.in.get("ARDT") == null ? 0 : mi.in.get("ARDT") as Integer;
    inNETW = mi.inData.get("NETW") == null ? "0.0" : mi.inData.get("NETW").trim();
    inNOWT = mi.in.get("NOWT") == null ? 0 : mi.in.get("NOWT") as Integer;
    inDPDT = mi.in.get("DPDT") == null ? 0 : mi.in.get("DPDT") as Integer;
    inDTEM = mi.in.get("DTEM") == null ? 0 : mi.in.get("DTEM") as Integer;
    inDECL = mi.inData.get("DECL") == null ? "" : mi.inData.get("DECL").trim();
    inDEID = mi.in.get("DEID") == null ? 0 : mi.in.get("DEID") as Integer;
    inDENM = mi.inData.get("DENM") == null ? "" : mi.inData.get("DENM").trim();
    inDRMD = mi.inData.get("DRMD") == null ? "" : mi.inData.get("DRMD").trim();
    inSIRD = mi.in.get("SIRD") == null ? 0 : mi.in.get("SIRD") as Integer;
    inSTNO = mi.inData.get("STNO") == null ? "" : mi.inData.get("STNO").trim();
    inPRMD = mi.inData.get("PRMD") == null ? "" : mi.inData.get("PRMD").trim();
    inBNSC = mi.in.get("BNSC") == null ? 0 : mi.in.get("BNSC") as Integer;
    inGALS = mi.in.get("GALS") == null ? 0 : mi.in.get("GALS") as Double;
    inCDAT = mi.in.get("CDAT") == null ? 0 : mi.in.get("CDAT") as Integer;
    inACDT = mi.in.get("ACDT") == null ? 0 : mi.in.get("ACDT") as Integer;
    inGFAD = mi.in.get("GFAD") == null ? 0 : mi.in.get("GFAD") as Integer;
    inLTID = mi.inData.get("LTID") == null ? "" : mi.inData.get("LTID").trim();
    inHAID = mi.inData.get("HAID") == null ? "" : mi.inData.get("HAID").trim();
    inHANI = mi.inData.get("HANI") == null ? "" : mi.inData.get("HANI").trim();
    inHANM = mi.inData.get("HANM") == null ? "" : mi.inData.get("HANM").trim();
    inTRID = mi.inData.get("TRID") == null ? "" : mi.inData.get("TRID").trim();
    inTRFN = mi.inData.get("TRFN") == null ? "" : mi.inData.get("TRFN").trim();
    inTRLN = mi.inData.get("TRLN") == null ? "" : mi.inData.get("TRLN").trim();
    inTRPN = mi.inData.get("TRPN") == null ? "" : mi.inData.get("TRPN").trim();
    inTRSN = mi.inData.get("TRSN") == null ? "" : mi.inData.get("TRSN").trim();
    inMLOC = mi.inData.get("MLOC") == null ? "" : mi.inData.get("MLOC").trim();
    inPV01 = mi.inData.get("PV01") == null ? "" : mi.inData.get("PV01") as char;
    inPV02 = mi.inData.get("PV02") == null ? "" : mi.inData.get("PV02") as char;
    inPV03 = mi.inData.get("PV03") == null ? "" : mi.inData.get("PV03") as char;
    inPV04 = mi.inData.get("PV04") == null ? "" : mi.inData.get("PV04") as char;
    inPV05 = mi.inData.get("PV05") == null ? "" : mi.inData.get("PV05") as char;
    inPV06 = mi.inData.get("PV06") == null ? "" : mi.inData.get("PV06") as char;
    inPV07 = mi.inData.get("PV07") == null ? "" : mi.inData.get("PV07") as char;
    inPV08 = mi.inData.get("PV08") == null ? "" : mi.inData.get("PV08") as char;
    inPV09 = mi.inData.get("PV09") == null ? "" : mi.inData.get("PV09") as char;
    inPV10 = mi.inData.get("PV10") == null ? "" : mi.inData.get("PV10") as char;

    if (!isValidInput()) {
      return;
    }
    
    DBAction query = database.table("EXTDL1").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    container.set("EXDSEQ", inDSEQ);

    if (query.read(container)) {
      mi.error("Record already exists in EXTDL1");
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

    container.set("EXPDST", inPDST);
    container.set("EXPRSD", inPRSD);
    container.set("EXPRST", inPRST.isBlank() ? 0 : inPRST as int);
    container.set("EXPRED", inPRED);
    container.set("EXPRET", inPRET.isBlank() ? 0 : inPRET as int);
    container.set("EXBODO", inBODO);
    container.set("EXENDT", inENDT);
    container.set("EXENTM", inENTM.isBlank() ? 0 : inENTM as int);
    container.set("EXEODO", inEODO);
    container.set("EXSTDT", inSTDT);
    container.set("EXSTTM", inSTTM.isBlank() ? 0 : inSTTM as int);
    container.set("EXEVDT", inEVDT);
    container.set("EXEVTM", inEVTM.isBlank() ? 0 : inEVTM as int);
    container.set("EXACTD", inACTD);
    container.set("EXARDT", inARDT);
    container.set("EXARTM", inARTM.isBlank() ? 0 : inARTM as int);
    container.set("EXNETW", inNETW.isBlank() ? 0 : inNETW as double);
    container.set("EXNOWT", inNOWT);
    container.set("EXDPDT", inDPDT);
    container.set("EXDPTM", inDPTM.isBlank() ? 0 : inDPTM as int);
    container.set("EXDTEM", inDTEM);
    container.set("EXDECL", inDECL);
    container.set("EXDEID", inDEID);
    container.set("EXDENM", inDENM);
    container.set("EXDRMD", inDRMD);
    container.set("EXSIRD", inSIRD);
    container.set("EXSTNO", inSTNO);
    container.set("EXPRMD", inPRMD);
    container.set("EXBNSC", inBNSC);
    container.set("EXGALS", inGALS);
    container.set("EXCDAT", inCDAT);
    container.set("EXCTIM", inCTIM.isBlank() ? 0 : inCTIM as int);
    container.set("EXACDT", inACDT);
    container.set("EXACTM", inACTM.isBlank() ? 0 : inACTM as int);
    container.set("EXGFAD", inGFAD);
    container.set("EXGFAT", inGFAT.isBlank() ? 0 : inGFAT as int);
    container.set("EXLTID", inLTID);
    container.set("EXHAID", inHAID);
    container.set("EXHANI", inHANI);
    container.set("EXCHID", inCHID);
    container.set("EXCHNO", inCHNO);
    container.set("EXRGDT", inRGDT);
    container.set("EXRGTM", inRGTM);
    container.set("EXLMDT", inLMDT);
    container.set("EXLMTM", entryTime);
    
    query.insert(container);
    
    DBAction query1 = database.table("EXTDL2").index("00").selectAllFields().build();
    DBContainer container1 = query1.getContainer();
    container1.set("EXCONO", inCONO);
    container1.set("EXDIVI", inDIVI);
    container1.set("EXWHLO", inWHLO);
    container1.set("EXLDID", inLDID);
    container1.set("EXDSEQ", inDSEQ);

    if (query1.read(container1)) {
      mi.error("Record already exists  in EXTDL2");
      return;
    }
    
    container1.set("EXHANM", inHANM);
    container1.set("EXTRID", inTRID);
    container1.set("EXTRFN", inTRFN);
    container1.set("EXTRLN", inTRLN);
    container1.set("EXTRPN", inTRPN);
    container1.set("EXTRSN", inTRSN);
    container1.set("EXMLOC", inMLOC);
    container1.set("EXPV01", inPV01.toUpperCase());
    container1.set("EXPV02", inPV02.toUpperCase());
    container1.set("EXPV03", inPV03.toUpperCase());
    container1.set("EXPV04", inPV04.toUpperCase());
    container1.set("EXPV05", inPV05.toUpperCase());
    container1.set("EXPV06", inPV06.toUpperCase());
    container1.set("EXPV07", inPV07.toUpperCase());
    container1.set("EXPV08", inPV08.toUpperCase());
    container1.set("EXPV09", inPV09.toUpperCase());
    container1.set("EXPV10", inPV10.toUpperCase());
    container1.set("EXCHID", inCHID);
    container1.set("EXCHNO", inCHNO);
    container1.set("EXRGDT", inRGDT);
    container1.set("EXRGTM", inRGTM);
    container1.set("EXLMDT", inLMDT);
    container1.set("EXLMTM", entryTime);
    
    query1.insert(container1);
    
  }

  boolean isValidInput() {
    // Check WHLO
    if (!inWHLO.isBlank()) {
      if (!this.checkWHLO()) {
        mi.error("Warehouse ${inWHLO} does not exist");
        return false;
      }
    }
    
    // Check inPRSD
    if (inPRSD != 0) {
      if (!utility.call("DateUtil", "isDateValid", inPRSD.toString(), "yyyyMMdd")) {
        mi.error("Invalid DATE input for ${inPRSD}. It must be in the format yyyyMMdd.");
        return false;
      }
    }
    
    // Check inPRST
    if (!inPRST.isBlank()) {
      if (!utility.call("DateUtil", "isTimeValid", inPRST.padLeft(6, '0'), "HHmmss")) {
        mi.error("Invalid TIME input for ${inPRST}. It must be in the format HHmmss.");
        return false;
      }
    } 
    
    // Check inPRED
    if (inPRED != 0) {
      if (!utility.call("DateUtil", "isDateValid", inPRED.toString(), "yyyyMMdd")) {
        mi.error("Invalid DATE input for ${inPRED}. It must be in the format yyyyMMdd.");
        return false;
      }
    }
    
    // Check inPRET
    if (!inPRET.isBlank()) {
      if (!utility.call("DateUtil", "isTimeValid", inPRET.padLeft(6, '0'), "HHmmss")) {
        mi.error("Invalid TIME input for ${inPRET}. It must be in the format HHmmss.");
        return false;
      }
    } 
    
    // Check inENDT
    if (inENDT != 0) {
      if (!utility.call("DateUtil", "isDateValid", inENDT.toString(), "yyyyMMdd")) {
        mi.error("Invalid DATE input for ${inENDT}. It must be in the format yyyyMMdd.");
        return false;
      }
    }
    
    // Check inENTM
    if (!inENTM.isBlank()) {
      if (!utility.call("DateUtil", "isTimeValid", inENTM.padLeft(6, '0'), "HHmmss")) {
        mi.error("Invalid TIME input for ${inENTM}. It must be in the format HHmmss.");
        return false;
      }
    } 

    // Check inSTDT
    if (inSTDT != 0) {
      if (!utility.call("DateUtil", "isDateValid", inSTDT.toString(), "yyyyMMdd")) {
        mi.error("Invalid DATE input for ${inSTDT}. It must be in the format yyyyMMdd.");
        return false;
      }
    }
    
    // Check inSTTM
    if (!inSTTM.isBlank()) {
      if (!utility.call("DateUtil", "isTimeValid", inSTTM.padLeft(6, '0'), "HHmmss")) {
        mi.error("Invalid TIME input for ${inSTTM}. It must be in the format HHmmss.");
        return false;
      }
    } 
    
    // Check inEVDT
    if (inEVDT != 0) {
      if (!utility.call("DateUtil", "isDateValid", inEVDT.toString(), "yyyyMMdd")) {
        mi.error("Invalid DATE input for ${inEVDT}. It must be in the format yyyyMMdd.");
        return false;
      }
    }
    
    // Check inEVTM
    if (!inEVTM.isBlank()) {
      if (!utility.call("DateUtil", "isTimeValid", inEVTM.padLeft(6, '0'), "HHmmss")) {
        mi.error("Invalid TIME input for ${inEVTM}. It must be in the format HHmmss.");
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
      if (!utility.call("DateUtil", "isTimeValid", inARTM.padLeft(6, '0'), "HHmmss")) {
        mi.error("Invalid TIME input for ${inARTM}. It must be in the format HHmmss.");
        return false;
      }
    }
    
    // Check inDPDT
    if (inDPDT != 0) {
      if (!utility.call("DateUtil", "isDateValid", inDPDT.toString(), "yyyyMMdd")) {
        mi.error("Invalid DATE input for ${inDPDT}. It must be in the format yyyyMMdd.");
        return false;
      }
    }
    
    // Check inDPTM
    if (!inDPTM.isBlank()) {
      if (!utility.call("DateUtil", "isTimeValid", inDPTM.padLeft(6, '0'), "HHmmss")) {
        mi.error("Invalid TIME input for ${inDPTM}. It must be in the format HHmmss.");
        return false;
      }
    }
    
     // Check inCDAT
    if (inCDAT != 0) {
      if (!utility.call("DateUtil", "isDateValid", inCDAT.toString(), "yyyyMMdd")) {
        mi.error("Invalid DATE input for ${inCDAT}. It must be in the format yyyyMMdd.");
        return false;
      }
    }
    
    // Check inCTIM
    if (!inCTIM.isBlank()) {
      if (!utility.call("DateUtil", "isTimeValid", inCTIM.padLeft(6, '0'), "HHmmss")) {
        mi.error("Invalid TIME input for ${inCTIM}. It must be in the format HHmmss.");
        return false;
      }
    }
    
    // Check inACDT
    if (inACDT != 0) {
      if (!utility.call("DateUtil", "isDateValid", inACDT.toString(), "yyyyMMdd")) {
        mi.error("Invalid DATE input for ${inACDT}. It must be in the format yyyyMMdd.");
        return false;
      }
    }
    
    // Check inACTM
    if (!inACTM.isBlank()) {
      if (!utility.call("DateUtil", "isTimeValid", inACTM.padLeft(6, '0'), "HHmmss")) {
        mi.error("Invalid TIME input for ${inACTM}. It must be in the format HHmmss.");
        return false;
      }
    }
    
    // Check inGFAD
    if (inGFAD != 0) {
      if (!utility.call("DateUtil", "isDateValid", inGFAD.toString(), "yyyyMMdd")) {
        mi.error("Invalid DATE input for ${inGFAD}. It must be in the format yyyyMMdd.");
        return false;
      }
    }
    
    // Check inGFAT
    if (!inGFAT.isBlank()) {
      if (!utility.call("DateUtil", "isTimeValid", inGFAT.padLeft(6, '0'), "HHmmss")) {
        mi.error("Invalid TIME input for ${inGFAT}. It must be in the format HHmmss.");
        return false;
      }
    }
    
    //Check inPV01
    if (!inPV01.isBlank()) {
      if (!utility.call("CommonUtil", "isValidFlag", inPV01, "Y", "N")) {
        mi.error("Invalid input value for ${inPV01}. Must be Y or N.");
        return false;
      }
    }
    
    //Check inPV02
    if (!inPV02.isBlank()) {
      if (!utility.call("CommonUtil", "isValidFlag", inPV02, "Y", "N")) {
        mi.error("Invalid input value for ${inPV02}. Must be Y or N.");
        return false;
      }
    }
    
    //Check inPV03
    if (!inPV03.isBlank()) {
      if (!utility.call("CommonUtil", "isValidFlag", inPV03, "Y", "N")) {
        mi.error("Invalid input value for ${inPV03}. Must be Y or N.");
        return false;
      }
    }
    
    //Check inPV04
    if (!inPV04.isBlank()) {
      if (!utility.call("CommonUtil", "isValidFlag", inPV04, "Y", "N")) {
        mi.error("Invalid input value for ${inPV04}. Must be Y or N.");
        return false;
      }
    }
    
    //Check inPV05
    if (!inPV05.isBlank()) {
      if (!utility.call("CommonUtil", "isValidFlag", inPV05, "Y", "N")) {
        mi.error("Invalid input value for ${inPV05}. Must be Y or N.");
        return false;
      }
    }
    
    //Check inPV06
    if (!inPV06.isBlank()) {
      if (!utility.call("CommonUtil", "isValidFlag", inPV06, "Y", "N")) {
        mi.error("Invalid input value for ${inPV06}. Must be Y or N.");
        return false;
      }
    }
    
    //Check inPV07
    if (!inPV07.isBlank()) {
      if (!utility.call("CommonUtil", "isValidFlag", inPV07, "Y", "N")) {
        mi.error("Invalid input value for ${inPV07}. Must be Y or N.");
        return false;
      }
    }
    
    //Check inPV08
    if (!inPV08.isBlank()) {
      if (!utility.call("CommonUtil", "isValidFlag", inPV08, "Y", "N")) {
        mi.error("Invalid input value for ${inPV08}. Must be Y or N.");
        return false;
      }
    }
    
    //Check inPV09
    if (!inPV09.isBlank()) {
      if (!utility.call("CommonUtil", "isValidFlag", inPV09, "Y", "N")) {
        mi.error("Invalid input value for ${inPV09}. Must be Y or N.");
        return false;
      }
    }
    
    //Check inPV10
    if (!inPV10.isBlank()) {
      if (!utility.call("CommonUtil", "isValidFlag", inPV10, "Y", "N")) {
        mi.error("Invalid input value for ${inPV10}. Must be Y or N.");
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