package com.threbrooks.bluetoothkeyboard;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

public class ControllerView extends  android.support.v7.widget.AppCompatImageView {

    ScaleGestureDetector mScaleDetector = null;
    GestureDetector mGestureDetector = null;
    private Matrix mM = null;
    Bitmap mCurrentBitmap = null;

    public ControllerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        mGestureDetector = new GestureDetector(context ,mSimpleGestureListener);
    }

    GestureDetector.SimpleOnGestureListener mSimpleGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (e2.getPointerCount() == 2) {
                mM.postTranslate(-distanceX, -distanceY);
                postInvalidate();
            }
            return true;
        }
    };

    public void selectController(String controllerString) {
        Resources res = getResources();
        int resId = res.getIdentifier(controllerString, "drawable", "com.threbrooks.bluetoothkeyboard");
        Drawable drawable = res.getDrawable(resId);
        mCurrentBitmap = ((BitmapDrawable) drawable).getBitmap();

        float scaleFac = Math.min((float)getHeight()/mCurrentBitmap.getHeight(), (float)getWidth()/mCurrentBitmap.getWidth());

        mM = new Matrix();
        mM.postTranslate(-mCurrentBitmap.getWidth()/2,-mCurrentBitmap.getHeight()/2);
        mM.postScale(scaleFac, scaleFac);
        mM.postTranslate(getWidth()/2,getHeight()/2);

        postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mScaleDetector.onTouchEvent(ev);
        mGestureDetector.onTouchEvent(ev);
        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mCurrentBitmap != null) canvas.drawBitmap(mCurrentBitmap, mM, null);
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
