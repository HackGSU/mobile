#!/usr/bin/env bash


echo "Starting update SDK bash script";

echo "y" | $ANDROID_HOME/tools/android update sdk -u -a -t `$ANDROID_HOME/tools/android list sdk --all | grep -m 1 Support | sed 's/.\([0-9]*\).*/\1/'`;

echo "Finished update SDK bash script";