package com.threbrooks.bluetoothkeyboard;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by Me on 2018-01-24.
 */

public class ControllerSNES extends BitmapControllerView {

    static String TAG = "ControllerSNES";

    public ControllerSNES(Context context) {
        super(context, R.drawable.controller_snes, R.drawable.controller_snes_mask);
    }

    int blueToKeyCode(int b) {
        if (b == 0) return KeyEvent.KEYCODE_DPAD_UP;
        else if (b == 1) return KeyEvent.KEYCODE_DPAD_LEFT;
        else if (b == 2) return KeyEvent.KEYCODE_DPAD_RIGHT;
        else if (b == 3) return KeyEvent.KEYCODE_DPAD_DOWN;
        else if (b == 4) return KeyEvent.KEYCODE_BUTTON_SELECT;
        else if (b == 5) return KeyEvent.KEYCODE_BUTTON_START;
        else if (b == 6) return KeyEvent.KEYCODE_BUTTON_X;
        else if (b == 7) return KeyEvent.KEYCODE_BUTTON_Y;
        else if (b == 8) return KeyEvent.KEYCODE_BUTTON_A;
        else if (b == 9) return KeyEvent.KEYCODE_BUTTON_B;
        else return -1;
    }

    public void onPixelClick(int r, int g, int b, int action) {
        if (r == 255 & g == 255) {
            transmitKey(blueToKeyCode(b), action == MotionEvent.ACTION_DOWN ? "KEY_DOWN" : "KEY_UP");
        }
    }
}
