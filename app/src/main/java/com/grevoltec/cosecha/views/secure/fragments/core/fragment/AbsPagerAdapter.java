package com.grevoltec.cosecha.views.secure.fragments.core.fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public abstract class AbsPagerAdapter extends FragmentStatePagerAdapter {

    protected int numTabs;

    public AbsPagerAdapter(FragmentManager fm, int numTabs) {
        super(fm);
        this.numTabs = numTabs;
    }

    @Override
    public int getCount() {
        return numTabs;
    }

}
