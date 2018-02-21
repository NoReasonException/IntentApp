package com.example.stefstef.criminalintent;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;


/***
 * CrimeActivity extends SingleFragmentActivity
 * Just a Activity triggers a single Fragment , the CrimeFragment!
 *
 */
public class CrimeActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return CrimeFragment.getInstance((UUID)this.getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_UUID));
    }
}
