package com.lec.android.wswe.ui.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.lec.android.wswe.R;

public class AddRestaurant extends AppCompatActivity {
    EditText etName, etPhone;
    RatingBar stars;
    Button btnAdd;

//    public static final String EXTRA_NAME = "com.lec.android.wswe.ui.menu.EXTRA_NAME";
//    public static final String EXTRA_PHONE =  "com.lec.android.wswe.ui.menu.EXTRA_PHONE";
//    public static final String EXTRA_STARS =  "com.lec.android.wswe.ui.menu.EXTRA_STARS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        stars = findViewById(R.id.stars);
        btnAdd = findViewById(R.id.btnAdd);

    } // end onCreate()


    private void saveRest(){
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        float starNum = stars.getRating();

        if (name.trim().isEmpty()) {
            Toast.makeText(this, "식당 이름은 필수항목 입니다.", Toast.LENGTH_SHORT).show();
            return;
        } // end if
        Intent data = new Intent();
//        data.putExtra(EXTRA_NAME, name);
//        data.putExtra(EXTRA_PHONE, phone);
//        data.putExtra(EXTRA_STARS, starNum);

        setResult(RESULT_OK, data);
        finish();
    } // end saveRest()
} // end Activity
