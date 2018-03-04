package com.threbrooks.universalcontroller;

import android.content.Context;
import android.widget.LinearLayout;

import org.json.JSONObject;

public abstract class ControllerBaseView extends LinearLayout {
    private BluetoothManager mBTManager = null;

    public ControllerBaseView(Context context) {
        super(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setLayoutParams(layoutParams);
    }

    public void setBluetoothManager(BluetoothManager manager) {
        mBTManager = manager;
    }

    public void transmitEvent(JSONObject json) {
        mBTManager.writeString(json.toString());
    }

    public abstract void shutdown();
}