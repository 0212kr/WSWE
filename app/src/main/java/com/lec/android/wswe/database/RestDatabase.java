package com.lec.android.wswe.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Restaurant.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class RestDatabase extends RoomDatabase {
    public abstract RestDAO restDAO();

    private static RestDatabase INSTANCE;
    public static synchronized RestDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RestDatabase.class, "wsweDB")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return INSTANCE;
    } // end getInstance

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        } // end onCreate()
    }; // end RoomDatabase.Callback

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private RestDAO restDAO;

        public PopulateDbAsyncTask(RestDatabase db) {
            restDAO = db.restDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            restDAO.insert(new Restaurant("test 1", "000-0000-0000", 3));
            restDAO.insert(new Restaurant("test 2", "010-1111-1111", 1));
            restDAO.insert(new Restaurant("test 3", "002-2222-2222", 3));
            return null;
        }
    }


} // end RestDatabase
