package sisop;

/**
 * A support class that will be used for saving the message and the thread name
 * in the same object 
 * @author Samuele Dal Porto
 * @author Davide Pellegrino
 *
 */

public class Message<T> {
    T message;
    String threadName;

    /**
     * Create a Message with specific parameters
     *
     * @param data The message that will be saved
     * @param name The name of tne sender thread
     */
    public Message( T data, String name) {
        this.message = data;
        this.threadName = name;
    }

    /**
     * Create a Message with specific parameters
     *
     * @return the message that was sent
     */
    public T getMessage(){
        return this.message;
    }

/**
     * Create a Message with specific parameters
     *
     * @return the threadName that sent the message
     */
    public String getName(){
        return this.threadName;
    }
}
