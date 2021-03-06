package com.lec.android.wswe.ui.cafe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.lec.android.wswe.R;

public class CafeFragment extends Fragment {

    private CafeViewModel cafeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cafeViewModel =
                ViewModelProviders.of(this).get(CafeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cafe, container, false);
        final TextView textView = root.findViewById(R.id.text_cafe);
        cafeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
