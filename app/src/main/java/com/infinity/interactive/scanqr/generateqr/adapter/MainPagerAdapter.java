package com.infinity.interactive.scanqr.generateqr.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import com.infinity.interactive.scanqr.generateqr.fragment.QRGenerateFragment;
import com.infinity.interactive.scanqr.generateqr.fragment.QRHistoryFragment;
import com.infinity.interactive.scanqr.generateqr.fragment.QRScanFragment;
import com.infinity.interactive.scanqr.generateqr.fragment.QRSettingsFragment;


/**
 * Created by Bezruk on 16/10/18.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<String> mFragmentItems;

    public MainPagerAdapter(FragmentManager fm, ArrayList<String> fragmentItems) {
        super(fm);
        this.mFragmentItems = fragmentItems;
    }

    @Override
    public Fragment getItem(int i) {

        Fragment fragment = null;

        if (i == 0) {
            fragment = new QRScanFragment();
//            fragment = new ScanningFragment();
        } else if (i == 1) {
            fragment = new QRGenerateFragment();
        } else if (i == 2) {
            fragment = new QRHistoryFragment();
        } else if (i == 3) {
            fragment = new QRSettingsFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return mFragmentItems.size();
    }

}
