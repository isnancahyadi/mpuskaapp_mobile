package com.arcmedtek.mpuskaapp_mobile.activity.user.lecture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.adapter.ChangeAssessmentsListAdapter;
import com.arcmedtek.mpuskaapp_mobile.adapter.CplListAdapter;
import com.arcmedtek.mpuskaapp_mobile.adapter.CpmkListAdapter;
import com.arcmedtek.mpuskaapp_mobile.adapter.ListCpmkAdapter;
import com.arcmedtek.mpuskaapp_mobile.adapter.ListAssessmentAdapter;
import com.arcmedtek.mpuskaapp_mobile.config.OnEditTextChanged2;
import com.arcmedtek.mpuskaapp_mobile.config.OnSpinnerChanged;
import com.arcmedtek.mpuskaapp_mobile.model.CourseModel;
import com.arcmedtek.mpuskaapp_mobile.model.KhsModel;
import com.arcmedtek.mpuskaapp_mobile.service.MPuskaDataService;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class Assessment extends AppCompatActivity {

    PieChart _scorePercentagePieChart;
    int totalPercent = 0;
    String idAssessment, selectedCpmk, selectedAchievements;

    ArrayList<PieEntry> _assessment;
    TextView _collegeYear, _nameCourse, _codeCourse, _classroom, _txtTitleAchievements;
    String _strCollegeYear, _strNameCourse, _strCodeCourse, _strClassroom, _strIdTeacher;
    RecyclerView _cplRecycler, _cpmkRecycler, _percentRecycler;
    ImageView _btnSetting;
    Button _btnSaveUpdateAssessments, _btnCancelUpdateAssessments, _btnAddAchievements;
    LinearLayout _listAssessmentsContainer, _listCplContainer, _listCpmkContainer;
    CardView _btnSwitch;
    RelativeLayout _chartScoreContainer;

    MPuskaDataService _mPuskaDataService;
    Typeface _robotoBold, _roboto;
    CplListAdapter _cplListAdapter;
    CpmkListAdapter _cpmkListAdapter;
    ChangeAssessmentsListAdapter _changeAssessmentsListAdapter;
    ListAssessmentAdapter _listAssessmentAdapter;
    ListCpmkAdapter _listCpmkAdapter;

    MenuBuilder _menuBuilder;

    String[] _idAssessments, _percent, _keyScore;
    boolean _isAchievementsActived = false;

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
        _listCplContainer = findViewById(R.id.list_cpl_container);
        _listCpmkContainer = findViewById(R.id.list_cpmk_container);
        _txtTitleAchievements = findViewById(R.id.txt_title_achievements);
        _btnSetting = findViewById(R.id.btn_setting_assessment);
        _btnSwitch = findViewById(R.id.btn_switch);
        _btnAddAchievements = findViewById(R.id.btn_add_achievements);
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

        cplCpmkViews();

        assessmentsSetting(_strCodeCourse, _strClassroom, _strCollegeYear);
    }

    @SuppressLint("SetTextI18n")
    private void cplCpmkViews() {
        _mPuskaDataService.getCpl(_strCodeCourse, new MPuskaDataService.CplListener() {
            @Override
            public void onResponse(ArrayList<KhsModel> khsModels) {
                setCplRecycler(khsModels);
            }

            @Override
            public void onError(String message) {
                //Toast.makeText(Assessment.this, message, Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(Assessment.this);

                builder.setMessage(message)
                        .setTitle("Error");

                AlertDialog dialog = builder.create();

                dialog.show();
            }
        });

        _mPuskaDataService.getCpmk(_strCodeCourse, new MPuskaDataService.CpmkListener() {
            @Override
            public void onResponse(ArrayList<KhsModel> khsModels) {
                setCpmkRecycler(khsModels, _strIdTeacher, _isAchievementsActived);
            }

            @Override
            public void onError(String message) {
                //Toast.makeText(Assessment.this, message, Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(Assessment.this);

                builder.setMessage(message)
                        .setTitle("Error");

                AlertDialog dialog = builder.create();

                dialog.show();
            }
        });

        _btnSwitch.setOnClickListener(v -> {
            _isAchievementsActived = !_isAchievementsActived;

            if (_isAchievementsActived) {
                _listCplContainer.setVisibility(View.GONE);
                _txtTitleAchievements.setText("Komposisi Asesmen");
                _btnAddAchievements.setVisibility(View.VISIBLE);
            } else {
                _listCplContainer.setVisibility(View.VISIBLE);
                _txtTitleAchievements.setText("Capaian Pembelajaran");
                _btnAddAchievements.setVisibility(View.GONE);
            }

            _mPuskaDataService.getCpmk(_strCodeCourse, new MPuskaDataService.CpmkListener() {
                @Override
                public void onResponse(ArrayList<KhsModel> khsModels) {
                    setCpmkRecycler(khsModels, _strIdTeacher, _isAchievementsActived);
                }

                @Override
                public void onError(String message) {
                    //Toast.makeText(Assessment.this, message, Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(Assessment.this);

                    builder.setMessage(message)
                            .setTitle("Error");

                    AlertDialog dialog = builder.create();

                    dialog.show();
                }
            });
        });

        _btnAddAchievements.setOnClickListener(v -> {
            addAchievements();
        });
    }

    private void addAchievements() {
        Dialog dialogInput = new Dialog(Assessment.this);
        dialogInput.setContentView(R.layout.add_achievements);

        ImageView btnClose;
        Spinner chooseCpmk, chooseAchievements;
        Button btnSaveAchievements;

        btnClose = dialogInput.findViewById(R.id.btn_close_input);
        chooseCpmk = dialogInput.findViewById(R.id.choose_cpmk);
        chooseAchievements = dialogInput.findViewById(R.id.choose_achievements);
        btnSaveAchievements = dialogInput.findViewById(R.id.btn_save_achievements);

        _mPuskaDataService.getCpmk(_strCodeCourse, new MPuskaDataService.CpmkListener() {
            @Override
            public void onResponse(ArrayList<KhsModel> khsModels) {
                _listCpmkAdapter = new ListCpmkAdapter(Assessment.this, khsModels);
                chooseCpmk.setAdapter(_listCpmkAdapter);
            }

            @Override
            public void onError(String message) {
                //Toast.makeText(Assessment.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        _mPuskaDataService.getAssessments(_strIdTeacher, new MPuskaDataService.AssessmentsListener() {
            @Override
            public void onResponse(ArrayList<CourseModel> courseModels) {
                _listAssessmentAdapter = new ListAssessmentAdapter(courseModels, Assessment.this);
                chooseAchievements.setAdapter(_listAssessmentAdapter);
            }

            @Override
            public void onError(String message) {

            }
        });

        chooseCpmk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                KhsModel itemCpmkSelected = (KhsModel) parent.getItemAtPosition(position);
                selectedCpmk = String.valueOf(itemCpmkSelected.get_idCpmk());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        chooseAchievements.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CourseModel itemAchievementsSelected = (CourseModel) parent.getItemAtPosition(position);
                selectedAchievements = String.valueOf(itemAchievementsSelected.get_idAssessments());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSaveAchievements.setOnClickListener(v -> {
            _mPuskaDataService.addAchievements(_strIdTeacher, selectedAchievements, selectedCpmk, new MPuskaDataService.AddAchievementsListener() {
                @Override
                public void onResponse(String message) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Assessment.this, R.style.AlertDialogStyle);
                    View doneDialog = LayoutInflater.from(Assessment.this).inflate(R.layout.custom_done_dialog, findViewById(R.id.confirm_done_dialog));
                    builder.setView(doneDialog);

                    TextView txtMessage = doneDialog.findViewById(R.id.done_message);
                    txtMessage.setText(message);

                    final AlertDialog alertDialog = builder.create();

                    doneDialog.findViewById(R.id.btn_confirm_done).setOnClickListener(v -> {
                        alertDialog.dismiss();
                        refreshActivity();
                    });

                    if (alertDialog.getWindow() != null) {
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }

                    alertDialog.show();
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(Assessment.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnClose.setOnClickListener(v2 -> {
            dialogInput.dismiss();
        });

        dialogInput.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogInput.show();
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
                        editAssessments(_strIdTeacher);
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

        Spinner chooseAssessments = addAssessments.findViewById(R.id.list_assessments_spinner);
        ImageView btnClose = addAssessments.findViewById(R.id.btn_close);
        TextInputEditText percent = addAssessments.findViewById(R.id.txt_inet_change_percent);
        TextView btnAddNewAssessments = addAssessments.findViewById(R.id.btn_add_new_assessments);
        Button btnSaveAddAssessments = addAssessments.findViewById(R.id.btn_save_add_assessments);

        _mPuskaDataService.getAllAssessments(new MPuskaDataService.AllAssessmentsListener() {
            @Override
            public void onResponse(ArrayList<CourseModel> courseModels) {
                _listAssessmentAdapter = new ListAssessmentAdapter(courseModels, Assessment.this);
                chooseAssessments.setAdapter(_listAssessmentAdapter);

                chooseAssessments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        CourseModel clickedItem = (CourseModel) parent.getItemAtPosition(position);
                        idAssessment = String.valueOf(clickedItem.get_idAssessments());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onError(String message) {

            }
        });

        btnSaveAddAssessments.setOnClickListener(v -> {
            _mPuskaDataService.getAssessments(_strIdTeacher, new MPuskaDataService.AssessmentsListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(ArrayList<CourseModel> courseModels) {
                    int _percentPercent = 0;
                    for (int i = 0; i < courseModels.size(); i++) {
                        _percentPercent = _percentPercent + courseModels.get(i).get_assessmentsPercentage();
                    }

                    _percentPercent = _percentPercent + Integer.parseInt(percent.getText().toString());

                    if (_percentPercent > 100) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Assessment.this, R.style.AlertDialogStyle);
                        View warningDialog = LayoutInflater.from(Assessment.this).inflate(R.layout.custom_warning_dialog, findViewById(R.id.confirm_warning_dialog));
                        builder.setView(warningDialog);

                        TextView txtMessage = warningDialog.findViewById(R.id.warning_message);
                        txtMessage.setText("Bobot melebihi batas");

                        final AlertDialog alertDialog = builder.create();

                        warningDialog.findViewById(R.id.btn_confirm_warning).setOnClickListener(v -> {
                            alertDialog.dismiss();
                        });

                        if (alertDialog.getWindow() != null) {
                            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                        }

                        alertDialog.show();
                    } else {
                        _mPuskaDataService.addAssessments(_strIdTeacher, idAssessment, percent.getText().toString(), new MPuskaDataService.AddAssessmentsListener() {
                            @Override
                            public void onResponse(String message) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Assessment.this, R.style.AlertDialogStyle);
                                View doneDialog = LayoutInflater.from(Assessment.this).inflate(R.layout.custom_done_dialog, findViewById(R.id.confirm_done_dialog));
                                builder.setView(doneDialog);

                                TextView txtMessage = doneDialog.findViewById(R.id.done_message);
                                txtMessage.setText(message);

                                final AlertDialog alertDialog = builder.create();

                                doneDialog.findViewById(R.id.btn_confirm_done).setOnClickListener(v -> {
                                    alertDialog.dismiss();
                                    refreshActivity();
                                });

                                if (alertDialog.getWindow() != null) {
                                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                                }

                                alertDialog.show();
                            }

                            @Override
                            public void onError(String message) {
                                Toast.makeText(Assessment.this, message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onError(String message) {

                }
            });
        });

        btnAddNewAssessments.setOnClickListener(v -> {
            Dialog addNewAssessment = new Dialog(this);
            addNewAssessment.setContentView(R.layout.add_new_assessments_dialog);

            ImageView btnCloseAddNewAssessment = addNewAssessment.findViewById(R.id.btn_close);
            TextInputEditText inputNameAssessment = addNewAssessment.findViewById(R.id.txt_inet_add_new_assessment);
            Button btnAddNewAssessment = addNewAssessment.findViewById(R.id.btn_save_add_new_assessment);

            btnAddNewAssessment.setOnClickListener(v1 -> {
                Toast.makeText(this, inputNameAssessment.getText().toString(), Toast.LENGTH_SHORT).show();
                _mPuskaDataService.createAssessments(inputNameAssessment.getText().toString(), new MPuskaDataService.CreateAssessmentsListener() {
                    @Override
                    public void onResponse(String message) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Assessment.this, R.style.AlertDialogStyle);
                        View doneDialog = LayoutInflater.from(Assessment.this).inflate(R.layout.custom_done_dialog, findViewById(R.id.confirm_done_dialog));
                        builder.setView(doneDialog);

                        TextView txtMessage = doneDialog.findViewById(R.id.done_message);
                        txtMessage.setText(message);

                        final AlertDialog alertDialog = builder.create();

                        doneDialog.findViewById(R.id.btn_confirm_done).setOnClickListener(v -> {
                            alertDialog.dismiss();
                            refreshActivity();
                        });

                        if (alertDialog.getWindow() != null) {
                            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                        }

                        alertDialog.show();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(Assessment.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            });

            btnCloseAddNewAssessment.setOnClickListener(v1 -> {
                addNewAssessment.dismiss();
            });

            addNewAssessment.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            addNewAssessment.show();
        });

        btnClose.setOnClickListener(v -> {
            addAssessments.dismiss();
        });

        addAssessments.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addAssessments.show();
    }

    private void editAssessments(String idTeacher) {
        _mPuskaDataService.getAssessments(idTeacher, new MPuskaDataService.AssessmentsListener() {
            @Override
            public void onResponse(ArrayList<CourseModel> courseModels) {
                setAssessmentsRecycler(courseModels);
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setAssessmentsRecycler(ArrayList<CourseModel> courseModels) {
        _scorePercentagePieChart.setVisibility(View.GONE);
        _listAssessmentsContainer.setVisibility(View.VISIBLE);

        _idAssessments = new String[courseModels.size()];
        _percent = new String[courseModels.size()];
        _keyScore = new String[courseModels.size()];

        for (int i = 0; i < courseModels.size(); i++) {
            _idAssessments[i] = String.valueOf(courseModels.get(i).get_idAssessments());
            _percent[i] = String.valueOf(courseModels.get(i).get_assessmentsPercentage());
            _keyScore[i] = String.valueOf(courseModels.get(i).get_keyScore());
        }

        _changeAssessmentsListAdapter = new ChangeAssessmentsListAdapter(courseModels, Assessment.this, new OnEditTextChanged2() {
            @Override
            public void onTextChanged(int position, String charSeq) {
                _percent[position] = charSeq;
            }

            @Override
            public void beforeTextChanged(int position, String charSeq) {
                _percent[position] = charSeq;
            }
        }, new OnSpinnerChanged() {
            @Override
            public void onItemChanged(int position, String selectedItem) {
                _idAssessments[position] = selectedItem;
            }

            @Override
            public void beforeItemChanged(int position, String selectedItem) {
                //_idAssessments[position] = selectedItem;
            }
        });
        _percentRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        _percentRecycler.setAdapter(_changeAssessmentsListAdapter);

        _btnSaveUpdateAssessments.setOnClickListener(v -> {
            int _percentChecking = 0;
            for (int i = 0; i < _percent.length; i++) {
                _percentChecking = _percentChecking + Integer.parseInt(_percent[i]);
            }

            if (_percentChecking > 100) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Assessment.this, R.style.AlertDialogStyle);
                View warningDialog = LayoutInflater.from(Assessment.this).inflate(R.layout.custom_warning_dialog, findViewById(R.id.confirm_warning_dialog));
                builder.setView(warningDialog);

                TextView txtMessage = warningDialog.findViewById(R.id.warning_message);
                txtMessage.setText("Bobot melebihi batas");

                final AlertDialog alertDialog = builder.create();

                warningDialog.findViewById(R.id.btn_confirm_warning).setOnClickListener(v2 -> {
                    alertDialog.dismiss();
                });

                if (alertDialog.getWindow() != null) {
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }

                alertDialog.show();
            } else {
                updateAssessments(_keyScore, _percent, _idAssessments, _strIdTeacher);
            }
            //Toast.makeText(this, "ID Assessment -> "+ Arrays.toString(_idAssessments) +"\nBobot -> "+ Arrays.toString(_percent), Toast.LENGTH_SHORT).show();
        });
        _btnCancelUpdateAssessments.setOnClickListener(v -> refreshActivity());
    }

    private void updateAssessments(String[] keyScore, String[] percent, String[] idAssessments, String idTeacher) {
        _mPuskaDataService.updateAssessments(idTeacher, keyScore, idAssessments, percent, new MPuskaDataService.UpdateAssessments() {
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
                //refreshActivity();

                Toast.makeText(Assessment.this, String.valueOf(message), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshActivity() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    private void setCpmkRecycler(ArrayList<KhsModel> khsModels, String strIdTeacher, boolean isAchievementsActived) {
        _cpmkListAdapter = new CpmkListAdapter(khsModels, Assessment.this, strIdTeacher, isAchievementsActived);
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
                _scorePercentagePieChart.setCenterTextSize(32);
                _scorePercentagePieChart.setCenterText(totalPercent + "%");
                _scorePercentagePieChart.setCenterTextTypeface(_robotoBold);
                _scorePercentagePieChart.setDrawEntryLabels(false);
                _scorePercentagePieChart.setTransparentCircleRadius(80);
                _scorePercentagePieChart.setHoleRadius(70);
                //_scorePercentagePieChart.setExtraRightOffset(15);
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

//                Legend l = _scorePercentagePieChart.getLegend();
//                l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
//                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//                l.setOrientation(Legend.LegendOrientation.VERTICAL);
//                l.setFormSize(15);
//                l.setFormToTextSpace(5);
//                //l.setWordWrapEnabled(true);
//                l.setYOffset(-30);
//                l.setXOffset(-20);
//                l.setTypeface(_roboto);
//                l.setTextSize(14);
//                l.setYEntrySpace(10f);
//                l.setTextColor(Color.argb(255, 79, 79, 79));
            }

            @Override
            public void onError(String message) {
                Toast.makeText(Assessment.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}