package sisop;

import sisop.SynchPort;
import sisop.logging.Log;
import java.util.Vector;
import sisop.FairSemaphore;


public class PortVector<T> {
    public static final int PORT_DIM = 1; 
    Vector<SynchPort<T>> vector;
    FairSemaphore synchReceive;
    int[] messageInQueue;
    int countMessage, numberOfPorts;
    boolean[] messageRequired;
    boolean isEmptyRequest;

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

    public void sendTo(int portIndex, T message, String name) {
        //Log.info(name + ": PortVector sendTo()");
        synchronized(messageInQueue){
            this.messageInQueue[portIndex]++;
            this.countMessage++;
            if (!this.isEmptyRequest && this.messageRequired[portIndex]) {
                Log.info(Thread.currentThread().getName() + ": WakeUp blocked Receiver");
                this.synchReceive.V();
                //Reset isEmptyRequest because if a sendTo() occurs before receiveFrom() 
                //recovers and acquires the lock on synchronized, it makes a further V()
                //that wake up illegally the process to the next P()
                this.isEmptyRequest = true;
            }
        }
        this.vector.get(portIndex).sendTo(message, name);
    }

    public MessageReceived<T> receiveFrom(int[] portIndex, int length) {
        int index = -1;
        synchronized(messageInQueue){
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
            Log.info(Thread.currentThread().getName() + ": Wait message");
            synchReceive.P();
            Log.info(Thread.currentThread().getName() + ": WakeUp");
            //Wakeup by a sendTo in a required port
            synchronized(messageInQueue){
                //Check the first message in required port
                index = getIndex(portIndex, length);
                //Reset the request
                resetPorts();
            }    
        }        
        //Log.info(Thread.currentThread().getName() + " Index: " + index);
        Message<T> app;
        app = this.vector.get(index).receiveFrom();
        MessageReceived<T> result = new MessageReceived<T>(app.message, app.threadName, index);
        return result;
    }



    //Not Synchronized, lock is inherited
    void setRequiredPorts(int[] indexes, int length) {
        for (int i = 0; i < length; i++) {
            this.messageRequired[indexes[i]] = true;
        }
        this.isEmptyRequest = false;
    }


    //Not Synchronized, lock is inherited
    void resetPorts() {
        for (int i = 0; i < this.numberOfPorts; i++) {
            this.messageRequired[i] = false;
        }
    } 


    //Not Synchronized, lock is inherited
    int getIndex(int[] indexes, int length) {
        int index = -1;
        for (int i = 0; i < length; i++) {
            if (this.messageInQueue[indexes[i]] > 0) {
                index = indexes[i];
                this.messageInQueue[index]--;
                break;
            }
        }
        return index;
    } 

}



class MessageReceived<T> {
    T message;
    String threadName;
    int portIndex;
    
    MessageReceived(T data, String name, int index) {
        this.message = data;
        this.threadName = name;
        this.portIndex = index;
    }
}
