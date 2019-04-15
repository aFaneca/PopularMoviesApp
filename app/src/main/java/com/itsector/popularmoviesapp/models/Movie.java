/*
Copyright (c) 2019 ITSector Software. All rights reserved.
ITSector Software Confidential and Proprietary information. It is strictly forbidden for 3rd
parties to modify, decompile, disassemble, defeat, disable or circumvent any protection
mechanism; to sell, license, lease, rent, redistribute or make accessible to any third party,
whether for profit or without charge.
*/

package com.itsector.popularmoviesapp.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.itsector.popularmoviesapp.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by E936 on 4/11/2019.
 */

@Entity
public class Movie implements Constants {
    @NonNull
    @PrimaryKey
    private int mID;
    private String mOriginalTitle;
    private String mThumbnailImagePath;
    @Ignore
    private String mBackdropImagePath;
    @Ignore
    private String mPlotSynopsis;
    @Ignore
    private double mVoteAverage;
    @Ignore
    private String mReleaseDate;
    @Ignore
    private double mPopularity;

    public Movie(int mID, String mOriginalTitle, String mThumbnailImagePath) {
        this.mID = mID;
        this.mOriginalTitle = mOriginalTitle;
        this.mThumbnailImagePath = mThumbnailImagePath;
    }

    public Movie(int ID, String mOriginalTitle, String mThumbnailImagePath, String mBackdropImagePath, String mPlotSynopsis, double mVoteAverage, String releaseDate, double popularity) {
        this.mID = ID;
        this.mOriginalTitle = mOriginalTitle;
        this.mThumbnailImagePath = mThumbnailImagePath;
        this.mBackdropImagePath = mBackdropImagePath;
        this.mPlotSynopsis = mPlotSynopsis;
        this.mVoteAverage = mVoteAverage;
        this.mReleaseDate = releaseDate;
        this.mPopularity = popularity;
    }




    /* Getters & Setters */

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public String getPlotSynopsis() {
        return mPlotSynopsis;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getImgPath() {
        return mThumbnailImagePath;
    }

    public double getPopularity() {
        return mPopularity;
    }

    public String getBackdropImgPath() { return mBackdropImagePath; }

    public String getThumbnailImagePath() {
        return mThumbnailImagePath;
    }

    public void setmThumbnalImagePath(String mThumbnalImagePath) {
        this.mThumbnailImagePath = mThumbnalImagePath;
    }

    /**
     * Converts the releaseDate String into a Date object & extracts the year
     * @return the year
     */
    public int getYear(){
        /* Convert string into date object */
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(mReleaseDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            return cal.get(Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }


    }

    public int getID(){
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public void setmOriginalTitle(String mOriginalTitle) {
        this.mOriginalTitle = mOriginalTitle;
    }
}
