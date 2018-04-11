package SystemC;

import InstrumentationPackage.*;
import MessagePackage.*;
import java.util.*;

class ServiceMaintenanceConsole {

    Message Msg = null;                     // Message object
    MessageQueue eq = null;                 // Message Queue
    int MsgId = 0;                          // User specified message ID
    MessageManagerInterface em = null;      // Interface object to the message manager
    int Delay = 2500;                       // The loop delay (2.5 seconds)
    boolean Done = false;                   // Loop termination flag
    Map<String, Date> connectedDevices = new HashMap<>();
    int moduleId = 12;

    ServiceMaintenanceConsole(String args[]) throws Exception {
        em = MessageManagerInterface.register(args);
    }

    void run() throws Exception {
        while (!Done) {
            eq = em.GetMessageQueue();
            int size = eq.GetSize();
            for ( int i = 0; i < size; i++ ) {
                Message msg  = eq.GetMessage();
                System.out.println(msg.GetMessage());
                if ( msg.GetMessageId() == moduleId ) {
                    processMessage(msg);
                }
            }
            computeOutputs();
            Thread.sleep( Delay );
        }
    }

    void processMessage(Message msg) {
        String [] parsedCommmand = msg.GetMessage().split("\\.");
        System.out.println(Arrays.toString(parsedCommmand));
        if (parsedCommmand.length > 0) {
            if (parsedCommmand[0].equals("HEARTBEAT") && parsedCommmand.length == 3) {
                connectedDevices.put(parsedCommmand[1] + parsedCommmand[2], new Date());
            }
        }
    }

    void computeOutputs() {
        System.out.println(Arrays.toString(connectedDevices.entrySet().toArray()));
    }

    public static void main(String args[]) throws Exception {
        ServiceMaintenanceConsole serviceMaintananceConsole = new ServiceMaintenanceConsole(args);
        serviceMaintananceConsole.run();
    }
}