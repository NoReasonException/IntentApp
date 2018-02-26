package com.example.stefstef.criminalintent.Models;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by stefstef on 26/2/2018.
 */

public class CriminalIntentJsonSerializer {
    private Context context;
    private String  filename;

    public CriminalIntentJsonSerializer(Context context, String filename) {
        this.context = context;
        this.filename = filename;
    }
    public void saveCrimes(ArrayList<Crime> crimeArrayList) throws JSONException,IOException{
        JSONArray arr = new JSONArray();
        for(Crime c:crimeArrayList) arr.put(c.logMe().toJSON());
        Writer writer = null;
        try{
            writer=new OutputStreamWriter(this.context.openFileOutput(this.filename,
                    Context.MODE_PRIVATE));
            writer.write(arr.toString());

        }finally {
            if(writer!=null)writer.close();
        }
    }
    public ArrayList<Crime> loadCrimes() throws JSONException,IOException{
        ArrayList<Crime> crimes = new ArrayList<>();
        BufferedReader reader=null;
        try{
            InputStream in = this.context.openFileInput(this.filename);
            reader=new BufferedReader(new InputStreamReader(in));
            StringBuilder json=new StringBuilder();
            String line=null;
            while((line=reader.readLine())!=null){
                json.append(line);
            }
            JSONArray arr=(JSONArray)new JSONTokener(json.toString()).nextValue();
            for (int i = 0; i < arr.length(); i++) {
                crimes.add(new Crime(arr.getJSONObject(i)));
            }


        }catch (Exception e){

        }finally {
            if(reader!=null)reader.close();
        }
        return crimes;
    }
}
