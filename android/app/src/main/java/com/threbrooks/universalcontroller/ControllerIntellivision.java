package com.threbrooks.universalcontroller;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.threbrooks.universalcontroller.R;

import java.io.File;
import java.util.ArrayList;

public class ControllerIntellivision extends BitmapControllerView {

    static String TAG = "ControllerIntellivision";
    ArrayList<File> mOverlays = new ArrayList<File>();
    Context mCtx = null;
    Bitmap mMaskBitmap = null;

    public ControllerIntellivision(Context context) {
        super(context, false);
        mCtx = context;
        Bitmap maskBitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.controller_intellivision_mask)).getBitmap();
        setMaskBitmap(maskBitmap);
        mMaskBitmap = maskBitmap;
        setLayerBitmap(((BitmapDrawable) getResources().getDrawable(R.drawable.controller_intellivision)).getBitmap(), 0);

        setLayerBitmap(((BitmapDrawable) getResources().getDrawable(R.drawable.controller_intellivision_top_layer)).getBitmap(), 2);

        File directory = new File(Environment.getExternalStorageDirectory().toString()+"/INTV");
        if (directory.exists() && directory.listFiles() != null) {
            for (File file : directory.listFiles()) {
                mOverlays.add(file);
            }
        }

        setOverlayBitmap(mOverlays.get(0).getAbsolutePath());
    }

    private void setOverlayBitmap(String filePath) {
        if (filePath == null) {
            setLayerBitmap(null, 1);
            return;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

        Matrix matrix = new Matrix();
        matrix.postTranslate(-bitmap.getWidth()/2, -bitmap.getHeight()/2);
        matrix.postRotate(-90);
        matrix.postTranslate(bitmap.getHeight()/2, bitmap.getWidth()/2);
        float overlayVisibleNeedstoBe = mMaskBitmap.getHeight()*0.62f;
        float scaleFactor = overlayVisibleNeedstoBe/(0.86f*bitmap.getWidth());
        matrix.postScale(scaleFactor,scaleFactor);
        float bottomVisibleEdgeNeedsToBe = mMaskBitmap.getWidth()*0.589f;
        float leftVisibleEdgeNeedsToBe = mMaskBitmap.getHeight()*0.806f;
        matrix.postTranslate(bottomVisibleEdgeNeedsToBe-scaleFactor*bitmap.getHeight()+0.06f*scaleFactor*bitmap.getWidth(), leftVisibleEdgeNeedsToBe-0.91f*scaleFactor*bitmap.getWidth());

        Bitmap overlayBitmap = Bitmap.createBitmap(mMaskBitmap.getWidth(), mMaskBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlayBitmap);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bitmap, matrix, paint);
        setLayerBitmap(overlayBitmap, 1);

        invalidate();
        forceLayout();
    }

    String mPrevDirKey = "";
    int mCurrOverlayIdx = 0;

    enum ControllerLeftRight {
        Left,
        Right
    }

    ControllerLeftRight mCurrentLR = ControllerLeftRight.Left;

    public boolean onPixelClick(int r, int g, int b, boolean pressed) {
        Log.d(TAG, r+","+g+","+b);
        if (r == 255 & g == 255) {
            String dirKey = "";
            if (pressed && (b == 0 || b == 1)) {
                if (mOverlays.size() == 0) Toast.makeText(mCtx, "No overlays placed in INTV folder", Toast.LENGTH_LONG);
                else {
                    int inc = (b == 0) ? 1 : -1;
                    mCurrOverlayIdx = (mCurrOverlayIdx+mOverlays.size()+1+inc)%(mOverlays.size()+1);
                }
                setOverlayBitmap(mCurrOverlayIdx == 0 ? null : mOverlays.get(mCurrOverlayIdx-1).getAbsolutePath());
            }
            else if (b == 2) { // Toggle left/right controller
            }
            else if (b == 3) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_KP1 : UInput.KEY_1;
            else if (b == 4) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_KP2 : UInput.KEY_2;
            else if (b == 5) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_KP3 : UInput.KEY_3;
            else if (b == 6) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_KP4 : UInput.KEY_4;
            else if (b == 7) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_KP5 : UInput.KEY_5;
            else if (b == 8) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_KP6 : UInput.KEY_6;
            else if (b == 9) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_KP7 : UInput.KEY_7;
            else if (b == 10) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_KP8 : UInput.KEY_8;
            else if (b == 11) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_KP9 : UInput.KEY_9;
            else if (b == 12) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_KP0 : UInput.KEY_MINUS;
            else if (b == 13) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_DOT : UInput.KEY_0;
            else if (b == 14) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_ENTER : UInput.KEY_EQUAL;

            else if (b == 17) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_LEFTSHIFT : UInput.KEY_RIGHTSHIFT;
            else if (b == 18) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_LEFTALT : UInput.KEY_RIGHTALT;

            else if (b == 19) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_I : UInput.KEY_E;
            else if (b == 20) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_O : UInput.KEY_R;
            else if (b == 21) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_K : UInput.KEY_D;
            else if (b == 22) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_COMMA : UInput.KEY_C;
            else if (b == 23) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_M : UInput.KEY_X;
            else if (b == 24) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_N : UInput.KEY_Z;
            else if (b == 25) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_J : UInput.KEY_S;
            else if (b == 26) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_W : UInput.KEY_U;


            if (!mPrevDirKey.equals(dirKey) && !mPrevDirKey.equals("") && pressed) {
                transmitEvent(UInput.createKeyEvent(mPrevDirKey, false));
            }

            transmitEvent(UInput.createKeyEvent(dirKey, pressed));

            mPrevDirKey = dirKey;

            return true;
        }
        return false;
    }

    public void shutdown() {
    }
}
