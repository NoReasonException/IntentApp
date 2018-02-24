package com.example.stefstef.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.Date;

/**
 * Created by stefstef on 24/2/2018.
 */

public class CrimePickerPager extends FragmentActivity {
    private ViewPager pager;

    /**
     * Perform initialization of all fragments and loaders.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.pager=new ViewPager(this);
        this.pager.setId(R.id.pickerViewPager);
        this.setContentView(this.pager);

        FragmentManager fm = this.getSupportFragmentManager();

        this.pager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                if(position==0){
                    return CrimeDatePicker.getInstance(new Date(),new CrimeFragment(),CrimeFragment.REQUEST_NEW_DATE);
                }
                return CrimeTimePicker.getInstance(new Date(),new CrimeFragment(),CrimeFragment.REQUEST_NEW_DATE);

            }

            @Override
            public int getCount() {
                return 1;
            }
        });

    }
}
