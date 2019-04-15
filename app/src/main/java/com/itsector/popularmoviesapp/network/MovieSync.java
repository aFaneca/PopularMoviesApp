/*
 * Copyright (c) 2019 ITSector Software. All rights reserved.
 * ITSector Software Confidential and Proprietary information. It is strictly forbidden for 3rd
 * parties to modify, decompile, disassemble, defeat, disable or circumvent any protection
 * mechanism; to sell, license, lease, rent, redistribute or make accessible to any third party,
 * whether for profit or without charge.
 */

package com.itsector.popularmoviesapp.network;

import android.content.Context;

import com.itsector.popularmoviesapp.models.Movie;
import com.itsector.popularmoviesapp.utils.APIUtils;
import com.itsector.popularmoviesapp.utils.Constants;
import com.itsector.popularmoviesapp.utils.DBUtils;
import com.itsector.popularmoviesapp.utils.GetAllMoviesCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by E936 on 4/12/2019.
 */
public class MovieSync implements Constants {


    synchronized public static List<Movie> getPopularMovies() {
        HttpURLConnection urlConn = null;
        BufferedReader reader = null;

        /* Will contain the JSON response */
        String jsonResponseStr = null;

        URL requestURL = APIUtils.getFullURLforAction(API_ACTIONS.API_GET_POPULAR_MOVIES);

        /* Make the request */
        try {
            urlConn = (HttpURLConnection) requestURL.openConnection();
            urlConn.setRequestMethod("GET");
            urlConn.connect();

            /* Read the input stream into a stream */
            InputStream inputStream = urlConn.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) return new ArrayList<Movie>();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            /* While there's still lines to read, append them to the buffer */
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "/n");
            }

            if (buffer.length() == 0) return new ArrayList<Movie>();

            /* Convert buffer into a string */
            jsonResponseStr = buffer.toString();

            return getMoviesFromJSon(jsonResponseStr);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConn != null) urlConn.disconnect();
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return new ArrayList<Movie>();
    }

    synchronized public static List<Movie> getTopRatedMovies() {
        HttpURLConnection urlConn = null;
        BufferedReader reader = null;

        /* Will contain the JSON response */
        String jsonResponseStr = null;

        URL requestURL = APIUtils.getFullURLforAction(API_ACTIONS.API_GET_TOP_RATED_MOVIES);

        /* Make the request */
        try {
            urlConn = (HttpURLConnection) requestURL.openConnection();
            urlConn.setRequestMethod("GET");
            urlConn.connect();

            /* Read the input stream into a stream */
            InputStream inputStream = urlConn.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) return new ArrayList<Movie>();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            /* While there's still lines to read, append them to the buffer */
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "/n");
            }

            if (buffer.length() == 0) return new ArrayList<Movie>();

            /* Convert buffer into a string */
            jsonResponseStr = buffer.toString();

            return getMoviesFromJSon(jsonResponseStr);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConn != null) urlConn.disconnect();
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return new ArrayList<Movie>();
    }

    synchronized public static Movie getMovie(int movieID) {
        HttpURLConnection urlConn = null;
        BufferedReader reader = null;

        /* Will contain the JSON response */
        String jsonResponseStr = null;

        URL requestURL = APIUtils.getFullURLforAction(API_ACTIONS.API_GET_MOVIE, "" + movieID);

        /* Make the request */
        try {
            urlConn = (HttpURLConnection) requestURL.openConnection();
            urlConn.setRequestMethod("GET");
            urlConn.connect();

            /* Read the input stream into a stream */
            InputStream inputStream = urlConn.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) return new Movie(-1, "", "");

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            /* While there's still lines to read, append them to the buffer */
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "/n");
            }

            if (buffer.length() == 0) return new Movie(-1, "", "");

            /* Convert buffer into a string */
            jsonResponseStr = buffer.toString();

            return getSingleMovieFromJson(jsonResponseStr);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConn != null) urlConn.disconnect();
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return new Movie(-1, "", "");
    }


    /**
     * Converts the string representing the JSON response and extracts the important data
     *
     * @param jsonResponseStr
     */
    private static Movie getSingleMovieFromJson(String jsonResponseStr) {
        Movie movie = null;

        try {
            JSONObject movieEntry = new JSONObject(jsonResponseStr);
            /*JSONArray resultsArr = resultsJson.getJSONArray(API_POPULAR_BASE_OBJ);*/

            int ID = movieEntry.getInt(API_POPULAR_ID);
            String originalTitle = movieEntry.getString(API_POPULAR_ORIGINAL_TITLE);
            String thumbnalImagePath = movieEntry.getString(API_POPULAR_IMG_PATH);
            String backdropImagePath = movieEntry.getString(API_POPULAR_BACKDROP_PATH);
            String plotSynopsys = movieEntry.getString(API_POPULAR_PLOT_SYNOPSYS);
            double voteAverage = movieEntry.getDouble(API_POPULAR_VOTE_AVERAGE);
            String releaseDate = movieEntry.getString(API_POPULAR_RELEASE_DATE);
            double popularity = movieEntry.getDouble(API_POPULAR_POPULARITY);

            movie = new Movie(ID, originalTitle, thumbnalImagePath, backdropImagePath, plotSynopsys, voteAverage, releaseDate, popularity);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return movie;
    }

    /**
     * Converts the string representing the JSON response and extracts the important data
     *
     * @param jsonResponseStr
     */
    private static List<Movie> getMoviesFromJSon(String jsonResponseStr) {
        List<Movie> moviesList = new ArrayList<>();

        try {
            JSONObject resultsJson = new JSONObject(jsonResponseStr);
            JSONArray resultsArr = resultsJson.getJSONArray(API_POPULAR_BASE_OBJ);

            for (int i = 0; i < resultsArr.length(); i++) {
                JSONObject movieEntry = resultsArr.getJSONObject(i);

                int ID = movieEntry.getInt(API_POPULAR_ID);
                String originalTitle = movieEntry.getString(API_POPULAR_ORIGINAL_TITLE);
                String thumbnalImagePath = movieEntry.getString(API_POPULAR_IMG_PATH);
                String backdropImagePath = movieEntry.getString(API_POPULAR_BACKDROP_PATH);
                String plotSynopsys = movieEntry.getString(API_POPULAR_PLOT_SYNOPSYS);
                double voteAverage = movieEntry.getDouble(API_POPULAR_VOTE_AVERAGE);
                String releaseDate = movieEntry.getString(API_POPULAR_RELEASE_DATE);
                double popularity = movieEntry.getDouble(API_POPULAR_POPULARITY);

                moviesList.add(new Movie(ID, originalTitle, thumbnalImagePath, backdropImagePath, plotSynopsys, voteAverage, releaseDate, popularity));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return moviesList;
    }


    public static List<Movie> getFavoriteMovies(Context context) {
        List<Movie> moviesList = new ArrayList<>();
        /* Get all the movies in the DB */
        moviesList = DBUtils.getAllMoviesSync(context);

        return moviesList;
    }
}
