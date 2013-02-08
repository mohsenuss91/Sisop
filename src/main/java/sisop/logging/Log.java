package sisop.logging;


import java.io.IOException;
import java.util.logging.*;
import sisop.logging.CustomFormatter;

public class Log {
    /** Set your logging level here */
    private static final Level LEVEL = Level.FINEST;
    private static final Logger LOGGER = Logger.getLogger("");


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

    public static void severe( String msg ) { LOGGER.severe( msg ); }

    public static void warning( String msg ) { LOGGER.warning( msg ); }

    public static void info( String msg ) { LOGGER.info( msg ); }
}
