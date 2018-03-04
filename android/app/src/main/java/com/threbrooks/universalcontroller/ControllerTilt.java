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

public class ControllerTilt extends ControllerBaseView implements SensorEventListener {

    static String TAG = "ControllerTilt";
    Button mLeftFire = null;
    Button mRightFire = null;
    LinearLayout mLayout = null;
    SensorManager mSensorManager = null;
    Sensor mAccelSensor = null;
    Sensor mMagnSensor = null;

    public ControllerTilt(Context context, ViewGroup parent) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayout = (LinearLayout)inflater.inflate(R.layout.controller_tilt, null, true);
        mLayout.setLongClickable(false);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mLayout.setLayoutParams(layoutParams);
        addView(mLayout);

        mLeftFire = (Button)findViewById(R.id.controller_tilt_left_button);
        mLeftFire.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getActionMasked();
                if (!(action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_UP)) return false;
                transmitEvent(UInput.createKeyEvent(UInput.BTN_LEFT, motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN));
                return false;
            }
        });
        mRightFire = (Button)findViewById(R.id.controller_tilt_right_button);
        mRightFire.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getActionMasked();
                if (!(action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_UP)) return false;
                transmitEvent(UInput.createKeyEvent(UInput.BTN_RIGHT, motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN));
                return false;
            }
        });

        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        mAccelSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelSensor , SensorManager.SENSOR_DELAY_FASTEST);

        mMagnSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorManager.registerListener(this, mMagnSensor , SensorManager.SENSOR_DELAY_FASTEST);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if ((ev.getPointerCount() == 1) && (ev.getActionMasked() == MotionEvent.ACTION_DOWN || ev.getActionMasked() == MotionEvent.ACTION_UP)) {
            return true;
        }
        return false;
    }

    float[] mCurrAccelVec = null;
    float[] mCurrMagnVec = null;

    enum State {
        Left,
        Center,
        Right
    }

    State mState = State.Center;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mCurrAccelVec = event.values;
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mCurrMagnVec = event.values;
        }
        if (mCurrAccelVec != null && mCurrMagnVec != null) {
            final float[] rotationMatrix = new float[9];
            mSensorManager.getRotationMatrix(rotationMatrix, null,
                    mCurrAccelVec, mCurrMagnVec);

            final float[] orientationAngles = new float[3];
            mSensorManager.getOrientation(rotationMatrix, orientationAngles);

            float angle = orientationAngles[1];

            //Log.d(TAG, ""+angle);

            State newState = State.Center;
            if (angle > Math.PI/8.0) newState = State.Right;
            if (angle < Math.PI/8.0) newState = State.Left;

            if(newState == State.Right) {
                if (mState == State.Center) {
                    transmitEvent(UInput.createKeyEvent(UInput.KEY_RIGHT, true));
                } else if (mState == State.Left) {
                    transmitEvent(UInput.createKeyEvent(UInput.KEY_LEFT, false));
                    transmitEvent(UInput.createKeyEvent(UInput.KEY_RIGHT, true));
                }
            } else if(newState == State.Center) {
                if (mState == State.Left) {
                    transmitEvent(UInput.createKeyEvent(UInput.KEY_LEFT, false));
                } else if (mState == State.Right) {
                    transmitEvent(UInput.createKeyEvent(UInput.KEY_RIGHT, false));
                }
            } else if(newState == State.Left) {
                if (mState == State.Center) {
                    transmitEvent(UInput.createKeyEvent(UInput.KEY_LEFT, true));
                } else if (mState == State.Right) {
                    transmitEvent(UInput.createKeyEvent(UInput.KEY_RIGHT, false));
                    transmitEvent(UInput.createKeyEvent(UInput.KEY_LEFT, true));
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
