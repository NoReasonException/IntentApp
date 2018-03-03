package com.example.stefstef.criminalintent;

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
import java.util.ArrayList;
import android.os.Handler;
import android.view.View;
import android.os.Bundle;
import android.util.Log;
import java.util.Date;


/***
 * DialogFragmentScroller
 * Takes Fragments , Represents it as Scrollable AlertBox
 *
 */
public class DialogFragmentScroller extends DialogFragment {
    //--------------------------------------------Private Section----------------------------------------------------------------//
    private static java.lang.String                 fragmentListHash="com.example.stefstef.criminalintent.DialogFragmentScroller";
    private static java.lang.String                 TAG="DialogFragmentScroller_LOG:";
    private ViewPager                               pager;
    private View                                    dialogView;
    private ArrayList<RadioButton>                  radioButtons;
    private ArrayList<Class<? extends CrimePicker>> fragmentsClasses;
    private ArrayList<Fragment>                     fragments;
    private LinearLayout                            radioLay;

    /**
     * getInstance(...
     * @param fragmentsList The fragment's.class
     * @return              A bright new DialogFragmentScroller
     */
    public static DialogFragmentScroller getInstance(ArrayList<Class<? extends CrimePicker>> fragmentsList){
        DialogFragmentScroller dialog = new DialogFragmentScroller();
        Bundle args=new Bundle();
        args.putSerializable(DialogFragmentScroller.fragmentListHash,fragmentsList);
        dialog.setArguments(args);
        return dialog;
    }
    @Override
    @SuppressWarnings("unchecked")
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.fragmentsClasses=(ArrayList<Class<? extends CrimePicker>>) this.getArguments().getSerializable(DialogFragmentScroller.fragmentListHash);
        if(this.fragmentsClasses==null){throw new InvalidParameterException("Use DialogFragmentScroller.getInstance()");}

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
                                    CrimePicker.getInstance(new Date(),null,12,f.newInstance())
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
