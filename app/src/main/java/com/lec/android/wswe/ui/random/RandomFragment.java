package com.lec.android.wswe.ui.random;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lec.android.wswe.R;
import com.lec.android.wswe.database.Restaurant;
import com.lec.android.wswe.ui.menu.MenuViewModel;

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
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        randomAdapter = new RandomAdapter(this.getContext(), root);
        mViewModel.getAllRest().observe(getViewLifecycleOwner(), new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurants) {
                randomAdapter.setRandomList(restaurants);
            }
        });
        recyclerView.setAdapter(randomAdapter);

        final Button randomStart = root.findViewById(R.id.randStart);
        final Button randomStop = root.findViewById(R.id.randStop);
        randomStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (randomAdapter.randomStart()) {
                    randomStart.setVisibility(View.INVISIBLE);
                    randomStop.setVisibility(View.VISIBLE);
                    randomStop.setClickable(false);
                    new AsyncTask<Void, Integer, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            for (int i = 3; i > 0; i--) {
                                publishProgress(i);
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            return null;
                        }

                        @Override
                        protected void onProgressUpdate(Integer... values) {
                            super.onProgressUpdate(values);
                            randomStop.setText("" + values[0]);
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            randomStop.setText("멈추기");
                            randomStop.setClickable(true);
                        }
                    }.execute();
                }
            }
        });
        randomStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomAdapter.randomStop();
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}
