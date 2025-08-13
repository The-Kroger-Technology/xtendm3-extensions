/**
 * @Name: LstEXTTPR.EXTTPR
 * @Description: List record on table EXTTPR
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class LstEXTTPR extends ExtendM3Transaction {
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
  private String inEVDT;
  private String inEVTM;
  private String inPLCL;
  private String inPLID;
  private String inPLNA;
  private String inPDRM;
  private String inPSIR;
  private String inDISP;
  private String inCOMM;
  
  public LstEXTTPR(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inEVDT = mi.inData.get("EVDT") == null ? "" : mi.inData.get("EVDT").trim();
    inEVTM = mi.inData.get("EVTM") == null ? "" : mi.inData.get("EVTM").trim();
    inPLCL = mi.inData.get("PLCL") == null ? "" : mi.inData.get("PLCL").trim();
    inPLID = mi.inData.get("PLID") == null ? "" : mi.inData.get("PLID").trim();
    inPLNA = mi.inData.get("PLNA") == null ? "" : mi.inData.get("PLNA").trim();
    inPDRM = mi.inData.get("PDRM") == null ? "" : mi.inData.get("PDRM").trim();
    inPSIR = mi.inData.get("PSIR") == null ? "" : mi.inData.get("PSIR").trim();
    inDISP = mi.inData.get("DISP") == null ? "" : mi.inData.get("DISP").trim();
    inCOMM = mi.inData.get("COMM") == null ? "" : mi.inData.get("COMM").trim();
    DBAction query = database.table("EXTTPR").index("00").selectAllFields().build();
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
    if (!inINDN.isBlank()) {
      container.set("EXINDN", inINDN as int);
      index++;
    }
    if (!inREAS.isBlank()) {
      container.set("EXREAS", inREAS);
      index++;
    }
    query.readAll(container, index, 10000, {
      DBContainer data ->
      mi.outData.put("CONO", data.get("EXCONO").toString());
      mi.outData.put("DIVI", data.get("EXDIVI").toString());
      mi.outData.put("WHLO", data.get("EXWHLO").toString());
      mi.outData.put("LDID", data.get("EXLDID").toString());
      mi.outData.put("INDN", data.get("EXINDN").toString());
      mi.outData.put("REAS", data.get("EXREAS").toString());
      mi.outData.put("EVDT", data.get("EXEVDT").toString());
      mi.outData.put("EVTM", data.get("EXEVTM").toString());
      mi.outData.put("PLCL", data.get("EXPLCL").toString());
      mi.outData.put("PLID", data.get("EXPLID").toString());
      mi.outData.put("PLNA", data.get("EXPLNA").toString());
      mi.outData.put("PDRM", data.get("EXPDRM").toString());
      mi.outData.put("PSIR", data.get("EXPSIR").toString());
      mi.outData.put("DISP", data.get("EXDISP").toString());
      mi.outData.put("COMM", data.get("EXCOMM").toString());
      mi.write();
    });
  }
}