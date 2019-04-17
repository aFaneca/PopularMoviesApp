/*
 * Copyright (c) 2019 ITSector Software. All rights reserved.
 * ITSector Software Confidential and Proprietary information. It is strictly forbidden for 3rd
 * parties to modify, decompile, disassemble, defeat, disable or circumvent any protection
 * mechanism; to sell, license, lease, rent, redistribute or make accessible to any third party,
 * whether for profit or without charge.
 */

package com.itsector.popularmoviesapp.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import com.itsector.popularmoviesapp.R;
import com.itsector.popularmoviesapp.models.Movie;
import com.itsector.popularmoviesapp.network.AsyncResponse;
import com.itsector.popularmoviesapp.network.MovieSync;
import com.itsector.popularmoviesapp.network.SyncTask;
import com.itsector.popularmoviesapp.utils.Constants;
import com.itsector.popularmoviesapp.utils.DBUtils;
import com.itsector.popularmoviesapp.utils.MovieUtils;
import com.itsector.popularmoviesapp.utils.MoviesListViewModel;
import com.itsector.popularmoviesapp.views.adapters.MoviesListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Constants {
    private RecyclerView mMoviesList_recycler_view;
    private MoviesListAdapter mMoviesListAdapter;
    private List<Movie> mMoviesList;
    private List<Movie> mFavoriteMovies;
    private MoviesListViewModel moviesListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupActionBar();
        startSyncTask();
        setupMoviesListAdapter();
    }

    /**
     * Initializes and sets up the recycler view + adapter + view model + livedata
     * for the movies list
     */
    private void setupMoviesListAdapter() {
        mMoviesList_recycler_view = (RecyclerView) findViewById(R.id.movie_list_recycler_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.recyler_view_videos_num_of_columns));
        mMoviesList_recycler_view.setLayoutManager(gridLayoutManager);
        getNewMoviesListAdapter(new ArrayList<Movie>());
        mMoviesList_recycler_view.setAdapter(mMoviesListAdapter);

        moviesListViewModel = ViewModelProviders.of(this).get(MoviesListViewModel.class);
        moviesListViewModel.getMoviesList().observe(MainActivity.this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                /* Keeping a local copy of the favorite movies list */
                mFavoriteMovies = movies;

                /* Checks Shared Preferences */
                String sortOrder = DBUtils.getSortOrderFromSharedPreferences(getApplicationContext());
                /* Only update the UI if we're showing the favorite movies list */
                if (sortOrder.equals(SORT_ORDER_FAVORITES))
                    updateAdapter(movies);
            }
        });
    }

    /**
     * Sets up te toolbar settings for the main activity
     */
    private void setupActionBar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |
                ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_USE_LOGO);
        getSupportActionBar().setTitle(" " + getActionBarTitle());
        getSupportActionBar().setIcon(R.drawable.ic_popcorn);

        /* Removes unnecessary shadows below the action bar */
        getSupportActionBar().setElevation(0f);
    }

    /**
     * Chooses a title for the action bar depending on the sort order preferences
     *
     * @return
     */
    private String getActionBarTitle() {
        String sortOrder = DBUtils.getSortOrderFromSharedPreferences(getApplicationContext());
        String title = "";

        switch (sortOrder) {
            case SORT_ORDER_FAVORITES:
                title = getString(R.string.app_name_fav);
                break;
            case SORT_ORDER_POPULARITY_DESC:
                title = getString(R.string.app_name_popular);
                break;
            case SORT_ORDER_RATING_DESC:
                title = getString(R.string.app_name_rating);
                break;
            default:
                title = getString(R.string.app_name_default);
                break;
        }

        return title;
    }

    @Override
    protected void onResume() {
        super.onResume();
        /* Update the action bar title */
        getSupportActionBar().setTitle(" " + getActionBarTitle());

        String sortOrder = DBUtils.getSortOrderFromSharedPreferences(getApplicationContext());

        /* If we need to display the favorites list... */
        if (sortOrder.equals(SORT_ORDER_FAVORITES)) {
            if (mFavoriteMovies != null)
                /* ... update the adapter's dataset*/
                updateAdapter(mFavoriteMovies);
        } else {
            /* Else, the sync task will check what type of list we need (By Rating or By Popularity)
             * and fetch it, storing it in the mMoviesList variable */
            startSyncTask();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startSyncTask() {
        /* Checks Shared Preferences */
        String sortOrder = DBUtils.getSortOrderFromSharedPreferences(getApplicationContext());

        if (!sortOrder.equals(SORT_ORDER_FAVORITES)) {
            SyncTask task = new SyncTask(new AsyncResponse() {
                @Override
                public void onGetMoviesCompleted(List<Movie> moviesList) {
                    mMoviesList = moviesList;
                    updateAdapter(mMoviesList);
                }
            }, this);
            task.execute();
        }
    }

    /**
     * Feeds the new data to the adapter
     *
     * @param updatedDataset
     */
    private void updateAdapter(List<Movie> updatedDataset) {
        mMoviesListAdapter.swap(updatedDataset);
    }

    /**
     * Returns a new instance of the adapter, containing the dataset provided
     *
     * @param dataset
     * @return
     */
    private RecyclerView.Adapter getNewMoviesListAdapter(List<Movie> dataset) {
        /*mMoviesList = sortMoviesOrder(dataset);*/
        mMoviesListAdapter = new MoviesListAdapter(dataset, new MoviesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Movie movie) {
                goToDetailsForMovie(movie);
            }
        });

        return mMoviesListAdapter;
    }

    private List<Movie> sortMoviesOrder(List<Movie> moviesList) {
        return MovieUtils.sortOrder(this, moviesList);
    }

    /**
     * Redirects to the activity containing the details for this movie
     *
     * @param movie
     */
    private void goToDetailsForMovie(Movie movie) {
        /* Knowing that I can't access the 'movie' object from within the inner class below (the runnable),
         * I made the variable below final, so that I can interact between the second thread and this method */
        final List<Movie> movies = new ArrayList<>();
        movies.add(movie);

        int movieID = -1;
        String originalTitle = "";
        int year = -1;
        double popularity = -1;
        double rating = -1;
        String synopsis = "";
        String imgPath = "";
        String backdropPath = "";

        /* Checks if we already have all the data we need */
        if (movie.getBackdropImgPath() == null) {
            /* If we don't have this field, it means we're dealing with a movie object stored
             * in a local DB (which only stores an ID, title and path to the thumbnail image),
             * so we have to fetch the rest of the data directly from the API */
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    Movie originalMovie = movies.get(0);
                    Movie fullMovie = null;
                    try {
                        fullMovie = MovieSync.getMovie(originalMovie.getID());
                        movies.add(fullMovie);
                    } catch (IOException e) {
                        /* Show toast message with the error */
                        Toast.makeText(getApplicationContext(), getString(R.string.toast_network_error),
                                Toast.LENGTH_LONG).show();
                    }

                }
            });
            t.start();
            try {
                t.join(); /* Wait for the thread to finish its job before moving on */
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /* The movie object we care about will always be the last one added to the movies list */
        movie = movies.get(movies.size() - 1);

        /* All the info the views need */
        movieID = movie.getID();
        originalTitle = movie.getOriginalTitle();
        year = movie.getYear();
        popularity = movie.getPopularity();
        rating = movie.getVoteAverage();
        synopsis = movie.getPlotSynopsis();
        imgPath = movie.getImgPath();
        backdropPath = movie.getBackdropImgPath();


        /* Put all the data into a bundle */
        Bundle args = new Bundle();
        args.putInt(getString(R.string.details_id_key), movieID);
        args.putString(getString(R.string.details_title_key), originalTitle);
        args.putInt(getString(R.string.details_year_key), year);
        args.putDouble(getString(R.string.details_popularity_key), popularity);
        args.putDouble(getString(R.string.details_rating_key), rating);
        args.putString(getString(R.string.details_synopsis_key), synopsis);
        args.putString(getString(R.string.details_img_path_key), imgPath);
        args.putString(getString(R.string.details_backdrop_path_key), backdropPath);

        /* Go to the details activity */
        Intent detailsIntent = new Intent(this, DetailsActivity.class)
                .putExtra(getString(R.string.details_bundle_key), args);
        startActivity(detailsIntent);
    }
}