package com.example.stefstef.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import java.security.InvalidParameterException;
import java.util.Calendar;

/**
 * Created by stefstef on 24/2/2018.
 */

public class CrimeTimePicker extends CrimePicker {
    public static java.lang.String  TAG="CrimeTimePicker";
    public static final int         RESPONCE_NEW_TIME=-2;
    public static CrimePicker getInstance(java.util.Date date,
                                              android.support.v4.app.Fragment targetFragment,
                                              final int requestCode){
        return CrimeTimePicker.getInstance(date,targetFragment,requestCode,new CrimeTimePicker());
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.date=(java.util.Date)getArguments().getSerializable(CrimeTimePicker.dateHash);
        if(this.date==null){throw new InvalidParameterException("use CrimeTmePicker.getInstance();");}
        this.calendar=Calendar.getInstance();
        this.calendar.setTime(this.date);
        TimePicker p = new TimePicker(this.getActivity());
        p.setCurrentHour(this.calendar.get(Calendar.HOUR));
        p.setCurrentMinute(this.calendar.get(Calendar.MINUTE));
        p.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                CrimeTimePicker.this.calendar.set(Calendar.HOUR,i);
                CrimeTimePicker.this.calendar.set(Calendar.MINUTE,i1);
            }
        });
        return new AlertDialog.Builder(this.getActivity())
                .setView(p)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CrimeTimePicker.this.sendDate(CrimeFragment.REQUEST_NEW_TIME,CrimeTimePicker.RESPONCE_NEW_TIME);
                    }
                })
                .create();


    }
}
