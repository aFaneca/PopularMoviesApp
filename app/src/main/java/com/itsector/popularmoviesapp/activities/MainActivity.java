/*
 * Copyright (c) 2019 ITSector Software. All rights reserved.
 * ITSector Software Confidential and Proprietary information. It is strictly forbidden for 3rd
 * parties to modify, decompile, disassemble, defeat, disable or circumvent any protection
 * mechanism; to sell, license, lease, rent, redistribute or make accessible to any third party,
 * whether for profit or without charge.
 */

package com.itsector.popularmoviesapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.itsector.popularmoviesapp.R;
import com.itsector.popularmoviesapp.models.Movie;
import com.itsector.popularmoviesapp.network.AsyncResponse;
import com.itsector.popularmoviesapp.network.SyncTask;
import com.itsector.popularmoviesapp.utils.MovieUtils;
import com.itsector.popularmoviesapp.views.adapters.MoviesListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int NUMBER_OF_COLUMNS = 2;
    private RecyclerView mMoviesList_recycler_view;
    private MoviesListAdapter mMoviesListAdapter;
    private List<Movie> mMoviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Removes unnecessary shadows below the action bar*/
        getSupportActionBar().setElevation(0f);


        startSyncTask();

        mMoviesList_recycler_view = (RecyclerView) findViewById(R.id.movie_list_recycler_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, NUMBER_OF_COLUMNS);
        mMoviesList_recycler_view.setLayoutManager(gridLayoutManager);
        mMoviesList_recycler_view.setAdapter(mMoviesListAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        /* Update the dataset in the adapter, in case the sorting settings have changed
        *  (It won't make a new API request. It'll only resort the dataset and refresh the view
        * */
        if(mMoviesList != null)
            updateAdapter(mMoviesList);
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
        SyncTask task = new SyncTask(new AsyncResponse() {
            @Override
            public void onGetMoviesCompleted(List<Movie> moviesList) {
                moviesList = sortMoviesOrder(moviesList);
                mMoviesList = moviesList;
                updateAdapter(mMoviesList);
            }
        });
        task.execute();
    }

    /**
     * Sorts the new dataset provided (updatedDataset) and feeds it to the adapter
     * @param updatedDataset
     */
    private void updateAdapter(List<Movie> updatedDataset){
        mMoviesList = sortMoviesOrder(updatedDataset);
        mMoviesList_recycler_view.swapAdapter(getNewMoviesListAdapter(mMoviesList), true);
    }

    /**
     * Returns a new instance of the adapter, containing the dataset provided
     * @param dataset
     * @return
     */
    private RecyclerView.Adapter getNewMoviesListAdapter(List<Movie> dataset) {
        mMoviesList = sortMoviesOrder(dataset);
        mMoviesListAdapter = new MoviesListAdapter(mMoviesList, new MoviesListAdapter.OnItemClickListener() {
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
     * @param movie
     */
    private void goToDetailsForMovie(Movie movie) {

        /* All the info the views needs */
        int movieID = movie.getID();
        String originalTitle = movie.getOriginalTitle();
        int year = movie.getYear();
        double popularity = movie.getPopularity();
        double rating = movie.getVoteAverage();
        String synopsys = movie.getPlotSynopsis();
        String imgPath = movie.getImgPath();
        String backdropPath = movie.getBackdropImgPath();

        /* Put all the data into a bundle */
        Bundle args = new Bundle();
        args.putInt(getString(R.string.details_id_key), movieID);
        args.putString(getString(R.string.details_title_key), originalTitle);
        args.putInt(getString(R.string.details_year_key), year);
        args.putDouble(getString(R.string.details_popularity_key), popularity);
        args.putDouble(getString(R.string.details_rating_key), rating);
        args.putString(getString(R.string.details_synopsis_key), synopsys);
        args.putString(getString(R.string.details_img_path_key), imgPath);
        args.putString(getString(R.string.details_backdrop_path_key), backdropPath);

        /* Go to the details activity */
        Intent detailsIntent = new Intent(this, DetailsActivity.class)
                .putExtra(getString(R.string.details_bundle_key), args);
        startActivity(detailsIntent);
    }
}
