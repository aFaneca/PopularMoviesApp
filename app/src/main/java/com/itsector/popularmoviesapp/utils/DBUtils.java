/*
 * Copyright (c) 2019 ITSector Software. All rights reserved.
 * ITSector Software Confidential and Proprietary information. It is strictly forbidden for 3rd
 * parties to modify, decompile, disassemble, defeat, disable or circumvent any protection
 * mechanism; to sell, license, lease, rent, redistribute or make accessible to any third party,
 * whether for profit or without charge.
 */

package com.itsector.popularmoviesapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;



/**
 * Created by E936 on 4/15/2019.
 */
public class DBUtils implements Constants{


    public static String getSortOrderFromSharedPreferences(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String sortOrder = prefs.getString(SORT_ORDER_PREF_KEY, SORT_ORDER_DEFAULT);

        return sortOrder;
    }
    public static String getVideoPlayerFromSharedPreferences(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String videoPlayer = prefs.getString(VIDEOS_PREF_KEY, VIDEOS_DEFAULT);

        return videoPlayer;
    }

}
