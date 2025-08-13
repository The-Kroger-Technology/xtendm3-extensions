/**
 * @Name: AddEXTTPR.EXTTPR
 * @Description: Add record on table EXTTPR
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddEXTTPR extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO;
  private String inDIVI;
  private String inWHLO;
  private String inLDID;
  private String inINDN;
  private String inREAS;
  private String inEVDT;
  private String inEVTM;
  private String inPLCL;
  private String inPLID;
  private String inPLNA;
  private String inPDRM;
  private String inPSIR;
  private String inDISP;
  private String inCOMM;
  private String inCHID;
  private int inCHNO;
  private int inRGDT;
  private int inRGTM;
  private int inLMDT;
  private int inLMTM;

  public AddEXTTPR(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
    this.mi = mi;
    this.utility = utility;
    this.logger = logger;
    this.program = program;
    this.miCaller = miCaller;
    this.database = database;
  }

  public void main() {
    inCONO = mi.in.get("CONO") == null ? program.LDAZD.CONO as int : mi.in.get("CONO") as int;
    inDIVI = mi.inData.get("DIVI") == null ? "" : mi.inData.get("DIVI").trim();
    inWHLO = mi.inData.get("WHLO") == null ? "" : mi.inData.get("WHLO").trim();
    inLDID = mi.inData.get("LDID") == null ? "" : mi.inData.get("LDID").trim();
    inINDN = mi.inData.get("INDN") == null ? "" : mi.inData.get("INDN").trim();
    inREAS = mi.inData.get("REAS") == null ? "" : mi.inData.get("REAS").trim();
    inEVDT = mi.inData.get("EVDT") == null ? "" : mi.inData.get("EVDT").trim();
    inEVTM = mi.inData.get("EVTM") == null ? "" : mi.inData.get("EVTM").trim();
    inPLCL = mi.inData.get("PLCL") == null ? "" : mi.inData.get("PLCL").trim();
    inPLID = mi.inData.get("PLID") == null ? "" : mi.inData.get("PLID").trim();
    inPLNA = mi.inData.get("PLNA") == null ? "" : mi.inData.get("PLNA").trim();
    inPDRM = mi.inData.get("PDRM") == null ? "" : mi.inData.get("PDRM").trim();
    inPSIR = mi.inData.get("PSIR") == null ? "" : mi.inData.get("PSIR").trim();
    inDISP = mi.inData.get("DISP") == null ? "" : mi.inData.get("DISP").trim();
    inCOMM = mi.inData.get("COMM") == null ? "" : mi.inData.get("COMM").trim();

    DBAction query = database.table("EXTTPR").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    container.set("EXINDN", inINDN as int);
    container.set("EXREAS", inREAS);

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
    inLMTM = entryTime;

    container.set("EXEVDT", inEVDT);
    container.set("EXEVTM", inEVTM);
    container.set("EXPLCL", inPLCL);
    container.set("EXPLID", inPLID);
    container.set("EXPLNA", inPLNA);
    container.set("EXPDRM", inPDRM);
    container.set("EXPSIR", inPSIR);
    container.set("EXDISP", inDISP);
    container.set("EXCOMM", inCOMM);
    container.set("EXCHID", inCHID);
    container.set("EXCHNO", inCHNO);
    container.set("EXRGDT", inRGDT);
    container.set("EXRGTM", inRGTM);
    container.set("EXLMDT", inLMDT);
    container.set("EXLMTM", inLMTM);

    query.insert(container);
  }

  boolean isValidInput() {
    // Check WHLO
    if (inWHLO != null && !inWHLO.isEmpty()) {
      if (!checkWHLO()) {
        mi.error("WHLO not found in MITWHL");
        return false;
      }
    }
  
    // Check EVDT (Event Date)
    if (inEVDT != null && !inEVDT.trim().isEmpty()) {
      if (!checkEVDT()) {
        mi.error("EVDT is invalid");
        return false;
      }
    }
  
    // Check EVTM (Event Time)
    if (inEVTM != null && !inEVTM.trim().isEmpty()) {
      if (!checkEVTM()) {
        mi.error("EVTM is invalid");
        return false;
      }
    }
  
    return true;
  }
  
  boolean checkWHLO() {
    DBAction query = database.table("MITWHL").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("MWCONO", inCONO);
    container.set("MWWHLO", inWHLO);
    return query.read(container);
  }
  
  boolean checkEVDT() {
    return utility.call("DateUtil", "isDateValid", inEVDT, "yyyyMMdd");
  }
  
  boolean checkEVTM() {
    return utility.call("DateUtil", "isTimeValid", inEVTM, "HHmmss");
  }
}