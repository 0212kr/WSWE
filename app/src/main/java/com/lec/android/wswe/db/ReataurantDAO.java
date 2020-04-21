package com.lec.android.wswe.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ReataurantDAO {
    @Query("SELECT * FROM RESTAURANT")
    List<Restaurant> getAll();

    @Insert
    void insert_rest(Restaurant restaurant);

    @Update
    void update_rest(Restaurant restaurant);

    @Delete
    void delete_rest(Restaurant restaurant);
}
