package com.threbrooks.bluetoothkeyboard;

import android.content.Context;
import android.provider.Contacts;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class ControllerMouse extends ControllerBaseView {

    static String TAG = "ControllerMouse";
    GestureDetector mGestureDetector = null;
    Button mLeftButton = null;
    Button mRightButton = null;
    LinearLayout mLayout = null;

    public ControllerMouse(Context context, ViewGroup parent) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayout = (LinearLayout)inflater.inflate(R.layout.controller_mouse, null, true);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mLayout.setLayoutParams(layoutParams);
        addView(mLayout);

        mGestureDetector = new GestureDetector(context ,mSimpleGestureListener);

        mLeftButton = (Button)findViewById(R.id.controller_mouse_left_button);
        mLeftButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getActionMasked();
                if (!(action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_UP)) return false;
                transmitEvent(UInput.createKeyEvent(UInput.BTN_LEFT, motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN));
                return false;
            }
        });
        mRightButton = (Button)findViewById(R.id.controller_mouse_right_button);
        mRightButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getActionMasked();
                if (!(action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_UP)) return false;
                transmitEvent(UInput.createKeyEvent(UInput.BTN_RIGHT, motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN));
                return false;
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mGestureDetector.onTouchEvent(ev);
        if ((ev.getPointerCount() == 1) && (ev.getActionMasked() == MotionEvent.ACTION_DOWN || ev.getActionMasked() == MotionEvent.ACTION_UP)) {
            return true;
        }
        return false;
    }

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