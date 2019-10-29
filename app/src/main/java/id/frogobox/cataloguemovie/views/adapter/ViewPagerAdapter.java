package id.frogobox.cataloguemovie.views.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import id.frogobox.cataloguemovie.R;
import id.frogobox.cataloguemovie.views.fragments.FavoriteFragment;
import id.frogobox.cataloguemovie.views.fragments.MovieNowplayingFragment;
import id.frogobox.cataloguemovie.views.fragments.MovieSearchFragment;
import id.frogobox.cataloguemovie.views.fragments.MovieUpcomingFragment;

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * CatalogueMovie
 * Copyright (C) 14/01/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Line     : bullbee117
 * Phone    : 081357108568
 * Majors   : D3 Teknik Informatika 2016
 * Campus   : Telkom University
 * -----------------------------------------
 * id.amirisback.frogobox
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public ViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MovieNowplayingFragment();
            case 1:
                return new MovieUpcomingFragment();
            case 2:
                return new MovieSearchFragment();
            case 3:
                return new FavoriteFragment();
            default:
                throw new IllegalArgumentException();
        }

    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.title_nowplaying);
            case 1:
                return mContext.getString(R.string.title_upcoming);
            case 2:
                return mContext.getString(R.string.title_search);
            case 3:
                return mContext.getString(R.string.title_favorite);
            default:
                return null;
        }
    }
}