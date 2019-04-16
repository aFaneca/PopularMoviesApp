/*
 * Copyright (c) 2019 ITSector Software. All rights reserved.
 * ITSector Software Confidential and Proprietary information. It is strictly forbidden for 3rd
 * parties to modify, decompile, disassemble, defeat, disable or circumvent any protection
 * mechanism; to sell, license, lease, rent, redistribute or make accessible to any third party,
 * whether for profit or without charge.
 */

package com.itsector.popularmoviesapp.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itsector.popularmoviesapp.R;
import com.itsector.popularmoviesapp.models.Review;
import com.itsector.popularmoviesapp.models.Video;

import java.util.List;

/**
 * Created by E936 on 4/16/2019.
 */
public class MovieVideosAdapter extends RecyclerView.Adapter<MovieVideosAdapter.VideosViewHolder> {
    private List<Video> mVideosList;
    private final OnItemClickListener listener;

    public MovieVideosAdapter(List<Video> mVideosList, OnItemClickListener listener) {
        this.mVideosList = mVideosList;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Video video);
    }

    /**
     * Swaps the adapter's dataset for the one provided
     * @param dataset - the new dataset
     */
    public void swap(List<Video> dataset)
    {
        mVideosList.clear();
        mVideosList.addAll(dataset);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VideosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        /* Inflate the custom layout */
        View view = inflater.inflate(R.layout.videos_list_item, viewGroup, false);

        VideosViewHolder videosViewHolder = new VideosViewHolder(view);

        return videosViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideosViewHolder videosViewHolder, int i) {
        Video video = mVideosList.get(i);

        videosViewHolder.bind(video, listener);
    }

    @Override
    public int getItemCount() {
        return mVideosList.size();
    }

    public static class VideosViewHolder extends RecyclerView.ViewHolder {
    public TextView videoTitle_text_view;
    public TextView videoSite_text_view;

        public VideosViewHolder(@NonNull View itemView) {
            super(itemView);
            videoTitle_text_view = (TextView) itemView.findViewById(R.id.video_title_text_view);
            videoSite_text_view = (TextView) itemView.findViewById(R.id.video_site_text_view);
        }

        public void bind(final Video video, final OnItemClickListener listener) {

            videoTitle_text_view.setText(video.getName());
            videoSite_text_view.setText(video.getSite());

            /* Bind the listener */
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(video);
                }
            });
        }
    }
}
