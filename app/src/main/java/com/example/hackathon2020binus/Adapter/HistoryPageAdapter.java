package com.example.hackathon2020binus.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.hackathon2020binus.Tab.FranchiseTab;
import com.example.hackathon2020binus.Tab.PartnershipTab;

public class HistoryPageAdapter extends FragmentPagerAdapter {
    public HistoryPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                PartnershipTab partnershipTab = new PartnershipTab();
                return partnershipTab;
            case 1:
                FranchiseTab franchiseTab = new FranchiseTab();
                return franchiseTab;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Partnership";
            case 1:
                return "Franchise";
            default:
                return null;
        }

    }
}
