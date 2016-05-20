package com.sourcebits.fitfind.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sourcebits.fitfind.R;
import com.sourcebits.fitfind.fragments.ClientRegisterFragment;
import com.sourcebits.fitfind.fragments.TrainerRegisterFragment;

/**
 * Created by vaishaliarora on 18/05/16.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private static final int NO_OF_TABS = 2;

    public PagerAdapter(Context ctx,FragmentManager fm) {
        super(fm);
        mContext = ctx;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new ClientRegisterFragment();
                break;
            case 1:
                fragment = new TrainerRegisterFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return NO_OF_TABS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position) {
            case 0:
                title = mContext.getString(R.string.client);
                break;
            case 1:
                title = mContext.getString(R.string.trainer);
                break;
        }
        return title;
    }
}
