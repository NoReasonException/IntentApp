package com.example.stefstef.criminalintent.Models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by stefstef on 20/2/2018.
 */

public class CrimeLab {
    private static CrimeLab ourInstance=null;
    private ArrayList<Crime>    crimes  =new ArrayList<>();
    private Context             context =null;
    @NonNull
    public static CrimeLab getInstance(Context c) {
        if(CrimeLab.ourInstance==null){
            CrimeLab.ourInstance=new CrimeLab(c.getApplicationContext());
            CrimeLab.ourInstance.foolInitializer();
        }
        return ourInstance;
    }

    public ArrayList<Crime> getCrimes() {
        return crimes;
    }
    @Nullable
    public Crime getCrimeById(UUID id){
        for(Crime c:this.getCrimes()){
            if(c.getId().equals(id))return c;
        }
        return null;
    }
    @NonNull
    public Integer getIndexByID(UUID id){
        for (int i = 0; i < this.getCrimes().size(); i++) {
            if(id.equals(this.getCrimes().get(i).getId())){
                return i;
            }
        }
        return 0;
    }
    public void foolInitializer(){
        for (int i = 0; i < 100 ;i++) {
            this.getCrimes().add(new Crime().
                    setTitle("Crime #"+i).
                    setSolved(i%2==0));
        }
    }
    private CrimeLab(Context c) {
        this.context=c;
        this.crimes=new ArrayList<Crime>();
    }
}
