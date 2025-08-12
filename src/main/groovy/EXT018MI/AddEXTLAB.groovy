/**
 * @Name: AddEXTLAB.EXTLAB
 * @Description: Add record on table EXTLAB
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddEXTLAB extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO, inCHNO, inRGDT, inRGTM, inLMDT;
  private String inDIVI;
  private String inLDID;
  private String inLRID;
  private String inWHLO;
  private String inTKID;
  private String inLRIT;
  private String inLRNM;
  private String inTSEQ;
  private String inCPID;
  private String inLRMN;
  private String inLRMX;
  private String inLABR;
  private String inLBRS;
  private String inLBMN;
  private String inLCFM;
  private String inCHID;
  private String[] validItemType = ["M"]; // M=MILK

  public AddEXTLAB(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inTKID = mi.inData.get("TKID") == null ? "" : mi.inData.get("TKID").trim();
    inLRID = mi.inData.get("LRID") == null ? "0" : mi.inData.get("LRID").trim();
    inLRIT = mi.inData.get("LRIT") == null ? "" : mi.inData.get("LRIT").trim();
    inLRNM = mi.inData.get("LRNM") == null ? "" : mi.inData.get("LRNM").trim();
    inTSEQ = mi.inData.get("TSEQ") == null ? "0" : mi.inData.get("TSEQ").trim();
    inCPID = mi.inData.get("CPID") == null ? "" : mi.inData.get("CPID").trim();
    inLRMN = mi.inData.get("LRMN") == null ? "" : mi.inData.get("LRMN").trim();
    inLRMX = mi.inData.get("LRMX") == null ? "" : mi.inData.get("LRMX").trim();
    inLABR = mi.inData.get("LABR") == null ? "0.0" : mi.inData.get("LABR").trim();
    inLBRS = mi.inData.get("LBRS") == null ? "" : mi.inData.get("LBRS").trim();
    inLBMN = mi.inData.get("LBMN") == null ? "" : mi.inData.get("LBMN") as char;
    inLCFM = mi.inData.get("LCFM") == null ? "" : mi.inData.get("LCFM") as char;

    if (!isValidInput()) {
      return;
    }

    DBAction query = database.table("EXTLAB").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    container.set("EXTKID", inTKID);
    container.set("EXLRIT", inLRIT);
    container.set("EXLRID", inLRID as int);

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
    
    container.set("EXLRNM", inLRNM);
    container.set("EXTSEQ", inTSEQ.isBlank() ? 0 : inTSEQ as int);
    container.set("EXCPID", inCPID);
    container.set("EXLRMN", inLRMN.isBlank() ? 0 : inLRMN as double);
    container.set("EXLRMX", inLRMX.isBlank() ? 0 : inLRMX as double);
    container.set("EXLABR", inLABR.isBlank() ? 0.0 : inLABR as double);
    container.set("EXLBRS", inLBRS);
    container.set("EXLBMN", inLBMN.toUpperCase());
    container.set("EXLCFM", inLCFM.toUpperCase());
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
    
    //Check inLBMN
    if (!inLBMN.isBlank()) {
      if (!utility.call("CommonUtil", "isValidFlag", inLBMN, "Y", "N")) {
        mi.error("Invalid input value for ${inLBMN}. Must be Y or N.");
        return false;
      }
    }
    
    //Check inLCFM
    if (!inLCFM.isBlank()) {
      if (!utility.call("CommonUtil", "isValidFlag", inLCFM, "Y", "N")) {
        mi.error("Invalid input value for ${inLCFM}. Must be Y or N.");
        return false;
      }
    }
    
    if (!validItemType.contains(inLRIT)) {
      mi.error("Invalid item type. Allowed item type are ${Arrays.toString(validItemType)}");
      return false;
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