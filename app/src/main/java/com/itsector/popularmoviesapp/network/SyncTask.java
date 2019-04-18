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
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.itsector.popularmoviesapp.R;
import com.itsector.popularmoviesapp.models.Movie;
import com.itsector.popularmoviesapp.models.MoviesFetch;
import com.itsector.popularmoviesapp.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by E936 on 4/12/2019.
 */
public class SyncTask extends AsyncTask<Integer, Void, MoviesFetch> implements Constants {
    public AsyncResponse delegate = null;
    private Context context;

    public SyncTask(AsyncResponse delegate, Context context) {
        this.delegate = delegate;
        this.context = context;
    }

    @Override
    protected MoviesFetch doInBackground(Integer... pageNumber) {
        /* Checks Shared Preferences */
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String sortOrder = prefs.getString(SORT_ORDER_PREF_KEY, SORT_ORDER_DEFAULT);

        if(pageNumber == null || pageNumber.length < 1) pageNumber = new Integer[]{1};

        switch (sortOrder) {
            case SORT_ORDER_POPULARITY_DESC:
                try {
                    return MovieSync.getPopularMovies(pageNumber[0]);
                } catch (IOException e) {
                    showToastErrorMessage(context);
                    return new MoviesFetch(0, 0, new ArrayList<Movie>());
                }
            case SORT_ORDER_RATING_DESC:
                try {
                    return MovieSync.getTopRatedMovies(pageNumber[0]);
                } catch (IOException e) {
                    showToastErrorMessage(context);
                    return new MoviesFetch(0, 0, new ArrayList<Movie>());
                }
            /*case SORT_ORDER_FAVORITES:
                return MovieSync.getFavoriteMovies(context);*/
            default:
                try {
                    return MovieSync.getPopularMovies(pageNumber[0]);
                } catch (IOException e) {
                    showToastErrorMessage(context);
                    return new MoviesFetch(0, 0, new ArrayList<Movie>());
                }
        }
    }

    private void showToastErrorMessage(final Context mContext) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(mContext, context.getString(R.string.toast_network_error), Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    @Override
    protected void onPostExecute(MoviesFetch movies) {
        delegate.onGetMoviesCompleted(movies);
    }
}
