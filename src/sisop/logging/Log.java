package sisop.logging;


import java.io.IOException;
import java.util.logging.*;
import sisop.logging.CustomFormatter;

/**
 * Print to screen and save into log file the messages with different levels
 */

public class Log {
    /** Set your logging level here */
    private static final Level LEVEL = Level.FINEST;
    private static final Logger LOGGER = Logger.getLogger("");

    /**
     * Open the log file and inizialize the handler for the different levels
     *
     * @param logFile The name of the log file
     */
    public static void setup( String logFile ) throws IOException {
        LOGGER.setLevel( LEVEL );
        FileHandler fileTxt = new FileHandler( logFile );

        // Setup custom txt format for all handlers
        CustomFormatter formatterTxt = new CustomFormatter();
        LOGGER.addHandler(fileTxt);
        for ( Handler h : LOGGER.getHandlers() ) {
            h.setFormatter(formatterTxt);
        }
    }

    /**
     * Print to screen and save in log file the message with Severe level
     *
     * @param msg The message to print and save
     */
    public static void severe( String msg ) { LOGGER.severe( msg ); }

    /**
     * Print to screen and save in log file the message with Warning level
     *
     * @param msg The message to print and save
     */
    public static void warning( String msg ) { LOGGER.warning( msg ); }

    /**
     * Print to screen and save in log file the message with Info level
     *
     * @param msg The message to print and save
     */
    public static void info( String msg ) { LOGGER.info( msg ); }
}
