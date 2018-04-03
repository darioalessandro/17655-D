#!/bin/bash
# docker run -it -d -p 1099:1099 rmiregistry rmiregistry
# TODO: fix this script
ECHO STOPPING ECS System
ECHO STOPPING ECS MONITORING CONSOLE
pkill -f ECSConsole
ECHO STOPPING TEMPERATURE CONTROLLER CONSOLE
pkill -f TemperatureController
ECHO STOPPING HUMIDITY SENSOR CONSOLE
pkill -f HumidityController
ECHO STOPPING TEMPERATURE SENSOR
pkill -f TemperatureSensor
ECHO STOPPING HUMIDITY SENSOR CONSOLE
pkill -f HumiditySensor