/**
 * @Name: GetEXTCCL.EXTCCL
 * @Description: Get record on table EXTCCL
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date        User        Description
 *  1.0.0     20250818    JTAPANG     Initial Release - Generated from XtendM3 CRUD Generator. Add modifications
 *  1.1.0     20250911    JTAPANG     Add XtendM3 review comments.(Standard field validations, handling numeric exception, remove unused codes, Fix naming and variables)
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class GetEXTCCL extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  
  private int inCONO;
  private String inLINE;
  private String inDLIX;
  private String inINOU;
  
  public GetEXTCCL(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
    this.mi = mi;
    this.utility = utility;
    this.logger = logger;
    this.program = program;
    this.miCaller = miCaller;
    this.database = database;
  }
  public void main() {
    inCONO = mi.in.get("CONO") == null ? program.LDAZD.CONO as int : mi.in.get("CONO") as int;
    inLINE = mi.inData.get("LINE") == null ? "" : mi.inData.get("LINE").trim();
    inDLIX = mi.inData.get("DLIX") == null ? "" : mi.inData.get("DLIX").trim();
    inINOU = mi.inData.get("INOU") == null ? "" : mi.inData.get("INOU").trim();
    
    DBAction query = database.table("EXTCCL").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXLINE", inLINE as int);
    container.set("EXDLIX", inDLIX as long);
    container.set("EXINOU", inINOU as int);
    
    if (!query.read(container)) {
      mi.error("Record does not exists");
      return;
    }
    
    mi.outData.put("CONO", container.get("EXCONO").toString());
    mi.outData.put("LINE", container.get("EXLINE").toString());
    mi.outData.put("DLIX", container.get("EXDLIX").toString());
    mi.outData.put("INOU", container.get("EXINOU").toString());
    mi.outData.put("LINB", container.get("EXLINB").toString());
    mi.outData.put("ORNO", container.get("EXORNO").toString());
    mi.outData.put("PONR", container.get("EXPONR").toString());
    mi.outData.put("ITNO", container.get("EXITNO").toString());
    mi.outData.put("TEDS", container.get("EXTEDS").toString());
    mi.outData.put("DPLO", container.get("EXDPLO").toString());
    mi.outData.put("ORQT", container.get("EXORQT").toString());
    mi.outData.put("UNMS", container.get("EXUNMS").toString());
    mi.write();
  }
}
