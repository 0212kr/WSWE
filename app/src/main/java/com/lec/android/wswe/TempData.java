package com.lec.android.wswe;

import java.io.Serializable;

public class TempData implements Serializable {

    String notice = "";

    private static TempData instance = null;
    public static TempData getInstance() {
        if (instance == null) {
            instance = new TempData();
        }
        return instance;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
}
