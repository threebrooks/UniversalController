package com.threbrooks.universalcontroller;

import android.content.Context;
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
