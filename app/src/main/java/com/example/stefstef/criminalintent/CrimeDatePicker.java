package com.example.stefstef.criminalintent;

import java.security.InvalidParameterException;
import android.support.annotation.NonNull;
import android.content.DialogInterface;
import java.util.GregorianCalendar;

import android.support.v4.app.Fragment;
import android.widget.DatePicker;
import android.app.AlertDialog;
import android.app.Dialog;
import java.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import java.util.Date;

public class CrimeDatePicker extends CrimePicker {
    //----------------------------Public Section----------------------------------//
    public static java.lang.String TAG="com.example.stefstef.criminalintent";    //Log Tag
    public static int RESPONCE_NEW_DATE=-1;                                     //Responce to CrimeFragment
    //----------------------------Private Section-----------------------------//

    /***
     * To get a proper instance of CrimeDatePicker
     * If you dont use this , the onCreateDialog may produce InvalidParameterException
     *
     * @param param The date paramater
     * @return CrimeDatePicker
     */
    public static CrimePicker getInstance(@NonNull Date param,
                                              Fragment targetFragment,
                                              final int requestCode){
        return CrimePicker.getInstance(param,targetFragment,requestCode,new CrimeDatePicker());
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        /*In case of not calling the getInstance()*/
        this.date=(java.util.Date)this.getArguments().getSerializable(CrimePicker.dateHash);
        if(this.date==null){
            throw new InvalidParameterException("Please call CrimeDatePicker.getInstance(java.utill.Date date)");
        }

        this.calendar=Calendar.getInstance();
        calendar.setTime(this.date);
        DatePicker datePicker = new DatePicker(this.getActivity());
        /*Initialization of calendar*/
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE),
                new DatePicker.OnDateChangedListener() {
                    /*@Note : We save the current date to avoid rotation bug*/
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                        Date date=new GregorianCalendar(i,i1,i2).getTime();
                        CrimeDatePicker.this.date=date;
                        Log.i(CrimeDatePicker.TAG, String.format("Date changed to year:%d month:%d day:%d",i,i1,i2));
                        CrimeDatePicker.this.getArguments().putSerializable(
                                CrimePicker.dateHash,date);
                    }
                });
        /*A crime cant occur tommorow! (unless we are the criminal :O)*/
        datePicker.setMaxDate(new Date().getTime());
        /*Here we inject the DatePicker without inflated first , so lets mark it as #TODO :) */

        return new AlertDialog.Builder(this.getActivity())
                .setView(datePicker)                                //TODO , Make a .xml and inflated it!
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override

                    public void onClick(DialogInterface dialogInterface, int i) {
                        CrimeDatePicker.this.sendDate(CrimeFragment.REQUEST_NEW_TIME,CrimeTimePicker.RESPONCE_NEW_TIME);
                    }
                })
                .create();
    }

}
