#!/bin/bash
# docker run -it -d -p 1099:1099 rmiregistry rmiregistry
# TODO: fix this script
ECHO Starting ECS System
ECHO ECS Monitoring Console
java ECSConsole
ECHO Starting Temperature Controller Console
java TemperatureController
ECHO Starting Humidity Sensor Console
ECHO "HUMIDITY CONTROLLER CONSOLE"
java HumidityController
ECHO "TEMPERATURE SENSOR CONSOLE"
java TemperatureSensor
ECHO Starting Humidity Sensor Console
java HumiditySensor