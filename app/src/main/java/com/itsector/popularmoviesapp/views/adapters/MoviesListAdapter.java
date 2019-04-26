/*
Copyright (c) 2019 ITSector Software. All rights reserved.
ITSector Software Confidential and Proprietary information. It is strictly forbidden for 3rd
parties to modify, decompile, disassemble, defeat, disable or circumvent any protection
mechanism; to sell, license, lease, rent, redistribute or make accessible to any third party,
whether for profit or without charge.
*/
package com.itsector.popularmoviesapp.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itsector.popularmoviesapp.R;
import com.itsector.popularmoviesapp.models.Movie;
import com.itsector.popularmoviesapp.utils.ImageLoader;
import com.itsector.popularmoviesapp.utils.MovieUtils;

import java.net.URL;
import java.util.List;

/**
 * Created by E936 on 4/11/2019.
 */
public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MoviesViewHolder> {
    private List<Movie> mMoviesList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }

    public MoviesListAdapter(List<Movie> moviesList, OnItemClickListener listener) {
        this.mMoviesList = moviesList;
        this.listener = listener;
    }

    /**
     * Swaps the adapter's dataset for the one provided
     * @param dataset - the new dataset
     */
    public void swap(List<Movie> dataset)
    {
        mMoviesList.clear();
        mMoviesList.addAll(dataset);
        notifyDataSetChanged();
    }

    /* Create the new views */
    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        /* Inflate the custom layout */
        View view = inflater.inflate(R.layout.movie_list_item, viewGroup, false);

        MoviesViewHolder moviesViewHolder = new MoviesViewHolder(view);

        return moviesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder viewHolder, int i) {
        Movie movie = mMoviesList.get(i);

        /* Bind with view & OnItemClickListener */
        viewHolder.bind(movie, listener);
    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }


    public static class MoviesViewHolder extends RecyclerView.ViewHolder {
        public ImageView movieThumbnal_image_view;
        public TextView movieTitle_text_view;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            movieThumbnal_image_view = itemView.findViewById(R.id.movie_thumbnail_image_view);
            movieTitle_text_view = itemView.findViewById(R.id.movie_title_text_view);
        }

        /**
         * Binds a movie object with its views & an OnItemClickListener
         * @param movie
         * @param listener
         */
        public void bind(final Movie movie, final OnItemClickListener listener){
            URL url = MovieUtils.getImgURL(movie.getImgPath());

            /* Associate image with the imageview */
            ImageLoader.loadImage(url, this.movieThumbnal_image_view);

            /* Associate title with textview */
            this.movieTitle_text_view.setText(movie.getOriginalTitle());

            /* Bind the listener */
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    listener.onItemClick(movie);
                }
            });
        }
    }

}
