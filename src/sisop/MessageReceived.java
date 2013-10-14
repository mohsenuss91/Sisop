package sisop;

/**
 * A support class that will be used for saving the message, the thread name
 * in the same object and the index of selected port
 */

public class MessageReceived<T> {
    T message;
    String threadName;
    int portIndex;
    
    /**
     * Create a MessageReceived with specific parameters
     *
     * @param data The message that will be saved
     * @param name The name of tne sender thread
     * @param index The index of selected port
     */
    MessageReceived(T data, String name, int index) {
        this.message = data;
        this.threadName = name;
        this.portIndex = index;
    }

    /**
     * Return the message
     *
     * @return the message that was sent
     */
    public T getMessage(){
        return this.message;
    }

    /**
     * Return the name of the thread
     *
     * @return the threadName that sent the message
     */
    public String getName(){
        return this.threadName;
    }

    /**
     * Return the index
     *
     * @return the index of the port
     */
    public int getIndex(){
        return this.portIndex;
    }
}
