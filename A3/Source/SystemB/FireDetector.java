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

    @Override
    public void run() throws Exception {
        em.SendMessage(new Message( (int) 10, "FIREDETECTOR." + componentName + "." + (Active ? "ACTIVE":"INACTIVE")));
    }

    public static void main(String args[]) throws Exception {
        ArrayList<Thread> arrThreads = new ArrayList<Thread>();
        FireDetector fireDetector1 = new FireDetector("Fire_Detector1", args);
        fireDetector1.start();
    }
}