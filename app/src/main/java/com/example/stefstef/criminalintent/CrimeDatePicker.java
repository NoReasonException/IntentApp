package com.example.stefstef.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by stefstef on 22/2/2018.
 */

public class CrimeDatePicker extends DialogFragment {
    public static java.lang.String DIALOG_DATE_TAG="date";
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        return new AlertDialog.Builder(this.getActivity())
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(R.string.ok,null)
                .create();
    }
}
