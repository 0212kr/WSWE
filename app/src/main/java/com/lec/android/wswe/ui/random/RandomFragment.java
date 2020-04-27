package com.lec.android.wswe.ui.random;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lec.android.wswe.R;
import com.lec.android.wswe.database.Restaurant;
import com.lec.android.wswe.ui.menu.MenuViewModel;

import java.util.ArrayList;
import java.util.List;

public class RandomFragment extends Fragment {

    private MenuViewModel mViewModel;
    RecyclerView recyclerView;
    RandomAdapter randomAdapter;

    public static RandomFragment newInstance() {
        return new RandomFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(getActivity()).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.random_fragment, container, false);
        setHasOptionsMenu(true);

        recyclerView = root.findViewById(R.id.randomList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(manager);
        randomAdapter = new RandomAdapter();
        recyclerView.setAdapter(randomAdapter);

        mViewModel.getAllRest().observe(getViewLifecycleOwner(), new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurants) {
                randomAdapter.setRandomList(restaurants);
            }
        });

        final Button randomStart = root.findViewById(R.id.randStart);
        final Button randomStop = root.findViewById(R.id.randStop);
        randomStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomAdapter.randomStart();
                randomStart.setVisibility(View.INVISIBLE);
                randomStop.setVisibility(View.VISIBLE);
            }
        });
        randomStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomAdapter.randomStop();
                randomStart.setVisibility(View.VISIBLE);
                randomStop.setVisibility(View.INVISIBLE);
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}
