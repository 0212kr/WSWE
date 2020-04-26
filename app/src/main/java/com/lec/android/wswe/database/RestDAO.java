package com.lec.android.wswe.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RestDAO {
    @Query("SELECT * FROM RESTAURANT")
    LiveData<List<Restaurant>> getAll();

    @Insert
    void insert(Restaurant restaurant);

    @Update
    void update(Restaurant restaurant);

    @Delete
    void delete(Restaurant restaurant);

    @Query("DELETE FROM RESTAURANT")
    void deleteAll();
}
