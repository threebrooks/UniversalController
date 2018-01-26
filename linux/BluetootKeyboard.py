# file: rfcomm-server.py

# auth: Albert Huang <albert@csail.mit.edu>
# desc: simple demonstration of a server application that uses RFCOMM sockets
#
# $Id: rfcomm-server.py 518 2007-08-10 07:20:07Z albert $

import uinput
from bluetooth import *

uuid = "94f39d29-7d6d-437d-973b-fba39e49d4ee"

keyMap = {
        "KEYCODE_DPAD_UP" : uinput.KEY_DPAD_UP,
        "KEYCODE_DPAD_LEFT" : uinput.KEY_DPAD_LEFT,
        "KEYCODE_DPAD_RIGHT" : uinput.KEY_DPAD_RIGHT,
        "KEYCODE_DPAD_DOWN" : uinput.KEY_DPAD_DOWN,
        "KEYCODE_BUTTON_START" : uinput.KEY_BTN_START,
        "KEYCODE_BUTTON_SELECT" : uinput.KEY_BTN_SELECT,
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
    client_sock, client_info = server_sock.accept()
    print "Accepted connection from ", client_info
    
    try:
        while True:
            data = client_sock.recv(1024)
            if len(data) == 0: break
            if data == "KEY_X":
                device.emit_click(uinput.KEY_X)
            elif data == "KEY_Y":
                device.emit_click(uinput.KEY_Y)
            elif data == "KEY_A":
                device.emit_click(uinput.KEY_A)
            elif data == "KEY_B":
                device.emit_click(uinput.KEY_B)
            elif data == "KEY_UP":
                device.emit_click(uinput.KEY_UP)
            elif data == "KEY_LEFT":
                device.emit_click(uinput.KEY_LEFT)
            elif data == "KEY_RIGHT":
                device.emit_click(uinput.KEY_RIGHT)
            elif data == "KEY_DOWN":
                device.emit_click(uinput.KEY_DOWN)
    except IOError:
        client_sock.close()
        continue
    
server_sock.close()

