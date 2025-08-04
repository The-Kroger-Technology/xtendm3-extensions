/**
 * @Name: GetEXTTPR.EXTTPR
 * @Description: Get record on table EXTTPR
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class GetEXTTPR extends ExtendM3Transaction {
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
  private String inINDN;
  private String inREAS;
  
  public GetEXTTPR(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inLDID = mi.inData.get("LDID") == null ? "" : mi.inData.get("LDID").trim();
    inINDN = mi.inData.get("INDN") == null ? "" : mi.inData.get("INDN").trim();
    inREAS = mi.inData.get("REAS") == null ? "" : mi.inData.get("REAS").trim();
    
    DBAction query = database.table("EXTTPR").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    container.set("EXINDN", inINDN as int);
    container.set("EXREAS", inREAS);
    if (!query.read(container)) {
      mi.error("Record does not exists");
      return;
    }
    mi.outData.put("CONO", container.get("EXCONO").toString());
    mi.outData.put("DIVI", container.get("EXDIVI").toString());
    mi.outData.put("WHLO", container.get("EXWHLO").toString());
    mi.outData.put("LDID", container.get("EXLDID").toString());
    mi.outData.put("INDN", container.get("EXINDN").toString());
    mi.outData.put("REAS", container.get("EXREAS").toString());
    mi.outData.put("EVDT", container.get("EXEVDT").toString());
    mi.outData.put("EVTM", container.get("EXEVTM").toString());
    mi.outData.put("PLCL", container.get("EXPLCL").toString());
    mi.outData.put("PLID", container.get("EXPLID").toString());
    mi.outData.put("PLNA", container.get("EXPLNA").toString());
    mi.outData.put("PDRM", container.get("EXPDRM").toString());
    mi.outData.put("PSIR", container.get("EXPSIR").toString());
    mi.outData.put("DISP", container.get("EXDISP").toString());
    mi.outData.put("COMM", container.get("EXCOMM").toString());
    mi.write();
  }
}