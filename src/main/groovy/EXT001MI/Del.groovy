/**
 * @Name: EXT001MI.Del
 * @Description: Deletes an EXT001 record
 * @Authors: Jonard Tapang
 *
 * @CHANGELOGS
 *  Version   Date    User        Description
 *  1.0.0     240302  JTAPANG     Initial release
 */
public class Del extends ExtendM3Transaction {
  private final MIAPI mi;
  private final ProgramAPI program;
  private final DatabaseAPI database;
  
  int inCONO, inLINR;
  String inDIVI, inADBM, inSINO;
  
  public Del(MIAPI mi, ProgramAPI program, DatabaseAPI database) {
    this.mi = mi;
    this.program = program;
    this.database = database;
  }
  
  public void main() {
    // Initilize input fields
    inCONO = mi.inData.get("CONO") == null ? 0 : mi.inData.get("CONO") as Integer;
    inDIVI = mi.inData.get("DIVI") == null ? "" : mi.inData.get("DIVI") as String;
    inADBM = mi.inData.get("ADBM") == null ? "" : mi.inData.get("ADBM") as String;
    inSINO = mi.inData.get("SINO") == null ? "" : mi.inData.get("SINO") as String;
    inLINR = mi.inData.get("LINR") == null ? 0 : mi.inData.get("LINR") as Integer;
    
    // Check Company
    if(inCONO == 0){
      inCONO = (Integer) program.LDAZD.CONO;
    }
    
    // Check Division
    if(inDIVI.isBlank()){
      inDIVI = (String) program.LDAZD.CONO;
    }
    
    // Check if exists
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
    
    // Delete record
    dbaEXT001.readLock(EXT001, { LockedResult lockedResult ->
      lockedResult.delete();
    });
  }
}