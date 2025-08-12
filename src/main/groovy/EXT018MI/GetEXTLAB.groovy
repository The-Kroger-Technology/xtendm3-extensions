/**
 * @Name: GetEXTLAB.EXTLAB
 * @Description: Get record on table EXTLAB
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class GetEXTLAB extends ExtendM3Transaction {
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
  
  public GetEXTLAB(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inLRID = mi.inData.get("LRID") == null ? "" : mi.inData.get("LRID").trim();
    inLRIT = mi.inData.get("LRIT") == null ? "" : mi.inData.get("LRIT").trim();

    DBAction query = database.table("EXTLAB").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    container.set("EXTKID", inTKID);
    container.set("EXLRIT", inLRIT);
    container.set("EXLRID", inLRID as int);
    if (!query.read(container)) {
      mi.error("Record does not exists");
      return;
    }
    mi.outData.put("CONO", container.get("EXCONO").toString());
    mi.outData.put("DIVI", container.get("EXDIVI").toString());
    mi.outData.put("WHLO", container.get("EXWHLO").toString());
    mi.outData.put("LDID", container.get("EXLDID").toString());
    mi.outData.put("TKID", container.get("EXTKID").toString());
    mi.outData.put("LRIT", container.get("EXLRIT").toString());
    mi.outData.put("LRID", container.get("EXLRID").toString());
    mi.outData.put("LRNM", container.get("EXLRNM").toString());
    mi.outData.put("TSEQ", container.get("EXTSEQ").toString());
    mi.outData.put("CPID", container.get("EXCPID").toString());
    mi.outData.put("LRMN", container.get("EXLRMN").toString());
    mi.outData.put("LRMX", container.get("EXLRMX").toString());
    mi.outData.put("LABR", container.get("EXLABR").toString());
    mi.outData.put("LBRS", container.get("EXLBRS").toString());
    mi.outData.put("LBMN", container.get("EXLBMN").toString());
    mi.outData.put("LCFM", container.get("EXLCFM").toString());
    mi.write();
  }
}