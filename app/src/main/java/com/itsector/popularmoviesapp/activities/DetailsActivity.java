/*
 * Copyright (c) 2019 ITSector Software. All rights reserved.
 * ITSector Software Confidential and Proprietary information. It is strictly forbidden for 3rd
 * parties to modify, decompile, disassemble, defeat, disable or circumvent any protection
 * mechanism; to sell, license, lease, rent, redistribute or make accessible to any third party,
 * whether for profit or without charge.
 */

package com.itsector.popularmoviesapp.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itsector.popularmoviesapp.R;
import com.itsector.popularmoviesapp.models.Movie;
import com.itsector.popularmoviesapp.models.Review;
import com.itsector.popularmoviesapp.models.Video;
import com.itsector.popularmoviesapp.network.MovieSync;
import com.itsector.popularmoviesapp.utils.Constants;
import com.itsector.popularmoviesapp.utils.GetMovieCallback;
import com.itsector.popularmoviesapp.utils.ImageLoader;
import com.itsector.popularmoviesapp.utils.MovieUtils;
import com.itsector.popularmoviesapp.utils.MoviesListViewModel;
import com.itsector.popularmoviesapp.views.adapters.MovieReviewsAdapter;
import com.itsector.popularmoviesapp.views.adapters.MovieVideosAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    private Bundle mReceivedBundle;
    private int mMovieID;

    private RecyclerView mReviewsList_recycler_view;
    private MovieReviewsAdapter mMovieReviewsAdapter;

    private RecyclerView mVideosList_recycler_view;
    private MovieVideosAdapter mMovieVideosAdapter;

    private TextView mMovieTitle_text_view;
    private TextView mMovieYear_text_view;
    private TextView mMoviePopularity_text_view;
    private TextView mMovieRating_text_view;
    private TextView mDetails_movie_plot_synopsys_text_view;
    private Button mMovieFav_button;
    private ImageView mMovieThumbnail_image_view;

    private ShareActionProvider mShareActionProvider;

    /* Holds a "cached" version of this value during the lifecycle of this activity, to avoid unnecessary calls to the DB */
    private Boolean isMarkedAsFavorite;
    MoviesListViewModel moviesListViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        /* Get instance of the view model */
        moviesListViewModel = ViewModelProviders.of(this).get(MoviesListViewModel.class);

        setupReviewsListAdapter();
        setupVideosListAdapter();
        setupToolbar();

        initializeViews();

        bindViews();
        getReviews();
        getVideos();
    }

    /**
     * Initializes and sets up the toolbar
     */
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.details_label);
    }

    /**
     * Initializes all the views
     */
    private void initializeViews() {
        mMovieTitle_text_view = (TextView) findViewById(R.id.details_movie_title_text_view);
        mMovieYear_text_view = (TextView) findViewById(R.id.details_movie_year_text_view);
        mMoviePopularity_text_view = (TextView) findViewById(R.id.details_movie_popularity_text_view);
        mMovieRating_text_view = (TextView) findViewById(R.id.details_movie_rating_text_view);
        mDetails_movie_plot_synopsys_text_view = (TextView) findViewById(R.id.details_movie_plot_synopsys_text_view);
        mMovieThumbnail_image_view = (ImageView) findViewById(R.id.details_movie_thumbnail_image_view);
        mMovieFav_button = (Button) findViewById(R.id.details_movie_fav_button);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);

        MenuItem menuItem = menu.findItem(R.id.action_share);

        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        mShareActionProvider.setShareIntent(createShareMovieIntent());

        return super.onCreateOptionsMenu(menu);

    }

    private Intent createShareMovieIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, getMovieShareStr());

        return shareIntent;
    }

    /**
     * Returns a string representation of what's going to be shared using the ShareActionProvider
     *
     * @return string
     */
    private String getMovieShareStr() {
        String movieName = mReceivedBundle.getString(getString(R.string.details_title_key));
        int year = mReceivedBundle.getInt(getString(R.string.details_year_key));
        String rating = getString(R.string.details_rating, mReceivedBundle.getDouble(getString(R.string.details_rating_key)));

        return getString(R.string.movie_share_string, movieName, year, rating);
    }

    /**
     * Initialize & setup the recycler view + adapter for the videos list
     */
    private void setupVideosListAdapter() {
        /* Initialize the adapter + recycler view */
        mVideosList_recycler_view = (RecyclerView) findViewById(R.id.videos_list_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mVideosList_recycler_view.setLayoutManager(linearLayoutManager);
        getNewVideosListAdapter(new ArrayList<Video>());
        mVideosList_recycler_view.setAdapter(mMovieVideosAdapter);
    }

    /**
     * Initialize & setup the recycler view + adapter for the reviews list
     */
    private void setupReviewsListAdapter() {
        /* Initialize the adapter + recycler view */
        mReviewsList_recycler_view = (RecyclerView) findViewById(R.id.reviews_list_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        /*linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);*/
        mReviewsList_recycler_view.setLayoutManager(linearLayoutManager);
        getNewReviewsListAdapter(new ArrayList<Review>());
        mReviewsList_recycler_view.setAdapter(mMovieReviewsAdapter);
    }


    /**
     * Fetch reviews for this movie from the API
     */
    public interface getVideosCallback {
        public void getAllVideos(List<Video> videos);
    }

    private void getVideos() {
        MovieSync.getMovieVideos(this, mMovieID, new getVideosCallback() {
            @Override
            public void getAllVideos(List<Video> videos) {
                updateVideosAdapter(videos);
            }
        });
    }


    /**
     * Fetch reviews for this movie from the API
     */
    public interface getReviewsCallback {
        public void getAllReviews(List<Review> reviews);
    }

    private void getReviews() {
        MovieSync.getMovieReviews(this, mMovieID, new getReviewsCallback() {
            @Override
            public void getAllReviews(List<Review> reviews) {
                updateReviewsAdapter(reviews);
            }
        });
    }


    /**
     * Bind all of the views with the data received
     */
    private void bindViews() {
        mReceivedBundle = getIntent().getExtras().getBundle(getString(R.string.details_bundle_key));
        mMovieID = mReceivedBundle.getInt(getString(R.string.details_id_key));
        URL url = MovieUtils.getImgURL(mReceivedBundle.getString(getString(R.string.details_img_path_key)));

        bindBackdropImg();

        /* Associate image with the imageview */
        ImageLoader.loadImage(url, mMovieThumbnail_image_view);

        /* Bind the views */
        mMovieTitle_text_view.setText(mReceivedBundle.getString(getString(R.string.details_title_key)));
        mMovieYear_text_view.setText("" + mReceivedBundle.getInt(getString(R.string.details_year_key)));
        mMoviePopularity_text_view.setText(getString(R.string.details_popularity, mReceivedBundle.getDouble(getString(R.string.details_popularity_key))));
        mDetails_movie_plot_synopsys_text_view.setText(mReceivedBundle.getString(getString(R.string.details_synopsis_key)));
        mMovieRating_text_view.setText(getString(R.string.details_rating, mReceivedBundle.getDouble(getString(R.string.details_rating_key))));

        checkFavoriteStatus();

        mMovieFav_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* A copy of the movie, containing its ID and title (the only params the DB cares about) */
                final Movie movieCpy = new Movie(mMovieID, mReceivedBundle.getString(getString(R.string.details_title_key)),
                        mReceivedBundle.getString(getString(R.string.details_img_path_key)));

                /* Check the current status of the movie (starred/unstarred) */
                if (isMarkedAsFavorite != null) {
                    /* If this variable is defined, avoid checking the DB */
                    if (isMarkedAsFavorite) {
                        /* Remove from the DB */
                        moviesListViewModel.deleteMovie(getParent(), movieCpy);
                        /*DBUtils.deleteMovie(getParent(), movieCpy);*/
                        styleFavButtonAsUnmarked();
                        isMarkedAsFavorite = false;
                    } else {
                        /* Adds a copy of the movie to the DB */
                        moviesListViewModel.addMovie(getParent(), movieCpy);
                        /*DBUtils.addMovie(getParent(), movieCpy);*/
                        styleFavButtonAsMarked();
                        isMarkedAsFavorite = true;
                    }
                } else {
                    /* Here we have no choice but to fetch the info directly from the DB (since the 'isMarkedAsFavorite var isn't yet populated  */
                    /* Check if this movie's ID is present in the DB */
                    moviesListViewModel.getMovieByID(getParent(), mMovieID, new GetMovieCallback() {
                        @Override
                        public void getSingleMovie(Movie movie) {
                            if (movie != null) {
                                /* Movie is marked as favorite */
                                styleFavButtonAsUnmarked();
                                isMarkedAsFavorite = false;

                                /* Remove from the DB */
                                moviesListViewModel.deleteMovie(getParent(), movieCpy);
                                /*DBUtils.deleteMovie(getParent(), movieCpy);*/
                            } else {
                                isMarkedAsFavorite = true;

                                /* Adds a copy of the movie to the DB */
                                styleFavButtonAsMarked();
                                moviesListViewModel.addMovie(getParent(), movieCpy);
                                /*DBUtils.addMovie(getParent(), movieCpy);*/
                            }

                        }
                    });

                }
            }
        });
    }

    /**
     * Given that Picasso as a known bug with weak references making the images not loading
     * (or loading and being garbage collected before being displayed), a different approach
     * is required loading the backdrop image
     * Context: https://stackoverflow.com/a/26918731
     */
    private void bindBackdropImg() {
        final URL backdropURL = MovieUtils.getBackdropImgURL(mReceivedBundle.getString(getString(R.string.details_backdrop_path_key)));
        final LinearLayout mDetailsMovieTitle_container = (LinearLayout) findViewById(R.id.details_movie_title_container);

        final Target target = new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, final Picasso.LoadedFrom from) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mDetailsMovieTitle_container.setBackground(new BitmapDrawable(getResources(), bitmap));
                    mDetailsMovieTitle_container.getBackground().setAlpha(120);

                }
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                e.printStackTrace();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        /* Add backdrop as background of the title view */
        ImageLoader.loadImage(backdropURL, target);

        mDetailsMovieTitle_container.setTag(target);
    }

    private void styleFavButtonAsUnmarked() {
        mMovieFav_button.setText(R.string.details_mark_as_favorite);
        mMovieFav_button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    private void styleFavButtonAsMarked() {
        mMovieFav_button.setText(R.string.details_unmark_as_favorite);
        mMovieFav_button.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    /**
     * Checks if the movie is marked as favorite or not &
     * reflects that status in the "Mark as Favorite" button
     */
    private void checkFavoriteStatus() {
        /* Check if this movie's ID is present in the DB */
        moviesListViewModel.getMovieByID(this, mMovieID, new GetMovieCallback() {
            @Override
            public void getSingleMovie(Movie movie) {
                if (movie != null) {
                    /* Movie is marked as favorite */
                    isMarkedAsFavorite = true;

                    /* Anything that updates the UI needs to run in the main thread */
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            styleFavButtonAsMarked();
                        }
                    });
                } else {
                    isMarkedAsFavorite = false;
                    /* Anything that updates the UI needs to run in the main thread */
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            styleFavButtonAsUnmarked();
                        }
                    });
                }
            }


        });
    }


    /**
     * Returns a new instance of the adapter, containing the dataset provided
     *
     * @param dataset
     * @return
     */
    private RecyclerView.Adapter getNewVideosListAdapter(List<Video> dataset) {
        /*mMoviesList = sortMoviesOrder(dataset);*/
        mMovieVideosAdapter = new MovieVideosAdapter(dataset, new MovieVideosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Video video) {
                if (video.getSite().equals(Constants.YOUTUBE_SITE_LABEL))
                    openYoutubeVideoIntent(video);
            }
        });

        return mMovieVideosAdapter;
    }


    /**
     * Opens the review's URL in the web browser through an intent
     *
     * @param review
     */
    private void openReviewInBrowserIntent(Review review) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(review.getmURL()));

        startActivity(webIntent);
    }

    /**
     * Tries to open the video in the youtube app using an intent.
     * If it can't, tries to open via the web browser
     *
     * @param video
     */
    private void openYoutubeVideoIntent(Video video) {
        Intent ytAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + video.getKey()));
        Intent ytWebIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.YOUTUBE_VIDEOS_BASE_URL + video.getKey()));

        try {
            startActivity(ytAppIntent);
        } catch (ActivityNotFoundException ex) {
            /* If it can't open the app, tries to open through the browser */
            startActivity(ytWebIntent);
        }
    }


    /**
     * Returns a new instance of the adapter, containing the dataset provided
     *
     * @param dataset
     * @return
     */
    private RecyclerView.Adapter getNewReviewsListAdapter(List<Review> dataset) {
        /*mMoviesList = sortMoviesOrder(dataset);*/
        mMovieReviewsAdapter = new MovieReviewsAdapter(dataset, new MovieReviewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Review review) {
                openReviewInBrowserIntent(review);
            }
        });

        return mMovieReviewsAdapter;
    }

    /**
     * Feeds the new data to the adapter
     *
     * @param updatedDataset
     */
    private void updateReviewsAdapter(List<Review> updatedDataset) {
        mMovieReviewsAdapter.swap(updatedDataset);

        if (updatedDataset.isEmpty()) {
            findViewById(R.id.details_reviews_no_reviews).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.details_reviews_no_reviews).setVisibility(View.GONE);
        }
    }

    /**
     * Feeds the new data to the adapter
     *
     * @param updatedDataset
     */
    private void updateVideosAdapter(List<Video> updatedDataset) {
        mMovieVideosAdapter.swap(updatedDataset);

        if (updatedDataset.isEmpty()) {
            findViewById(R.id.details_videos_no_videos).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.details_videos_no_videos).setVisibility(View.GONE);
        }
    }
}
