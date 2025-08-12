/**
 * @Name: GetEXTTPL.EXTTPL
 * @Description: Get record on table EXTTPL
 * @Authors:  Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class GetEXTTPL extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;
  private int inCONO, inPSEQ;
  private String inDIVI;
  private String inWHLO;
  private String inLDID;
  
  public GetEXTTPL(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    inPSEQ = mi.in.get("PSEQ") == null ? 0 : mi.in.get("PSEQ") as Integer;
    
    DBAction query = database.table("EXTTPL").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    container.set("EXPSEQ", inPSEQ);
    
    if (!query.read(container)) {
      mi.error("Record does not exists");
      return;
    }
    mi.outData.put("CONO", container.get("EXCONO").toString());
    mi.outData.put("DIVI", container.get("EXDIVI").toString());
    mi.outData.put("WHLO", container.get("EXWHLO").toString());
    mi.outData.put("LDID", container.get("EXLDID").toString());
    mi.outData.put("PSEQ", container.get("EXPSEQ").toString());
    mi.outData.put("MNUM", container.get("EXMNUM").toString());
    mi.outData.put("EVDT", container.get("EXEVDT").toString());
    mi.outData.put("EVTM", container.get("EXEVTM").toString());
    mi.outData.put("DPDT", container.get("EXDPDT").toString());
    mi.outData.put("DPTM", container.get("EXDPTM").toString());
    mi.outData.put("ARDT", container.get("EXARDT").toString());
    mi.outData.put("ARTM", container.get("EXARTM").toString());
    mi.outData.put("FNWT", container.get("EXFNWT").toString());
    mi.outData.put("GRWT", container.get("EXGRWT").toString());
    mi.outData.put("STNO", container.get("EXSTNO").toString());
    mi.outData.put("TRWT", container.get("EXTRWT").toString());
    mi.outData.put("STIM", container.get("EXSTIM").toString());
    mi.outData.put("MKGD", container.get("EXMKGD").toString());
    mi.outData.put("STIL", container.get("EXSTIL").toString());
    mi.outData.put("EPDT", container.get("EXEPDT").toString());
    mi.outData.put("EPTM", container.get("EXEPTM").toString());
    mi.outData.put("SUCL", container.get("EXSUCL").toString());
    mi.outData.put("SUID", container.get("EXSUID").toString());
    mi.outData.put("SUNM", container.get("EXSUNM").toString());
    mi.outData.put("OSMW", container.get("EXOSMW").toString());
    mi.outData.put("MEVD", container.get("EXMEVD").toString());
    mi.outData.put("MEVT", container.get("EXMEVT").toString());
    mi.outData.put("AUTO", container.get("EXAUTO").toString());
    mi.outData.put("PDCL", container.get("EXPDCL").toString());
    mi.outData.put("PDID", container.get("EXPDID").toString());
    mi.outData.put("PDNM", container.get("EXPDNM").toString());
    mi.outData.put("PDCD", container.get("EXPDCD").toString());
    mi.outData.put("BTUN", container.get("EXBTUN").toString());
    mi.outData.put("DIVN", container.get("EXDIVN").toString());
    mi.outData.put("GALS", container.get("EXGALS").toString());
    mi.outData.put("PUST", container.get("EXPUST").toString());
    mi.write();
  }
}