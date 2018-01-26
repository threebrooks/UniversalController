edit /etc/systemd/system/dbus-org.bluez.service
sudo sdptool add SP
sudo systemctl daemon-reload
sudo /etc/init.d/bluetooth restart

sudo bluetoothctl
  power on
  pairable on
  discoverable on
  default-agent

sudo apt-get install python-pip
sudo apt-get install python-bluetooth
sudo pip install python-uinput
add uinput to /etc/modules
sudo cp BluetoothKeyboard.py /etc/init.d/
sudo update-rc.d  BluetoothKeyboard.py defaults
