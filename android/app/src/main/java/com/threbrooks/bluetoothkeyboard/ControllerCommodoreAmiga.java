package com.threbrooks.bluetoothkeyboard;

import android.content.Context;

public class ControllerCommodoreAmiga extends BitmapControllerView {

    public ControllerCommodoreAmiga(Context context) {
        super(context, R.drawable.controller_amiga, R.drawable.controller_amiga_mask);
    }

    public boolean onPixelClick(int r, int g, int b, int action) {
        return false;
    }
}
