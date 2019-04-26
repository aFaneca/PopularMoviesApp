/*
 * Copyright (c) 2019 ITSector Software. All rights reserved.
 * ITSector Software Confidential and Proprietary information. It is strictly forbidden for 3rd
 * parties to modify, decompile, disassemble, defeat, disable or circumvent any protection
 * mechanism; to sell, license, lease, rent, redistribute or make accessible to any third party,
 * whether for profit or without charge.
 */

package com.itsector.popularmoviesapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import com.itsector.popularmoviesapp.utils.Constants;

/**
 * Created by E936 on 4/23/2019.
 */
public class YoutubePlayerFragment extends YouTubePlayerSupportFragment implements YouTubePlayer.OnInitializedListener {

    private static YouTubePlayer youtubePlayer;
    private static String videoID;
    private static int currPlayTime;

    public YoutubePlayerFragment newInstance(final String vID) {
        videoID = vID;
        YoutubePlayerFragment youTubePlayerFragment = new YoutubePlayerFragment();
        youTubePlayerFragment.initialize(Constants.YOUTUBE_PLAYER_API_KEY, this);

        return youTubePlayerFragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (videoID != null)
            bundle.putString(Constants.YOUTUBE_PLAYER_VIDEO_ID_KEY, videoID);
        if (youtubePlayer != null)
            bundle.putInt(Constants.YOUTUBE_PLAYER_VIDEO_CURR_TIME_KEY, youtubePlayer.getCurrentTimeMillis());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            videoID = savedInstanceState.getString(Constants.YOUTUBE_PLAYER_VIDEO_ID_KEY, videoID);
            currPlayTime = savedInstanceState.getInt(Constants.YOUTUBE_PLAYER_VIDEO_CURR_TIME_KEY, -1);
        }

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored && youTubePlayer != null && videoID != null) {
            youtubePlayer = youTubePlayer;
            youTubePlayer.cueVideo(videoID);

            /* If the video was already playing (e.g. before a rotation), go back to that time */
            if(currPlayTime != -1)
                youTubePlayer.seekToMillis(currPlayTime);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
