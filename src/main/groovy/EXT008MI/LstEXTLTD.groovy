/**
 * @Name: LstEXTLTD.EXTLTD
 * @Description: List record on table EXTLTD
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     20240408  JHC     Initial Release - Generated from XtendM3 CRUD Generator, additional changes
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class LstEXTLTD extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  private int inCONO;
  private String inDIVI;
  private String inWHLO;
  private String inTLIT;
  private String inLTID;
  
  public LstEXTLTD(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
    this.mi = mi;
    this.utility = utility;
    this.logger = logger;
    this.program = program;
    this.miCaller = miCaller;
    this.database = database;
  }
  public void main() {
    inCONO = mi.in.get("CONO") == null ? program.LDAZD.CONO as int : mi.in.get("CONO") as int;
    inDIVI = mi.inData.get("DIVI") == null ? "" : mi.inData.get("DIVI").trim();
    inWHLO = mi.inData.get("WHLO") == null ? "" : mi.inData.get("WHLO").trim();
    inTLIT = mi.inData.get("TLIT") == null ? "" : mi.inData.get("TLIT").trim();
    inLTID = mi.inData.get("LTID") == null ? "" : mi.inData.get("LTID").trim();
    
    DBAction query = database.table("EXTLTD").index("00").selectAllFields().build();
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
    if (!inTLIT.isBlank()) {
      container.set("EXTLIT", inTLIT);
      index++;
    }
    if (!inLTID.isBlank()) {
      container.set("EXLTID", inLTID as int);
      index++;
    }
    query.readAll(container, index, 10000, {
      DBContainer data ->
      mi.outData.put("CONO", data.get("EXCONO").toString());
      mi.outData.put("DIVI", data.get("EXDIVI").toString());
      mi.outData.put("WHLO", data.get("EXWHLO").toString());
      mi.outData.put("TLIT", data.get("EXTLIT").toString());
      mi.outData.put("LTID", data.get("EXLTID").toString());
      mi.outData.put("TSEQ", data.get("EXTSEQ").toString());
      mi.outData.put("TSNM", data.get("EXTSNM").toString());
      mi.outData.put("RGMN", data.get("EXRGMN").toString());
      mi.outData.put("RGMX", data.get("EXRGMX").toString());
      mi.outData.put("PLMN", data.get("EXPLMN").toString());
      mi.outData.put("PLMX", data.get("EXPLMX").toString());
      mi.outData.put("PLUS", data.get("EXPLUS").toString());
      mi.outData.put("COUS", data.get("EXCOUS").toString());
      mi.outData.put("COTS", data.get("EXCOTS").toString());
      mi.outData.put("ACTV", data.get("EXACTV").toString());
      mi.write();
    });
  }
}