/**
 * @Name: GetEXTLOD.EXTLOD
 * @Description: Get record on table EXTLOD
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     250704   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class GetEXTLOD extends ExtendM3Transaction {
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
  private String inMNUM;
  
  public GetEXTLOD(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inMNUM = mi.inData.get("MNUM") == null ? "" : mi.inData.get("MNUM").trim();
    
    DBAction query = database.table("EXTLOD").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    container.set("EXMNUM", inMNUM);
    if (!query.read(container)) {
      mi.error("Record does not exists");
      return;
    }
    mi.outData.put("CONO", container.get("EXCONO").toString());
    mi.outData.put("DIVI", container.get("EXDIVI").toString());
    mi.outData.put("WHLO", container.get("EXWHLO").toString());
    mi.outData.put("LDID", container.get("EXLDID").toString());
    mi.outData.put("MNUM", container.get("EXMNUM").toString());
    mi.outData.put("TPND", container.get("EXTPND").toString());
    mi.outData.put("EPDT", container.get("EXEPDT").toString());
    mi.outData.put("EPTM", container.get("EXEPTM").toString());
    mi.outData.put("SUNO", container.get("EXSUNO").toString());
    mi.outData.put("SUNM", container.get("EXSUNM").toString());
    mi.outData.put("CMPT", container.get("EXCMPT").toString());
    mi.outData.put("OSMW", container.get("EXOSMW").toString());
    mi.outData.put("EVDT", container.get("EXEVDT").toString());
    mi.outData.put("EVTM", container.get("EXEVTM").toString());
    mi.outData.put("CLDT", container.get("EXCLDT").toString());
    mi.outData.put("DCWT", container.get("EXDCWT").toString());
    mi.outData.put("CLWT", container.get("EXCLWT").toString());
    mi.outData.put("TRWT", container.get("EXTRWT").toString());
    mi.outData.put("ADWT", container.get("EXADWT").toString());
    mi.outData.put("AMWT", container.get("EXAMWT").toString());
    mi.outData.put("SMWT", container.get("EXSMWT").toString());
    mi.outData.put("CMNO", container.get("EXCMNO").toString());
    mi.outData.put("CPWT", container.get("EXCPWT").toString());
    mi.outData.put("CBFP", container.get("EXCBFP").toString());
    mi.outData.put("CBFL", container.get("EXCBFL").toString());
    mi.outData.put("CPPT", container.get("EXCPPT").toString());
    mi.outData.put("CPLB", container.get("EXCPLB").toString());
    mi.outData.put("CNFP", container.get("EXCNFP").toString());
    mi.outData.put("CNFL", container.get("EXCNFL").toString());
    mi.outData.put("CTSP", container.get("EXCTSP").toString());
    mi.outData.put("CTSL", container.get("EXCTSL").toString());
    mi.outData.put("PBFP", container.get("EXPBFP").toString());
    mi.outData.put("PBFL", container.get("EXPBFL").toString());
    mi.outData.put("PPPT", container.get("EXPPPT").toString());
    mi.outData.put("PPLB", container.get("EXPPLB").toString());
    mi.outData.put("PNFP", container.get("EXPNFP").toString());
    mi.outData.put("PNFL", container.get("EXPNFL").toString());
    mi.outData.put("PTSP", container.get("EXPTSP").toString());
    mi.outData.put("PTSL", container.get("EXPTSL").toString());
    mi.outData.put("STIS", container.get("EXSTIS").toString());
    mi.outData.put("YEIS", container.get("EXYEIS").toString());
    mi.outData.put("MOIS", container.get("EXMOIS").toString());
    mi.write();
  }
}