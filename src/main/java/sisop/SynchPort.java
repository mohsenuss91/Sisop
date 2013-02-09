package sisop;

import sisop.FairSemaphore;
import sisop.logging.Log;
import java.util.Vector;


public class SynchPort<T> {
    Vector<Message<T>> buffer;
    int head, tail, count, maxDim;
    FairSemaphore synchAdd, synchRemove, synchReceive;

    public SynchPort(int nMitt) {
        buffer = new Vector<Message<T>>(nMitt);
        synchAdd = new FairSemaphore(nMitt);
        synchRemove = new FairSemaphore(0);
        synchReceive = new FairSemaphore(0);
        head = 0;
        tail = 0;
        count = 0;
        maxDim = nMitt;
    }

    public void sendTo( T message, String name ) {
        Message<T> app = new Message<T>(message, name);
        synchAdd.P();
        synchronized(buffer){
            try {
                buffer.setElementAt(app, tail);    
            }
            catch (ArrayIndexOutOfBoundsException e) {
                buffer.add(tail, app);
            }
            tail = (tail+1)%maxDim;
            count++;
            Log.info(Thread.currentThread().getName() + ": Insert message in port");
            synchReceive.V();
            Log.info(Thread.currentThread().getName() + ": Wait until message received");
        }
        synchRemove.P();
        synchAdd.V();
    }

    public Message<T> receiveFrom() {
        Log.info(Thread.currentThread().getName() + ": Start receive");
        Message<T> app;
        synchReceive.P();
        synchronized(buffer){
            app = buffer.get(head);
            head = (head+1)%maxDim;
            count--;
        
        Log.info(Thread.currentThread().getName() + ": Extract message");
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
