#!/bin/bash
# docker run -it -d -p 1099:1099 rmiregistry rmiregistry
# TODO: fix this script
ECHO STOPPING ECS System
ECHO STOPPING ECS MONITORING CONSOLE
pkill ECSConsole
ECHO STOPPING TEMPERATURE CONTROLLER CONSOLE
pkill TemperatureController
ECHO STOPPING HUMIDITY SENSOR CONSOLE
pkill HumidityController
ECHO STOPPING TEMPERATURE SENSOR
pkill TemperatureSensor
ECHO STOPPING HUMIDITY SENSOR CONSOLE
pkill HumiditySensor