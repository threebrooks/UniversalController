package com.threbrooks.universalcontroller;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.threbrooks.universalcontroller.R;

public class ControllerDigitalJoystick extends BitmapControllerView {

    static String TAG = "ControllerDigitalJoystick";

    public ControllerDigitalJoystick(Context context) {
        super(context, R.drawable.controller_digital_joystick, R.drawable.controller_digital_joystick_mask, false);
    }

    String mPrevDirKey = "";

    public boolean onPixelClick(int r, int g, int b, boolean pressed) {
        Log.d(TAG, r+","+g+","+b);
        if (r == 247 & g == 247) {
            String dirKey = "";
            if (b == 0) dirKey = UInput.KEY_KP8;
            else if (b == 1) dirKey = UInput.KEY_KP9;
            else if (b == 2) dirKey = UInput.KEY_KP6;
            else if (b == 3) dirKey = UInput.KEY_KP3;
            else if (b == 4) dirKey = UInput.KEY_KP2;
            else if (b == 5) dirKey = UInput.KEY_KP1;
            else if (b == 6) dirKey = UInput.KEY_KP4;
            else if (b == 7) dirKey = UInput.KEY_KP7;
            else if (b == 8) dirKey = UInput.KEY_KP0;

            if (!mPrevDirKey.equals(dirKey) && !mPrevDirKey.equals("") && pressed) {
                transmitEvent(UInput.createKeyEvent(mPrevDirKey, false));
            }

            transmitEvent(UInput.createKeyEvent(dirKey, pressed));

            mPrevDirKey = dirKey;

            return true;
        }
        return false;
    }

    public void shutdown() {
    }
}
