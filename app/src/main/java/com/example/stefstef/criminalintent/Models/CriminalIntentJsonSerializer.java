package com.example.stefstef.criminalintent.Models;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOError;
import java.io.IOException;
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
}
