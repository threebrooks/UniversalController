#!/bin/bash
set -e

if [[ $EUID -ne 0 ]]; then
   echo "This script must be run as root. Run 'sudo ./install.sh'" 
   exit 1
fi

echo "#### Installing dependencies"
apt-get -y install bluetooth bluez bluez-tools bluez-firmware python-pip python-bluetooth 
yes | pip install python-uinput

echo "#### Enabling bluetooth service"
systemctl enable bluetooth.service  || true
echo "#### Stopping bluetooth service"
systemctl stop bluetooth.service

echo "#### Patching bluez service to be backward compatible"
perl -pi.bak -e 's/(ExecStart=\/usr\/lib\/bluetooth\/bluetoothd\ *)$/\1\ -C/' /etc/systemd/system/dbus-org.bluez.service 
perl -pi.bak -e 's/(ExecStart=\/usr\/lib\/bluetooth\/bluetoothd\ *)$/\1\ -C/' /etc/systemd/system/bluetooth.target.wants/bluetooth.service 
perl -pi.bak -e 's/(ExecStart=\/usr\/lib\/bluetooth\/bluetoothd\ *)$/\1\ -C/' /lib/systemd/system/bluetooth.service 
rm /etc/systemd/system/dbus-org.bluez.service.bak 
if grep -q "DisablePlugins = pnat" /etc/bluetooth/main.conf; then
   echo
else
   echo "DisablePlugins = pnat" >> /etc/bluetooth/main.conf 
fi
systemctl daemon-reload
systemctl restart bluetooth
echo "#### Adding SP"
sdptool add SP 
echo "#### Restarting bluetooth service"

echo Adding modules to /etc/modules
if grep -q "uinput" /etc/modules; then
   echo
else
   echo "uinput" >> /etc/modules
fi
if grep -q "evdev" /etc/modules; then
   echo
else
   echo "evdev" >> /etc/modules
fi

echo "#### Installing BluetoothKeyboard service"
cp BluetoothKeyboard.py /usr/bin/
cp BluetoothKeyboard.service /lib/systemd/system/
systemctl enable BluetoothKeyboard.service
systemctl start BluetoothKeyboard.service
