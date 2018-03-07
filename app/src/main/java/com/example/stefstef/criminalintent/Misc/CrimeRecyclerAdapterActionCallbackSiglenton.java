package com.example.stefstef.criminalintent.Misc;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.stefstef.criminalintent.Models.CrimeLab;
import com.example.stefstef.criminalintent.R;

import java.util.ArrayList;
/*
 * Created by stefstef on 7/3/2018.
 */

public class CrimeRecyclerAdapterActionCallbackSiglenton implements android.support.v7.view.ActionMode.Callback {
    private static CrimeRecyclerAdapterActionCallbackSiglenton instance=null;
    private static String TAG="CrimeRecyclerAdapterActionCallbackSiglenton_LOG";
    private ArrayList<Integer>  selectedIndexes;
    private RecyclerView recyclerView;
    private boolean allowMultiChoice;
    private int menuLayoutID=-1;
    private Activity act;




    private CrimeRecyclerAdapterActionCallbackSiglenton(int menuLayoutID,
                                                        Activity act,
                                                        RecyclerView recyclerView){
        this.menuLayoutID=menuLayoutID;
        this.recyclerView=recyclerView;
        this.act =act;


        this.selectedIndexes=new ArrayList<>();
        this.selectedIndexes.ensureCapacity(CrimeLab.getInstance(this.act).getCrimes().size()/2);
    }

    public static CrimeRecyclerAdapterActionCallbackSiglenton getInstance(int menuLayoutID,
                                                                          Activity act,
                                                                          RecyclerView recyclerView){
        if(instance==null){
            instance=new CrimeRecyclerAdapterActionCallbackSiglenton(menuLayoutID,act,recyclerView);
        }
        return instance;
    }
    public void selectIndexAt(Integer index){
        if(this.allowMultiChoice){
            this.selectedIndexes.add(index);
            Log.i(TAG,"Item "+index+" Selected");
        }
    }

    @Override
    public boolean onCreateActionMode(android.support.v7.view.ActionMode mode, Menu menu) {
        this.allowMultiChoice=true;
        mode.getMenuInflater().inflate(this.menuLayoutID,menu);
        this.act.getActionBar().hide();
        return true;
    }
    @Override
    public boolean onPrepareActionMode(android.support.v7.view.ActionMode mode, Menu menu) {
        return false;
    }
    @Override
    public boolean onActionItemClicked(android.support.v7.view.ActionMode mode, MenuItem item) {
        switch(item.getItemId()){
            case R.id.deleteCrime:
                Snackbar.make(recyclerView,"Ok!",Snackbar.LENGTH_SHORT).show();
                mode.finish();
                return true;
            default:
                return false;
        }
    }
    @Override
    public void onDestroyActionMode(android.support.v7.view.ActionMode mode) {
        this.allowMultiChoice=false;
        this.act.getActionBar().show();

    }

}
