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
    MessageQueue eq = null;               // Message Queue
    int MsgId = 0;                        // User specified message ID
    Map<String,Boolean> alarmsState = new HashMap<>();
    boolean fireAlarmActive = false;
    boolean isAnyAlarmActive = false;
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
        eq = em.GetMessageQueue();
        for ( int i = 0; i < eq.GetSize(); i++ ) {
            Message msg  = eq.GetMessage();
            System.out.println(msg.GetMessage());
            if ( msg.GetMessageId() == 10 ) {
                processMessage(msg);
            }
        }
        processIntrusionAlarmsState();
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
                alarmsState.put(parsedCommmand[1], parsedCommmand[2].equals("ACTIVE"));
            }
        }
    }

    void processIntrusionAlarmsState() {
        boolean isAnyAlarmActive = false;
        for (Map.Entry<String, Boolean> alarm : alarmsState.entrySet()) {
            if (alarm.getValue()) {
                isAnyAlarmActive = true;
                continue;
            }
        }
        System.out.println("isAnyAlarmActive " + isAnyAlarmActive + " this.isAnyAlarmActive " + this.isAnyAlarmActive);
        if (isAnyAlarmActive) {
            if (!this.isAnyAlarmActive) {
                alarmTransitionedToActive = new Date();
            }
            this.isAnyAlarmActive = true;
        } else {
            this.isAnyAlarmActive = false;
        }
        intrusionAlarmIndicator.SetLampColorAndMessage("Intrusion_Alarm",this.isAnyAlarmActive ? Color.red : Color.black);
    }

    void cancelIntrutionAlarm() {
        if (isAnyAlarmActive &&
                new Date().getTime() - alarmTransitionedToActive.getTime() < SECURITY_MONITOR_CANCEL_SECURITY_ALARM) {
            isAnyAlarmActive = false;
        }
    }

    void stopIntrutionAlarm() {
        isAnyAlarmActive = false;
    }

    public static void main(String args[]) throws Exception {
        SecurityMonitor monitor = new SecurityMonitor(args);
        monitor.start();
    }
}