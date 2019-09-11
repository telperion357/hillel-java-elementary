package ua.hillel.java.elementary1.logger.implementations.kosenkov;

import ua.hillel.java.elementary1.logger.Logger;
import ua.hillel.java.elementary1.logger.LoggerFactory;

public class MegaLoggerFactory implements LoggerFactory {

    /**
     * Creates MegaLogger instance with the given name and default filename and size.
     * @param name The logger name to be added to each log message.
     */
    @Override
    public Logger create(String name) {
        return new MegaLogger(name);
    }

    /**
     * Creates MegaLogger instance with the given name and filename
     * and default rotation size of 5Mb.
     * @param name The logger name to be added to each log message.
     * @param filename The full name of logger file, with path.
     */
    @Override
    public Logger create(String name, String filename) {
        return new MegaLogger(name, filename);
    }

    /**
     * Creates the MegaLogger instance with parameters.
     *
     * @param name - The logger name to be added to each log message.
     * @param filename - The full name of logger file, with path.
     * @param maxSize - The maximum size of logger file in bytes,
     *                after which the file is rotated.
     */
    @Override
    public Logger create(String name, String filename, long maxSize) {
        return new MegaLogger(name, filename, maxSize);
    }
}
