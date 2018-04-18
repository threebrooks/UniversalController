package com.threbrooks.universalcontroller;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
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
    HeartbeatThread mHeartbeat = null;
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

        mConnectThread = new ConnectThread();
        mConnectThread.start();

        mHeartbeat = new HeartbeatThread();
        mHeartbeat.start();
    }

    public ArrayList<BluetoothDeviceDisplay> getPairedDevices() {
        ArrayList<BluetoothDeviceDisplay> list = new ArrayList<BluetoothDeviceDisplay>();
        for(BluetoothDevice device : mBluetoothAdapter.getBondedDevices()) {
            list.add(new BluetoothDeviceDisplay(device));
        }
        return list;
    }

    public interface BluetoothConnectorInterface {
        public void bluetoothConnected(String version);
        public void bluetoothDisconnected();
    }

    public void writeString(String string) {
        if (mConnectThread != null) mConnectThread.write(string.getBytes());
    }

    public void setBluetoothDevice(BluetoothDevice device) {
        if (mConnectThread != null) mConnectThread.setBluetoothDevice(device);
    }

    private class HeartbeatThread extends Thread {
        public void run() {
            while(true) {
                try {Thread.sleep(1000);} catch (Exception e) {}
                writeString(UInput.createKeyEvent("HEARTBEAT",true).toString());
            }
        }
    }


    private class ConnectThread extends Thread {
        private BluetoothSocket mSocket;
        private BluetoothDevice mDevice;
        private boolean mStopRunning = false;
        private ArrayDeque<byte[]> mQueue = new ArrayDeque<byte[]>();
        private Object mWaitMutex = new Object();

        public ConnectThread() {
            mDevice = null;
        }

        public void setBluetoothDevice(BluetoothDevice device) {
            mDevice = device;
            synchronized (mWaitMutex) {
                mWaitMutex.notifyAll();
            }
        }

        public void run() {
            while (!mStopRunning) {
                mIntf.bluetoothDisconnected();

                try {
                    while(mDevice == null) {
                        synchronized (mWaitMutex) {
                            mWaitMutex.wait();
                        }
                    }
                    // Get a BluetoothSocket to connect with the given BluetoothDevice.
                    // MY_UUID is the app's UUID string, also used in the server code.
                    mSocket = mDevice.createRfcommSocketToServiceRecord(MY_UUID);
                } catch (Exception e) {
                    Log.e(TAG, "Socket's create() method failed", e);
                }

                // Cancel discovery because it otherwise slows down the connection.
                mBluetoothAdapter.cancelDiscovery();

                try {
                    // Connect to the remote device through the socket. This call blocks
                    // until it succeeds or throws an exception.
                    mSocket.connect();
                } catch (IOException connectException) {
                    try {Thread.sleep(1000);} catch (Exception e) {}
                    continue;
                }

                InputStream inStream;
                OutputStream outStream;

                // Get the input and output streams; using temp objects because
                // member streams are final.
                try {
                    inStream = mSocket.getInputStream();
                    Log.d(TAG, "Got input stream");
                } catch (IOException e) {
                    Log.e(TAG, "Error occurred when creating input stream", e);
                    continue;
                }
                try {
                    outStream = mSocket.getOutputStream();
                    Log.d(TAG, "Got output stream");
                } catch (IOException e) {
                    Log.e(TAG, "Error occurred when creating output stream", e);
                    continue;
                }

                // Get the version
                byte[] versionBytes = new byte[1024];
                try {
                    int bytesRead = inStream.read(versionBytes);
                    mIntf.bluetoothConnected(new String(versionBytes, 0, bytesRead, "UTF-8"));
                } catch (IOException e) {
                    Log.e(TAG, "Error occurred when reading version", e);
                    continue;
                }

                synchronized (mQueue) {
                    mQueue.clear();
                }

                while(true) {
                    try {
                        synchronized (mQueue) {
                            mQueue.wait();
                            String totalString = "";
                            while (mQueue.size() > 0) {
                                byte[] bytes = mQueue.removeLast();
                                totalString += new String(bytes, "UTF-8")+"@@@";
                            }
                            byte[] bytes = totalString.getBytes();
                            byte[] header = (""+bytes.length+":").getBytes();
                            outStream.write(header);
                            outStream.write(bytes);
                            Log.d(TAG, "Sent " + new String(bytes));
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
                mSocket.close();
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
