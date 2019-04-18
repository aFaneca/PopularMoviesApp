/*
 * Copyright (c) 2019 ITSector Software. All rights reserved.
 * ITSector Software Confidential and Proprietary information. It is strictly forbidden for 3rd
 * parties to modify, decompile, disassemble, defeat, disable or circumvent any protection
 * mechanism; to sell, license, lease, rent, redistribute or make accessible to any third party,
 * whether for profit or without charge.
 */

package com.itsector.popularmoviesapp.models;

import java.util.List;

/**
 * Created by E936 on 4/18/2019.
 */
public class MoviesFetch {
    private int mCurrentPage;
    private int mTotalPages;
    private List<Movie> movies;

    public MoviesFetch(int mCurrentPage, int mTotalPages, List<Movie> movies) {
        this.mCurrentPage = mCurrentPage;
        this.mTotalPages = mTotalPages;
        this.movies = movies;
    }

    /* Getters & Setters */

    public int getmCurrentPage() {
        return mCurrentPage;
    }

    public int getmTotalPages() {
        return mTotalPages;
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
