/**
 * @Name: DelEXTCCL.EXTCCL
 * @Description: Delete record on table EXTCCL
 * @Authors:  Jonard Tapang`
 *
 * @CHANGELOGS
 *  Version   Date        User        Description
 *  1.0.0     20250818    JTAPANG     Initial Release - Generated from XtendM3 CRUD Generator. Add modifications
 *  1.1.0     20250911    JTAPANG     Add XtendM3 review comments.(Standard field validations, handling numeric exception, remove unused codes, Fix naming and variables)
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class DelEXTCCL extends ExtendM3Transaction {
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
  
  public DelEXTCCL(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
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
    
    DBAction query = database.table("EXTCCL").index("10").build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDLIX", inDLIX as long);
    container.set("EXINOU", inINOU as int);
    int nrOfRecords = mi.getMaxRecords() <= 0 || mi.getMaxRecords() >= 10000? 10000: mi.getMaxRecords();
    int nrOfKeys = 3;
    if(!inLINE.isBlank()) {
      container.set("EXLINE", inLINE as int);
      nrOfKeys++;
    }
    
    Closure<Boolean> deleteCallback = { DBContainer data ->  
       DBAction action = database.table("EXTCCL").index("10").build();
       action.readLock(data, { LockedResult lockedResult ->
        lockedResult.delete();
       })
    }
    
    if (!query.readAll(container, nrOfKeys, nrOfRecords, deleteCallback)) {
      mi.error("The record does not exist");
    }

  }
  
}
