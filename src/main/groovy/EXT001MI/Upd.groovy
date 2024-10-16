/**
 * @Name: EXT001MI.Upd
 * @Description: Updates EXT001 record
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date    User        Description
 *  1.0.0     240302  ADY         Initial Release
 *  1.0.1     240327  FCortez     Do not update if DTXT is blank
 */
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Upd extends ExtendM3Transaction {
  private final MIAPI mi;
  private final ProgramAPI program;
  private final DatabaseAPI database;
  private final UtilityAPI utility;
  
  int inCONO, inLINR;
  String inDIVI, inADBM, inSINO, inDTXT;
  
  public Upd(MIAPI mi, ProgramAPI program, DatabaseAPI database, UtilityAPI utility) {
    this.mi = mi;
    this.program = program;
    this.database = database;
    this.utility = utility;
  }
  
  public void main() {
    // Initialize input fields
    inCONO = mi.inData.get("CONO") == null ? 0 : mi.inData.get("CONO") as Integer;
    inDIVI = mi.inData.get("DIVI") == null ? "" : mi.inData.get("DIVI") as String;
    inADBM = mi.inData.get("ADBM") == null ? "" : mi.inData.get("ADBM") as String;
    inSINO = mi.inData.get("SINO") == null ? "" : mi.inData.get("SINO") as String;
    inLINR = mi.inData.get("LINR") == null ? 0 : mi.inData.get("LINR") as Integer;
    inDTXT = mi.inData.get("DTXT") == null ? "" : mi.inData.get("DTXT") as String;
    
    // Check Company
    if(inCONO == 0){
      inCONO = (Integer) program.LDAZD.CONO;
    }
    
    // Check Division
    if(inDIVI.isBlank()){
      inDIVI = (String) program.LDAZD.CONO;
    }
    
    // Check DTXT Blank - Do not update
    if(inDTXT.isBlank()){
      return;
    }
    
    // Check if record exists
    DBAction dbaEXT001 = database.table("EXT001").index("00").build();
    DBContainer EXT001 = dbaEXT001.getContainer();
    EXT001.set("EXCONO", inCONO);
    EXT001.set("EXDIVI", inDIVI);
    EXT001.set("EXADBM", inADBM);
    EXT001.set("EXSINO", inSINO);
    EXT001.set("EXLINR", inLINR);
    if (!dbaEXT001.read(EXT001)) {
      mi.error("Record does not exist");
      return;
    }
    
        dbaEXT001.readLock(EXT001, { LockedResult lockedResult ->
      if (!inDTXT.isBlank()) {
        lockedResult.set("EXDTXT", inDTXT.trim().equals("?") ? "" : inDTXT);
      }
      lockedResult.set("EXCHNO", lockedResult.getInt("EXCHNO") + 1);
      lockedResult.set("EXLMDT", LocalDate.now().format(DateTimeFormatter.ofPattern("YYYYMMdd")).toInteger());
      lockedResult.set("EXLMTS", LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss")).toInteger());
      lockedResult.set("EXCHID", program.getUser());
      lockedResult.update();
    });
  }
}