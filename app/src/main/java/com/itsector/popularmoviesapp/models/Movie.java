/*
Copyright (c) 2019 ITSector Software. All rights reserved.
ITSector Software Confidential and Proprietary information. It is strictly forbidden for 3rd
parties to modify, decompile, disassemble, defeat, disable or circumvent any protection
mechanism; to sell, license, lease, rent, redistribute or make accessible to any third party,
whether for profit or without charge.
*/

package com.itsector.popularmoviesapp.models;

import com.itsector.popularmoviesapp.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by E936 on 4/11/2019.
 */
public class Movie implements Constants {
    private int mID;
    private String mOriginalTitle;
    private String mThumbnalImagePath;
    private String mBackdropImagePath;
    private String mPlotSynopsis;
    private double mVoteAverage;
    private String mReleaseDate;
    private double mPopularity;

    public Movie(int ID, String mOriginalTitle, String mThumbnalImagePath, String mBackdropImagePath, String mPlotSynopsis, double mVoteAverage, String releaseDate, double popularity) {
        this.mID = ID;
        this.mOriginalTitle = mOriginalTitle;
        this.mThumbnalImagePath = mThumbnalImagePath;
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
        return mThumbnalImagePath;
    }

    public double getPopularity() {
        return mPopularity;
    }

    public String getBackdropImgPath() { return mBackdropImagePath; }

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


}
