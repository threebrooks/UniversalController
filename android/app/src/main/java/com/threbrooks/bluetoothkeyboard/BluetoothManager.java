package com.threbrooks.bluetoothkeyboard;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Me on 2018-01-20.
 */

public class BluetoothManager {

    BluetoothAdapter mBluetoothAdapter = null;
    int REQUEST_ENABLE_BT = 12345;

    String TAG = "BluetoothManager";
    UUID MY_UUID = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee");

    ConnectThread mConnectThread = null;
    BluetoothConnectorInterface mIntf = null;

    public BluetoothManager(Activity mainAct, BluetoothConnectorInterface intf) {
        mIntf = intf;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Log.d(TAG,"No bluetooth supported");
            return;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            mainAct.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                if (deviceName.equals("keyboardpi")) {
                    Log.d(TAG, "device: " + deviceName);
                    mConnectThread = new ConnectThread(device);
                    mConnectThread.start();
                }
            }
        }
    }

    public interface BluetoothConnectorInterface {
        public void bluetoothConnected();
        public void bluetoothDisconnected();
    }

    public void writeString(String string) {
        if (mConnectThread != null) mConnectThread.write(string.getBytes());
    }


    private class ConnectThread extends Thread {
        private BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private boolean mStopRunning = false;
        private ArrayDeque<byte[]> mQueue = new ArrayDeque<byte[]>();

        public ConnectThread(BluetoothDevice device) {
            mmDevice = device;
        }

        public void run() {
            while (!mStopRunning) {
                mIntf.bluetoothDisconnected();
                try {
                    // Get a BluetoothSocket to connect with the given BluetoothDevice.
                    // MY_UUID is the app's UUID string, also used in the server code.
                    mmSocket = mmDevice.createRfcommSocketToServiceRecord(MY_UUID);
                } catch (IOException e) {
                    Log.e(TAG, "Socket's create() method failed", e);
                }

                // Cancel discovery because it otherwise slows down the connection.
                mBluetoothAdapter.cancelDiscovery();

                try {
                    // Connect to the remote device through the socket. This call blocks
                    // until it succeeds or throws an exception.
                    mmSocket.connect();
                } catch (IOException connectException) {
                    // Unable to connect; close the socket and return.
                    Log.e(TAG, "Unable to connect", connectException);
                    try {
                        mmSocket.close();
                    } catch (IOException closeException) {
                        Log.e(TAG, "Could not close the client socket", closeException);
                    }
                    continue;
                }

                InputStream inStream;
                OutputStream outStream;

                // Get the input and output streams; using temp objects because
                // member streams are final.
                try {
                    inStream = mmSocket.getInputStream();
                    Log.d(TAG, "Got input stream");
                } catch (IOException e) {
                    Log.e(TAG, "Error occurred when creating input stream", e);
                    continue;
                }
                try {
                    outStream = mmSocket.getOutputStream();
                    Log.d(TAG, "Got output stream");
                } catch (IOException e) {
                    Log.e(TAG, "Error occurred when creating output stream", e);
                    continue;
                }

                mIntf.bluetoothConnected();

                while(true) {
                    try {
                        synchronized (mQueue) {
                            mQueue.wait();
                            while (mQueue.size() > 0) {
                                byte[] bytes = mQueue.removeLast();
                                outStream.write(bytes);
                                Log.d(TAG, "Sent " + new String(bytes));
                            }
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error occurred when sending data", e);
                        break;
                    }
                }
            }
        }

        // Closes the client socket and causes the thread to finish.
        public void cancel() {
            try {
                mStopRunning = true;
                synchronized (mQueue) {
                    mQueue.clear();
                }
                mmSocket.close();
                join();
            } catch (Exception e) {
                Log.e(TAG, "Could not close the client socket", e);
            }
        }

        // Call this from the main activity to send data to the remote device.
        public void write(byte[] bytes) {
            synchronized (mQueue) {
                mQueue.addFirst(bytes);
                mQueue.notifyAll();
            }
        }
    }
}
