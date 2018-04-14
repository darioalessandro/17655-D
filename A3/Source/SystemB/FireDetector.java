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
import shared.*;

class FireDetector extends shared.Component {
    public boolean Active = false;

    FireDetector(String alarmName, String args[]) throws Exception {
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
            System.out.println("msg: " + msg.GetMessageId() + " : " + msg.GetMessage());
        }
    }

    @Override
    public void run() throws Exception {
        flushMessages();
        String cmd = "FIREDETECTOR." + componentName + "." + (Active ? "ACTIVE":"INACTIVE");
        System.out.println("state " + cmd);
        Message m = new Message( (int) 10, cmd);
        em.SendMessage(m);
    }

    public static void main(String args[]) throws Exception {
        FireDetector fireDetector1 = new FireDetector("Fire_Detector1", args);
        fireDetector1.start();
    }
}