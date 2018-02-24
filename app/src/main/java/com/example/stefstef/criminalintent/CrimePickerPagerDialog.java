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

import java.util.Date;

/**
 * Created by stefstef on 24/2/2018.
 */

public class CrimePickerPagerDialog extends DialogFragment {
    private ViewPager pager;

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
        this.pager=new ViewPager(this.getActivity());
        this.pager.setId(R.id.pickerViewPager);
        final FragmentManager fm = this.getChildFragmentManager();
        this.pager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                if(position==0){
                    return CrimeDatePicker.getInstance(new Date(),null,CrimeFragment.REQUEST_NEW_DATE   );

                }
                return CrimeTimePicker.getInstance(new Date(),null,12);
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
        return this.pager;
    }
}
