package com.example.stefstef.criminalintent;


import com.example.stefstef.criminalintent.Misc.CrimeArrayAdapter;
import com.example.stefstef.criminalintent.Misc.CrimeRecyclerAdapter;
import com.example.stefstef.criminalintent.Misc.Utills;
import com.example.stefstef.criminalintent.Models.CrimeLab;
import com.example.stefstef.criminalintent.Models.Crime;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import java.util.ArrayList;


/**
 * CrimeListFragment
 * Contains all the crimes , starts first , triggered by CrimeListActivity
 * (At first) and CrimePagerActivity in chapter 11
 */

    public class CrimeListFragment extends android.support.v4.app.ListFragment {
    public static java.lang.String TAG="CrimeListFragment_Log";
    private View view ;
    private RecyclerView recyclerView;


    public void initializeListeners(){
        this.view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrimeListFragment.this.getActivity().onOptionsItemSelected(null);
            }
        });
    }
    private void initializeReferences(){
        this.recyclerView=this.view.findViewById(R.id.recycler_list_view);
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.getActivity().setTitle(R.string.crime_title);
            ArrayAdapter<Crime> adapter=new CrimeArrayAdapter(this.getActivity(),this.getActivity(),
                CrimeLab.getInstance(this.getActivity()).getCrimes());
        this.setListAdapter(adapter);




    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,Bundle onSaveInstanceState){

        this.view= inflater.inflate(R.layout.crime_list_fragment,viewGroup,false);
        this.initializeReferences();

        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL,false));
        this.recyclerView.setAdapter(new CrimeRecyclerAdapter(getContext()));

        this.getActivity().setActionBar((android.widget.Toolbar) this.view.findViewById(R.id.new_action_bar));
        this.initializeListeners();





        ListView listView=(ListView)this.view.findViewById(android.R.id.list);
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP){
            this.registerForContextMenu(listView); //every view will react to long-press
        }else {
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                @Override
                public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

                    if(b){
                        getListView().getChildAt(i).animate().translationXBy(50).setDuration(300).start();
                        return;
                    }
                    getListView().getChildAt(i).animate().translationX(0).setDuration(300).start();

                }

                @Override
                public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                    actionMode.getMenuInflater().inflate(R.menu.menu_contextual,menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                    return false;
                }
                private void restoreViews(){

                    for (int i = 0; i < getListView().getLastVisiblePosition()-getListView().getFirstVisiblePosition(); i++) {
                        Log.i(TAG,"restore "+i+CrimeListFragment.this.getListView().getChildAt(i));

                        CrimeListFragment.this.getListView().getChildAt(i).animate().translationX(0f).start();
                    }
                }
                @Override
                public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {

                    switch(menuItem.getItemId()){
                        case R.id.deleteCrime:
                            int j=0;
                            ArrayList<Crime>deletedCrimes=new ArrayList<>();

                            Log.i(CrimeListFragment.TAG,"DELETE PUSHED");
                            final CrimeArrayAdapter adapter = (CrimeArrayAdapter) getListAdapter();
                            CrimeLab crimeLab=CrimeLab.getInstance(getActivity());
                                for (int i= adapter.getCount()-1;i>=0; i--) {
                                    if (getListView().isItemChecked(i)) {
                                        j+=1;
                                        deletedCrimes.add(adapter.getItem(i));
                                        crimeLab.getCrimes().remove(adapter.getItem(i));

                                    }
                                }
    
                            Snackbar.make(CrimeListFragment.this.getView(), String.format("You delete %d %s!", j,j>1?"crimes":"crime"),Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener() {
                                ArrayList<Crime>restoreCrimes=null;
                                @Override
                                public void onClick(View view) {
                                    CrimeLab.getInstance(getContext()).getCrimes().addAll(this.restoreCrimes);
                                    adapter.notifyDataSetChanged();
                                }
                                public View.OnClickListener init(ArrayList<Crime> undo){
                                    this.restoreCrimes=undo;
                                    return this;
                                }

                            }.init(deletedCrimes)).show();
                            actionMode.finish();
                            adapter.notifyDataSetChanged();
                            return true;
                        default:
                            return false;
                    }
                }

                @Override
                public void onDestroyActionMode(ActionMode actionMode) {
                    restoreViews();

                }
            });
        }
        return this.view;
    }

    /**
     * Get the fragment's list view widget.
     */
    @Override
    public ListView getListView() {
        return this.view.findViewById(android.R.id.list);
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
