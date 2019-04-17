/*
 * Copyright (c) 2019 ITSector Software. All rights reserved.
 * ITSector Software Confidential and Proprietary information. It is strictly forbidden for 3rd
 * parties to modify, decompile, disassemble, defeat, disable or circumvent any protection
 * mechanism; to sell, license, lease, rent, redistribute or make accessible to any third party,
 * whether for profit or without charge.
 */

package com.itsector.popularmoviesapp.utils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by E936 on 4/12/2019.
 */
public class APIUtils implements Constants {

    public static URL getFullURLforAction(API_ACTIONS action) {
        switch (action) {
            case API_GET_POPULAR_MOVIES:
                return buildURLForPopularMovies();
            case API_GET_TOP_RATED_MOVIES:
                return buildURLForTopRatedMovies();
            case API_GET_MOVIE:
                return buildURLForMovie("-1");
            case API_GET_MOVIE_REVIEWS:
                return buildURLForMovieReviews("-1");
            case API_GET_MOVIE_TRAILERS:
                return buildURLForMovieTrailers("-1");
            default:
                return buildURLForPopularMovies();
        }
    }

    public static URL getFullURLforAction(API_ACTIONS action, String param) {
        switch (action) {
            case API_GET_POPULAR_MOVIES:
                return buildURLForPopularMovies();
            case API_GET_TOP_RATED_MOVIES:
                return buildURLForTopRatedMovies();
            case API_GET_MOVIE:
                return buildURLForMovie(param);
            case API_GET_MOVIE_REVIEWS:
                return buildURLForMovieReviews(param);
            case API_GET_MOVIE_TRAILERS:
                return buildURLForMovieTrailers(param);
            default:
                return buildURLForPopularMovies();
        }
    }



    /* Helper Methods */

    /**
     * Builds and returns the full URL to the API request for a specific method
     *
     * @return
     */
    private static URL buildURLForMovie(String movieID) {
        URL fullURL = null;
        try {
            fullURL = new URL(BASE_API_URL + PATH_MOVIE + movieID + getQueryParamForAPIKey());
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
        return fullURL;
    }

    /**
     * Builds and returns the full URL to the API request for a specific method
     *
     * @return
     */
    private static URL buildURLForMovieReviews(String movieID) {
        URL fullURL = null;
        try {
            fullURL = new URL(BASE_API_URL + PATH_MOVIE + movieID + PATH_REVIEWS +  getQueryParamForAPIKey());
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
        return fullURL;
    }

    /**
     * Builds and returns the full URL to the API request for a specific method
     *
     * @return
     */
    private static URL buildURLForMovieTrailers(String movieID) {
        URL fullURL = null;
        try {
            fullURL = new URL(BASE_API_URL + PATH_MOVIE + movieID + PATH_TRAILERS +  getQueryParamForAPIKey());
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
        return fullURL;
    }

    /**
     * Builds and returns the full URL to the API request for top rated movies
     *
     * @return
     */
    private static URL buildURLForTopRatedMovies() {
        URL fullURL = null;
        try {
            fullURL = new URL(BASE_API_URL + PATH_TOP_RATED_MOVIES + getQueryParamForAPIKey());
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
        return fullURL;
    }

    /**
     * Builds and returns the full URL to the API request for popular movies
     *
     * @return
     */
    private static URL buildURLForPopularMovies() {
        URL fullURL = null;
        try {
            fullURL = new URL(BASE_API_URL + PATH_POPULAR_MOVIES + getQueryParamForAPIKey());
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
        return fullURL;
    }

    private static String getQueryParamForAPIKey() {
        return "?api_key=" + API_KEY;
    }
}
