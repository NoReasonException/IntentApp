package com.example.stefstef.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import java.security.InvalidParameterException;
import java.util.Calendar;

/**
 * Created by stefstef on 24/2/2018.
 */

public class CrimeTimePicker extends DialogFragment {
    public static java.lang.String  TAG="CrimeTimePicker";
    public static java.lang.String  dateHash="com.example.stefstef.criminalintent";
    public static final int         RESPONCE_NEW_TIME=-2;
    public java.util.Date           date;
    public static CrimeTimePicker getInstance(java.util.Date date,
                                              android.support.v4.app.Fragment targetFragment,
                                              final int requestCode){
        Bundle args = new Bundle();
        CrimeTimePicker p=new CrimeTimePicker();
        args.putSerializable(CrimeTimePicker.dateHash,date);
        p.setArguments(args);
        p.setTargetFragment(targetFragment,requestCode);
        return p;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.date=(java.util.Date)getArguments().getSerializable(CrimeTimePicker.dateHash);
        if(this.date==null){throw new InvalidParameterException("use CrimeTmePicker.getInstance();");}
        TimePicker p = new TimePicker(this.getActivity());
        Calendar c = Calendar.getInstance();
        p.setCurrentHour(c.get(Calendar.HOUR));
        p.setCurrentMinute(c.get(Calendar.MINUTE));
        return new AlertDialog.Builder(this.getActivity())
                .setView(p)
                .setPositiveButton("OK",null)
                .create();


    }
}
