package com.threbrooks.bluetoothkeyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

/**
 * Created by Me on 2018-01-24.
 */

public class ControllerCommodore64 extends BitmapControllerView {

    static String TAG = "ControllerSNES";

    public ControllerCommodore64(Context context) {
        super(context, R.drawable.controller_c64, R.drawable.controller_c64_mask);
    }

    int blueToKeyCode(int b) {
        if (b == 0) return KeyEvent.KEYCODE_GRAVE; // Left arrow
        else if (b == 1) return KeyEvent.KEYCODE_1;
        else if (b == 2) return KeyEvent.KEYCODE_2;
        else if (b == 3) return KeyEvent.KEYCODE_3;
        else if (b == 4) return KeyEvent.KEYCODE_4;
        else if (b == 5) return KeyEvent.KEYCODE_5;
        else if (b == 6) return KeyEvent.KEYCODE_6;
        else if (b == 7) return KeyEvent.KEYCODE_7;
        else if (b == 8) return KeyEvent.KEYCODE_8;
        else if (b == 9) return KeyEvent.KEYCODE_9;
        else if (b == 10) return KeyEvent.KEYCODE_0;
        else if (b == 11) return KeyEvent.KEYCODE_PLUS;
        else if (b == 12) return KeyEvent.KEYCODE_MINUS;
        else if (b == 13) return KeyEvent.KEYCODE_BACKSLASH; // Pound key
        else if (b == 14) return KeyEvent.KEYCODE_HOME; // Clr/Home
        else if (b == 15) return KeyEvent.KEYCODE_INSERT; // Insert/Delete ?
        else if (b == 16) return KeyEvent.KEYCODE_TAB; // CTRL
        else if (b == 17) return KeyEvent.KEYCODE_Q;
        else if (b == 18) return KeyEvent.KEYCODE_W;
        else if (b == 19) return KeyEvent.KEYCODE_E;
        else if (b == 20) return KeyEvent.KEYCODE_R;
        else if (b == 21) return KeyEvent.KEYCODE_T;
        else if (b == 22) return KeyEvent.KEYCODE_Y;
        else if (b == 23) return KeyEvent.KEYCODE_U;
        else if (b == 24) return KeyEvent.KEYCODE_I;
        else if (b == 25) return KeyEvent.KEYCODE_O;
        else if (b == 26) return KeyEvent.KEYCODE_P;
        else if (b == 27) return KeyEvent.KEYCODE_AT;
        else if (b == 28) return KeyEvent.KEYCODE_STAR;
        else if (b == 29) return KeyEvent.KEYCODE_DEL; // Arrow up
        else if (b == 30) return KeyEvent.KEYCODE_PAGE_UP; // Restore
        else if (b == 31) return KeyEvent.KEYCODE_ESCAPE; // Run/stop
        else if (b == 32) return KeyEvent.KEYCODE_SPACE; // Shift lock?
        else if (b == 33) return KeyEvent.KEYCODE_A;
        else if (b == 34) return KeyEvent.KEYCODE_S;
        else if (b == 35) return KeyEvent.KEYCODE_D;
        else if (b == 36) return KeyEvent.KEYCODE_F;
        else if (b == 37) return KeyEvent.KEYCODE_G;
        else if (b == 38) return KeyEvent.KEYCODE_H;
        else if (b == 39) return KeyEvent.KEYCODE_J;
        else if (b == 40) return KeyEvent.KEYCODE_K;
        else if (b == 41) return KeyEvent.KEYCODE_L;
        else if (b == 42) return KeyEvent.KEYCODE_SPACE; // Colon ?
        else if (b == 43) return KeyEvent.KEYCODE_SEMICOLON;
        else if (b == 44) return KeyEvent.KEYCODE_EQUALS;
        else if (b == 45) return KeyEvent.KEYCODE_ENTER; // Return
        else if (b == 46) return KeyEvent.KEYCODE_CTRL_LEFT; // Commodore key
        else if (b == 47) return KeyEvent.KEYCODE_SHIFT_LEFT;
        else if (b == 48) return KeyEvent.KEYCODE_Z;
        else if (b == 49) return KeyEvent.KEYCODE_X;
        else if (b == 50) return KeyEvent.KEYCODE_C;
        else if (b == 51) return KeyEvent.KEYCODE_V;
        else if (b == 52) return KeyEvent.KEYCODE_B;
        else if (b == 53) return KeyEvent.KEYCODE_N;
        else if (b == 54) return KeyEvent.KEYCODE_M;
        else if (b == 55) return KeyEvent.KEYCODE_COMMA;
        else if (b == 56) return KeyEvent.KEYCODE_PERIOD;
        else if (b == 57) return KeyEvent.KEYCODE_SLASH;
        else if (b == 58) return KeyEvent.KEYCODE_SHIFT_RIGHT;
        else if (b == 59) return KeyEvent.KEYCODE_DPAD_DOWN;
        else if (b == 60) return KeyEvent.KEYCODE_DPAD_RIGHT;
        else if (b == 61) return KeyEvent.KEYCODE_SPACE;
        else if (b == 62) return KeyEvent.KEYCODE_F1;
        else if (b == 63) return KeyEvent.KEYCODE_F3;
        else if (b == 64) return KeyEvent.KEYCODE_F5;
        else if (b == 65) return KeyEvent.KEYCODE_F7;

        else return -1;
    }

    public boolean onPixelClick(int r, int g, int b, int action) {
        if (r == 255 & g == 255) {
            //Log.d(TAG,"b="+b);
            transmitKey(blueToKeyCode(b), action == MotionEvent.ACTION_DOWN ? "KEY_DOWN" : "KEY_UP");
            return true;
        }
        return false;
    }
}
