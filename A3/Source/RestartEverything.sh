#!/bin/bash

javac *.java
./EMRestart.sh
sleep 2
./ECRestart.sh
sleep 2
