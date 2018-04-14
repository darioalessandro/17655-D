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

class Sprinkler extends shared.Component {
    public boolean Active = false;
    Indicator i;

    Sprinkler(String sprinklerName, String args[]) throws Exception {
        this.componentName = sprinklerName;
        em = MessageManagerInterface.register(args);
        i = new Indicator(componentName, 0, 0, 0);
        i.repaint();
    }

    @Override
    public void run() throws Exception {
        MessageQueue eq = em.GetMessageQueue();
        int length = eq.GetSize();
        for ( int i = 0; i < length; i++ ) {
            Message msg  = eq.GetMessage();
            System.out.println("msg: " + msg.GetMessageId() + " : " + msg.GetMessage());
            if ( msg.GetMessageId() == 11 ) {
                processMessage(msg);
            }
        }
        i.SetLampColorAndMessage(componentName, Active ? Color.green : Color.black);
        em.SendMessage(new Message( (int) 10, "SPRINKLER." + componentName + "." + (Active ? "ACTIVE":"INACTIVE")));
    }

    void processMessage(Message msg) {
        System.out.println("Got sprinkler update. " + msg.GetMessage());
        String [] parsedCommmand = msg.GetMessage().split("\\.");
        System.out.println(Arrays.toString(parsedCommmand));
        if (parsedCommmand.length > 0) {
            if (parsedCommmand[0].equals("SPRINKLER") && parsedCommmand.length == 3) {
                Active = parsedCommmand[2].equals("ACTIVE");
            }
        }
    }

    public static void main(String args[]) throws Exception {
        Sprinkler s1 = new Sprinkler("Sprinkler_1", args);
        s1.start();
    }
}