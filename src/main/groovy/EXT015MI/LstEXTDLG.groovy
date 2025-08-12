/**
 * @Name: LstEXTDLG.EXTDLG
 * @Description: List record on table EXTDLG
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class LstEXTDLG extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  private int inCONO, inDSEQ;
  private String inDIVI;
  private String inWHLO;
  private String inLDID;
  final int MAX_RECORDS = mi.getMaxRecords() == 0 ? 10000 : mi.getMaxRecords();
  
  public LstEXTDLG(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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

    DBAction query = database.table("EXTDL1").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    int index = 1;
    if (!inDIVI.isBlank()) {
      container.set("EXDIVI", inDIVI);
      index++;
    }
    if (!inWHLO.isBlank()) {
      container.set("EXWHLO", inWHLO);
      index++;
    }
    if (!inLDID.isBlank()) {
      container.set("EXLDID", inLDID);
      index++;
    }
    if (inDSEQ != 0) {
      container.set("EXDSEQ", inDSEQ);
      index++;
    }
    
    query.readAll(container, index, MAX_RECORDS, {
      DBContainer data ->
      mi.outData.put("CONO", data.get("EXCONO").toString());
      mi.outData.put("DIVI", data.get("EXDIVI").toString());
      mi.outData.put("WHLO", data.get("EXWHLO").toString());
      mi.outData.put("LDID", data.get("EXLDID").toString());
      mi.outData.put("DSEQ", data.get("EXDSEQ").toString());
      mi.outData.put("PDST", data.get("EXPDST").toString());
      mi.outData.put("PRSD", data.get("EXPRSD").toString());
      mi.outData.put("PRST", data.get("EXPRST").toString());
      mi.outData.put("PRED", data.get("EXPRED").toString());
      mi.outData.put("PRET", data.get("EXPRET").toString());
      mi.outData.put("BODO", data.get("EXBODO").toString());
      mi.outData.put("ENDT", data.get("EXENDT").toString());
      mi.outData.put("ENTM", data.get("EXENTM").toString());
      mi.outData.put("EODO", data.get("EXEODO").toString());
      mi.outData.put("STDT", data.get("EXSTDT").toString());
      mi.outData.put("STTM", data.get("EXSTTM").toString());
      mi.outData.put("EVDT", data.get("EXEVDT").toString());
      mi.outData.put("EVTM", data.get("EXEVTM").toString());
      mi.outData.put("ACTD", data.get("EXACTD").toString());
      mi.outData.put("ARDT", data.get("EXARDT").toString());
      mi.outData.put("ARTM", data.get("EXARTM").toString());
      mi.outData.put("NETW", data.get("EXNETW").toString());
      mi.outData.put("NOWT", data.get("EXNOWT").toString());
      mi.outData.put("DPDT", data.get("EXDPDT").toString());
      mi.outData.put("DPTM", data.get("EXDPTM").toString());
      mi.outData.put("DTEM", data.get("EXDTEM").toString());
      mi.outData.put("DECL", data.get("EXDECL").toString());
      mi.outData.put("DEID", data.get("EXDEID").toString());
      mi.outData.put("DENM", data.get("EXDENM").toString());
      mi.outData.put("DRMD", data.get("EXDRMD").toString());
      mi.outData.put("SIRD", data.get("EXSIRD").toString());
      mi.outData.put("STNO", data.get("EXSTNO").toString());
      mi.outData.put("PRMD", data.get("EXPRMD").toString());
      mi.outData.put("BNSC", data.get("EXBNSC").toString());
      mi.outData.put("GALS", data.get("EXGALS").toString());
      mi.outData.put("CDAT", data.get("EXCDAT").toString());
      mi.outData.put("CTIM", data.get("EXCTIM").toString());
      mi.outData.put("ACDT", data.get("EXACDT").toString());
      mi.outData.put("ACTM", data.get("EXACTM").toString());
      mi.outData.put("GFAD", data.get("EXGFAD").toString());
      mi.outData.put("GFAT", data.get("EXGFAT").toString());
      mi.outData.put("LTID", data.get("EXLTID").toString());
      mi.outData.put("HAID", data.get("EXHAID").toString());
      mi.outData.put("HANI", data.get("EXHANI").toString());
      
      ExpressionFactory exp = database.getExpressionFactory("EXTDL2");
      exp = exp.eq("EXRGDT", data.get("EXRGDT").toString()).and(exp.eq("EXRGTM", data.get("EXRGTM").toString()));
    
      DBAction query1 = database.table("EXTDL2").index("00").matching(exp).selectAllFields().build();
      DBContainer container1 = query1.createContainer();
      container1.set("EXCONO", data.get("EXCONO"));
      container1.set("EXDIVI", data.get("EXDIVI").toString());
      container1.set("EXWHLO", data.get("EXWHLO").toString());
      container1.set("EXLDID", data.get("EXLDID"));
      container1.set("EXDSEQ", data.get("EXDSEQ"));
      
      if (query1.read(container1)) {
        mi.outData.put("HANM", container1.get("EXHANM").toString());
        mi.outData.put("TRID", container1.get("EXTRID").toString());
        mi.outData.put("TRFN", container1.get("EXTRFN").toString());
        mi.outData.put("TRLN", container1.get("EXTRLN").toString());
        mi.outData.put("TRPN", container1.get("EXTRPN").toString());
        mi.outData.put("TRSN", container1.get("EXTRSN").toString());
        mi.outData.put("MLOC", container1.get("EXMLOC").toString());
        mi.outData.put("PV01", container1.get("EXPV01").toString());
        mi.outData.put("PV02", container1.get("EXPV02").toString());
        mi.outData.put("PV03", container1.get("EXPV03").toString());
        mi.outData.put("PV04", container1.get("EXPV04").toString());
        mi.outData.put("PV05", container1.get("EXPV05").toString());
        mi.outData.put("PV06", container1.get("EXPV06").toString());
        mi.outData.put("PV07", container1.get("EXPV07").toString());
        mi.outData.put("PV08", container1.get("EXPV08").toString());
        mi.outData.put("PV09", container1.get("EXPV09").toString());
        mi.outData.put("PV10", container1.get("EXPV10").toString());
      };
    
      mi.write();
    });
  }
}