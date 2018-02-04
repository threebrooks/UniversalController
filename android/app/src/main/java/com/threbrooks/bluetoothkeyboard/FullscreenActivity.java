package com.threbrooks.bluetoothkeyboard;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.drm.DrmStore;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity implements BluetoothManager.BluetoothConnectorInterface {

    String TAG = "BluetoothKeyboard";

    BluetoothManager mBluetoothManager = null;
    ImageView mActionMenuBluetoothIV = null;
    ArrayAdapter<BluetoothDeviceDisplay> mBluetoothArrayAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_fullscreen);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(myToolbar);

        mBluetoothManager= new BluetoothManager(this, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar, menu);
        final Resources res = getResources();

        // --------------- bluetooth icon ---------------
        MenuItem bluetoothIconMenuItem = menu.findItem(R.id.action_bar_bluetooth_icon);
        mActionMenuBluetoothIV = (ImageView) bluetoothIconMenuItem.getActionView();
        mActionMenuBluetoothIV.setBackgroundResource(R.drawable.bluetooth_disconnected);
        int margins = 150;
        mActionMenuBluetoothIV.setPadding(margins, margins, margins, margins);
        mActionMenuBluetoothIV.setAdjustViewBounds(true);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mActionMenuBluetoothIV.setLayoutParams(layoutParams);

        // --------------- bluetooth list ---------------
        MenuItem bluetoothListMenuItem = menu.findItem(R.id.action_bar_bluetooth_list);
        Spinner actionMenuBluetoothSpinner = (Spinner) bluetoothListMenuItem.getActionView();
        ArrayList<BluetoothDeviceDisplay> bluetoothListDisplay = new ArrayList<BluetoothDeviceDisplay>();
        mBluetoothArrayAdapter = new ArrayAdapter<BluetoothDeviceDisplay>
                (this, android.R.layout.simple_spinner_item, bluetoothListDisplay);
        mBluetoothArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionMenuBluetoothSpinner.setAdapter(mBluetoothArrayAdapter);

        actionMenuBluetoothSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                mBluetoothManager.setBluetoothDevice(((BluetoothDeviceDisplay)adapterView.getItemAtPosition(pos)).mDevice);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
            });

        // --------------- Controller list ------------------
        MenuItem controllerMenuItem = menu.findItem(R.id.action_bar_controller);
        final Spinner actionBarControllerSpinner = (Spinner) controllerMenuItem.getActionView();
        final String[] controllerListStringRes = res.getStringArray(R.array.controller_init_list);
        ArrayList<String> controllerListDisplay = new ArrayList<String>();
        for(String stringRes : controllerListStringRes) {
            int resId = res.getIdentifier( getPackageName()+":string/"+stringRes, null, null);
            controllerListDisplay.add(getResources().getString(resId));
        }
        ArrayAdapter<String> controllerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, controllerListDisplay);
        controllerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        actionBarControllerSpinner.setAdapter(controllerArrayAdapter);
        actionBarControllerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ControllerBaseView newView = null;
                int resId = res.getIdentifier( getPackageName()+":string/"+controllerListStringRes[i], null, null);
                LinearLayout mainLL = (LinearLayout) findViewById(R.id.MainLinearLayout);
                if (resId == R.string.controller_c64) {
                    newView = new ControllerCommodore64(FullscreenActivity.this);
                } else if (resId == R.string.controller_amiga) {
                    newView = new ControllerCommodoreAmiga(FullscreenActivity.this);
                } else if (resId == R.string.controller_snes) {
                    newView = new ControllerSNES(FullscreenActivity.this);
                }  else if (resId == R.string.controller_mouse) {
                    newView = new ControllerMouse(FullscreenActivity.this, mainLL);
                }
                if (newView != null) {
                    newView.setBluetoothManager(mBluetoothManager);
                    newView.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT));
                }
                int childCount = mainLL.getChildCount();
                if (childCount > 1) {
                    mainLL.removeViewAt(childCount - 1);
                }
                mainLL.addView(newView);
                mainLL.requestLayout();
                mainLL.postInvalidate();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        mBluetoothArrayAdapter.clear();
        mBluetoothArrayAdapter.addAll(mBluetoothManager.getPairedDevices());
        return true;
    }

    public void bluetoothConnected() {
        if (mActionMenuBluetoothIV != null) mActionMenuBluetoothIV.setBackgroundResource(R.drawable.bluetooth_connected);
    }

    public void bluetoothDisconnected() {
        if (mActionMenuBluetoothIV != null) mActionMenuBluetoothIV.setBackgroundResource(R.drawable.bluetooth_disconnected);
    }

}
