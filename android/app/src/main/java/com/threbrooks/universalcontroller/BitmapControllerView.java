package com.threbrooks.universalcontroller;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import android.widget.LinearLayout;

public abstract class BitmapControllerView extends ControllerBaseView implements BitmapControllerImageView.ImageViewCallback {

    BitmapControllerImageView mIV = null;

    static String TAG = "BitmapControllerView";

    public BitmapControllerView(Context context, boolean scaleAndPinch) {
        super(context);

        mIV = new BitmapControllerImageView(context, this, scaleAndPinch);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mIV.setLayoutParams(layoutParams);
        addView(mIV);
    }

    public void setMaskBitmap(Bitmap bitmap) {
        mIV.setMaskBitmap(bitmap);
    }

    public void setLayerBitmap(Bitmap bitmap, int idx) {
        mIV.setLayerBitmap(bitmap, idx);
    }

    public abstract boolean onPixelClick(int r, int g, int b, boolean pressed);
}
