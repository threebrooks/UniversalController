package com.threbrooks.universalcontroller;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Vibrator;
import android.support.v4.widget.ImageViewCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import java.util.ArrayList;

class BitmapControllerImageView extends android.support.v7.widget.AppCompatImageView {

    static String TAG = "BitmapControllerImageView";

    ArrayList<Bitmap> mDisplayBitmaps = new ArrayList<Bitmap>();
    Bitmap mMaskBitmap = null;
    private Matrix mM = null;
    ScaleGestureDetector mScaleDetector = null;
    GestureDetector mGestureDetector = null;
    ImageViewCallback mCallback = null;
    boolean mScaleAndPinch = false;

    Vibrator mVibrator = null;

    interface ImageViewCallback {
        public boolean  onPixelClick(int r, int g, int b, boolean pressed);
    }

    public BitmapControllerImageView(Context context, ImageViewCallback callback, boolean scaleAndPinch) {
        super(context);

        mCallback = callback;
        mScaleAndPinch = scaleAndPinch;

        if (mScaleAndPinch) {
            mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
            mGestureDetector = new GestureDetector(context, mSimpleGestureListener);
        }

        mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void setMaskBitmap(Bitmap bitmap) {
        mMaskBitmap = bitmap;
    }

    public void setLayerBitmap(Bitmap bitmap, int idx) {
        for(int arrrayIdx = mDisplayBitmaps.size(); arrrayIdx <= idx; arrrayIdx++) mDisplayBitmaps.add(null);
        mDisplayBitmaps.set(idx, bitmap);
        invalidate();
    }


    int getPixelsFromMotionEvent(MotionEvent e) {
        try {
            Matrix invMat = new Matrix();
            mM.invert(invMat);
            float[] points = new float[2];
            int pointerIndex = (e.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
            points[0] = e.getX(e.getPointerId(pointerIndex));
            points[1] = e.getY(e.getPointerId(pointerIndex));
            invMat.mapPoints(points);
            if (points[0] >= 0.0f && points[0] < mMaskBitmap.getWidth() && points[1] >= 0.0f && points[1] < mMaskBitmap.getHeight()) {
                return mMaskBitmap.getPixel((int) points[0], (int) points[1]);
            } else {
                return 0;
            }
        } catch (Exception exc) {}
        return 0;
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

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mScaleAndPinch) {
            mScaleDetector.onTouchEvent(ev);
            mGestureDetector.onTouchEvent(ev);
        }
        if ((ev.getActionMasked() == MotionEvent.ACTION_DOWN || ev.getActionMasked() == MotionEvent.ACTION_UP || ev.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN || ev.getActionMasked() == MotionEvent.ACTION_POINTER_UP)) {
            int maskPixel = getPixelsFromMotionEvent(ev);
            int maskPixelR = Color.red(maskPixel);
            int maskPixelG = Color.green(maskPixel);
            int maskPixelB = Color.blue(maskPixel);
            Log.d(TAG, ev.toString());
            if (mCallback.onPixelClick(maskPixelR, maskPixelG, maskPixelB, ev.getActionMasked() == MotionEvent.ACTION_DOWN || ev.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN)) {
                if (ev.getActionMasked() == MotionEvent.ACTION_DOWN || ev.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) mVibrator.vibrate(10);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (mM == null) {
            float scaleFac = Math.min((float)h/mMaskBitmap.getHeight(), (float)w/mMaskBitmap.getWidth());
            mM = new Matrix();
            mM.postTranslate(-mMaskBitmap.getWidth()/2,-mMaskBitmap.getHeight()/2);
            mM.postScale(scaleFac, scaleFac);
            mM.postTranslate(w/2,h/2);
        }
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

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        for (int layerIdx = 0; layerIdx < mDisplayBitmaps.size(); layerIdx++) {
            Bitmap bitmap = mDisplayBitmaps.get(layerIdx);
            if (bitmap != null && mM != null) canvas.drawBitmap(bitmap, mM, null);
        }
    }
}