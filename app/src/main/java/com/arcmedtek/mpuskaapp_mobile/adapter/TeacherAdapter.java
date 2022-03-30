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
import com.arcmedtek.mpuskaapp_mobile.model.TeacherModel;

import java.util.ArrayList;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherHolder> {

    ArrayList<TeacherModel> _teacherModels;
    Context _context;

    public TeacherAdapter(ArrayList<TeacherModel> _teacherModels, Context _context) {
        this._teacherModels = _teacherModels;
        this._context = _context;
    }

    @NonNull
    @Override
    public TeacherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context).inflate(R.layout.teacher_row_item, parent, false);

        return new TeacherHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TeacherHolder holder, int position) {
        TeacherModel model = _teacherModels.get(position);
        holder._courseCode.setText(model.get_courseCode() + " (Thn. " + model.get_collegeYear() + ")");
        holder._nameCourse.setText(model.get_courseName() + " (" + model.get_classRoom() + ")");
    }

    @Override
    public int getItemCount() {
        return _teacherModels.size();
    }

    public static class TeacherHolder extends RecyclerView.ViewHolder {

        TextView _nameCourse, _courseCode;

        public TeacherHolder(@NonNull View itemView) {
            super(itemView);

            _nameCourse = itemView.findViewById(R.id.name_course);
            _courseCode = itemView.findViewById(R.id.course_code);
        }
    }
}
