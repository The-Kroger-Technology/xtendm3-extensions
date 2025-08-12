/**
 * @Name: UpdEXTTPL.EXTTPL
 * @Description: Update record on table EXTTPL
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UpdEXTTPL extends ExtendM3Transaction {
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
  private String inSTIM;
  private String inMKGD;
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
  
  public UpdEXTTPL(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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

    if (!query.read(container)) {
      mi.error("Record does not exists");
      return;
    }

    LocalDateTime dateTime = LocalDateTime.now();
    int changedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toInteger();
    int changedTime = dateTime.format(DateTimeFormatter.ofPattern("HHmmss")).toInteger();

    query.readLock(container, {
      LockedResult lockedResult ->
      if (inEVDT != 0) {
        lockedResult.set("EXEVDT", inEVDT.equals("?") ? 0 : inEVDT);
      }
      if (!inEVTM.isBlank()) {
        lockedResult.set("EXEVTM", inEVTM.equals("?") ? 0 : inEVTM as int);
      }
      if (inDPDT != 0) {
        lockedResult.set("EXDPDT", inDPDT.equals("?") ? 0 : inDPDT);
      }
      if (!inDPTM.isBlank()) {
        lockedResult.set("EXDPTM", inDPTM.equals("?") ? 0 : inDPTM as int);
      }
      if (inARDT != 0) {
        lockedResult.set("EXARDT", inARDT.equals("?") ? 0 : inARDT);
      }
      if (!inARTM.isBlank()) {
        lockedResult.set("EXARTM", inARTM.equals("?") ? 0 : inARTM as int);
      }
      if (!inFNWT.isBlank()) {
        lockedResult.set("EXFNWT", inFNWT.equals("?") ? 0.0 : inFNWT as double);
      }
      if (!inGRWT.isBlank()) {
        lockedResult.set("EXGRWT", inGRWT.equals("?") ? 0.0 : inGRWT  as double);
      }
      if (!inSTNO.isBlank()) {
        lockedResult.set("EXSTNO", inSTNO.equals("?") ? "" : inSTNO);
      }
      if (!inTRWT.isBlank()) {
        lockedResult.set("EXTRWT", inTRWT.equals("?") ? 0.0 : inTRWT as double);
      }
      if (!inSTIM.isBlank()) {
        lockedResult.set("EXSTIM", inSTIM.equals("?") ? "" : inSTIM);
      }
      if (!inMKGD.isBlank()) {
        lockedResult.set("EXMKGD", inMKGD.equals("?") ? "" : inMKGD);
      }
      if (!inSTIL.isBlank()) {
        lockedResult.set("EXSTIL", inSTIL.equals("?") ? "" : inSTIL);
      }
      if (inEPDT != 0) {
        lockedResult.set("EXEPDT", inEPDT.equals("?") ? 0 : inEPDT);
      }
      if (!inEPTM.isBlank()) {
        lockedResult.set("EXEPTM", inEPTM.equals("?") ? 0 : inEPTM as int);
      }
      if (!inSUCL.isBlank()) {
        lockedResult.set("EXSUCL", inSUCL.equals("?") ? "" : inSUCL);
      }
      if (!inSUID.isBlank()) {
        lockedResult.set("EXSUID", inSUID.equals("?") ? "" : inSUID);
      }
      if (!inSUNM.isBlank()) {
        lockedResult.set("EXSUNM", inSUNM.equals("?") ? "" : inSUNM);
      }
      if (!inOSMW.isBlank()) {
        lockedResult.set("EXOSMW", inOSMW.equals("?") ? "" : inOSMW);
      }
      if (inMEVD != 0) {
        lockedResult.set("EXMEVD", inMEVD.equals("?") ? 0 : inMEVD);
      }
      if (!inMEVT.isBlank()) {
        lockedResult.set("EXMEVT", inMEVT.equals("?") ? 0 : inMEVT as int);
      }
      if (!inAUTO.isBlank()) {
        lockedResult.set("EXAUTO", inAUTO.equals("?") ? "" : inAUTO);
      }
      if (!inPDCL.isBlank()) {
        lockedResult.set("EXPDCL", inPDCL.equals("?") ? "" : inPDCL);
      }
      if (!inPDID.isBlank()) {
        lockedResult.set("EXPDID", inPDID.equals("?") ? "" : inPDID);
      }
      if (!inPDNM.isBlank()) {
        lockedResult.set("EXPDNM", inPDNM.equals("?") ? "" : inPDNM);
      }
      if (!inPDCD.isBlank()) {
        lockedResult.set("EXPDCD", inPDCD.equals("?") ? "" : inPDCD);
      }
      if (!inBTUN.isBlank()) {
        lockedResult.set("EXBTUN", inBTUN.equals("?") ? "" : inBTUN);
      }
      if (!inDIVN.isBlank()) {
        lockedResult.set("EXDIVN", inDIVN.equals("?") ? "" : inDIVN);
      }
      if (!inGALS.isBlank()) {
        lockedResult.set("EXGALS", inGALS.equals("?") ? "" : inGALS);
      }
      if (!inPUST.isBlank()) {
        lockedResult.set("EXPUST", inPUST.equals("?") ? "" : inPUST);
      }
      lockedResult.set("EXLMTM", changedTime);
      lockedResult.set("EXCHNO", lockedResult.getInt("EXCHNO") + 1);
      lockedResult.set("EXLMDT", changedDate);
      lockedResult.set("EXCHID", program.getUser());
      lockedResult.update();
    });
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
    if (!inEVTM.isBlank() && !inEVTM.equals("?")) {
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
    if (!inDPTM.isBlank() && !inDPTM.equals("?")) {
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
    if (!inARTM.isBlank() && !inARTM.equals("?")) {
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
    if (!inMEVT.isBlank() && !inMEVT.equals("?")) {
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
    if (!inEPTM.isBlank() && !inEPTM.equals("?")) {
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