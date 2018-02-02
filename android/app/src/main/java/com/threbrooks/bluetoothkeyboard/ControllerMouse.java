package com.threbrooks.bluetoothkeyboard;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Button;

public class ControllerMouse extends ControllerBaseView {

    static String TAG = "BitmapControllerView";
    GestureDetector mGestureDetector = null;
    Button mLeftButton = null;

    public ControllerMouse(Context context, ViewGroup parent) {
        super(context);
        inflate(context, R.layout.controller_mouse, parent);
        mGestureDetector = new GestureDetector(context ,mSimpleGestureListener);
    }

    /*
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mGestureDetector.onTouchEvent(ev);
        if ((ev.getPointerCount() == 1) && (ev.getActionMasked() == MotionEvent.ACTION_DOWN || ev.getActionMasked() == MotionEvent.ACTION_UP)) {
            return true;
        }
        return false;
    } */

    GestureDetector.SimpleOnGestureListener mSimpleGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (e2.getPointerCount() == 1) {
                transmitEvent(UInput.createMouseEvent(distanceX, distanceY));
                return false;
            }
            return false;
        }
    };
}