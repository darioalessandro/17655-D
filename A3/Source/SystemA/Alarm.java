/******************************************************************************************************************
 * Course: 17655
 * Project: Assignment 3
 * Alarm is used as the base class for all the Alarms.
 *
 * Each alarm runs on it's own Thread and manages it's own subscription to the MessageManager.
 *
*/
package SystemA;

import InstrumentationPackage.*;
import MessagePackage.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Alarm {

    public Message Msg = null;                    // Message object
    public MessageQueue eq = null;                // Message Queue
    public int MsgId = 0;                        // User specified message ID
    public MessageManagerInterface em = null;   // Interface object to the message manager
    public int Delay = 2500;                    // The loop delay (2.5 seconds)
    public boolean Done = false;                // Loop termination flag
    public boolean Active = false;
    public String alarmName = null;

    Alarm(String alarmName, String args[]) throws Exception {
        this.alarmName = alarmName;
        em = MessageManagerInterface.register(args);
        Indicator i = new Indicator(alarmName, 0, 0, 0);
        i.repaint();
        i.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Active = !Active;
                if (Active) {
                    i.SetLampColorAndMessage(alarmName, Color.red);
                } else {
                    i.SetLampColorAndMessage(alarmName, Color.green);
                }
            }
        });
    }


    void sendHeartbeat(String name, long processId) throws Exception {
        System.out.println("HEARTBEAT" + "." + name + "." + processId);
        em.SendMessage(new Message( (int) 12, "HEARTBEAT" + "." + name + "." + processId));
    }

    void flushMessages()  throws Exception {
        eq = em.GetMessageQueue();
        for ( int i = 0; i < eq.GetSize(); i++ ) {
            Message msg  = eq.GetMessage();
        }
    }

    public void run() throws Exception {
        while(!Done) {
            try {
                flushMessages();
                sendHeartbeat(alarmName, em.ParticipantId);
                Thread.sleep(Delay);
                Message msg = new Message( (int) 10, "ALARM." + alarmName + "." + (Active ? "ACTIVE":"INACTIVE"));
                em.SendMessage(msg);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}