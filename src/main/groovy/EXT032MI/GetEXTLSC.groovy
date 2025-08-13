/**
 * @Name: GetEXTLSC.EXTLSC
 * @Description: Get record on table EXTLSC
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date      User                Description
 *  1.0.0     20250701  Job Hanrhee Cuta    Initial Release - Generated from XtendM3 CRUD Generator
 */
public class GetEXTLSC extends ExtendM3Transaction {

    private final MIAPI mi
    private final UtilityAPI utility
    private final LoggerAPI logger
    private final ProgramAPI program
    private final MICallerAPI miCaller
    private final DatabaseAPI database
    private int inCONO
    private String inDIVI
    private String inWHLO
    private String inWHSL
    private String inWHLT
    private String inITNO
    private String inBANO
    private String inCNDT

    public GetEXTLSC(
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

        DBAction query = database.table('EXTLSC').index('00').selectAllFields().build()
        DBContainer container = query.getContainer()

        container.set('EXCONO', inCONO)
        container.set('EXDIVI', inDIVI)
        container.set('EXCNDT', inCNDT)
        container.set('EXWHLO', inWHLO)
        container.set('EXWHSL', inWHSL)
        container.set('EXWHLT', inWHLT)
        container.set('EXITNO', inITNO)
        container.set('EXBANO', inBANO)

        if (!query.read(container)) {
            mi.error('Record does not exists')
            return
        }
        mi.outData.put('CONO', container.get('EXCONO').toString())
        mi.outData.put('DIVI', container.get('EXDIVI').toString())
        mi.outData.put('WHLO', container.get('EXWHLO').toString())
        mi.outData.put('CNDT', container.get('EXCNDT').toString())
        mi.outData.put('WHSL', container.get('EXWHSL').toString())
        mi.outData.put('WHLT', container.get('EXWHLT').toString())
        mi.outData.put('ITNO', container.get('EXITNO').toString())
        mi.outData.put('BANO', container.get('EXBANO').toString())
        mi.outData.put('FTPR', container.get('EXFTPR').toString())
        mi.outData.put('MLKP', container.get('EXMLKP').toString())
        mi.outData.put('MLKG', container.get('EXMLKG').toString())
        mi.outData.put('FATP', container.get('EXFATP').toString())
        mi.outData.put('TMLP', container.get('EXTMLP').toString())
        mi.outData.put('TFTP', container.get('EXTFTP').toString())
        mi.outData.put('TSKP', container.get('EXTSKP').toString())
        mi.outData.put('OFPR', container.get('EXOFPR').toString())
        mi.outData.put('OFLB', container.get('EXOFLB').toString())
        mi.outData.put('OMGL', container.get('EXOMGL').toString())
        mi.outData.put('OMLP', container.get('EXOMLP').toString())
        mi.write()
    }

}
