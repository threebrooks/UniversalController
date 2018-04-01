# UniversalController

## What is it?
UniversalController is an Android app that connects to your RetroPie via Bluetooth. You can use it as a generic, virtual controller for the different platforms. A short list of currently supported controllers:

1. SNES/RetroPie
<img src="https://github.com/threebrooks/UniversalController/blob/master/images/snes.png" alt="SNES controller" width="300px"/>

Useful for controlling SNES games, and RetroPie itself

2. Commodore 64 Keyboard
<img src="https://github.com/threebrooks/UniversalController/blob/master/images/c64_keyboard.png" alt="C64 keyboard" width="300px"/>

Supports all the keys (F-keys, Run/Stop etc) that one needs during use

3. Commodore Amiga Keyboard
<img src="https://github.com/threebrooks/UniversalController/blob/master/images/amiga_keyboard.png" alt="Amiga keyboard" width="300px"/>

4. Mouse
<img src="https://github.com/threebrooks/UniversalController/blob/master/images/mouse.png" alt="Mouse" width="300px"/>

Useful for Amiga games (for example) that require mouse input

5. Joystick
<img src="https://github.com/threebrooks/UniversalController/blob/master/images/joystick.png" alt="Joystick" width="300px"/>

A generic touchscreen joystick (Note: To use it you have to switch the joystick in VICE to "numpad")

6. Android Keyboard
<img src="https://github.com/threebrooks/UniversalController/blob/master/images/android_keyboard.png" alt="Android keyboard" width="300px"/>

Android keyboard are optimized for usability, this can be useful for writing longer text

Some of the more crammed keyboards (Amiga, C64) support pinch and zoom. E.g. if a game needs repeated use of the F-keys you can zoom in on those.

## Installation

### On the RPi
* Attach keyboard to RetroPie (this is just for installation)
* Drop out of RetroPie by hitting F4
* On the command line:
* `cd`
* `git clone https://github.com/threebrooks/UniversalController`
* `cd linux`
* `sudo ./install.sh`

Hopefully the installation went well, then restart the RPi with 

* `sudo shutdown -r now`

### On your Android device

* Go into the Bluetooth settings, select to pair a new device
* Your RetroPie should (allow some time for it to show up) show as "retropie". Pair with it
* Download the "UniversalController" app on the Google Play Store (TODO: add link!)
* In the top bar, tap on the right item (left of the Bluetooth icon) and select "retropie"
* After a few seconds, the Bluetooth icon should change to that it is connected
* You're ready to go! The drop-down list of platforms is to the left of the Bluetooth list
* To reiterate, the C64 and the Amiga keyboard support pinch and zoom (two fingers)
