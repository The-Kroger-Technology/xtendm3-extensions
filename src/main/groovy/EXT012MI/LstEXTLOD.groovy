/**
 * @Name: LstEXTLOD.EXTLOD
 * @Description: List record on table EXTLOD
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class LstEXTLOD extends ExtendM3Transaction {
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
  final int MAX_RECORDS = mi.getMaxRecords() == 0 ? 10000 : mi.getMaxRecords();
  
  public LstEXTLOD(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    if (!inMNUM.isBlank()) {
      container.set("EXMNUM", inMNUM);
      index++;
    }
    query.readAll(container, index, MAX_RECORDS, {
      DBContainer data ->
      mi.outData.put("CONO", data.get("EXCONO").toString());
      mi.outData.put("DIVI", data.get("EXDIVI").toString());
      mi.outData.put("WHLO", data.get("EXWHLO").toString());
      mi.outData.put("LDID", data.get("EXLDID").toString());
      mi.outData.put("MNUM", data.get("EXMNUM").toString());
      mi.outData.put("TPND", data.get("EXTPND").toString());
      mi.outData.put("EPDT", data.get("EXEPDT").toString());
      mi.outData.put("EPTM", data.get("EXEPTM").toString());
      mi.outData.put("SUNO", data.get("EXSUNO").toString());
      mi.outData.put("SUNM", data.get("EXSUNM").toString());
      mi.outData.put("CMPT", data.get("EXCMPT").toString());
      mi.outData.put("OSMW", data.get("EXOSMW").toString());
      mi.outData.put("EVDT", data.get("EXEVDT").toString());
      mi.outData.put("EVTM", data.get("EXEVTM").toString());
      mi.outData.put("CLDT", data.get("EXCLDT").toString());
      mi.outData.put("DCWT", data.get("EXDCWT").toString());
      mi.outData.put("CLWT", data.get("EXCLWT").toString());
      mi.outData.put("TRWT", data.get("EXTRWT").toString());
      mi.outData.put("ADWT", data.get("EXADWT").toString());
      mi.outData.put("AMWT", data.get("EXAMWT").toString());
      mi.outData.put("SMWT", data.get("EXSMWT").toString());
      mi.outData.put("CMNO", data.get("EXCMNO").toString());
      mi.outData.put("CPWT", data.get("EXCPWT").toString());
      mi.outData.put("CBFP", data.get("EXCBFP").toString());
      mi.outData.put("CBFL", data.get("EXCBFL").toString());
      mi.outData.put("CPPT", data.get("EXCPPT").toString());
      mi.outData.put("CPLB", data.get("EXCPLB").toString());
      mi.outData.put("CNFP", data.get("EXCNFP").toString());
      mi.outData.put("CNFL", data.get("EXCNFL").toString());
      mi.outData.put("CTSP", data.get("EXCTSP").toString());
      mi.outData.put("CTSL", data.get("EXCTSL").toString());
      mi.outData.put("PBFP", data.get("EXPBFP").toString());
      mi.outData.put("PBFL", data.get("EXPBFL").toString());
      mi.outData.put("PPPT", data.get("EXPPPT").toString());
      mi.outData.put("PPLB", data.get("EXPPLB").toString());
      mi.outData.put("PNFP", data.get("EXPNFP").toString());
      mi.outData.put("PNFL", data.get("EXPNFL").toString());
      mi.outData.put("PTSP", data.get("EXPTSP").toString());
      mi.outData.put("PTSL", data.get("EXPTSL").toString());
      mi.outData.put("STIS", data.get("EXSTIS").toString());
      mi.outData.put("YEIS", data.get("EXYEIS").toString());
      mi.outData.put("MOIS", data.get("EXMOIS").toString());
      mi.write();
    });
  }
}