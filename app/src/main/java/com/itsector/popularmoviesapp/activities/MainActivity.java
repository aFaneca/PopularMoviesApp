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

import com.itsector.popularmoviesapp.R;
import com.itsector.popularmoviesapp.models.Movie;
import com.itsector.popularmoviesapp.network.AsyncResponse;
import com.itsector.popularmoviesapp.network.SyncTask;
import com.itsector.popularmoviesapp.views.adapters.MoviesListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int NUMBER_OF_COLUMNS = 2;
    private RecyclerView mMoviesList_recycler_view;
    private RecyclerView.Adapter mMoviesListAdapter;
    private List<Movie> mMoviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SyncTask task = new SyncTask(new AsyncResponse() {
            @Override
            public void onGetMoviesCompleted(List<Movie> moviesList) {
                mMoviesList_recycler_view.swapAdapter(getNewMoviesListAdapter(moviesList), false);
            }
        });
        task.execute();

        mMoviesList_recycler_view = (RecyclerView) findViewById(R.id.movie_list_recycler_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, NUMBER_OF_COLUMNS);
        mMoviesList_recycler_view.setLayoutManager(gridLayoutManager);
        mMoviesList_recycler_view.setAdapter(mMoviesListAdapter);
    }

    private RecyclerView.Adapter getNewMoviesListAdapter(List<Movie> dataset) {
        mMoviesListAdapter = new MoviesListAdapter(dataset, new MoviesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Movie movie) {
                goToDetailsForMovie(movie);
            }
        });

        return mMoviesListAdapter;
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
        int runtime = movie.getRuntime();
        double rating = movie.getVoteAverage();
        String synopsys = movie.getPlotSynopsys();
        String imgPath = movie.getImgPath();

        /* Put all the data into a bundle */
        Bundle args = new Bundle();
        args.putInt(getString(R.string.details_id_key), movieID);
        args.putString(getString(R.string.details_title_key), originalTitle);
        args.putInt(getString(R.string.details_year_key), year);
        args.putInt(getString(R.string.details_runtime_key), runtime);
        args.putDouble(getString(R.string.details_rating_key), rating);
        args.putString(getString(R.string.details_synopsys_key), synopsys);
        args.putString(getString(R.string.details_img_path_key), imgPath);
        /* Go to the details activity */
        Intent detailsIntent = new Intent(this, DetailsActivity.class)
                .putExtra(getString(R.string.details_bundle_key), args);
        startActivity(detailsIntent);
    }

    private List<Movie> generateDataSet(int q) {
        List<Movie> tempMoviesList = new ArrayList<>(q);


        for(int i = 0; i < q; i++){
            tempMoviesList.add(new Movie(1,"Titulo " + i,  "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "", 2.5, "2015-10-15", 120 ));
        }




        return tempMoviesList;
    }
}
