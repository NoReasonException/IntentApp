package com.example.stefstef.criminalintent.Misc;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.stefstef.criminalintent.CrimeListFragment;
import com.example.stefstef.criminalintent.Models.Crime;
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


    /***
     * CallBacks to ActionMode Siglenton
     * @param menuLayoutID the menu to render
     * @param act          Parent Activity
     * @param recyclerView The RecuclerView who adapter belongs
     */
    private CrimeRecyclerAdapterActionCallbackSiglenton(int menuLayoutID,
                                                        Activity act,
                                                        RecyclerView recyclerView){
        this.menuLayoutID=menuLayoutID;
        this.recyclerView=recyclerView;
        this.act =act;

        this.selectedIndexes=new ArrayList<>();
        //Optimisation //
        this.selectedIndexes.ensureCapacity(CrimeLab.getInstance(this.act).getCrimes().size()/2);
    }

    /***
     *
     * @param menuLayoutID  the menu to render
     * @param act           The parant Activity
     * @param recyclerView  The RecyclerView
     * @return              A single instance of callbacks to activate ActionMode
     */
    public static CrimeRecyclerAdapterActionCallbackSiglenton getInstance(int menuLayoutID,
                                                                          Activity act,
                                                                          RecyclerView recyclerView){
        if(instance==null){
            instance=new CrimeRecyclerAdapterActionCallbackSiglenton(menuLayoutID,act,recyclerView);
        }
        return instance;
    }

    /***
     *
     * @param index index to select (or deselect)
     * @return      true if the actionMode is activated , false otherwise
     * @Note        if the actionMode is deactivated , then a click must
     *                      Redirect the user to CrimePagerActivity
     */
    public boolean selectIndexAt(Integer index){
        if(this.allowMultiChoice){
            if(this.selectedIndexes.contains(index)){ //already selected , deselect
                this.onItemCheckedStateChanged(index,false);
                this.selectedIndexes.remove(index);
            }
            else{ //non selected! add to selected arrayList
                this.onItemCheckedStateChanged(index,true);
                this.selectedIndexes.add(index);
            }
            return true; // multichoice is activated
        }
        return false; //multichoice is not activated , is a simple view onClick , handle it
    }


    /***
     * This method tries to mimic the classic ListViews , MultiChoiceModeListener.onItemCheckedState
     * It is called when a view has changed status (selected or deselect)
     * @param i , the index of event
     * @param b , it is selected or deselected?
     */
    public void onItemCheckedStateChanged(int i, boolean b) {
        if(b)
            this.recyclerView.getChildAt(i).animate().translationXBy(50f).setDuration(300).start();
        else
            this.recyclerView.getChildAt(i).animate().translationX(0f).setDuration(300).start();


        Log.i(TAG,"select "+i+" state "+b);
    }

    /***
     * Calls when a ActionMode created
     * @param mode  The ActionMode object
     * @param menu  The menu to inflate
     * @return true if it is created successfully!
     */
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

    /***
     * Called when the action is clicked!
     * @param mode
     * @param item
     * @return
     */
    @Override
    public boolean onActionItemClicked(android.support.v7.view.ActionMode mode, MenuItem item) {
        switch(item.getItemId()){
            case R.id.deleteCrime:
                ArrayList<Crime> deletedCrimes=new ArrayList<>();
                for (Integer i :this.selectedIndexes) {
                    deletedCrimes.add(CrimeLab.getInstance(this.act).getCrimes().get(i));
                }
                for (Crime cr:deletedCrimes) {
                    CrimeLab.getInstance(this.act).getCrimes().remove(cr);
                }
                /*Set the undo Snackbar!*/
                Snackbar.make(recyclerView, String.format("You deleted %d %s",
                        this.selectedIndexes.size(),
                        this.selectedIndexes.size()>1?"crimes":"crime"),Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            ArrayList<Crime> restoreCrimes;
                            @Override
                            public void onClick(View view) {
                                for (Crime crime:this.restoreCrimes) {
                                    CrimeLab.getInstance(act).getCrimes().add(crime);
                                }
                                recyclerView.getAdapter().notifyDataSetChanged();
                            }
                            public View.OnClickListener init(ArrayList<Crime> cr){
                                this.restoreCrimes=cr;
                                return this;
                            }

                        }.init(deletedCrimes)
                ).show();


                this.recyclerView.getAdapter().notifyDataSetChanged();
                mode.finish();
                return true;
            default:
                return false;
        }
    }

    /***
     * Called when the ActionMode is about to terminate itself
     * @param mode
     */
    @Override
    public void onDestroyActionMode(android.support.v7.view.ActionMode mode) {
        this.selectedIndexes.clear();   //clear selected items
        this.restoreViews();            //restore animated views
        this.allowMultiChoice=false;    //deactivate choice mode(click will react differently)
        this.act.getActionBar().show(); //show the Appbar Again!

    }

    /***
     * restores the views back in ther initial position (undo whatever onItemCheckedStateChanged does!)
     */
    private void restoreViews(){
        View tmp;
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            if((tmp=recyclerView.getChildAt(i))!=null) { // if is visible
                tmp.animate().translationX(0f).setDuration(300).start();
            }
        }
    }

}
