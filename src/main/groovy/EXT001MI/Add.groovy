/**
 * @Name: EXT001MI.Add
 * @Description: Adds a new EXT001 record
 * @Authors: Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date    User        Description
 *  1.0.0     240302  JTAPANG     Initial Release
 *  1.0.1     240327  FCORTEZ     Updated validation for MPLINE and CATYPE
 */
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Add extends ExtendM3Transaction {
  private final MIAPI mi;
  private final ProgramAPI program;
  private final DatabaseAPI database;
  private final UtilityAPI utility;
  
  int inCONO, inINYR, inLINR, inPNLI, inIVDT, inREPN, inCDSE, inINBN;
  String inDIVI, inADBM, inSINO, inPUNO, inITNO, inRSCD, inDTXT, inSUNO, inACTY, inAIT1, inAIT2, inAIT3, inAIT4, inAIT5, inAIT6, inAIT7, inINLP, inCEID;
  double inQVAR, inPVAR, inVAMT, inIVNA, inIVQT, inIVOC, inNLAM;
  
  public Add(MIAPI mi, ProgramAPI program, DatabaseAPI database, UtilityAPI utility) {
    this.mi = mi;
    this.program = program;
    this.database = database;
    this.utility = utility;
  }
  
  public void main() {
    // Initialize input fields
    inCONO = mi.in.get("CONO") == null ? 0 : mi.in.get("CONO") as Integer;
    inDIVI = mi.in.get("DIVI") == null ? "" : mi.in.get("DIVI") as String;
    inADBM = mi.in.get("ADBM") == null ? "" : mi.in.get("ADBM") as String;
    inSINO = mi.in.get("SINO") == null ? "" : mi.in.get("SINO") as String;
    inINYR = mi.in.get("INYR") == null ? 0 : mi.in.get("INYR") as Integer;
    inIVDT = mi.in.get("IVDT") == null ? 0 : mi.in.get("IVDT") as Integer;
    inPUNO = mi.in.get("PUNO") == null ? "" : mi.in.get("PUNO") as String;
    inPNLI = mi.in.get("PNLI") == null ? 0 : mi.in.get("PNLI") as Integer;
    inREPN = mi.in.get("REPN") == null ? 0 : mi.in.get("REPN") as Integer;
    inLINR = mi.in.get("LINR") == null ? 0 : mi.in.get("LINR") as Integer;
    inITNO = mi.in.get("ITNO") == null ? "" : mi.in.get("ITNO") as String;
    inQVAR = mi.in.get("QVAR") == null ? 0 : mi.in.get("QVAR") as Double;
    inPVAR = mi.in.get("PVAR") == null ? 0 : mi.in.get("PVAR") as Double;
    inVAMT = mi.in.get("VAMT") == null ? 0 : mi.in.get("VAMT") as Double;
    inRSCD = mi.in.get("RSCD") == null ? "" : mi.in.get("RSCD") as String;
    inDTXT = mi.in.get("DTXT") == null ? "" : mi.in.get("DTXT") as String;
    inSUNO = mi.in.get("SUNO") == null ? "" : mi.in.get("SUNO") as String;
    inINLP = mi.in.get("INLP") == null ? "" : mi.in.get("INLP") as String;
    inACTY = mi.in.get("ACTY") == null ? "" : mi.in.get("ACTY") as String;
    inAIT1 = mi.in.get("AIT1") == null ? "" : mi.in.get("AIT1") as String;
    inAIT2 = mi.in.get("AIT2") == null ? "" : mi.in.get("AIT2") as String;
    inAIT3 = mi.in.get("AIT3") == null ? "" : mi.in.get("AIT3") as String;
    inAIT4 = mi.in.get("AIT4") == null ? "" : mi.in.get("AIT4") as String;
    inAIT5 = mi.in.get("AIT5") == null ? "" : mi.in.get("AIT5") as String;
    inAIT6 = mi.in.get("AIT6") == null ? "" : mi.in.get("AIT6") as String;
    inAIT7 = mi.in.get("AIT7") == null ? "" : mi.in.get("AIT7") as String;
    inIVNA = mi.in.get("IVNA") == null ? 0 : mi.in.get("IVNA") as Double;
    inIVQT = mi.in.get("IVQT") == null ? 0 : mi.in.get("IVQT") as Double;
    inIVOC = mi.in.get("IVOC") == null ? 0 : mi.in.get("IVOC") as Double;
    inNLAM = mi.in.get("NLAM") == null ? 0 : mi.in.get("NLAM") as Double;
    inCEID = mi.in.get("CEID") == null ? "" : mi.in.get("CEID") as String;
    inCDSE = mi.in.get("CDSE") == null ? 0 : mi.in.get("CDSE") as Integer;
    inINBN = mi.in.get("INBN") == null ? 0 : mi.in.get("INBN") as Integer;
    
    // Check Company
    if(inCONO == 0){
      inCONO = (Integer) program.LDAZD.CONO;
    }
    
    // Check Division
    if(inDIVI.isBlank()){
      inDIVI = (String) program.LDAZD.CONO;
    }
    
    // Check if record already exists
    DBAction dbaEXT001 = database.table("EXT001").index("00").build();
    DBContainer EXT001 = dbaEXT001.getContainer();
    EXT001.set("EXCONO", inCONO);
    EXT001.set("EXDIVI", inDIVI);
    EXT001.set("EXADBM", inADBM);
    EXT001.set("EXSINO", inSINO);
    EXT001.set("EXLINR", inLINR);
    if (dbaEXT001.read(EXT001)) {
      mi.error("Record already exists");
      return;
    }
    
    // Validate inputs
    if (!this.isValidInput()) {
      return;
    }
    
    EXT001.set("EXPUNO", inPUNO);
    EXT001.set("EXPNLI", inPNLI);
    EXT001.set("EXREPN", inREPN);
    EXT001.set("EXINLP", inINLP);
    EXT001.set("EXITNO", inITNO);
    EXT001.set("EXQVAR", inQVAR);
    EXT001.set("EXPVAR", inPVAR);
    EXT001.set("EXVAMT", inVAMT);
    EXT001.set("EXRSCD", inRSCD);
    EXT001.set("EXDTXT", inDTXT);
    EXT001.set("EXINYR", inINYR);
    EXT001.set("EXIVDT", inIVDT);
    EXT001.set("EXSUNO", inSUNO);
    EXT001.set("EXACTY", inACTY);
    EXT001.set("EXAIT1", inAIT1);
    EXT001.set("EXAIT2", inAIT2);
    EXT001.set("EXAIT3", inAIT3);
    EXT001.set("EXAIT4", inAIT4);
    EXT001.set("EXAIT5", inAIT5);
    EXT001.set("EXAIT6", inAIT6);
    EXT001.set("EXAIT7", inAIT7);
    EXT001.set("EXIVNA", inIVNA);
    EXT001.set("EXIVQT", inIVQT);
    EXT001.set("EXIVOC", inIVOC);
    EXT001.set("EXNLAM", inNLAM);
    EXT001.set("EXCEID", inCEID);
    EXT001.set("EXCDSE", inCDSE);
    EXT001.set("EXINBN", inINBN);
    EXT001.set("EXCHNO", 0);
    EXT001.set("EXLMDT", LocalDate.now().format(DateTimeFormatter.ofPattern("YYYYMMdd")).toInteger());
    EXT001.set("EXRGDT", LocalDate.now().format(DateTimeFormatter.ofPattern("YYYYMMdd")).toInteger());
    EXT001.set("EXRGTM", LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss")).toInteger());
    EXT001.set("EXLMTS", LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss")).toInteger());
    EXT001.set("EXCHID", program.getUser());
    dbaEXT001.insert(EXT001);
  }
  
  /**
   * Validate input fields
   */
  boolean isValidInput() {
    // Check Item number
    if (!inITNO.isBlank()) {
      if (!this.checkITNO()) {
        mi.error("Item number ${inITNO} does not exist");
        return false;
      }
    }
    
    // Check ACTY
    if (!inACTY.isBlank()) {
      if (!this.checkACTY()) {
        mi.error("Accounting type ${inACTY} does not exist");
        return false;
      }
    } 
    
    // Check PO number
    if (!inPUNO.isBlank()) {
      if (!this.checkPUNO()) {
        mi.error("Purchase order number ${inPUNO} does not exist");
        return false;
      }
    }
    
    // Check Supplier number
    if (!inSUNO.isBlank()) {
      if (!this.checkSUNO()) {
        mi.error("Supplier number ${inSUNO} does not exist");
        return false;
      }
    }
    
    // Check PO line number
    if (inPNLI != 0) {
      if (!this.checkPNLI()) {
        mi.error("Purchase order line number ${inPNLI} does not exist");
        return false;
      }
    }
    
    // Check Invoice Line Type
    if (!inINLP.isBlank()) {
      if (!(inINLP.equals("ITEM") || inINLP.equals("FREIGHT LINE") || inINLP.equals("MISCELLANEOUS"))) {
        mi.error("Invoice line type ${inINLP} does not exist");
        return false;
      }
    }
    
    // Check Transaction Reason
    if (!inRSCD.isBlank()) {
      if (!this.checkRSCD()) {
        mi.error("Transaction reason ${inRSCD} does not exist");
        return false;
      }
    }
    
    // Validate Invoice Date
    if (inIVDT != 0) {
      if(!utility.call("DateUtil", "isDateValid", inIVDT.toString(), "yyyyMMdd")) {
        mi.error("The invoice date '" + inIVDT + "' is invalid. It must be in the format yyyymmdd.");
        return false;
      }
    }
    
    // Check Supplier Invoice Number
    if (!inSINO.isBlank()) {
      if (!this.checkSINO()) {
        mi.error("Supplier invoice number ${inSINO} does not exist");
        return false;
      }
    }
    
    // Check Costing Element
    if (!inCEID.isBlank()) {
      if (!this.checkCEID()) {
        mi.error("Costing element ${inCEID} does not exist");
        return false;
      }
    }
    
    // Check Invoice Batch Number
    if (inINBN != 0) {
      if (!this.checkINBN()) {
        mi.error("Invoice batch number ${inINBN} does not exist");
        return false;
      }
    }
    
    return true;
  }
  
  /**
   * Validate ITNO from MITMAS or if MISCELLANEOUS/FREIGHT
   */
  boolean checkITNO() {
    DBAction MITMAS_query = database.table("MITMAS").index("00").build();
    DBContainer MITMAS = MITMAS_query.getContainer();
    MITMAS.set("MMCONO", inCONO);
    MITMAS.set("MMITNO", inITNO);
  
    if (!MITMAS_query.read(MITMAS) && !(inITNO == "MISCELLANEOUS" || inITNO == "FREIGHT")) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * Validate ACTY from CATYPE
   */
  boolean checkACTY() {
    DBAction CATYPE_query = database.table("CATYPE").index("00").build();
    DBContainer CATYPE = CATYPE_query.getContainer();
    CATYPE.set("COCONO", inCONO);
    CATYPE.set("CODIVI", '');
    CATYPE.set("COACTY", inACTY);
  
    if (!CATYPE_query.read(CATYPE)) {
      return false;
    } else {
      return true;
    }
  } 
  
  /**
   * Validate PUNO from MPHEAD
   */
  boolean checkPUNO() {
    DBAction MPHEAD_query = database.table("MPHEAD").index("00").build();
    DBContainer MPHEAD = MPHEAD_query.getContainer();
    MPHEAD.set("IACONO", inCONO);
    MPHEAD.set("IAPUNO", inPUNO);
  
    if (!MPHEAD_query.read(MPHEAD)) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * Validate SUNO from CIDMAS
   */
  boolean checkSUNO() {
    DBAction CIDMAS_query = database.table("CIDMAS").index("00").build();
    DBContainer CIDMAS = CIDMAS_query.getContainer();
    CIDMAS.set("IDCONO", inCONO);
    CIDMAS.set("IDSUNO", inSUNO);
    
    if (!CIDMAS_query.read(CIDMAS)) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * Validate PNLI from MPLINE
   */
  boolean checkPNLI() {
    DBAction MPLINE_query = database.table("MPLINE").index("00").build();
    DBContainer MPLINE = MPLINE_query.getContainer();
    MPLINE.set("IBCONO", inCONO);
    MPLINE.set("IBPUNO", inPUNO);
    MPLINE.set("IBPNLI", inPNLI);
    MPLINE.set("IBPNLS", 0);
    
    if (!MPLINE_query.read(MPLINE)) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * Validate RSCD from CSYTAB
   */
  boolean checkRSCD() {
    DBAction CSYTAB_query = database.table("CSYTAB").index("00").build();
    DBContainer CSYTAB = CSYTAB_query.getContainer();
    CSYTAB.set("CTCONO", inCONO);
    CSYTAB.set("CTDIVI", inDIVI);
    CSYTAB.set("CTSTCO", "REAP");
    CSYTAB.set("CTSTKY", inRSCD);
    
    if (!CSYTAB_query.read(CSYTAB)) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * Validate SINO from FGINHE
   */
  boolean checkSINO() {
    DBAction FGINHE_query = database.table("FGINHE").index("00").build();
    DBContainer FGINHE = FGINHE_query.getContainer();
    FGINHE.set("F4CONO", inCONO);
    FGINHE.set("F4DIVI", inDIVI);
    FGINHE.set("F4SUNO", inSUNO);
    FGINHE.set("F4SINO", inSINO);
    FGINHE.set("F4INYR", inINYR);
    
    if (!FGINHE_query.read(FGINHE)) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * Validate CEID from MPCELE
   */
  boolean checkCEID() {
    DBAction MPCELE_query = database.table("MPCELE").index("00").build();
    DBContainer MPCELE = MPCELE_query.getContainer();
    MPCELE.set("INCONO", inCONO);
    MPCELE.set("INCEID", inCEID);
    
    if (!MPCELE_query.read(MPCELE)) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * Validate INBN from FAPIBH
   */
  boolean checkINBN() {
    DBAction FAPIBH_query = database.table("FAPIBH").index("00").build();
    DBContainer FAPIBH = FAPIBH_query.getContainer();
    FAPIBH.set("E5CONO", inCONO);
    FAPIBH.set("E5DIVI", inDIVI);
    FAPIBH.set("E5INBN", inINBN);
    
    if (!FAPIBH_query.read(FAPIBH)) {
      return false;
    } else {
      return true;
    }
  }
  
}