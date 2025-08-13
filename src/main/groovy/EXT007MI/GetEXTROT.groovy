/**
 * @Name: GetEXTROT.EXTROT
 * @Description: Get record on table EXTROT
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class GetEXTROT extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  private int inCONO;
  private String inDIVI;
  private String inWHLO;
  private String inRNBR;
  private String inRHID;
  
  public GetEXTROT(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inRNBR = mi.inData.get("RNBR") == null ? "" : mi.inData.get("RNBR").trim();
    inRHID = mi.inData.get("RHID") == null ? "" : mi.inData.get("RHID").trim();
    DBAction query = database.table("EXTROT").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXRNBR", inRNBR);
    container.set("EXRHID", inRHID);
    if (!query.read(container)) {
      mi.error("Record does not exists");
      return;
    }
    mi.outData.put("CONO", container.get("EXCONO").toString());
    mi.outData.put("DIVI", container.get("EXDIVI").toString());
    mi.outData.put("WHLO", container.get("EXWHLO").toString());
    mi.outData.put("RNBR", container.get("EXRNBR").toString());
    mi.outData.put("RHID", container.get("EXRHID").toString());
    mi.outData.put("ROHN", container.get("EXROHN").toString());
    mi.outData.put("RODE", container.get("EXRODE").toString());
    mi.outData.put("HPRT", container.get("EXHPRT").toString());
    mi.outData.put("STAT", container.get("EXSTAT").toString());
    mi.outData.put("DBFP", container.get("EXDBFP").toString());
    mi.write();
  }
}