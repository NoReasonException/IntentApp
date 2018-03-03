package com.example.stefstef.criminalintent;

import android.support.v7.widget.Toolbar;

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
