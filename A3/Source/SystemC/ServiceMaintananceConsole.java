import InstrumentationPackage.*;
import MessagePackage.*;
import java.util.*;

class ServiceMaintananceConsole {

    public static void main(String args[]) throws Exception {
        Message Msg = null;                    // Message object
        MessageQueue eq = null;                // Message Queue
        int MsgId = 0;                        // User specified message ID
        MessageManagerInterface em = MessageManagerInterface.register(args);    // Interface object to the message manager
        int Delay = 2500;                    // The loop delay (2.5 seconds)
        boolean Done = false;                // Loop termination flag

        while(!Done) {
            Thread.sleep( Delay );
        }
    }
}