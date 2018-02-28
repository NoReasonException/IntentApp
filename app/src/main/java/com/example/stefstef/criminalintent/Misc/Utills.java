package com.example.stefstef.criminalintent.Misc;
import com.example.stefstef.criminalintent.R;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.app.Activity;

/***
 * Utills Class
 * @author stefstef
 * @version 0.0.1
 */
public class Utills {
    /**
     * getActionBarSizeAttr takes the actionbar size attribute of current theme
     * @param theme  The theme to take the attribute
     * @return       The size of bar!
     * @Note         The size of bar is in unnkown type size (px,dp e.t.c) you must know
     * in what measuring system this value is , and to convert it in pixels to work
     * with View Methods
     */
    public static int getActionBarSizeAttr(Resources.Theme theme){
        TypedArray a = theme.obtainStyledAttributes(
                new int[]{R.attr.actionBarSize}
        );
        String retval;
        if((retval=a.getString(0))==null)return 0;
        return Integer.valueOf(retval.substring(0,2));
    }
    /***
     * Converts a @param dp value to pixels , for use with View methods!
     * @param metrics   The metrics of this phone (Taken by Activity's resources)
     * @param dp        The dp's!
     * @return          The pixel equilevant , casted to int
     */
    public static int convertDPtoPX(DisplayMetrics metrics, int dp){
        return (int)TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                (float)dp,
                metrics
        );
    }


}
