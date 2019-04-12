/*
 * Copyright (c) 2019 ITSector Software. All rights reserved.
 * ITSector Software Confidential and Proprietary information. It is strictly forbidden for 3rd
 * parties to modify, decompile, disassemble, defeat, disable or circumvent any protection
 * mechanism; to sell, license, lease, rent, redistribute or make accessible to any third party,
 * whether for profit or without charge.
 */

package com.itsector.popularmoviesapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import com.itsector.popularmoviesapp.models.Movie;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Helper methods for everything related with the Movies entity
 * Created by E936 on 4/12/2019.
 */
public class MovieUtils implements Constants {

    /**
     * Returns the full URL to the thumbnail image
     *
     * @param imgPath - path to the img (e.g. '/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg')
     * @return the URL object
     */
    public static URL getImgURL(String imgPath) {
        URL url = null;

        try {
            url = new URL(BASE_API_IMG_URL + BASE_API_IMG_SIZE + imgPath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Returns the full URL to the backdrop image
     *
     * @param imgPath - path to the img (e.g. '/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg')
     * @return the URL object
     */
    public static URL getBackdropImgURL(String imgPath) {
        URL url = null;

        try {
            url = new URL(BASE_API_IMG_URL + BASE_API_BACKDROP_SIZE + imgPath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Checks SharedPreferences to get the sorting method & applies it
     *
     * @param mMoviesList
     * @return the sorted list
     */
    public static List<Movie> sortOrder(Context context, List<Movie> mMoviesList) {
        /* Checks Shared Preferences */
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String sortOrder = prefs.getString(SORT_ORDER_PREF_KEY, SORT_ORDER_DEFAULT);

        switch (sortOrder) {
            case SORT_ORDER_POPULARITY_DESC:
                return sortOrderByPopularity(mMoviesList);
            case SORT_ORDER_RATING_DESC:
                return sortOrderByRating(mMoviesList);
            default:
                return sortOrderByPopularity(mMoviesList);
        }


    }

    /**
     * Sort the list in DESCENDING ORDER of POPULARITY
     *
     * @param mMoviesList
     * @return
     */
    private static List<Movie> sortOrderByPopularity(List<Movie> mMoviesList) {
        Collections.sort(mMoviesList, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                return Double.compare(o2.getPopularity(), o1.getPopularity());
            }
        });

        return mMoviesList;
    }

    /**
     * Sort the list in DESCENDING ORDER of RATING
     *
     * @param mMoviesList
     * @return
     */
    private static List<Movie> sortOrderByRating(List<Movie> mMoviesList) {
        Collections.sort(mMoviesList, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                return Double.compare(o2.getVoteAverage(), o1.getVoteAverage());
            }
        });

        return mMoviesList;
    }
}
