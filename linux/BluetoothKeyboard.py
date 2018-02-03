#!/usr/bin/python

import uinput
import subprocess
import sys, inspect
import json
from bluetooth import *

uuid = "94f39d29-7d6d-437d-973b-fba39e49d4ee"

def getAllKeys():
  out = []
  for name, obj in inspect.getmembers(uinput):
    if ((name.upper() == name) and (not name.startswith("_"))):
      out.append(obj)
  return out

command=['modprobe', 'uinput']
p = subprocess.Popen(command, stdout=subprocess.PIPE)

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
            for msgString in str(data).split("@@@"):
              if (msgString == ""): continue
              #print msgString
              js = json.loads(msgString)
              type = js['type']
              if (type == "KEY"):
                if (js['pressed'] == "true"):
                  press = 1
                else:
                  press = 0
                for keyStringEl in js['keylist'].split("|"):
                  key = getattr(uinput, keyStringEl);
                  #print "Emitting "+keyStringEl
                  device.emit(key, press)
              elif (type == "MOUSE"):
                #print "Emitting mouse move"
                device.emit(uinput.REL_X, int(js['dx']), syn=False)
                device.emit(uinput.REL_Y, int(js['dy']))

    except:
        print("Lost connection\n"+str(sys.exc_info()[0]))
        client_sock.close()
        continue
    
server_sock.close()

