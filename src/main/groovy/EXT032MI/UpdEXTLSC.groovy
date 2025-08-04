/**
 * @Name: UpdEXTLSC.EXTLSC
 * @Description: Update record on table EXTLSC
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date      User                Description
 *  1.0.0     20250701  Job Hanrhee Cuta    Initial Release - Generated from XtendM3 CRUD Generator
 */

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

public class UpdEXTLSC extends ExtendM3Transaction {

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

    public UpdEXTLSC(
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
        container.set('EXDIVI', inDIVI)
        container.set('EXCNDT', inCNDT)
        container.set('EXWHLO', inWHLO)
        container.set('EXWHSL', inWHSL)

        if (!query.read(container)) {
            mi.error('Record does not exists')
            return
        }

        if (!isValidInput()) {
            return
        }

        LocalDateTime dateTime = LocalDateTime.now()
        int changedDate = dateTime.format(DateTimeFormatter.ofPattern('yyyyMMdd')).toInteger()
        int changedTime = dateTime.format(DateTimeFormatter.ofPattern('HHmmss')).toInteger()

        query.readLock(container, {
      LockedResult lockedResult ->
            if (!inFTPR.isBlank()) {
                lockedResult.set('EXFTPR', inFTPR.equals('?') ? 0 : inFTPR as double)
            }
            if (!inMLKP.isBlank()) {
                lockedResult.set('EXMLKP', inMLKP.equals('?') ? 0 : inMLKP as double)
            }
            if (!inFATP.isBlank()) {
                lockedResult.set('EXFATP', inFATP.equals('?') ? 0 : inFATP as double)
            }
            if (!inTMLP.isBlank()) {
                lockedResult.set('EXTMLP', inTMLP.equals('?') ? 0 : inTMLP as double)
            }
            if (!inTFTP.isBlank()) {
                lockedResult.set('EXTFTP', inTFTP.equals('?') ? 0 : inTFTP as double)
            }
            if (!inTSKP.isBlank()) {
                lockedResult.set('EXTSKP', inTSKP.equals('?') ? 0 : inTSKP as double)
            }
            if (!inMLKG.isBlank()) {
                lockedResult.set('EXMLKG', inMLKG.equals('?') ? 0 : inMLKG as double)
            }
            if (!inOFPR.isBlank()) {
                lockedResult.set('EXOFPR', inOFPR.equals('?') ? 0 : inOFPR as double)
            }
            if (!inOFLB.isBlank()) {
                lockedResult.set('EXOFLB', inOFLB.equals('?') ? 0 : inOFLB as double)
            }
            if (!inOMGL.isBlank()) {
                lockedResult.set('EXOMGL', inOMGL.equals('?') ? 0 : inOMGL as double)
            }
            if (!inOMLP.isBlank()) {
                lockedResult.set('EXOMLP', inOMLP.equals('?') ? 0 : inOMLP as double)
            }
            lockedResult.set('EXCHNO', lockedResult.getInt('EXCHNO') + 1)
            lockedResult.set('EXLMDT', changedDate)
            lockedResult.set('EXLMTM', changedTime)
            lockedResult.set('EXCHID', program.getUser())
            lockedResult.update()
        });
    }

    boolean isValidInput() {
        return true
    }

}
