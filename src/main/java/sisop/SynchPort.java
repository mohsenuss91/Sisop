package sisop;

import sisop.FairSemaphore;
import java.util.Vector;


public class SynchPort<T> {
    Vector<Message<T>> buffer;
    int head, tail, count, maxDim;
    FairSemaphore synchAdd, synchRemove, synchReceive;

    public SynchPort(int nMitt) {
        buffer = new Vector<Message<T>>(nMitt);
        synchAdd = new FairSemaphore(0);
        synchRemove = new FairSemaphore(0);
        synchReceive = new FairSemaphore();
        head = 0;
        tail = 0;
        count = 0;
        maxDim = nMitt;
    }

    public synchronized void sendTo( T message, String name ) {
        Message<T> app = new Message<T>(message, name);
        if (count==maxDim) {
            synchAdd.P();
        }
        buffer.add(tail, app);
        tail = (tail+1)%maxDim;
        count++;
        if (!synchReceive.isEmpty()) synchReceive.V();
        synchRemove.P();
    }

    public synchronized Message<T> receiveFrom() {
            Message<T> app;
            if (count==0) {
                synchReceive.P();
            }
            app = buffer.get(head);
            head = (head+1)%maxDim;
            count--;
            synchRemove.V();            
            if (synchAdd.isEmpty()) synchAdd.V();
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
