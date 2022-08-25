package com.arcmedtek.mpuskaapp_mobile.activity.user.lecture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.adapter.InputValueAdapter;
import com.arcmedtek.mpuskaapp_mobile.model.KhsModel;
import com.arcmedtek.mpuskaapp_mobile.model.KrsModel;
import com.arcmedtek.mpuskaapp_mobile.service.MPuskaDataService;
import com.arcmedtek.mpuskaapp_mobile.service.NetworkChangeListener;

import java.util.ArrayList;

public class InputValue extends AppCompatActivity {

    TextView _collegeYear, _nameCourse, _codeCourse, _classroom;
    ImageView _btnBack;
    String _strCollegeYear, _strNameCourse, _strCodeCourse, _strClassroom, _strIdTeacher;

    RecyclerView _studentRecycler;
    InputValueAdapter _inputValueAdapter;
    MPuskaDataService _mPuskaDataService;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_value);

        _strCollegeYear = getIntent().getStringExtra("college_year");
        _strNameCourse = getIntent().getStringExtra("course_name");
        _strCodeCourse = getIntent().getStringExtra("course_code");
        _strClassroom = getIntent().getStringExtra("classroom");
        _strIdTeacher = getIntent().getStringExtra("ID_teacher");

        _mPuskaDataService = new MPuskaDataService(InputValue.this);

        _collegeYear = findViewById(R.id.txt_college_year_input_value);
        _nameCourse = findViewById(R.id.txt_name_course);
        _codeCourse = findViewById(R.id.txt_code_course);
        _classroom = findViewById(R.id.txt_classroom);
        _btnBack = findViewById(R.id.btn_back_input_value);
        _studentRecycler = findViewById(R.id.list_student);

        main(_strCollegeYear, _strNameCourse, _strCodeCourse, _strClassroom, _strIdTeacher);
    }

    @SuppressLint("SetTextI18n")
    private void main(String collegeYear, String courseName, String courseCode, String classRoom, String idTeacher) {
        _collegeYear.setText("TA. " + collegeYear);
        _nameCourse.setText(courseName);
        _codeCourse.setText(courseCode);
        _classroom.setText(classRoom);

        _mPuskaDataService.getStudentList(idTeacher, new MPuskaDataService.StudentListListener() {
            @Override
            public void onResponse(ArrayList<KrsModel> krsModels) {
                setStudentRecycler(krsModels, idTeacher, collegeYear, courseCode);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(InputValue.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        _btnBack.setOnClickListener(v -> onBackPressed());
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setStudentRecycler(ArrayList<KrsModel> krsModels, String strIdTeacher, String strCollegeYear, String courseCode) {
        _inputValueAdapter = new InputValueAdapter(krsModels, InputValue.this, strIdTeacher, strCollegeYear, courseCode);
        _studentRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        _studentRecycler.setAdapter(_inputValueAdapter);
        _inputValueAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        main(_strCollegeYear, _strNameCourse, _strCodeCourse, _strClassroom, _strIdTeacher);
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
}