package ua.hillel.java.elementary1.logger;

/**
 * Logger class which must be used to log system information.
 */
public interface Logger {
    /**
     * Log message into logging system for further display.
     *
     * @param message    the message
     * @param parameters the parameters
     */
    void log(String message, Object... parameters);
}
