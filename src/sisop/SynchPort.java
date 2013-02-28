package sisop;

import sisop.FairSemaphore;
import sisop.logging.Log;

import java.util.Vector;

/**
 * This class implement a generic synchronized port, using FairSemaphore, compared to type <T>
 *
 * @author Samuele Dal Porto
 * @author Davide Pellegrino
 *
 */

public class SynchPort<T> {
    Vector<Message<T>> buffer;
    int head, tail, maxDim;
    FairSemaphore synchRemove, synchReceive;

    /**
     * Create a synchronized port for the required senders
     *
     * @param nMitt Number of senders that will use the port
     */
    public SynchPort(int nMitt) {
        this.buffer = new Vector<Message<T>>(nMitt);
        this.synchRemove = new FairSemaphore(0);
        this.synchReceive = new FairSemaphore(0);
        this.head = 0;
        this.tail = 0;
        this.maxDim = nMitt;
    }

    /**
     * Send a <T> message to the synchronized port. In the buffer will be saved, addition
     * to the message, the name of the sender thread
     * 
     * @param message The message that must be sent
     * @param name The name of the sender thread
     */
    public void sendTo( T message, String name ) {
        Message<T> app = new Message<T>(message, name);
        synchronized(buffer) {
            try {
                this.buffer.setElementAt(app, this.tail);    
            }
            catch (ArrayIndexOutOfBoundsException e) {
                this.buffer.add(this.tail, app);
            }
            this.tail = (this.tail+1)%this.maxDim;
            synchReceive.V();
        }
        synchRemove.P();
    }

    /**
     * Receive, if there is at least one, a message from the synchronized port; otherwise it will be blocked
     *
     * @return A Message<T> object that contains the message (Message.message) and 
     * the name of the sender thread (Message.threadName)
     */
    public Message<T> receiveFrom() {
        Message<T> app;
        synchReceive.P();
        synchronized(this.buffer){
            app = this.buffer.get(this.head);
            this.head = (this.head+1)%this.maxDim;
            synchRemove.V();
        }
        return app;
    }
}



/**
 * A support class that will be used for saving the message and the thread name
 * in the same object
 */


class Message<T> {
    T message;
    String threadName;

    /**
     * Create a Message with specific parameters
     *
     * @param data The message that will be saved
     * @param name The name of tne sender thread
     */
    Message( T data, String name) {
        this.message = data;
        this.threadName = name;
    }
}
