#!/usr/bin/python

import uinput
import sys, inspect
from bluetooth import *

uuid = "94f39d29-7d6d-437d-973b-fba39e49d4ee"

def getAllKeys():
  out = []
  for name, obj in inspect.getmembers(uinput):
    if ((name.upper() == name) and (not name.startswith("_"))):
      out.append(obj)
  return out


device = uinput.Device(getAllKeys())

server_sock=BluetoothSocket( RFCOMM )
server_sock.bind(("",PORT_ANY))
server_sock.listen(1)

port = server_sock.getsockname()[1]

advertise_service( server_sock, "BluetoothKeyboard",
        service_id = uuid,
        service_classes = [ uuid, SERIAL_PORT_CLASS ],
        profiles = [ SERIAL_PORT_PROFILE ], 
        #                   protocols = [ OBEX_UUID ] 
        )

while True:
    print("Waiting for connection")
    client_sock, client_info = server_sock.accept()
    print("Accepted connection from "+ str(client_info))
    
    try:
        while True:
            data = client_sock.recv(1024)
            if len(data) == 0: continue
            keysPressedArray = str(data).split(",") 
            keyString = keysPressedArray[0]
            pressedString = keysPressedArray[1]
            if (pressedString == "KEY_DOWN"):
              press = 1
            else:
              press = 0
            key = getattr(uinput, keyString);
            print(str(data))
            device.emit(key, press)
    except:
        print("Lost connection\n"+str(sys.exc_info()[0]))
        client_sock.close()
        continue
    
server_sock.close()

