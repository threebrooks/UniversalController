package com.threbrooks.universalcontroller;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.threbrooks.universalcontroller.R;

import java.lang.reflect.Field;

public class ControllerAndroidKeyboard extends ControllerBaseView {

    static String TAG = "ControllerAndroidKeyboard";
    LinearLayout mLayout = null;
    InputMethodManager mIm = null;

    public ControllerAndroidKeyboard(Context context, ViewGroup parent) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayout = (LinearLayout)inflater.inflate(R.layout.controller_android_keyboard, null, true);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mLayout.setLayoutParams(layoutParams);
        addView(mLayout);

        //mEditText = (EditText)findViewById(R.id.controller_android_keyboard);

        setFocusable(true);
        setFocusableInTouchMode(true);

        mIm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        mIm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        requestFocus();
    }

    String getKeyString(int keyCode) throws Exception {
        String keyString = KeyEvent.keyCodeToString(keyCode);
        String uinputString = keyString.replace("KEYCODE","KEY");
        Field field = UInput.class.getField(uinputString);
        String uinputFieldString = (String)field.get(null);
        return uinputFieldString;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        try {
            transmitEvent(UInput.createKeyEvent(getKeyString(keyCode), false));
        } catch (Exception e) {
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        try {
            transmitEvent(UInput.createKeyEvent(getKeyString(keyCode), true));
        } catch (Exception e) {
            Log.d(TAG,e.getMessage());
        }
        return true;
    }

    public void shutdown() {
        mIm.hideSoftInputFromWindow(getWindowToken(), 0);
    }
}