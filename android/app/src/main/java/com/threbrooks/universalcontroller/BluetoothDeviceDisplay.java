package com.threbrooks.universalcontroller;

import android.bluetooth.BluetoothDevice;

public class BluetoothDeviceDisplay extends DisplayItem {
    BluetoothDevice mDevice = null;
    BluetoothDeviceDisplay(BluetoothDevice device) {
        mDevice = device;
    }

    @Override
    public String toString() {
        return mDevice.getName();
    }
}