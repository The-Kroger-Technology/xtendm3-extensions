/**
 * @Name: LstEXTCCL.EXTCCL
 * @Description: List record on table EXTCCL
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LstEXTCCL extends ExtendM3Transaction {
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
  
  public LstEXTCCL(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    
    DBAction query = database.table("EXTCCL").index("10").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    int index = 1;
    if (!inDLIX.isBlank()) {
      container.set("EXDLIX", inDLIX as int);
      index++;
    }
    if (!inINOU.isBlank()) {
      container.set("EXINOU", inINOU as int);
      index++;
    }
    if (!inLINE.isBlank()) {
      container.set("EXLINE", inLINE as int);
      index++;
    }
    query.readAll(container, index, 10000, {
      DBContainer data ->
      mi.outData.put("CONO", data.get("EXCONO").toString());
      mi.outData.put("LINE", data.get("EXLINE").toString());
      mi.outData.put("DLIX", data.get("EXDLIX").toString());
      mi.outData.put("INOU", data.get("EXINOU").toString());
      mi.outData.put("LINB", data.get("EXLINB").toString());
      mi.outData.put("ORNO", data.get("EXORNO").toString());
      mi.outData.put("PONR", data.get("EXPONR").toString());
      mi.outData.put("ITNO", data.get("EXITNO").toString());
      mi.outData.put("TEDS", data.get("EXTEDS").toString());
      mi.outData.put("DPLO", data.get("EXDPLO").toString());
      mi.outData.put("ORQT", data.get("EXORQT").toString());
      mi.outData.put("UNMS", data.get("EXUNMS").toString());
      mi.write();
    });
  }
}