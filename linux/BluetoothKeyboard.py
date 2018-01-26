#!/usr/bin/python

import uinput
from bluetooth import *

uuid = "94f39d29-7d6d-437d-973b-fba39e49d4ee"

keyMap = {
        "KEYCODE_DPAD_UP" : uinput.BTN_DPAD_UP,
        "KEYCODE_DPAD_LEFT" : uinput.BTN_DPAD_LEFT,
        "KEYCODE_DPAD_RIGHT" : uinput.BTN_DPAD_RIGHT,
        "KEYCODE_DPAD_DOWN" : uinput.BTN_DPAD_DOWN,
        "KEYCODE_BUTTON_START" : uinput.BTN_START,
        "KEYCODE_BUTTON_SELECT" : uinput.BTN_SELECT,
        "KEYCODE_BUTTON_X" : uinput.BTN_X,
        "KEYCODE_BUTTON_Y" : uinput.BTN_Y,
        "KEYCODE_BUTTON_A" : uinput.BTN_A,
        "KEYCODE_BUTTON_B" : uinput.BTN_B
        }

device = uinput.Device(list(keyMap.values()))

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
    print "Waiting for connection"
    client_sock, client_info = server_sock.accept()
    print "Accepted connection from ", client_info
    
    try:
        while True:
            data = client_sock.recv(1024)
            if len(data) == 0: break
            if data in keyMap:
                print data
                device.emit_click(keyMap[data])
            else:
                print "Key not defined in map!"
    except IOError:
        print "Lost connection"
        client_sock.close()
        continue
    
server_sock.close()

