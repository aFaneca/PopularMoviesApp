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
    private String mPlotSynopsys;
    private double mVoteAverage;
    private String mReleaseDate;
    private int mRuntime;

    public Movie(int ID, String mOriginalTitle, String mThumbnalImagePath, String mPlotSynopsys, double mVoteAverage, String releaseDate, int runtime) {
        this.mID = ID;
        this.mOriginalTitle = mOriginalTitle;
        this.mThumbnalImagePath = mThumbnalImagePath;
        this.mPlotSynopsys = mPlotSynopsys;
        this.mVoteAverage = mVoteAverage;
        this.mReleaseDate = releaseDate;
        this.mRuntime = runtime;
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

    public String getImgPath() {
        return mThumbnalImagePath;
    }

    public int getRuntime() {
        return mRuntime;
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


}
