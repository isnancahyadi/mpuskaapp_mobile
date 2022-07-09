package com.arcmedtek.mpuskaapp_mobile.activity.user.lecture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.adapter.ChangeAssessmentsListAdapter;
import com.arcmedtek.mpuskaapp_mobile.adapter.CplListAdapter;
import com.arcmedtek.mpuskaapp_mobile.adapter.CpmkListAdapter;
import com.arcmedtek.mpuskaapp_mobile.adapter.ListAssessmentAdapter;
import com.arcmedtek.mpuskaapp_mobile.config.OnEditTextChanged;
import com.arcmedtek.mpuskaapp_mobile.config.OnEditTextChanged2;
import com.arcmedtek.mpuskaapp_mobile.model.CourseModel;
import com.arcmedtek.mpuskaapp_mobile.model.KhsModel;
import com.arcmedtek.mpuskaapp_mobile.service.MPuskaDataService;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.internal.TextScale;
import com.google.android.material.textfield.TextInputEditText;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Assessment extends AppCompatActivity {

    PieChart _scorePercentagePieChart;
    int totalPercent = 0;

    ArrayList<PieEntry> _assessment;
    TextView _collegeYear, _nameCourse, _codeCourse, _classroom;
    String _strCollegeYear, _strNameCourse, _strCodeCourse, _strClassroom, _strIdTeacher;
    RecyclerView _cplRecycler, _cpmkRecycler, _percentRecycler;
    ImageView _btnSetting, _btnSaveUpdateAssessments, _btnCancelUpdateAssessments;
    LinearLayout _listAssessmentsContainer;
    RelativeLayout _chartScoreContainer;

    MPuskaDataService _mPuskaDataService;
    Typeface _robotoBold, _roboto;
    CplListAdapter _cplListAdapter;
    CpmkListAdapter _cpmkListAdapter;
    ChangeAssessmentsListAdapter _changeAssessmentsListAdapter;
    ListAssessmentAdapter _listAssessmentAdapter;
    
    MenuBuilder _menuBuilder;

    String[] _idAssessments, _assessments, _percent;

    @SuppressLint({"NewApi", "SetTextI18n", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);

        _strCollegeYear = getIntent().getStringExtra("college_year");
        _strNameCourse = getIntent().getStringExtra("course_name");
        _strClassroom = getIntent().getStringExtra("classroom");
        _strCodeCourse = getIntent().getStringExtra("course_code");
        _strIdTeacher = getIntent().getStringExtra("ID_teacher");

        _scorePercentagePieChart = findViewById(R.id.score_percentage_pie_chart);
        _collegeYear = findViewById(R.id.txt_college_year_assessment);
        _nameCourse = findViewById(R.id.txt_name_course);
        _codeCourse = findViewById(R.id.txt_code_course);
        _classroom = findViewById(R.id.txt_classroom);
        _cplRecycler = findViewById(R.id.list_cpl);
        _cpmkRecycler = findViewById(R.id.list_cpmk);
        _percentRecycler = findViewById(R.id.list_assessments);
        _chartScoreContainer = findViewById(R.id.chart_score_percentage);
        _btnSetting = findViewById(R.id.btn_setting_assessment);
        _listAssessmentsContainer = findViewById(R.id.list_assessments_container);
        _btnSaveUpdateAssessments = findViewById(R.id.btn_save_update_assessments);
        _btnCancelUpdateAssessments = findViewById(R.id.btn_cancel_update_assessments);

        _collegeYear.setText("TA. " + _strCollegeYear);
        _nameCourse.setText(_strNameCourse);
        _codeCourse.setText(_strCodeCourse);
        _classroom.setText(_strClassroom);

        _robotoBold = ResourcesCompat.getFont(Assessment.this, R.font.roboto_bold);
        _roboto = ResourcesCompat.getFont(Assessment.this, R.font.roboto);
        _assessment = new ArrayList<>();
        _mPuskaDataService = new MPuskaDataService(Assessment.this);
        _menuBuilder = new MenuBuilder(this);

        assessmentsChart();

        _mPuskaDataService.getCpl(_strCodeCourse, new MPuskaDataService.CplListener() {
            @Override
            public void onResponse(ArrayList<KhsModel> khsModels) {
                setCplRecycler(khsModels);
            }

            @Override
            public void onError(String message) {

            }
        });
        
        _mPuskaDataService.getCpmk(_strCodeCourse, new MPuskaDataService.CpmkListener() {
            @Override
            public void onResponse(ArrayList<KhsModel> khsModels) {
                setCpmkRecycler(khsModels);
            }

            @Override
            public void onError(String message) {

            }
        });
        
        assessmentsSetting(_strCodeCourse, _strClassroom, _strCollegeYear);
    }

    @SuppressLint("RestrictedApi")
    private void assessmentsSetting(String strCodeCourse, String strClassroom, String strCollegeYear) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.popup_menu_setting_assessments, _menuBuilder);
        
        _btnSetting.setOnClickListener(v -> {
            MenuPopupHelper optionMenu = new MenuPopupHelper(Assessment.this, _menuBuilder, v);
            optionMenu.setForceShowIcon(true);
            
            _menuBuilder.setCallback(new MenuBuilder.Callback() {
                @Override
                public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                    if (item.getItemId() == R.id.edit_assessment) {
//                        editAssessments(strCodeCourse, strClassroom, strCollegeYear);
                        return true;
                    } else if (item.getItemId() == R.id.add_assessments) {
                        addAssessments();
                    }
                    return false;
                }

                @Override
                public void onMenuModeChange(@NonNull MenuBuilder menu) {

                }
            });
            optionMenu.show();
        });
    }

    private void addAssessments() {
        Dialog addAssessments = new Dialog(this);
        addAssessments.setContentView(R.layout.add_assessments_dialog);

        AutoCompleteTextView chooseAssessments = addAssessments.findViewById(R.id.choose_assessments);
        ImageView btnClose = addAssessments.findViewById(R.id.btn_close);

        _mPuskaDataService.getAllAssessments(new MPuskaDataService.AssessmentsListener() {
            @Override
            public void onResponse(ArrayList<CourseModel> courseModels) {
                _listAssessmentAdapter = new ListAssessmentAdapter(Assessment.this, courseModels);
                chooseAssessments.setAdapter(_listAssessmentAdapter);
            }

            @Override
            public void onError(String message) {

            }
        });

        btnClose.setOnClickListener(v -> {
            addAssessments.dismiss();
        });

        addAssessments.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addAssessments.show();
    }

//    private void editAssessments(String codeCourse, String classroom, String collegeYear) {
//
//
//        _mPuskaDataService.getAssessments(codeCourse, classroom, collegeYear, new MPuskaDataService.AssessmentsListener() {
//            @Override
//            public void onResponse(ArrayList<KhsModel> khsModels) {
//                setAssessmentsRecycler(khsModels);
//            }
//
//            @Override
//            public void onError(String message) {
//
//            }
//        });
//    }

    private void setAssessmentsRecycler(ArrayList<KhsModel> khsModels) {
        _scorePercentagePieChart.setVisibility(View.GONE);
        _listAssessmentsContainer.setVisibility(View.VISIBLE);
        _btnSetting.setVisibility(View.GONE);
        _btnSaveUpdateAssessments.setVisibility(View.VISIBLE);
        _btnCancelUpdateAssessments.setVisibility(View.VISIBLE);

        _idAssessments = new String[khsModels.size()];
        _assessments = new String[khsModels.size()];
        _percent = new String[khsModels.size()];

        for (int i = 0; i < khsModels.size(); i++) {
            _idAssessments[i] = String.valueOf(khsModels.get(i).get_idAsesmen());
            _assessments[i] = String.valueOf(khsModels.get(i).get_assessment());
            _percent[i] = String.valueOf(khsModels.get(i).get_percent());
        }

        _changeAssessmentsListAdapter = new ChangeAssessmentsListAdapter(khsModels, Assessment.this, new OnEditTextChanged() {
            @Override
            public void onTextChanged(int position, String charSeq) {
                _assessments[position] = charSeq;
            }

            @Override
            public void beforeTextChanged(int position, String charSeq) {
                _assessments[position] = charSeq;
            }
        }, new OnEditTextChanged2() {
            @Override
            public void onTextChanged(int position, String charSeq) {
                _percent[position] = charSeq;
            }

            @Override
            public void beforeTextChanged(int position, String charSeq) {
                _percent[position] = charSeq;
            }
        });
        _percentRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        _percentRecycler.setAdapter(_changeAssessmentsListAdapter);

        _btnSaveUpdateAssessments.setOnClickListener(v -> {
            updateAssessments(_percent, _assessments, _idAssessments);
        });
        _btnCancelUpdateAssessments.setOnClickListener(v -> refreshActivity());
    }

    private void updateAssessments(String[] percent, String[] assessments, String[] idAssessments) {
        _mPuskaDataService.updateAssessments(idAssessments, assessments, percent, new MPuskaDataService.UpdateAssessments() {
            @Override
            public void onResponse(String message) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Assessment.this, R.style.AlertDialogStyle);
                View doneDialog = LayoutInflater.from(Assessment.this).inflate(R.layout.custom_done_dialog, findViewById(R.id.confirm_done_dialog));
                builder.setView(doneDialog);

                TextView txtMessage = doneDialog.findViewById(R.id.done_message);
                txtMessage.setText(message);

                final AlertDialog alertDialog = builder.create();

                doneDialog.findViewById(R.id.btn_confirm_done).setOnClickListener(v -> refreshActivity());

                if (alertDialog.getWindow() != null) {
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }

                alertDialog.show();
            }

            @Override
            public void onError(String message) {
                refreshActivity();

                Toast.makeText(Assessment.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshActivity() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    private void setCpmkRecycler(ArrayList<KhsModel> khsModels) {
        _cpmkListAdapter = new CpmkListAdapter(khsModels, Assessment.this);
        _cpmkRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        _cpmkRecycler.setAdapter(_cpmkListAdapter);
    }

    private void setCplRecycler(ArrayList<KhsModel> khsModels) {
        _cplListAdapter = new CplListAdapter(khsModels, Assessment.this);
        _cplRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        _cplRecycler.setAdapter(_cplListAdapter);
    }

    private void assessmentsChart() {
        _mPuskaDataService.getAssessments(_strIdTeacher, new MPuskaDataService.AssessmentsListener() {
            @Override
            public void onResponse(ArrayList<CourseModel> courseModels) {
                for (int i = 0; i < courseModels.size(); i++) {
                    _assessment.add(new PieEntry(courseModels.get(i).get_assessmentsPercentage(), courseModels.get(i).get_assessments()));
                    totalPercent = totalPercent + courseModels.get(i).get_assessmentsPercentage();
                }
                PieDataSet pieDataSet = new PieDataSet(_assessment, null);
                pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                pieDataSet.setDrawValues(false);
                pieDataSet.setSliceSpace(10f);

                PieData pieData = new PieData(pieDataSet);
                pieData.setValueFormatter(new PercentFormatter(_scorePercentagePieChart));

                _scorePercentagePieChart.getDescription().setEnabled(false);
                _scorePercentagePieChart.setCenterTextColor(Color.argb(255, 95, 126, 237));
                _scorePercentagePieChart.setCenterTextSize(27);
                _scorePercentagePieChart.setCenterText(totalPercent + "%");
                _scorePercentagePieChart.setCenterTextTypeface(_robotoBold);
                _scorePercentagePieChart.setDrawEntryLabels(false);
                _scorePercentagePieChart.setTransparentCircleRadius(80);
                _scorePercentagePieChart.setHoleRadius(70);
                _scorePercentagePieChart.setExtraRightOffset(45);
                _scorePercentagePieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry e, Highlight h) {
                        _scorePercentagePieChart.setCenterText((int) e.getY() + "%");
                    }

                    @Override
                    public void onNothingSelected() {
                        _scorePercentagePieChart.setCenterText(totalPercent + "%");
                    }
                });
                _scorePercentagePieChart.setData(pieData);
                _scorePercentagePieChart.notifyDataSetChanged();
                _scorePercentagePieChart.invalidate();
                _scorePercentagePieChart.animate();

                Legend l = _scorePercentagePieChart.getLegend();
                l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                l.setOrientation(Legend.LegendOrientation.VERTICAL);
                l.setFormSize(15);
                l.setFormToTextSpace(5);
                l.setWordWrapEnabled(true);
//                l.setYOffset(-40);
                l.setXOffset(70);
                l.setTypeface(_roboto);
                l.setTextSize(14);
                l.setYEntrySpace(10f);
                l.setTextColor(Color.argb(255, 79, 79, 79));
            }

            @Override
            public void onError(String message) {
                Toast.makeText(Assessment.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}