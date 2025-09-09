/**
 * @Name: LstEXTCCO.EXTCCO
 * @Description: List record on table EXTCCO
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date     User       Description
 *  1.0.0     YYddMM   User       Initial Release - Generated from XtendM3 CRUD Generator
 *  1.1.0     250618   JHC        Added fields DIST, STOR, PRDY, PRTM
 *  1.1.1     250904   JTAPANG    Add Comments, Description, change cast from int to long, remove unnecessary code.   
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class LstEXTCCO extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  
  private int inCONO;
  private String inDLIX;
  private String inINOU;
  
  public LstEXTCCO(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
    this.mi = mi;
    this.utility = utility;
    this.logger = logger;
    this.program = program;
    this.miCaller = miCaller;
    this.database = database;
  }
  public void main() {
    inCONO = mi.in.get("CONO") == null ? program.LDAZD.CONO as int : mi.in.get("CONO") as int;
    inDLIX = mi.inData.get("DLIX") == null ? "" : mi.inData.get("DLIX").trim();
    inINOU = mi.inData.get("INOU") == null ? "" : mi.inData.get("INOU").trim();
    
    DBAction query = database.table("EXTCCO").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    int index = 1;
    if (!inDLIX.isBlank()) {
      container.set("EXDLIX", inDLIX as long);
      index++;
    }
    if (!inINOU.isBlank()) {
      container.set("EXINOU", inINOU as int);
      index++;
    }
    query.readAll(container, index, 10000, {
      DBContainer data ->
      mi.outData.put("CONO", data.get("EXCONO").toString());
      mi.outData.put("DIVI", data.get("EXDIVI").toString());
      mi.outData.put("DLIX", data.get("EXDLIX").toString());
      mi.outData.put("INOU", data.get("EXINOU").toString());
      mi.outData.put("BAID", data.get("EXBAID").toString());
      mi.outData.put("DIST", data.get("EXDIST").toString());
      mi.outData.put("STOR", data.get("EXSTOR").toString());
      mi.outData.put("PRDY", data.get("EXPRDY").toString());
      mi.outData.put("PRTM", data.get("EXPRTM").toString());
      mi.outData.put("ORNO", data.get("EXORNO").toString());
      mi.outData.put("ROUT", data.get("EXROUT").toString());
      mi.outData.put("LINB", data.get("EXLINB").toString());
      mi.outData.put("DSDT", data.get("EXDSDT").toString());
      mi.write();
    });
  }
}