package com.sabekur2017.weatherappbd;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sabekur2017.weatherappbd.adapter.TabPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    //public static double latitude, longitude;
    public static String units = "metric";
    public static String tempSign = "°C";
    public static double latitude = 23.743;  // todo:neet to change dynamic location
    public static double longitude = 90.372;
   // private String unit = "metric";
    // tablayout
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TabPagerAdapter tabPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabLayout=findViewById(R.id.tabs);
        mViewPager=findViewById(R.id.view_pager);
        mTabLayout.addTab(mTabLayout.newTab().setText("current weather").setIcon(R.drawable.icon));
        mTabLayout.addTab(mTabLayout.newTab().setText("forecust weather").setIcon(R.drawable.icon));
        tabPagerAdapter=new TabPagerAdapter(getSupportFragmentManager(),mTabLayout.getTabCount());
        mViewPager.setAdapter(tabPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

}
