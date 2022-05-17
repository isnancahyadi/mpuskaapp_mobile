package com.arcmedtek.mpuskaapp_mobile.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.model.KhsModel;
import com.arcmedtek.mpuskaapp_mobile.service.MPuskaDataService;

import java.util.ArrayList;

public class InputValueAdapter extends RecyclerView.Adapter<InputValueAdapter.InputValueHolder> {

    ArrayList<KhsModel> _khsModels;
    Context _context;

    String _strCodeCourse, _strClassroom, _strCollegeYear;

    public InputValueAdapter(ArrayList<KhsModel> _khsModels, Context _context, String _strCodeCourse, String _strClassroom, String _strCollegeYear) {
        this._khsModels = _khsModels;
        this._context = _context;
        this._strCodeCourse = _strCodeCourse;
        this._strClassroom = _strClassroom;
        this._strCollegeYear = _strCollegeYear;
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

            TextView nim, name, teamName;
            ImageView btnClose;
            RecyclerView assessmentScoreRecycler;
            MPuskaDataService mPuskaDataService;

            mPuskaDataService = new MPuskaDataService(_context);

            nim = dialogInput.findViewById(R.id.txt_nim_input_score);
            name = dialogInput.findViewById(R.id.txt_name_input_score);
            teamName = dialogInput.findViewById(R.id.txt_team_name_input_score);
            btnClose = dialogInput.findViewById(R.id.btn_close_input);
            assessmentScoreRecycler = dialogInput.findViewById(R.id.assessment_score_list);

            nim.setText(model.get_nim());
            name.setText(model.get_studentFirstName() + " " + model.get_studentMiddleName() + " " + model.get_studentLastName());
            teamName.setText(model.get_teamName());

            mPuskaDataService.getStudentScore(model.get_nim(), _strCodeCourse, _strClassroom, _strCollegeYear, new MPuskaDataService.StudentScoreListener() {
                @Override
                public void onResponse(ArrayList<KhsModel> khsModels) {
                    AssessmentScoreListAdapter assessmentScoreListAdapter;
                    assessmentScoreListAdapter = new AssessmentScoreListAdapter(khsModels, _context);
                    assessmentScoreRecycler.setLayoutManager(new LinearLayoutManager(_context.getApplicationContext()));

                    assessmentScoreRecycler.setAdapter(assessmentScoreListAdapter);
                }

                @Override
                public void onError(String message) {

                }
            });

            btnClose.setOnClickListener(v2 -> {
                dialogInput.dismiss();
            });

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
