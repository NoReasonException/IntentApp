package com.example.stefstef.criminalintent.Misc;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.stefstef.criminalintent.Models.Crime;
import com.example.stefstef.criminalintent.Models.CrimeLab;
import com.example.stefstef.criminalintent.R;

import java.text.DateFormat;

/**
 * Created by stefstef on 7/3/2018.
 */

public class CrimeRecyclerAdapter extends RecyclerView.Adapter<CrimeRecyclerAdapter.CrimeViewHolder> {
    private CrimeRecyclerAdapterActionCallbackSiglenton callbackSiglenton;
    private RecyclerView recyclerView;
    private Activity act;
    public static class CrimeViewHolder extends RecyclerView.ViewHolder{
        View v;
        public CrimeViewHolder(View itemView) {
            super(itemView);
            this.v=itemView;
        }
    }

    public CrimeRecyclerAdapter(Activity act,RecyclerView recyclerView) {
        this.callbackSiglenton=CrimeRecyclerAdapterActionCallbackSiglenton.getInstance(R.menu.menu_contextual,act,recyclerView);
        this.recyclerView=recyclerView;
        this.act = act;

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
    }

    @Override
    public int getItemCount(){
        return CrimeLab.getInstance(this.act).getCrimes().size();
    }
}

