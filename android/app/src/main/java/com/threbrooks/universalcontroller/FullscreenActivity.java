package com.threbrooks.universalcontroller;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity implements BluetoothManager.BluetoothConnectorInterface {

    String TAG = "universalcontroller";
    String APP_VERSION="1.0.0";

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

        getSupportActionBar().setDisplayShowTitleEnabled(false);

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
        mActionMenuBluetoothIV.setLayoutParams(new LinearLayout.LayoutParams( (int)(0.5*getSupportActionBar().getHeight()), (int)(0.5*getSupportActionBar().getHeight())));

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
                if (resId == R.string.controller_c64_keyboard) {
                    newView = new ControllerC64Keyboard(FullscreenActivity.this);
                } else if (resId == R.string.controller_amiga_keyboard) {
                    newView = new ControllerAmigaKeyboard(FullscreenActivity.this);
                } else if (resId == R.string.controller_snes) {
                    newView = new ControllerSNES(FullscreenActivity.this);
                }  else if (resId == R.string.controller_mouse) {
                    newView = new ControllerMouse(FullscreenActivity.this, mainLL);
                }  else if (resId == R.string.controller_digital_joystick) {
                    newView = new ControllerDigitalJoystick(FullscreenActivity.this);
                }  else if (resId == R.string.controller_android_keyboard) {
                    newView = new ControllerAndroidKeyboard(FullscreenActivity.this, mainLL);
                }
                if (newView != null) {
                    newView.setBluetoothManager(mBluetoothManager);
                    newView.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT));
                }
                int childCount = mainLL.getChildCount();
                if (childCount > 1) {
                    ControllerBaseView oldView = (ControllerBaseView)mainLL.getChildAt(childCount-1);
                    oldView.shutdown();
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

    public void bluetoothConnected(final String version) {
        if (!version.equals(APP_VERSION)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FullscreenActivity.this);
                    builder.setMessage("The application version ("+APP_VERSION+") does not match the server Python script's version ("+version+"). You will probably have to update the Python script").setTitle("Version mismatch");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            FullscreenActivity.this.finish();
                        }
                    });
                    builder.create().show();
                }
            });
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mActionMenuBluetoothIV != null)
                    mActionMenuBluetoothIV.setBackgroundResource(R.drawable.bluetooth_connected);
            }});
    }

    public void bluetoothDisconnected() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mActionMenuBluetoothIV != null)
                    mActionMenuBluetoothIV.setBackgroundResource(R.drawable.bluetooth_disconnected);
            }});
    }

}
