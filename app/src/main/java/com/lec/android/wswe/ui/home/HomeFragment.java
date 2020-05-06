package com.lec.android.wswe.ui.home;

import android.os.Bundle;
import android.os.Handler;
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private TextView text_home;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        text_home = root.findViewById(R.id.text_home);

        getWebsite();


        return root;
    }

    private void getWebsite() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    Document doc = Jsoup.connect("https://docs.google.com/document/d/1DRVTSLjwYd_Q1fv4rVpHi8J08EQbXLUAqG_kDsFqZew/edit?usp=sharing").get();
//                    String title = doc.title();
//                    Elements links = doc.select("#kix-appview > div.kix-appview-editor-container > div > div:nth-child(1) > div.kix-zoomdocumentplugin-outer > div > div > div > div:nth-child(2) > div > div.kix-page-content-wrapper > div:nth-child(1) > div > div > div:nth-child(1) > div");
                    Elements links = doc.getElementsByTag("meta");

//                    builder.append(title).append("\n");

                    for (Element link : links) {
                        if (link.attr("property").equals("og:description")) {
                            builder.append(link.attr("content"));
                        }
                    }

                } catch (Exception e) {
                    e.getStackTrace();
                    e.getMessage();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String s = builder.toString();
                        String[] ss = s.split("<br>");
                        String result = "";
                        for (int i = 0; i < ss.length; i++) {
                            result += ss[i] + "\n";
                        }
                        text_home.setText(result);
                    }
                });
            }
        }).start();
    }
}
