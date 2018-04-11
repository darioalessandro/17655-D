package SystemC;

import InstrumentationPackage.*;
import MessagePackage.*;
import java.util.*;
import java.util.stream.Collectors;

class Process {

    Process(String name, long participantId, Date lastSeen) {
        this.lastSeen = lastSeen;
        this.participantId = participantId;
        this.name = name;
    }

    public Date lastSeen;
    public long participantId;
    public String name;

    @Override
    public String toString() {
        return name + " " + participantId + " " + lastSeen;
    }
}

class ServiceMaintenanceConsole {

    Message Msg = null;                     // Message object
    MessageQueue eq = null;                 // Message Queue
    int MsgId = 0;                          // User specified message ID
    MessageManagerInterface em = null;      // Interface object to the message manager
    int Delay = 2500;                       // The loop delay (2.5 seconds)
    boolean Done = false;                   // Loop termination flag
    Map<String, Process> connectedDevices = new HashMap<>();
    int moduleId = 12;
    int DeviceWatchdog = 10000;             // millis to wait before prunning the device.

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
                Process p = new Process(parsedCommmand[1] , Long.parseLong(parsedCommmand[2]), new Date());
                connectedDevices.put(parsedCommmand[1] + parsedCommmand[2], p);
            }
        }
    }

    void computeOutputs() {
        // prune old devices
        connectedDevices = connectedDevices.entrySet().stream()
                .filter(entry -> {
                    boolean shouldPrune = new Date().getTime() - entry.getValue().lastSeen.getTime() > DeviceWatchdog;
                    if (shouldPrune) {
                        System.out.println("Removing device " + entry.getKey());
                        try {
                            em.UnRegister(entry.getValue().participantId);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return !shouldPrune;
                })
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));

        System.out.println(Arrays.toString(connectedDevices.entrySet().toArray()));


    }

    public static void main(String args[]) throws Exception {
        ServiceMaintenanceConsole serviceMaintananceConsole = new ServiceMaintenanceConsole(args);
        serviceMaintananceConsole.run();
    }
}