/*
 * Copyright (c) 2019 ITSector Software. All rights reserved.
 * ITSector Software Confidential and Proprietary information. It is strictly forbidden for 3rd
 * parties to modify, decompile, disassemble, defeat, disable or circumvent any protection
 * mechanism; to sell, license, lease, rent, redistribute or make accessible to any third party,
 * whether for profit or without charge.
 */

package com.itsector.popularmoviesapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.itsector.popularmoviesapp.models.Movie;

import java.util.List;

/**
 * Part of Room's implementation architecture
 * Defines all the ways we can access and manage the movies Data Access Objects
 * Created by E936 on 4/15/2019.
 */

@Dao
public interface DaoAccess {

    @Insert
    void insertSingleMovie(Movie movie);

    @Insert
    void insertMultipleMovies(List<Movie> movies);

    @Query("SELECT * FROM Movie")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * FROM Movie WHERE mID = :movieID")
    Movie getMovieByID(int movieID);

    @Update
    void updateMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);
}
