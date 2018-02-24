package com.example.stefstef.criminalintent;

import com.example.stefstef.criminalintent.Models.Crime;
import com.example.stefstef.criminalintent.Models.CrimeLab;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.CompoundButton;
import android.view.LayoutInflater;
import android.text.TextWatcher;
import android.widget.EditText;
import android.content.Intent;
import android.view.ViewGroup;
import android.text.Editable;
import android.widget.Switch;
import java.text.DateFormat;
import android.view.View;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class CrimeFragment extends Fragment{
    //-------------------------------Public Section------------------------------------------------//
    public static java.lang.String  TAG="CrimeFragmentLog";  //LogTag
    public static java.lang.String  EXTRA_CRIME_UUID="Crime";//To pass the crime ID in intent..
    public static java.lang.String  DIALOG_DATE_TAG="criminalintent";
    public static final int         REQUEST_NEW_DATE=1;      //The Request Code for CrimeDatePicker
    public static final int         REQUEST_NEW_TIME=2;      //The Request Code for CrimeDatePicker
    //--------------------------------Private Section----------------------------------------------//
    private Crime       crime;
    private EditText    title;
    private EditText    details;
    private Switch      solvedSwitch;

    /***
     * TODO , why dont pass the whole Crime Object dude?
     * @param id , the ID of the crime to display
     * @return  A initialized CrimeFragment!
     */
    public static CrimeFragment getInstance(UUID id){
        CrimeFragment fr = new CrimeFragment();
        Bundle args = new Bundle();
        args.putSerializable(CrimeFragment.EXTRA_CRIME_UUID,id);
        fr.setArguments(args);
        return fr;
    }

    /**
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID id=(UUID)getArguments().getSerializable(CrimeFragment.EXTRA_CRIME_UUID);
        this.crime=CrimeLab.getInstance(this.getActivity()).getCrimeById(id);


    }

    /***
     * Return the fragments View
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_fragment,container,false);
        this.initializeReferences(v);
        this.updateView(v);
        this.initializeListeners();
        return v;

    }

    /**
     * Receive the result from a previous call to
     * {@link #startActivityForResult(Intent, int)}.  This follows the
     * related Activity API as described there in
     * {@link Activity#onActivityResult(int, int, Intent)}.
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==CrimeDatePicker.RESPONCE_NEW_DATE || resultCode==CrimeTimePicker.RESPONCE_NEW_TIME){
            this.crime.setDate((java.util.Date)data.getSerializableExtra(CrimeFragment.DIALOG_DATE_TAG));
            Log.i(CrimeFragment.TAG,this.crime.getDate().toString());
        }
        Log.i(CrimeFragment.TAG,"onActivityResult triggered");
        this.updateView(this.getView());
    }

    /***
     * Initialize the class references , the view cant be null!
     *
     * @param v The view to take references
     */
    private void initializeReferences(@NonNull View v){
        this.title=v.findViewById(R.id.Title);
        this.details=v.findViewById(R.id.Details);

        this.solvedSwitch=v.findViewById(R.id.Solved);
    }

    /***
     * Updates the view with the this.crime Data
     *
     * @param v , the view to update
     */
    private void updateView(@NonNull View v){
        this.title.setText(this.crime.getTitle());
        this.details.setText(DateFormat.getInstance().format(this.crime.getDate()));

        this.solvedSwitch.setChecked(this.crime.isSolved());
    }

    /***
     * Initialzize the views Listeners
     * @Note , must by called first updateView(View v)
     */
    private void initializeListeners(){
        this.solvedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                CrimeFragment.this.crime.setSolved(b);
            }
        });
        this.title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                return;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                return;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                CrimeFragment.this.crime.setTitle(editable.toString());
            }
        });
        this.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(CrimeFragment.TAG,"Date pressed , CrimeDatePicked initialization...");
                /*CrimePicker picker= CrimeDatePicker.getInstance(CrimeFragment.this.crime.getDate(),
                        CrimeFragment.this,
                        CrimeFragment.REQUEST_NEW_DATE);*/
                DialogFragment picker=CrimePickerPagerDialog.getInstance(new ArrayList<Class<? extends CrimePicker>>(){{
                    this.add(CrimeDatePicker.class);
                    this.add(CrimeTimePicker.class);
                }});
                picker.show(CrimeFragment.this.getActivity().getSupportFragmentManager(),
                        CrimeFragment.DIALOG_DATE_TAG);

            }
        });
    }

}
