package com.example.stefstef.criminalintent;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.stefstef.criminalintent.Models.Crime;
import com.example.stefstef.criminalintent.Models.CrimeLab;

import java.util.Date;

/**
 * Abstract Single Fragment Activity
 * Override the createFragment and return your fragment , this will take care
 * all the rest
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initialization();
        FragmentManager fr = getSupportFragmentManager();
        android.support.v4.app.Fragment fragment = fr.findFragmentById(R.id.FragmentContainer);
        if(fragment==null){
            fragment=this.createFragment();
            fr.beginTransaction()
                    .add(R.id.FragmentContainer,fragment)
                    .commit();
        }

    }

    protected abstract android.support.v4.app.Fragment createFragment();

    protected void initialization(){}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.main_actionbar,menu);
        return true;
    }

}
