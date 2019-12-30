package com.sabekur2017.weatherappbd.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sabekur2017.weatherappbd.fragment.CurrentWeatherFragment;
import com.sabekur2017.weatherappbd.fragment.ForecastWeatherFragment;

public class TabPagerAdapter extends FragmentPagerAdapter {
    private int tabCount;

    public TabPagerAdapter(@NonNull FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new CurrentWeatherFragment();
            case 1:
                return new ForecastWeatherFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
