package com.arcmedtek.mpuskaapp_mobile.activity.user.lecture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.adapter.InputValueAdapter;
import com.arcmedtek.mpuskaapp_mobile.model.KhsModel;
import com.arcmedtek.mpuskaapp_mobile.service.MPuskaDataService;

import java.util.ArrayList;

public class InputValue extends AppCompatActivity {

    TextView _collegeYear, _nameCourse, _codeCourse, _classroom;
    ImageView _btnBack;
    String _strCollegeYear, _strNameCourse, _strCodeCourse, _strClassroom;

    RecyclerView _studentRecycler;
    InputValueAdapter _inputValueAdapter;
    MPuskaDataService _mPuskaDataService;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_value);

        _strCollegeYear = getIntent().getStringExtra("college_year");
        _strNameCourse = getIntent().getStringExtra("course_name");
        _strCodeCourse = getIntent().getStringExtra("course_code");
        _strClassroom = getIntent().getStringExtra("classroom");

        _mPuskaDataService = new MPuskaDataService(InputValue.this);

        _collegeYear = findViewById(R.id.txt_college_year_input_value);
        _nameCourse = findViewById(R.id.txt_name_course);
        _codeCourse = findViewById(R.id.txt_code_course);
        _classroom = findViewById(R.id.txt_classroom);
        _btnBack = findViewById(R.id.btn_back_input_value);
        _studentRecycler = findViewById(R.id.list_student);

        _collegeYear.setText("TA. " + _strCollegeYear);
        _nameCourse.setText(_strNameCourse);
        _codeCourse.setText(_strCodeCourse);
        _classroom.setText(_strClassroom);

        _mPuskaDataService.getStudentList(_strCodeCourse, _strClassroom, _strCollegeYear, new MPuskaDataService.StudentListListener() {
            @Override
            public void onResponse(ArrayList<KhsModel> khsModels) {
                setStudentRecycler(khsModels);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(InputValue.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        _btnBack.setOnClickListener(v -> onBackPressed());
    }

    private void setStudentRecycler(ArrayList<KhsModel> khsModels) {
        _inputValueAdapter = new InputValueAdapter(khsModels, InputValue.this, _strCodeCourse, _strClassroom, _strCollegeYear);
        _studentRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        _studentRecycler.setAdapter(_inputValueAdapter);
    }
}