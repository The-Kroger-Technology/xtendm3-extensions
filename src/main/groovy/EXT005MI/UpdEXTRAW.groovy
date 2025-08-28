/**
 * @Name: EXT005MI.UpdEXTRAW
 * @Description: Update record on table EXTRAW
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date      User    Description
 *  1.0.0     20250325  ADY     Initial Release
 *  1.0.1     20250826  ADY     Fixed variable names, removed SimpleDateFormat
 *
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UpdEXTRAW extends ExtendM3Transaction {
  private final MIAPI mi;
  private final UtilityAPI utility;
  private final LoggerAPI logger;
  private final ProgramAPI program;
  private final MICallerAPI miCaller;
  private final DatabaseAPI database;

  private int inCONO;
  private String inFACI, inMTNO, inITNO, inITCL, inTRQT, inTAMT, inPCTG, inFDAT, inTDAT;

  public UpdEXTRAW(MIAPI mi, UtilityAPI utility, LoggerAPI logger, ProgramAPI program, MICallerAPI miCaller, DatabaseAPI database) {
    this.mi = mi;
    this.utility = utility;
    this.logger = logger;
    this.program = program;
    this.miCaller = miCaller;
    this.database = database;
  }

  public void main() {
    inCONO = mi.in.get("CONO") == null ? program.LDAZD.CONO as int : mi.in.get("CONO") as int;
    inFACI = mi.inData.get("FACI") == null ? "" : mi.inData.get("FACI").trim() as String;
    inMTNO = mi.inData.get("MTNO") == null ? "" : mi.inData.get("MTNO").trim() as String;
    inITNO = mi.inData.get("ITNO") == null ? "" : mi.inData.get("ITNO").trim() as String;
    inITCL = mi.inData.get("ITCL") == null ? "" : mi.inData.get("ITCL").trim() as String;
    inTRQT = mi.inData.get("TRQT") == null ? "" : mi.inData.get("TRQT").trim() as String;
    inTAMT = mi.inData.get("TAMT") == null ? "" : mi.inData.get("TAMT").trim() as String;
    inPCTG = mi.inData.get("PCTG") == null ? "" : mi.inData.get("PCTG").trim() as String;
    inFDAT = mi.inData.get("FDAT") == null ? "" : mi.inData.get("FDAT").trim() as String;
    inTDAT = mi.inData.get("TDAT") == null ? "" : mi.inData.get("TDAT").trim() as String;

    DBAction queryEXTRAW = database.table("EXTRAW").index("00").build();
    DBContainer containerEXTRAW = queryEXTRAW.getContainer();
    containerEXTRAW.set("EXCONO", inCONO);
    containerEXTRAW.set("EXFACI", inFACI);
    containerEXTRAW.set("EXMTNO", inMTNO);
    containerEXTRAW.set("EXITNO", inITNO);
    containerEXTRAW.set("EXITCL", inITCL);
    containerEXTRAW.set("EXFDAT", inFDAT as int);
    containerEXTRAW.set("EXTDAT", inTDAT as int);

    if (!queryEXTRAW.read(containerEXTRAW)) {
      mi.error("Record does not exist");
      return;
    }

    LocalDateTime dateTime = LocalDateTime.now();
    int changedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toInteger();
    int changedTime = dateTime.format(DateTimeFormatter.ofPattern("HHmmss")).toInteger();

    queryEXTRAW.readLock(containerEXTRAW, { LockedResult lockedResult ->
      if (!inTRQT.isBlank()) {
        lockedResult.set("EXTRQT", inTRQT.equals("?") ? 0 : inTRQT as double);
      }
      
      if (!inTAMT.isBlank()) {
        lockedResult.set("EXTAMT", inTAMT.equals("?") ? 0 : inTAMT as double);
      }
      
      if (!inPCTG.isBlank()) {
        lockedResult.set("EXPCTG", inPCTG.equals("?") ? 0 : inPCTG as double);
      }
      
      lockedResult.set("EXCHNO", lockedResult.getInt("EXCHNO") + 1);
      lockedResult.set("EXLMDT", changedDate);
      lockedResult.set("EXLMTM", changedTime);
      lockedResult.set("EXCHID", program.getUser());
      lockedResult.update();
    });
  }
}