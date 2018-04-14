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

    Sprinkler(String sprinklerName, String args[]) throws Exception {
        this.componentName = sprinklerName;
        em = MessageManagerInterface.register(args);
        Indicator i = new Indicator(sprinklerName, 0, 0, 0);
        i.repaint();
    }

    @Override
    public void run() throws Exception {
        em.SendMessage(new Message( (int) 10, "SPRINKLER." + componentName + "." + (Active ? "ACTIVE":"INACTIVE")));
    }

    public static void main(String args[]) throws Exception {
        Sprinkler s1 = new Sprinkler("Sprinkler_1", args);
        s1.start();
    }
}