/**
 * @Name: EXT201MI.LstOutboundLds
 * @Description: List Outbound Loads
 * @Authors: Mark Jayson Mandap (M3GDS)
 *
 * @CHANGELOGS
 *  Version   Date    User          Description
 *  1.0.0     250806  22RR86TW86    Initial Release
 */

public class LstOutboundLds extends ExtendM3Transaction {
  private final MIAPI mi;
  private final ProgramAPI program;
  private final DatabaseAPI database;
  private final UtilityAPI utility;

  private int inCONO;
  private String inWHLO;
  private String inDIVI;
  private String inITNO;
  private String inFDDT;
  private String inTDDT;
  private String inSTAS;
  private String XXCTTP;

  public LstOutboundLds(MIAPI mi, ProgramAPI program, DatabaseAPI database, UtilityAPI utility) {
    this.mi = mi;
    this.program = program;
    this.database = database;
    this.utility = utility;
  }

  public void main() {
    final int MAX_RECORDS = mi.getMaxRecords() <= 0 || mi.getMaxRecords() >= 10000 ? 10000 : mi.getMaxRecords();

    // Initialize input fields
    initializeInput();

    // Validate inputs
    if (!isValidInput()) {
      return;
    }

    ExpressionFactory expression = database.getExpressionFactory("EXTDL1");
    expression = expression.eq("EXDIVI", inDIVI);
    expression = expression.and(expression.eq("EXWHLO", inWHLO));
    if (!inFDDT.isBlank()) {
      expression = expression.and(expression.ge("EXDPDT", inFDDT));
    }
    if (!inTDDT.isBlank()) {
      expression = expression.and(expression.le("EXDPDT", inTDDT));
    }

    DBAction dbaEXTDL1 = database
        .table("EXTDL1")
        .index("00")
        .matching(expression)
        .selection("EXCONO", "EXDIVI", "EXWHLO", "EXLDID", "EXDSEQ", "EXPDST", "EXDPDT", "EXDPTM")
        .build();

    DBContainer container = dbaEXTDL1.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    dbaEXTDL1.readAll(container, 3, MAX_RECORDS, { DBContainer data ->
      String XXPDST = data.getString("EXPDST").trim();
      // Only output records where status is part of the filter
      if (inSTAS.isBlank() ||
          !inSTAS.isBlank() && !XXPDST.isBlank() && inSTAS.indexOf(XXPDST) > -1) {

        // Fetch header data
        getHeaderData(data.getString("EXLDID"));

        // Only output commodity type OUTBOUND
        if (XXCTTP == "OUTBOUND") {
          mi.outData.put("CONO", data.get("EXCONO").toString());
          mi.outData.put("DIVI", data.get("EXDIVI").toString());
          mi.outData.put("WHLO", data.get("EXWHLO").toString());
          mi.outData.put("LDID", data.get("EXLDID").toString());
          mi.outData.put("PDST", data.get("EXPDST").toString());
          mi.outData.put("DPDT", data.get("EXDPDT").toString());
          mi.outData.put("DPTM", data.get("EXDPTM").toString());
          mi.write();
        }
      }
    });
  }

  /**
   * Get PDST field from EXTDL1
   */
  void getHeaderData(String LDID) {
    XXCTTP = "";

    // Read EXTHDR
    DBAction dbaEXTHDR = database.table("EXTHDR").index("00").selection("EXCTTP", "EXRFNO").build();
    DBContainer XTHDR = dbaEXTHDR.getContainer();
    XTHDR.set("EXCONO", inCONO);
    XTHDR.set("EXDIVI", inDIVI);
    XTHDR.set("EXWHLO", inWHLO);
    XTHDR.set("EXLDID", LDID);
    if (dbaEXTHDR.read(XTHDR)) {
      XXCTTP = XTHDR.getString("EXCTTP").trim();
      mi.outData.put("RFNO", XTHDR.get("EXRFNO").toString());
    }
  }

  /**
   * Validate input fields
   */
  boolean isValidInput() {
    // Check From date
    if (!inFDDT.isBlank() && !inFDDT.equals("0") && !checkDate(inFDDT)) {
      mi.error("Invalid date ${inFDDT}");
      return false;
    }

    // Check To date
    if (!inTDDT.isBlank() && !inTDDT.equals("0") && !checkDate(inTDDT)) {
      mi.error("Invalid date ${inTDDT}");
      return false;
    }

    return true;
  }

  /**
   * Initialize input fields
   */
  void initializeInput() {
    inCONO = program.LDAZD.get("CONO");
    if (mi.inData.get("CONO")?.trim()) {
      inCONO = mi.in.get("CONO");
    }
    inDIVI = mi.inData.get("DIVI")?.trim() ?: "";
    inFDDT = mi.inData.get("FDDT")?.trim() ?: "";
    inTDDT = mi.inData.get("TDDT")?.trim() ?: "";
    inSTAS = mi.inData.get("STAS")?.trim() ?: "";
    inWHLO = mi.inData.get("WHLO")?.trim() ?: "";

    XXCTTP = "";
  }

  /**
   * Check if valid date
   */
  boolean checkDate(String date) {
    return utility.call("DateUtil", "isValidDate", "uuuuMMdd", date);
  }
}