#!/bin/bash

./SecurityStop.sh
sleep 2

ECHO COMPILING SECURITY CLASSES
export CLASS_PATH=.:SystemA/:SystemB/:SystemC/:InstrumentationPackage/

javac -cp ${CLASS_PATH} ./SystemA/*.java
javac -cp ${CLASS_PATH} ./SystemB/*.java
javac -cp ${CLASS_PATH} ./SystemC/*.java
javac *.java

./EMRestart.sh
sleep 2

ECHO STARTING ALARM CONTROLLER
java -cp ${CLASS_PATH} SystemA/SecurityMonitor &

ECHO STARTING ALARMS
java -cp ${CLASS_PATH} SystemA/Alarm &

