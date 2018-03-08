package com.example.stefstef.criminalintent.Misc;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by stefstef on 8/3/2018.
 */

public class myViewPager extends ViewPager {
    public myViewPager(Context context) {
        super(context);
    }

    public myViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /***
     * Make ViewPager really wraps its context!
     * special thanks to https://stackoverflow.com/a/20784791/8146220 :)
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for(int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if(h > height) height = h;
        }

        if (height != 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
