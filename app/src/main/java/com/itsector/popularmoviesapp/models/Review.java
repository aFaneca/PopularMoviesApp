/*
 * Copyright (c) 2019 ITSector Software. All rights reserved.
 * ITSector Software Confidential and Proprietary information. It is strictly forbidden for 3rd
 * parties to modify, decompile, disassemble, defeat, disable or circumvent any protection
 * mechanism; to sell, license, lease, rent, redistribute or make accessible to any third party,
 * whether for profit or without charge.
 */

package com.itsector.popularmoviesapp.models;

/**
 * Created by E936 on 4/16/2019.
 */
public class Review {
    private String mID;
    private String mAuthor;
    private String mContent;
    private String mURL;

    public Review(String mID, String mAuthor, String mContent, String mURL) {
        this.mID = mID;
        this.mAuthor = mAuthor;
        this.mContent = mContent;
        this.mURL = mURL;
    }

    /* GETTERS & SETTERS */

    public String getmID() {
        return mID;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmURL() {
        return mURL;
    }

    public void setmURL(String mURL) {
        this.mURL = mURL;
    }
}
