package com.lec.android.wswe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Loding extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loding);

        ImageView load = (ImageView)findViewById(R.id.logingWswe);
        Glide.with(this).load(R.raw.wswe4).into(load);

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
                        TempData tempData = TempData.getInstance();
                        tempData.setNotice(result);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("tempData", tempData);
                        startActivity(intent);  // 화면 전환
                        finish();   // intro 화면은 종료료
                    }
                });
            }
        }).start();

//        Handler mhandler = new Handler(){
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//
//            }
//        } ;

//        mhandler.sendEmptyMessageDelayed(1,2850);
    }
}
