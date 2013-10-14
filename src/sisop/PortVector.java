package sisop;

import sisop.SynchPort;
import sisop.logging.Log;
import sisop.FairSemaphore;
import sisop.MessageReceived;
import sisop.Message;

import java.util.Vector;

/**
 * This class implement an array of synchronized port and provides 
 * the tools for communication between threads
 *
 * @author Samuele Dal Porto
 * @author Davide Pellegrino
 *
 */

public class PortVector<T> {
    static final int PORT_DIM = 5; //Number of senders for every SynchPort
    Vector<SynchPort<T>> vector;
    FairSemaphore synchReceive;
    int[] messageInQueue;
    int countMessage, numberOfPorts;
    boolean[] messageRequired;
    boolean isEmptyRequest;

    /**
     * Create a vector of Synchronized port, each one with the same number of senders
     * defined statically
     *
     * @param numberOfPorts Number of ports which represents the lenght of the vector
     */
    public PortVector(int numberOfPorts) {
        this.synchReceive = new FairSemaphore(0);
        this.vector = new Vector<SynchPort<T>>(numberOfPorts);
        this.messageInQueue = new int[numberOfPorts];
        this.messageRequired = new boolean[numberOfPorts];
        for (int i = 0; i < numberOfPorts; i++) {
            this.vector.add(i, new SynchPort<T>(PORT_DIM));
            this.messageInQueue[i] = 0;
            this.messageRequired[i] = false;
        }
        this.countMessage = 0;
        this.isEmptyRequest = true;
        this.numberOfPorts = numberOfPorts;

       
    }

    /**
     * Send a <T> message to a specific port of the vector. In the buffer of this port will be saved, addition
     * to the message, the name of the sender thread
     * 
     * @param portIndex The number of the port where send the message
     * @param message The message that must be sent
     * @param name The name of the sender thread
     */
    public void sendTo(int portIndex, T message, String name) {
        synchronized(this.messageInQueue) {
            this.messageInQueue[portIndex]++;
            this.countMessage++;
            if (!this.isEmptyRequest && this.messageRequired[portIndex]) {
                this.synchReceive.V();
                //Reset isEmptyRequest because if a sendTo() occurs before receiveFrom() 
                //recovers and acquires the lock on synchronized, it makes a further V()
                //that wake up illegally the process to the next P()
                this.isEmptyRequest = true;
            }
        }
        this.vector.get(portIndex).sendTo(message, name);
    }

    /**
     * Receive the first avaible message in the ports that want be checked, if there is at least one;
     * otherwise it will be blocked until a message is received in those ports
     *
     * @return A MessageReceived<T> object that contains the message (MessageReceived.message),  
     * the name of the sender thread (MessageReceived.threadName) and the index of selected port
     */
    public MessageReceived<T> receiveFrom(int[] portIndex, int length) {
        int index = -1;
        synchronized(this.messageInQueue) {
            if (this.countMessage > 0) {
                //Check if there is a message in required port
                index = getIndex(portIndex, length);
            }
            if (index == -1) {
                //Set the port required
                setRequiredPorts(portIndex, length);
            }
        }
        if (index == -1) {
            this.synchReceive.P();
            //Wakeup by a sendTo in a required port
            synchronized(this.messageInQueue) {
                //Check the first message in required port
                index = getIndex(portIndex, length);
                //Reset the request
                resetPorts();
            }    
        }        
        Message<T> app;
        SynchPort<T> prova = this.vector.get(index);
        app = this.vector.get(index).receiveFrom();
        MessageReceived<T> result = new MessageReceived<T>(app.message, app.threadName, index);
        return result;
    }



    //Not Synchronized, lock is inherited
    private void setRequiredPorts(int[] indexes, int length) {
        for (int i = 0; i < length; i++) {
            this.messageRequired[indexes[i]] = true;
        }
        this.isEmptyRequest = false;
    }


    //Not Synchronized, lock is inherited
    private void resetPorts() {
        for (int i = 0; i < this.numberOfPorts; i++) {
            this.messageRequired[i] = false;
        }
    } 


    //Not Synchronized, lock is inherited
    private int getIndex(int[] indexes, int length) {
        int index = -1;
        for (int i = 0; i < length; i++) {
            if (this.messageInQueue[indexes[i]] > 0) {
                index = indexes[i];
                this.messageInQueue[index]--;
                //If there is a requested message, countMessage must be decresed 
                this.countMessage--;
                break;
            }
        }
        return index;
    }


     /**
     * Check if there are messages in the SynchPorts or the receiver is blocked for a new message.
     *
     * @return A boolean that is false if there are  at least one message in SynchPort or the receiver
     * is blocked for a new message (in that case countMessage is zero), true otherwise.
     */
    public synchronized boolean isEmpty() {
            return (this.countMessage == 0 && this.synchReceive.isEmpty())?true:false;
    } 

}
