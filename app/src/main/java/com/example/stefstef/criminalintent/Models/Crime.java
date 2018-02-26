package com.example.stefstef.criminalintent.Models;

import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

/**
 * Created by stefstef on 20/2/2018.
 */

public class Crime {

    private UUID    mId;
    private String  mTitle;
    private Date    mDate;
    private boolean misSolved;

    private static String TAG="Crime_LOG";
    private static String JSON_ID="ID";
    private static String JSON_TITLE="TITLE";
    private static String JSON_DATE="DATE";
    private static String JSON_SOLVED="SOLVED";
    public Crime(){
        this.mId=UUID.randomUUID();
        this.mDate=new Date();
    }

    public Date getDate() {
        return mDate;
    }

    public Crime setDate(Date mDate) {
        this.mDate = mDate;
        return this;
    }

    public boolean isSolved() {
        return misSolved;
    }

    public Crime setSolved(boolean misSolved) {
        this.misSolved = misSolved;
        return this;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {

        return mTitle;
    }

    public Crime setTitle(String mTitle) {
        this.mTitle = mTitle;
        return this;
    }

    @Nullable
    public JSONObject toJSON(){
        try{
            return new JSONObject().put(Crime.JSON_ID,this.getId())
                    .put(Crime.JSON_TITLE,this.getTitle()
                    )
                    .put(Crime.JSON_DATE,this.getDate())
                    .put(Crime.JSON_SOLVED,this.isSolved());
        }catch (JSONException e){
            return null;
        }
    }

    @Override
    public String toString() {
        return this.mTitle;
    }
    public Crime logMe(){
        Log.i(Crime.TAG,this.toJSON().toString());
        return this;
    }
}
