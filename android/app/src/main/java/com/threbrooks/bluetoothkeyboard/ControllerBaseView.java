package com.threbrooks.bluetoothkeyboard;

import android.content.Context;

import org.json.JSONObject;

public abstract class ControllerBaseView extends android.support.v7.widget.AppCompatImageView {
    private BluetoothManager mBTManager = null;

    public ControllerBaseView(Context context) {
        super(context);
    }

    public void setBluetoothManager(BluetoothManager manager) {
        mBTManager = manager;
    }

    public void transmitEvent(JSONObject json) {
        mBTManager.writeString(json.toString());
    }
}