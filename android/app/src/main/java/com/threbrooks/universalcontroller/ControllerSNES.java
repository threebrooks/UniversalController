package com.threbrooks.universalcontroller;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;

import com.threbrooks.universalcontroller.R;

public class ControllerSNES extends BitmapControllerView {

    static String TAG = "ControllerSNES";

    public ControllerSNES(Context context) {
        super(context, false);
        setMaskBitmap(((BitmapDrawable) getResources().getDrawable(R.drawable.controller_snes_mask)).getBitmap());
        setLayerBitmap(((BitmapDrawable) getResources().getDrawable(R.drawable.controller_snes)).getBitmap(), 0);
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
        else if (b == 10) return UInput.KEY_L;
        else if (b == 11) return UInput.KEY_R;
        else return "";
    }

    public boolean onPixelClick(int r, int g, int b, boolean pressed) {
        if (r == 255 & g == 255) {
            transmitEvent(UInput.createKeyEvent(blueToKeyCode(b), pressed));
            return true;
        }
        return false;
    }

    public void shutdown() {}
}
