package com.threbrooks.bluetoothkeyboard;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {

    Button mButtonArrowUp = null;
    Button mButtonArrowLeft = null;
    Button mButtonArrowRight = null;
    Button mButtonArrowDown = null;
    Button mButtonX = null;
    Button mButtonY = null;
    Button mButtonA = null;
    Button mButtonB = null;

    String TAG = "BluetoothKeyboard";

    private final View.OnClickListener mArrowListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view == mButtonArrowUp) mBluetoothManager.writeString("KEY_UP");
            else if (view == mButtonArrowLeft) mBluetoothManager.writeString("KEY_LEFT");
            else if (view == mButtonArrowRight) mBluetoothManager.writeString("KEY_RIGHT");
            else if (view == mButtonArrowDown) mBluetoothManager.writeString("KEY_DOWN");
            else if (view == mButtonX) mBluetoothManager.writeString("KEY_X");
            else if (view == mButtonY) mBluetoothManager.writeString("KEY_Y");
            else if (view == mButtonA) mBluetoothManager.writeString("KEY_A");
            else if (view == mButtonB) mBluetoothManager.writeString("KEY_B");
        }
    };

    BluetoothManager mBluetoothManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_fullscreen);

        mButtonArrowUp = findViewById(R.id.bArrowUp);
        mButtonArrowLeft = findViewById(R.id.bArrowLeft);
        mButtonArrowRight = findViewById(R.id.bArrowRight);
        mButtonArrowDown = findViewById(R.id.bArrowDown);
        mButtonX = findViewById(R.id.bX);
        mButtonY = findViewById(R.id.bY);
        mButtonA = findViewById(R.id.bA);
        mButtonB = findViewById(R.id.bB);

        mButtonArrowUp.setOnClickListener(mArrowListener);
        mButtonArrowLeft.setOnClickListener(mArrowListener);
        mButtonArrowRight.setOnClickListener(mArrowListener);
        mButtonArrowDown.setOnClickListener(mArrowListener);
        mButtonA.setOnClickListener(mArrowListener);
        mButtonB.setOnClickListener(mArrowListener);
        mButtonX.setOnClickListener(mArrowListener);
        mButtonY.setOnClickListener(mArrowListener);

        mBluetoothManager= new BluetoothManager(this);
    }
}
