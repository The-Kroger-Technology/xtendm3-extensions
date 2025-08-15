/**
 * @Name: EXT201MI.GetManifestDet
 * @Description: Fetch information for a manifest
 * @Authors: Mark Jayson Mandap (M3GDS)
 *
 * @CHANGELOGS
 *  Version   Date    User          Description
 *  1.0.0     250611  22RR86TW86    Initial Release
 */

public class GetManifestDet extends ExtendM3Transaction {
  private final MIAPI mi;
  private final ProgramAPI program;
  private final DatabaseAPI database;
  private final MICallerAPI miCaller;

  private int inCONO;
  private String inDIVI;
  private String inWHLO;
  private String inLDID;
  private String inNMLK;

  public GetManifestDet(MIAPI mi, ProgramAPI program, DatabaseAPI database, MICallerAPI miCaller) {
    this.mi = mi;
    this.program = program;
    this.database = database;
    this.miCaller = miCaller;
  }

  public void main() {
    // Initialize input fields
    initializeInput();

    // Fetch Manifest data
    fetchManifestData();

    // Fetch Load data
    fetchLoadData();

    // Fetch Header data
    fetchHeaderData();

    // Fetch PO Line data
    fetchPOLineData();

    // Fetch Samples data
    fetchSampleData();

    // Fetch Tank
    fetchTankerData();

    // Fetch Pickup parts data
    fetchPickupPartsData();

    mi.outData.put("CONO", inCONO.toString());
    mi.outData.put("DIVI", inDIVI);
    mi.outData.put("WHLO", inWHLO);
    mi.outData.put("LDID", inLDID);
    mi.write();
  }

  /**
   * Fetch Manifest data
   */
  void fetchManifestData() {
    String XXTPND = "0";
    String XXMNUM = "";
    String XXFLOD = "0";
    String XXDCWT = "0";
    String XXTRWT = "0";
    String XXCLWT = "0";
    String XXWTVR = 0;
    String XXPBFP = "";
    String XXPPPT = "";
    String XXPNFP = "";

    Map<String, String> params = [
      CONO: inCONO.toString(),
      DIVI: inDIVI,
      WHLO: inWHLO,
      LDID: inLDID
    ];
    miCaller.call("EXT012MI", "LstEXTLOD", params, { Map<String, String> response ->
      if (response.error) {
        mi.error("EXT012MI.LstEXTLOD Error: ${response.errorMessage}");
        return;
      }

      XXTPND = response.TPND.trim();
      XXMNUM = response.MNUM.trim();
      XXDCWT = response.DCWT.trim();
      XXTRWT = response.TRWT.trim();
      XXCLWT = response.CLWT.trim();
      XXPBFP = response.PBFP.trim();
      XXPPPT = response.PPPT.trim();
      XXPNFP = response.PNFP.trim();

      double TPND = 0;
      double CLWT = 0;

      try {
        TPND = Double.parseDouble(XXTPND);
        CLWT = Double.parseDouble(XXTRWT);
      } catch (NumberFormatException e) {
        TPND = 0;
        CLWT = 0;
      }

      XXWTVR = TPND - CLWT;

      XXFLOD = "1";
    });

    mi.outData.put("TPND", XXTPND);
    mi.outData.put("MNUM", XXMNUM);
    mi.outData.put("DCWT", XXDCWT);
    mi.outData.put("TRWT", XXTRWT);
    mi.outData.put("CLWT", XXCLWT);
    mi.outData.put("WTVR", XXWTVR.toString());
    mi.outData.put("PBFP", XXPBFP.toString());
    mi.outData.put("PPPT", XXPPPT.toString());
    mi.outData.put("PNFP", XXPNFP.toString());
    mi.outData.put("FLOD", XXFLOD);
  }

  /**
   * Fetch Load data
   */
  void fetchLoadData() {
    String XXDSEQ = "0";
    String XXHAID = "";
    String XXPDST = "";
    String XXHANM = "";
    String XXTRFN = "";
    String XXTRLN = "";
    String XXTRID = "";
    String XXMLOC = "";
    String XXSTNO = "";
    String XXPV01 = "0";
    String XXPV02 = "0";
    String XXPV03 = "0";
    String XXPV04 = "0";
    String XXPV05 = "0";
    String XXPV06 = "0";
    String XXFDLG = "0";

    Map<String, String> params = [
      CONO: inCONO.toString(),
      DIVI: inDIVI,
      WHLO: inWHLO,
      LDID: inLDID
    ];
    miCaller.call("EXT015MI", "LstEXTDLG", params, { Map<String, String> response ->
      if (response.error) {
        mi.error("EXT015MI.LstEXTDLG Error: ${response.errorMessage}");
        return;
      }

      XXDSEQ = response.DSEQ.trim();
      XXHAID = response.HAID.trim();
      XXPDST = response.PDST.trim();
      XXHANM = response.HANM.trim();
      XXTRFN = response.TRFN.trim();
      XXTRLN = response.TRLN.trim();
      XXTRID = response.TRID.trim();
      XXMLOC = response.MLOC.trim();
      XXSTNO = response.STNO.trim();
      XXPV01 = response.PV01.trim().toUpperCase() == "Y" ? "1" : "0";
      XXPV02 = response.PV02.trim().toUpperCase() == "Y" ? "1" : "0";
      XXPV03 = response.PV03.trim().toUpperCase() == "Y" ? "1" : "0";
      XXPV04 = response.PV04.trim().toUpperCase() == "Y" ? "1" : "0";
      XXPV05 = response.PV05.trim().toUpperCase() == "Y" ? "1" : "0";
      XXPV06 = response.PV06.trim().toUpperCase() == "Y" ? "1" : "0";
      XXFDLG = "1";
    });

    mi.outData.put("DSEQ", XXDSEQ);
    mi.outData.put("HAID", XXHAID);
    mi.outData.put("PDST", XXPDST);
    mi.outData.put("HANM", XXHANM);
    mi.outData.put("TRFN", XXTRFN);
    mi.outData.put("TRLN", XXTRLN);
    mi.outData.put("TRID", XXTRID);
    mi.outData.put("MLOC", XXMLOC);
    mi.outData.put("STNO", XXSTNO);
    mi.outData.put("PV01", XXPV01);
    mi.outData.put("PV02", XXPV02);
    mi.outData.put("PV03", XXPV03);
    mi.outData.put("PV04", XXPV04);
    mi.outData.put("PV05", XXPV05);
    mi.outData.put("PV06", XXPV06);
    mi.outData.put("FDLG", XXFDLG);
  }

  /**
   * Fetch Header data
   */
  void fetchHeaderData() {
    String XXRTNO = "";
    String XXCTTP = "";
    String XXRFNO = "";
    String XXFHDR = "0";

    Map<String, String> params = [
      CONO: inCONO.toString(),
      DIVI: inDIVI,
      WHLO: inWHLO,
      LDID: inLDID
    ];
    miCaller.call("EXT011MI", "GetEXTHDR", params, { Map<String, String> response ->
      if (response.error) {
        mi.error("EXT011MI.GetEXTHDR Error: ${response.errorMessage}");
        return;
      }

      XXRTNO = response.RTNO.trim();
      XXCTTP = response.CTTP.trim();
      XXRFNO = response.RFNO.trim();
      XXFHDR = "1";
    });

    mi.outData.put("RTNO", XXRTNO);
    mi.outData.put("CTTP", XXCTTP);
    mi.outData.put("RFNO", XXRFNO);
    mi.outData.put("FHDR", XXFHDR);
  }

  /**
   * Fetch PO line data
   */
  void fetchPOLineData() {
    String XXPUNO = '';
    String XXORTY = '';

    ExpressionFactory expression = database.getExpressionFactory("MPLINE");
    expression = expression.eq("IBUCA1", inLDID);
    // K51 for MILK else it is K21
    if (inNMLK.isBlank() || inNMLK.equals("0")) {
      expression = expression.and(expression.eq("IBORTY", "K51"));
      expression = expression.and(expression.ge("IBPUSL", "35"));
    } else {
      expression = expression.and(expression.ge("IBPUSL", "20"));
      expression = expression.and(expression.eq("IBORTY", "K21"));
    }
    expression = expression.and(expression.le("IBPUSL", "75"));

    DBAction dbaMPLINE = database
        .table("MPLINE")
        .index("00")
        .matching(expression)
        .selection("IBPUNO", "IBORTY")
        .build();
    DBContainer PLINE = dbaMPLINE.getContainer();
    PLINE.set("IBCONO", inCONO);
    dbaMPLINE.readAll(PLINE, 1, 1, { DBContainer data ->
      XXPUNO = data.getString("IBPUNO");
      XXORTY = data.getString("IBORTY");
    });

    mi.outData.put("PUNO", XXPUNO);
    mi.outData.put("ORTY", XXORTY);
  }

  /**
   * Fetch Sample data
   */
  void fetchSampleData() {
    String XXPSEQ = "0";
    String XXSPNO = "";
    String XXMKTP = "";
    String XXMKGD = "";
    String XXFTPS = "0";

    Map<String, String> params = [
      CONO: inCONO.toString(),
      DIVI: inDIVI,
      WHLO: inWHLO,
      LDID: inLDID
    ];
    miCaller.call("EXT021MI", "LstEXTTPS", params, { Map<String, String> response ->
      if (response.error) {
        mi.error("EXT021MI.LstEXTTPS Error: ${response.errorMessage}");
        return;
      }

      XXPSEQ = response.PSEQ.trim();
      XXSPNO = response.SPNO.trim();
      XXMKTP = response.MKTP.trim();
      XXMKGD = response.MKGD.trim();
      XXFTPS = "1";
    });

    mi.outData.put("PSEQ", XXPSEQ);
    mi.outData.put("SPNO", XXSPNO);
    mi.outData.put("MKTP", XXMKTP);
    mi.outData.put("MKGD", XXMKGD);
    mi.outData.put("FTPS", XXFTPS);
  }

  /**
   * Fetch Tanker data
   */
  void fetchTankerData() {
    String XXTKID = "";
    String XXPWDT = "0";
    String XXWTDT = "0";
    String XXPWTM = "0";
    String XXWTTM = "0";
    String XXTPOS = "";
    String XXLTWL = "";
    String XXRGST = "";
    String XXRGMO = "0";
    String XXRGYE = "0";
    String XXTEMP = "";
    String XXFTNK = "0";

    Map<String, String> params = [
      CONO: inCONO.toString(),
      DIVI: inDIVI,
      WHLO: inWHLO,
      LDID: inLDID
    ];
    miCaller.call("EXT013MI", "LstEXTTNK", params, { Map<String, String> response ->
      if (response.error) {
        mi.error("EXT013MI.LstEXTTNK Error: ${response.errorMessage}");
        return;
      }

      XXTKID = response.TKID.trim();
      XXPWDT = response.PWDT.trim();
      XXWTDT = response.WTDT.trim();
      XXPWTM = response.PWTM.trim();
      XXWTTM = response.WTTM.trim();
      XXTPOS = response.TPOS.trim();
      XXLTWL = response.LTWL.trim();
      XXRGST = response.RGST.trim();
      XXRGMO = response.RGMO.trim();
      XXRGYE = response.RGYE.trim();
      XXTEMP = response.TEMP.trim();
      XXFTNK = "1";
    });

    mi.outData.put("TKID", XXTKID);
    mi.outData.put("PWDT", XXPWDT);
    mi.outData.put("WTDT", XXWTDT);
    mi.outData.put("PWTM", XXPWTM);
    mi.outData.put("WTTM", XXWTTM);
    mi.outData.put("TPOS", XXTPOS);
    mi.outData.put("LTWL", XXLTWL);
    mi.outData.put("RGST", XXRGST);
    mi.outData.put("RGMO", XXRGMO);
    mi.outData.put("RGYE", XXRGYE);
    mi.outData.put("TEMP", XXTEMP);
    mi.outData.put("FTNK", XXFTNK);
  }

  /**
   * Fetch pickup parts data
   */
  void fetchPickupPartsData() {
    String XXLSTM = "";
    String XXFTPP = "0";

    Map<String, String> params = [
      CONO: inCONO.toString(),
      DIVI: inDIVI,
      WHLO: inWHLO,
      LDID: inLDID
    ];
    miCaller.call("EXT022MI", "LstEXTTPP", params, { Map<String, String> response ->
      if (response.error) {
        mi.error("EXT022MI.LstEXTTPP Error: ${response.errorMessage}");
        return;
      }

      XXLSTM = response.LSTM.trim();
      XXFTPP = "1";
    });

    mi.outData.put("LSTM", XXLSTM);
    mi.outData.put("FTPP", XXFTPP);
  }

  /**
   * Initialize input fields
   */
  void initializeInput() {
    inCONO = mi.in.get("CONO");
    inDIVI = mi.in.get("DIVI");
    inWHLO = mi.in.get("WHLO");
    inLDID = mi.in.get("LDID");
    inNMLK = mi.inData.get("NMLK")?.trim() ?: "";
  }
}