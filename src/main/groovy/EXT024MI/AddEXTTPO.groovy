/**
 * @Name: AddEXTTPO.EXTTPO
 * @Description: Add record on table EXTTPO
 * @Authors:
 *
 * @CHANGELOGS
 *  Version   Date     User     Description
 *  1.0.0     YYddMM   User     Initial Release - Generated from XtendM3 CRUD Generator
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddEXTTPO extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO, inPSEQ, inCHNO, inRGDT, inRGTM, inLMDT, inLMTM;
  private String inDIVI;
  private String inWHLO;
  private String inLDID;
  private String inLOID;
  private String inSANU;
  private String inLOFN;
  private String inLOLN;
  private String inLOTY;
  private String inLOPN;
  private String inCHID;

  public AddEXTTPO(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
    this.mi = mi;
    this.utility = utility;
    this.logger = logger;
    this.program = program;
    this.miCaller = miCaller;
    this.database = database;
  }

  public void main() {
    inCONO = mi.in.get("CONO") == null ? program.LDAZD.CONO as Integer : mi.in.get("CONO") as Integer;
    inDIVI = mi.inData.get("DIVI") == null ? "" : mi.inData.get("DIVI").trim();
    inWHLO = mi.inData.get("WHLO") == null ? "" : mi.inData.get("WHLO").trim();
    inLDID = mi.inData.get("LDID") == null ? "" : mi.inData.get("LDID").trim();
    inPSEQ = mi.in.get("PSEQ") == null ? 0 : mi.in.get("PSEQ") as Integer;
    inLOID = mi.inData.get("LOID") == null ? "" : mi.inData.get("LOID").trim();
    inSANU = mi.inData.get("SANU") == null ? "" : mi.inData.get("SANU").trim();
    inLOFN = mi.inData.get("LOFN") == null ? "" : mi.inData.get("LOFN").trim();
    inLOLN = mi.inData.get("LOLN") == null ? "" : mi.inData.get("LOLN").trim();
    inLOTY = mi.inData.get("LOTY") == null ? "" : mi.inData.get("LOTY").trim();
    inLOPN = mi.inData.get("LOPN") == null ? "" : mi.inData.get("LOPN").trim();

    DBAction query = database.table("EXTTPO").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("EXCONO", inCONO);
    container.set("EXDIVI", inDIVI);
    container.set("EXWHLO", inWHLO);
    container.set("EXLDID", inLDID);
    container.set("EXPSEQ", inPSEQ);
    container.set("EXLOID", inLOID);

    if (query.read(container)) {
      mi.error("Record already exists");
      return;
    }

    if (!isValidInput()) {
      return;
    }

    LocalDateTime dateTime = LocalDateTime.now();
    int entryDate = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toInteger();
    int entryTime = dateTime.format(DateTimeFormatter.ofPattern("HHmmss")).toInteger();
    inCHID = program.getUser();
    inCHNO = 1;
    inRGDT = entryDate;
    inRGTM = entryTime;
    inLMDT = entryDate;
    inLMTM = entryTime;

    container.set("EXSANU", inSANU);
    container.set("EXLOFN", inLOFN);
    container.set("EXLOLN", inLOLN);
    container.set("EXLOTY", inLOTY);
    container.set("EXLOPN", inLOPN);
    container.set("EXCHID", inCHID);
    container.set("EXCHNO", inCHNO);
    container.set("EXRGDT", inRGDT);
    container.set("EXRGTM", inRGTM);
    container.set("EXLMDT", inLMDT);
    container.set("EXLMTM", inLMTM);

    query.insert(container);
  }

  boolean checkWHLO() {
    DBAction query = database.table("MITWHL").index("00").selectAllFields().build();
    DBContainer container = query.getContainer();
    container.set("MWCONO", inCONO);
    container.set("MWWHLO", inWHLO);
    return query.read(container);
  }
  
  boolean isValidInput() {
    if (inWHLO != null && !inWHLO.isEmpty()) {
      if (!checkWHLO()) {
        mi.error("WHLO not found in MITWHL");
        return false;
      }
    }
  }

}