package com.threbrooks.universalcontroller;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity implements BluetoothManager.BluetoothConnectorInterface {

    String TAG = "universalcontroller";
    String APP_VERSION = "1.0.0";
    static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 0;

    BluetoothManager mBluetoothManager = null;

    LinearLayout mSettingSliderLL = null;
    ListView mSettingSliderMain = null;
    ArrayAdapter<String> mSettingsSliderMainAdapter = null;

    ListView mSettingSliderSelect = null;
    ArrayAdapter<DisplayItem> mSettingsSliderSelectAdapter = null;

    ImageView mBluetoothIV = null;
    ImageView mSettingsButton = null;

    enum SettingsMainSelectState {
        Controller,
        Bluetooth,
        None
    }

    SettingsMainSelectState mSettingSliderState = SettingsMainSelectState.None;

    enum SettingsViewState {
        Visible,
        Invisible,
    }

    SettingsViewState mSettingsViewState = SettingsViewState.Visible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_fullscreen);

        final Resources res = getResources();
        mSettingSliderLL = (LinearLayout) findViewById(R.id.settings_slider);
        mSettingSliderMain = (ListView) findViewById(R.id.settings_slider_main);
        mSettingsSliderMainAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        mSettingsSliderMainAdapter.addAll(res.getStringArray(R.array.settings_slider_list));
        mSettingSliderMain.setAdapter(mSettingsSliderMainAdapter);
        mSettingSliderMain.setOnItemClickListener(mSettingsSliderMainOnClickListener);

        mSettingSliderSelect = (ListView) findViewById(R.id.settings_slider_select);
        mSettingsSliderSelectAdapter = new ArrayAdapter<DisplayItem>(this, android.R.layout.simple_list_item_1);
        mSettingSliderSelect.setAdapter(mSettingsSliderSelectAdapter);
        mSettingSliderSelect.setOnItemClickListener(mSettingsSliderSelectOnClickListener);

        mSettingsButton = (ImageView) findViewById(R.id.settings_button);
        mSettingsButton.setOnClickListener(mSettingButtonOnClickListener);
        mBluetoothIV = (ImageView) findViewById(R.id.bluetooth_icon);
        mBluetoothIV.setOnClickListener(mSettingButtonOnClickListener);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }

        mBluetoothManager = new BluetoothManager(this, this);

        SelectController(0);
        SettingsMainSlide();
    }

    void SettingsMainSlide() {
        if (mSettingsViewState == SettingsViewState.Invisible) {
            mSettingsViewState = SettingsViewState.Visible;
        } else if (mSettingsViewState == SettingsViewState.Visible) {
            mSettingsViewState = SettingsViewState.Invisible;
        }
        mSettingSliderLL.setVisibility(mSettingsViewState == SettingsViewState.Visible ? View.VISIBLE : View.INVISIBLE);
    }

    View.OnClickListener mSettingButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SettingsMainSlide();
        }
    };

    AdapterView.OnItemClickListener mSettingsSliderMainOnClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long ID) {
            if (mSettingSliderState == SettingsMainSelectState.None) {
                if (pos == 0) mSettingSliderState = SettingsMainSelectState.Controller;
                else if (pos == 1) mSettingSliderState = SettingsMainSelectState.Bluetooth;
            } else if (mSettingSliderState == SettingsMainSelectState.Controller) {
                if (pos == 0) mSettingSliderState = SettingsMainSelectState.None;
                else if (pos == 1) mSettingSliderState = SettingsMainSelectState.Bluetooth;
            } else if (mSettingSliderState == SettingsMainSelectState.Bluetooth) {
                if (pos == 0) mSettingSliderState = SettingsMainSelectState.Controller;
                else if (pos == 1) mSettingSliderState = SettingsMainSelectState.None;
            }

            mSettingSliderSelect.setVisibility(mSettingSliderState == SettingsMainSelectState.None ? View.INVISIBLE : View.VISIBLE);
            mSettingsSliderSelectAdapter.clear();

            if (mSettingSliderState == SettingsMainSelectState.Bluetooth) {
                mSettingsSliderSelectAdapter.addAll(mBluetoothManager.getPairedDevices());
            } else if (mSettingSliderState == SettingsMainSelectState.Controller) {

                final Resources res = getResources();
                final String[] controllerListStringRes = res.getStringArray(R.array.controller_init_list);
                ArrayList<DisplayItem> controllerListDisplay = new ArrayList<DisplayItem>();
                for (String stringRes : controllerListStringRes) {
                    int resId = res.getIdentifier(getPackageName() + ":string/" + stringRes, null, null);
                    controllerListDisplay.add(new DisplayItem(getResources().getString(resId)));
                }
                mSettingsSliderSelectAdapter.addAll(controllerListDisplay);
            }
        }
    };

    AdapterView.OnItemClickListener mSettingsSliderSelectOnClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long ID) {
            DisplayItem dispIt = (DisplayItem) adapterView.getItemAtPosition(position);
            if (dispIt.getClass() == BluetoothDeviceDisplay.class) {
                mBluetoothManager.setBluetoothDevice(((BluetoothDeviceDisplay) dispIt).mDevice);
            } else if (dispIt.getClass() == DisplayItem.class) {
                SelectController(position);
            }
            SettingsMainSlide();
        }
    };

    ControllerBaseView mCurrentControllerView = null;

    private void SelectController(int i) {
        ControllerBaseView newView = null;
        final Resources res = getResources();
        final String[] controllerListStringRes = res.getStringArray(R.array.controller_init_list);
        int resId = res.getIdentifier(getPackageName() + ":string/" + controllerListStringRes[i], null, null);
        RelativeLayout mainCL = (RelativeLayout) findViewById(R.id.MainLinearLayout);
        if (resId == R.string.controller_c64_keyboard) {
            newView = new ControllerC64Keyboard(FullscreenActivity.this);
        } else if (resId == R.string.controller_amiga_keyboard) {
            newView = new ControllerAmigaKeyboard(FullscreenActivity.this);
        } else if (resId == R.string.controller_snes) {
            newView = new ControllerSNES(FullscreenActivity.this);
        } else if (resId == R.string.controller_mouse) {
            newView = new ControllerMouse(FullscreenActivity.this, mainCL);
        } else if (resId == R.string.controller_digital_joystick) {
            newView = new ControllerDigitalJoystick(FullscreenActivity.this);
        } else if (resId == R.string.controller_android_keyboard) {
            newView = new ControllerAndroidKeyboard(FullscreenActivity.this, mainCL);
        } else if (resId == R.string.controller_intellivision) {
            newView = new ControllerIntellivision(FullscreenActivity.this);
        } else if (resId == R.string.controller_colecovision) {
            newView = new ControllerColecoVision(FullscreenActivity.this);
        }
        if (newView != null) {
            newView.setBluetoothManager(mBluetoothManager);
            newView.setLayoutParams(new LinearLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.MATCH_PARENT));
        }
        if (mCurrentControllerView != null) {
            mCurrentControllerView.shutdown();
            mainCL.removeView(mCurrentControllerView);
        }
        mainCL.addView(newView, 0);
        mCurrentControllerView = newView;
        mainCL.requestLayout();
        mainCL.postInvalidate();
    }

    public void bluetoothConnected(final String version) {
        if (!version.equals(APP_VERSION)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FullscreenActivity.this);
                    builder.setMessage("The application version (" + APP_VERSION + ") does not match the server Python script's version (" + version + "). You will probably have to update the Python script").setTitle("Version mismatch");
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
                if (mBluetoothIV != null) mBluetoothIV.setBackgroundResource(R.drawable.bluetooth_connected);
            }
        });
    }

    public void bluetoothDisconnected() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBluetoothIV != null) mBluetoothIV.setBackgroundResource(R.drawable.bluetooth_disconnected);
            }
        });
    }

}
