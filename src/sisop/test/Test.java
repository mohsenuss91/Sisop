package sisop.test;

import sisop.FairSemaphore;
import sisop.SynchPort;
import sisop.PortVector;
import sisop.test.Mitt;
import sisop.test.Dest;
import sisop.Mailbox;
import sisop.logging.Log;

import java.lang.Thread;
import java.lang.Integer;

public class Test{
    public static void main(String[] args) {
        
        //-------------Test Mailbox------------------
        try {
            Log.setup("../test.log");
            Log.info("---------Program Start------------");
            Mailbox mailbox = new Mailbox();        
            mailbox.start();
            Mitt[] mitt = new Mitt[5];
            Dest dest = new Dest(mailbox);
            for (int i = 0; i < 5; i++) {
                mitt[i] = new Mitt(i, mailbox); 
                mitt[i].start();
            }
            dest.start();
            dest.join();
            Log.info("----------Program End--------------");
            System.exit(0);
        }
        catch (Exception e) {
            Log.severe("Main Error " + e);
        }
    }
}
