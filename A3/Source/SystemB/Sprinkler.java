/******************************************************************************************************************
 * Course: 17655
 * Project: Assignment 3
 */

package SystemB;

import InstrumentationPackage.*;
import MessagePackage.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Sprinkler extends Thread {

    public Message Msg = null;                    // Message object
    public MessageQueue eq = null;                // Message Queue
    public int MsgId = 0;                        // User specified message ID
    public MessageManagerInterface em = null;   // Interface object to the message manager
    public int Delay = 2500;                    // The loop delay (2.5 seconds)
    public boolean Done = false;                // Loop termination flag
    public boolean Active = false;
    public String sprinklerName = null;

    Sprinkler(String sprinklerName, String args[]) throws Exception {
        this.sprinklerName = sprinklerName;
        em = MessageManagerInterface.register(args);
        Indicator i = new Indicator(sprinklerName, 0, 0, 0);
        i.repaint();
    }

    @Override
    public void run() {
        while(!Done) {
            try {
                Message msg = new Message( (int) 10, "SPRINKLER." + sprinklerName + "." + (Active ? "ACTIVE":"INACTIVE"));
                Thread.sleep(Delay);
                em.SendMessage(msg);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) throws Exception {
        ArrayList<Thread> arrThreads = new ArrayList<Thread>();
        Sprinkler s1 = new Sprinkler("Sprinkler_1", args);
        Sprinkler s2 = new Sprinkler("Sprinkler_2", args);
        s1.start();
        s2.start();
        arrThreads.add(s1);
        arrThreads.add(s2);
        for (int i = 0; i < arrThreads.size(); i++) {
            arrThreads.get(i).join();
        }
    }
}