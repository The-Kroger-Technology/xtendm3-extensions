/**
 * @Name: LstEXTLSC.EXTLSC
 * @Description: List record on table EXTLSC
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date      User                Description
 *  1.0.0     20250701  Job Hanrhee Cuta    Initial Release - Generated from XtendM3 CRUD Generator
 */
public class LstEXTLSC extends ExtendM3Transaction {

    private final MIAPI mi
    private final UtilityAPI utility
    private final LoggerAPI logger
    private final ProgramAPI program
    private final MICallerAPI miCaller
    private final DatabaseAPI database
    private int inCONO
    private String inDIVI
    private String inWHLO
    private String inCNDT
    private String inWHSL
    private String inWHLT
    private String inITNO
    private String inBANO
    private String inFTPR
    private String inMLKP
    private String inFATP
    private String inTMLP
    private String inTFTP
    private String inTSKP
    private String inMLKG
    private String inOFPR
    private String inOFLB
    private String inOMGL
    private String inOMLP

    public LstEXTLSC(
      MIAPI mi,
      UtilityAPI utility,
      LoggerAPI logger,
      ProgramAPI program,
      MICallerAPI miCaller,
      DatabaseAPI database
    ) {
        this.mi = mi
        this.utility = utility
        this.logger = logger
        this.program = program
        this.miCaller = miCaller
        this.database = database
    }
    public void main() {
        inCONO = mi.in.get('CONO') == null ? program.LDAZD.CONO as int : mi.in.get('CONO') as int
        inDIVI = mi.inData.get('DIVI') == null ? '' : mi.inData.get('DIVI').trim()
        inCNDT = mi.inData.get('CNDT') == null ? '' : mi.inData.get('CNDT').trim()
        inWHLO = mi.inData.get('WHLO') == null ? '' : mi.inData.get('WHLO').trim()
        inWHSL = mi.inData.get('WHSL') == null ? '' : mi.inData.get('WHSL').trim()
        inWHLT = mi.inData.get('WHLT') == null ? '' : mi.inData.get('WHLT').trim()
        inITNO = mi.inData.get('ITNO') == null ? '' : mi.inData.get('ITNO').trim()
        inBANO = mi.inData.get('BANO') == null ? '' : mi.inData.get('BANO').trim()
        inFTPR = mi.inData.get('FTPR') == null ? '' : mi.inData.get('FTPR').trim()
        inMLKP = mi.inData.get('MLKP') == null ? '' : mi.inData.get('MLKP').trim()
        inFATP = mi.inData.get('FATP') == null ? '' : mi.inData.get('FATP').trim()
        inTMLP = mi.inData.get('TMLP') == null ? '' : mi.inData.get('TMLP').trim()
        inTFTP = mi.inData.get('TFTP') == null ? '' : mi.inData.get('TFTP').trim()
        inTSKP = mi.inData.get('TSKP') == null ? '' : mi.inData.get('TSKP').trim()
        inMLKG = mi.inData.get('MLKG') == null ? '' : mi.inData.get('MLKG').trim()
        inOFPR = mi.inData.get('OFPR') == null ? '' : mi.inData.get('OFPR').trim()
        inOFLB = mi.inData.get('OFLB') == null ? '' : mi.inData.get('OFLB').trim()
        inOMGL = mi.inData.get('OMGL') == null ? '' : mi.inData.get('OMGL').trim()
        inOMLP = mi.inData.get('OMLP') == null ? '' : mi.inData.get('OMLP').trim()
        DBAction query = database.table('EXTLSC').index('00').selectAllFields().build()
        DBContainer container = query.getContainer()
        container.set('EXCONO', inCONO)
        int index = 1

        if (!inDIVI.isBlank()) {
            container.set('EXDIVI', inDIVI)
            index++
        }
        if (!(inCNDT == 0)) {
            container.set('EXRGDT', inCNDT)
            index++
        }
        if (!inWHLO.isBlank()) {
            container.set('EXWHLO', inWHLO)
            index++
        }
        if (!inWHSL.isBlank()) {
            container.set('EXWHSL', inWHSL)
            index++
        }

        query.readAll(container, index, 10000, {
          DBContainer data ->
            mi.outData.put('CONO', data.get('EXCONO').toString())
            mi.outData.put('DIVI', data.get('EXDIVI').toString())
            mi.outData.put('CNDT', data.get('EXRGDT').toString())
            mi.outData.put('WHLO', data.get('EXWHLO').toString())
            mi.outData.put('WHSL', data.get('EXWHSL').toString())
            mi.outData.put('WHLT', data.get('EXWHLT').toString())
            mi.outData.put('ITNO', data.get('EXITNO').toString())
            mi.outData.put('BANO', data.get('EXBANO').toString())
            mi.outData.put('FTPR', data.get('EXFTPR').toString())
            mi.outData.put('MLKP', data.get('EXMLKP').toString())
            mi.outData.put('MLKG', data.get('EXMLKG').toString())
            mi.outData.put('FATP', data.get('EXFATP').toString())
            mi.outData.put('TMLP', data.get('EXTMLP').toString())
            mi.outData.put('TFTP', data.get('EXTFTP').toString())
            mi.outData.put('TSKP', data.get('EXTSKP').toString())
            mi.outData.put('OFPR', data.get('EXOFPR').toString())
            mi.outData.put('OFLB', data.get('EXOFLB').toString())
            mi.outData.put('OMGL', data.get('EXOMGL').toString())
            mi.outData.put('OMLP', data.get('EXOMLP').toString())
            mi.write()
        })
    }

}
