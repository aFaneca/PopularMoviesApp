/*
 * Copyright (c) 2019 ITSector Software. All rights reserved.
 * ITSector Software Confidential and Proprietary information. It is strictly forbidden for 3rd
 * parties to modify, decompile, disassemble, defeat, disable or circumvent any protection
 * mechanism; to sell, license, lease, rent, redistribute or make accessible to any third party,
 * whether for profit or without charge.
 */

package com.itsector.popularmoviesapp.utils;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.itsector.popularmoviesapp.data.MovieDatabase;
import com.itsector.popularmoviesapp.models.Movie;

import java.util.List;


/**
 * Created by E936 on 4/15/2019.
 */
public class MoviesListViewModel extends AndroidViewModel {

    private final LiveData<List<Movie>> moviesList;
    private MovieDatabase db;

    public MoviesListViewModel(@NonNull Application application) {
        super(application);
        db = MovieDatabase.getMovieDatabase(this.getApplication());
        moviesList = db.daoAccess().getAllMovies();

    }

    public LiveData<List<Movie>> getMoviesList(){
        return moviesList;
    }

    /**
     * Async method; Adds a movie to the db
     * @param context
     * @param movie
     * @return
     */
    public Movie addMovie(final Context context, final Movie movie){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MovieDatabase db = MovieDatabase.getMovieDatabase(context);

                /* Add the movie to the db */
                db.daoAccess().insertSingleMovie(movie);


            }
        }).start();

        return movie;
    }

    /**
     * Async method; deletes a movie from the db
     * @param context
     * @param movie
     * @return
     */
    public void deleteMovie(final Context context, final Movie movie){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MovieDatabase db = MovieDatabase.getMovieDatabase(context);

                /* Remove the movie from the db */
                db.daoAccess().deleteMovie(movie);
            }
        }).start();
    }

    /**
     * Async method; Gets movie from the db, by its ID
     * @param context
     * @param movieID
     * @param callback
     * @return
     */
    public void getMovieByID(final Context context, final int movieID, final GetMovieCallback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MovieDatabase db = MovieDatabase.getMovieDatabase(context);

                Movie movie = db.daoAccess().getMovieByID(movieID);

                callback.getSingleMovie(movie);
            }
        }).start();
    }

    /**
     * Async method; Gets all movies from the DB
     * @param context
     * @param callback
     * @return
     */
    public void getAllMovies(final Context context, final GetAllMoviesCallback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MovieDatabase db = MovieDatabase.getMovieDatabase(context);

                /*List<Movie> movieList = db.daoAccess().getAllMovies();*/

                callback.getAllMovies(moviesList);
            }
        }).start();
    }

    /**
     * Sync method; Gets all movies from the DB;
     * THIS METHOD IS THREAD BLOCKING AND SHOULD ONLY BE USED WHEN NECESSARY
     * @param context
     * @return
     */
    /*private static List<Movie> moviesList; *//* so that I can access it from within the inner class */
    public LiveData<List<Movie>> getAllMoviesSync(final Context context){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                MovieDatabase db = MovieDatabase.getMovieDatabase(context);

                /*moviesList = db.daoAccess().getAllMovies();*/
            }
        });
        t.start();
        try {
            t.join(); /* Wait for the thread to do its job */
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            return moviesList;
        }

    }

}
