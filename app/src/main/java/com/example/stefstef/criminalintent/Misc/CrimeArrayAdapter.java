package com.example.stefstef.criminalintent.Misc;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;

import com.example.stefstef.criminalintent.Models.Crime;
import com.example.stefstef.criminalintent.Models.CrimeLab;
import com.example.stefstef.criminalintent.R;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by stefstef on 20/2/2018.
 * CrimeArrayAdapter extends ArrayAdapter
 * Contains Crimes
 *
 * This class is Adapter in CrimeListFragment , its renders the list_item_crime , an
 * custom view in list_item_crime.xml
 */

public class CrimeArrayAdapter extends ArrayAdapter<Crime> {
    public CrimeArrayAdapter(Activity parentActivity, @NonNull Context context, ArrayList<Crime> data) {
        super(context, 0,data);
    }

    /**
     * getView(int,View,ViewGroup)
     * @param position      The position of View in arrayList
     * @param convertView   An used object for re-use ,may be null
     * @param parent        The parent ViewGroup
     * This method returns the appropiate view in @param position ,
     * @Note : maybe convertView is null , because convertView is used to
     * re-use objects already in memory!
     *
     * */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView=View.inflate(this.getContext(), R.layout.list_item_crime,null);
        }
        return CrimeArrayAdapter.updateCrimeView(convertView,position);
    }

    /***
     * Updates a view with new crime data!
     * @param v         The View (Must not be null)
     * @param position  A valid position
     * @return          A Updated view
     */
    @NonNull
    public static View updateCrimeView(@NonNull View v,int position) throws ArrayIndexOutOfBoundsException{
        if(v.findViewById(R.id.check)==null)return v;

        ((CheckBox)v.findViewById(R.id.check)).
                setChecked(CrimeLab.getInstance(v.getContext()).getCrimes().get(position).isSolved());
        ((TextView)v.findViewById(R.id.ctitle)).
                setText(CrimeLab.getInstance(v.getContext()).getCrimes().get(position).getTitle());
        ((TextView)v.findViewById(R.id.cdate)).
                setText(DateFormat.getDateInstance().format(CrimeLab.getInstance(
                        v.getContext()).getCrimes().get(position).getDate()));
        return v;
    }
}
