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

    String blueToKeyCode(int b) {
        if (b == 0) return UInput.KEY_UP;
        else if (b == 1) return UInput.KEY_LEFT;
        else if (b == 2) return UInput.KEY_RIGHT;
        else if (b == 3) return UInput.KEY_DOWN;
        else if (b == 4) return UInput.KEY_F1;
        else if (b == 5) return UInput.KEY_F2;
        else if (b == 6) return UInput.KEY_X;
        else if (b == 7) return UInput.KEY_Y;
        else if (b == 8) return UInput.KEY_A;
        else if (b == 9) return UInput.KEY_B;
        else return "";
    }

    public boolean onPixelClick(int r, int g, int b, boolean pressed) {
        if (r == 255 & g == 255) {
            transmitKey(blueToKeyCode(b), pressed ? "KEY_DOWN" : "KEY_UP");
            return true;
        }
        return false;
    }
}
