package ua.hillel.java.elementary1.logger;

/**
 * The interface Logger factory used for creation of {@link Logger} instances.
 */
public interface LoggerFactory {
    /**
     * Create logger from name provided.
     *
     * @param name the name of the logger.
     *
     * @return the logger with provided name.
     */
    Logger create(String name);

    /**
     * Create logger from name and file provided.
     *
     * @param name     the name of the logger.
     * @param filename name of the file used to logging.
     *
     * @return the logger with provided name.
     */
    Logger create(String name, String filename);

    /**
     * Create logger from name and file provided.
     *
     * @param name     the name of the logger.
     * @param filename name of the file used to logging.
     * @param maxSize  maximum size of the file allowed before rolling.
     *
     * @return the logger with provided name.
     */
    Logger create(String name, String filename, long maxSize);
}
