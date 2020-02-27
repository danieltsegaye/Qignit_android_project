package com.example.tabmusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    Toolbar mToolbar;
    TabLayout mTablayout;
    TabItem curMusic;
    TabItem allMusic;
    TabItem playList;
    ViewPager mpager;
    PagerController mPagerController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar=findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Tab Music");
        mTablayout=findViewById(R.id.tabLayout);
        curMusic=findViewById(R.id.currentMusic);
        allMusic=findViewById(R.id.allMusic);
        playList=findViewById(R.id.playList);
        mpager=findViewById(R.id.viewpager);


        mPagerController = new PagerController(getSupportFragmentManager(),mTablayout.getTabCount());
        mpager.setAdapter(mPagerController);

        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               mpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTablayout));
    }
}
