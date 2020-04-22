package com.lec.android.wswe.database;

import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Restaurant.class}, version = 1)
public abstract class Database extends RoomDatabase {
    public abstract RestDAO restDAO();
}
