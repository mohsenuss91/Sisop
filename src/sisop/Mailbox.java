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


public class Mailbox extends Thread {
    public PortVector<Integer> inPortVector, outPortVector;
    Vector<Message<Integer>> buffer;
    int recPortIndex = 0; //Index of receiving port: fixed to zero becaouse the outPortVector is composed of a single SynchPort.
    int head, tail, count, maxDim;
    int[] portSelected;
    int portNumber;
    
    /**
     * Create a mailbox with a buffer of three elements that receive message from five senders 
     */
    public Mailbox() {
        super("Mailbox");
        this.maxDim = 3;
        this.portNumber = 5;
        this.inPortVector = new PortVector<Integer>(this.portNumber);
        this.outPortVector = new PortVector<Integer>(1);
        this.buffer = new Vector<Message<Integer>>(this.maxDim);
        this.portSelected = new int[this.portNumber];
        this.tail = 0;
        this.head = 0;
        this.count = 0;
        for (int i = 0; i < this.portNumber; i++) {
            this.portSelected[i] = i;
        }
    }


    /**
     * Run a loop where every time send a message to the receiver, if there are at least one element in buffer and the receiver had required a message 
     * or the buffer is full, and receive a message from the senders, if there is at least one free element in the buffer and one sender had sent a message 
     * or the buffer is empty
     *
     */
    public void run() {
            Message<Integer> message;
            MessageReceived<Integer> messageReceived;
            while (true) {
                try {        
                    //Send a message to the receiver if there are at least one element in buffer and the receiver had required a message
                    //or the buffer is full
                    if (this.count != 0 && !this.outPortVector.isEmpty() || this.count == maxDim) {
                        message = this.buffer.get(this.head);
                        this.head = (this.head+1)%3;
                        this.count--;
                        Log.info(Thread.currentThread().getName() + ": invio messaggio al destinatario");
                        this.outPortVector.sendTo(this.recPortIndex, message.message, message.threadName);
                    }
                    //Receive a message from the senders if there is at least one free element in the buffer and one sender had sent a message 
                    //or the buffer is empty
                    if (this.count < this.maxDim && !this.inPortVector.isEmpty() || this.count == 0) {
                        messageReceived = this.inPortVector.receiveFrom(this.portSelected, this.portNumber);
                        Log.info(Thread.currentThread().getName() + ": ricevo un messaggio da: " + messageReceived.getName());
                        message = new Message<Integer>(messageReceived.getMessage(), messageReceived.getName());
                        try {
                            this.buffer.setElementAt(message, this.tail);    
                        }
                        catch (ArrayIndexOutOfBoundsException e) {
                            this.buffer.add(this.tail, message);
                        }
                        this.tail = (this.tail+1)%3;
                        this.count++;
                    }
                }
                catch (Exception e) {
                    Log.info(Thread.currentThread().getName() + " Error " + e);   
                }
            }
    }
}
