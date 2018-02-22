package com.example.stefstef.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.stefstef.criminalintent.Models.Crime;
import com.example.stefstef.criminalintent.Models.CrimeLab;

import java.text.DateFormat;
import java.util.UUID;

/**
 * Created by stefstef on 20/2/2018.
 */

public class CrimeFragment extends Fragment{
    public static java.lang.String TAG="CrimeFragmentLog";
    public static java.lang.String EXTRA_CRIME_UUID="Crime";
    public static final int        REQUEST_NEW_DATE=0;

    public static CrimeFragment getInstance(UUID id){
        CrimeFragment fr = new CrimeFragment();
        Bundle args = new Bundle();
        args.putSerializable(CrimeFragment.EXTRA_CRIME_UUID,id);
        fr.setArguments(args);
        return fr;
    }

    private Crime       crime;
    private EditText    title;
    private EditText details;
    private Switch      solvedSwitch;
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
        if(resultCode==CrimeDatePicker.RESPONCE_NEW_DATE){
            this.crime.setDate((java.util.Date)data.getSerializableExtra(CrimeDatePicker.DIALOG_DATE_TAG));
        }

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
        this.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(CrimeFragment.TAG,"Date pressed , CrimeDatePicked initialization...");
                CrimeDatePicker picker= CrimeDatePicker.getInstance(CrimeFragment.this.crime.getDate());
                picker.setTargetFragment(CrimeFragment.this,REQUEST_NEW_DATE);
                picker.show(CrimeFragment.this.getActivity().getSupportFragmentManager(),
                        CrimeDatePicker.DIALOG_DATE_TAG);

            }
        });
        this.solvedSwitch.setChecked(this.crime.isSolved());
    }

    /***
     * Initialzize listeners , to update the data inside the crime object
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
                Log.i(TAG,"afterTextCHanged");
                CrimeFragment.this.crime.setTitle(editable.toString());
            }
        });
    }

}
