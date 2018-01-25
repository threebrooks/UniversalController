package com.threbrooks.bluetoothkeyboard;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;

/**
 * Created by Me on 2018-01-24.
 */

public class ControllerSNES extends BitmapControllerView {

    static String TAG = "ControllerSNES";

    public ControllerSNES(Context context) {
        super(context, R.drawable.controller_snes, R.drawable.controller_snes_mask);
    }

    public void onPixelClick(int r, int g, int b) {
        if (r == 255 & g == 255) {
            if (b == 0) transmitKey(KeyEvent.KEYCODE_DPAD_UP);
            else if (b == 1) transmitKey(KeyEvent.KEYCODE_DPAD_LEFT);
            else if (b == 2) transmitKey(KeyEvent.KEYCODE_DPAD_RIGHT);
            else if (b == 3) transmitKey(KeyEvent.KEYCODE_DPAD_DOWN);
            else if (b == 4) transmitKey(KeyEvent.KEYCODE_BUTTON_SELECT);
            else if (b == 5) transmitKey(KeyEvent.KEYCODE_BUTTON_START);
            else if (b == 6) transmitKey(KeyEvent.KEYCODE_BUTTON_X);
            else if (b == 7) transmitKey(KeyEvent.KEYCODE_BUTTON_Y);
            else if (b == 8) transmitKey(KeyEvent.KEYCODE_BUTTON_A);
            else if (b == 9) transmitKey(KeyEvent.KEYCODE_BUTTON_B);
        }
    }
}
