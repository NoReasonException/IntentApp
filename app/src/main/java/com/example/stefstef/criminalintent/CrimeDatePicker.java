package com.example.stefstef.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import com.example.stefstef.criminalintent.Models.Crime;

import java.security.InvalidParameterException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by stefstef on 22/2/2018.
 */

public class CrimeDatePicker extends DialogFragment {
    public static java.lang.String DIALOG_DATE_TAG="date";
    public static int RESPONCE_NEW_DATE=1;
    public static java.lang.String TAG="com.example.stefstef.criminalintent";

    private Date date;
    private java.util.Calendar  calendar;
    public static CrimeDatePicker getInstance(@NonNull Date param){
        CrimeDatePicker picker = new CrimeDatePicker();
        Bundle args= new Bundle();
        args.putSerializable(CrimeDatePicker.DIALOG_DATE_TAG,param);
        picker.setArguments(args);
        return picker;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        this.date=(java.util.Date)this.getArguments().getSerializable(CrimeDatePicker.DIALOG_DATE_TAG);
        if(this.date==null){
            throw new InvalidParameterException("Please call CrimeDatePicker.getInstance(java.utill.Date date)");
        }

        this.calendar=Calendar.getInstance();
        calendar.setTime(this.date);
        DatePicker datePicker = new DatePicker(this.getActivity());
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                        Date date=new GregorianCalendar(i,i1,i2).getTime();
                        CrimeDatePicker.this.date=date;
                        Log.i(CrimeDatePicker.TAG, String.format("Date changed to year:%d month:%d day:%d",i,i1,i2));
                        CrimeDatePicker.this.getArguments().putSerializable(
                                CrimeDatePicker.DIALOG_DATE_TAG,date);
                    }
                });
        datePicker.setMaxDate(new Date().getTime());

        return new AlertDialog.Builder(this.getActivity())
                .setView(datePicker)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CrimeDatePicker.this.sendDate();
                    }
                })
                .create();
    }
    @NonNull
    public CrimeDatePicker setDate(@NonNull Date cr){
        this.date =cr;
        return this;
    }
    private void sendDate(){
        Intent intent = new Intent();
        intent.putExtra(CrimeDatePicker.DIALOG_DATE_TAG,this.date);
        this.getTargetFragment().onActivityResult(CrimeFragment.REQUEST_NEW_DATE,CrimeDatePicker.RESPONCE_NEW_DATE,intent);
    }

}
