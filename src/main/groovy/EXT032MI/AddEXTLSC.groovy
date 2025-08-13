/**
 * @Name: AddEXTLSC.EXTLSC
 * @Description: Add record on table EXTLSC
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date      User                Description
 *  1.0.0     20250701  Job Hanrhee Cuta    Initial Release - Generated from XtendM3 CRUD Generator
 */

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

public class AddEXTLSC extends ExtendM3Transaction {

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
    private String inOFPR
    private String inOFLB
    private String inOMGL
    private String inOMLP
    private String inCHID
    private String inMLKG
    private int inCHNO
    private int inRGDT
    private int inRGTM
    private int inLMDT

    public AddEXTLSC(
      MIAPI mi,
      UtilityAPI utility,
      LoggerAPI logger,
      ProgramAPI program,
      MICallerAPI miCaller,
      DatabaseAPI database) {
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
      inWHLO = mi.inData.get('WHLO') == null ? '' : mi.inData.get('WHLO').trim()
      inCNDT = mi.inData.get('CNDT') == null ? '' : mi.inData.get('CNDT').trim()
      inWHSL = mi.inData.get('WHSL') == null ? '' : mi.inData.get('WHSL').trim()
      inWHLT = mi.inData.get('WHLT') == null ? '' : mi.inData.get('WHLT').trim()
      inITNO = mi.inData.get('ITNO') == null ? '' : mi.inData.get('ITNO').trim()
      inBANO = mi.inData.get('BANO') == null ? '' : mi.inData.get('BANO').trim()
      inFTPR = mi.inData.get('FTPR') == null ? '' : mi.inData.get('FTPR').trim()
      inMLKP = mi.inData.get('MLKP') == null ? '' : mi.inData.get('MLKP').trim()
      inMLKG = mi.inData.get('MLKG') == null ? '' : mi.inData.get('MLKG').trim()
      inFATP = mi.inData.get('FATP') == null ? '' : mi.inData.get('FATP').trim()
      inTMLP = mi.inData.get('TMLP') == null ? '' : mi.inData.get('TMLP').trim()
      inTFTP = mi.inData.get('TFTP') == null ? '' : mi.inData.get('TFTP').trim()
      inTSKP = mi.inData.get('TSKP') == null ? '' : mi.inData.get('TSKP').trim()
      inOFPR = mi.inData.get('OFPR') == null ? '' : mi.inData.get('OFPR').trim()
      inOFLB = mi.inData.get('OFLB') == null ? '' : mi.inData.get('OFLB').trim()
      inOMGL = mi.inData.get('OMGL') == null ? '' : mi.inData.get('OMGL').trim()
      inOMLP = mi.inData.get('OMLP') == null ? '' : mi.inData.get('OMLP').trim()

      DBAction query = database.table('EXTLSC').index('00').selectAllFields().build()
      DBContainer container = query.getContainer()
      container.set('EXCONO', inCONO)
      container.set('EXDIVI', inDIVI)
      container.set('EXCNDT', Integer.parseInt(inCNDT))
      container.set('EXWHLO', inWHLO)
      container.set('EXWHSL', inWHSL)
      container.set('EXITNO', inITNO)
      container.set('EXBANO', inBANO)

      if (query.read(container)) {
          mi.error('Record already exists')
          return
      }

      if (!isValidInput()) {
          return
      }

      LocalDateTime dateTime = LocalDateTime.now()
      int entryDate = dateTime.format(DateTimeFormatter.ofPattern('yyyyMMdd')).toInteger()
      int entryTime = dateTime.format(DateTimeFormatter.ofPattern('HHmmss')).toInteger()
      inCHID = program.getUser()
      inCHNO = 1
      inRGDT = entryDate
      inRGTM = entryTime
      inLMDT = entryDate

      container.set('EXWHLT', inWHLT)
      container.set('EXITNO', inITNO)
      container.set('EXBANO', inBANO)
      container.set('EXFTPR', !inFTPR.isBlank() ? inFTPR as double : 0)
      container.set('EXMLKP', !inMLKP.isBlank() ? inMLKP as double : 0)
      container.set('EXMLKG', !inMLKG.isBlank() ? inMLKG as double : 0)
      container.set('EXFATP', !inFATP.isBlank() ? inFATP as double : 0)
      container.set('EXTMLP', !inTMLP.isBlank() ? inTMLP as double : 0)
      container.set('EXTFTP', !inTFTP.isBlank() ? inTFTP as double : 0)
      container.set('EXTSKP', !inTSKP.isBlank() ? inTSKP as double : 0)
      container.set('EXOFPR', !inOFPR.isBlank() ? inOFPR as double : 0)
      container.set('EXOFLB', !inOFLB.isBlank() ? inOFLB as double : 0)
      container.set('EXOMGL', !inOMGL.isBlank() ? inOMGL as double : 0)
      container.set('EXOMLP', !inOMLP.isBlank() ? inOMLP as double : 0)
      container.set('EXCHID', inCHID)
      container.set('EXCHNO', inCHNO)
      container.set('EXRGDT', inRGDT)
      container.set('EXRGTM', inRGTM)
      container.set('EXLMDT', inLMDT)

      query.insert(container)
    }

    boolean isValidInput() {
        if (inWHLO != null && !inWHLO.isEmpty()) {
            if (!checkWHLO()) {
                mi.error('WHLO not found in MITWHL')
                return false
            }
        }

        if (inITNO != null && !inITNO.isEmpty()) {
            if (!checkITNO()) {
                mi.error('ITNO not found in MITMAS')
                return false
            }
        }
        return true
    }

    boolean checkWHLO() {
        DBAction query = database.table('MITWHL').index('00').selectAllFields().build()
        DBContainer container = query.getContainer()
        container.set('MWCONO', inCONO)
        container.set('MWWHLO', inWHLO)
        return query.read(container)
    }

    boolean checkITNO() {
        DBAction query = database.table('MITMAS').index('00').selectAllFields().build()
        DBContainer container = query.getContainer()
        container.set('MMCONO', inCONO)
        container.set('MMITNO', inITNO)
        return query.read(container)
    }

}
