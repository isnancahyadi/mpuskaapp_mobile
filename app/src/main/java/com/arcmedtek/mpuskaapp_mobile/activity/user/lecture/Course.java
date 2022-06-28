package com.arcmedtek.mpuskaapp_mobile.activity.user.lecture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arcmedtek.mpuskaapp_mobile.R;

public class Course extends AppCompatActivity {

    TextView _collegeYear, _nameCourse, _codeCourse, _classroom;
    ImageView _btnBack;
    String _strCollegeYear, _strNameCourse, _strCodeCourse, _strClassroom, _strIdTeacher;

    CardView _btnStudentList, _btnInputValue, _btnAssessment;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        _strCollegeYear = getIntent().getStringExtra("college_year");
        _strNameCourse = getIntent().getStringExtra("course_name");
        _strClassroom = getIntent().getStringExtra("classroom");
        _strCodeCourse = getIntent().getStringExtra("course_code");
        _strIdTeacher = getIntent().getStringExtra("ID_teacher");

        _collegeYear = findViewById(R.id.txt_college_year_course);
        _nameCourse = findViewById(R.id.txt_name_course);
        _codeCourse = findViewById(R.id.txt_code_course);
        _classroom = findViewById(R.id.txt_classroom);
        _btnBack = findViewById(R.id.btn_back_course);
        _btnStudentList = findViewById(R.id.btn_student_list);
        _btnInputValue = findViewById(R.id.btn_input_value);
        _btnAssessment = findViewById(R.id.btn_assessment);

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
            intent.putExtra("ID_teacher", _strIdTeacher);
            startActivity(intent);
        });

        _btnInputValue.setOnClickListener(v -> {
            Intent intent = new Intent(Course.this, InputValue.class);
            intent.putExtra("college_year", _strCollegeYear);
            intent.putExtra("course_name", _strNameCourse);
            intent.putExtra("classroom", _strClassroom);
            intent.putExtra("course_code", _strCodeCourse);
            intent.putExtra("ID_teacher", _strIdTeacher);
            startActivity(intent);
        });

        _btnAssessment.setOnClickListener(v -> {
            Intent intent = new Intent(Course.this, Assessment.class);
            intent.putExtra("college_year", _strCollegeYear);
            intent.putExtra("course_name", _strNameCourse);
            intent.putExtra("classroom", _strClassroom);
            intent.putExtra("course_code", _strCodeCourse);
            startActivity(intent);
        });

        _btnBack.setOnClickListener(v -> onBackPressed());
    }
}