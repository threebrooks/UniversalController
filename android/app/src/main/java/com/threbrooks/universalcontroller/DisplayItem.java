package com.threbrooks.universalcontroller;

class DisplayItem {
    DisplayItem() {}
    DisplayItem(String name) {
        mName = name;
    }
    String mName;
    public String toString() {
        return mName;
    }
}