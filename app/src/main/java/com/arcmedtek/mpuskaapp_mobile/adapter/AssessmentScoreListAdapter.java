package com.arcmedtek.mpuskaapp_mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.model.KhsModel;

import java.util.ArrayList;
import java.util.List;

public class AssessmentScoreListAdapter extends RecyclerView.Adapter<AssessmentScoreListAdapter.AssessmentHolder> {

    ArrayList<KhsModel> _khsModels;
    Context _context;

    public AssessmentScoreListAdapter(ArrayList<KhsModel> _khsModels, Context _context) {
        this._khsModels = _khsModels;
        this._context = _context;
    }

    @NonNull
    @Override
    public AssessmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context).inflate(R.layout.score_row_item, parent, false);

        return new AssessmentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentHolder holder, int position) {
        KhsModel model = _khsModels.get(position);
        holder._assessmentName.setText(model.get_assessment());
        holder._assessmentScore.setText(String.valueOf(model.get_score()));

        if (model.get_score() >= 0 && model.get_score() < 40.00) {
            holder._grade.setImageResource(R.drawable.ic_grade_e);
        } else if (model.get_score() >= 40.00 && model.get_score() < 43.75) {
            holder._grade.setImageResource(R.drawable.ic_grade_d);
        } else if (model.get_score() >= 43.75 && model.get_score() < 51.25) {
            holder._grade.setImageResource(R.drawable.ic_grade_d_plus);
        } else if (model.get_score() >= 51.25 && model.get_score() < 55.00) {
            holder._grade.setImageResource(R.drawable.ic_grade_c_min);
        } else if (model.get_score() >= 55.00 && model.get_score() < 57.50) {
            holder._grade.setImageResource(R.drawable.ic_grade_c);
        } else if (model.get_score() >= 57.50 && model.get_score() < 62.50) {
            holder._grade.setImageResource(R.drawable.ic_grade_c_plus);
        } else if (model.get_score() >= 62.50 && model.get_score() < 65.00) {
            holder._grade.setImageResource(R.drawable.ic_grade_b_min);
        } else if (model.get_score() >= 65.00 && model.get_score() < 68.75) {
            holder._grade.setImageResource(R.drawable.ic_grade_b);
        } else if (model.get_score() >= 68.75 && model.get_score() < 76.25) {
            holder._grade.setImageResource(R.drawable.ic_grade_b_plus);
        } else if (model.get_score() >= 76.25 && model.get_score() < 80.00) {
            holder._grade.setImageResource(R.drawable.ic_grade_a_min);
        } else if (model.get_score() >= 80.00 && model.get_score() <= 100.00) {
            holder._grade.setImageResource(R.drawable.ic_grade_a);
        }
    }

    @Override
    public int getItemCount() {
        return _khsModels.size();
    }

    public static class AssessmentHolder extends RecyclerView.ViewHolder {

        TextView _assessmentName, _assessmentScore;
        ImageView _grade;

        public AssessmentHolder(@NonNull View itemView) {
            super(itemView);

            _assessmentName = itemView.findViewById(R.id.txt_name_assessment);
            _assessmentScore = itemView.findViewById(R.id.txt_score_assessment);
            _grade = itemView.findViewById(R.id.grade);
        }
    }
}
