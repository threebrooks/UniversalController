package com.threbrooks.bluetoothkeyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.ScaleGestureDetector;

/**
 * Created by Me on 2018-01-24.
 */

public class ControllerCommodore64 extends BitmapControllerView {

    public ControllerCommodore64(Context context) {
        super(context, R.drawable.controller_c64, R.drawable.controller_c64_mask);
    }

    public void onPixelClick(int r, int g, int b) {

    }
}
