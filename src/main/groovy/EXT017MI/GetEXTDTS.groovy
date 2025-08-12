/**
 * @Name: GetEXTDTS.EXTDTS
 * @Description: Get record on table EXTDTS
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class GetEXTDTS extends ExtendM3Transaction {
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
  
  public GetEXTDTS(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    container.set("EXDSEQ", inDSEQ);
    container.set("EXSNUM", inSNUM);
    
    if (!query.read(container)) {
      mi.error("Record does not exists");
      return;
    }
    
    mi.outData.put("CONO", container.get("EXCONO").toString());
    mi.outData.put("DIVI", container.get("EXDIVI").toString());
    mi.outData.put("WHLO", container.get("EXWHLO").toString());
    mi.outData.put("LDID", container.get("EXLDID").toString());
    mi.outData.put("DSEQ", container.get("EXDSEQ").toString());
    mi.outData.put("SNUM", container.get("EXSNUM").toString());
    mi.outData.put("LOCA", container.get("EXLOCA").toString());
    mi.outData.put("STAT", container.get("EXSTAT").toString());
    mi.outData.put("RRSN", container.get("EXRRSN").toString());
    mi.outData.put("PSRL", container.get("EXPSRL").toString());
    mi.outData.put("PSRS", container.get("EXPSRS").toString());
    mi.outData.put("PSRR", container.get("EXPSRR").toString());
    mi.outData.put("PSVR", container.get("EXPSVR").toString());
    mi.write();
  }
}