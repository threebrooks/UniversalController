# file: rfcomm-server.py

# auth: Albert Huang <albert@csail.mit.edu>
# desc: simple demonstration of a server application that uses RFCOMM sockets
#
# $Id: rfcomm-server.py 518 2007-08-10 07:20:07Z albert $

import uinput
from bluetooth import *

device = uinput.Device([
    uinput.KEY_X,
    uinput.KEY_Y,
    uinput.KEY_A,
    uinput.KEY_B,
    uinput.KEY_UP,
    uinput.KEY_LEFT,
    uinput.KEY_RIGHT,
    uinput.KEY_DOWN
    ])

server_sock=BluetoothSocket( RFCOMM )
server_sock.bind(("",PORT_ANY))
server_sock.listen(1)

port = server_sock.getsockname()[1]

uuid = "94f39d29-7d6d-437d-973b-fba39e49d4ee"

advertise_service( server_sock, "SampleServer",
                   service_id = uuid,
                   service_classes = [ uuid, SERIAL_PORT_CLASS ],
                   profiles = [ SERIAL_PORT_PROFILE ], 
#                   protocols = [ OBEX_UUID ] 
                    )

print "Waiting for connection on RFCOMM channel %d" % port

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
    pass

print "disconnected"

client_sock.close()
server_sock.close()
print "all done"

