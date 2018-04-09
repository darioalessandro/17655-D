#!/bin/bash

ECHO STOPPING SECURITY PROCESSES
pkill -f DoorBreak
pkill -f SecurityMonitor
pkill -f ServiceMaintenanceConsole
pkill -f DoorBreak
pkill -f WindowBreak
pkill -f MotionDetector