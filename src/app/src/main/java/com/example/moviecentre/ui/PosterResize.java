package com.example.moviecentre.ui;

import android.content.Context;
import android.util.DisplayMetrics;

public class PosterResize {

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 120);
    }

}
