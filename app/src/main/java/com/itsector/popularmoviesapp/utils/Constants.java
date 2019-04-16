package com.itsector.popularmoviesapp.utils;

/**
 * Created by E936 on 4/11/2019.
 */
public interface Constants {
    /* API */
    String API_KEY = "b9f27d8d2b4a8722ef2d3ba80e3efa14";
    String BASE_API_URL = "https://api.themoviedb.org/3/";
    String PATH_POPULAR_MOVIES = "movie/popular";
    String PATH_TOP_RATED_MOVIES = "movie/top_rated";
    String PATH_MOVIE = "movie/";
    String PATH_REVIEWS = "reviews";
    String PATH_TRAILERS = "videos";

    String BASE_API_IMG_URL = "http://image.tmdb.org/t/p/";
    String BASE_API_IMG_SIZE = "w185";
    String BASE_API_BACKDROP_SIZE = "w780";

    /* NAMES OF JSON OBJECTS RELEVANT FOR POPULAR & TOP RATED MOVIES */
    String API_POPULAR_BASE_OBJ = "results";
    String API_POPULAR_ID = "id";
    String API_POPULAR_ORIGINAL_TITLE = "original_title";
    String API_POPULAR_IMG_PATH = "poster_path";
    String API_POPULAR_BACKDROP_PATH = "backdrop_path";
    String API_POPULAR_PLOT_SYNOPSYS = "overview";
    String API_POPULAR_VOTE_AVERAGE = "vote_average";
    String API_POPULAR_RELEASE_DATE = "release_date";
    String API_POPULAR_POPULARITY = "popularity";

    /* NAMES OF JSON OBJECTS RELEVANT FOR REVIEWS */
    String API_REVIEWS_BASE_OBJ = "results";
    String API_REVIEWS_AUTHOR = "author";
    String API_REVIEWS_CONTENT = "content";
    String API_REVIEWS_ID = "id";
    String API_REVIEWS_URL = "url";

    /* NAMES OF JSON OBJECTS RELEVANT FOR TRAILERS/VIDEOS */
    String API_VIDEOS_BASE_OBJ = "results";
    String API_VIDEOS_KEY = "key";
    String API_VIDEOS_NAME = "name";
    String API_VIDEOS_ID = "id";
    String API_VIDEOS_TYPE = "type";
    String API_VIDEOS_SITE = "site";



    /* API Actions */
    enum API_ACTIONS {
        API_GET_POPULAR_MOVIES,
        API_GET_TOP_RATED_MOVIES,
        API_GET_MOVIE,
        API_GET_MOVIE_REVIEWS,
        API_GET_MOVIE_TRAILERS
    }


    /* SORT ORDER */
    String SORT_ORDER_PREF_KEY = "pref_sort_order_key";
    String SORT_ORDER_DEFAULT = "pref_sort_order_popularity_key";
    String SORT_ORDER_POPULARITY_DESC = "pref_sort_order_popularity_key";
    String SORT_ORDER_RATING_DESC = "pref_sort_order_rating_key";
    String SORT_ORDER_FAVORITES = "pref_sort_order_favorites_key";

    /* DB */
    String DB_NAME = "movies_db";

}
