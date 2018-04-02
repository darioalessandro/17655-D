#!/bin/sh

echo "rmi registry"
nohup sh -c rmiregistry &
echo "Message Manager"
java MessageManager