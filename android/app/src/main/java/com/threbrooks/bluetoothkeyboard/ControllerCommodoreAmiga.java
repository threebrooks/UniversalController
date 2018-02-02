package com.threbrooks.bluetoothkeyboard;

import android.content.Context;
import android.util.Log;

public class ControllerCommodoreAmiga extends BitmapControllerView {

    public ControllerCommodoreAmiga(Context context) {
        super(context, R.drawable.controller_amiga, R.drawable.controller_amiga_mask);
    }

    enum AmigaKey {
        KEY_ESC,
        KEY_F1,
        KEY_F2,
        KEY_F3,
        KEY_F4,
        KEY_F5,
        KEY_F6,
        KEY_F7,
        KEY_F8,
        KEY_F9,
        KEY_F10,

        KEY_BACKQUOTE,
        KEY_TILDE,
        KEY_1,
        KEY_EXCLAMATION_MARK,
        KEY_2,
        KEY_QUOTES,
        KEY_3,
        KEY_POUND,
        KEY_4,
        KEY_DOLLAR,
        KEY_5,
        KEY_PERCENT,
        KEY_6,
        KEY_CARET,
        KEY_7,
        KEY_AMPERSAND,
        KEY_8,
        KEY_STAR,
        KEY_9,
        KEY_OPEN_PAREN,
        KEY_0,
        KEY_CLOSE_PAREN,
        KEY_MINUS,
        KEY_UNDERSCORE,
        KEY_EQUALS,
        KEY_PLUS,
        KEY_BACKSLASH,
        KEY_PIPE,
        KEY_BACKSPACE,
        KEY_DEL,
        KEY_HELP,

        KEY_TABRIGHT,
        KEY_TABLEFT,
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
        KEY_OPEN_SQUARED_BRACKET,
        KEY_OPEN_CURLY_BRACKET,
        KEY_CLOSE_SQUARED_BRACKET,
        KEY_CLOSE_CURLY_BRACKET,

        KEY_CONTROL,
        KEY_CAPS_LOCK,
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
        KEY_SEMICOLON,
        KEY_HASH,
        KEY_AT,
        KEY_RETURN,

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

        KEY_LEFTALT,
        KEY_LEFTAMIGA,
        KEY_SPACE,
        KEY_RIGHTAMIGA,
        KEY_RIGHTALT,

        KEY_CURSOR_DOWN,
        KEY_CURSOR_UP,
        KEY_CURSOR_RIGHT,
        KEY_CURSOR_LEFT,

        KEY_VICE_EXIT,
        NONE
    }

    AmigaKey blueToAmigaKey(int b, Modifier mod) {
        Log.d(TAG,"b="+b);

        if (b == 0) return AmigaKey.KEY_ESC;
        else if (b == 1) return AmigaKey.KEY_F1;
        else if (b == 2) return AmigaKey.KEY_F2;
        else if (b == 3) return AmigaKey.KEY_F3;
        else if (b == 4) return AmigaKey.KEY_F4;
        else if (b == 5) return AmigaKey.KEY_F5;
        else if (b == 6) return AmigaKey.KEY_F6;
        else if (b == 7) return AmigaKey.KEY_F7;
        else if (b == 8) return AmigaKey.KEY_F8;
        else if (b == 9) return AmigaKey.KEY_F9;
        else if (b == 10) return AmigaKey.KEY_F10;
        else if (b == 11) {
            if (mod == Modifier.Normal) return AmigaKey.KEY_BACKQUOTE;
            else if (mod == Modifier.Shift) return AmigaKey.KEY_TILDE;
        }
        else if (b == 12) {
            if (mod == Modifier.Normal) return AmigaKey.KEY_1;
            else if (mod == Modifier.Shift) return AmigaKey.KEY_EXCLAMATION_MARK;
        }
        else if (b == 13) {
            if (mod == Modifier.Normal) return AmigaKey.KEY_2;
            else if (mod == Modifier.Shift) return AmigaKey.KEY_QUOTES;
        }
        else if (b == 14) {
            if (mod == Modifier.Normal) return AmigaKey.KEY_3;
            else if (mod == Modifier.Shift) return AmigaKey.KEY_POUND;
        }
        else if (b == 15) {
            if (mod == Modifier.Normal) return AmigaKey.KEY_4;
            else if (mod == Modifier.Shift) return AmigaKey.KEY_DOLLAR;
        }
        else if (b == 16) {
            if (mod == Modifier.Normal) return AmigaKey.KEY_5;
            else if (mod == Modifier.Shift) return AmigaKey.KEY_PERCENT;
        }
        else if (b == 17) {
            if (mod == Modifier.Normal) return AmigaKey.KEY_6;
            else if (mod == Modifier.Shift) return AmigaKey.KEY_CARET;
        }
        else if (b == 18) {
            if (mod == Modifier.Normal) return AmigaKey.KEY_7;
            else if (mod == Modifier.Shift) return AmigaKey.KEY_AMPERSAND;
        }
        else if (b == 19) {
            if (mod == Modifier.Normal) return AmigaKey.KEY_8;
            else if (mod == Modifier.Shift) return AmigaKey.KEY_STAR;
        }
        else if (b == 20) {
            if (mod == Modifier.Normal) return AmigaKey.KEY_9;
            else if (mod == Modifier.Shift) return AmigaKey.KEY_OPEN_PAREN;
        }
        else if (b == 21) {
            if (mod == Modifier.Normal) return AmigaKey.KEY_0;
            else if (mod == Modifier.Shift) return AmigaKey.KEY_CLOSE_PAREN;
        }
        else if (b == 22) {
            if (mod == Modifier.Normal) return AmigaKey.KEY_MINUS;
            else if (mod == Modifier.Shift) return AmigaKey.KEY_UNDERSCORE;
        }
        else if (b == 23) {
            if (mod == Modifier.Normal) return AmigaKey.KEY_EQUALS;
            else if (mod == Modifier.Shift) return AmigaKey.KEY_PLUS;
        }
        else if (b == 24) {
            if (mod == Modifier.Normal) return AmigaKey.KEY_BACKSLASH;
            else if (mod == Modifier.Shift) return AmigaKey.KEY_PIPE;
        }
        else if (b == 25) return AmigaKey.KEY_BACKSPACE;
        else if (b == 26) return AmigaKey.KEY_DEL;
        else if (b == 27) return AmigaKey.KEY_HELP;
        else if (b == 28) {
            if (mod == Modifier.Normal) return AmigaKey.KEY_TABRIGHT;
            else if (mod == Modifier.Shift) return AmigaKey.KEY_TABLEFT;
        }
        else if (b == 29) return AmigaKey.KEY_Q;
        else if (b == 30) return AmigaKey.KEY_W;
        else if (b == 31) return AmigaKey.KEY_E;
        else if (b == 32) return AmigaKey.KEY_R;
        else if (b == 33) return AmigaKey.KEY_T;
        else if (b == 34) return AmigaKey.KEY_Y;
        else if (b == 35) return AmigaKey.KEY_U;
        else if (b == 36) return AmigaKey.KEY_I;
        else if (b == 37) return AmigaKey.KEY_O;
        else if (b == 38) return AmigaKey.KEY_P;
        else if (b == 39) {
            if (mod == Modifier.Normal) return AmigaKey.KEY_OPEN_SQUARED_BRACKET;
            else if (mod == Modifier.Shift) return AmigaKey.KEY_OPEN_CURLY_BRACKET;
        }
        else if (b == 40) {
            if (mod == Modifier.Normal) return AmigaKey.KEY_CLOSE_SQUARED_BRACKET;
            else if (mod == Modifier.Shift) return AmigaKey.KEY_CLOSE_CURLY_BRACKET;
        }
        else if (b == 41) return AmigaKey.KEY_CONTROL;
        else if (b == 42) return AmigaKey.KEY_CAPS_LOCK;
        else if (b == 43) return AmigaKey.KEY_A;
        else if (b == 44) return AmigaKey.KEY_S;
        else if (b == 45) return AmigaKey.KEY_D;
        else if (b == 46) return AmigaKey.KEY_F;
        else if (b == 47) return AmigaKey.KEY_G;
        else if (b == 48) return AmigaKey.KEY_H;
        else if (b == 49) return AmigaKey.KEY_J;
        else if (b == 50) return AmigaKey.KEY_K;
        else if (b == 51) return AmigaKey.KEY_L;
        else if (b == 52) {
            if (mod == Modifier.Normal) return AmigaKey.KEY_SEMICOLON;
            else if (mod == Modifier.Shift) return AmigaKey.KEY_COLON;
        }
        else if (b == 53) {
            if (mod == Modifier.Normal) return AmigaKey.KEY_HASH;
            else if (mod == Modifier.Shift) return AmigaKey.KEY_AT;
        }
        else if (b == 54) return AmigaKey.KEY_RETURN;
        else if (b == 55) return AmigaKey.KEY_LEFTSHIFT;
        else if (b == 56) return AmigaKey.KEY_Z;
        else if (b == 57) return AmigaKey.KEY_X;
        else if (b == 58) return AmigaKey.KEY_C;
        else if (b == 59) return AmigaKey.KEY_V;
        else if (b == 60) return AmigaKey.KEY_B;
        else if (b == 61) return AmigaKey.KEY_N;
        else if (b == 62) return AmigaKey.KEY_M;
        else if (b == 63) {
            if (mod == Modifier.Normal) return AmigaKey.KEY_COMMA;
            else if (mod == Modifier.Shift) return AmigaKey.KEY_OPEN_ANGLED_BRACKET;
        }
        else if (b == 64) {
            if (mod == Modifier.Normal) return AmigaKey.KEY_DOT;
            else if (mod == Modifier.Shift) return AmigaKey.KEY_CLOSE_ANGLED_BRACKET;
        }
        else if (b == 65) {
            if (mod == Modifier.Normal) return AmigaKey.KEY_SLASH;
            else if (mod == Modifier.Shift) return AmigaKey.KEY_QUESTION_MARK;
        }
        else if (b == 66) return AmigaKey.KEY_LEFTSHIFT;
        else if (b == 67) return AmigaKey.KEY_LEFTALT;
        else if (b == 68) return AmigaKey.KEY_LEFTAMIGA;
        else if (b == 69) return AmigaKey.KEY_SPACE;
        else if (b == 70) return AmigaKey.KEY_RIGHTAMIGA;
        else if (b == 71) return AmigaKey.KEY_RIGHTALT;
        else if (b == 72) return AmigaKey.KEY_CURSOR_UP;
        else if (b == 73) return AmigaKey.KEY_CURSOR_LEFT;
        else if (b == 74) return AmigaKey.KEY_CURSOR_DOWN;
        else if (b == 75) return AmigaKey.KEY_CURSOR_RIGHT;
        return AmigaKey.NONE;
    }

    String AmigaKeyToViceCombo(AmigaKey key) {
        if (key == AmigaKey.KEY_ESC) return UInput.KEY_ESC;
        else if (key == AmigaKey.KEY_F1) return UInput.KEY_F1;
        else if (key == AmigaKey.KEY_F2) return UInput.KEY_F2;
        else if (key == AmigaKey.KEY_F3) return UInput.KEY_F3;
        else if (key == AmigaKey.KEY_F4) return UInput.KEY_F4;
        else if (key == AmigaKey.KEY_F5) return UInput.KEY_F5;
        else if (key == AmigaKey.KEY_F6) return UInput.KEY_F6;
        else if (key == AmigaKey.KEY_F7) return UInput.KEY_F7;
        else if (key == AmigaKey.KEY_F8) return UInput.KEY_F8;

        if (key == AmigaKey.KEY_BACKQUOTE) return UInput.KEY_GRAVE;
        if (key == AmigaKey.KEY_TILDE) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_GRAVE;

        else if (key == AmigaKey.KEY_1) return UInput.KEY_1;
        else if (key == AmigaKey.KEY_EXCLAMATION_MARK) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_1;
        else if (key == AmigaKey.KEY_2) return UInput.KEY_2;
        else if (key == AmigaKey.KEY_QUOTES) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_APOSTROPHE;
        else if (key == AmigaKey.KEY_3) return UInput.KEY_3;
        else if (key == AmigaKey.KEY_POUND) return "";
        else if (key == AmigaKey.KEY_4) return UInput.KEY_4;
        else if (key == AmigaKey.KEY_DOLLAR) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_4;
        else if (key == AmigaKey.KEY_5) return UInput.KEY_5;
        else if (key == AmigaKey.KEY_PERCENT) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_5;
        else if (key == AmigaKey.KEY_6) return UInput.KEY_6;
        else if (key == AmigaKey.KEY_CARET) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_6;
        else if (key == AmigaKey.KEY_7) return UInput.KEY_7;
        else if (key == AmigaKey.KEY_AMPERSAND) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_7;
        else if (key == AmigaKey.KEY_8) return UInput.KEY_8;
        else if (key == AmigaKey.KEY_STAR) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_8;
        else if (key == AmigaKey.KEY_9) return UInput.KEY_9;
        else if (key == AmigaKey.KEY_OPEN_PAREN) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_9;
        else if (key == AmigaKey.KEY_0) return UInput.KEY_0;
        else if (key == AmigaKey.KEY_CLOSE_PAREN) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_0;
        else if (key == AmigaKey.KEY_MINUS) return UInput.KEY_MINUS;
        else if (key == AmigaKey.KEY_UNDERSCORE) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_MINUS;
        else if (key == AmigaKey.KEY_EQUALS) return UInput.KEY_EQUAL;
        else if (key == AmigaKey.KEY_PLUS) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_EQUAL;
        else if (key == AmigaKey.KEY_BACKSLASH) return UInput.KEY_BACKSLASH;
        else if (key == AmigaKey.KEY_PIPE) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_BACKSLASH;
        else if (key == AmigaKey.KEY_BACKSPACE) return UInput.KEY_BACKSPACE;
        else if (key == AmigaKey.KEY_DEL) return UInput.KEY_DELETE;
        else if (key == AmigaKey.KEY_HELP) return UInput.KEY_HELP;

        else if (key == AmigaKey.KEY_TABLEFT) return UInput.KEY_TAB;
        else if (key == AmigaKey.KEY_TABRIGHT) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_TAB;
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
        else if (key == AmigaKey.KEY_OPEN_SQUARED_BRACKET) return UInput.KEY_LEFTBRACE;
        else if (key == AmigaKey.KEY_OPEN_CURLY_BRACKET) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_LEFTBRACE;
        else if (key == AmigaKey.KEY_CLOSE_SQUARED_BRACKET) return UInput.KEY_RIGHTBRACE;
        else if (key == AmigaKey.KEY_CLOSE_CURLY_BRACKET) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_RIGHTBRACE;

        else if (key == AmigaKey.KEY_CONTROL) return UInput.KEY_LEFTCTRL;
        else if (key == AmigaKey.KEY_CAPS_LOCK) return "";
        else if (key == AmigaKey.KEY_A) return UInput.KEY_A;
        else if (key == AmigaKey.KEY_S) return UInput.KEY_S;
        else if (key == AmigaKey.KEY_D) return UInput.KEY_D;
        else if (key == AmigaKey.KEY_F) return UInput.KEY_F;
        else if (key == AmigaKey.KEY_G) return UInput.KEY_G;
        else if (key == AmigaKey.KEY_H) return UInput.KEY_H;
        else if (key == AmigaKey.KEY_J) return UInput.KEY_J;
        else if (key == AmigaKey.KEY_K) return UInput.KEY_K;
        else if (key == AmigaKey.KEY_L) return UInput.KEY_L;
        else if (key == AmigaKey.KEY_SEMICOLON) return UInput.KEY_SEMICOLON;
        else if (key == AmigaKey.KEY_COLON) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_SEMICOLON;
        else if (key == AmigaKey.KEY_HASH) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_3;
        else if (key == AmigaKey.KEY_AT) return UInput.KEY_LEFTSHIFT + "|" + UInput.KEY_2;
        else if (key == AmigaKey.KEY_RETURN) return UInput.KEY_ENTER;

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

        else if (key == AmigaKey.KEY_LEFTALT) return UInput.KEY_LEFTALT;
        else if (key == AmigaKey.KEY_LEFTAMIGA) return UInput.KEY_INSERT;
        else if (key == AmigaKey.KEY_SPACE) return UInput.KEY_SPACE;
        else if (key == AmigaKey.KEY_RIGHTAMIGA) return UInput.KEY_HOME;

        else if (key == AmigaKey.KEY_CURSOR_DOWN) return UInput.KEY_DOWN;
        else if (key == AmigaKey.KEY_CURSOR_UP) return UInput.KEY_UP;
        else if (key == AmigaKey.KEY_CURSOR_RIGHT) return UInput.KEY_RIGHT;
        else if (key == AmigaKey.KEY_CURSOR_LEFT) return UInput.KEY_LEFT;
        else return "";
    }

    enum Modifier {
        Normal,
        Shift,
        Amiga
    };

    private Modifier mMod = Modifier.Normal;

    public boolean onPixelClick(int r, int g, int b, boolean pressed) {
        if (r == 255 & g == 255) {
            //Log.d(TAG, "Mode before: "+mMod.toString());
            AmigaKey amigaKey = blueToAmigaKey(b, mMod);
            Log.d(TAG, "Amiga key: "+amigaKey.toString());
            boolean isFunctionKey = amigaKey.equals(AmigaKey.KEY_LEFTSHIFT) || amigaKey.equals(AmigaKey.KEY_RIGHTSHIFT) || amigaKey.equals(AmigaKey.KEY_LEFTAMIGA) || amigaKey.equals(AmigaKey.KEY_RIGHTAMIGA);
            //Log.d(TAG, "isFunctionKey: "+Boolean.toString(isFunctionKey));
            String keyString = AmigaKeyToViceCombo(amigaKey);
            //Log.d(TAG, "keyString: "+keyString);
            if (!isFunctionKey && !keyString.equals("")) transmitKey(keyString, pressed ? "KEY_DOWN" : "KEY_UP");

            if (mMod == Modifier.Normal) {
                if (amigaKey.equals(AmigaKey.KEY_LEFTSHIFT) || amigaKey.equals(AmigaKey.KEY_RIGHTSHIFT)) {
                    mMod = Modifier.Shift;
                } else if (amigaKey.equals(AmigaKey.KEY_LEFTAMIGA) || amigaKey.equals(AmigaKey.KEY_RIGHTAMIGA)) {
                    mMod = Modifier.Amiga;
                }
            } else if (!pressed && !isFunctionKey) {
                mMod = Modifier.Normal;
            }
            //Log.d(TAG, "Mode after: "+mMod.toString());
            return true;
        }
        return false;
    }
}
