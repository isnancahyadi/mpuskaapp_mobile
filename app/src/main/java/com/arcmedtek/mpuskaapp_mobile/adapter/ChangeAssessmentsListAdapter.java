package com.arcmedtek.mpuskaapp_mobile.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.config.OnEditTextChanged;
import com.arcmedtek.mpuskaapp_mobile.config.OnEditTextChanged2;
import com.arcmedtek.mpuskaapp_mobile.model.CourseModel;
import com.arcmedtek.mpuskaapp_mobile.model.KhsModel;
import com.arcmedtek.mpuskaapp_mobile.service.MPuskaDataService;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ChangeAssessmentsListAdapter extends RecyclerView.Adapter<ChangeAssessmentsListAdapter.ChangeAssessmentsHolder> {

    ArrayList<CourseModel> _courseModels;
    Context _context;
    OnEditTextChanged2 _onEditTextChanged2;
    ListAssessmentAdapter adapter;

    public ChangeAssessmentsListAdapter(ArrayList<CourseModel> _courseModels, Context _context, OnEditTextChanged2 _onEditTextChanged2) {
        this._courseModels = _courseModels;
        this._context = _context;
        this._onEditTextChanged2 = _onEditTextChanged2;
    }

    @NonNull
    @Override
    public ChangeAssessmentsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context).inflate(R.layout.change_assessment_row_item, parent, false);

        return new ChangeAssessmentsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChangeAssessmentsHolder holder, @SuppressLint("RecyclerView") int position) {
        CourseModel model = _courseModels.get(position);
        MPuskaDataService mPuskaDataService = new MPuskaDataService(_context);

        mPuskaDataService.getAllAssessments(new MPuskaDataService.AllAssessmentsListener() {
            @Override
            public void onResponse(ArrayList<CourseModel> courseModels) {
                adapter = new ListAssessmentAdapter(courseModels, _context);
                holder._assessment.setAdapter(adapter);

                ListAssessmentAdapter adapter2 = new ListAssessmentAdapter(_courseModels, _context);

                for (int i = 0; i < courseModels.size(); i++) {
                    if (adapter2.getItem(position).get_idAssessments() == adapter.getItem(i).get_idAssessments()) {
                        holder._assessment.setSelection(i);
                    }
                }
            }

            @Override
            public void onError(String message) {

            }
        });

        holder._percent.setText(String.valueOf(model.get_assessmentsPercentage()));

        holder._percent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                _onEditTextChanged2.beforeTextChanged(position, s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _onEditTextChanged2.onTextChanged(position, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return _courseModels.size();
    }

    public static class ChangeAssessmentsHolder extends RecyclerView.ViewHolder {

        Spinner _assessment;
        TextInputEditText _percent;

        public ChangeAssessmentsHolder(@NonNull View itemView) {
            super(itemView);

            _assessment = itemView.findViewById(R.id.list_assessments_spinner);
            _percent = itemView.findViewById(R.id.txt_inet_change_percent);
        }
    }
}
