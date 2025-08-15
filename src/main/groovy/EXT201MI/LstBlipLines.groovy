/**
 * @Name: EXT201MI.LstBlipLines
 * @Description: List BLiP lines
 * @Authors: Mark Jayson Mandap (M3GDS)
 *
 * @CHANGELOGS
 *  Version   Date    User          Description
 *  1.0.0     250523  22RR86TW86    Initial Release
 */

public class LstBlipLines extends ExtendM3Transaction {
  private final MIAPI mi;
  private final ProgramAPI program;
  private final DatabaseAPI database;
  private final UtilityAPI utility;

  private int inCONO;
  private String inFWDT;
  private String inTWDT;
  private String inSTAS;
  private String inWHLO;
  private String inLDID;
  private String inNMLK;

  private String XXSUNM;
  private String XXFUDS;
  private String XXCFI5;
  private String XXPDST;
  private String XXLDID;
  private String XXSCTM;

  public LstBlipLines(MIAPI mi, ProgramAPI program, DatabaseAPI database, UtilityAPI utility) {
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

    DBAction dbaMPLINE = database
        .table("MPLINE")
        .index("00")
        .matching(setExpression())
        .selection("IBPUNO", "IBPNLI", "IBPNLS", "IBITNO", "IBPUSL", "IBORQA", "IBSUNO", "IBORTY", "IBDWDT", "IBWHLO", "IBUCA1", "IBUCA2", "IBUCA5")
        .build();
    DBContainer PLINE = dbaMPLINE.getContainer();
    PLINE.set("IBCONO", inCONO);

    dbaMPLINE.readAll(PLINE, 1, MAX_RECORDS, { DBContainer data ->
      XXLDID = "";
      XXSCTM = "";
      XXPDST = "";
      XXSUNM = "";
      XXFUDS = "";
      XXCFI5 = "";

      // Get Status
      getStatus(data);

      // Get Arrival time
      getHeaderArrivalTime(data)

      // Only output records where status is part of the filter
      if (inSTAS.isBlank() ||
          !inSTAS.isBlank() && !XXPDST.isBlank() && inSTAS.indexOf(XXPDST) > -1) {
        // Fetch item number data
        readMITMAS(data.getString("IBITNO"));

        if (XXCFI5.equals("B")) {
          mi.outData.put("CONO", data.get("IBCONO").toString());
          mi.outData.put("PUNO", data.get("IBPUNO").toString());
          mi.outData.put("PNLI", data.get("IBPNLI").toString());
          mi.outData.put("PNLS", data.get("IBPNLS").toString());
          mi.outData.put("PUSL", data.get("IBPUSL").toString());
          mi.outData.put("ORQA", data.get("IBORQA").toString());
          mi.outData.put("SUNO", data.get("IBSUNO").toString());
          mi.outData.put("ORTY", data.get("IBORTY").toString());
          mi.outData.put("DWDT", data.get("IBDWDT").toString());
          mi.outData.put("WHLO", data.get("IBWHLO").toString());
          mi.outData.put("ITNO", data.get("IBITNO").toString());
          mi.outData.put("LDID", data.get("IBUCA1").toString());
          mi.outData.put("MFLG", data.get("IBUCA2").toString().trim());
          mi.outData.put("SCTM", data.get("IBUCA5").toString().trim());
          mi.outData.put("FUDS", XXFUDS);

          // Fetch supplier data
          readCIDMAS(data.getString("IBSUNO"));
          mi.outData.put("SUNM", XXSUNM);

          mi.write();
        }
      }
    });
  }

  /**
   * Get PDST field from EXTDL1
   */
  void getStatus(DBContainer kitHeader) {
    // Default the status to 10
    XXPDST = "10";
    int XXFDLG = 0;

    // Read EXTDL1
    DBAction dbaEXTDL1 = database.table("EXTDL1").index("00").selection("EXPDST").build();
    DBContainer XTDL1 = dbaEXTDL1.getContainer();
    XTDL1.set("EXCONO", inCONO);
    XTDL1.set("EXDIVI", program.LDAZD.get("DIVI"));
    XTDL1.set("EXWHLO", kitHeader.getString("IBWHLO"));
    XTDL1.set("EXLDID", kitHeader.getString("IBUCA1").trim());
    XTDL1.set("EXDSEQ", 1);
    if (dbaEXTDL1.read(XTDL1)) {
      XXPDST = XTDL1.getString("EXPDST").trim();
      XXFDLG = 1;
    }

    mi.outData.put("PDST", XXPDST);
    mi.outData.put("FDLG", XXFDLG.toString());
  }

  /**
   * Get ARTM field from EXTHDR
   */
  void getHeaderArrivalTime(DBContainer kitHeader) {
    int XXARTM = 0;
    int XXFHDR = 0;

    // Read EXTHDR
    DBAction dbaEXTHDR = database.table("EXTHDR").index("00").selection("EXARTM").build();
    DBContainer XTHDR = dbaEXTHDR.getContainer();
    XTHDR.set("EXCONO", inCONO);
    XTHDR.set("EXDIVI", program.LDAZD.get("DIVI"));
    XTHDR.set("EXWHLO", kitHeader.getString("IBWHLO"));
    XTHDR.set("EXLDID", kitHeader.getString("IBUCA1").trim());
    if (dbaEXTHDR.read(XTHDR)) {
      XXARTM = XTHDR.getInt("EXARTM");
      XXFHDR = 1;
    }

    mi.outData.put("ARTM", XXARTM.toString());
    mi.outData.put("FHDR", XXFHDR.toString());
  }

  /**
   * Fetch item information
   */
  void readMITMAS(String ITNO) {
    // Read MITMAS
    DBAction dbaMITMAS = database.table("MITMAS").index("00").selection("MMFUDS", "MMCFI5").build();
    DBContainer ITMAS = dbaMITMAS.getContainer();
    ITMAS.set("MMCONO", inCONO);
    ITMAS.set("MMITNO", ITNO);
    if (dbaMITMAS.read(ITMAS)) {
      XXFUDS = ITMAS.getString("MMFUDS");
      XXCFI5 = ITMAS.get("MMCFI5").toString();
    }
  }

  /**
   * Fetch supplier information
   */
  void readCIDMAS(String SUNO) {
    // Read CIDMAS
    DBAction dbaCIDMAS = database.table("CIDMAS").index("00").selection("IDSUNM").build();
    DBContainer IDMAS = dbaCIDMAS.getContainer();
    IDMAS.set("IDCONO", inCONO);
    IDMAS.set("IDSUNO", SUNO);
    if (dbaCIDMAS.read(IDMAS)) {
      XXSUNM = IDMAS.getString("IDSUNM");
    }
  }

  /**
   * Set expression for reading MPLINE
   */
  ExpressionFactory setExpression() {
    ExpressionFactory expression = database.getExpressionFactory("MPLINE");
    expression = expression.eq("IBCONO", inCONO.toString());
    expression = expression.and(expression.le("IBPUSL", "75"));
    // K51 for MILK else it is K21
    if (inNMLK.isBlank() || inNMLK.equals("0")) {
      expression = expression.and(expression.eq("IBORTY", "K51"));
      expression = expression.and(expression.ge("IBPUSL", "35"));
    } else {
      expression = expression.and(expression.ge("IBPUSL", "20"));
      expression = expression.and(expression.eq("IBORTY", "K21"));
    }
    expression = expression.and(expression.ne("IBUCA1", ""));

    if (!inFWDT.isBlank()) {
      expression = expression.and(expression.ge("IBDWDT", inFWDT));
    }
    if (!inTWDT.isBlank()) {
      expression = expression.and(expression.le("IBDWDT", inTWDT));
    }
    if (!inWHLO.isBlank()) {
      expression = expression.and(expression.eq("IBWHLO", inWHLO));
    }
    if (!inLDID.isBlank()) {
      expression = expression.and(expression.eq("IBUCA1", inLDID));
    }

    return expression;
  }

  /**
   * Validate input fields
   */
  boolean isValidInput() {
    // Check From date
    if (!inFWDT.isBlank() && !inFWDT.equals("0") && !checkDate(inFWDT)) {
      mi.error("Invalid date ${inFWDT}");
      return false;
    }

    // Check To date
    if (!inTWDT.isBlank() && !inTWDT.equals("0") && !checkDate(inTWDT)) {
      mi.error("Invalid date ${inTWDT}");
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
    inFWDT = mi.inData.get("FWDT")?.trim() ?: "";
    inTWDT = mi.inData.get("TWDT")?.trim() ?: "";
    inSTAS = mi.inData.get("STAS")?.trim() ?: "";
    inWHLO = mi.inData.get("WHLO")?.trim() ?: "";
    inLDID = mi.inData.get("LDID")?.trim() ?: "";
    inNMLK = mi.inData.get("NMLK")?.trim() ?: "";

    XXSUNM = "";
    XXFUDS = "";
    XXPDST = "";
    XXLDID = "";
    XXSCTM = "";
  }

  /**
   * Check if valid date
   */
  boolean checkDate(String date) {
    return utility.call("DateUtil", "isValidDate", "uuuuMMdd", date);
  }
}