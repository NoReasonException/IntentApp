package com.example.stefstef.criminalintent;  //<-- this imperfection can kill me actually ...
import android.support.v4.app.DialogFragment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Date;

/**
 * Created by stefstef on 24/2/2018.
 */

/***
 * public class CrimePicker extends DialogFragment
 * Represents a abstract DialogFragment , witch takes an Date , and returns an updated version of this date
 */
public class CrimePicker extends DialogFragment {



    public static java.lang.String  dateHash="com.example.stefstef.criminalintent"; //Used to pass Date in intent

    //---------------------Protected Section-----------------------------------//
    protected java.util.Calendar  calendar;                                         //Expected to initialized on onCreateDialog
    protected Date date;                                                            //Expected to initialized on onCreateDialog


    /***
     *
     * @param param             the Date object to inject via intent
     * @param targetFragment    Target fragment to send back data
     * @param requestCode       Request code , needed to call onActivityResult
     * @param picker            Picker , here we need an object from derived class
     * @return                  A CrimePicker ready for action! ;)
     */
    protected static CrimePicker getInstance(@NonNull Date param,
                                              Fragment targetFragment,
                                              final int requestCode,
                                              CrimePicker picker){
        Bundle args= new Bundle();
        args.putSerializable(CrimePicker.dateHash,param);
        picker.setArguments(args);
        picker.setTargetFragment(targetFragment,requestCode);
        return picker;
    }
    /***
     *
     * @param request               The request code (usually exist as static final integer on sender-class)
     * @param responce              The responce code(usually exist as static final integer on derived-class)
     * @throws IllegalStateException
     *              Why? it is expected to initialize the date and calendar members on onCreateDialog
     *              if not? Boom!
     */
    protected void sendDate(final int request,final int responce){
        if(date==null)throw new IllegalStateException("you must initialize this.date and this.calendar on CrimePicker.onCreateDialog() ");
        Intent intent = new Intent();
        intent.putExtra(CrimeFragment.DIALOG_DATE_TAG,this.date);
        this.getTargetFragment().onActivityResult(request,responce,intent);
    }

    protected CrimePicker setView(View v){
        this.setView(v);
        return this;
    }


}
