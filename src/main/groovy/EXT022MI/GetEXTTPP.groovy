/**
 * @Name: GetEXTTPP.EXTTPP
 * @Description: Get record on table EXTTPP
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class GetEXTTPP extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  private int inCONO, inPSEQ, inTPLS;
  private String inDIVI;
  private String inWHLO;
  private String inLDID;
  
  public GetEXTTPP(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
    this.mi = mi;
    this.utility = utility;
    this.logger = logger;
    this.program = program;
    this.miCaller = miCaller;
    this.database = database;
  }
  public void main() {
    inCONO = mi.in.get("CONO") == null ? program.LDAZD.CONO as Integer : mi.in.get("CONO") as Integer;
    inDIVI = mi.inData.get("DIVI") == null ? "" : mi.inData.get("DIVI").trim();
    inWHLO = mi.inData.get("WHLO") == null ? "" : mi.inData.get("WHLO").trim();
    inLDID = mi.inData.get("LDID") == null ? "" : mi.inData.get("LDID").trim();
    inPSEQ = mi.in.get("PSEQ") == null ?  0 : mi.in.get("PSEQ") as Integer;
    inTPLS = mi.in.get("TPLS") == null ?  0 : mi.in.get("TPLS") as Integer;
    
    DBAction query = database.table("EXTTPP").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    container.set("EXPSEQ", inPSEQ);
    container.set("EXTPLS", inTPLS);
    if (!query.read(container)) {
      mi.error("Record does not exists");
      return;
    }
    mi.outData.put("CONO", container.get("EXCONO").toString());
    mi.outData.put("DIVI", container.get("EXDIVI").toString());
    mi.outData.put("WHLO", container.get("EXWHLO").toString());
    mi.outData.put("LDID", container.get("EXLDID").toString());
    mi.outData.put("PSEQ", container.get("EXPSEQ").toString());
    mi.outData.put("TPLS", container.get("EXTPLS").toString());
    mi.outData.put("EVDT", container.get("EXEVDT").toString());
    mi.outData.put("EVTM", container.get("EXEVTM").toString());
    mi.outData.put("DESC", container.get("EXDESC").toString());
    mi.outData.put("BRMJ", container.get("EXBRMJ").toString());
    mi.outData.put("BRMN", container.get("EXBRMN").toString());
    mi.outData.put("NOTI", container.get("EXNOTI").toString());
    mi.outData.put("BGWT", container.get("EXBGWT").toString());
    mi.outData.put("ERMJ", container.get("EXERMJ").toString());
    mi.outData.put("ERMN", container.get("EXERMN").toString());
    mi.outData.put("ENDW", container.get("EXENDW").toString());
    mi.outData.put("FINW", container.get("EXFINW").toString());
    mi.outData.put("GALS", container.get("EXGALS").toString());
    mi.outData.put("PTEM", container.get("EXPTEM").toString());
    mi.outData.put("HTEM", container.get("EXHTEM").toString());
    mi.outData.put("LSTM", container.get("EXLSTM").toString());
    mi.outData.put("ULTM", container.get("EXULTM").toString());
    mi.outData.put("RMET", container.get("EXRMET").toString());
    mi.outData.put("NOTE", container.get("EXNOTE").toString());
    mi.outData.put("TKNO", container.get("EXTKNO").toString());
    mi.outData.put("TRUM", container.get("EXTRUM").toString());
    mi.outData.put("TMKG", container.get("EXTMKG").toString());
    mi.outData.put("TMKT", container.get("EXTMKT").toString());
    mi.write();
  }
}