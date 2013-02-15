package sisop;

import sisop.FairSemaphore;
import sisop.logging.Log;

import java.util.Vector;


public class SynchPort<T> {
    Vector<Message<T>> buffer;
    int head, tail, maxDim;
    FairSemaphore synchAdd, synchRemove, synchReceive;

    public SynchPort(int nMitt) {
        this.buffer = new Vector<Message<T>>(nMitt);
        this.synchAdd = new FairSemaphore(nMitt);
        this.synchRemove = new FairSemaphore(0);
        this.synchReceive = new FairSemaphore(0);
        this.head = 0;
        this.tail = 0;
        this.maxDim = nMitt;
    }

    public void sendTo( T message, String name ) {
        Message<T> app = new Message<T>(message, name);
        synchAdd.P();
        synchronized(buffer) {
            try {
                this.buffer.setElementAt(app, this.tail);    
            }
            catch (ArrayIndexOutOfBoundsException e) {
                this.buffer.add(this.tail, app);
            }
            this.tail = (this.tail+1)%this.maxDim;
            // Log.info(Thread.currentThread().getName() + ": Insert message in port");
            synchReceive.V();
            // Log.info(Thread.currentThread().getName() + ": Wait until message received");
        }
        synchRemove.P();
        synchAdd.V();
    }

    public Message<T> receiveFrom() {
        // Log.info(Thread.currentThread().getName() + ": Start receive");
        Message<T> app;
        synchReceive.P();
        synchronized(this.buffer){
            app = this.buffer.get(this.head);
            this.head = (this.head+1)%this.maxDim;
        
            // Log.info(Thread.currentThread().getName() + ": Extract message");
            synchRemove.V();
        }
        return app;
    }
}






class Message<T> {
    T message;
    String threadName;

    Message( T data, String name) {
        this.message = data;
        this.threadName = name;
    }
}
