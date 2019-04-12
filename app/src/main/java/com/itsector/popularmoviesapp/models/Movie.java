/*
Copyright (c) 2019 ITSector Software. All rights reserved.
ITSector Software Confidential and Proprietary information. It is strictly forbidden for 3rd
parties to modify, decompile, disassemble, defeat, disable or circumvent any protection
mechanism; to sell, license, lease, rent, redistribute or make accessible to any third party,
whether for profit or without charge.
*/

package com.itsector.popularmoviesapp.models;

import com.itsector.popularmoviesapp.utils.Constants;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by E936 on 4/11/2019.
 */
public class Movie implements Constants{
    private String mOriginalTitle;
    private String mThumbnalImagePath;
    private String mPlotSynopsys;
    private double mVoteAverage;
    private String mReleaseDate;

    public Movie(String mOriginalTitle, String mThumbnalImagePath, String mPlotSynopsys, double mVoteAverage, String releaseDate) {
        this.mOriginalTitle = mOriginalTitle;
        this.mThumbnalImagePath = mThumbnalImagePath;
        this.mPlotSynopsys = mPlotSynopsys;
        this.mVoteAverage = mVoteAverage;
        this.mReleaseDate = releaseDate;
    }




    /* Getters & Setters */

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public String getPlotSynopsys() {
        return mPlotSynopsys;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getImgPath() { return mThumbnalImagePath; }
}
