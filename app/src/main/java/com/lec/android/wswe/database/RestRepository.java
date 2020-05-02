package com.lec.android.wswe.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RestRepository {
    private RestDAO restDAO;
    private LiveData<List<Restaurant>> allRest;

    public RestRepository(Application application) {
        RestDatabase db = RestDatabase.getInstance(application);
        restDAO = db.restDAO();
        allRest = restDAO.getAll();
    } // end RestRepository()

    public void insert(Restaurant restaurant) {
        new InsertAsyncTask(restDAO).execute(restaurant);
    } // end insert()

    public void update(Restaurant restaurant) {
        new UpdateAsyncTask(restDAO).execute(restaurant);
    } // end update()

    public void delete(Restaurant restaurant) {
        new DeleteAsyncTask(restDAO).execute(restaurant);
    } // end delete()

    public void deleteAllRestaurant() {
        new DeleteAllAsyncTask(restDAO).execute();
    } // end deleteAll()

    public LiveData<List<Restaurant>> getAllRest(){
        return allRest;
    } // end getAllRest()

    public class InsertAsyncTask extends AsyncTask<Restaurant, Void, Void> {
        private RestDAO mRestDao;

        public InsertAsyncTask(RestDAO RestDao) {
            this.mRestDao = RestDao;
        }

        @Override
        protected Void doInBackground(Restaurant... restaurants) {
            mRestDao.insert(restaurants[0]);
            return null;
        }

    } // end InsertAsyncTask

    public class UpdateAsyncTask extends AsyncTask<Restaurant, Void, Void> {
        private RestDAO mRestDao;

        public UpdateAsyncTask(RestDAO RestDao) {
            this.mRestDao = RestDao;
        }

        @Override
        protected Void doInBackground(Restaurant... restaurants) {
            mRestDao.update(restaurants[0]);
            return null;
        }

    } // end UpdateAsyncTask

    public class DeleteAsyncTask extends AsyncTask<Restaurant, Void, Void> {
        private RestDAO mRestDao;

        public DeleteAsyncTask(RestDAO RestDao) {
            this.mRestDao = RestDao;
        }

        @Override
        protected Void doInBackground(Restaurant... restaurants) {
            mRestDao.delete(restaurants[0]);
            return null;
        }

    } // end DeleteAsyncTask

    public class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private RestDAO mRestDao;

        public DeleteAllAsyncTask(RestDAO RestDao) {
            this.mRestDao = RestDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mRestDao.deleteAll();
            return null;
        }

    } // end DeleteAllAsyncTask

} // end Class
