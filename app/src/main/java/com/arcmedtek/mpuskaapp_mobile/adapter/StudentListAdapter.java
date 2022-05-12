package com.arcmedtek.mpuskaapp_mobile.adapter;

import android.annotation.SuppressLint;
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

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentListHolder> {

    ArrayList<KhsModel> _khsModels;
    Context _context;

    public StudentListAdapter(ArrayList<KhsModel> _khsModels, Context _context) {
        this._khsModels = _khsModels;
        this._context = _context;
    }

    @NonNull
    @Override
    public StudentListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context).inflate(R.layout.student_list_row_item, parent, false);

        return new StudentListHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull StudentListHolder holder, int position) {
        KhsModel model = _khsModels.get(position);
        holder._nim.setText(model.get_nim());
        holder._studentName.setText(model.get_studentFirstName() + " " + model.get_studentMiddleName() + " " + model.get_studentLastName());
    }

    @Override
    public int getItemCount() {
        return _khsModels.size();
    }

    public static class StudentListHolder extends RecyclerView.ViewHolder {

        TextView _nim, _studentName;

        public StudentListHolder(@NonNull View itemView) {
            super(itemView);

            _nim = itemView.findViewById(R.id.txt_nim_list_student);
            _studentName = itemView.findViewById(R.id.txt_name_list_student);
        }
    }
}
