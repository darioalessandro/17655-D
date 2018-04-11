#!/bin/bash

./SecurityStop.sh
sleep 2

ECHO COMPILING SECURITY CLASSES
export CLASS_PATH=.:SystemA/:SystemB/:SystemC/:InstrumentationPackage/

./Build.sh


./EMRestart.sh
sleep 2

ECHO STARTING Security Monitor
java -cp ${CLASS_PATH} SystemC/ServiceMaintenanceConsole &

ECHO STARTING Security Monitor
java -cp ${CLASS_PATH} Shared/SecurityMonitor &

ECHO STARTING DOOR BREAK
java -cp ${CLASS_PATH} SystemA/DoorBreak &

ECHO STARTING WINDOW BREAK
java -cp ${CLASS_PATH} SystemA/WindowBreak &

ECHO STARTING MOTION DETECTOR
java -cp ${CLASS_PATH} SystemA/MotionDetector &

