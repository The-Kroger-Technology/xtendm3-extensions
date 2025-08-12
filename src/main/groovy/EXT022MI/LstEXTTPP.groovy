/**
 * @Name: LstEXTTPP.EXTTPP
 * @Description: List record on table EXTTPP
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class LstEXTTPP extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  private int inCONO, inPSEQ, inTPLS, inEVDT, inEVTM, inCHNO, inRGDT, inRGTM, inLMDT;
  private String inDIVI;
  private String inWHLO;
  private String inLDID;
  private String inDESC;
  private String inBRMJ;
  private String inBRMN;
  private String inNOTI;
  private String inBGWT;
  private String inERMJ;
  private String inERMN;
  private String inENDW;
  private String inFINW;
  private String inGALS;
  private String inPTEM;
  private String inHTEM;
  private String inLSTM;
  private String inULTM;
  private String inRMET;
  private String inNOTE;
  private String inTKNO;
  private String inTRUM;
  private String inTMKG;
  private String inTMKT;
  private String inCHID;
  final int MAX_RECORDS = mi.getMaxRecords() == 0 ? 10000 : mi.getMaxRecords();
  
  public LstEXTTPP(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inPSEQ = mi.in.get("PSEQ") == null ? 0 : mi.in.get("PSEQ") as Integer;
    inTPLS = mi.in.get("TPLS") == null ? 0 : mi.in.get("TPLS") as Integer;
    
    DBAction query = database.table("EXTTPP").index("00").selectAllFields().build();
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
    if (inPSEQ != 0) {
      container.set("EXPSEQ", inPSEQ);
      index++;
    }
    if (inTPLS != 0) {
      container.set("EXTPLS", inTPLS);
      index++;
    }
    query.readAll(container, index, MAX_RECORDS, {
      DBContainer data ->
      mi.outData.put("CONO", data.get("EXCONO").toString());
      mi.outData.put("DIVI", data.get("EXDIVI").toString());
      mi.outData.put("WHLO", data.get("EXWHLO").toString());
      mi.outData.put("LDID", data.get("EXLDID").toString());
      mi.outData.put("PSEQ", data.get("EXPSEQ").toString());
      mi.outData.put("TPLS", data.get("EXTPLS").toString());
      mi.outData.put("EVDT", data.get("EXEVDT").toString());
      mi.outData.put("EVTM", data.get("EXEVTM").toString());
      mi.outData.put("DESC", data.get("EXDESC").toString());
      mi.outData.put("BRMJ", data.get("EXBRMJ").toString());
      mi.outData.put("BRMN", data.get("EXBRMN").toString());
      mi.outData.put("NOTI", data.get("EXNOTI").toString());
      mi.outData.put("BGWT", data.get("EXBGWT").toString());
      mi.outData.put("ERMJ", data.get("EXERMJ").toString());
      mi.outData.put("ERMN", data.get("EXERMN").toString());
      mi.outData.put("ENDW", data.get("EXENDW").toString());
      mi.outData.put("FINW", data.get("EXFINW").toString());
      mi.outData.put("GALS", data.get("EXGALS").toString());
      mi.outData.put("PTEM", data.get("EXPTEM").toString());
      mi.outData.put("HTEM", data.get("EXHTEM").toString());
      mi.outData.put("LSTM", data.get("EXLSTM").toString());
      mi.outData.put("ULTM", data.get("EXULTM").toString());
      mi.outData.put("RMET", data.get("EXRMET").toString());
      mi.outData.put("NOTE", data.get("EXNOTE").toString());
      mi.outData.put("TKNO", data.get("EXTKNO").toString());
      mi.outData.put("TRUM", data.get("EXTRUM").toString());
      mi.outData.put("TMKG", data.get("EXTMKG").toString());
      mi.outData.put("TMKT", data.get("EXTMKT").toString());
      mi.write();
    });
  }
}