package com.example.stefstef.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import java.util.Date;

/**
 * Created by stefstef on 24/2/2018.
 */

public class CrimePicker extends DialogFragment {



    public static java.lang.String  dateHash="com.example.stefstef.criminalintent";

    //---------------------Protected Section-----------------------------------//
    protected java.util.Calendar  calendar;         //Expected to initialized on onCreateDialog
    protected Date date;                            //Expected to initialized on onCreateDialog


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


    /*This method calls the targets fragment onActivityResult to pass the data.*/
    protected void sendDate(final int request,final int responce){
        if(date==null)throw new IllegalStateException("you must initialize this.date and this.calendar on CrimePicker.onCreateDialog() ");
        Intent intent = new Intent();
        intent.putExtra(CrimeFragment.DIALOG_DATE_TAG,this.date);
        this.getTargetFragment().onActivityResult(request,responce,intent);
    }


}
