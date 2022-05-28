package com.arcmedtek.mpuskaapp_mobile.activity.user.lecture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.adapter.ChangeScoreListAdapter;
import com.arcmedtek.mpuskaapp_mobile.model.KhsModel;
import com.arcmedtek.mpuskaapp_mobile.service.MPuskaDataService;

import java.util.ArrayList;

public class ChangeScore extends AppCompatActivity {

    TextView _collegeYear, _nim, _studentName, _teamName;
    String _strCollegeYear, _strNim, _strStudentName, _strTeamName, _strCourseCode, _strClassroom;

    RecyclerView _changeScoreRecycler;
    ChangeScoreListAdapter _changeScoreListAdapter;
    MPuskaDataService _mPuskaDataService;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_score);

        _strCollegeYear = getIntent().getStringExtra("college_year");
        _strNim = getIntent().getStringExtra("nim");
        _strStudentName = getIntent().getStringExtra("student_name");
        _strTeamName = getIntent().getStringExtra("team_name");
        _strCourseCode = getIntent().getStringExtra("course_code");
        _strClassroom = getIntent().getStringExtra("classroom");

        _collegeYear = findViewById(R.id.txt_college_year_change_score);
        _nim = findViewById(R.id.txt_nim_input_score);
        _studentName = findViewById(R.id.txt_name_input_score);
        _teamName = findViewById(R.id.txt_team_name_input_score);
        _changeScoreRecycler = findViewById(R.id.score_list);

        _mPuskaDataService = new MPuskaDataService(ChangeScore.this);

        _collegeYear.setText("TA. " + _strCollegeYear);
        _nim.setText(_strNim);
        _studentName.setText(_strStudentName);
        _teamName.setText(_strTeamName);

        _mPuskaDataService.getStudentScore(_strNim, _strCourseCode, _strClassroom, _strCollegeYear, new MPuskaDataService.StudentScoreListener() {
            @Override
            public void onResponse(ArrayList<KhsModel> khsModels) {
                _changeScoreListAdapter = new ChangeScoreListAdapter(khsModels, ChangeScore.this);
                _changeScoreRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                _changeScoreRecycler.setAdapter(_changeScoreListAdapter);
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}