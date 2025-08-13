/**
 * @Name: GetEXTLTD.EXTLTD
 * @Description: Get record on table EXTLTD
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     20240408  JHC     Initial Release - Generated from XtendM3 CRUD Generator, additional changes
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class GetEXTLTD extends ExtendM3Transaction {
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
  public GetEXTLTD(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXTLIT", inTLIT);
    container.set("EXLTID", inLTID as int);
    if (!query.read(container)) {
      mi.error("Record does not exists");
      return;
    }
    mi.outData.put("CONO", container.get("EXCONO").toString());
    mi.outData.put("DIVI", container.get("EXDIVI").toString());
    mi.outData.put("WHLO", container.get("EXWHLO").toString());
    mi.outData.put("TLIT", container.get("EXTLIT").toString());
    mi.outData.put("LTID", container.get("EXLTID").toString());
    mi.outData.put("TSEQ", container.get("EXTSEQ").toString());
    mi.outData.put("TSNM", container.get("EXTSNM").toString());
    mi.outData.put("RGMN", container.get("EXRGMN").toString());
    mi.outData.put("RGMX", container.get("EXRGMX").toString());
    mi.outData.put("PLMN", container.get("EXPLMN").toString());
    mi.outData.put("PLMX", container.get("EXPLMX").toString());
    mi.outData.put("PLUS", container.get("EXPLUS").toString());
    mi.outData.put("COUS", container.get("EXCOUS").toString());
    mi.outData.put("COTS", container.get("EXCOTS").toString());
    mi.outData.put("ACTV", container.get("EXACTV").toString());
    mi.write();
  }
}