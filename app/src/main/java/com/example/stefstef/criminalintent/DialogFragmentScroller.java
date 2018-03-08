package com.example.stefstef.criminalintent;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.security.InvalidParameterException;
import android.support.v4.app.FragmentManager;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.os.Handler;
import android.view.View;
import android.os.Bundle;
import android.util.Log;


/***
 * DialogFragmentScroller
 * Takes Fragments , Represents it as Scrollable AlertBox
 *
 */
public class DialogFragmentScroller extends DialogFragment {
    public static int                               NEW_DATE_RESPONCE=12;
    //--------------------------------------------Private Section----------------------------------------------------------------//
    private static java.lang.String                 FRAGMENT_LIST_HASH ="FRAG_LIST#com.example.stefstef.criminalintent.DialogFragmentScroller";
    private static java.lang.String                 CRIME_CURR_DATE="DAT_CURR#com.example.stefstef.criminalintent.DialogFragmentScroller";
    private static java.lang.String                 TAG="DialogFragmentScroller_LOG:";
    private ViewPager                               pager;
    private View                                    dialogView;
    private ArrayList<RadioButton>                  radioButtons;
    private ArrayList<Class<? extends CrimePicker>> fragmentsClasses;
    private ArrayList<Fragment>                     fragments;
    private LinearLayout                            radioLay;
    private FloatingActionButton                    saveBtn;
    private static java.util.Date                   globalDate;

    public static void submitDateChange(java.util.Date newDate){
        DialogFragmentScroller.globalDate=newDate;
        Log.i(DialogFragmentScroller.TAG, String.format("Date updated , is %s ", DateFormat.getDateInstance().format(DialogFragmentScroller.globalDate)));

    }
    /**
     * getInstance(...
     * @param fragmentsList The fragment's.class
     * @return              A bright new DialogFragmentScroller
     */
    public static DialogFragmentScroller getInstance(ArrayList<Class<? extends CrimePicker>> fragmentsList,
                                                     java.util.Date curr_date){
        DialogFragmentScroller dialog = new DialogFragmentScroller();
        Bundle args=new Bundle();
        args.putSerializable(DialogFragmentScroller.FRAGMENT_LIST_HASH,fragmentsList);
        args.putSerializable(DialogFragmentScroller.CRIME_CURR_DATE,curr_date);
        dialog.setArguments(args);
        Log.i(DialogFragmentScroller.TAG, String.format("%d fragments with %s date on FragmentScrolller",fragmentsList.size(), DateFormat.getDateInstance().format(curr_date)));
        return dialog;
    }
    @Override
    @SuppressWarnings("unchecked")
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DialogFragmentScroller.globalDate=(java.util.Date)
                this.getArguments().getSerializable(DialogFragmentScroller.CRIME_CURR_DATE);
        this.fragmentsClasses=(ArrayList<Class<? extends CrimePicker>>)
                this.getArguments().getSerializable(DialogFragmentScroller.FRAGMENT_LIST_HASH);
        if(this.fragmentsClasses==null || this.globalDate==null)
            throw new InvalidParameterException("Use DialogFragmentScroller.getInstance()");

    }

    /***
     * initializeRadioButtons
     * @throws NullPointerException . Must call initializeReferences() first
     */
    private void initializeRadioButtons(){
        this.radioButtons=new ArrayList<RadioButton>();
        RadioButton cursor;
        for (final Class<? extends CrimePicker> f:this.fragmentsClasses){
            cursor=new RadioButton(this.getActivity());
            this.radioLay.addView(cursor);
            this.radioButtons.add(cursor);
        }


    }

    /***
     * Updates the members of the class
     * @param v , the inflated view
     */
    private void initializeReferences(View v){
        this.radioLay=v.findViewById(R.id.radioLinear);
        this.pager=v.findViewById(R.id.viewPager);
        this.saveBtn=v.findViewById(R.id.scrollerFab);

    }
    private void initializeHandlers(){
        this.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(globalDate==null)throw new IllegalStateException("you must initialize this.date and this.calendar on CrimePicker.onCreateDialog() ");
                Intent intent = new Intent();
                intent.putExtra(CrimeFragment.DIALOG_DATE_TAG,globalDate);
                getTargetFragment().onActivityResult(CrimeFragment.REQUEST_NEW_DATE,DialogFragmentScroller.NEW_DATE_RESPONCE,intent);
                getFragmentManager().beginTransaction().remove(DialogFragmentScroller.this).commit();

            }
        });
    }

    /***
     * Submits a new job in looper , to update radiobuttons position
     * @param position , the position to update
     */
    private void setRadioButtonOnPosition(int position){

        Handler handler=new Handler();
        handler.post(new Runnable() {
            int position=0;
            @Override
            public void run() {
                Log.i("submit","setRadioButtonOnPosition");
                for (RadioButton b:DialogFragmentScroller.this.radioButtons) {
                    b.setChecked(false);
                    b.setEnabled(false);

                }
                DialogFragmentScroller.this.radioButtons.get(position).setChecked(true);
                DialogFragmentScroller.this.radioButtons.get(position).setEnabled(true);
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
        this.dialogView = infl.inflate(R.layout.datetime_config,viewGroup);
        this.initializeReferences(dialogView);
        this.initializeRadioButtons();
        this.initializeHandlers();
        final FragmentManager fm = this.getChildFragmentManager();
        this.pager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                if(DialogFragmentScroller.this.fragments==null){
                    DialogFragmentScroller.this.fragments=new ArrayList<Fragment>();
                    /*For every class , make an instance , and
                    * use .getInstance to load the parameters via Bundle
                    *
                    * */
                    for (final Class<? extends CrimePicker> f:DialogFragmentScroller.this.fragmentsClasses){
                        try{
                            DialogFragmentScroller.this.fragments.add(
                                    CrimePicker.getInstance(DialogFragmentScroller.globalDate,null,12,f.newInstance())
                            );
                            DialogFragmentScroller.this.setRadioButtonOnPosition(position);

                        }catch (Exception e){e.printStackTrace();}
                    }

                }
                Fragment fr = DialogFragmentScroller.this.fragments.get(position);
                Log.i(DialogFragmentScroller.TAG,"getItem returns object "+fr);
                return fr;
            }

            @Override
            public int getCount() {

                return DialogFragmentScroller.this.fragmentsClasses.size();
            }
        });
        this.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i(DialogFragmentScroller.TAG,"onPageSelected at position +"+position);

                DialogFragmentScroller.this.setRadioButtonOnPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return dialogView;
    }
}
