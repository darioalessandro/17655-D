package Shared;

import InstrumentationPackage.*;
import MessagePackage.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import shared.*;

class SecurityMonitor extends shared.Component {
    int SECURITY_MONITOR_CANCEL_SECURITY_ALARM = 10000;
    Map<String,Boolean> intrusionAlarmsState = new HashMap<>();
    Map<String,Boolean> fireDetectorsState = new HashMap<>();
    boolean isAnyFireDetectorActive = false;
    boolean isAnyIntrusionAlarmActive = false;
    boolean wasIntrusionAlertCancelled = false;
    Date alarmTransitionedToActive = new Date();
    MessageWindow mw = null;
    Indicator fireAlarmActiveIndicator;
    Indicator intrusionAlarmIndicator;

    SecurityMonitor(String args[]) throws Exception {
        componentName = "Security Monitor";
        em = MessageManagerInterface.register(args);    // Interface object to the message manager
        setupUI();
    }

    @Override
    public void run() throws Exception {
        MessageQueue eq = em.GetMessageQueue();
        int length = eq.GetSize();
        for ( int i = 0; i < length; i++ ) {
            Message msg  = eq.GetMessage();
            System.out.println("msg: " + msg.GetMessageId() + " : " + msg.GetMessage());
            if ( msg.GetMessageId() == 10 ) {
                processMessage(msg);
            }
        }
        processIntrusionAlarmsState();
        processFireDetectorsState();
        em.SendMessage(new Message( (int) 11, "SPRINKLER." + componentName + "." + (this.isAnyFireDetectorActive ? "ACTIVE":"INACTIVE")));
    }

    void setupUI() {
        mw = new MessageWindow("Security Monitor", 0, 0);
        fireAlarmActiveIndicator = new Indicator ("Fire_Alarm", mw.GetX()+ mw.Width(), 0);
        intrusionAlarmIndicator = new Indicator ("Intrusion_Alarm", mw.GetX()+ mw.Width(), (int)(mw.Height()/2), 2 );
        fireAlarmActiveIndicator.SetLampColorAndMessage("Fire_Alarm", Color.black);
        intrusionAlarmIndicator.SetLampColorAndMessage("Intrusion_Alarm",Color.black);
        intrusionAlarmIndicator.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                cancelIntrutionAlarm();
            }
        });
    }

    void processMessage(Message msg) {
        System.out.println("Got alarm update. " + msg.GetMessage());
        String [] parsedCommmand = msg.GetMessage().split("\\.");
        System.out.println(Arrays.toString(parsedCommmand));
        if (parsedCommmand.length > 0) {
            if (parsedCommmand[0].equals("ALARM") && parsedCommmand.length == 3) {
                intrusionAlarmsState.put(parsedCommmand[1], parsedCommmand[2].equals("ACTIVE"));
            } else if (parsedCommmand[0].equals("FIREDETECTOR") && parsedCommmand.length == 3) {
                fireDetectorsState.put(parsedCommmand[1], parsedCommmand[2].equals("ACTIVE"));
            }
        }
    }

    void processIntrusionAlarmsState() {
        boolean isAnyIntrusionAlarmActive = false;
        for (Map.Entry<String, Boolean> alarm : intrusionAlarmsState.entrySet()) {
            if (alarm.getValue()) {
                isAnyIntrusionAlarmActive = true;
                continue;
            }
        }
        System.out.println("isAnyIntrusionAlarmActive " + isAnyIntrusionAlarmActive + " this.isAnyIntrusionAlarmActive " + this.isAnyIntrusionAlarmActive);
        if (isAnyIntrusionAlarmActive) {
            if (!this.isAnyIntrusionAlarmActive) {
                alarmTransitionedToActive = new Date();
            }
            this.isAnyIntrusionAlarmActive = true;
        } else {
            this.isAnyIntrusionAlarmActive = false;
        }
        intrusionAlarmIndicator.SetLampColorAndMessage("Intrusion_Alarm",this.isAnyIntrusionAlarmActive ? Color.red : Color.black);
    }

    void processFireDetectorsState() {
        boolean isAnyFireDetectorActive = false;
        for (Map.Entry<String, Boolean> alarm : fireDetectorsState.entrySet()) {
            if (alarm.getValue()) {
                isAnyFireDetectorActive = true;
                continue;
            }
        }
        System.out.println("isAnyFireDetectorActive " + isAnyFireDetectorActive + " this.isAnyFireDetectorActive " + this.isAnyFireDetectorActive);
        if (isAnyFireDetectorActive) {
            if (!this.isAnyFireDetectorActive) {
                alarmTransitionedToActive = new Date();
            }
            this.isAnyFireDetectorActive = true;
        } else {
            this.isAnyFireDetectorActive = false;
        }
        fireAlarmActiveIndicator.SetLampColorAndMessage("Fire_Alarm",this.isAnyFireDetectorActive ? Color.red : Color.black);
    }

    void cancelIntrutionAlarm() {
        if (isAnyIntrusionAlarmActive &&
                new Date().getTime() - alarmTransitionedToActive.getTime() < SECURITY_MONITOR_CANCEL_SECURITY_ALARM) {
            isAnyIntrusionAlarmActive = false;
        }
    }

    void stopIntrutionAlarm() {
        isAnyIntrusionAlarmActive = false;
    }

    public static void main(String args[]) throws Exception {
        SecurityMonitor monitor = new SecurityMonitor(args);
        monitor.start();
    }
}