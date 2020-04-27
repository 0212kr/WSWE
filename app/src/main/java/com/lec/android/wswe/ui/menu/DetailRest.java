package com.lec.android.wswe.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.lec.android.wswe.R;

public class DetailRest extends AppCompatActivity {
    /**
     * @PrimaryKey(autoGenerate = true)
     *         private int rest_id;
     * @NonNull private String rest_name;
     *         private String telephone;
     *         private float star;
     *         private int visited;
     *         private Date recentDate;
     *         private double latitude;
     *         private double longitude;
     * @Ignore private int randomNum;
     */
    public static final String EXTRA_ID = "com.lec.android.wswe.ui.menu.EXTRA_ID";
    public static final String EXTRA_NAME = "com.lec.android.wswe.ui.menu.EXTRA_NAME";
    public static final String EXTRA_PHONE = "com.lec.android.wswe.ui.menu.EXTRA_PHONE";
    public static final String EXTRA_STARS = "com.lec.android.wswe.ui.menu.EXTRA_STARS";
    public static final String EXTRA_VISITED = "com.lec.android.wswe.ui.menu.EXTRA_VISITED";
    public static final String EXTRA_RECENTDATE = "com.lec.android.wswe.ui.menu.EXTRA_RECENTDATE";
    public static final String EXTRA_LATITUDE = "com.lec.android.wswe.ui.menu.EXTRA_LATITUDE";
    public static final String EXTRA_LONGITUDE = "com.lec.android.wswe.ui.menu.EXTRA_LONGITUDE";
    public static final String EXTRA_RANDOMNUM = "com.lec.android.wswe.ui.menu.EXTRA_RANDOMNUM";

    TextView tvId, tvName, tvPhone, tvVisited, tvRecentdate, tvLatitude, tvLongitude, tvRandomnum;
    RatingBar showStar;
    ImageView imageView;
    ActionBar actionBar;
    String restName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_rest);


        tvId = findViewById(R.id.tvDetailId);
        tvName = findViewById(R.id.tvDetailName);
        tvPhone = findViewById(R.id.tvDetailPhone);
        tvVisited = findViewById(R.id.detailVisited);
        tvRecentdate = findViewById(R.id.detailRecentDate);
        tvLatitude = findViewById(R.id.detailLatitude);
        tvLongitude = findViewById(R.id.detailLongitude);
        tvRandomnum = findViewById(R.id.detailRandomNum);
        showStar = findViewById(R.id.detailStar);

        Intent intent = getIntent();
        Log.d("myLog", "" + intent.getIntExtra(EXTRA_ID, -1));
        tvId.setText("" + intent.getIntExtra(EXTRA_ID, 1));
        restName = intent.getStringExtra(EXTRA_NAME);
        tvName.setText(restName);
        tvPhone.setText(intent.getStringExtra(EXTRA_PHONE));
        tvVisited.setText(""+intent.getIntExtra(EXTRA_VISITED, 0));
        tvRecentdate.setText(intent.getStringExtra(EXTRA_RECENTDATE));
        tvLatitude.setText(String.format("%f", intent.getDoubleExtra(EXTRA_LATITUDE, 0)));
        tvLongitude.setText(String.format("%f", intent.getDoubleExtra(EXTRA_LONGITUDE, 0)));
        tvRandomnum.setText(""+intent.getIntExtra(EXTRA_RANDOMNUM, 0));
        showStar.setRating(intent.getFloatExtra(EXTRA_STARS, 5));
    }
}
