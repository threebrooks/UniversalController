package com.threbrooks.bluetoothkeyboard;

import android.content.Context;
import android.util.Log;

public class ControllerCommodoreAmiga extends BitmapControllerView {

    public ControllerCommodoreAmiga(Context context) {
        super(context, R.drawable.controller_amiga, R.drawable.controller_amiga_mask);
    }

    enum AmigaKey {
        KEY_ARROW_LEFT,
        KEY_1,
        KEY_EXCLAMATION_MARK,
        KEY_COMBO_C64_1,

        KEY_2,
        KEY_QUOTES,
        KEY_COMBO_C64_2,

        KEY_3,
        KEY_HASH,
        KEY_COMBO_C64_3,

        KEY_4,
        KEY_DOLLAR,
        KEY_COMBO_C64_4,

        KEY_5,
        KEY_PERCENT,
        KEY_COMBO_C64_5,

        KEY_6,
        KEY_AMPERSAND,
        KEY_COMBO_C64_6,

        KEY_7,
        KEY_SINGLE_QUOTE,
        KEY_COMBO_C64_7,

        KEY_8,
        KEY_OPEN_PAREN,
        KEY_COMBO_C64_8,

        KEY_9,
        KEY_CLOSE_PAREN,
        KEY_COMBO_C64_9,

        KEY_0,
        KEY_PLUS,
        KEY_MINUS,
        KEY_POUND,
        KEY_HOME,
        KEY_CLR,
        KEY_DEL,
        KEY_INST,
        KEY_CTRL,
        KEY_Q,
        KEY_W,
        KEY_E,
        KEY_R,
        KEY_T,
        KEY_Y,
        KEY_U,
        KEY_I,
        KEY_O,
        KEY_P,
        KEY_AT,
        KEY_STAR,
        KEY_ARROW_UP,
        KEY_RESTORE,
        KEY_STOP,
        KEY_RUN,
        KEY_SHIFT_LOCK,
        KEY_A,
        KEY_S,
        KEY_D,
        KEY_F,
        KEY_G,
        KEY_H,
        KEY_J,
        KEY_K,
        KEY_L,
        KEY_COLON,
        KEY_OPEN_SQUARED_BRACKET,
        KEY_SEMICOLON,
        KEY_CLOSE_SQUARED_BRACKET,
        KEY_EQUALS,
        KEY_RETURN,
        KEY_COMMODORE,
        KEY_SHIFT_COMMODORE,
        KEY_LEFTSHIFT,
        KEY_Z,
        KEY_X,
        KEY_C,
        KEY_V,
        KEY_B,
        KEY_N,
        KEY_M,
        KEY_COMMA,
        KEY_OPEN_ANGLED_BRACKET,
        KEY_DOT,
        KEY_CLOSE_ANGLED_BRACKET,
        KEY_SLASH,
        KEY_QUESTION_MARK,
        KEY_RIGHTSHIFT,
        KEY_CURSOR_DOWN,
        KEY_CURSOR_UP,
        KEY_CURSOR_RIGHT,
        KEY_CURSOR_LEFT,
        KEY_SPACE,
        KEY_F1,
        KEY_F2,
        KEY_F3,
        KEY_F4,
        KEY_F5,
        KEY_F6,
        KEY_F7,
        KEY_F8,
        KEY_VICE_EXIT,
        NONE
    }

    AmigaKey blueToAmigaKey(int b, ControllerCommodore64.Modifier mod) {
        if (b == 65) return AmigaKey.KEY_ARROW_LEFT; // Left arrow
        else if (b == 0) {
            if (mod == ControllerCommodore64.Modifier.Normal) return AmigaKey.KEY_1;
            else if (mod == ControllerCommodore64.Modifier.Shift) return AmigaKey.KEY_EXCLAMATION_MARK;
            else if (mod == ControllerCommodore64.Modifier.Commodore) return AmigaKey.KEY_COMBO_C64_1;
        }
        else if (b == 1) {
            if (mod == ControllerCommodore64.Modifier.Normal) return AmigaKey.KEY_2;
            else if (mod == ControllerCommodore64.Modifier.Shift) return AmigaKey.KEY_QUOTES;
            else if (mod == ControllerCommodore64.Modifier.Commodore) return AmigaKey.KEY_COMBO_C64_2;
        }
        else if (b == 2) {
            if (mod == ControllerCommodore64.Modifier.Normal) return AmigaKey.KEY_3;
            else if (mod == ControllerCommodore64.Modifier.Shift) return AmigaKey.KEY_HASH;
            else if (mod == ControllerCommodore64.Modifier.Commodore) return AmigaKey.KEY_COMBO_C64_3;
        }
        else if (b == 3) {
            if (mod == ControllerCommodore64.Modifier.Normal) return AmigaKey.KEY_4;
            else if (mod == ControllerCommodore64.Modifier.Shift) return AmigaKey.KEY_DOLLAR;
            else if (mod == ControllerCommodore64.Modifier.Commodore) return AmigaKey.KEY_COMBO_C64_4;
        }
        else if (b == 4) {
            if (mod == ControllerCommodore64.Modifier.Normal) return AmigaKey.KEY_5;
            else if (mod == ControllerCommodore64.Modifier.Shift) return AmigaKey.KEY_PERCENT;
            else if (mod == ControllerCommodore64.Modifier.Commodore) return AmigaKey.KEY_COMBO_C64_5;
        }
        else if (b == 5) {
            if (mod == ControllerCommodore64.Modifier.Normal) return AmigaKey.KEY_6;
            else if (mod == ControllerCommodore64.Modifier.Shift) return AmigaKey.KEY_AMPERSAND;
            else if (mod == ControllerCommodore64.Modifier.Commodore) return AmigaKey.KEY_COMBO_C64_6;
        }
        else if (b == 6) {
            if (mod == ControllerCommodore64.Modifier.Normal) return AmigaKey.KEY_7;
            else if (mod == ControllerCommodore64.Modifier.Shift) return AmigaKey.KEY_SINGLE_QUOTE;
            else if (mod == ControllerCommodore64.Modifier.Commodore) return AmigaKey.KEY_COMBO_C64_7;
        }
        else if (b == 7) {
            if (mod == ControllerCommodore64.Modifier.Normal) return AmigaKey.KEY_8;
            else if (mod == ControllerCommodore64.Modifier.Shift) return AmigaKey.KEY_OPEN_PAREN;
            else if (mod == ControllerCommodore64.Modifier.Commodore) return AmigaKey.KEY_COMBO_C64_8;
        }
        else if (b == 8) {
            if (mod == ControllerCommodore64.Modifier.Normal) return AmigaKey.KEY_9;
            else if (mod == ControllerCommodore64.Modifier.Shift) return AmigaKey.KEY_CLOSE_PAREN;
            else if (mod == ControllerCommodore64.Modifier.Commodore) return AmigaKey.KEY_COMBO_C64_9;
        }
        else if (b == 9) return AmigaKey.KEY_0;
        else if (b == 10) return AmigaKey.KEY_PLUS;
        else if (b == 11) return AmigaKey.KEY_MINUS;
        else if (b == 12) return AmigaKey.KEY_POUND;
        else if (b == 13) {
            if (mod == ControllerCommodore64.Modifier.Normal) return AmigaKey.KEY_HOME;
            else if (mod == ControllerCommodore64.Modifier.Shift) return AmigaKey.KEY_CLR;
        }
        else if (b == 14) {
            if (mod == ControllerCommodore64.Modifier.Normal) return AmigaKey.KEY_DEL;
            else if (mod == ControllerCommodore64.Modifier.Shift) return AmigaKey.KEY_INST;
        }
        else if (b == 15) return AmigaKey.KEY_CTRL;
        else if (b == 16) return AmigaKey.KEY_Q;
        else if (b == 17) return AmigaKey.KEY_W;
        else if (b == 18) return AmigaKey.KEY_E;
        else if (b == 19) return AmigaKey.KEY_R;
        else if (b == 20) return AmigaKey.KEY_T;
        else if (b == 21) return AmigaKey.KEY_Y;
        else if (b == 22) return AmigaKey.KEY_U;
        else if (b == 23) return AmigaKey.KEY_I;
        else if (b == 24) return AmigaKey.KEY_O;
        else if (b == 25) return AmigaKey.KEY_P;
        else if (b == 26) return AmigaKey.KEY_AT;
        else if (b == 27) return AmigaKey.KEY_STAR;
        else if (b == 28) return AmigaKey.KEY_ARROW_UP;
        else if (b == 29) return AmigaKey.KEY_RESTORE;
        else if (b == 30) {
            if (mod == ControllerCommodore64.Modifier.Normal) return AmigaKey.KEY_STOP;
            else if (mod == ControllerCommodore64.Modifier.Shift) return AmigaKey.KEY_RUN;
        }
        else if (b == 66) return AmigaKey.KEY_SHIFT_LOCK;
        else if (b == 31) return AmigaKey.KEY_A;
        else if (b == 32) return AmigaKey.KEY_S;
        else if (b == 33) return AmigaKey.KEY_D;
        else if (b == 34) return AmigaKey.KEY_F;
        else if (b == 35) return AmigaKey.KEY_G;
        else if (b == 36) return AmigaKey.KEY_H;
        else if (b == 37) return AmigaKey.KEY_J;
        else if (b == 38) return AmigaKey.KEY_K;
        else if (b == 39) return AmigaKey.KEY_L;
        else if (b == 40) {
            if (mod == ControllerCommodore64.Modifier.Normal) return AmigaKey.KEY_COLON;
            else if (mod == ControllerCommodore64.Modifier.Shift) return AmigaKey.KEY_OPEN_SQUARED_BRACKET;
        }
        else if (b == 41) {
            if (mod == ControllerCommodore64.Modifier.Normal) return AmigaKey.KEY_SEMICOLON;
            else if (mod == ControllerCommodore64.Modifier.Shift) return AmigaKey.KEY_CLOSE_SQUARED_BRACKET;
        }
        else if (b == 42) return AmigaKey.KEY_EQUALS;
        else if (b == 43) return AmigaKey.KEY_RETURN;
        else if (b == 44) {
            if (mod == ControllerCommodore64.Modifier.Shift) return AmigaKey.KEY_SHIFT_COMMODORE;
            else return AmigaKey.KEY_COMMODORE;
        }
        else if (b == 45) {
            if (mod == ControllerCommodore64.Modifier.Commodore) return AmigaKey.KEY_SHIFT_COMMODORE;
            else return AmigaKey.KEY_LEFTSHIFT;
        }
        else if (b == 46) return AmigaKey.KEY_Z;
        else if (b == 47) return AmigaKey.KEY_X;
        else if (b == 48) return AmigaKey.KEY_C;
        else if (b == 49) return AmigaKey.KEY_V;
        else if (b == 50) return AmigaKey.KEY_B;
        else if (b == 51) return AmigaKey.KEY_N;
        else if (b == 52) return AmigaKey.KEY_M;
        else if (b == 53) {
            if (mod == ControllerCommodore64.Modifier.Normal) return AmigaKey.KEY_COMMA;
            else if (mod == ControllerCommodore64.Modifier.Shift) return AmigaKey.KEY_OPEN_ANGLED_BRACKET;
        }
        else if (b == 54) {
            if (mod == ControllerCommodore64.Modifier.Normal) return AmigaKey.KEY_DOT;
            else if (mod == ControllerCommodore64.Modifier.Shift) return AmigaKey.KEY_CLOSE_ANGLED_BRACKET;
        }
        else if (b == 55) {
            if (mod == ControllerCommodore64.Modifier.Normal) return AmigaKey.KEY_SLASH;
            else if (mod == ControllerCommodore64.Modifier.Shift) return AmigaKey.KEY_QUESTION_MARK;
        }
        else if (b == 56) {
            if (mod == ControllerCommodore64.Modifier.Commodore) return AmigaKey.KEY_SHIFT_COMMODORE;
            else return AmigaKey.KEY_RIGHTSHIFT;
        }
        else if (b == 57) {
            if (mod == ControllerCommodore64.Modifier.Normal) return AmigaKey.KEY_CURSOR_DOWN;
            else if (mod == ControllerCommodore64.Modifier.Shift) return AmigaKey.KEY_CURSOR_UP;
        }
        else if (b == 58) {
            if (mod == ControllerCommodore64.Modifier.Normal) return AmigaKey.KEY_CURSOR_RIGHT;
            else if (mod == ControllerCommodore64.Modifier.Shift) return AmigaKey.KEY_CURSOR_LEFT;
        }
        else if (b == 59) return AmigaKey.KEY_SPACE;
        else if (b == 60) {
            if (mod == ControllerCommodore64.Modifier.Normal) return AmigaKey.KEY_F1;
            else if (mod == ControllerCommodore64.Modifier.Shift) return AmigaKey.KEY_F2;
        }
        else if (b == 61) {
            if (mod == ControllerCommodore64.Modifier.Normal) return AmigaKey.KEY_F3;
            else if (mod == ControllerCommodore64.Modifier.Shift) return AmigaKey.KEY_F4;
        }
        else if (b == 63) {
            if (mod == ControllerCommodore64.Modifier.Normal) return AmigaKey.KEY_F5;
            else if (mod == ControllerCommodore64.Modifier.Shift) return AmigaKey.KEY_F6;
        }
        else if (b == 62) {
            if (mod == ControllerCommodore64.Modifier.Normal) return AmigaKey.KEY_F7;
            else if (mod == ControllerCommodore64.Modifier.Shift) return AmigaKey.KEY_F8;
        }
        else if (b == 100) return AmigaKey.KEY_VICE_EXIT;
        return AmigaKey.NONE;
    }

    String AmigaKeyToViceCombo(AmigaKey key) {
        if (key == AmigaKey.KEY_ARROW_LEFT) return UInput.KEY_GRAVE;
        else if (key == AmigaKey.KEY_1) return UInput.KEY_1;
        else if (key == AmigaKey.KEY_EXCLAMATION_MARK) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_1;
        else if (key == AmigaKey.KEY_COMBO_C64_1) return UInput.KEY_TAB + "|" + UInput.KEY_1;

        else if (key == AmigaKey.KEY_2) return UInput.KEY_2;
        else if (key == AmigaKey.KEY_QUOTES) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_APOSTROPHE;
        else if (key == AmigaKey.KEY_COMBO_C64_2) return UInput.KEY_TAB + "|" + UInput.KEY_2;

        else if (key == AmigaKey.KEY_3) return UInput.KEY_3;
        else if (key == AmigaKey.KEY_HASH) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_3;
        else if (key == AmigaKey.KEY_COMBO_C64_3) return UInput.KEY_TAB + "|" + UInput.KEY_3;

        else if (key == AmigaKey.KEY_4) return UInput.KEY_4;
        else if (key == AmigaKey.KEY_DOLLAR) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_4;
        else if (key == AmigaKey.KEY_COMBO_C64_4) return UInput.KEY_TAB + "|" + UInput.KEY_4;

        else if (key == AmigaKey.KEY_5) return UInput.KEY_5;
        else if (key == AmigaKey.KEY_PERCENT) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_5;
        else if (key == AmigaKey.KEY_COMBO_C64_5) return UInput.KEY_TAB + "|" + UInput.KEY_5;

        else if (key == AmigaKey.KEY_6) return UInput.KEY_6;
        else if (key == AmigaKey.KEY_AMPERSAND) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_7;
        else if (key == AmigaKey.KEY_COMBO_C64_6) return UInput.KEY_TAB + "|" + UInput.KEY_6;

        else if (key == AmigaKey.KEY_7) return UInput.KEY_7;
        else if (key == AmigaKey.KEY_SINGLE_QUOTE) return UInput.KEY_APOSTROPHE;
        else if (key == AmigaKey.KEY_COMBO_C64_7) return UInput.KEY_TAB + "|" + UInput.KEY_7;

        else if (key == AmigaKey.KEY_8) return UInput.KEY_8;
        else if (key == AmigaKey.KEY_OPEN_PAREN) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_8;
        else if (key == AmigaKey.KEY_COMBO_C64_8) return UInput.KEY_TAB + "|" + UInput.KEY_8;

        else if (key == AmigaKey.KEY_9) return UInput.KEY_9;
        else if (key == AmigaKey.KEY_CLOSE_PAREN) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_0;
        else if (key == AmigaKey.KEY_COMBO_C64_9) return UInput.KEY_TAB + "|" + UInput.KEY_9;

        else if (key == AmigaKey.KEY_0) return UInput.KEY_0;

        else if (key == AmigaKey.KEY_PLUS) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_EQUAL;
        else if (key == AmigaKey.KEY_MINUS) return UInput.KEY_MINUS;
        else if (key == AmigaKey.KEY_POUND) return UInput.KEY_NUMERIC_POUND;
        else if (key == AmigaKey.KEY_HOME) return UInput.KEY_HOME;
        else if (key == AmigaKey.KEY_CLR) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_HOME;
        else if (key == AmigaKey.KEY_DEL) return UInput.KEY_BACKSPACE;
        else if (key == AmigaKey.KEY_INST) return UInput.KEY_INSERT;
        else if (key == AmigaKey.KEY_CTRL) return UInput.KEY_TAB;
        else if (key == AmigaKey.KEY_Q) return UInput.KEY_Q;
        else if (key == AmigaKey.KEY_W) return UInput.KEY_W;
        else if (key == AmigaKey.KEY_E) return UInput.KEY_E;
        else if (key == AmigaKey.KEY_R) return UInput.KEY_R;
        else if (key == AmigaKey.KEY_T) return UInput.KEY_T;
        else if (key == AmigaKey.KEY_Y) return UInput.KEY_Y;
        else if (key == AmigaKey.KEY_U) return UInput.KEY_U;
        else if (key == AmigaKey.KEY_I) return UInput.KEY_I;
        else if (key == AmigaKey.KEY_O) return UInput.KEY_O;
        else if (key == AmigaKey.KEY_P) return UInput.KEY_P;
        else if (key == AmigaKey.KEY_AT) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_2;
        else if (key == AmigaKey.KEY_STAR) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_8;
        else if (key == AmigaKey.KEY_ARROW_UP) return UInput.KEY_DELETE;
        else if (key == AmigaKey.KEY_RESTORE) return UInput.KEY_PAGEUP;
        else if (key == AmigaKey.KEY_STOP) return UInput.KEY_ESC;
        else if (key == AmigaKey.KEY_RUN) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_ESC;
        else if (key == AmigaKey.KEY_SHIFT_LOCK) return "";
        else if (key == AmigaKey.KEY_A) return UInput.KEY_A;
        else if (key == AmigaKey.KEY_S) return UInput.KEY_S;
        else if (key == AmigaKey.KEY_D) return UInput.KEY_D;
        else if (key == AmigaKey.KEY_F) return UInput.KEY_F;
        else if (key == AmigaKey.KEY_G) return UInput.KEY_G;
        else if (key == AmigaKey.KEY_H) return UInput.KEY_H;
        else if (key == AmigaKey.KEY_J) return UInput.KEY_J;
        else if (key == AmigaKey.KEY_K) return UInput.KEY_K;
        else if (key == AmigaKey.KEY_L) return UInput.KEY_L;
        else if (key == AmigaKey.KEY_COLON) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_SEMICOLON;
        else if (key == AmigaKey.KEY_OPEN_SQUARED_BRACKET) return UInput.KEY_LEFTBRACE;
        else if (key == AmigaKey.KEY_SEMICOLON) return UInput.KEY_SEMICOLON;
        else if (key == AmigaKey.KEY_CLOSE_SQUARED_BRACKET) return UInput.KEY_RIGHTBRACE;
        else if (key == AmigaKey.KEY_EQUALS) return UInput.KEY_EQUAL;
        else if (key == AmigaKey.KEY_RETURN) return UInput.KEY_ENTER;
        else if (key == AmigaKey.KEY_COMMODORE) return UInput.KEY_TAB;
        else if (key == AmigaKey.KEY_SHIFT_COMMODORE) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_TAB;
        else if (key == AmigaKey.KEY_LEFTSHIFT) return UInput.KEY_LEFTSHIFT;
        else if (key == AmigaKey.KEY_Z) return UInput.KEY_Z;
        else if (key == AmigaKey.KEY_X) return UInput.KEY_X;
        else if (key == AmigaKey.KEY_C) return UInput.KEY_C;
        else if (key == AmigaKey.KEY_V) return UInput.KEY_V;
        else if (key == AmigaKey.KEY_B) return UInput.KEY_B;
        else if (key == AmigaKey.KEY_N) return UInput.KEY_N;
        else if (key == AmigaKey.KEY_M) return UInput.KEY_M;
        else if (key == AmigaKey.KEY_COMMA) return UInput.KEY_COMMA;
        else if (key == AmigaKey.KEY_OPEN_ANGLED_BRACKET) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_COMMA;
        else if (key == AmigaKey.KEY_DOT) return UInput.KEY_DOT;
        else if (key == AmigaKey.KEY_CLOSE_ANGLED_BRACKET) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_DOT;
        else if (key == AmigaKey.KEY_SLASH) return UInput.KEY_SLASH;
        else if (key == AmigaKey.KEY_QUESTION_MARK) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_SLASH;
        else if (key == AmigaKey.KEY_RIGHTSHIFT) return UInput.KEY_RIGHTSHIFT;
        else if (key == AmigaKey.KEY_CURSOR_DOWN) return UInput.KEY_DOWN;
        else if (key == AmigaKey.KEY_CURSOR_UP) return UInput.KEY_UP;
        else if (key == AmigaKey.KEY_CURSOR_RIGHT) return UInput.KEY_RIGHT;
        else if (key == AmigaKey.KEY_CURSOR_LEFT) return UInput.KEY_LEFT;
        else if (key == AmigaKey.KEY_SPACE) return UInput.KEY_SPACE;
        else if (key == AmigaKey.KEY_F1) return UInput.KEY_F1;
        else if (key == AmigaKey.KEY_F2) return UInput.KEY_F2;
        else if (key == AmigaKey.KEY_F3) return UInput.KEY_F3;
        else if (key == AmigaKey.KEY_F4) return UInput.KEY_F4;
        else if (key == AmigaKey.KEY_F5) return UInput.KEY_F5;
        else if (key == AmigaKey.KEY_F6) return UInput.KEY_F6;
        else if (key == AmigaKey.KEY_F7) return UInput.KEY_F7;
        else if (key == AmigaKey.KEY_F8) return UInput.KEY_F8;
        else if (key == AmigaKey.KEY_VICE_EXIT) return UInput.KEY_F12;
        else return "";
    }

    enum Modifier {
        Normal,
        Shift,
        Commodore
    };

    private ControllerCommodore64.Modifier mMod = ControllerCommodore64.Modifier.Normal;

    public boolean onPixelClick(int r, int g, int b, boolean pressed) {
        if (r == 247 & g == 247) {
            Log.d(TAG, "Mode before: "+mMod.toString());
            AmigaKey AmigaKey = blueToAmigaKey(b, mMod);
            boolean isFunctionKey = AmigaKey.equals(AmigaKey.KEY_LEFTSHIFT) || AmigaKey.equals(AmigaKey.KEY_RIGHTSHIFT) || AmigaKey.equals(AmigaKey.KEY_COMMODORE);
            Log.d(TAG, "isFunctionKey: "+Boolean.toString(isFunctionKey));
            String keyString = AmigaKeyToViceCombo(AmigaKey);
            Log.d(TAG, "keyString: "+keyString);
            if (!isFunctionKey && !keyString.equals("")) transmitKey(keyString, pressed ? "KEY_DOWN" : "KEY_UP");

            if (mMod == ControllerCommodore64.Modifier.Normal) {
                if (AmigaKey.equals(AmigaKey.KEY_LEFTSHIFT) || AmigaKey.equals(AmigaKey.KEY_RIGHTSHIFT)) {
                    mMod = ControllerCommodore64.Modifier.Shift;
                } else if (AmigaKey.equals(AmigaKey.KEY_COMMODORE)) {
                    mMod = ControllerCommodore64.Modifier.Commodore;
                }
            } else if (!pressed && !isFunctionKey) {
                mMod = ControllerCommodore64.Modifier.Normal;
            }
            Log.d(TAG, "Mode after: "+mMod.toString());
            return true;
        }
        return false;
    }
}
