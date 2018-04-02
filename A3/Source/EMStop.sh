#!/bin/bash
ECHO "STOPPING EVENT MANAGER REGISTRY"
pkill rmiregistry
ECHO "STOPPING EVENT MANAGER"
pkill MessageManager
sleep 3 # Wait for registry to stop.