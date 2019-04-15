/*
 * Copyright (c) 2019 ITSector Software. All rights reserved.
 * ITSector Software Confidential and Proprietary information. It is strictly forbidden for 3rd
 * parties to modify, decompile, disassemble, defeat, disable or circumvent any protection
 * mechanism; to sell, license, lease, rent, redistribute or make accessible to any third party,
 * whether for profit or without charge.
 */

package com.itsector.popularmoviesapp.activities;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itsector.popularmoviesapp.R;
import com.itsector.popularmoviesapp.utils.ImageLoader;
import com.itsector.popularmoviesapp.utils.MovieUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.net.URL;

public class DetailsActivity extends AppCompatActivity {
    private Bundle mReceivedBundle;
    private int mMovieID;

    private TextView mMovieTitle_text_view;
    private TextView mMovieYear_text_view;
    private TextView mMoviePopularity_text_view;
    private TextView mMovieRating_text_view;
    private TextView mDetails_movie_plot_synopsys_text_view;
    private Button mMovieFav_button;
    private ImageView mMovieThumbnail_image_view;
    private LinearLayout mDetailsMovieTitle_container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        /* Initialize all views */
        mMovieTitle_text_view = (TextView) findViewById(R.id.details_movie_title_text_view);
        mMovieYear_text_view = (TextView) findViewById(R.id.details_movie_year_text_view);
        mMoviePopularity_text_view = (TextView) findViewById(R.id.details_movie_popularity_text_view);
        mMovieRating_text_view = (TextView) findViewById(R.id.details_movie_rating_text_view);
        mDetails_movie_plot_synopsys_text_view = (TextView) findViewById(R.id.details_movie_plot_synopsys_text_view);
        mMovieThumbnail_image_view = (ImageView) findViewById(R.id.details_movie_thumbnail_image_view);
        mMovieFav_button = (Button) findViewById(R.id.details_movie_fav_button);
        mDetailsMovieTitle_container = (LinearLayout) findViewById(R.id.details_movie_title_container);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.details_label);

        bindViews();
    }



    /**
     * Bind all of the views with the data received
     */
    private void bindViews() {
        mReceivedBundle = getIntent().getExtras().getBundle(getString(R.string.details_bundle_key));
        mMovieID = mReceivedBundle.getInt(getString(R.string.details_id_key));
        URL url = MovieUtils.getImgURL(mReceivedBundle.getString(getString(R.string.details_img_path_key)));
        URL backdropURL = MovieUtils.getBackdropImgURL(mReceivedBundle.getString(getString(R.string.details_backdrop_path_key)));

        /* Bind the views */
        mMovieTitle_text_view.setText(mReceivedBundle.getString(getString(R.string.details_title_key)));
        mMovieYear_text_view.setText("" + mReceivedBundle.getInt(getString(R.string.details_year_key)));
        mMoviePopularity_text_view.setText(getString(R.string.details_popularity, mReceivedBundle.getDouble(getString(R.string.details_popularity_key))));
        mDetails_movie_plot_synopsys_text_view.setText(mReceivedBundle.getString(getString(R.string.details_synopsis_key)));
        mMovieRating_text_view.setText(getString(R.string.details_rating, mReceivedBundle.getDouble(getString(R.string.details_rating_key))));

        /* Associate image with the imageview */
        ImageLoader.loadImage(url, mMovieThumbnail_image_view);

        /* Add backdrop as background of the title view */
        Picasso.get().load(String.valueOf(backdropURL)).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
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
        });

        mMovieFav_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: listener
            }
        });
    }
}
