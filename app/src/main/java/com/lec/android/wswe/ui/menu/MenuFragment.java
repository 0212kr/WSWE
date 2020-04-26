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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lec.android.wswe.R;
import com.lec.android.wswe.database.RestDAO;
import com.lec.android.wswe.database.RestDatabase;
import com.lec.android.wswe.database.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    private MenuViewModel menuViewModel;
    private Button btnAdd;
    private EditText etName, etPhone;
    private RatingBar stars;
    MenuAdapter menuAdapter;
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuViewModel =
                ViewModelProviders.of(getActivity()).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_menu, container, false);

        setHasOptionsMenu(true);

        recyclerView = root.findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        menuAdapter = new MenuAdapter();
        recyclerView.setAdapter(menuAdapter);

        menuViewModel.getAllRest().observe(getViewLifecycleOwner(), new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurants) {
                menuAdapter.setRestaurantList(restaurants);
            }
        });

        btnAdd = root.findViewById(R.id.btnAdd);
        etName = root.findViewById(R.id.etName);
        etPhone = root.findViewById(R.id.etPhone);
        stars = root.findViewById(R.id.stars);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
        menu.getItem(0).setVisible(false);
    }
}
