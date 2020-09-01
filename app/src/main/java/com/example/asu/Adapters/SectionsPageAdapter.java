package com.example.asu.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.asu.DomainModel.DMSetting;
import com.example.asu.Fragments.Tab1Fragment;

import java.util.ArrayList;
import java.util.List;

public class SectionsPageAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();

    private final List<String> mFragmentTitleList = new ArrayList<>();

    public void addFragment(Tab1Fragment fragment, String title){
        int day = fragment.day%10;
            String daystr = "showday"+String.valueOf(day);
            DMSetting dmSetting = DMSetting.findOne(daystr);
            if (dmSetting.checkShowDaySetting() || !dmSetting.exists()){
                Fragment fr = new Tab1Fragment();
                ((Tab1Fragment) fr).day = day;
                mFragmentList.add(fragment);
                mFragmentTitleList.add(title);
        }

    }

    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
