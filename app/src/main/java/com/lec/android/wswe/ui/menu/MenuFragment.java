package com.lec.android.wswe.ui.menu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.lec.android.wswe.R;
import com.lec.android.wswe.database.RestDAO;
import com.lec.android.wswe.database.RestDatabase;
import com.lec.android.wswe.database.Restaurant;

public class MenuFragment extends Fragment {

    private Button btnAdd;
    private EditText etName, etPhone;
    private RatingBar stars;
    RestDatabase db = RestDatabase.getInstance(getActivity());

    private MenuViewModel menuViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuViewModel =
                ViewModelProviders.of(this).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_menu, container, false);

        setHasOptionsMenu(true);

        btnAdd = root.findViewById(R.id.btnAdd);
        etName = root.findViewById(R.id.etName);
        etPhone = root.findViewById(R.id.etPhone);
        stars = root.findViewById(R.id.stars);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = "";
                String phone = "";
                float star=0;
                try {
                    name = etName.getText().toString().trim();
                    phone = etPhone.getText().toString().trim();
                    star = stars.getRating();

                    if (name.equals("")) {
                        Toast.makeText(v.getContext(), "식당 이름은 필수입니다.", Toast.LENGTH_LONG).show();
                        return;
                    } else if (phone.equals("")) {
                        phone = "입력 없음";
                    }

                    new InsertAsynkTask(db.restDAO()).execute(new Restaurant(name, phone, star));

                } catch (Exception e) {
                    Toast.makeText(v.getContext(), "오류가 발생했습니다. 다시 시도해 주세요", Toast.LENGTH_LONG).show();
                }
            }
        });

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
        menu.getItem(0).setVisible(false);
    }

    public static class InsertAsynkTask extends AsyncTask<Restaurant, Void, Void> {
        private RestDAO mRestDao;

        public InsertAsynkTask(RestDAO RestDao) {
            this.mRestDao = RestDao;
        }

        @Override
        protected Void doInBackground(Restaurant... restaurants) {
            mRestDao.insert(restaurants[0]);

            return null;
        }
    }

}
