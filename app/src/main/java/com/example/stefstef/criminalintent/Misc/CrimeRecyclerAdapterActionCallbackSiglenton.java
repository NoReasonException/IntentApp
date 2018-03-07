package com.example.stefstef.criminalintent.Misc;

import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by stefstef on 7/3/2018.
 */

public class CrimeRecyclerAdapterActionCallbackSiglenton implements android.support.v7.view.ActionMode.Callback {
    private int menuLayoutID=-1;
    private Activity act;
    private static CrimeRecyclerAdapterActionCallbackSiglenton instance=null;
    private CrimeRecyclerAdapterActionCallbackSiglenton(int menuLayoutID,
                                                        Activity act){
        this.menuLayoutID=menuLayoutID;
        this.act =act;
    }
    public static CrimeRecyclerAdapterActionCallbackSiglenton getInstance(int menuLayoutID,
                                                                          Activity act){
        if(instance==null){
            instance=new CrimeRecyclerAdapterActionCallbackSiglenton(menuLayoutID,act);
        }
        return instance;
    }
    @Override
    public boolean onCreateActionMode(android.support.v7.view.ActionMode mode, Menu menu) {
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
        return false;
    }
    @Override
    public void onDestroyActionMode(android.support.v7.view.ActionMode mode) {
        this.act.getActionBar().show();

    }
}
