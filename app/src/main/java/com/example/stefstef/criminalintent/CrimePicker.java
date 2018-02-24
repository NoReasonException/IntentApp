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


    //expected to initialized in override version of onCreateDialog
    public static java.lang.String  dateHash="com.example.stefstef.criminalintent";
    protected Date date;
    protected java.util.Calendar  calendar;


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
        Intent intent = new Intent();
        intent.putExtra(CrimeDatePicker.DIALOG_DATE_TAG,this.date);
        this.getTargetFragment().onActivityResult(CrimeFragment.REQUEST_NEW_DATE,CrimeDatePicker.RESPONCE_NEW_DATE,intent);
    }


}
