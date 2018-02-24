package com.example.stefstef.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.util.Date;

/**
 * Created by stefstef on 24/2/2018.
 */

public class CrimePickerPagerDialog extends DialogFragment {
    private ViewPager   pager;
    private View        v;
    private RadioButton ch1;
    private RadioButton ch2;

    /**
     * Perform initialization of all fragments and loaders.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater infl, ViewGroup viewGroup,Bundle save){
        this.v = infl.inflate(R.layout.datetime_config,viewGroup);
        this.pager=v.findViewById(R.id.viewPager);
        this.ch1=v.findViewById(R.id.ch1);
        this.ch2=v.findViewById(R.id.ch2);
        //this.pager=new ViewPager(this.getActivity());
        this.pager.setId(R.id.pickerViewPager);
        final FragmentManager fm = this.getChildFragmentManager();
        this.pager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                if(position==0){
                    CrimePickerPagerDialog.this.ch1.setChecked(true);
                    CrimePickerPagerDialog.this.ch2.setChecked(false);
                    return CrimeDatePicker.getInstance(new Date(),null,CrimeFragment.REQUEST_NEW_DATE   );

                }

                CrimePickerPagerDialog.this.ch1.setChecked(false);
                CrimePickerPagerDialog.this.ch2.setChecked(true);
                return CrimeTimePicker.getInstance(new Date(),null,12);
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
        this.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    CrimePickerPagerDialog.this.ch1.setChecked(true);
                    CrimePickerPagerDialog.this.ch2.setChecked(false);
                    return;
                    //return CrimeDatePicker.getInstance(new Date(),null,CrimeFragment.REQUEST_NEW_DATE   );

                }

                CrimePickerPagerDialog.this.ch1.setChecked(false);
                CrimePickerPagerDialog.this.ch2.setChecked(true);
                //return CrimeTimePicker.getInstance(new Date(),null,12);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return v;
    }
}
