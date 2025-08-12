/**
 * @Name: LstEXTDTS.EXTDTS
 * @Description: List record on table EXTDTS
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class LstEXTDTS extends ExtendM3Transaction {
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
  private String inSNUM;
  final int MAX_RECORDS = mi.getMaxRecords() == 0 ? 10000 : mi.getMaxRecords();
  
  public LstEXTDTS(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inSNUM = mi.inData.get("SNUM") == null ? "" : mi.inData.get("SNUM").trim();
    
    DBAction query = database.table("EXTDTS").index("00").selectAllFields().build();
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
      mi.outData.put("DSEQ", data.get("EXDSEQ").toString());
      mi.outData.put("SNUM", data.get("EXSNUM").toString());
      mi.outData.put("LOCA", data.get("EXLOCA").toString());
      mi.outData.put("STAT", data.get("EXSTAT").toString());
      mi.outData.put("RRSN", data.get("EXRRSN").toString());
      mi.outData.put("PSRL", data.get("EXPSRL").toString());
      mi.outData.put("PSRS", data.get("EXPSRS").toString());
      mi.outData.put("PSRR", data.get("EXPSRR").toString());
      mi.outData.put("PSVR", data.get("EXPSVR").toString());
      mi.write();
    });
  }
}