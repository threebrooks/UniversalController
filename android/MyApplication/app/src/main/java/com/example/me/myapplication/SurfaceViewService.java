package com.example.me.myapplication;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.WindowManager;

/**
 * Created by Me on 2017-03-12.
 */

public class SurfaceViewService extends Service {
    private WindowManager windowManager;
    final WindowManager.LayoutParams myParams = new WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT);
    private static SurfaceView mSurfaceView = null;

    @Override
    public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
        myParams.gravity = Gravity.TOP | Gravity.CENTER;
        myParams.height=1;
        myParams.width=1;
        myParams.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        mSurfaceView = new SurfaceView(this);
        windowManager.addView(mSurfaceView, myParams);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("gg")
                .setTicker("gg")
                .setContentText("gg is running")
                .setWhen(System.currentTimeMillis());

        Notification cur_notification = builder.build();

        startForeground(1, cur_notification);
    }

    public static SurfaceView getSurfaceView(){
        if(mSurfaceView==null) {
            Log.d("SurfaceViewService", "surfaceview service not started");
        }
        return mSurfaceView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        windowManager.removeView(mSurfaceView);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

}