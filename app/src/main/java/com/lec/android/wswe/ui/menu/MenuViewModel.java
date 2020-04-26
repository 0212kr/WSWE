package com.lec.android.wswe.ui.menu;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.lec.android.wswe.database.RestDAO;
import com.lec.android.wswe.database.RestDatabase;
import com.lec.android.wswe.database.RestRepository;
import com.lec.android.wswe.database.Restaurant;

import java.util.List;

public class MenuViewModel extends AndroidViewModel {

    private RestRepository restRepository;
    private LiveData<List<Restaurant>> allRest;

    public MenuViewModel(@NonNull Application application) {
        super(application);
        restRepository = new RestRepository(application);
        allRest = restRepository.getAllRest();
    }

    public void insert(Restaurant restaurant) {
        restRepository.insert(restaurant);
    }

    public void update(Restaurant restaurant) {
        restRepository.update(restaurant);
    }

    public void delete(Restaurant restaurant) {
        restRepository.delete(restaurant);
    }

    public void deleteAll(Restaurant restaurant) {
        restRepository.deleteAllRestaurant();
    }

    public LiveData<List<Restaurant>> getAllRest(){
        return allRest;
    }
}