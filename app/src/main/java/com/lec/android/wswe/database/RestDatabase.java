package com.lec.android.wswe.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Restaurant.class}, version = 1)
public abstract class RestDatabase extends RoomDatabase {
    private static RestDatabase INSTANCE;

    public abstract RestDAO restDAO();

    public static RestDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, RestDatabase.class, "wsweDB").build();
        }
        return INSTANCE;
    } // end getInstance

    public static void destroyInstance() {
        INSTANCE = null;
    }

} // end RestDatabase
