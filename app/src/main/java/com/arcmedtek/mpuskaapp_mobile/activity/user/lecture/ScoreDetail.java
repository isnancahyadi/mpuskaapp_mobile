package com.arcmedtek.mpuskaapp_mobile.activity.user.lecture;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.adapter.AchievementsAdapter;
import com.arcmedtek.mpuskaapp_mobile.adapter.AssessmentScoreListAdapter;
import com.arcmedtek.mpuskaapp_mobile.adapter.ChangeScoreListAdapter;
import com.arcmedtek.mpuskaapp_mobile.config.OnEditTextChanged;
import com.arcmedtek.mpuskaapp_mobile.model.KhsModel;
import com.arcmedtek.mpuskaapp_mobile.service.MPuskaDataService;

import java.util.ArrayList;

public class ScoreDetail extends AppCompatActivity {

    TextView _collegeYear, _nim, _studentName, _teamName;
    RecyclerView _assessmentScoreRecycler, _changeAssessmentScoreRecycler, _achievementsListRecycler;
    CardView _btnChangeScore;
    RelativeLayout _containerListScoreDetail, _containerChangeScoreDetail;
    Button _btnCancelUpdateScore, _btnSaveUpdateScore;

    String _strCollegeYear, _strNim, _strFirstName, _strMiddleName, _strLastName, _strTeamName, _strIdKrs, _strCourseCode, _strIdTeacher;
    String[] _score, _idAssessment;

    MPuskaDataService _mPuskaDataService;
    AssessmentScoreListAdapter _assessmentScoreListAdapter;
    ChangeScoreListAdapter _changeScoreListAdapter;
    AchievementsAdapter _achievementsAdapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_detail);

        _strCollegeYear = getIntent().getStringExtra("college_year");
        _strNim = getIntent().getStringExtra("nim");
        _strFirstName = getIntent().getStringExtra("first_name");
        _strMiddleName = getIntent().getStringExtra("middle_name");
        _strLastName = getIntent().getStringExtra("last_name");
        _strTeamName = getIntent().getStringExtra("team_name");
        _strIdKrs = getIntent().getStringExtra("ID_krs");
        _strCourseCode = getIntent().getStringExtra("course_code");
        _strIdTeacher = getIntent().getStringExtra("ID_teacher");

        _collegeYear = findViewById(R.id.txt_college_year_score_detail);
        _nim = findViewById(R.id.txt_nim_score_detail);
        _studentName = findViewById(R.id.txt_name_score_detail);
        _teamName = findViewById(R.id.txt_team_name_score_detail);
        _assessmentScoreRecycler = findViewById(R.id.score_list);
        _changeAssessmentScoreRecycler = findViewById(R.id.change_score_list);
        _achievementsListRecycler = findViewById(R.id.cpmk_status_list);
        _containerListScoreDetail = findViewById(R.id.container_list_score_detail);
        _containerChangeScoreDetail = findViewById(R.id.container_change_score_detail);
        _btnChangeScore = findViewById(R.id.btn_change_score);
        _btnCancelUpdateScore = findViewById(R.id.btn_cancel_update_assessments);
        _btnSaveUpdateScore = findViewById(R.id.btn_save_update_assessments);

        _mPuskaDataService = new MPuskaDataService(ScoreDetail.this);

        _collegeYear.setText(_strCollegeYear);
        _nim.setText(_strNim);
        if (_strMiddleName.equals("")) {
            _studentName.setText(_strFirstName + " " + _strLastName);
        } else {
            _studentName.setText(_strFirstName + " " + _strMiddleName + " " + _strLastName);
        }
        _teamName.setText(_strTeamName);

        studentList(_strIdKrs);

        achievementsList(_strCourseCode, _strIdTeacher, _strIdKrs);
    }

    private void achievementsList(String courseCode, String idTeacher, String idKrs) {
        _mPuskaDataService.getCpmk(courseCode, new MPuskaDataService.CpmkListener() {
            @Override
            public void onResponse(ArrayList<KhsModel> khsModels) {
                _achievementsAdapter = new AchievementsAdapter(khsModels, ScoreDetail.this, idTeacher, idKrs);
                _achievementsListRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                _achievementsListRecycler.setAdapter(_achievementsAdapter);
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    private void studentList(String strIdKrs) {
        _mPuskaDataService.getStudentScore(strIdKrs, new MPuskaDataService.StudentScoreListener() {
            @Override
            public void onResponse(ArrayList<KhsModel> khsModels) {
                _assessmentScoreListAdapter = new AssessmentScoreListAdapter(khsModels, ScoreDetail.this);
                _assessmentScoreRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                _assessmentScoreRecycler.setAdapter(_assessmentScoreListAdapter);
            }

            @Override
            public void onError(String message) {

            }
        });

        _btnChangeScore.setOnClickListener(v -> {
            changeStudentScore(strIdKrs);
        });
    }

    private void changeStudentScore(String strIdKrs) {
        _containerListScoreDetail.setVisibility(View.GONE);
        _containerChangeScoreDetail.setVisibility(View.VISIBLE);

        _mPuskaDataService.getStudentScore(strIdKrs, new MPuskaDataService.StudentScoreListener() {
            @Override
            public void onResponse(ArrayList<KhsModel> khsModels) {
                _score = new String[khsModels.size()];
                _idAssessment = new String[khsModels.size()];

                for (int i = 0; i < khsModels.size(); i++) {
                    _idAssessment[i] = String.valueOf(khsModels.get(i).get_idAsesmen());
                    _score[i] = String.valueOf(khsModels.get(i).get_score());
                }

                _changeScoreListAdapter = new ChangeScoreListAdapter(khsModels, ScoreDetail.this, strIdKrs, new OnEditTextChanged() {
                    @Override
                    public void onTextChanged(int position, String charSeq) {
                        _score[position] = charSeq;
                    }

                    @Override
                    public void beforeTextChanged(int position, String charSeq) {
                        _score[position] = charSeq;
                    }
                });

                _changeAssessmentScoreRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                _changeAssessmentScoreRecycler.setAdapter(_changeScoreListAdapter);
            }

            @Override
            public void onError(String message) {

            }
        });

        _btnSaveUpdateScore.setOnClickListener(v -> {
            updateScore(_score, _idAssessment, strIdKrs);
        });

        _btnCancelUpdateScore.setOnClickListener(v -> refreshActivity());
    }

    private void updateScore(String[] score, String[] idAssessment, String strIdKrs) {
        _mPuskaDataService.updateScoreMhs(strIdKrs, idAssessment, score, new MPuskaDataService.UpdateScoreMhs() {
            @Override
            public void onResponse(String message) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ScoreDetail.this, R.style.AlertDialogStyle);
                View doneDialog = LayoutInflater.from(ScoreDetail.this).inflate(R.layout.custom_done_dialog, findViewById(R.id.confirm_done_dialog));
                builder.setView(doneDialog);

                TextView txtMessage = doneDialog.findViewById(R.id.done_message);
                txtMessage.setText(message);

                final AlertDialog alertDialog = builder.create();

                doneDialog.findViewById(R.id.btn_confirm_done).setOnClickListener(v -> {
                    refreshActivity();
                });

                if (alertDialog.getWindow() != null) {
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }

                alertDialog.show();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(ScoreDetail.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshActivity() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
}