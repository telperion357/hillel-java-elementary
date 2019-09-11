package ua.hillel.java.elementary1.logger.implementations.kosenkov;

import ua.hillel.java.elementary1.logger.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

// ***Logger***
//
// You are given two interfaces `ua.hillel.java.elementary1.logger.Logger` and
// ```ua.hillel.java.elementary1.logger.LoggerFactory```.
// Your task is to write file logger.
// File logger will append all message happens in the system into the logging file.
//
// When no file with such name exists it must be added into filesystem.
// We must assume that number of the messages will be high and we should minimize latency
// of IO flow (read-write flow) as much as possible. (use buffers, caches, etc.)
//
// Each log message must be logger on separate line.
// Each log message should start with date and time when this message is logged.
//
public class MegaLogger implements Logger {

    private static final String DEFAULT_LOGGER_NAME = "default_logger";
    private static final String DEFAULT_FILE_NAME = "default.log";
    private static final String DEFAULT_REL_PATH = "src/main/resources/logs/";
    private static final long MAX_LOG_FILE_SIZE = 5000000L;
    private static final int DATE_LENGTH = 10;

    /**
     * The name of log file without path
     */
    private final String logFileName;

    /**
     * The relative path to the log file, without file name
     */
    private final String logFilePath;

    /**
     * The logger name to be added to each log message
     */
    private final String loggerName;

    /**
     * Abstract file reference to the log file
     */
    private final File logFile;

    /**
     * Abstract file reference to the log file directory
     */
    private final File logDir;

    /**
     * The reference to the file writer to write logs into the log file.
     * The file writer instance is created once with the logger,
     * and is recreated after each rotation.
     */
    private FileWriter logFileWriter;

    /**
     * Creates MegaLogger instance with default parameters.
     * The logger name is "default_logger".
     * The logger file name "src/main/resources/logs/default.log"
     * The max logger size is 5Mb.
     */
    public MegaLogger() {
        this(DEFAULT_LOGGER_NAME, DEFAULT_REL_PATH + DEFAULT_FILE_NAME, MAX_LOG_FILE_SIZE);
    }

    /**
     * Creates MegaLogger instance with the given name and default filename and size.
     * @param name The logger name to be added to each log message.
     */
    public MegaLogger(String name) {
        this(name, DEFAULT_REL_PATH + DEFAULT_FILE_NAME, MAX_LOG_FILE_SIZE);
    }

    /**
     * Creates MegaLogger instance with the given name and filename
     * and default rotation size of 5Mb.
     * @param name The logger name to be added to each log message.
     * @param fileName The full name of logger file, with path.
     */
    public MegaLogger(String name, String fileName) {
        this(name, fileName, MAX_LOG_FILE_SIZE);
    }

    /**
     * Creates the MegaLogger instance with parameters.
     *
     * @param name - The logger name to be added to each log message.
     * @param fileName - The full name of logger file, with path.
     * @param maxSize - The maximum size of logger file in bytes,
     *                after which the file is rotated.
     */
    public MegaLogger(String name, String fileName, long maxSize) {
        this.loggerName = name;
        this.logFile = new File(fileName);
        this.logFileName = logFile.getName();
        this.logDir = logFile.getParentFile();
//      Three days of debugging because of the backslash instead of slash
//      this.logFilePath = logDir.getPath() + "\\";
//      Always use slash or the system separator for path name!
        this.logFilePath = logDir.getPath() + File.separator;
        createLogFileIfNotExists();
        initLogFileWriter();
    }

    /**
     * Opens the log file for writing.
     */
    private void initLogFileWriter() {
        try {
            logFileWriter = new FileWriter(logFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reopens the log file to write from the beginning.
     */
    private void reopenLogFile() {
        try {
            if (logFileWriter != null) {
                logFileWriter.close();
            }
            logFileWriter = new FileWriter(logFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates the log file and the log file directory, if necessary.
     */
    private void createLogFileIfNotExists() {
        if ( ! logDir.exists()) {
            logDir.mkdirs();
        }
        if ( ! logFile.exists()) {
            try {
                logFile.createNewFile();
                reopenLogFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void log(String message, Object... parameters) {
        createLogFileIfNotExists();

        // Get current date and time.
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDate currentDate = LocalDate.from(currentDateTime);

        // *Date rotation*
        // read the date from logs in the log file.
        LocalDate logFileDate = readLogFileDate();
        // If the log file is not empty and
        // log file date differs from current date,
        // rotate log file by date.
        if ((logFileDate != null) && ( ! logFileDate.equals(currentDate))) {
            // Archives the content of the log file into the new gzip file.
            // Starts writing the log file from the beginning.
            rotateByDate(logFileDate);
        }

        // *Size rotation*
        if (logFileIsOversize()) {
            // Archives the content of the log file into the new gzip file.
            // Starts writing the log file from the beginning.
            rotateBySize(currentDate);
        }

        // Compose the log string by specification, adding date and time.
        String log = composeLog(currentDateTime, message, parameters);
        writeToLogFile(log);
    }


    /**
     * Composes a log line with the message.
     * Adds date and time and log name to the string before the message,
     * and the new line marker at the end of the line.
     *
     * @param dateTime The date and time to be included into the log line.
     * @param message The string message or a string formatter.
     * @param params Optional parameters to pass into string formatter,
     *               if the <code>message</code> is a formatter.
     *
     * @return A string with the message preceded with current date, time, and logger name,
     * and the new line marker at the end;
     */
    private String composeLog(LocalDateTime dateTime, String message, Object... params) {
        return String.format(
                "%s %s : %s%n",
                dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                loggerName,
                String.format(message, params)
        );
    }


    /**
     * @param date - the date to format.
     * @return formatted date in format defined by ISO_LOCAL_DATE (yyyy-MM-dd).
     */
    private String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * Appends a log line into the log file.
     *
     * @param log the log line to be written.
     */
    private void writeToLogFile(String log) {
        try {
            logFileWriter.write(log);
            logFileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads the date from the first log line of the log file.
     * It is made <code>package private</code> for unit test purposes.
     * @return the date from the first log line of the log file,
     * or <code>null</code> if the file is empty.
     */
     public LocalDate readLogFileDate() {
        /// Read date from the first log message in the log file ///
        try(BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            // Read the first line of log file.
            String firstLog = reader.readLine();
            // If the first line is null -> the file is empty -> return null.
            if(firstLog == null) {
                return null;
            }
            // Substring the characters that represent date.
            String date = firstLog.substring(0, DATE_LENGTH);
            // Parse and return the date.
            return LocalDate.parse(date);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return true, if the log file size is greater than @MAX_LOG_FILE_SIZE.
     */
    private boolean logFileIsOversize() {
        return logFile.length() > MAX_LOG_FILE_SIZE;
    }

    /**
     * Reads and returns the number of rotations by size
     * from the names of archived files of that date, if any.
     *
     * By spec we have the format of archived files, rotated by size, as follows:
     * "server.log.1-2019-10-03.gz", "server.log.2-2019-10-03.gz"
     * where the number between log file name and date is the number of rotations by size;
     * And the format of archived files, rotated by date, as follows:
     * "server.log-2019-10-03.gz"
     * We read the archived file names of that date, if any. Cut the log file name
     * and the date. What is left should be the number of rotations by size, if any.
     * Find the max of it, and return.
     *
     * @param date the date for which we define the number of rotations,
     *             most probably the current date.
     * @return The number of rotations, if any;
     * 0 if there are no archived files with rotations number for this date.
     *
     */
    private int defineRotationsNumber(LocalDate date) {
        // Get the array of names of files in log directory, that are archived with the given date.
        // For example, all files ending as "-2019-03-16.gz"
        String filenameEnding = "-" + formatDate(date) + ".gz";
        String[] fileNames = logDir.list(
                (dir, name) -> name.endsWith(filenameEnding));
        // If there are no archived files for that date, there are no rotation.
        // Return 0.
        if(fileNames.length == 0) {
            return 0;
        }
        List<Integer> rotNums = new ArrayList<>();
        for (String name : fileNames) {
            // Substring what should be rotation number by spec,
            // cutting the log file name from the beginning and date from the ending.
            String rotNum = name.substring(logFileName.length(), name.length() - filenameEnding.length());
            // If the string is not empty, it should contain the rotation number,
            // for example ".2"
            if(!rotNum.isEmpty()) {
                // get rid of the point
                rotNum = rotNum.substring(1, rotNum.length());
                // Parse the number and add it to list.
                rotNums.add(Integer.parseInt(rotNum));
            }
        }
        // Now we have the list of rotation numbers.
        // If the list is empty, return 0. (No rotation numbers in file names)
        // If it is not empty, find the max value and return it.
        return rotNums.stream().max(Integer::compareTo).orElse(0);
    }

     // *Date rotation*
     //
     // When calendar date passed and there exists messages in the log file your logger should
     // rename existed file and add date at the end. Also it must zip this file.
     // New file with same filename used at the beginning must be created.
     //
     // Example:
     // 1. I created logger which must wrote logs into file `server.log`. Today is 10.03
     // 2. When next day came (11.03) and `server.log` contains any messages
     // (you can check just if this file was created).
     // 3. `server.log` must be renamed into `server.log-2019-10-03` and zip (gzip) into file `server.log-2019-10-03.gz`.
     // 4. Further logs must be written into new `server.log` file.
     //
    /**
     * Archives the content of the log file into the new gzip file.
     * Starts writing the log file from the beginning.
     * The name of the archive gzip file is composed according to specification
     * and consists of the log file name, date and .gz extension.
     * Example: server.log-2019-10-03.gz
     * @param date the date to be added to the gzip filename.
     */
    private void rotateByDate(LocalDate date) {
        // Compose the archive file name as described in specification.
        String archiveName = String.format(
                "%s-%s",
                logFileName,
                formatDate(date)
        );
        // zip the log file into the new zip file.
        gzipLogFile(archiveName);
        // Overwrite the content of the log file.
        reopenLogFile();
    }

    // *Size rotation*
    //
    // Same as upper but with file size. When size of the file reaches limit you must rename file
    // in same way as declined above and (zip) gzip. There must be more the one file per day so add counter
    // in the file.
    //
    // Example:
    // 1. I created logger which must wrote logs into file `server.log`. Today is 10.03 limit 5MB.
    // 2. When file size became more then 5 MB
    // `server.log` must be renamed into `server.log.1-2019-10-03` and
    // zip (gzip) into file `server.log.1-2019-10-03.gz`.
    // 3. When on the same day `server.log` again reaches limit it
    // must be renamed into `server.log.2-2019-10-03` and
    // zip (gzip) into file `server.log.2-2019-10-03.gz`.
    //
    /**
     * Archives the content of the log file into the new gzip file.
     * Starts writing the log file from the beginning.
     * The name of the archive gzip file is composed according to specification
     * and consists of the log file name, the number of rotations, date and .gz extension.
     * Example: server.log.2-2019-10-03.gz
     * @param date the date to be added to the gzip filename.
     */
    private void rotateBySize(LocalDate date) {
        // Define the number of rotations to be added to the archive file name
        int rotationsNumber = defineRotationsNumber(date) + 1;
        // Compose the archive file name as described in specification.
        String archiveName = String.format(
                "%s.%d-%s",
                logFileName,
                rotationsNumber,
                formatDate(date)
        );
        // zip the log file into the new zip file.
        gzipLogFile(archiveName);
        // Overwrite the content of the log file.
        reopenLogFile();
    }

    /**
     * Archives the log file into the new .gz file.
     * @param destFileName - the name of destination gzip file, without path and extension.
     */
    private void gzipLogFile(String destFileName) {
        File gzFile = new File(logFilePath + destFileName + ".gz");
        try {
             gzFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Writing: String >> BufferedWriter >> OutputStreamWriter >> GZIPOutputStream >> FileOutputStream >> file
        // BufferedWriter writes strings to char stream with buffer, buffering conversion in next layer;
        // OutputStreamWriter converts from char stream to byte stream and buffers result;
        // GZIPOutputStream compresses stream and buffers result;
        // FileOutputStream writes gzip buffered stream to file.
        // Reading: file >> FileReader >> BufferedReader >> String;
        try ( BufferedWriter gzipFileWriter =
                      new BufferedWriter(
                              new OutputStreamWriter(
                                      new GZIPOutputStream(
                                              new FileOutputStream(gzFile)))
                      );
                BufferedReader reader = new BufferedReader(new FileReader(logFile))
        ) {
            String logLine = reader.readLine();
            while (logLine != null) {
                gzipFileWriter.write(logLine);
                gzipFileWriter.newLine();
                logLine = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
