/*
 * Copyright (c) 2019 ITSector Software. All rights reserved.
 * ITSector Software Confidential and Proprietary information. It is strictly forbidden for 3rd
 * parties to modify, decompile, disassemble, defeat, disable or circumvent any protection
 * mechanism; to sell, license, lease, rent, redistribute or make accessible to any third party,
 * whether for profit or without charge.
 */

package com.itsector.popularmoviesapp.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.itsector.popularmoviesapp.models.Movie;
import com.itsector.popularmoviesapp.utils.Constants;

import java.util.List;

/**
 * Created by E936 on 4/12/2019.
 */
public class SyncTask extends AsyncTask<Void, Void, List<Movie>> implements Constants {
    public AsyncResponse delegate = null;
    private Context context;

    public SyncTask(AsyncResponse delegate, Context context) {
        this.delegate = delegate;
        this.context = context;
    }

    @Override
    protected List<Movie> doInBackground(Void... voids) {
        /* Checks Shared Preferences */
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String sortOrder = prefs.getString(SORT_ORDER_PREF_KEY, SORT_ORDER_DEFAULT);

        switch (sortOrder) {
            case SORT_ORDER_POPULARITY_DESC:
                return MovieSync.getPopularMovies();
            case SORT_ORDER_RATING_DESC:
                return MovieSync.getTopRatedMovies();
            case SORT_ORDER_FAVORITES:
                return MovieSync.getFavoriteMovies(context);
            default:
                return MovieSync.getPopularMovies();
        }
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        delegate.onGetMoviesCompleted(movies);
    }
}
