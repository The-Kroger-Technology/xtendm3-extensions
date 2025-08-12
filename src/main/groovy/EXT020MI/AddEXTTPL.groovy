/**
 * @Name: AddEXTTPL.EXTTPL
 * @Description: Add record on table EXTTPL
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddEXTTPL extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO, inPSEQ, inEVDT,inDPDT, inARDT, inEPDT, inMEVD;
  private String inDIVI;
  private String inWHLO;
  private String inLDID;
  private String inMNUM;
  private String inFNWT;
  private String inGRWT;
  private String inEVTM;
  private String inDPTM;
  private String inARTM;
  private String inEPTM;
  private String inMEVT;
  private String inSTNO;
  private String inTRWT;
  private String inMKGD;
  private String inSTIM;
  private String inSTIL;
  private String inSUCL;
  private String inSUID;
  private String inSUNM;
  private String inOSMW;
  private String inAUTO;
  private String inPDCL;
  private String inPDID;
  private String inPDNM;
  private String inPDCD;
  private String inBTUN;
  private String inDIVN;
  private String inGALS;
  private String inPUST;
  private String inLMTM;
  private String inCHID;
  private int inCHNO;
  private int inRGDT;
  private int inRGTM;
  private int inLMDT;

  public AddEXTTPL(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inPSEQ = mi.in.get("PSEQ") == null ? 0 : mi.in.get("PSEQ") as Integer;
    inMNUM = mi.inData.get("MNUM") == null ? "" : mi.inData.get("MNUM").trim();
    inEVDT = mi.in.get("EVDT") == null ? 0: mi.in.get("EVDT") as Integer;
    inEVTM = mi.inData.get("EVTM") == null ? "0" : mi.inData.get("EVTM").trim();
    inDPDT = mi.in.get("DPDT") == null ? 0: mi.in.get("DPDT") as Integer;
    inDPTM = mi.inData.get("DPTM") == null ? "0" : mi.inData.get("DPTM").trim();
    inARDT = mi.in.get("ARDT") == null ? 0: mi.in.get("ARDT") as Integer;
    inARTM = mi.inData.get("ARTM") == null ? "0" : mi.inData.get("ARTM").trim();
    inFNWT = mi.inData.get("FNWT") == null ? "0.0" : mi.inData.get("FNWT").trim();
    inGRWT = mi.inData.get("GRWT") == null ? "0.0" : mi.inData.get("GRWT").trim();
    inSTNO = mi.inData.get("STNO") == null ? "" : mi.inData.get("STNO").trim();
    inTRWT = mi.inData.get("TRWT") == null ? "0.0" : mi.inData.get("TRWT").trim();
    inMKGD = mi.inData.get("MKGD") == null ? "" : mi.inData.get("MKGD").trim();
    inSTIM = mi.inData.get("STIM") == null ? "" : mi.inData.get("STIM").trim();
    inSTIL = mi.inData.get("STIL") == null ? "" : mi.inData.get("STIL").trim();
    inEPDT = mi.in.get("EPDT") == null ? 0 : mi.in.get("EPDT") as Integer;
    inEPTM = mi.inData.get("EPTM") == null ? "0" : mi.inData.get("EPTM").trim();
    inSUCL = mi.inData.get("SUCL") == null ? "" : mi.inData.get("SUCL").trim();
    inSUID = mi.inData.get("SUID") == null ? "" : mi.inData.get("SUID").trim();
    inSUNM = mi.inData.get("SUNM") == null ? "" : mi.inData.get("SUNM").trim();
    inOSMW = mi.inData.get("OSMW") == null ? "" : mi.inData.get("OSMW").trim();
    inMEVD = mi.in.get("MEVD") == null ? 0: mi.in.get("MEVD") as Integer;
    inMEVT = mi.inData.get("MEVT") == null ? "0" : mi.inData.get("MEVT").trim();
    inAUTO = mi.inData.get("AUTO") == null ? "" : mi.inData.get("AUTO").trim();
    inPDCL = mi.inData.get("PDCL") == null ? "" : mi.inData.get("PDCL").trim();
    inPDID = mi.inData.get("PDID") == null ? "" : mi.inData.get("PDID").trim();
    inPDNM = mi.inData.get("PDNM") == null ? "" : mi.inData.get("PDNM").trim();
    inPDCD = mi.inData.get("PDCD") == null ? "" : mi.inData.get("PDCD").trim();
    inBTUN = mi.inData.get("BTUN") == null ? "" : mi.inData.get("BTUN").trim();
    inDIVN = mi.inData.get("DIVN") == null ? "" : mi.inData.get("DIVN").trim();
    inGALS = mi.inData.get("GALS") == null ? "" : mi.inData.get("GALS").trim();
    inPUST = mi.inData.get("PUST") == null ? "" : mi.inData.get("PUST").trim();

    if (!isValidInput()) {
      return;
    }
    
    DBAction query = database.table("EXTTPL").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    container.set("EXPSEQ", inPSEQ);

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

    container.set("EXMNUM", inMNUM);
    container.set("EXEVDT", inEVDT);
    container.set("EXEVTM", inEVTM.isBlank() ? 0 : inEVTM as int);
    container.set("EXDPDT", inDPDT);
    container.set("EXDPTM", inDPTM.isBlank() ? 0 : inDPTM as int);
    container.set("EXARDT", inARDT);
    container.set("EXARTM", inARTM.isBlank() ? 0 : inARTM as int);
    container.set("EXFNWT", inFNWT.isBlank() ? 0 : inFNWT as double);
    container.set("EXGRWT", inGRWT.isBlank() ? 0 : inGRWT as double);
    container.set("EXSTNO", inSTNO);
    container.set("EXTRWT", inTRWT.isBlank() ? 0 : inTRWT as double);
    container.set("EXMKGD", inMKGD);
    container.set("EXSTIM", inSTIM);
    container.set("EXSTIL", inSTIL);
    container.set("EXEPDT", inEPDT);
    container.set("EXEPTM", inEPTM.isBlank() ? 0 : inEPTM as int);
    container.set("EXSUCL", inSUCL);
    container.set("EXSUID", inSUID);
    container.set("EXSUNM", inSUNM);
    container.set("EXOSMW", inOSMW);
    container.set("EXMEVD", inMEVD);
    container.set("EXMEVT", inMEVT.isBlank() ? 0 : inMEVT as int);
    container.set("EXAUTO", inAUTO);
    container.set("EXPDCL", inPDCL);
    container.set("EXPDID", inPDID);
    container.set("EXPDNM", inPDNM);
    container.set("EXPDCD", inPDCD);
    container.set("EXBTUN", inBTUN);
    container.set("EXDIVN", inDIVN);
    container.set("EXGALS", inGALS);
    container.set("EXPUST", inPUST);
    container.set("EXLMTM", inLMTM);
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
    
    // Check inEVDT
    if (inEVDT != 0) {
      if (!utility.call("DateUtil", "isDateValid", inEVDT.toString(), "yyyyMMdd")) {
        mi.error("Invalid DATE input for ${inEVDT}. It must be in the format yyyyMMdd.");
        return false;
      }
    }
    
    // Check inEVTM
    if (!inEVTM.isBlank()) {
      if (!utility.call("CommonUtil", "isValidTime", inEVTM.padLeft(6, '0'))) {
        mi.error("Invalid TIME input for ${inEVTM}. It must be in the format HHmmss.");
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
      if (!utility.call("CommonUtil", "isValidTime", inDPTM.padLeft(6, '0'))) {
        mi.error("Invalid TIME input for ${inDPTM}. It must be in the format HHmmss.");
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
    
   // Check inMEVD
    if (inMEVD != 0) {
      if (!utility.call("DateUtil", "isDateValid", inMEVD.toString(), "yyyyMMdd")) {
        mi.error("Invalid DATE input for ${inMEVD}. It must be in the format yyyyMMdd.");
        return false;
      }
    }
    
    // Check inMEVT
    if (!inMEVT.isBlank()) {
      if (!utility.call("CommonUtil", "isValidTime", inMEVT.padLeft(6, '0'))) {
        mi.error("Invalid TIME input for ${inMEVT}. It must be in the format HHmmss.");
        return false;
      }
    }  
    
    // Check inEPDT
    if (inEPDT != 0) {
      if (!utility.call("DateUtil", "isDateValid", inEPDT.toString(), "yyyyMMdd")) {
        mi.error("Invalid DATE input for ${inEPDT}. It must be in the format yyyyMMdd.");
        return false;
      }
    }
    
    // Check inEPTM
    if (!inEPTM.isBlank()) {
      if (!utility.call("CommonUtil", "isValidTime", inEPTM.padLeft(6, '0'))) {
        mi.error("Invalid TIME input for ${inEPTM}. It must be in the format HHmmss.");
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