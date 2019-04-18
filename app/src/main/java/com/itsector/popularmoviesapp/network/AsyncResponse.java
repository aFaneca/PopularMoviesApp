/*
 * Copyright (c) 2019 ITSector Software. All rights reserved.
 * ITSector Software Confidential and Proprietary information. It is strictly forbidden for 3rd
 * parties to modify, decompile, disassemble, defeat, disable or circumvent any protection
 * mechanism; to sell, license, lease, rent, redistribute or make accessible to any third party,
 * whether for profit or without charge.
 */

package com.itsector.popularmoviesapp.network;

import com.itsector.popularmoviesapp.models.Movie;
import com.itsector.popularmoviesapp.models.MoviesFetch;

import java.util.List;

/**
 * Created by E936 on 4/15/2019.
 */
public interface AsyncResponse {

    void onGetMoviesCompleted(MoviesFetch moviesFetch);
}