/**
 * This class implement a mailbox that receive message of type Integer from five senders
 * and send those to a single receiver. There is a buffer of three element to storage the message.
 *
 * @author Samuele Dal Porto
 * @author Davide Pellegrino
 *
 */

package sisop;

import sisop.PortVector;
import sisop.FairSemaphore;
import sisop.logging.Log;

import java.lang.Thread;
import java.lang.Integer;
import java.util.Vector;


public class Server extends Thread {
    PortVector<Integer> mailbox;
    FairSemaphore synchAdd, synchReceive;
    Vector<Message<Integer>> buffer;
    int head, tail;
    int[] portSelected;
    int portNumber;
    
    /**
     * Create a mailbox with a buffer of three elements that receive message from five senders 
     */
    public Server() {
        super("Server");
        this.mailbox = new PortVector<Integer>(5);
        this.synchAdd = new FairSemaphore(3);
        this.synchReceive = new FairSemaphore(0);
        this.buffer = new Vector<Message<Integer>>(3);
        this.portSelected = new int[5];
        this.tail = 0;
        this.head = 0;
        this.portNumber = 5;
        for (int i = 0; i < 5; i++) {
            this.portSelected[i] = i;
        }
    }

    /**
     * Run a loop where every time, if there is free space into the buffer, it receives a message from the
     * first available sender. It inserts the message and signals the receiver a new one is available.
     */
    public void run() {
        MessageReceived<Integer> messageReceived;
        Message<Integer> message;
        while (true) {
            synchAdd.P();
            messageReceived = this.mailbox.receiveFrom(this.portSelected, this.portNumber);
            message = new Message<Integer>(messageReceived.message, messageReceived.threadName);
            synchronized(this.buffer) {
                try {
                    this.buffer.setElementAt(message, this.tail);    
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    this.buffer.add(this.tail, message);
                }
                this.tail = (this.tail+1)%3;   
            }
            Log.info(Thread.currentThread().getName() + ": Message received from: " + message.threadName);                
            synchReceive.V();
        }
    }


    /**
     * Extract a message from the buffer, if there is one, and return it
     *
     * @return A Message<T> that contains the data and the name of the sender thread
     */
    public Message<Integer> receiveFrom() {
        Message<Integer> message;
        synchReceive.P();
        synchronized(this.buffer) {
            message = this.buffer.get(this.head);
            this.head = (this.head+1)%3;
        }
        // Log.info(Thread.currentThread().getName() + ": Message received from: " + message.threadName + " Value: " + message.message);
        synchAdd.V();
        return message;
    }
    
    /**
     * Make available the mailbox
     *
     * @return A PortVector<T> that represent the mailbox
     */
    public PortVector<Integer> getPort() {
        return this.mailbox;
    }

}
