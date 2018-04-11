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

class MotionDetector {

    public static void main(String args[]) throws Exception {
        Alarm MotionDetector = new Alarm("MotionDetector", args);
        MotionDetector.run();
    }
}