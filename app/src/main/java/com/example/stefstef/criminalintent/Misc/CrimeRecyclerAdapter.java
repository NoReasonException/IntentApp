package com.example.stefstef.criminalintent.Misc;

import android.app.Activity;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.stefstef.criminalintent.CrimeListFragment;
import com.example.stefstef.criminalintent.Models.Crime;
import com.example.stefstef.criminalintent.Models.CrimeLab;
import com.example.stefstef.criminalintent.R;

import java.text.DateFormat;

/**
 * Created by stefstef on 7/3/2018.
 */

public class CrimeRecyclerAdapter extends RecyclerView.Adapter<CrimeRecyclerAdapter.CrimeViewHolder> {
    private static String TAG="CrimeRecyclerAdapterActionCallbackSiglenton_LOG";

    private CrimeRecyclerAdapterActionCallbackSiglenton callbackSiglenton;

    private RecyclerView recyclerView;
    private Activity act;
    private android.support.v4.app.Fragment fr;
    public static class CrimeViewHolder extends RecyclerView.ViewHolder{
        View v;
        public CrimeViewHolder(View itemView) {
            super(itemView);
            this.v=itemView;
        }
    }

    public CrimeRecyclerAdapter(android.support.v4.app.Fragment fr, RecyclerView recyclerView) {
        this.callbackSiglenton=CrimeRecyclerAdapterActionCallbackSiglenton.getInstance(R.menu.menu_contextual,fr.getActivity(),recyclerView);
        this.recyclerView=recyclerView;
        this.act = fr.getActivity();
        this.fr=fr;

    }

    @Override
    public CrimeRecyclerAdapter.CrimeViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        return new CrimeRecyclerAdapter.CrimeViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_crime,parent,false));

    }
    @Override
    public void onBindViewHolder(CrimeViewHolder holder, int position) {
        Crime m=CrimeLab.getInstance(CrimeRecyclerAdapter.this.act).getCrimes().get(position);
        ((TextView)holder.v.findViewById(R.id.ctitle)).setText(m.getTitle());
        ((TextView)holder.v.findViewById(R.id.cdate)).setText(DateFormat.getDateInstance().format(
            m.getDate()));
        ((CheckBox)holder.v.findViewById(R.id.check)).setChecked(m.isSolved());
        this.onBindContextualActionBar(holder);
    }

    /***
     * Activates the CAB for multiple choice (Same as CHOICE_MODE_MULTIPLE_MODAL on listviews!)
     */
    private void onBindContextualActionBar(CrimeViewHolder holder){
        holder.v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ((AppCompatActivity)view.getContext()).startSupportActionMode(callbackSiglenton);
                callbackSiglenton.selectIndexAt(recyclerView.getChildAdapterPosition(view));
                return true;
            }
        });
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!callbackSiglenton.selectIndexAt(recyclerView.getChildAdapterPosition(view))){
                    Log.i(CrimeRecyclerAdapter.TAG,  "Jump to CrimeFragment!");
                    ((CrimeListFragment)fr).JumpToCrimePagerFragment(
                            ((Crime)CrimeLab.getInstance(act).getCrimes().get(
                                    recyclerView.getChildAdapterPosition(view)
                            )).getId()
                    );
                }
            }
        });
    }

    @Override
    public int getItemCount(){
        return CrimeLab.getInstance(this.act).getCrimes().size();
    }
}

