package com.example.stefstef.criminalintent;

import java.security.InvalidParameterException;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.content.DialogInterface;
import java.util.GregorianCalendar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManagerNonConfig;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.app.AlertDialog;
import android.app.Dialog;
import java.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
                                              Integer requestCode){
        return CrimePicker.getInstance(param,targetFragment,requestCode,new CrimeDatePicker());
    }
    @Override
    public View onCreateView(LayoutInflater infl, ViewGroup vg, Bundle savedInstanceState){
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
        return datePicker;
    }

    /**
     * Override to build your own custom Dialog container.  This is typically
     * used to show an AlertDialog instead of a generic Dialog; when doing so,
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} does not need
     * to be implemented since the AlertDialog takes care of its own content.
     * <p>
     * <p>This method will be called after {@link #onCreate(Bundle)} and
     * before {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.  The
     * default implementation simply instantiates and returns a {@link Dialog}
     * class.
     * <p>
     * <p><em>Note: DialogFragment own the {@link Dialog#setOnCancelListener
     * Dialog.setOnCancelListener} and {@link Dialog#setOnDismissListener
     * Dialog.setOnDismissListener} callbacks.  You must not set them yourself.</em>
     * To find out about these events, override {@link #onCancel(DialogInterface)}
     * and {@link #onDismiss(DialogInterface)}.</p>
     *
     * @param savedInstanceState The last saved instance state of the Fragment,
     *                           or null if this is a freshly created Fragment.
     * @return Return a new Dialog instance to be displayed by the Fragment.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(this.getActivity())
                .setPositiveButton("OK",null).create();
    }
}
