package com.arcmedtek.mpuskaapp_mobile.activity.user.lecture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.adapter.CourseConversionAdapter;
import com.arcmedtek.mpuskaapp_mobile.model.KrsModel;
import com.arcmedtek.mpuskaapp_mobile.service.MPuskaDataService;

import java.util.ArrayList;

public class CourseConversion extends AppCompatActivity {

    TextView _collegeYear, _nameCourse, _codeCourse, _classroom;
    String _strCollegeYear, _strNameCourse, _strCodeCourse, _strClassroom, _strIdTeacher;

    RecyclerView _studentRecycler;
    CourseConversionAdapter _courseConversionAdapter;
    MPuskaDataService _mPuskaDataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_conversion);

        _strCollegeYear = getIntent().getStringExtra("college_year");
        _strNameCourse = getIntent().getStringExtra("course_name");
        _strClassroom = getIntent().getStringExtra("classroom");
        _strCodeCourse = getIntent().getStringExtra("course_code");
        _strIdTeacher = getIntent().getStringExtra("ID_teacher");

        _mPuskaDataService = new MPuskaDataService(CourseConversion.this);

        _collegeYear = findViewById(R.id.txt_college_year_course_conversion);
        _nameCourse = findViewById(R.id.txt_name_course);
        _codeCourse = findViewById(R.id.txt_code_course);
        _classroom = findViewById(R.id.txt_classroom);
        _studentRecycler = findViewById(R.id.list_student);

        main(_strCollegeYear, _strNameCourse, _strCodeCourse, _strClassroom, _strIdTeacher);
    }

    @SuppressLint("SetTextI18n")
    private void main(String collegeYear, String courseName, String courseCode, String classRoom, String idTeacher) {
        _collegeYear.setText("TA. " + collegeYear);
        _nameCourse.setText(courseName);
        _classroom.setText(classRoom);
        _codeCourse.setText(courseCode);

        _mPuskaDataService.getStudentList(idTeacher, new MPuskaDataService.StudentListListener() {
            @Override
            public void onResponse(ArrayList<KrsModel> krsModels) {
                setStudentRecycler(krsModels, courseCode, collegeYear);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(CourseConversion.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setStudentRecycler(ArrayList<KrsModel> krsModels, String courseCode, String collegeYear) {
        _courseConversionAdapter = new CourseConversionAdapter(krsModels, courseCode, CourseConversion.this);
        _studentRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        _studentRecycler.setAdapter(_courseConversionAdapter);
        _courseConversionAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        main(_strCollegeYear, _strNameCourse, _strCodeCourse, _strClassroom, _strIdTeacher);
    }
}