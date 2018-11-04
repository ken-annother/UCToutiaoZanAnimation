package com.nicekun.uctoutiaozananimation;

import android.graphics.Rect;

public class UCZanUtils {
    public static final String TAG = "UCZanAnimation";

    public static boolean outOf(Rect parent, Rect child) {
        return parent.left > child.right || parent.right < child.left || parent.top > child.bottom || parent.bottom < child.bottom;
    }
}
