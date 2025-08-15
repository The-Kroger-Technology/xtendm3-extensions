/**
 * @Name: EXT201MI.LstExpenseItems
 * @Description: List Expense items
 * @Authors: Mark Jayson Mandap (M3GDS)
 *
 * @CHANGELOGS
 *  Version   Date    User          Description
 *  1.0.0     250806  22RR86TW86    Initial Release
 */

public class LstExpenseItems extends ExtendM3Transaction {
  private final MIAPI mi;
  private final ProgramAPI program;
  private final DatabaseAPI database;
  private final UtilityAPI utility;

  private int inCONO;
  private String inWHLO;
  private String inDIVI;
  private String inITNO;
  private String inFADT;
  private String inTADT;
  private String inSTAS;
  private String XXPDST;

  public LstExpenseItems(MIAPI mi, ProgramAPI program, DatabaseAPI database, UtilityAPI utility) {
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

    ExpressionFactory expression = database.getExpressionFactory("EXTHDR");
    expression = expression.eq("EXDIVI", inDIVI);
    expression = expression.and(expression.eq("EXWHLO", inWHLO));
    expression = expression.eq("EXCTTP", "EXPENSE");
    if (!inFADT.isBlank()) {
      expression = expression.and(expression.ge("EXARDT", inFADT));
    }
    if (!inTADT.isBlank()) {
      expression = expression.and(expression.le("EXARDT", inTADT));
    }

    DBAction dbaEXTHDR = database
        .table("EXTHDR")
        .index("00")
        .matching(expression)
        .selection("EXCONO", "EXDIVI", "EXWHLO", "EXLDID", "EXRFNO", "EXARDT", "EXARTM")
        .build();

    DBContainer container = dbaEXTHDR.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    dbaEXTHDR.readAll(container, 3, MAX_RECORDS, { DBContainer data ->
      // Get Status
      getStatus(data);

      // Only output records where status is part of the filter
      if (inSTAS.isBlank() ||
          !inSTAS.isBlank() && !XXPDST.isBlank() && inSTAS.indexOf(XXPDST) > -1) {
        mi.outData.put("CONO", data.get("EXCONO").toString());
        mi.outData.put("DIVI", data.get("EXDIVI").toString());
        mi.outData.put("WHLO", data.get("EXWHLO").toString());
        mi.outData.put("LDID", data.get("EXLDID").toString());
        mi.outData.put("RFNO", data.get("EXRFNO").toString());
        mi.outData.put("ARDT", data.get("EXARDT").toString());
        mi.outData.put("ARTM", data.get("EXARTM").toString());
        mi.write();
      }
    });
  }

  /**
   * Get PDST field from EXTDL1
   */
  void getStatus(DBContainer header) {
    XXPDST = "";

    // Read EXTDL1
    DBAction dbaEXTDL1 = database.table("EXTDL1").index("00").selection("EXPDST").build();
    DBContainer XTDL1 = dbaEXTDL1.getContainer();
    XTDL1.set("EXCONO", header.getInt("EXCONO"));
    XTDL1.set("EXDIVI", header.getString("EXDIVI"));
    XTDL1.set("EXWHLO", header.getString("EXWHLO"));
    XTDL1.set("EXLDID", header.getString("EXLDID"));
    XTDL1.set("EXDSEQ", 1);
    if (dbaEXTDL1.read(XTDL1)) {
      XXPDST = XTDL1.getString("EXPDST").trim();
    }

    mi.outData.put("PDST", XXPDST);
  }

  /**
   * Validate input fields
   */
  boolean isValidInput() {
    // Check From date
    if (!inFADT.isBlank() && !inFADT.equals("0") && !checkDate(inFADT)) {
      mi.error("Invalid date ${inFADT}");
      return false;
    }

    // Check To date
    if (!inTADT.isBlank() && !inTADT.equals("0") && !checkDate(inTADT)) {
      mi.error("Invalid date ${inTADT}");
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
    inFADT = mi.inData.get("FADT")?.trim() ?: "";
    inTADT = mi.inData.get("TADT")?.trim() ?: "";
    inSTAS = mi.inData.get("STAS")?.trim() ?: "";
    inWHLO = mi.inData.get("WHLO")?.trim() ?: "";

    XXPDST = "";
  }

  /**
   * Check if valid date
   */
  boolean checkDate(String date) {
    return utility.call("DateUtil", "isValidDate", "uuuuMMdd", date);
  }
}