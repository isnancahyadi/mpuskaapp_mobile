package com.arcmedtek.mpuskaapp_mobile.activity.user.lecture;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.model.TeacherModel;
import com.arcmedtek.mpuskaapp_mobile.service.MPuskaDataService;

import java.util.ArrayList;

public class Course extends AppCompatActivity {

    TextView _collegeYear, _nameCourse, _codeCourse;
    ImageView _btnBack;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        _collegeYear = findViewById(R.id.txt_college_year_course);
        _nameCourse = findViewById(R.id.txt_name_course);
        _codeCourse = findViewById(R.id.txt_code_course);
        _btnBack = findViewById(R.id.btn_back_course);

        _collegeYear.setText("TA. " + getIntent().getStringExtra("college_year"));
        _nameCourse.setText(getIntent().getStringExtra("course_name") + " (" + getIntent().getStringExtra("classroom") + ")");
        _codeCourse.setText(String.valueOf(getIntent().getStringExtra("course_code")));

        _btnBack.setOnClickListener(v -> onBackPressed());
    }
}