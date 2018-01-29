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

    String blueToKeyCode(int b) {
        if (b == 65) return UInput.KEY_GRAVE; // Left arrow
        else if (b == 0) return UInput.KEY_1;
        else if (b == 1) return UInput.KEY_2;
        else if (b == 2) return UInput.KEY_3;
        else if (b == 3) return UInput.KEY_4;
        else if (b == 4) return UInput.KEY_5;
        else if (b == 5) return UInput.KEY_6;
        else if (b == 6) return UInput.KEY_7;
        else if (b == 7) return UInput.KEY_8;
        else if (b == 8) return UInput.KEY_9;
        else if (b == 9) return UInput.KEY_0;
        else if (b == 10) return UInput.KEY_KPPLUS;
        else if (b == 11) return UInput.KEY_MINUS;
        else if (b == 12) return UInput.KEY_BACKSLASH; // Pound key
        else if (b == 13) return UInput.KEY_HOME; // Clr/Home
        else if (b == 14) return UInput.KEY_INSERT; // Insert/Delete ?
        else if (b == 15) return UInput.KEY_TAB; // CTRL
        else if (b == 16) return UInput.KEY_Q;
        else if (b == 17) return UInput.KEY_W;
        else if (b == 18) return UInput.KEY_E;
        else if (b == 19) return UInput.KEY_R;
        else if (b == 20) return UInput.KEY_T;
        else if (b == 21) return UInput.KEY_Y;
        else if (b == 22) return UInput.KEY_U;
        else if (b == 23) return UInput.KEY_I;
        else if (b == 24) return UInput.KEY_O;
        else if (b == 25) return UInput.KEY_P;
        else if (b == 26) return UInput.KEY_LEFTSHIFT+"|"+UInput.KEY_2; // At sign
        else if (b == 27) return UInput.KEY_LEFTSHIFT+"|"+UInput.KEY_8; // Star
        else if (b == 28) return UInput.KEY_DELETE; // Arrow up
        else if (b == 29) return UInput.KEY_PAGEUP; // Restore
        else if (b == 30) return UInput.KEY_ESC; // Run/stop
        else if (b == 66) return ""; // Shift lock?
        else if (b == 31) return UInput.KEY_A;
        else if (b == 32) return UInput.KEY_S;
        else if (b == 33) return UInput.KEY_D;
        else if (b == 34) return UInput.KEY_F;
        else if (b == 35) return UInput.KEY_G;
        else if (b == 36) return UInput.KEY_H;
        else if (b == 37) return UInput.KEY_J;
        else if (b == 38) return UInput.KEY_K;
        else if (b == 39) return UInput.KEY_L;
        else if (b == 40) return UInput.KEY_LEFTSHIFT+"|"+UInput.KEY_SEMICOLON; // Colon
        else if (b == 41) return UInput.KEY_SEMICOLON;
        else if (b == 42) return UInput.KEY_EQUAL;
        else if (b == 43) return UInput.KEY_ENTER; // Return
        else if (b == 44) return UInput.KEY_LEFTCTRL; // Commodore key
        else if (b == 45) return UInput.KEY_LEFTSHIFT;
        else if (b == 46) return UInput.KEY_Z;
        else if (b == 47) return UInput.KEY_X;
        else if (b == 48) return UInput.KEY_C;
        else if (b == 49) return UInput.KEY_V;
        else if (b == 50) return UInput.KEY_B;
        else if (b == 51) return UInput.KEY_N;
        else if (b == 52) return UInput.KEY_M;
        else if (b == 53) return UInput.KEY_COMMA;
        else if (b == 54) return UInput.KEY_DOT;
        else if (b == 55) return UInput.KEY_SLASH;
        else if (b == 56) return UInput.KEY_RIGHTSHIFT;
        else if (b == 57) return UInput.KEY_DOWN;
        else if (b == 58) return UInput.KEY_RIGHT;
        else if (b == 59) return UInput.KEY_SPACE;
        else if (b == 60) return UInput.KEY_F1;
        else if (b == 61) return UInput.KEY_F3;
        else if (b == 63) return UInput.KEY_F5;
        else if (b == 62) return UInput.KEY_F7;
        else return "";
    }

    public boolean onPixelClick(int r, int g, int b, boolean pressed) {
        Log.d(TAG,"r="+r+" g="+g+" b="+b);
        if (r == 247 & g == 247) {
            String keyString = blueToKeyCode(b);
            if (!keyString.equals(""))transmitKey(keyString, pressed ? "KEY_DOWN" : "KEY_UP");
            return true;
        }
        return false;
    }
}
