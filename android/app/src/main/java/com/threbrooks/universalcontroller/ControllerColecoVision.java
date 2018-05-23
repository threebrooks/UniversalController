package com.threbrooks.universalcontroller;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
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

public class ControllerColecoVision extends BitmapControllerView {

    static String TAG = "ControllerColecoVision";
    ArrayList<File> mOverlays = new ArrayList<File>();
    Context mCtx = null;
    Bitmap mMaskBitmap = null;
    Bitmap mOverlayBitmap = null;

    public ControllerColecoVision(Context context) {
        super(context, false);
        mCtx = context;
        Bitmap maskBitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.controller_colecovision_mask)).getBitmap();
        setMaskBitmap(maskBitmap);
        mMaskBitmap = maskBitmap;
        setLayerBitmap(((BitmapDrawable) getResources().getDrawable(R.drawable.controller_colecovision)).getBitmap(), 0);

        setLayerBitmap(((BitmapDrawable) getResources().getDrawable(R.drawable.controller_colecovision_top_layer)).getBitmap(), 2);

        mOverlayBitmap = Bitmap.createBitmap(mMaskBitmap.getWidth(), mMaskBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        setLayerBitmap(mOverlayBitmap, 1);

        File directory = new File(Environment.getExternalStorageDirectory().toString()+"/CoolCV");
        if (directory.exists() && directory.listFiles() != null) {
            for (File file : directory.listFiles()) {
                mOverlays.add(file);
            }
        }

        render();
    }

    private void render() {
        mOverlayBitmap.eraseColor(Color.TRANSPARENT);
        renderOverlay();
        renderLeftRight();
        invalidate();
        forceLayout();
    }

    private void renderOverlay() {
        String filePath = mCurrOverlayIdx == 0 ? null : mOverlays.get(mCurrOverlayIdx-1).getAbsolutePath();
        if (filePath == null) {
            return;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

        Matrix matrix = new Matrix();
        matrix.postTranslate(-bitmap.getWidth()/2, -bitmap.getHeight()/2);
        matrix.postRotate(-90);
        matrix.postTranslate(bitmap.getHeight()/2, bitmap.getWidth()/2);
        float overlayVisibleNeedstoBe = mMaskBitmap.getHeight()*0.762f;
        float scaleFactor = overlayVisibleNeedstoBe/(0.879f*bitmap.getWidth());
        matrix.postScale(scaleFactor,scaleFactor);
        float bottomVisibleEdgeNeedsToBe = mMaskBitmap.getWidth()*0.943f;
        float leftVisibleEdgeNeedsToBe = mMaskBitmap.getHeight()*0.81f;
        matrix.postTranslate(bottomVisibleEdgeNeedsToBe-scaleFactor*bitmap.getHeight()+0.06f*scaleFactor*bitmap.getWidth(), leftVisibleEdgeNeedsToBe-0.95f*scaleFactor*bitmap.getWidth());

        Canvas canvas = new Canvas(mOverlayBitmap);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bitmap, matrix, paint);
    }

    private void renderLeftRight() {
        Canvas canvas = new Canvas(mOverlayBitmap);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        Bitmap lrBitmap = ((BitmapDrawable) getResources().getDrawable(mCurrentLR == ControllerLeftRight.Left ? R.drawable.controller_colecovision_l : R.drawable.controller_colecovision_r)).getBitmap();
        canvas.drawBitmap(lrBitmap, 0.395f*mOverlayBitmap.getWidth(),0.649f*mOverlayBitmap.getHeight(), paint);
    }

    int mCurrOverlayIdx = 0;

    enum ControllerLeftRight {
        Left,
        Right
    }

    ControllerLeftRight mCurrentLR = ControllerLeftRight.Left;

    String mPrevDirKey = "";

    public boolean onPixelClick(int r, int g, int b, boolean pressed) {
        Log.d(TAG, r+","+g+","+b);
        if (!mPrevDirKey.equals("") && !pressed) {
            transmitEvent(UInput.createKeyEvent(mPrevDirKey, false));
            mPrevDirKey = "";
            return true;
        }
        if (r == 255 & g == 255) {
            String dirKey = "";
            if (pressed && (b == 0 || b == 1)) {
                if (mOverlays.size() == 0) Toast.makeText(mCtx, "No overlays placed in CoolCV folder", Toast.LENGTH_LONG);
                else {
                    int inc = (b == 0) ? 1 : -1;
                    mCurrOverlayIdx = (mCurrOverlayIdx+mOverlays.size()+1+inc)%(mOverlays.size()+1);
                }
                render();
            }
            else if (pressed && b == 2) { // Toggle left/right controller
                if (mCurrentLR == ControllerLeftRight.Left) mCurrentLR = ControllerLeftRight.Right;
                else mCurrentLR = ControllerLeftRight.Left;
                render();
            }
            else if (b == 3) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_R : UInput.KEY_1;
            else if (b == 4) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_T : UInput.KEY_2;
            else if (b == 5) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_U : UInput.KEY_3;
            else if (b == 6) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_F : UInput.KEY_4;
            else if (b == 7) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_G : UInput.KEY_5;
            else if (b == 8) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_H : UInput.KEY_6;
            else if (b == 9) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_V : UInput.KEY_7;
            else if (b == 10) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_B : UInput.KEY_8;
            else if (b == 11) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_N : UInput.KEY_9;
            else if (b == 12) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_J : UInput.KEY_O;
            else if (b == 13) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_K : UInput.KEY_0;
            else if (b == 14) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_L : UInput.KEY_P;

            else if (b == 17) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_SPACE : UInput.KEY_TAB;
            else if (b == 15) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_M : UInput.KEY_Q;

            else if (b == 19) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_UP : UInput.KEY_W;
            else if (b == 20) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_UP + "|" + UInput.KEY_RIGHT : UInput.KEY_W  + "|" + UInput.KEY_D;
            else if (b == 21) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_RIGHT : UInput.KEY_D;
            else if (b == 22) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_RIGHT + "|" + UInput.KEY_DOWN : UInput.KEY_D  + "|" + UInput.KEY_S;
            else if (b == 23) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_DOWN : UInput.KEY_S;
            else if (b == 24) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_DOWN + "|" + UInput.KEY_LEFT : UInput.KEY_S  + "|" + UInput.KEY_A;
            else if (b == 25) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_LEFT : UInput.KEY_A;
            else if (b == 26) dirKey = mCurrentLR ==  ControllerLeftRight.Left ? UInput.KEY_LEFT + "|" + UInput.KEY_UP : UInput.KEY_A  + "|" + UInput.KEY_W;
            else if (b == 27) dirKey = UInput.KEY_F24;

            if (!dirKey.equals("")) {
                transmitEvent(UInput.createKeyEvent(dirKey, pressed));

                mPrevDirKey = dirKey;
            }

            return true;
        }
        return false;
    }

    public void shutdown() {
    }
}
