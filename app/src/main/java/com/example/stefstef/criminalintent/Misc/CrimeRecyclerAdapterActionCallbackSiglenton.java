package com.example.stefstef.criminalintent.Misc;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.example.stefstef.criminalintent.Models.Crime;

/**
 * Created by stefstef on 7/3/2018.
 */

public class CrimeRecyclerAdapterActionCallbackSiglenton implements android.support.v7.view.ActionMode.Callback {
    private int menuLayoutID=-1;
    private static CrimeRecyclerAdapterActionCallbackSiglenton instance=null;
    private CrimeRecyclerAdapterActionCallbackSiglenton(int menuLayoutID){this.menuLayoutID=menuLayoutID;}
    public static CrimeRecyclerAdapterActionCallbackSiglenton getInstance(int menuLayoutID){
        if(instance==null){
            instance=new CrimeRecyclerAdapterActionCallbackSiglenton(menuLayoutID);
        }
        return instance;
    }
    @Override
    public boolean onCreateActionMode(android.support.v7.view.ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(this.menuLayoutID,menu);
        return true;
    }
    @Override
    public boolean onPrepareActionMode(android.support.v7.view.ActionMode mode, Menu menu) {
        return false;
    }
    @Override
    public boolean onActionItemClicked(android.support.v7.view.ActionMode mode, MenuItem item) {
        return false;
    }
    @Override
    public void onDestroyActionMode(android.support.v7.view.ActionMode mode) {

    }
}
