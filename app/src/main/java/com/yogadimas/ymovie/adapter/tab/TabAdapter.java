package com.yogadimas.ymovie.adapter.tab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.yogadimas.ymovie.fragment.MovieFragment;
import com.yogadimas.ymovie.fragment.TvShowFragment;

import java.util.ArrayList;
import java.util.List;

public class TabAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mfragment = new ArrayList<>();
    private final List<String> mFragmentList = new ArrayList<>();
    private FragmentManager fragmentManager;

    public TabAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new MovieFragment();
        }
        return new TvShowFragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {
            return "Movie";
        }
        return "Tv Show";
    }

    @Override
    public int getCount() {
        return 2;
    }

    public void addFragment(Fragment fragment, String title) {
        mfragment.add(fragment);
        mFragmentList.add(title);
    }
}
