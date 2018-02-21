package com.example.stefstef.criminalintent.Models;

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

    @Override
    public String toString() {
        return this.mTitle;
    }
}
