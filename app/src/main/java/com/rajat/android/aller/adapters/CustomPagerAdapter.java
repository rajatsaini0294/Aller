package com.rajat.android.aller.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rajat.android.aller.ui.fragments.FutureLocationsFragment;
import com.rajat.android.aller.ui.fragments.VisitedLocationsFragment;

/**
 * Created by rajat on 3/2/2017.
 */
public class CustomPagerAdapter extends FragmentPagerAdapter{

    public CustomPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0: return new VisitedLocationsFragment();
            case 1: return new FutureLocationsFragment();
            default: return new VisitedLocationsFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Visited";
            case 1: return "To visit";
            default: return "Visited";
        }
    }
}
