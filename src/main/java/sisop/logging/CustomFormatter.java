package sisop.logging;


import java.util.logging.*;

/**
 * CustomFormatter formats the LogRecord as follows:
 * [level] localized message with parameters
 */
public class CustomFormatter extends Formatter {

    public CustomFormatter() {
        super();
    }

    public String format(LogRecord record) {
        StringBuffer sb = new StringBuffer();

        // Get the level name and add it to the buffer
        sb.append("[").append( record.getLevel().getName() ).append("] ");

        // Get the formatted message (includes localization
        // and substitution of paramters) and add it to the buffer
        sb.append( formatMessage(record) );
        sb.append("\n");

        return sb.toString();
    }
}
