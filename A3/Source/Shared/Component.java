package shared;

import InstrumentationPackage.*;
import MessagePackage.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Base class for all A3 components
 */

public class Component {

    public MessageManagerInterface em = null;   // Interface object to the message manager
    public String componentName = null;
    public int Delay = 2500;                    // The loop delay (2.5 seconds)
    public boolean Done = false;                // Loop termination flag

    public void sendHeartbeat() throws Exception {
        System.out.println("HEARTBEAT" + "." + componentName + "." + em.ParticipantId);
        em.SendMessage(new Message( (int) 12, "HEARTBEAT" + "." + componentName + "." + em.ParticipantId));
    }

    public void start() {
        while(!Done) {
            try {
                run();
                sendHeartbeat();
                Thread.sleep(Delay);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void run() throws Exception {

    }
}