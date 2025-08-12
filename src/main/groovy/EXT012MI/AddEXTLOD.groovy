/**
 * @Name: AddEXTLOD.EXTLOD
 * @Description: Add record on table EXTLOD
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     250704   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddEXTLOD extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO, inEPDT, inCLDT, inEVDT, inYEIS, inMOIS, inCHNO, inRGDT, inRGTM, inLMDT;
  private String inDIVI;
  private String inLDID;
  private String inWHLO;
  private String inMNUM;
  private String inTPND;
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

  public AddEXTLOD(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inTPND = mi.inData.get("TPND") == null ? "0.0" : mi.inData.get("TPND").trim();
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
    
    // Check Company
    if(inCONO == 0){
      inCONO = (Integer) program.LDAZD.CONO;
    }
    
    // Validate inputs
    if (!this.isValidInput()) {
      return;
    }
    
    DBAction query = database.table("EXTLOD").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    container.set("EXMNUM", inMNUM);

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

    container.set("EXTPND", inTPND.isBlank() ? 0.0 : inTPND as double);
    container.set("EXEPDT", inEPDT);
    container.set("EXEPTM", inEPTM.isBlank() ? 0 : inEPTM as int);
    container.set("EXSUNO", inSUNO);
    container.set("EXSUNM", inSUNM);
    container.set("EXCMPT", inCMPT);
    container.set("EXOSMW", inOSMW);
    container.set("EXEVDT", inEVDT);
    container.set("EXEVTM", inEVTM.isBlank() ? 0 : inEVTM as int);
    container.set("EXCLDT", inCLDT);
    container.set("EXDCWT", inDCWT.isBlank() ? 0.0 : inDCWT as double);
    container.set("EXCLWT", inCLWT.isBlank() ? 0.0 : inCLWT as double);
    container.set("EXTRWT", inTRWT.isBlank() ? 0.0 : inTRWT as double);
    container.set("EXADWT", inADWT.isBlank() ? 0.0 : inADWT as double);
    container.set("EXAMWT", inAMWT.isBlank() ? 0.0 : inAMWT as double);
    container.set("EXSMWT", inSMWT.isBlank() ? 0.0 : inSMWT as double);
    container.set("EXCMNO", inCMNO);
    container.set("EXCPWT", inCPWT.isBlank() ? 0.0 : inCPWT as double);
    container.set("EXCBFP", inCBFP.isBlank() ? 0.0 : inCBFP as double);
    container.set("EXCBFL", inCBFL.isBlank() ? 0.0 : inCBFL as double);
    container.set("EXCPPT", inCPPT.isBlank() ? 0.0 : inCPPT as double);
    container.set("EXCPLB", inCPLB.isBlank() ? 0.0 : inCPLB as double);
    container.set("EXCNFP", inCNFP.isBlank() ? 0.0 : inCNFP as double);
    container.set("EXCNFL", inCNFL.isBlank() ? 0.0 : inCNFL as double);
    container.set("EXCTSP", inCTSP.isBlank() ? 0.0 : inCTSP as double);
    container.set("EXCTSL", inCTSL.isBlank() ? 0.0 : inCTSL as double);
    container.set("EXPBFP", inPBFP.isBlank() ? 0.0 : inPBFP as double);
    container.set("EXPBFL", inPBFL.isBlank() ? 0.0 : inPBFL as double);
    container.set("EXPPPT", inPPPT.isBlank() ? 0.0 : inPPPT as double);
    container.set("EXPPLB", inPPLB.isBlank() ? 0.0 : inPPLB as double);
    container.set("EXPNFP", inPNFP.isBlank() ? 0.0 : inPNFP as double);
    container.set("EXPNFL", inPNFL.isBlank() ? 0.0 : inPNFL as double);
    container.set("EXPTSP", inPTSP.isBlank() ? 0.0 : inPTSP as double);
    container.set("EXPTSL", inPTSL.isBlank() ? 0.0 : inPTSL as double);
    container.set("EXSTIS", inSTIS);
    container.set("EXYEIS", inYEIS);
    container.set("EXMOIS", inMOIS);
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
    
    // Check inEPDT
    if (inEPDT != 0) {
      if (!utility.call("DateUtil", "isDateValid", inEPDT.toString(), "yyyyMMdd")) {
        mi.error("Invalid DATE input for ${inEPDT}. It must be in the format yyyyMMdd.");
        return false;
      }
    }
    
    //inEPTM
    if (!inEPTM.isBlank()) {
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

    //inEVTM
    if (!inEVTM.isBlank()) {
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