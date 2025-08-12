/**
 * @Name: GetEXTTKS.EXTTKS
 * @Description: Get record on table EXTTKS
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class GetEXTTKS extends ExtendM3Transaction {
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
  
  public GetEXTTKS(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    container.set("EXTKID", inTKID);
    container.set("EXSNUM", inSNUM);
    if (!query.read(container)) {
      mi.error("Record does not exists");
      return;
    }
    mi.outData.put("CONO", container.get("EXCONO").toString());
    mi.outData.put("DIVI", container.get("EXDIVI").toString());
    mi.outData.put("WHLO", container.get("EXWHLO").toString());
    mi.outData.put("LDID", container.get("EXLDID").toString());
    mi.outData.put("TKID", container.get("EXTKID").toString());
    mi.outData.put("SNUM", container.get("EXSNUM").toString());
    mi.outData.put("SLOC", container.get("EXSLOC").toString());
    mi.outData.put("SDES", container.get("EXSDES").toString());
    mi.outData.put("SSTA", container.get("EXSSTA").toString());
    mi.outData.put("RDES", container.get("EXRDES").toString());
    mi.outData.put("PRSL", container.get("EXPRSL").toString());
    mi.outData.put("PRSD", container.get("EXPRSD").toString());
    mi.outData.put("PRSS", container.get("EXPRSS").toString());
    mi.outData.put("PRRD", container.get("EXPRRD").toString());
    mi.outData.put("PTSV", container.get("EXPTSV").toString());
    mi.write();
  }
}