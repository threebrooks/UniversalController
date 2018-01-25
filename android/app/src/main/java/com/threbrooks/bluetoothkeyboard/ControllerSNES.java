package com.threbrooks.bluetoothkeyboard;

import android.content.Context;

/**
 * Created by Me on 2018-01-24.
 */

public class ControllerSNES extends BitmapControllerView {

    public ControllerSNES(Context context) {
        super(context, R.drawable.controller_snes, R.drawable.controller_snes_mask);
    }

    public void onPixelClick(int r, int g, int b) {

    }
}
