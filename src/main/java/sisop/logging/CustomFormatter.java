package sisop.logging;


import java.util.Date;
import java.util.logging.*;

/**
 * CustomFormatter formats the LogRecord as follows:
 * [date] [level] localized message with parameters
 */
public class CustomFormatter extends Formatter {

    public CustomFormatter() {
        super();
    }

    public String format(LogRecord record) {
        StringBuffer sb = new StringBuffer();

        // Get the date from the LogRecord and add it to the buffer
        // Date date = new Date( record.getMillis() );
        // sb.append("[").append( date.toString() ).append("] ");

        // Get the level name and add it to the buffer
        sb.append("[").append( record.getLevel().getName() ).append("] ");

        // Get the formatted message (includes localization
        // and substitution of paramters) and add it to the buffer
        sb.append( formatMessage(record) );
        sb.append("\n");

        return sb.toString();
    }

    // public String getHead(Handler h) {}

    // public String getTail(Handler h) {}
}
