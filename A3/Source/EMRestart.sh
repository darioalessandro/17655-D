#!/bin/bash
echo "Stopping existing registry and manager"
./EMStop.sh
echo "STARTING EVENT MANAGER REGISTRY"
rmiregistry &
sleep 3 # Wait for registry to start.
echo "STARTING EVENT MANAGER"
java MessageManager &