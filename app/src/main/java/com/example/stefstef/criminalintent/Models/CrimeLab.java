package com.example.stefstef.criminalintent.Models;

import android.content.Context;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by stefstef on 20/2/2018.
 */

public class CrimeLab {
    private static          CrimeLab                        ourInstance=null;
    private static final    String                          TAG="CrimeLab_LOG";
    private                 ArrayList<Crime>                crimes  =new ArrayList<>();
    private                 Context                         context =null;
    private                 CriminalIntentJsonSerializer    serializer;
    private static String                                   FILENAME="crimes.json";
    @NonNull
    public static CrimeLab getInstance(Context c) {
        if(CrimeLab.ourInstance==null){
            CrimeLab.ourInstance=new CrimeLab(c.getApplicationContext());
            CrimeLab.ourInstance.context=c;
            CrimeLab.ourInstance.serializer=new CriminalIntentJsonSerializer(
                    ourInstance.context,FILENAME);
            try{
                CrimeLab.ourInstance.getCrimes().addAll(
                        CrimeLab.getInstance(c).getSerializer().loadCrimes()
                );
                Log.i(CrimeLab.TAG, String.format("%d crimes found",CrimeLab.ourInstance.getCrimes().size()));

            }catch (Exception e){
                Log.i(CrimeLab.TAG,"no crimes found!");
            }
        }
        return ourInstance;
    }

    public boolean updateCrimes(){
        try{
            this.serializer.saveCrimes(this.getCrimes());
        }catch (JSONException|IOException e){
            return false;
        }
        return true;
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

    public CriminalIntentJsonSerializer getSerializer() {
        return serializer;
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
        this.crimes=new ArrayList<>();
    }
}
