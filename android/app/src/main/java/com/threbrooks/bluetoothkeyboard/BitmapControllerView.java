package com.threbrooks.bluetoothkeyboard;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

public abstract class BitmapControllerView extends android.support.v7.widget.AppCompatImageView {

    ScaleGestureDetector mScaleDetector = null;
    GestureDetector mGestureDetector = null;
    private Matrix mM = null;
    Bitmap mDisplayBitmap = null;
    Bitmap mMaskBitmap = null;
    static String TAG = "BitmapControllerView";

    public BitmapControllerView(Context context, int displayBitmapResId, int maskBitmapResId) {
        super(context);

        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        mGestureDetector = new GestureDetector(context ,mSimpleGestureListener);

        Resources res = getResources();
        mDisplayBitmap = ((BitmapDrawable) res.getDrawable(displayBitmapResId)).getBitmap();
        mMaskBitmap = ((BitmapDrawable) res.getDrawable(maskBitmapResId)).getBitmap();
    }

    int getPixelsFromMotionEvent(MotionEvent e) {
        Matrix invMat = new Matrix();
        mM.invert(invMat);
        float[] points = new float[2];
        points[0] = e.getX();
        points[1] = e.getY();
        invMat.mapPoints(points);
        return mMaskBitmap.getPixel((int)points[0],(int)points[1]);
    }


    GestureDetector.SimpleOnGestureListener mSimpleGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (e2.getPointerCount() == 2) {
                mM.postTranslate(-distanceX, -distanceY);
                postInvalidate();
                return false;
            }
            return false;
        }
    };

    public abstract void onPixelClick(int r, int g, int b, int action);

    private BluetoothManager mBTManager = null;

    public void setBluetoothManager(BluetoothManager manager) {
        mBTManager = manager;
    }

    public boolean transmitKey(int keyCode, String action) {
        return mBTManager.writeString(KeyEvent.keyCodeToString(keyCode)+","+action);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mScaleDetector.onTouchEvent(ev);
        mGestureDetector.onTouchEvent(ev);
        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN || ev.getActionMasked() == MotionEvent.ACTION_UP) {
            int maskPixel = getPixelsFromMotionEvent(ev);
            int maskPixelR = Color.red(maskPixel);
            int maskPixelG = Color.green(maskPixel);
            int maskPixelB = Color.blue(maskPixel);
            Log.d(TAG, "action: " + ev.getAction());
            onPixelClick(maskPixelR, maskPixelG, maskPixelB, ev.getActionMasked());
            return true;
        }
        return false;
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (mM == null) {
            float scaleFac = Math.min((float)h/mDisplayBitmap.getHeight(), (float)w/mDisplayBitmap.getWidth());
            mM = new Matrix();
            mM.postTranslate(-mDisplayBitmap.getWidth()/2,-mDisplayBitmap.getHeight()/2);
            mM.postScale(scaleFac, scaleFac);
            mM.postTranslate(w/2,h/2);
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mDisplayBitmap != null && mM != null) canvas.drawBitmap(mDisplayBitmap, mM, null);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mM.postTranslate(-detector.getFocusX(),-detector.getFocusY());
            mM.postScale(detector.getScaleFactor(), detector.getScaleFactor());
            mM.postTranslate(detector.getFocusX(),detector.getFocusY());

            invalidate();
            return true;
        }
    }
}
