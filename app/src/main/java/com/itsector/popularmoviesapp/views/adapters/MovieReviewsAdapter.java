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

import java.util.List;

/**
 * Created by E936 on 4/16/2019.
 */
public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.ReviewsViewHolder> {
    private List<Review> mReviewsList;
    private final OnItemClickListener listener;

    public MovieReviewsAdapter(List<Review> mReviewsList, OnItemClickListener listener) {
        this.mReviewsList = mReviewsList;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Review review);
    }

    /**
     * Swaps the adapter's dataset for the one provided
     * @param dataset - the new dataset
     */
    public void swap(List<Review> dataset)
    {
        mReviewsList.clear();
        mReviewsList.addAll(dataset);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        /* Inflate the custom layout */
        View view = inflater.inflate(R.layout.reviews_list_item, viewGroup, false);

        ReviewsViewHolder reviewsViewHolder = new ReviewsViewHolder(view);

        return reviewsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder reviewsViewHolder, int i) {
        Review review = mReviewsList.get(i);

        reviewsViewHolder.bind(review, listener);
    }

    @Override
    public int getItemCount() {
        return mReviewsList.size();
    }

    public static class ReviewsViewHolder extends RecyclerView.ViewHolder {
    public TextView reviewAuthor_text_view;
    public TextView reviewContent_text_view;

        public ReviewsViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewAuthor_text_view = (TextView) itemView.findViewById(R.id.review_author_text_view);
            reviewContent_text_view = (TextView) itemView.findViewById(R.id.review_content_text_view);
        }

        public void bind(final Review review, final OnItemClickListener listener) {

            reviewAuthor_text_view.setText(review.getmAuthor());
            reviewContent_text_view.setText(review.getmContent());

            /* Bind the listener */
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(review);
                }
            });
        }
    }
}
