package com.threbrooks.bluetoothkeyboard;

import android.content.Context;

/**
 * Created by Me on 2018-01-24.
 */

public class ControllerCommodoreAmiga extends BitmapControllerView {

    public ControllerCommodoreAmiga(Context context) {
        super(context, R.drawable.controller_amiga, R.drawable.controller_amiga_mask);
    }

    public void onPixelClick(int r, int g, int b) {

    }
}
