package ua.hillel.java.elementary1.logger.implementations.kosenkov;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;


import java.io.File;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MegaLoggerTest {

    private final String DEFAULT_FILE_NAME = "default.log";
    private final String DEFAULT_REL_PATH = "src/main/resources/logs/";
    private final String LOG_MSG_100_BYTE_PT1 = "This is a very interesting log message";
    private final String LOG_MSG_100_BYTE_PT2 = "of approximately 100 bytes long.";
    private final String LOG_MSG_100_BYTE_PT3 = "Very very interesting log message.";
    File logDir;
    @Spy MegaLogger logger;

    /**
     * Create a logger instance with default settings.
     * This will also create a log file and a directory, if necessary.
     * Write several log lines into the log file.
     */
    @Before
    public void init() {
        // Write several log lines into the log file.
        for (int i = 0; i < 5; i++) {
            logger.log(LOG_MSG_100_BYTE_PT1);
        }

        // delete all archive files from logger directory.
        logDir = new File(DEFAULT_REL_PATH);
        Arrays.stream(logDir.listFiles((dir, name) -> name.endsWith(".gz")))
                .forEach((file) -> file.delete());
        assertEquals(0,
                logDir.listFiles((dir, name) -> name.endsWith(".gz")).length);

        // Write several log lines into the log file.
        for (int i = 0; i < 5; i++) {
            logger.log(LOG_MSG_100_BYTE_PT1);
        }
    }

    /**
     * Write 10Mb logs to the logger file.
     * Check that archive files were created in the format:
     * default_logger.log.1-yyyy-MM-dd.gz, default_logger.log.2-yyyy-MM-dd.gz
     */
    @Test
    public void rotateBySize() {

        // write 10Mb logs
        // 1 msg = 100 byte; 10Mb = 100000 * 100 byte;
        for (int i = 0; i < 100000; i++) {
            logger.log(
                    "%d %s %s %s",
                    i,
                    LOG_MSG_100_BYTE_PT1,
                    LOG_MSG_100_BYTE_PT2,
                    LOG_MSG_100_BYTE_PT3
            );
        }

        // check that archive files were created in the format
        // default_logger.log.1-yyyy-MM-dd.gz, default_logger.log.2-yyyy-MM-dd.gz
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        String archive1Name = DEFAULT_REL_PATH + DEFAULT_FILE_NAME + ".1-" + formattedDate + ".gz";
        String archive2Name = DEFAULT_REL_PATH + DEFAULT_FILE_NAME + ".2-" + formattedDate + ".gz";
        File archFile1 = new File(archive1Name);
        File archFile2 = new File(archive2Name);
        assertEquals(true, archFile1.exists());
        assertEquals(true, archFile2.exists());
    }

    /**
     * Chrck that logger reads date correctly from the log file
     */
    @Test
    public void readLogFileDate() {
        // Current date was written into the log file in @Before init()
        assertEquals(LocalDate.now(), logger.readLogFileDate());
    }

    @Test
    public void rotateByDate() {
        assertEquals(0,
                logDir.listFiles((dir, name) -> name.endsWith(".gz")).length);
        // Mock the method readLogFileDate() to return yesterday's date
        // call a log() method.
        // check that an archive file with yestarday's date was created
        //
        LocalDate yesterday = LocalDate.now().minus(Period.ofDays(1));
        when(logger.readLogFileDate()).thenReturn(yesterday);
        logger.log(LOG_MSG_100_BYTE_PT1);

        // check that file were created: default_logger.log-yyyy-MM-dd.gz
        String formattedDate = yesterday.format(DateTimeFormatter.ISO_LOCAL_DATE);
        String archive1Name = DEFAULT_REL_PATH + DEFAULT_FILE_NAME + "-" + formattedDate + ".gz";
        File archFile1 = new File(archive1Name);
        assertEquals(true, archFile1.exists());
    }

    @Test
    public void pathSeparator() {
        // File.pathSeparator: ;
        // File.separator: /
        System.out.println("File.pathSeparator: " + File.pathSeparator);
        System.out.println("File.separator: " + File.separator);
    }
}