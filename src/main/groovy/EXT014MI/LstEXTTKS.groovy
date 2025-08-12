/**
 * @Name: LstEXTTKS.EXTTKS
 * @Description: List record on table EXTTKS
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class LstEXTTKS extends ExtendM3Transaction {
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
  private String inTKID;
  private String inSNUM;
  final int MAX_RECORDS = mi.getMaxRecords() == 0 ? 10000 : mi.getMaxRecords();
  
  public LstEXTTKS(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inSNUM = mi.inData.get("SNUM") == null ? "" : mi.inData.get("SNUM").trim();
    
    DBAction query = database.table("EXTTKS").index("00").selectAllFields().build();
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
    if (!inSNUM.isBlank()) {
      container.set("EXSNUM", inSNUM);
      index++;
    }
    query.readAll(container, index, MAX_RECORDS, {
      DBContainer data ->
      mi.outData.put("CONO", data.get("EXCONO").toString());
      mi.outData.put("DIVI", data.get("EXDIVI").toString());
      mi.outData.put("WHLO", data.get("EXWHLO").toString());
      mi.outData.put("LDID", data.get("EXLDID").toString());
      mi.outData.put("TKID", data.get("EXTKID").toString());
      mi.outData.put("SNUM", data.get("EXSNUM").toString());
      mi.outData.put("SLOC", data.get("EXSLOC").toString());
      mi.outData.put("SDES", data.get("EXSDES").toString());
      mi.outData.put("SSTA", data.get("EXSSTA").toString());
      mi.outData.put("RDES", data.get("EXRDES").toString());
      mi.outData.put("PRSL", data.get("EXPRSL").toString());
      mi.outData.put("PRSD", data.get("EXPRSD").toString());
      mi.outData.put("PRSS", data.get("EXPRSS").toString());
      mi.outData.put("PRRD", data.get("EXPRRD").toString());
      mi.outData.put("PTSV", data.get("EXPTSV").toString());
      mi.write();
    });
  }
}