package com.arcmedtek.mpuskaapp_mobile.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.model.KhsModel;

import java.util.ArrayList;

public class InputValueAdapter extends RecyclerView.Adapter<InputValueAdapter.InputValueHolder> {

    ArrayList<KhsModel> _khsModels;
    Context _context;

    public InputValueAdapter(ArrayList<KhsModel> _khsModels, Context _context) {
        this._khsModels = _khsModels;
        this._context = _context;
    }

    @NonNull
    @Override
    public InputValueHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context).inflate(R.layout.input_value_row_item, parent, false);

        return new InputValueHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull InputValueHolder holder, int position) {
        KhsModel model = _khsModels.get(position);
        holder._nim.setText(model.get_nim());
        holder._studentName.setText(model.get_studentFirstName() + " " + model.get_studentMiddleName() + " " + model.get_studentLastName());

        holder.itemView.setOnClickListener(v -> {
            Dialog dialogInput = new Dialog(v.getContext());
            dialogInput.setContentView(R.layout.input_score);

            dialogInput.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogInput.show();
        });
    }

    @Override
    public int getItemCount() {
        return _khsModels.size();
    }

    public static class InputValueHolder extends RecyclerView.ViewHolder {

        TextView _nim, _studentName;

        public InputValueHolder(@NonNull View itemView) {
            super(itemView);

            _nim = itemView.findViewById(R.id.txt_nim_list_student);
            _studentName = itemView.findViewById(R.id.txt_name_list_student);
        }
    }
}
