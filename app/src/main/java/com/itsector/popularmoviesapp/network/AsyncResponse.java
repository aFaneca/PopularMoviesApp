/*
 * Copyright (c) 2019 ITSector Software. All rights reserved.
 * ITSector Software Confidential and Proprietary information. It is strictly forbidden for 3rd
 * parties to modify, decompile, disassemble, defeat, disable or circumvent any protection
 * mechanism; to sell, license, lease, rent, redistribute or make accessible to any third party,
 * whether for profit or without charge.
 */

package com.itsector.popularmoviesapp.network;

import com.itsector.popularmoviesapp.models.Movie;

import java.util.List;

/**
 * This interface allows me to implement a solution that will communicate the responses
 * from async tasks back to an activity.
 *
 * Created by E936 on 4/12/2019.
 */
public interface AsyncResponse {

    void onGetMoviesCompleted(List<Movie> moviesList);
}
