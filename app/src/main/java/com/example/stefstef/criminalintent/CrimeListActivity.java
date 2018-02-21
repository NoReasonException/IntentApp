package com.example.stefstef.criminalintent;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.ArrayAdapter;

/***
 * CrimeListActivity extends SingleFragmentActivity
 * Triggers a single Fragment , CrimeListFragment , witch is full of crimes! :P
 *
 * *This is the main Activity!
 * @Note : Updated for
 */
public class CrimeListActivity extends SingleFragmentActivity {


    /**
     * @return An fragment, an CrimeListFragment , witch displays all the crimes!
     */
    @Override
    protected android.support.v4.app.Fragment createFragment() {
        return new CrimeListFragment();
    }
}
