/*
 * Copyright (c) 2019 ITSector Software. All rights reserved.
 * ITSector Software Confidential and Proprietary information. It is strictly forbidden for 3rd
 * parties to modify, decompile, disassemble, defeat, disable or circumvent any protection
 * mechanism; to sell, license, lease, rent, redistribute or make accessible to any third party,
 * whether for profit or without charge.
 */

package com.itsector.popularmoviesapp.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.itsector.popularmoviesapp.models.Movie;
import com.itsector.popularmoviesapp.utils.Constants;

/**
 * Part of Room's implementation architecture
 * Follows the singleton pattern to provide a single instance of the DB to the whole app
 * Created by E936 on 4/15/2019.
 */

@Database(entities = {Movie.class}, version = 3, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase implements Constants {
    private static MovieDatabase sDBInstance;

    public abstract DaoAccess daoAccess();

    public static MovieDatabase getMovieDatabase(Context context){
        if(sDBInstance == null){
            initializeDB(context);
        }

        return sDBInstance;
    }

    private static void initializeDB(Context context) {
        sDBInstance = Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class, DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    public static void destroyInstace(){
        sDBInstance = null;
    }
}
