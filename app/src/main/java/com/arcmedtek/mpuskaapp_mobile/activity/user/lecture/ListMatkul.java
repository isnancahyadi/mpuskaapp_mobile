package com.arcmedtek.mpuskaapp_mobile.activity.user.lecture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.google.android.material.textfield.TextInputEditText;

public class ListMatkul extends AppCompatActivity {

    TextInputEditText _txtInetSearchStudyYear;
    Button _btnSearchStudyYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_matkul);
        
        _txtInetSearchStudyYear = findViewById(R.id.txt_inet_search_study_year);
        _btnSearchStudyYear = findViewById(R.id.btn_search_study_year);
        
        _txtInetSearchStudyYear.addTextChangedListener(searchStudyYearTextWatcher);
        
        _btnSearchStudyYear.setOnClickListener(v -> searchCourse());
    }

    private void searchCourse() {
    }

    private final TextWatcher searchStudyYearTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String searchStudyYearInput = String.valueOf(_txtInetSearchStudyYear.getText());

            _btnSearchStudyYear.setEnabled(!searchStudyYearInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}