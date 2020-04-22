package com.lec.android.wswe.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Restaurant.class}, version = 1)
public abstract class Database extends RoomDatabase {
    private static Database INSTANCE;

    public abstract RestDAO restDAO();

    public static Database getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, Database.class, "wsweDB").build();
        }
        return INSTANCE;
    } // end getInstance

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
