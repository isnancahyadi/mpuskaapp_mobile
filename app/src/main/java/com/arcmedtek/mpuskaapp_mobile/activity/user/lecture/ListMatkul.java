package com.arcmedtek.mpuskaapp_mobile.activity.user.lecture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.adapter.TeacherAdapter;
import com.arcmedtek.mpuskaapp_mobile.model.TeacherModel;
import com.arcmedtek.mpuskaapp_mobile.service.MPuskaDataService;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ListMatkul extends AppCompatActivity {

    TextView _teacherProgramName;
    TextInputEditText _txtInetSearchStudyYear;
    Button _btnSearchStudyYear;
    ImageView _imgNotFound;

    RecyclerView _courseRecycler;
    TeacherAdapter _teacherAdapter;
    MPuskaDataService _mPuskaDataService;
    //CharSequence search = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_matkul);

        _teacherProgramName = findViewById(R.id.teacher_program_name);
        _txtInetSearchStudyYear = findViewById(R.id.txt_inet_search_study_year);
        _btnSearchStudyYear = findViewById(R.id.btn_search_study_year);
        _courseRecycler = findViewById(R.id.list_matkul);
        _imgNotFound = findViewById(R.id.img_teacher_course_not_found);

        _mPuskaDataService = new MPuskaDataService(ListMatkul.this);

        _teacherProgramName.setText(String.valueOf(getIntent().getStringExtra("spi")));

        _txtInetSearchStudyYear.addTextChangedListener(searchStudyYearTextWatcher);

        _mPuskaDataService.getTeacherListCourse(new MPuskaDataService.TeacherListCourseListener() {
            @Override
            public void onResponse(ArrayList<TeacherModel> teacherModels) {
                setCourseRecycler(teacherModels);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(ListMatkul.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setCourseRecycler(ArrayList<TeacherModel> courseList) {
        _teacherAdapter = new TeacherAdapter(courseList, ListMatkul.this);
        _courseRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        _courseRecycler.setAdapter(_teacherAdapter);
    }

    private final TextWatcher searchStudyYearTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String searchStudyYearInput = String.valueOf(_txtInetSearchStudyYear.getText());

            _btnSearchStudyYear.setEnabled(!searchStudyYearInput.isEmpty());

            _btnSearchStudyYear.setOnClickListener(v -> {
                _teacherAdapter.getFilter().filter(s, count1 -> {
                    if (count1 == 0) {
                        _imgNotFound.setVisibility(View.VISIBLE);
                    } else {
                        _imgNotFound.setVisibility(View.GONE);
                    }
                });
                //search = s;
            });
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}