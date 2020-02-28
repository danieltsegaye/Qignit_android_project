package com.example.tabmusic;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerController extends FragmentPagerAdapter {
    int tabCounts;

    public PagerController(FragmentManager fm, int tabCounts) {
        super(fm);
        this.tabCounts = tabCounts;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new AllMusic();
            case 1:
                return new Albums();

            case 2:
                return new Playlist();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return tabCounts;
    }
}
