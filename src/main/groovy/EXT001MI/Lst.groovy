/**
 * @Name: EXT001MI.Lst
 * @Description: List EXT001 records
 * @Authors: Ajian Dy
 *
 * @CHANGELOGS
 *  Version   Date    User        Description
 *  1.0.0     240302  ADY         Initial Release
 */
public class Lst extends ExtendM3Transaction {
  private final MIAPI mi;
  private final ProgramAPI program;
  private final DatabaseAPI database;
  
  int inCONO, inLINR;
  String inDIVI, inADBM, inSINO;
  
  public Lst(MIAPI mi, ProgramAPI program, DatabaseAPI database) {
    this.mi = mi;
    this.program = program;
    this.database = database;
  }
  
  public void main() {
    // Initilize input fields
    inCONO = mi.in.get("CONO") == null ? 0 : mi.in.get("CONO") as Integer;
    inDIVI = mi.in.get("DIVI") == null ? "" : mi.in.get("DIVI") as String;
    inADBM = mi.in.get("ADBM") == null ? "" : mi.in.get("ADBM") as String;
    inSINO = mi.in.get("SINO") == null ? "" : mi.in.get("SINO") as String;
    inLINR = mi.in.get("LINR") == null ? 0 : mi.in.get("LINR") as Integer;
    final int MAX_RECORDS = mi.getMaxRecords() == 0 ? 10000 : mi.getMaxRecords();
    
    // Check Company
    if(inCONO == 0){
      inCONO = (Integer) program.LDAZD.CONO;
    }
    
    // Check Division
    if(inDIVI.isBlank()){
      inDIVI = (String) program.LDAZD.CONO;
    }
    
    // Read EXT001 records
    DBAction dbaEXT001 = database.table("EXT001").index("00").selectAllFields().build();
    DBContainer EXT001 = dbaEXT001.getContainer();
    EXT001.set("EXCONO", inCONO);
    EXT001.set("EXDIVI", inDIVI);
    EXT001.set("EXADBM", inADBM);
    EXT001.set("EXSINO", inSINO);
    int index = 4;
    if (inLINR != 0) {
      EXT001.set("EXLINR", inLINR);
      index++;
    }
    dbaEXT001.readAll(EXT001, index, MAX_RECORDS, { DBContainer data ->
      mi.outData.put("CONO", data.get("EXCONO").toString());
      mi.outData.put("DIVI", data.get("EXDIVI").toString());
      mi.outData.put("ADBM", data.get("EXADBM").toString());
      mi.outData.put("SINO", data.get("EXSINO").toString());
      mi.outData.put("PUNO", data.get("EXPUNO").toString());
      mi.outData.put("PNLI", data.get("EXPNLI").toString());
      mi.outData.put("REPN", data.get("EXREPN").toString());
      mi.outData.put("LINR", data.get("EXLINR").toString());
      mi.outData.put("INLP", data.get("EXINLP").toString());
      mi.outData.put("ITNO", data.get("EXITNO").toString());
      mi.outData.put("QVAR", data.get("EXQVAR").toString());
      mi.outData.put("PVAR", data.get("EXPVAR").toString());
      mi.outData.put("VAMT", data.get("EXVAMT").toString());
      mi.outData.put("RSCD", data.get("EXRSCD").toString());
      mi.outData.put("DTXT", data.get("EXDTXT").toString());
      mi.outData.put("INYR", data.get("EXINYR").toString());
      mi.outData.put("IVDT", data.get("EXIVDT").toString());
      mi.outData.put("CHNO", data.get("EXCHNO").toString());
      mi.outData.put("LMDT", data.get("EXLMDT").toString());
      mi.outData.put("RGDT", data.get("EXRGDT").toString());
      mi.outData.put("RGTM", data.get("EXRGTM").toString());
      mi.outData.put("LMTS", data.get("EXLMTS").toString());
      mi.outData.put("CHID", data.get("EXCHID").toString());
      mi.outData.put("SUNO", data.get("EXSUNO").toString());
      mi.outData.put("ACTY", data.get("EXACTY").toString());
      mi.outData.put("AIT1", data.get("EXAIT1").toString());
      mi.outData.put("AIT2", data.get("EXAIT2").toString());
      mi.outData.put("AIT3", data.get("EXAIT3").toString());
      mi.outData.put("AIT4", data.get("EXAIT4").toString());
      mi.outData.put("AIT5", data.get("EXAIT5").toString());
      mi.outData.put("AIT6", data.get("EXAIT6").toString());
      mi.outData.put("AIT7", data.get("EXAIT7").toString());
      mi.outData.put("IVNA", data.get("EXIVNA").toString());
      mi.outData.put("IVQT", data.get("EXIVQT").toString());
      mi.outData.put("IVOC", data.get("EXIVOC").toString());
      mi.outData.put("NLAM", data.get("EXNLAM").toString());
      mi.outData.put("CEID", data.get("EXCEID").toString());
      mi.outData.put("CDSE", data.get("EXCDSE").toString());
      mi.outData.put("INBN", data.get("EXINBN").toString());
      mi.write();
    });
  }
}