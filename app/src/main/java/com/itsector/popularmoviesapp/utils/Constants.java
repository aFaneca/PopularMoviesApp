package com.itsector.popularmoviesapp.utils;

/**
 * Created by E936 on 4/11/2019.
 */
public interface Constants {
    /* API */
    String API_KEY = "b9f27d8d2b4a8722ef2d3ba80e3efa14";
    String BASE_API_URL = "https://api.themoviedb.org/3/movie/";
    String PATH_POPULAR_MOVIES = "popular";

    String BASE_API_IMG_URL = "http://image.tmdb.org/t/p/";
    String BASE_API_IMG_SIZE = "w185";
    String BASE_API_BACKDROP_SIZE = "w780";

    /* NAMES OF JSON Objects RELEVANT FOR POPULAR MOVIES */
    String API_POPULAR_BASE_OBJ = "results";
    String API_POPULAR_ID = "id";
    String API_POPULAR_ORIGINAL_TITLE = "original_title";
    String API_POPULAR_IMG_PATH = "poster_path";
    String API_POPULAR_BACKDROP_PATH = "backdrop_path";
    String API_POPULAR_PLOT_SYNOPSYS = "overview";
    String API_POPULAR_VOTE_AVERAGE = "vote_average";
    String API_POPULAR_RELEASE_DATE = "release_date";
    String API_POPULAR_POPULARITY = "popularity";


    /* API Actions */
    enum API_ACTIONS {
        API_GET_POPULAR_MOVIES,
    }


    /* SORT ORDER */
    String SORT_ORDER_PREF_KEY = "pref_sort_order_key";
    String SORT_ORDER_DEFAULT = "pref_sort_order_popularity_key";
    String SORT_ORDER_POPULARITY_DESC = "pref_sort_order_popularity_key";
    String SORT_ORDER_RATING_DESC = "pref_sort_order_rating_key";

}
