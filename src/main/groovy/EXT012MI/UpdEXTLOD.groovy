/**
 * @Name: UpdEXTLOD.EXTLOD
 * @Description: Update record on table EXTLOD
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UpdEXTLOD extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO, inEPDT, inCLDT, inEVDT, inYEIS, inMOIS, inCHNO, inRGDT, inRGTM, inLMDT;
  private String inDIVI;
  private String inWHLO;
  private String inLDID;
  private String inMNUM;
  private double inTPND;
  private String inSUNO;
  private String inSUNM;
  private String inCMPT;
  private String inOSMW;
  private String inDCWT;
  private String inCLWT;
  private String inTRWT;
  private String inADWT;
  private String inAMWT;
  private String inSMWT;
  private String inCMNO;
  private String inCPWT;
  private String inCBFP;
  private String inCBFL;
  private String inCPPT;
  private String inCPLB;
  private String inCNFP;
  private String inCNFL;
  private String inCTSP;
  private String inCTSL;
  private String inPBFP;
  private String inPBFL;
  private String inPPPT;
  private String inPPLB;
  private String inPNFP;
  private String inPNFL;
  private String inPTSP;
  private String inPTSL;
  private String inSTIS;
  private String inEPTM;
  private String inEVTM;
  private String inCHID;

  public UpdEXTLOD(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inMNUM = mi.inData.get("MNUM") == null ? "" : mi.inData.get("MNUM").trim();
    inTPND = mi.in.get("TPND") == null ? 0 : mi.in.get("TPND") as double;
    inEPDT = mi.in.get("EPDT") == null ? 0 : mi.in.get("EPDT") as Integer;
    inEPTM = mi.inData.get("EPTM") == null ? "0" : mi.inData.get("EPTM").trim();
    inSUNO = mi.inData.get("SUNO") == null ? "" : mi.inData.get("SUNO").trim();
    inSUNM = mi.inData.get("SUNM") == null ? "" : mi.inData.get("SUNM").trim();
    inCMPT = mi.inData.get("CMPT") == null ? "" : mi.inData.get("CMPT").trim();
    inOSMW = mi.inData.get("OSMW") == null ? "" : mi.inData.get("OSMW").trim();
    inEVDT = mi.in.get("EVDT") == null ? 0 : mi.in.get("EVDT") as Integer;
    inEVTM = mi.inData.get("EVTM") == null ? "0" : mi.inData.get("EVTM").trim();
    inCLDT = mi.in.get("CLDT") == null ? 0 : mi.in.get("CLDT") as Integer;
    inDCWT = mi.inData.get("DCWT") == null ? "" : mi.inData.get("DCWT").trim();
    inCLWT = mi.inData.get("CLWT") == null ? "" : mi.inData.get("CLWT").trim();
    inTRWT = mi.inData.get("TRWT") == null ? "" : mi.inData.get("TRWT").trim();
    inADWT = mi.inData.get("ADWT") == null ? "" : mi.inData.get("ADWT").trim();
    inAMWT = mi.inData.get("AMWT") == null ? "" : mi.inData.get("AMWT").trim();
    inSMWT = mi.inData.get("SMWT") == null ? "" : mi.inData.get("SMWT").trim();
    inCMNO = mi.inData.get("CMNO") == null ? "" : mi.inData.get("CMNO").trim();
    inCPWT = mi.inData.get("CPWT") == null ? "" : mi.inData.get("CPWT").trim();
    inCBFP = mi.inData.get("CBFP") == null ? "" : mi.inData.get("CBFP").trim();
    inCBFL = mi.inData.get("CBFL") == null ? "" : mi.inData.get("CBFL").trim();
    inCPPT = mi.inData.get("CPPT") == null ? "" : mi.inData.get("CPPT").trim();
    inCPLB = mi.inData.get("CPLB") == null ? "" : mi.inData.get("CPLB").trim();
    inCNFP = mi.inData.get("CNFP") == null ? "" : mi.inData.get("CNFP").trim();
    inCNFL = mi.inData.get("CNFL") == null ? "" : mi.inData.get("CNFL").trim();
    inCTSP = mi.inData.get("CTSP") == null ? "" : mi.inData.get("CTSP").trim();
    inCTSL = mi.inData.get("CTSL") == null ? "" : mi.inData.get("CTSL").trim();
    inPBFP = mi.inData.get("PBFP") == null ? "" : mi.inData.get("PBFP").trim();
    inPBFL = mi.inData.get("PBFL") == null ? "" : mi.inData.get("PBFL").trim();
    inPPPT = mi.inData.get("PPPT") == null ? "" : mi.inData.get("PPPT").trim();
    inPPLB = mi.inData.get("PPLB") == null ? "" : mi.inData.get("PPLB").trim();
    inPNFP = mi.inData.get("PNFP") == null ? "" : mi.inData.get("PNFP").trim();
    inPNFL = mi.inData.get("PNFL") == null ? "" : mi.inData.get("PNFL").trim();
    inPTSP = mi.inData.get("PTSP") == null ? "" : mi.inData.get("PTSP").trim();
    inPTSL = mi.inData.get("PTSL") == null ? "" : mi.inData.get("PTSL").trim();
    inSTIS = mi.inData.get("STIS") == null ? "" : mi.inData.get("STIS").trim();
    inYEIS = mi.in.get("YEIS") == null ? 0 : mi.in.get("YEIS") as Integer;
    inMOIS = mi.in.get("MOIS") == null ? 0 : mi.in.get("MOIS") as Integer;

    if (!isValidInput()) {
      return;
    }
    
    DBAction query = database.table("EXTLOD").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    container.set("EXMNUM", inMNUM);

    if (!query.read(container)) {
      mi.error("Record does not exists");
      return;
    }

    LocalDateTime dateTime = LocalDateTime.now();
    int changedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toInteger();
    int changedTime = dateTime.format(DateTimeFormatter.ofPattern("HHmmss")).toInteger();

    query.readLock(container, {
      LockedResult lockedResult ->
      if (inTPND != 0) {
        lockedResult.set("EXTPND", inTPND.equals("?") ? 0 : inTPND);
      }
      if (inEPDT != 0) {
        lockedResult.set("EXEPDT", inEPDT.equals("?") ? 0 :  inEPDT);
      }
      if (!inEPTM.isBlank()) {
        lockedResult.set("EXEPTM", inEPTM.equals("?") ? 0 : inEPTM as int);
      }
      if (!inSUNO.isBlank()) {
        lockedResult.set("EXSUNO", inSUNO.equals("?") ? "" : inSUNO);
      }
      if (!inSUNM.isBlank()) {
        lockedResult.set("EXSUNM", inSUNM.equals("?") ? "" : inSUNM);
      }
      if (!inCMPT.isBlank()) {
        lockedResult.set("EXCMPT", inCMPT.equals("?") ? "" : inCMPT);
      }
      if (!inOSMW.isBlank()) {
        lockedResult.set("EXOSMW", inOSMW.equals("?") ? "" : inOSMW);
      }
      if (inEVDT != 0) {
        lockedResult.set("EXEVDT", inEVDT.equals("?") ? 0 : inEVDT);
      }
      if (!inEVTM.isBlank()) {
        lockedResult.set("EXEVTM", inEVTM.equals("?") ? 0 : inEVTM as int);
      }
      if (inCLDT != 0) {
        lockedResult.set("EXCLDT", inCLDT.equals("?") ? 0 : inCLDT);
      }
      if (!inDCWT.isBlank()) {
        lockedResult.set("EXDCWT", inDCWT.equals("?") ? 0.0 : inDCWT as double);
      }
      if (!inCLWT.isBlank()) {
        lockedResult.set("EXCLWT", inCLWT.equals("?") ? 0.0 : inCLWT as double);
      }
      if (!inTRWT.isBlank()) {
        lockedResult.set("EXTRWT", inCLWT.equals("?") ? 0.0 : inTRWT as double);
      }
      if (!inADWT.isBlank()) {
        lockedResult.set("EXADWT", inADWT.equals("?") ? 0.0 : inADWT as double);
      }
      if (!inAMWT.isBlank()) {
        lockedResult.set("EXAMWT", inAMWT.equals("?") ? 0.0 : inAMWT as double);
      }
      if (!inSMWT.isBlank()) {
        lockedResult.set("EXSMWT", inSMWT.equals("?") ? 0.0 : inSMWT as double);
      }
      if (!inCMNO.isBlank()) {
        lockedResult.set("EXCMNO", inCMNO.equals("?") ? "" : inCMNO);
      }
      if (!inCPWT.isBlank()) {
        lockedResult.set("EXCPWT", inCPWT.equals("?") ? 0.0 : inCPWT as double);
      }
      if (!inCBFP.isBlank()) {
        lockedResult.set("EXCBFP", inCBFP.equals("?") ? 0.0 : inCBFP as double);
      }
      if (!inCBFL.isBlank()) {
        lockedResult.set("EXCBFL", inCBFL.equals("?") ? 0.0 : inCBFL as double);
      }
      if (!inCPPT.isBlank()) {
        lockedResult.set("EXCPPT", inCPPT.equals("?") ? 0.0 : inCPPT as double);
      }
      if (!inCPLB.isBlank()) {
        lockedResult.set("EXCPLB", inCPLB.equals("?") ? 0.0 : inCPLB as double);
      }
      if (!inCNFP.isBlank()) {
        lockedResult.set("EXCNFP", inCNFP.equals("?") ? 0.0 : inCNFP as double);
      }
      if (!inCNFL.isBlank()) {
        lockedResult.set("EXCNFL", inCNFL.equals("?") ? 0.0 : inCNFL as double);
      }
      if (!inCTSP.isBlank()) {
        lockedResult.set("EXCTSP", inCTSP.equals("?") ? 0.0 : inCTSP as double);
      }
      if (!inCTSL.isBlank()) {
        lockedResult.set("EXCTSL", inCTSL.equals("?") ? 0.0 : inCTSL as double);
      }
      if (!inPBFP.isBlank()) {
        lockedResult.set("EXPBFP", inPBFP.equals("?") ? 0.0 : inPBFP as double);
      }
      if (!inPBFL.isBlank()) {
        lockedResult.set("EXPBFL", inPBFL.equals("?") ? 0.0 : inPBFL as double);
      }
      if (!inPPPT.isBlank()) {
        lockedResult.set("EXPPPT", inPPPT.equals("?") ? 0.0 : inPPPT as double);
      }
      if (!inPPLB.isBlank()) {
        lockedResult.set("EXPPLB", inPPLB.equals("?") ? 0.0 : inPPLB as double);
      }
      if (!inPNFP.isBlank()) {
        lockedResult.set("EXPNFP", inPNFP.equals("?") ? 0.0 : inPNFP as double);
      }
      if (!inPNFL.isBlank()) {
        lockedResult.set("EXPNFL", inPNFL.equals("?") ? 0.0 : inPNFL as double);
      }
      if (!inPTSP.isBlank()) {
        lockedResult.set("EXPTSP", inPTSP.equals("?") ? 0.0 : inPTSP as double);
      }
      if (!inPTSL.isBlank()) {
        lockedResult.set("EXPTSL", inPTSL.equals("?") ? 0.0 : inPTSL as double);
      }
      if (!inSTIS.isBlank()) {
        lockedResult.set("EXSTIS", inSTIS.equals("?") ? "" : inSTIS);
      }
      if (inYEIS != 0) {
        lockedResult.set("EXYEIS", inYEIS.equals("?") ? 0 : inYEIS);
      }
      if (inMOIS != 0) {
        lockedResult.set("EXMOIS", inMOIS.equals("?") ? 0 : inMOIS);
      }
      lockedResult.set("EXCHNO", lockedResult.getInt("EXCHNO") + 1);
      lockedResult.set("EXLMDT", changedDate);
      lockedResult.set("EXLMTM", changedTime);
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
    
    // Check inCLDT
    if (inCLDT != 0) {
      if (!utility.call("DateUtil", "isDateValid", inCLDT.toString(), "yyyyMMdd")) {
        mi.error("Invalid DATE input for ${inCLDT}. It must be in the format yyyyMMdd.");
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