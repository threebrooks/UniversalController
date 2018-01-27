#!/usr/bin/python

import uinput
from bluetooth import *

uuid = "94f39d29-7d6d-437d-973b-fba39e49d4ee"

keyMap = {
        "KEYCODE_0" : uinput.KEY_0,
        "KEYCODE_1" : uinput.KEY_1,
        "KEYCODE_2" : uinput.KEY_2,
        "KEYCODE_3" : uinput.KEY_3,
        "KEYCODE_4" : uinput.KEY_4,
        "KEYCODE_5" : uinput.KEY_5,
        "KEYCODE_6" : uinput.KEY_6,
        "KEYCODE_7" : uinput.KEY_7,
        "KEYCODE_8" : uinput.KEY_8,
        "KEYCODE_9" : uinput.KEY_9,
        "KEYCODE_A" : uinput.KEY_A,
        "KEYCODE_B" : uinput.KEY_B,
        "KEYCODE_C" : uinput.KEY_C,
        "KEYCODE_D" : uinput.KEY_D,
        "KEYCODE_E" : uinput.KEY_E,
        "KEYCODE_F" : uinput.KEY_F,
        "KEYCODE_G" : uinput.KEY_G,
        "KEYCODE_H" : uinput.KEY_H,
        "KEYCODE_I" : uinput.KEY_I,
        "KEYCODE_J" : uinput.KEY_J,
        "KEYCODE_K" : uinput.KEY_K,
        "KEYCODE_L" : uinput.KEY_L,
        "KEYCODE_M" : uinput.KEY_M,
        "KEYCODE_N" : uinput.KEY_N,
        "KEYCODE_O" : uinput.KEY_O,
        "KEYCODE_P" : uinput.KEY_P,
        "KEYCODE_Q" : uinput.KEY_Q,
        "KEYCODE_R" : uinput.KEY_R,
        "KEYCODE_S" : uinput.KEY_S,
        "KEYCODE_T" : uinput.KEY_T,
        "KEYCODE_U" : uinput.KEY_U,
        "KEYCODE_V" : uinput.KEY_V,
        "KEYCODE_W" : uinput.KEY_W,
        "KEYCODE_X" : uinput.KEY_X,
        "KEYCODE_Y" : uinput.KEY_Y,
        "KEYCODE_Z" : uinput.KEY_Z,
        "KEYCODE_AT" : uinput.KEY_AT,
        "KEYCODE_BACKSLASH" : uinput.KEY_BACKSLASH,
        "KEYCODE_BUTTON_A" : uinput.KEY_A,
        "KEYCODE_BUTTON_B" : uinput.KEY_B
        "KEYCODE_BUTTON_SELECT" : uinput.KEY_F2,
        "KEYCODE_BUTTON_START" : uinput.KEY_F1,
        "KEYCODE_BUTTON_X" : uinput.KEY_X,
        "KEYCODE_BUTTON_Y" : uinput.KEY_Y,
        "KEYCODE_COMMA" : uinput.KEY_COMMA,
        "KEYCODE_CTRL_LEFT" : uinput.KEY_CTRL_LEFT,
        "KEYCODE_DEL" : uinput.KEY_DEL,
        "KEYCODE_DPAD_DOWN" : uinput.KEY_DOWN,
        "KEYCODE_DPAD_LEFT" : uinput.KEY_LEFT,
        "KEYCODE_DPAD_RIGHT" : uinput.KEY_DPAD_RIGHT,
        "KEYCODE_DPAD_UP" : uinput.KEY_UP,
        "KEYCODE_ENTER" : uinput.KEY_ENTER,
        "KEYCODE_EQUALS" : uinput.KEY_EQUALS,
        "KEYCODE_ESCAPE" : uinput.KEY_ESCAPE,
        "KEYCODE_F1" : uinput.KEY_F1,
        "KEYCODE_F2" : uinput.KEY_F2,
        "KEYCODE_F3" : uinput.KEY_F3,
        "KEYCODE_F4" : uinput.KEY_F4,
        "KEYCODE_F5" : uinput.KEY_F5,
        "KEYCODE_F6" : uinput.KEY_F6,
        "KEYCODE_F7" : uinput.KEY_F7,
        "KEYCODE_F8" : uinput.KEY_F8,
        "KEYCODE_GRAVE" : uinput.KEY_GRAVE,
        "KEYCODE_HOME" : uinput.KEY_HOME,
        "KEYCODE_INSERT" : uinput.KEY_INSERT,
        "KEYCODE_MINUS" : uinput.KEY_MINUS,
        "KEYCODE_PAGE_UP" : uinput.KEY_PAGE_UP,
        "KEYCODE_PERIOD" : uinput.KEY_PERIOD,
        "KEYCODE_PLUS" : uinput.KEY_PLUS,
        "KEYCODE_SEMICOLON" : uinput.KEY_SEMICOLON,
        "KEYCODE_SHIFT_LEFT" : uinput.KEY_SHIFT_LEFT,
        "KEYCODE_SHIFT_RIGHT" : uinput.KEY_SHIFT_RIGHT,
        "KEYCODE_SLASH" : uinput.KEY_SLASH,
        "KEYCODE_SPACE" : uinput.KEY_SPACE,
        "KEYCODE_STAR" : uinput.KEY_STAR,
        "KEYCODE_TAB" : uinput.KEY_TAB,
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

