
/**
 * @Name: EXT032
 * @Description: Creates a batch processing request
 * @Authors: Job Hanrhee Cuta
 *
 * @CHANGELOGS
 *  Version   Date      User                Description
 *  1.0.0     20250701  Job Hanrhee Cuta    Initial Release
 */

import groovy.json.JsonSlurper
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


public class EXT032 extends ExtendM3Batch {

    private final DatabaseAPI database
    private final MICallerAPI miCaller
    private final LoggerAPI logger
    private final ProgramAPI program
    private final BatchAPI batch
    private final UtilityAPI utility

    // Global vars
    private int XXCONO
    private String XXBJNO
    private String referenceId
    private int currentDate
    private int currentTime

    public EXT032(
      DatabaseAPI database,
      MICallerAPI miCaller,
      LoggerAPI logger,
      ProgramAPI program,
      BatchAPI batch,
      UtilityAPI utility
    ) {
        this.miCaller = miCaller
        this.database = database
        this.logger = logger
        this.program = program
        this.batch = batch
        this.utility = utility
    }

    public void main() {
        currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern('yyyyMMdd')).toInteger()
        XXCONO = program.LDAZD.CONO
        referenceId = ''
        XXBJNO = batch.getJobId()

        log("BJNO: ${XXBJNO}")

        // if Job reference exists, take from reference parameter
        if (batch.getReferenceId().isPresent()) {
            referenceId = batch.getReferenceId().get()
            log("referenceId: ${referenceId}")

            Optional<DBContainer> container = getJobData()
            if (!container.isPresent()) {
                // No job data found
                return
            }
            DBContainer EXTJOB = container.get()
            JsonSlurper jsonSlurper = new JsonSlurper()
            String jsonData = EXTJOB.getString('EXDATA')
            Map<String, String> params =  jsonSlurper.parseText(jsonData) as Map
            log("params: ${params}")

            updateData(params)

        }
    }

    void updateData(Map<String, String> params) {
        int countDate = Integer.parseInt(params['CNDT'])
        String DIVI = params['DIVI'] as String
        String WHLO = params['WHLO'] as String

        List<Map<String, String>> LstBalIDResponse = []

        Closure<?> callback = { Map<String, String> response ->
            logger.info("Response = ${response}")
            if (response.error != null) {
                log('Error calling MMS060MI.LstBalID')
      } else {
                if (response.INDT == countDate) {
                    Map<String, String> line = [
            'CONO': XXCONO.toString(),
            'DIVI': DIVI,
            'WHLO': WHLO,
            'WHSL': '',
            'WHLT': '',
            'TX40': '',
            'ITNO': '',
            'BANO': '',
          ]

                    LstBalIDResponse.add(line)
                }
            }
        }

        miCaller.call('CMS100MI', 'LstBALSDK', params, callback)


        addToEXTLSC(LstBalIDResponse)
        updateEXTJOB()
    }

    void addToEXTLSC(List<Map<String, String>> items) {
        items.each { item ->
            DBAction query = database.table('EXTLSC')
        .index('00')
        .build()

            DBContainer EXTLSC = query.getContainer()
            EXTLSC.set('EXCONO', XXCONO)
            EXTLSC.set('EXDIVI', item.DIVI)
            EXTLSC.set('EXRGDT', currentDate)
            EXTLSC.set('EXWHLO', item.WHLO)
            EXTLSC.set('EXWHSL', item.WHSL)
            EXTLSC.set('EXITNO', item.ITNO)
            EXTLSC.set('EXBANO', item.BANO)

            if (query.read(EXTLSC)) {
                log('Record already exists')
                return
            }

            String inCHID = program.getUser()
            int inCHNO = 1
            int inRGDT = currentDate
            int inRGTM = currentTime
            int inLMDT = currentDate

            EXTLSC.set('EXWHLT', item.WHLT)
            EXTLSC.set('EXFTPR', 0)
            EXTLSC.set('EXMLKP', 0)
            EXTLSC.set('EXMLKG', 0)
            EXTLSC.set('EXFATP', 0)
            EXTLSC.set('EXTMLP', 0)
            EXTLSC.set('EXTFTP', 0)
            EXTLSC.set('EXTSKP', 0)
            EXTLSC.set('EXOFPR', 0)
            EXTLSC.set('EXOFLB', 0)
            EXTLSC.set('EXOMGL', 0)
            EXTLSC.set('EXOMLP', 0)
            EXTLSC.set('EXCHID', inCHID)
            EXTLSC.set('EXCHNO', inCHNO)
            EXTLSC.set('EXRGDT', inRGDT)
            EXTLSC.set('EXRGTM', inRGTM)
            EXTLSC.set('EXLMDT', inLMDT)

            query.insert(EXTLSC)
        }
    }

  /**
   * updateEXTJOB - Update QCMD with latest run job, EXT501 / BJNO
   */
    void updateEXTJOB() {
        DBAction query = database.table('EXTJOB')
      .index('00')
      .build()

        DBContainer EXTJOB = query.getContainer()
        EXTJOB.set('EXCONO', XXCONO)
        EXTJOB.set('EXBJNO', referenceId)
        EXTJOB.set('EXBJLI', '00')

        Closure update = { LockedResult lockedResult ->
            lockedResult.set('EXQCMD', XXBJNO)
            lockedResult.set('EXLMDT', utility.call('DateUtil', 'currentDateY8AsInt'))
            lockedResult.set('EXCHNO', lockedResult.getInt('EXCHNO') + 1)
            lockedResult.set('EXCHID', program.getUser())
            lockedResult.update()
        }

        query.readLock(EXTJOB, update)
    }

  /**
   * getJobData - Get job parameters if exists
   */
    Optional<DBContainer> getJobData() {
        DBAction query = database.table('EXTJOB')
      .index('00')
      .selectAllFields()
      .build()

        DBContainer EXTJOB = query.getContainer()
        EXTJOB.set('EXCONO', XXCONO)
        EXTJOB.set('EXBJNO', referenceId)
        EXTJOB.set('EXBJLI', '00')
        if (query.read(EXTJOB)) {
            return Optional.of(EXTJOB)
        }
        return Optional.empty()
    }

  /**
   * Handle logger
   */
    void log(String message) {
        logger.debug("${message}")
    }

}
