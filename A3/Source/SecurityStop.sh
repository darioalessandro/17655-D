#!/bin/bash

ECHO STOPPING SECURITY PROCESSES
pkill -f AlarmController
pkill -f Alarm
pkill -f SecurityMonitor