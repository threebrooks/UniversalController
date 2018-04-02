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

## FAQ

Don't know yet, nobody has asked me anything yet :)

## Contributing

The app is located in the android/ folder of the project, it's an Android Studio project.

### Adding another keyboard-like controller

That's the easy one. You should start with copying maybe the Commodore 64 keyboard code and use that as a template.

Now, the way these keyboard-like controllers work is that there are two PNG files (look at the android/app/src/main/res/drawable folder), one normal picture of the keyboard (e.g. controller_c64.png) and one "mask" picture (controller_c64_mask.png) that is the same size as the normal one.

The app only shows the main picture, but when you touch a section of the picture, the underlying code (the "BitmapControllerView" class you inherit from does this for you) looks up the exact same image location in the mask picture, and calls the onPixelClick() function with the RGB value of the mask image's pixel you clicked on.

If you look at the controller_c64_mask.png picture, you see that all the buttons are yellow boxes. Each box has a different RGB value (actually, to make it easy in the code, they all have the same red and green value, the only thing changing is the blue value).

So, to add another controller, create the two pictures and put them into that folder, then refer to them in the constructor of your controller class. 

Two more places need to be adjusted:

1. android/app/src/main/res/values/strings.xml : Add your new controller as a new string value, and also add it to the controller_init_list (this is the order displayed in the GUI)
2. android/app/src/main/java/com/threbrooks/universalcontroller/FullscreenActivity.java : Add your class into that long if-then-else section

Test and debug, and then create a pull request!

### Adding another fancy controller

That's a bit harder. You have to inherit from ControllerBaseView and build up your own layout.
