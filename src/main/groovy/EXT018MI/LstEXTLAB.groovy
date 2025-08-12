/**
 * @Name: LstEXTLAB.EXTLAB
 * @Description: List record on table EXTLAB
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class LstEXTLAB extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  private int inCONO;
  private String inDIVI;
  private String inWHLO;
  private String inLDID;
  private String inLRID;
  private String inTKID;
  private String inLRIT;
  final int MAX_RECORDS = mi.getMaxRecords() == 0 ? 10000 : mi.getMaxRecords();
  
  public LstEXTLAB(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
   
    DBAction query = database.table("EXTLAB").index("00").selectAllFields().build();
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
     if (!inTKID.isBlank()) {
      container.set("EXTKID", inTKID);
      index++;
    }
     if (!inLRIT.isBlank()) {
      container.set("EXLRIT", inLRIT);
      index++;
    }
    if (!inLRID.isBlank()) {
      container.set("EXLRID", inLRID as int);
      index++;
    }
    query.readAll(container, index, MAX_RECORDS, {
      DBContainer data ->
      mi.outData.put("CONO", data.get("EXCONO").toString());
      mi.outData.put("DIVI", data.get("EXDIVI").toString());
      mi.outData.put("WHLO", data.get("EXWHLO").toString());
      mi.outData.put("LDID", data.get("EXLDID").toString());
      mi.outData.put("TKID", data.get("EXTKID").toString());
      mi.outData.put("LRIT", data.get("EXLRIT").toString());
      mi.outData.put("LRID", data.get("EXLRID").toString());
      mi.outData.put("LRNM", data.get("EXLRNM").toString());
      mi.outData.put("TSEQ", data.get("EXTSEQ").toString());
      mi.outData.put("CPID", data.get("EXCPID").toString());
      mi.outData.put("LRMN", data.get("EXLRMN").toString());
      mi.outData.put("LRMX", data.get("EXLRMX").toString());
      mi.outData.put("LABR", data.get("EXLABR").toString());
      mi.outData.put("LBRS", data.get("EXLBRS").toString());
      mi.outData.put("LBMN", data.get("EXLBMN").toString());
      mi.outData.put("LCFM", data.get("EXLCFM").toString());
      mi.write();
    });
  }
}