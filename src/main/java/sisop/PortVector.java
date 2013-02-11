package sisop;

import sisop.SynchPort;
import sisop.logging.Log;
import java.util.Vector;
import sisop.FairSemaphore;


public class PortVector<T> {
    public static final int PORT_DIM = 5; 
    Vector<SynchPort<T>> vector;
    FairSemaphore synchReceive;
    int[] messageInQueue;
    int countMessage, numberOfPorts;
    boolean[] messageRequired;
    boolean isEmptyRequest;

    public PortVector(int numberOfPorts, String name) {
        this.synchReceive = new FairSemaphore(0);
        this.vector = new Vector<SynchPort<T>>(numberOfPorts);
        this.messageInQueue = new int[numberOfPorts];
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
        Log.info(name + ": PortVector sendTo()");
        this.vector.get(portIndex).sendTo(message, name);
        synchronized(messageInQueue){
            this.messageInQueue[portIndex]++;
            this.countMessage++;
            if (!this.isEmptyRequest && this.messageRequired[portIndex]) {
                this.synchReceive.V();
            }
        }
    }

    public MessageReceived<T> receiveFrom(int[] portIndex, int length) {
        int index = -1;
        synchronized(messageInQueue){
            if (this.countMessage > 0) {
                //Check if there is a message in required port
                index = getIndex(portIndex, length);
            }else {
                //Set the port required
                setRequiredPorts(portIndex, length);
            }
        }
        if (index == -1) {
            synchReceive.P();
            //Wakeup by a sendTo in a required port
            synchronized(messageInQueue){
                //Check the first message in required port
                index = getIndex(portIndex, length);
                //Reset the request
                resetPorts();
            }    
        }        
        Message<T> app;
        app = this.vector.get(index).receiveFrom();
        MessageReceived<T> result = new MessageReceived<T>(app.message, app.threadName, index);
        return result;
    }

    void setRequiredPorts(int[] indexes, int length) {
        for (int i = 0; i < length; i++) {
            this.messageRequired[indexes[i]] = true;
        }
        this.isEmptyRequest = false;
    }

    void resetPorts() {
        for (int i = 0; i < this.numberOfPorts; i++) {
            this.messageRequired[i] = false;
        }
        this.isEmptyRequest = true;
    } 


//FIXME deve essere synchronized???
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
