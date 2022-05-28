package com.arcmedtek.mpuskaapp_mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.model.KhsModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ChangeScoreListAdapter extends RecyclerView.Adapter<ChangeScoreListAdapter.ChangeScoreHolder> {

    ArrayList<KhsModel> _khsModels;
    Context _context;

    public ChangeScoreListAdapter(ArrayList<KhsModel> _khsModels, Context _context) {
        this._khsModels = _khsModels;
        this._context = _context;
    }

    @NonNull
    @Override
    public ChangeScoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context).inflate(R.layout.change_score_row_item, parent, false);

        return new ChangeScoreHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChangeScoreHolder holder, int position) {
        KhsModel model = _khsModels.get(position);
        holder._assessment.setText(model.get_assessment());
        holder._score.setText(String.valueOf(model.get_score()));
    }

    @Override
    public int getItemCount() {
        return _khsModels.size();
    }

    public static class ChangeScoreHolder extends RecyclerView.ViewHolder {

        TextView _assessment;
        TextInputEditText _score;

        public ChangeScoreHolder(@NonNull View itemView) {
            super(itemView);

            _assessment = itemView.findViewById(R.id.txt_name_assessment);
            _score = itemView.findViewById(R.id.txt_inet_chage_score);
        }
    }
}
