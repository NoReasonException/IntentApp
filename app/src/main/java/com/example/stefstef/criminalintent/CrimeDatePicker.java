package com.example.stefstef.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.DatePicker;

import com.example.stefstef.criminalintent.Misc.CrimeArrayAdapter;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by stefstef on 22/2/2018.
 */

public class CrimeDatePicker extends DialogFragment {
    public static java.lang.String DIALOG_DATE_TAG="date";
    public static java.lang.String TAG="com.example.stefstef.criminalintent";
    private java.util.Date      date ;
    private java.util.Calendar  calendar;
    public static CrimeDatePicker getInstance(@NonNull Date param){
        return new CrimeDatePicker().setDate(param);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        this.calendar=Calendar.getInstance();
        calendar.setTime(this.date);
        DatePicker datePicker = new DatePicker(this.getActivity());
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                        Calendar c=CrimeDatePicker.this.calendar;
                        c.add(Calendar.YEAR,i);
                        c.add(Calendar.MONTH,i1);
                        c.add(Calendar.DATE,i2);
                        Log.i(CrimeDatePicker.TAG, String.format("Date changed to year:%d month:%d day:%d",i,i1,i2));
                    }
                });
        return new AlertDialog.Builder(this.getActivity())
                .setView(new DatePicker(this.getActivity()))
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(R.string.ok,null)
                .create();
    }
    public CrimeDatePicker setDate(Date date){
        this.date=date;
        return this;
    }

}
