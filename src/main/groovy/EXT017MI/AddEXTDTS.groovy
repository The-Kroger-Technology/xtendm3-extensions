/**
 * @Name: AddEXTDTS.EXTDTS
 * @Description: Add record on table EXTDTS
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddEXTDTS extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO, inDSEQ, inCHNO, inRGDT, inRGTM, inLMDT;
  private String inDIVI;
  private String inWHLO;
  private String inLDID;
  private String inSNUM;
  private String inLOCA;
  private String inSTAT;
  private String inRRSN;
  private String inPSRL;
  private String inPSRS;
  private String inPSRR;
  private String inPSVR;
  private String inCHID;

  public AddEXTDTS(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inSNUM = mi.inData.get("SNUM") == null ? "" : mi.inData.get("SNUM").trim();
    inLOCA = mi.inData.get("LOCA") == null ? "" : mi.inData.get("LOCA").trim();
    inSTAT = mi.inData.get("STAT") == null ? "" : mi.inData.get("STAT").trim();
    inRRSN = mi.inData.get("RRSN") == null ? "" : mi.inData.get("RRSN").trim();
    inPSRL = mi.inData.get("PSRL") == null ? "" : mi.inData.get("PSRL").trim();
    inPSRS = mi.inData.get("PSRS") == null ? "" : mi.inData.get("PSRS").trim();
    inPSRR = mi.inData.get("PSRR") == null ? "" : mi.inData.get("PSRR").trim();
    inPSVR = mi.inData.get("PSVR") == null ? "" : mi.inData.get("PSVR") as char;

    if (!isValidInput()) {
      return;
    }
    
    DBAction query = database.table("EXTDTS").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    container.set("EXDSEQ", inDSEQ);
    container.set("EXSNUM", inSNUM);

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

    container.set("EXLOCA", inLOCA);
    container.set("EXSTAT", inSTAT);
    container.set("EXRRSN", inRRSN);
    container.set("EXPSRL", inPSRL);
    container.set("EXPSRS", inPSRS);
    container.set("EXPSRR", inPSRR);
    container.set("EXPSVR", inPSVR.toUpperCase());
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
    
    //Check inPSVR
    if (!inPSVR.isBlank()) {
      if (!utility.call("CommonUtil", "isValidFlag", inPSVR, "Y", "N")) {
        mi.error("Invalid input value for ${inPSVR}. Should be Y or N.");
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