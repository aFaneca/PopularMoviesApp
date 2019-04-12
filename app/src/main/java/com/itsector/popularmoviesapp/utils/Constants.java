package com.itsector.popularmoviesapp.utils;

/**
 * Created by E936 on 4/11/2019.
 */
public interface Constants {
    /* API */
    String API_KEY = "b9f27d8d2b4a8722ef2d3ba80e3efa14";
    String BASE_API_URL = "https://api.themoviedb.org/3/movie/";
    String PATH_POPULAR_MOVIES = "popular";
    String QUERY_PARAM = "?q";

    String BASE_API_IMG_URL = "http://image.tmdb.org/t/p/";
    String BASE_API_IMG_SIZE = "w185";

    /* NAMES OF JSON Objects RELEVANT FOR POPULAR MOVIES */
    String API_POPULAR_BASE_OBJ = "results";
    String API_POPULAR_ID = "id";
    String API_POPULAR_ORIGINAL_TITLE = "original_title";
    String API_POPULAR_IMG_PATH = "poster_path";
    String API_POPULAR_PLOT_SYNOPSYS = "overview";
    String API_POPULAR_VOTE_AVERAGE = "vote_average";
    String API_POPULAR_RELEASE_DATE = "release_date";
    String API_POPULAR_RUNTIME = "";


    /* API Actions */
    enum API_ACTIONS {
        API_GET_POPULAR_MOVIES,
    }

}
