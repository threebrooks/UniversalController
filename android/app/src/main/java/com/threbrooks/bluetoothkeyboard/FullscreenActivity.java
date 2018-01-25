package com.threbrooks.bluetoothkeyboard;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

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
public class FullscreenActivity extends AppCompatActivity {

    String TAG = "BluetoothKeyboard";
/*
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
    }; */

    BluetoothManager mBluetoothManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_fullscreen);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(myToolbar);

        mBluetoothManager= new BluetoothManager(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar, menu);

        MenuItem item = menu.findItem(R.id.action_bar_dropdown);
        final Spinner actionBarDropDown = (Spinner) item.getActionView();

        final Resources res = getResources();
        final String[] controllerListStringRes = res.getStringArray(R.array.controller_init_list);
        ArrayList<String> controllerListDisplay = new ArrayList<String>();
        for(String stringRes : controllerListStringRes) {
            int resId = res.getIdentifier( getPackageName()+":string/"+stringRes, null, null);
            controllerListDisplay.add(getResources().getString(resId));
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, controllerListDisplay);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        actionBarDropDown.setAdapter(spinnerArrayAdapter);
        actionBarDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                BitmapControllerView newView = null;
                int resId = res.getIdentifier( getPackageName()+":string/"+controllerListStringRes[i], null, null);
                if (resId == R.string.controller_c64) {
                    newView = new ControllerCommodore64(FullscreenActivity.this);
                } else if (resId == R.string.controller_amiga) {
                    newView = new ControllerCommodoreAmiga(FullscreenActivity.this);
                } else if (resId == R.string.controller_snes) {
                    newView = new ControllerSNES(FullscreenActivity.this);
                }
                if (newView != null) {
                    newView.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT));
                }
                LinearLayout mainLL = (LinearLayout) findViewById(R.id.MainLinearLayout);
                int childCount = mainLL.getChildCount();
                if (childCount > 1) {
                    mainLL.removeViewAt(childCount - 1);
                }
                mainLL.addView(newView);
                mainLL.requestLayout();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return true;
    }
}
