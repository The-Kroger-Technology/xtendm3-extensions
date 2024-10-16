/**
 * @Name: EXT001MI.Get
 * @Description: Gets an EXT001 record
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date    User        Description
 *  1.0.0     240302  ADY         Initial Release
 */
public class Get extends ExtendM3Transaction {
  private final MIAPI mi;
  private final ProgramAPI program;
  private final DatabaseAPI database;
  
  int inCONO, inLINR;
  String inDIVI, inADBM, inSINO;
  
  public Get(MIAPI mi, ProgramAPI program, DatabaseAPI database) {
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
    
    // Read EXT001 record
    DBAction dbaEXT001 = database.table("EXT001").index("00").selectAllFields().build();
    DBContainer EXT001 = dbaEXT001.getContainer();
    EXT001.set("EXCONO", inCONO);
    EXT001.set("EXDIVI", inDIVI);
    EXT001.set("EXADBM", inADBM);
    EXT001.set("EXSINO", inSINO);
    EXT001.set("EXLINR", inLINR);
    if (dbaEXT001.read(EXT001)) {
      mi.outData.put("CONO", EXT001.get("EXCONO").toString());
      mi.outData.put("DIVI", EXT001.get("EXDIVI").toString());
      mi.outData.put("ADBM", EXT001.get("EXADBM").toString());
      mi.outData.put("SINO", EXT001.get("EXSINO").toString());
      mi.outData.put("PUNO", EXT001.get("EXPUNO").toString());
      mi.outData.put("PNLI", EXT001.get("EXPNLI").toString());
      mi.outData.put("REPN", EXT001.get("EXREPN").toString());
      mi.outData.put("LINR", EXT001.get("EXLINR").toString());
      mi.outData.put("INLP", EXT001.get("EXINLP").toString());
      mi.outData.put("ITNO", EXT001.get("EXITNO").toString());
      mi.outData.put("QVAR", EXT001.get("EXQVAR").toString());
      mi.outData.put("PVAR", EXT001.get("EXPVAR").toString());
      mi.outData.put("VAMT", EXT001.get("EXVAMT").toString());
      mi.outData.put("RSCD", EXT001.get("EXRSCD").toString());
      mi.outData.put("DTXT", EXT001.get("EXDTXT").toString());
      mi.outData.put("INYR", EXT001.get("EXINYR").toString());
      mi.outData.put("IVDT", EXT001.get("EXIVDT").toString());
      mi.outData.put("CHNO", EXT001.get("EXCHNO").toString());
      mi.outData.put("LMDT", EXT001.get("EXLMDT").toString());
      mi.outData.put("RGDT", EXT001.get("EXRGDT").toString());
      mi.outData.put("RGTM", EXT001.get("EXRGTM").toString());
      mi.outData.put("LMTS", EXT001.get("EXLMTS").toString());
      mi.outData.put("CHID", EXT001.get("EXCHID").toString());
      mi.outData.put("SUNO", EXT001.get("EXSUNO").toString());
      mi.outData.put("ACTY", EXT001.get("EXACTY").toString());
      mi.outData.put("AIT1", EXT001.get("EXAIT1").toString());
      mi.outData.put("AIT2", EXT001.get("EXAIT2").toString());
      mi.outData.put("AIT3", EXT001.get("EXAIT3").toString());
      mi.outData.put("AIT4", EXT001.get("EXAIT4").toString());
      mi.outData.put("AIT5", EXT001.get("EXAIT5").toString());
      mi.outData.put("AIT6", EXT001.get("EXAIT6").toString());
      mi.outData.put("AIT7", EXT001.get("EXAIT7").toString());
      mi.outData.put("IVNA", EXT001.get("EXIVNA").toString());
      mi.outData.put("IVQT", EXT001.get("EXIVQT").toString());
      mi.outData.put("IVOC", EXT001.get("EXIVOC").toString());
      mi.outData.put("NLAM", EXT001.get("EXNLAM").toString());
      mi.outData.put("CEID", EXT001.get("EXCEID").toString());
      mi.outData.put("CDSE", EXT001.get("EXCDSE").toString());
      mi.outData.put("INBN", EXT001.get("EXINBN").toString());
      mi.write();
    } else {
      mi.error("The record does not exist");
      return;
    };
  }
}