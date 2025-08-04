/**
 * @Name: GetEXTCCO.EXTCCO
 * @Description: Get record on table EXTCCO
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *  1.1.0     250618   JHC      Added fields DIST, STOR, PRDY, PRTM
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class GetEXTCCO extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  private int inCONO;
  private String inDIVI;
  private String inDLIX;
  private String inINOU;
  
  public GetEXTCCO(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inDLIX = mi.inData.get("DLIX") == null ? "" : mi.inData.get("DLIX").trim();
    inINOU = mi.inData.get("INOU") == null ? "" : mi.inData.get("INOU").trim();
    DBAction query = database.table("EXTCCO").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDLIX", inDLIX as int);
    container.set("EXINOU", inINOU as int);
    if (!query.read(container)) {
      mi.error("Record does not exists");
      return;
    }
    mi.outData.put("CONO", container.get("EXCONO").toString());
    mi.outData.put("DIVI", container.get("EXDIVI").toString());
    mi.outData.put("DLIX", container.get("EXDLIX").toString());
    mi.outData.put("INOU", container.get("EXINOU").toString());
    mi.outData.put("UCA8", container.get("EXUCA8").toString());
    mi.outData.put("DIST", container.get("EXDIST").toString());
    mi.outData.put("STOR", container.get("EXSTOR").toString());
    mi.outData.put("PRDY", container.get("EXPRDY").toString());
    mi.outData.put("PRTM", container.get("EXPRTM").toString());
    mi.outData.put("ORNO", container.get("EXORNO").toString());
    mi.outData.put("ROUT", container.get("EXROUT").toString());
    mi.outData.put("LINB", container.get("EXLINB").toString());
    mi.outData.put("DSDT", container.get("EXDSDT").toString());
    
    mi.write();
  }
}