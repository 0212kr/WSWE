package com.lec.android.wswe.db;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Restaurant.class}, version = 1)
public abstract class Database extends RoomDatabase {
    private static Database instance;
    public abstract ReataurantDAO reataurantDAO();

    public static Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, Database.class, "wswe_db").build();
        }
        return instance;
    }

    public static void destroyDb(){
        instance = null;
    }
}
