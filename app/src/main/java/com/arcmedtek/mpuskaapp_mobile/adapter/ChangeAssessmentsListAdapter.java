package com.arcmedtek.mpuskaapp_mobile.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.config.OnEditTextChanged;
import com.arcmedtek.mpuskaapp_mobile.config.OnEditTextChanged2;
import com.arcmedtek.mpuskaapp_mobile.model.KhsModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ChangeAssessmentsListAdapter extends RecyclerView.Adapter<ChangeAssessmentsListAdapter.ChangeAssessmentsHolder> {

    ArrayList<KhsModel> _khsModels;
    Context _context;
    OnEditTextChanged _onEditTextChanged;
    OnEditTextChanged2 _onEditTextChanged2;

    public ChangeAssessmentsListAdapter(ArrayList<KhsModel> _khsModels, Context _context, OnEditTextChanged _onEditTextChanged, OnEditTextChanged2 _onEditTextChanged2) {
        this._khsModels = _khsModels;
        this._context = _context;
        this._onEditTextChanged = _onEditTextChanged;
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
        KhsModel model = _khsModels.get(position);
        holder._assessment.setText(model.get_assessment());
        holder._percent.setText(String.valueOf(model.get_percent()));

        holder._assessment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                _onEditTextChanged.beforeTextChanged(position, s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _onEditTextChanged.onTextChanged(position,s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder._percent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                _onEditTextChanged2.beforeTextChanged(position, s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _onEditTextChanged2.onTextChanged(position,s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return _khsModels.size();
    }

    public static class ChangeAssessmentsHolder extends RecyclerView.ViewHolder {

        TextInputEditText _assessment, _percent;

        public ChangeAssessmentsHolder(@NonNull View itemView) {
            super(itemView);

            _assessment = itemView.findViewById(R.id.txt_inet_chage_assessments);
            _percent = itemView.findViewById(R.id.txt_inet_change_percent);
        }
    }
}
