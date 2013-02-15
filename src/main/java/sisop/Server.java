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
    
    public PortVector<Integer> getPort() {
        return this.mailbox;
    }

}
