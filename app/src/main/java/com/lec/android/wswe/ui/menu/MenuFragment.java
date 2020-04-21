package com.lec.android.wswe.ui.menu;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lec.android.wswe.R;
import com.lec.android.wswe.db.Database;
import com.lec.android.wswe.db.ReataurantDAO;
import com.lec.android.wswe.db.Restaurant;

public class MenuFragment extends Fragment {

    private MenuViewModel menuViewModel;
    Dialog dig1;
    Database db = Database.getInstance(getContext());

    @SuppressLint("RestrictedApi")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuViewModel =
                ViewModelProviders.of(this).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_menu, container, false);

        dig1 = new Dialog(getContext());
        dig1.setContentView(R.layout.add_rest);
        dig1.setOwnerActivity(getActivity());
        dig1.setCanceledOnTouchOutside(true);

        FloatingActionButton addRest = root.findViewById(R.id.addRest);
        addRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dig1.show();
            }
        });

        Button btnAdd = dig1.findViewById(R.id.btnAdd);
        final EditText etName = dig1.findViewById(R.id.etName);
        final EditText etPhone = dig1.findViewById(R.id.etPhone);
        final RatingBar ratingBar = dig1.findViewById(R.id.addRating);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InsertAsyncTask(db.reataurantDAO()).execute(new Restaurant(
                        etName.getText().toString().trim(),
                        etPhone.getText().toString().trim(),
                        ratingBar.getNumStars()
                ));
            }
        });

//        final TextView textView = root.findViewById(R.id.text_menu);
//        menuViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    public static class InsertAsyncTask extends AsyncTask<Restaurant, Void, Void> {
        private ReataurantDAO mRestaurantDao;

        public InsertAsyncTask(ReataurantDAO mRestaurantDao) {
            this.mRestaurantDao = mRestaurantDao;
        }

        @Override
        protected Void doInBackground(Restaurant... restaurants) {
            mRestaurantDao.insert_rest(restaurants[0]);
            return null;
        }
    }
}
