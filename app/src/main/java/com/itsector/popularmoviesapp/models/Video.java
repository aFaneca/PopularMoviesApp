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
public class Video {
    private int mID;
    private String mKey;
    private String mName;
    private String mType;
    private String mSite;

    public Video(int mID, String mKey, String mName, String mType, String mSite) {
        this.mID = mID;
        this.mKey = mKey;
        this.mName = mName;
        this.mType = mType;
        this.mSite = mSite;
    }

    /* GETTERS & SETTERS */

    public int getID() {
        return mID;
    }

    public void setID(int mID) {
        this.mID = mID;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String mKey) {
        this.mKey = mKey;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public String getSite() {
        return mSite;
    }

    public void setSite(String mSite) {
        this.mSite = mSite;
    }
}
