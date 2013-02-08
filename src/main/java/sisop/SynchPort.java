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
        System.out.println("SynchPort:Ho inizializzato tutto ");
    }

    public void sendTo( T message, String name ) {
        Message<T> app = new Message<T>(message, name);
        boolean go;
        synchronized(buffer){
            go=(count==maxDim)?false:true;
        }    
        if (!go) {
            System.out.println("SynchPort:Attendo buffer vuoto");
            synchAdd.P();
        }
        synchronized(buffer){
            buffer.add(tail, app);
            tail = (tail+1)%maxDim;
            count++;
        }
        System.out.println("SynchPort:Inserito elemento nel buffer");
            
        if (!synchReceive.isEmpty()) synchReceive.V();
        System.out.println("SynchPort:Attendo che venga ricevuto");
            
        synchRemove.P();
    }

    public Message<T> receiveFrom() {
            System.out.println("SynchPort:Inizio receive");
            Message<T> app;
            boolean go;
            synchronized(buffer){
                go=(count==0)?false:true;
            }
            if (!go) {
                System.out.println("SynchPort:Attendo che ci sia elemtno nel buffer");
                
                synchReceive.P();
            }
            synchronized(buffer){
                app = buffer.get(head);
                head = (head+1)%maxDim;
                count--;
            }
            System.out.println("SynchPort:Estraggo messaggio");

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
