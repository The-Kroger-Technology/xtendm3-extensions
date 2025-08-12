/**
 * @Name: LstEXTDLS.EXTDLS
 * @Description: List record on table EXTDLS
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class LstEXTDLS extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  private int inCONO, inDSEQ;
  private String inDIVI;
  private String inWHLO;
  private String inLDID;
  private String inSPNO;
  final int MAX_RECORDS = mi.getMaxRecords() == 0 ? 10000 : mi.getMaxRecords();
  
  public LstEXTDLS(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inDSEQ = mi.in.get("DSEQ") == null ? 0 : mi.in.get("DSEQ") as Integer;
    inSPNO = mi.inData.get("SPNO") == null ? "" : mi.inData.get("SPNO").trim();
    
    DBAction query = database.table("EXTDLS").index("00").selectAllFields().build();
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
    if (inDSEQ != 0) {
      container.set("EXDSEQ", inDSEQ);
      index++;
    }
    if (!inSPNO.isBlank()) {
      container.set("EXSPNO", inSPNO);
      index++;
    }
    query.readAll(container, index, MAX_RECORDS, {
      DBContainer data ->
      mi.outData.put("CONO", data.get("EXCONO").toString());
      mi.outData.put("DIVI", data.get("EXDIVI").toString());
      mi.outData.put("WHLO", data.get("EXWHLO").toString());
      mi.outData.put("LDID", data.get("EXLDID").toString());
      mi.outData.put("DSEQ", data.get("EXDSEQ").toString());
      mi.outData.put("SPNO", data.get("EXSPNO").toString());
      mi.outData.put("DESC", data.get("EXDESC").toString());
      mi.outData.put("POST", data.get("EXPOST").toString());
      mi.outData.put("CMPT", data.get("EXCMPT").toString());
      mi.outData.put("EVDT", data.get("EXEVDT").toString());
      mi.outData.put("EVTM", data.get("EXEVTM").toString());
      mi.write();
    });
  }
}