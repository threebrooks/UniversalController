#!/bin/bash
set -e

if [[ $EUID -ne 0 ]]; then
   echo "This script must be run as root. Run 'sudo install.sh'" 
   exit 1
fi

echo Patching bluez service to be backward compatible
perl -pi.bak -e 's/(ExecStart=\/usr\/lib\/bluetooth\/bluetoothd\s*)/\1\ -C/' /etc/systemd/system/dbus-org.bluez.service 
rm /etc/systemd/system/dbus-org.bluez.service.bak 
sdptool add SP
systemctl restart bluetooth

echo Installing Python dependencies
apt-get -y install python-pip python-bluetooth 
yes | pip install python-uinput

if grep -q "uinput" /etc/modules; then
else
   echo "uinput" >> /etc/modules
   echo "evdev" >> /etc/modules
fi

