package com.arcmedtek.mpuskaapp_mobile.activity.user.lecture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextClock;

import com.arcmedtek.mpuskaapp_mobile.R;

public class LectureDashboard extends AppCompatActivity {

    TextClock dateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_dashboard);

        dateTime = findViewById(R.id.date_time_lecture_dashboard);

        Typeface tf = ResourcesCompat.getFont(LectureDashboard.this, R.font.roboto_black);
        dateTime.setTypeface(tf);
    }
}