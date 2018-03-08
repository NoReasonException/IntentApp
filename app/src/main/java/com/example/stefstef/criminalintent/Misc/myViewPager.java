package com.example.stefstef.criminalintent.Misc;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by stefstef on 8/3/2018
 */

public class myViewPager extends ViewPager {
    public myViewPager(Context context) {
        super(context);
    }

    public myViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /***
     * Make ViewPager really wraps its context! (by default , the viewPager has fill_parent behavior . we override that :))
     * special thanks to https://stackoverflow.com/a/20784791/8146220 :)
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for(int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);             //get Every child
            //pass widthMeasureSpec + Unspecified HeightMeasureSpec
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight(); //get height of this child!
            if(h > height) height = h;         //keep only the biggest one!
        }

        if (height != 0) {                      //just in empty case..
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY); //make measureSpec as exacly
                                                                                            //the biggest height of
                                                                                            //child!
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
