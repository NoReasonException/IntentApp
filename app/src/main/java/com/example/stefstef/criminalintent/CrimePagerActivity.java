package com.example.stefstef.criminalintent;

import com.example.stefstef.criminalintent.Models.CrimeLab;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import java.util.UUID;

/**
 * Created by stefstef on 21/2/2018.
 */

public class CrimePagerActivity extends FragmentActivity {

    private ViewPager pager ;
    /**
     * Perform initialization of all fragments and loaders.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Because of simplicity , we inject an ViewPager directy instead of
        //Inflate a .xml file
        this.pager=new ViewPager(this);
        this.pager.setId(R.id.crimeViewPager);
        setContentView(this.pager);


        FragmentManager fm =this.getSupportFragmentManager();

        /**
         * We Set an FragmentStatePageAdapter as adapter ,
         * why not FragmentPageAdapter?Efficiency is the key!
         * @See FragmentStatePagerAdapter vs. FragmentPagerAdapter(Page 208)
         * */
        this.pager.setAdapter(new FragmentStatePagerAdapter(fm) {
            /***
             *
             * @param position  The position to render
             * @return          a fragment on position given
             */
            @Override
            public Fragment getItem(int position) {
                return CrimeFragment.getInstance(CrimeLab.getInstance(CrimePagerActivity.this)
                .getCrimes().get(position).getId());
            }

            /***
             *
             * @return the number of data exist and be able to render
             */
            @Override
            public int getCount() {
                return CrimeLab.getInstance(CrimePagerActivity.this).getCrimes().size();
            }


        });
        /*Why? to change the title bar to the crime title!**/
        this.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                return;
            }

            @Override
            public void onPageSelected(int position) {
                CrimePagerActivity.this.setTitle(
                        CrimeLab.getInstance(CrimePagerActivity.this)
                        .getCrimes().get(position)
                        .getTitle()
                );
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                return;
            }
        });

        /*Let the CrimePagerActivity know from witch element to start!*/
        UUID args = (UUID)this.getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_UUID);
        if(args!=null){
            this.pager.setCurrentItem(CrimeLab.getInstance(this).getIndexByID(args));
        }
    }
}
