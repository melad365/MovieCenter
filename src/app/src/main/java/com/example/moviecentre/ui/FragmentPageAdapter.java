package com.example.moviecentre.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.moviecentre.fragments.NowPlayingMoviesFragment;
import com.example.moviecentre.fragments.PopularMoviesFragment;
import com.example.moviecentre.fragments.RecommendedFragment;
import com.example.moviecentre.fragments.UpcomingMoviesFragment;

public class FragmentPageAdapter extends FragmentPagerAdapter {

    private Context context;
    private final String[] TITLES = {"Upcoming", "Now Playing ", "Popular", "Recommended"};

    public FragmentPageAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0){
            return new UpcomingMoviesFragment();
        }else if (position == 1){
            return new NowPlayingMoviesFragment();
        }else if(position ==2){
            return new PopularMoviesFragment();
        }else{
            return new RecommendedFragment();
        }

    }

    @Override
    public int getCount() {
        return TITLES.length;
    }
}
