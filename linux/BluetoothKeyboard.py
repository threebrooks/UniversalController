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

log = open("/var/log/bluetoothkeyboard.log","w")

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
    log.write("Waiting for connection\n")
    log.flush()
    client_sock, client_info = server_sock.accept()
    log.write("Accepted connection from "+ str(client_info)+"\n")
    log.flush()
    
    try:
        while True:
            data = client_sock.recv(1024)
            if len(data) == 0: continue
            els = str(data).split(",") 
            if els[0] in keyMap:
                if (els[1] == "KEY_DOWN"):
                  press = 1
                else:
                  press = 0
                log.write(els[0]+" "+els[1]+"\n")
                log.flush()
                device.emit(keyMap[els[0]], press)
            else:
                log.write("Key not defined in map!\n")
                log.flush()
    except:
        log.write("Lost connection\n"+str(sys.exc_info()[0]))
        log.flush()
        client_sock.close()
        continue
    
server_sock.close()

