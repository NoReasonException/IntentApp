package com.example.stefstef.criminalintent;


import com.example.stefstef.criminalintent.Misc.CrimeArrayAdapter;
import com.example.stefstef.criminalintent.Misc.Utills;
import com.example.stefstef.criminalintent.Models.CrimeLab;
import com.example.stefstef.criminalintent.Models.Crime;

import android.os.Build;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
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
    public static java.lang.String TAG="CrimeListFragment_Log";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.getActivity().setTitle(R.string.crime_title);
            ArrayAdapter<Crime> adapter=new CrimeArrayAdapter(this.getActivity(),this.getActivity(),
                CrimeLab.getInstance(this.getActivity()).getCrimes());
        this.setListAdapter(adapter);

        //this.registerForContextMenu(this.getListView());

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,Bundle onSaveInstanceState){
        View v = super.onCreateView(inflater,viewGroup,onSaveInstanceState);
        ListView listView=(ListView)v.findViewById(android.R.id.list);
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.HONEYCOMB){
            this.registerForContextMenu(listView); //every view will react to long-press
        }else {
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                @Override
                public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

                }

                @Override
                public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                    actionMode.getMenuInflater().inflate(R.menu.menu_contextual,menu);
                    CrimeListFragment.this.getActivity().findViewById(R.id.my_toolbar).setVisibility(View.INVISIBLE);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                    switch(menuItem.getItemId()){
                        case R.id.deleteCrime:
                            CrimeArrayAdapter adapter = (CrimeArrayAdapter) getListAdapter();
                            CrimeLab crimeLab=CrimeLab.getInstance(getActivity());
                            for (int i= adapter.getCount()-1;i>=0; i--) {
                                if(getListView().isItemChecked(i)){
                                    crimeLab.getCrimes().remove(adapter.getItem(i));
                                }
                            }
                            actionMode.finish();
                            adapter.notifyDataSetChanged();
                            return true;
                        default:
                            return false;
                    }
                }
                @Override
                public void onDestroyActionMode(ActionMode actionMode) {
                    CrimeListFragment.this.getActivity().findViewById(R.id.my_toolbar).setVisibility(View.VISIBLE );

                }
            });
        }
        return v;
    }
    /*This little hack will update the listView every time we return from
    * CrimeFragment*/
    @Override
    public void onResume() {
        super.onResume();
        this.getListView().invalidateViews();
        CrimeLab.getInstance(this.getActivity()).updateCrimes();
    }
    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.getInstance(this.getActivity()).updateCrimes();
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


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo info){
        this.getActivity().getMenuInflater().inflate(R.menu.menu_contextual,menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem i){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)i.getMenuInfo();

         CrimeLab.getInstance(this.getActivity()).getCrimes().remove(getListAdapter().getItem(info.position));

        this.getListView().invalidateViews();
        return true;
    }
}
