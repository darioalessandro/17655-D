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
import shared.*;

class Alarm extends shared.Component {
    public boolean Done = false;                // Loop termination flag
    public boolean Active = false;

    Alarm(String alarmName, String args[]) throws Exception {
        this.componentName = alarmName;
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

    void flushMessages()  throws Exception {
        MessageQueue eq = em.GetMessageQueue();
        int length = eq.GetSize();
        for ( int i = 0; i < length; i++ ) {
            Message msg  = eq.GetMessage();
        }
    }

    @Override
    public void run() throws Exception {
        flushMessages();
        em.SendMessage(new Message( (int) 10, "ALARM." + componentName + "." + (Active ? "ACTIVE":"INACTIVE")));
    }
}