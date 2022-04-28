package com.arcmedtek.mpuskaapp_mobile.activity.user.lecture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.model.TeacherModel;
import com.arcmedtek.mpuskaapp_mobile.service.MPuskaDataService;

import java.util.ArrayList;

public class Course extends AppCompatActivity {

    TextView _collegeYear, _nameCourse, _codeCourse, _classroom;
    ImageView _btnBack;
    String _strCollegeYear, _strNameCourse, _strCodeCourse, _strClassroom;

    CardView _btnStudentList;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        _strCollegeYear = getIntent().getStringExtra("college_year");
        _strNameCourse = getIntent().getStringExtra("course_name");
        _strClassroom = getIntent().getStringExtra("classroom");
        _strCodeCourse = getIntent().getStringExtra("course_code");

        _collegeYear = findViewById(R.id.txt_college_year_course);
        _nameCourse = findViewById(R.id.txt_name_course);
        _codeCourse = findViewById(R.id.txt_code_course);
        _classroom = findViewById(R.id.txt_classroom);
        _btnBack = findViewById(R.id.btn_back_course);
        _btnStudentList = findViewById(R.id.btn_student_list);

        _collegeYear.setText("TA. " + _strCollegeYear);
        _nameCourse.setText(_strNameCourse);
        _classroom.setText(_strClassroom);
        _codeCourse.setText(_strCodeCourse);

        _btnStudentList.setOnClickListener(v -> {
            Intent intent = new Intent(Course.this, StudentList.class);
            intent.putExtra("college_year", _strCollegeYear);
            intent.putExtra("course_name", _strNameCourse);
            intent.putExtra("classroom", _strClassroom);
            intent.putExtra("course_code", _strCodeCourse);
            startActivity(intent);
        });

        _btnBack.setOnClickListener(v -> onBackPressed());
    }
}