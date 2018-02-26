package com.example.stefstef.criminalintent.Models;

import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;
import java.util.jar.JarEntry;

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
    public Crime(JSONObject o)throws JSONException{
        if(o.has(Crime.JSON_ID))this.mId=UUID.fromString(o.getString(Crime.JSON_ID));
        if(o.has(Crime.JSON_TITLE))this.mTitle=o.getString(Crime.JSON_TITLE);
        if(o.has(Crime.JSON_DATE))this.mDate=new Date(o.getLong(Crime.JSON_DATE));
        if(o.has(Crime.JSON_SOLVED))this.misSolved=o.getBoolean(Crime.JSON_SOLVED);
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
                    .put(Crime.JSON_DATE,this.getDate().getTime())
                    .put(Crime.JSON_SOLVED,this.isSolved());
        }catch (JSONException e){
            return null;
        }
    }

    @Override
    public String toString() {
        return this.toJSON().toString();
    }
    public Crime logMe(){
        Log.i(Crime.TAG,this.toJSON().toString());
        return this;
    }
}
