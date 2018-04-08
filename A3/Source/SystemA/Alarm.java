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

class Alarm extends Thread {

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

    @Override
    public void run() {
        while(!Done) {
            try {
                Message msg = new Message( (int) 10, "ALARM." + alarmName + "." + (Active ? "ACTIVE":"INACTIVE"));
                Thread.sleep(Delay);
                em.SendMessage(msg);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) throws Exception {
        ArrayList<Thread> arrThreads = new ArrayList<Thread>();
        Alarm motionDetector = new Alarm("Motion", args);
        Alarm windowBreakSensor = new Alarm("Window_Break", args);
//        Alarm doorBreakSensor = new Alarm("Door_Break", args);
        motionDetector.start();
        windowBreakSensor.start();
//        doorBreakSensor.start();
        arrThreads.add(motionDetector);
        arrThreads.add(windowBreakSensor);
//        arrThreads.add(doorBreakSensor);
        for (int i = 0; i < arrThreads.size(); i++) {
            arrThreads.get(i).join();
        }
    }
}