package com.arcmedtek.mpuskaapp_mobile.activity.user.lecture;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.adapter.ChangeScoreListAdapter;
import com.arcmedtek.mpuskaapp_mobile.config.OnEditTextChanged;
import com.arcmedtek.mpuskaapp_mobile.model.KhsModel;
import com.arcmedtek.mpuskaapp_mobile.service.MPuskaDataService;

import java.util.ArrayList;
import java.util.Arrays;

public class ChangeScore extends AppCompatActivity {

    TextView _collegeYear, _nim, _studentName, _teamName;
    Button _btnChangeScore;
    String _strCollegeYear, _strNim, _strStudentName, _strTeamName, _strCourseCode, _strClassroom, _strIdKrs;

    RecyclerView _changeScoreRecycler;
    ChangeScoreListAdapter _changeScoreListAdapter;
    MPuskaDataService _mPuskaDataService;

    String[] _score, _idAssessment;

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
        _strIdKrs = getIntent().getStringExtra("ID_krs");

        _collegeYear = findViewById(R.id.txt_college_year_change_score);
        _nim = findViewById(R.id.txt_nim_input_score);
        _studentName = findViewById(R.id.txt_name_input_score);
        _teamName = findViewById(R.id.txt_team_name_input_score);
        _changeScoreRecycler = findViewById(R.id.score_list);
        _btnChangeScore = findViewById(R.id.btn_change_score);

        _mPuskaDataService = new MPuskaDataService(ChangeScore.this);

        _collegeYear.setText("TA. " + _strCollegeYear);
        _nim.setText(_strNim);
        _studentName.setText(_strStudentName);
        _teamName.setText(_strTeamName);

        _mPuskaDataService.getStudentScore(_strNim, _strCourseCode, _strClassroom, _strCollegeYear, new MPuskaDataService.StudentScoreListener() {
            @Override
            public void onResponse(ArrayList<KhsModel> khsModels) {
                setStudentScoreRecycler(khsModels);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(ChangeScore.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setStudentScoreRecycler(ArrayList<KhsModel> khsModels) {
        _score = new String[khsModels.size()];
        _idAssessment = new String[khsModels.size()];

        for (int i = 0; i < khsModels.size(); i++) {
            _idAssessment[i] = String.valueOf(khsModels.get(i).get_idAsesmen());
            _score[i] = String.valueOf(khsModels.get(i).get_score());
        }

        _changeScoreListAdapter = new ChangeScoreListAdapter(khsModels, ChangeScore.this, _strIdKrs, new OnEditTextChanged() {
            @Override
            public void onTextChanged(int position, String charSeq) {
                _score[position] = charSeq;
            }

            @Override
            public void beforeTextChanged(int position, String charSeq) {
                _score[position] = charSeq;
            }
        });
        _changeScoreRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        _changeScoreRecycler.setAdapter(_changeScoreListAdapter);

        _btnChangeScore.setOnClickListener(v -> {
            updateScore(_score, _idAssessment);
        });
    }

    private void updateScore(String[] score, String[] idAssessment) {
        _mPuskaDataService.updateScoreMhs(_strIdKrs, idAssessment, score, new MPuskaDataService.UpdateScoreMhs() {
            @Override
            public void onResponse(String message) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChangeScore.this, R.style.AlertDialogStyle);
                View doneDialog = LayoutInflater.from(ChangeScore.this).inflate(R.layout.custom_done_dialog, findViewById(R.id.confirm_done_dialog));
                builder.setView(doneDialog);

                TextView txtMessage = doneDialog.findViewById(R.id.done_message);
                txtMessage.setText(message);

                final AlertDialog alertDialog = builder.create();

                doneDialog.findViewById(R.id.btn_confirm_done).setOnClickListener(v -> {
                    onBackPressed();
                });

                if (alertDialog.getWindow() != null) {
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }

                alertDialog.show();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(ChangeScore.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}