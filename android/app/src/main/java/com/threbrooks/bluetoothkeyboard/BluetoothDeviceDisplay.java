package com.threbrooks.bluetoothkeyboard;

import android.bluetooth.BluetoothDevice;

public class BluetoothDeviceDisplay {
    BluetoothDevice mDevice = null;
    BluetoothDeviceDisplay(BluetoothDevice device) {
        mDevice = device;
    }

    public String toString() {
        return mDevice.getName();
    }
}