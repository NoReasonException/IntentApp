package com.example.stefstef.criminalintent;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

/**
 * Abstract Single Fragment Activity
 * Override the createFragment and return your fragment , this will take care
 * all the rest
 */

public abstract class SingleFragmentActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
