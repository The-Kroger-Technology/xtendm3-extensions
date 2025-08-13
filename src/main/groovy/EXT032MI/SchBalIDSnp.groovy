/**
 * @Name: SchBalIDSnp
 * @Description: Sends a batch processing request
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date      User                Description
 *  1.0.0     20250701  Job Hanrhee Cuta    Initial Release
 */

import groovy.json.JsonOutput
import java.time.*;
import java.time.format.*;

public class SchBalIDSnp extends ExtendM3Transaction {

    private final MIAPI mi
    private final DatabaseAPI database
    private final MICallerAPI miCaller
    private final LoggerAPI logger
    private final ProgramAPI program
    private final UtilityAPI utility

    //M3 Variables
    private int XXCONO //Company
    private String XXBJNO //Job no

    // Input parameters
    private String iWHLO //warehouse
    private String iDIVI //division

    public SchBalIDSnp(
      MIAPI mi, 
      DatabaseAPI database, 
      LoggerAPI logger, 
      ProgramAPI program, 
      MICallerAPI miCaller, 
      UtilityAPI utility
    ) {
        this.mi = mi
        this.database = database
        this.logger = logger
        this.program = program
        this.miCaller = miCaller
        this.utility = utility
    }

    public void main() {
        iDIVI = mi.inData.get('DIVI') == null ? '' : mi.inData.get('DIVI').trim()
        iWHLO = mi.inData.get('WHLO') == null ? '' : mi.inData.get('WHLO').trim()

        XXCONO = program.LDAZD.CONO

        // Check from warehouse
        if (!isValidWarehouse(iWHLO)) {
            mi.error("From warehouse ${iWHLO} is not valid")
            return
        }

        // Start job
        XXBJNO = UUID.randomUUID().toString()
        if (startJob()) {
            scheduleJob()
        }
    }

  /**
   * startJob - Create start job entry 00
   */
    boolean startJob() {
        Map<String, String> data = [:]
        data['WHLO'] = iWHLO
        data['DIVI'] = iDIVI
        return createJob(XXBJNO, '00', data)
    }

/**
 * scheduleJob - run SHS010MI/SchedXM3Job
 */
    void scheduleJob() {
        Closure<?> callback = {
      Map <String, String> result ->
            if (result.error != null) {
                // if has error remove from job table
                deleteJob()
                mi.error(result.errorMessage)
                mi.write()
        } else {
                mi.outData.put('BJNO', result.BJNO.trim())
                mi.outData.put('XJDT', result.XJDT.trim())
                mi.outData.put('XJTM', result.XJTM.trim())
                mi.outData.put('TIZO', result.TIZO.trim())
                mi.outData.put('RFID', XXBJNO)
            }
        }
        Map<String, String> params =
      [ 'JOB': 'EXT501',
        'UUID': XXBJNO,
        'TX30': mi.inData.get('TX30'),
        'TX60': mi.inData.get('TX60'),
        'XCAT': mi.inData.get('XCAT'),
        'SCTY': mi.inData.get('SCTY'),
        'XNOW': mi.inData.get('XNOW'),
        'XTOD': mi.inData.get('XTOD'),
        'XNMO': mi.inData.get('XNMO'),
        'XNTU': mi.inData.get('XNTU'),
        'XNWE': mi.inData.get('XNWE'),
        'XNTH': mi.inData.get('XNTH'),
        'XNFR': mi.inData.get('XNFR'),
        'XNSA': mi.inData.get('XNSA'),
        'XNSU': mi.inData.get('XNSU'),
        'XEMO': mi.inData.get('XEMO'),
        'XETU': mi.inData.get('XETU'),
        'XEWE': mi.inData.get('XEWE'),
        'XETH': mi.inData.get('XETH'),
        'XEFR': mi.inData.get('XEFR'),
        'XESA': mi.inData.get('XESA'),
        'XESU': mi.inData.get('XESU'),
        'XEMT': mi.inData.get('XEMT'),
        'XRDY': mi.inData.get('XRDY'),
        'XJDT': mi.inData.get('XJDT'),
        'XJTM': mi.inData.get('XJTM'),
        'XSTI': mi.inData.get('XSTI'),
        'FDAT': mi.inData.get('FDAT'),
        'TDAT': mi.inData.get('TDAT'),
        'JSCA': mi.inData.get('JSCA'),
        'PETP': mi.inData.get('PETP'),
        'TIZO': mi.inData.get('TIZO'),
        'XEYE': mi.inData.get('XEYE'),
        'ONMO': mi.inData.get('ONMO')
      ]
        miCaller.call('SHS010MI', 'SchedXM3Job', params, callback)
    }

  /**
   * createJob - Add record to EXTJOB
   */
    boolean createJob(String jobNo, String jobLine, Map<String, String> data) {
        DBAction query = database.table('EXTJOB')
      .index('00')
      .build()
        DBContainer EXTJOB = query.getContainer()
        EXTJOB.set('EXCONO', XXCONO)
        EXTJOB.set('EXRGDT', LocalDateTime.now().format(DateTimeFormatter.ofPattern('yyyyMMdd')) as Integer)
        EXTJOB.set('EXRGTM', LocalDateTime.now().format(DateTimeFormatter.ofPattern('HHmmss')) as Integer)
        EXTJOB.set('EXLMDT', LocalDateTime.now().format(DateTimeFormatter.ofPattern('yyyyMMdd')) as Integer)
        EXTJOB.set('EXCHID', program.getUser())
        EXTJOB.set('EXCHNO', 0)

        EXTJOB.set('EXBJNO', jobNo)
        EXTJOB.set('EXBJLI', jobLine)
        EXTJOB.set('EXBJNA', 'EXT032')
        EXTJOB.set('EXFILE', 'EXT032')
        EXTJOB.set('EXQCMD', '')
        EXTJOB.set('EXDATA', JsonOutput.toJson(data))
        return query.insert(EXTJOB, {});
    }

  /**
   * deleteJob - Delete record to EXTJOB
   */
    void deleteJob() {
        DBAction query = database.table('EXTJOB')
      .index('00')
      .build()

        DBContainer EXTJOB = query.getContainer()
        EXTJOB.set('EXCONO', XXCONO)
        EXTJOB.set('EXBJNO', XXBJNO)
        EXTJOB.set('EXBJLI', '00')

        Closure deleteEXTJOB = { LockedResult record ->
            record.delete()
        }

        query.readLock(EXTJOB, deleteEXTJOB)
    }

  /**
   * isValidWarehouse - check if valid warehouse MITWHL
   */
    boolean isValidWarehouse(String warehouse) {
        DBAction query = database.table('MITWHL')
      .index('00')
      .build()
        DBContainer MITWHL = query.getContainer()
        MITWHL.set('MWCONO', XXCONO)
        MITWHL.set('MWWHLO', warehouse)
        return query.read(MITWHL)
    }
}
