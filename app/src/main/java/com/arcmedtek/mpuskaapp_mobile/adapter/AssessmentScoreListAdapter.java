package com.arcmedtek.mpuskaapp_mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    }

    @Override
    public int getItemCount() {
        return _khsModels.size();
    }

    public static class AssessmentHolder extends RecyclerView.ViewHolder {

        TextView _assessmentName, _assessmentScore;

        public AssessmentHolder(@NonNull View itemView) {
            super(itemView);

            _assessmentName = itemView.findViewById(R.id.txt_name_assessment);
            _assessmentScore = itemView.findViewById(R.id.txt_score_assessment);
        }
    }
}
