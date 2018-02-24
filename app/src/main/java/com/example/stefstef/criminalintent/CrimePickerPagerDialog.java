package com.example.stefstef.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by stefstef on 24/2/2018.
 */

public class CrimePickerPagerDialog extends DialogFragment {
    private static java.lang.String                 fragmentListHash="com.example.stefstef.criminalintent.CrimePickerPagerDialog";
    private ViewPager                               pager;
    private View                                    v;
    private ArrayList<RadioButton>                  radioButtons;
    private ArrayList<Class<? extends CrimePicker>> fragmentsClasses;
    private ArrayList<Fragment>                     fragments;
    private LinearLayout                            radioLay;

    public static CrimePickerPagerDialog getInstance(ArrayList<Class<? extends CrimePicker>> fragmentsList){
        CrimePickerPagerDialog dialog = new CrimePickerPagerDialog();
        Bundle args=new Bundle();
        args.putSerializable(CrimePickerPagerDialog.fragmentListHash,fragmentsList);
        dialog.setArguments(args);
        return dialog;
    }
    /**
     * Perform initialization of all fragments and loaders.
     *
     * @param savedInstanceState
     */
    @Override
    @SuppressWarnings("unchecked")
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.fragmentsClasses=(ArrayList<Class<? extends CrimePicker>>) this.getArguments().getSerializable(CrimePickerPagerDialog.fragmentListHash);
        if(this.fragmentsClasses==null){throw new InvalidParameterException("Use CrimePickerPagerDialog.getInstance()");}

    }
    private void initializeRadioButtons(){
        this.radioButtons=new ArrayList<RadioButton>();
        RadioButton cursor;
        for (final Class<? extends CrimePicker> f:this.fragmentsClasses){
            cursor=new RadioButton(this.getActivity());
            this.radioLay.addView(cursor);
            this.radioButtons.add(cursor);
        }


    }
    private void initializeReferences(View v){
        this.radioLay=v.findViewById(R.id.radioLinear);
        this.pager=v.findViewById(R.id.viewPager);
    }
    private void setRadioButtonOnPosition(int position){

        Handler handler=new Handler();
        handler.post(new Runnable() {
            int position=0;
            @Override
            public void run() {
                Log.i("submit","setRadioButtonOnPosition");
                for (RadioButton b:CrimePickerPagerDialog.this.radioButtons) {
                    b.setChecked(false);

                }
                CrimePickerPagerDialog.this.radioButtons.get(position).setChecked(true);
            }
            public Runnable init(int position){
                this.position=position;
                return this;
            }
        }.init(position));


    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater infl, ViewGroup viewGroup,Bundle save){
        this.v = infl.inflate(R.layout.datetime_config,viewGroup);
        this.initializeReferences(v);
        this.initializeRadioButtons();

        //this.pager=new ViewPager(this.getActivity());
        this.pager.setId(R.id.pickerViewPager);
        final FragmentManager fm = this.getChildFragmentManager();
        this.pager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                if(CrimePickerPagerDialog.this.fragments==null){
                    CrimePickerPagerDialog.this.fragments=new ArrayList<Fragment>();
                    for (final Class<? extends CrimePicker> f:CrimePickerPagerDialog.this.fragmentsClasses){
                        try{
                            CrimePickerPagerDialog.this.fragments.add(
                                    CrimePicker.getInstance(new Date(),null,12,f.newInstance())
                            );
                            CrimePickerPagerDialog.this.setRadioButtonOnPosition(position);

                        }catch (Exception e){e.printStackTrace();}
                    }

                }
                return CrimePickerPagerDialog.this.fragments.get(position);
            }

            @Override
            public int getCount() {

                return CrimePickerPagerDialog.this.fragmentsClasses.size();
            }
        });
        this.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                CrimePickerPagerDialog.this.setRadioButtonOnPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return v;
    }
}
