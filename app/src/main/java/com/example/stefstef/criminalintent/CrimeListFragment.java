package com.example.stefstef.criminalintent;


import com.example.stefstef.criminalintent.Misc.CrimeArrayAdapter;
import com.example.stefstef.criminalintent.Models.CrimeLab;
import com.example.stefstef.criminalintent.Models.Crime;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;




/**
 * CrimeListFragment
 * Contains all the crimes , starts first , triggered by CrimeListActivity
 * (At first) and CrimePagerActivity in chapter 11
 */

    public class CrimeListFragment extends android.support.v4.app.ListFragment {
    public static java.lang.String TAG="CrimeListFragmentLog";
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.getActivity().setTitle(R.string.crime_title);
            ArrayAdapter<Crime> adapter=new CrimeArrayAdapter(this.getActivity(),this.getActivity(),
                CrimeLab.getInstance(this.getActivity()).getCrimes());
        this.setListAdapter(adapter);


    }
    /*This little hack will update the listView every time we return from
    * CrimeFragment*/
    @Override
    public void onResume() {
        super.onResume();
        this.getListView().invalidateViews();
    }
    /*
    * WARNING.
    * make the checkbox as -> focusable , false because this can never be run otherwise!*/
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
                             //-----------------\You can pass also CrimeActivity , is already set!
                            //                   \
        Intent i = new Intent(this.getActivity(),CrimePagerActivity.class);
        Log.i(CrimeListFragment.TAG,"CrimePagerActivity initialization ");
        i.putExtra(CrimeFragment.EXTRA_CRIME_UUID,CrimeLab.getInstance(this.getActivity()).getCrimes().get(position).getId());
        this.startActivity(i);
    }
}
