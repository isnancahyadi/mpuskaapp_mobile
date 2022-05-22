package com.arcmedtek.mpuskaapp_mobile.activity.user.lecture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.arcmedtek.mpuskaapp_mobile.R;
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

import java.util.ArrayList;
import java.util.List;

public class Assessment extends AppCompatActivity {

    PieChart _scorePercentagePieChart;
    int totalPercent = 0;

    ArrayList<PieEntry> _assessment;
    TextView _collegeYear, _nameCourse, _codeCourse, _classroom;
    String _strCollegeYear, _strNameCourse, _strCodeCourse, _strClassroom;

    MPuskaDataService _mPuskaDataService;
    Typeface _robotoBold, _roboto;

    @SuppressLint({"NewApi", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);

        _strCollegeYear = getIntent().getStringExtra("college_year");
        _strNameCourse = getIntent().getStringExtra("course_name");
        _strClassroom = getIntent().getStringExtra("classroom");
        _strCodeCourse = getIntent().getStringExtra("course_code");

        _scorePercentagePieChart = findViewById(R.id.score_percentage_pie_chart);
        _collegeYear = findViewById(R.id.txt_college_year_assessment);
        _nameCourse = findViewById(R.id.txt_name_course);
        _codeCourse = findViewById(R.id.txt_code_course);
        _classroom = findViewById(R.id.txt_classroom);

        _collegeYear.setText("TA. " + _strCollegeYear);
        _nameCourse.setText(_strNameCourse);
        _codeCourse.setText(_strCodeCourse);
        _classroom.setText(_strClassroom);

        _robotoBold = ResourcesCompat.getFont(Assessment.this, R.font.roboto_bold);
        _roboto = ResourcesCompat.getFont(Assessment.this, R.font.roboto);
        _assessment = new ArrayList<>();
        _mPuskaDataService = new MPuskaDataService(Assessment.this);

        assessmentsChart();
    }

    private void assessmentsChart() {
        _mPuskaDataService.getAssessments(_strCodeCourse, _strClassroom, _strCollegeYear, new MPuskaDataService.AssessmentsListener() {
            @Override
            public void onResponse(ArrayList<KhsModel> khsModels) {
                for (int i = 0; i < khsModels.size(); i++) {
                    _assessment.add(new PieEntry(khsModels.get(i).get_percent(), khsModels.get(i).get_assessment()));
                    totalPercent = totalPercent + khsModels.get(i).get_percent();
                }
                PieDataSet pieDataSet = new PieDataSet(_assessment, null);
                pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                pieDataSet.setDrawValues(false);
                pieDataSet.setSliceSpace(10f);

                PieData pieData = new PieData(pieDataSet);
                pieData.setValueFormatter(new PercentFormatter(_scorePercentagePieChart));

                _scorePercentagePieChart.getDescription().setEnabled(false);
                _scorePercentagePieChart.setCenterTextColor(Color.argb(255, 95, 126, 237));
                _scorePercentagePieChart.setCenterTextSize(36);
                _scorePercentagePieChart.setCenterText(totalPercent + "%");
                _scorePercentagePieChart.setCenterTextTypeface(_robotoBold);
                _scorePercentagePieChart.setDrawEntryLabels(false);
                _scorePercentagePieChart.setTransparentCircleRadius(80);
                _scorePercentagePieChart.setHoleRadius(70);
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
                l.setFormSize(20);
                l.setFormToTextSpace(10);
                l.setYOffset(-40);
                l.setXOffset(-30);
                l.setTypeface(_roboto);
                l.setTextSize(19);
                l.setYEntrySpace(10f);
                l.setTextColor(Color.argb(255, 79, 79, 79));
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}