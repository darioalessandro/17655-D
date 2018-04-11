#!/bin/bash

ECHO COMPILING ALL
export CLASS_PATH=.:SystemA/:SystemB/:SystemC/:InstrumentationPackage/

javac *.java
javac -cp ${CLASS_PATH} ./SystemA/*.java
javac -cp ${CLASS_PATH} ./SystemB/*.java
javac -cp ${CLASS_PATH} ./SystemC/*.java
javac -cp ${CLASS_PATH} ./Shared/*.java