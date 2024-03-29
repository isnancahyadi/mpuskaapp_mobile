package com.arcmedtek.mpuskaapp_mobile.activity.user.lecture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.model.CourseModel;
import com.arcmedtek.mpuskaapp_mobile.service.MPuskaDataService;
import com.arcmedtek.mpuskaapp_mobile.service.NetworkChangeListener;
import com.google.android.flexbox.AlignContent;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;

public class Course extends AppCompatActivity {

    TextView _collegeYear, _nameCourse, _codeCourse, _classroom;
    ImageView _btnBack;
    String _strCollegeYear, _strNameCourse, _strCodeCourse, _strClassroom, _strIdTeacher;

    CardView _btnConversion, _btnStudentList, _btnInputValue, _btnAssessment;
    LinearLayout _containerAction;
    FlexboxLayout _contentAction;

    MPuskaDataService _mPuskaDataService;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        _mPuskaDataService = new MPuskaDataService(Course.this);

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
        _btnConversion = findViewById(R.id.btn_conversion);
        _containerAction = findViewById(R.id.container_action);
        _contentAction = findViewById(R.id.content_action);

        _collegeYear.setText("TA. " + _strCollegeYear);
        _nameCourse.setText(_strNameCourse);
        _classroom.setText(_strClassroom);
        _codeCourse.setText(_strCodeCourse);

        _mPuskaDataService.getConversion(_strCodeCourse, new MPuskaDataService.GetConversionListener() {
            @Override
            public void onResponse(ArrayList<CourseModel> courseModels) {
                _btnConversion.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(String message) {
                _btnConversion.setVisibility(View.GONE);
                final float density = Course.this.getResources().getDisplayMetrics().density;
                ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
                layoutParams.setMargins(0, (int) (370 * density), 0, 0);
                _containerAction.setLayoutParams(layoutParams);

                _contentAction.setAlignContent(AlignContent.SPACE_AROUND);
            }
        });

        _btnConversion.setOnClickListener(v -> {
            Intent intent = new Intent(Course.this, CourseConversion.class);
            intent.putExtra("college_year", _strCollegeYear);
            intent.putExtra("course_name", _strNameCourse);
            intent.putExtra("classroom", _strClassroom);
            intent.putExtra("course_code", _strCodeCourse);
            intent.putExtra("ID_teacher", _strIdTeacher);
            startActivity(intent);
        });

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
            intent.putExtra("ID_teacher", _strIdTeacher);
            startActivity(intent);
        });

        _btnBack.setOnClickListener(v -> onBackPressed());
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