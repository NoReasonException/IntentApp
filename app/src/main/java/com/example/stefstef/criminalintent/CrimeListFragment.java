package com.example.stefstef.criminalintent;

import com.example.stefstef.criminalintent.Misc.CrimeRecyclerAdapter;
import com.example.stefstef.criminalintent.Models.CrimeLab;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.view.View;


/**
 * CrimeListFragment
 * @Note : on refactor stage
 * Contains all the crimes , starts first , triggered by CrimeListActivity
 * (At first) and CrimePagerActivity in chapter 11
 */

    public class CrimeListFragment extends android.support.v4.app.Fragment {
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




    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,Bundle onSaveInstanceState) {

        this.view = inflater.inflate(R.layout.crime_list_fragment, viewGroup, false);
        this.initializeReferences();

        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        this.recyclerView.setAdapter(new CrimeRecyclerAdapter(getActivity(), this.recyclerView));

        this.getActivity().setActionBar((android.widget.Toolbar) this.view.findViewById(R.id.new_action_bar));
        this.initializeListeners();
        return this.view;

    }
    /*This little hack will update the listView every time we return from
        * CrimeFragment*/
    @Override
    public void onResume() {
        super.onResume();
        this.recyclerView.getAdapter().notifyDataSetChanged();
        CrimeLab.getInstance(this.getActivity()).updateCrimes();
    }
    @Override
    public void onPause() {
        super.onPause();
        this.recyclerView.getAdapter().notifyDataSetChanged();
        CrimeLab.getInstance(this.getActivity()).updateCrimes();
    }
}
