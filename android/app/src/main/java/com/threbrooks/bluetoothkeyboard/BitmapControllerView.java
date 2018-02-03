package com.threbrooks.bluetoothkeyboard;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public abstract class BitmapControllerView extends ControllerBaseView implements BitmapControllerImageView.ImageViewCallback {

    ImageView mIV = null;

    static String TAG = "BitmapControllerView";

    public BitmapControllerView(Context context, int displayBitmapResId, int maskBitmapResId) {
        super(context);

        mIV = new BitmapControllerImageView(context, displayBitmapResId, maskBitmapResId, this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mIV.setLayoutParams(layoutParams);
        addView(mIV);
    }

    public abstract boolean onPixelClick(int r, int g, int b, boolean pressed);

}
