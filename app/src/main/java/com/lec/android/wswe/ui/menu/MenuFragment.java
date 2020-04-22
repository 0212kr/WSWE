package com.lec.android.wswe.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.lec.android.wswe.MainActivity;
import com.lec.android.wswe.R;

public class MenuFragment extends Fragment {

    private MenuViewModel menuViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuViewModel =
                ViewModelProviders.of(this).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_menu, container, false);



        return root;
    }

//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
////        inflater.inflate(R.menu.menu, menu);
//        menu.add(0, 1, 100, "메뉴추가");
//        menu.getItem(0).setVisible(false);
////        super.onCreateOptionsMenu(menu,);
//    }


    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.add(0, 1, 100, "메뉴추가");
        menu.getItem(0).setVisible(false);
    }
}
