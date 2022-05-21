package com.arcmedtek.mpuskaapp_mobile.activity.user.lecture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.arcmedtek.mpuskaapp_mobile.R;
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

public class Assessment extends AppCompatActivity {

    PieChart _scorePercentagePieChart;
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

        _assessment = new ArrayList<>();
        _mPuskaDataService = new MPuskaDataService(Assessment.this);

        percentageChart();
    }

    private void percentageChart() {
        _assessment.add(new PieEntry(15, "tugas"));
        _assessment.add(new PieEntry(25, "UTS"));
        _assessment.add(new PieEntry(45, "UAS"));

        PieDataSet pieDataSet = new PieDataSet(_assessment, "");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setDrawValues(false);
        pieDataSet.setSliceSpace(10f);

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter(_scorePercentagePieChart));

        _robotoBold = ResourcesCompat.getFont(Assessment.this, R.font.roboto_bold);
        _roboto = ResourcesCompat.getFont(Assessment.this, R.font.roboto);

        _scorePercentagePieChart.setData(pieData);
        _scorePercentagePieChart.getDescription().setEnabled(false);
        _scorePercentagePieChart.setCenterTextColor(Color.argb(255, 95, 126, 237));
        _scorePercentagePieChart.setCenterTextSize(36);
        _scorePercentagePieChart.setCenterText("100%");
        _scorePercentagePieChart.setCenterTextTypeface(_robotoBold);
        _scorePercentagePieChart.setDrawEntryLabels(false);
        _scorePercentagePieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                _scorePercentagePieChart.setCenterText((int) e.getY() + "%");
            }

            @Override
            public void onNothingSelected() {
                _scorePercentagePieChart.setCenterText("100%");
            }
        });
        _scorePercentagePieChart.animate();

        Legend l = _scorePercentagePieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setTypeface(_roboto);
        l.setTextSize(17);
        l.setYEntrySpace(10f);
        l.setTextColor(Color.argb(255, 79, 79, 79));
    }
}