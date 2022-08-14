package com.arcmedtek.mpuskaapp_mobile.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.activity.user.lecture.ChangeScore;
import com.arcmedtek.mpuskaapp_mobile.activity.user.lecture.ScoreDetail;
import com.arcmedtek.mpuskaapp_mobile.model.KhsModel;
import com.arcmedtek.mpuskaapp_mobile.model.KrsModel;
import com.arcmedtek.mpuskaapp_mobile.service.MPuskaDataService;

import java.util.ArrayList;

public class InputValueAdapter extends RecyclerView.Adapter<InputValueAdapter.InputValueHolder> {

    ArrayList<KrsModel> _krsModels;
    Context _context;

    String _strIdTeacher, _strCollegeYear, _strCourseCode;

    public InputValueAdapter(ArrayList<KrsModel> _krsModels, Context _context, String _strIdTeacher, String _strCollegeYear, String _strCourseCode) {
        this._krsModels = _krsModels;
        this._context = _context;
        this._strIdTeacher = _strIdTeacher;
        this._strCollegeYear = _strCollegeYear;
        this._strCourseCode = _strCourseCode;
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
        KrsModel model = _krsModels.get(position);
        holder._nim.setText(model.get_nim());

        if (model.get_studentMiddleName().equals("")) {
            holder._studentName.setText(model.get_studentFirstName() + " " + model.get_studentLastName());
        } else {
            holder._studentName.setText(model.get_studentFirstName() + " " + model.get_studentMiddleName() + " " + model.get_studentLastName());
        }

        MPuskaDataService _mPuskaDataService;
        _mPuskaDataService = new MPuskaDataService(_context);

        _mPuskaDataService.getGradeStudent(_strIdTeacher, model.get_nim(), new MPuskaDataService.GradeListener() {
            @Override
            public void onResponse(ArrayList<KhsModel> khsModels) {
                String grade = "";
                for (int i = 0; i < khsModels.size(); i++) {
                    grade = khsModels.get(i).get_grade();
                }

                switch (grade) {
                    case "E":
                        holder._grade.setImageResource(R.drawable.ic_grade_e);
                        holder._status.setImageResource(R.drawable.ic_notpassed);
                        break;
                    case "D":
                        holder._grade.setImageResource(R.drawable.ic_grade_d);
                        holder._status.setImageResource(R.drawable.ic_notpassed);
                        break;
                    case "D+":
                        holder._grade.setImageResource(R.drawable.ic_grade_d_plus);
                        holder._status.setImageResource(R.drawable.ic_notpassed);
                        break;
                    case "C-":
                        holder._grade.setImageResource(R.drawable.ic_grade_c_min);
                        holder._status.setImageResource(R.drawable.ic_notpassed);
                        break;
                    case "C":
                        holder._grade.setImageResource(R.drawable.ic_grade_c);
                        holder._status.setImageResource(R.drawable.ic_passed);
                        break;
                    case "C+":
                        holder._grade.setImageResource(R.drawable.ic_grade_c_plus);
                        holder._status.setImageResource(R.drawable.ic_passed);
                        break;
                    case "B-":
                        holder._grade.setImageResource(R.drawable.ic_grade_b_min);
                        holder._status.setImageResource(R.drawable.ic_passed);
                        break;
                    case "B":
                        holder._grade.setImageResource(R.drawable.ic_grade_b);
                        holder._status.setImageResource(R.drawable.ic_passed);
                        break;
                    case "B+":
                        holder._grade.setImageResource(R.drawable.ic_grade_b_plus);
                        holder._status.setImageResource(R.drawable.ic_passed);
                        break;
                    case "A-":
                        holder._grade.setImageResource(R.drawable.ic_grade_a_min);
                        holder._status.setImageResource(R.drawable.ic_passed);
                        break;
                    case "A":
                        holder._grade.setImageResource(R.drawable.ic_grade_a);
                        holder._status.setImageResource(R.drawable.ic_passed);
                        break;
                }
            }

            @Override
            public void onError(String message) {

            }
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(_context, ScoreDetail.class);
            intent.putExtra("ID_krs", String.valueOf(model.get_idKrs()));
            intent.putExtra("ID_teacher", _strIdTeacher);
            intent.putExtra("nim", String.valueOf(model.get_nim()));
            intent.putExtra("first_name", String.valueOf(model.get_studentFirstName()));
            intent.putExtra("middle_name", String.valueOf(model.get_studentMiddleName()));
            intent.putExtra("last_name", String.valueOf(model.get_studentLastName()));
            intent.putExtra("team_name", String.valueOf(model.get_teamName()));
            intent.putExtra("college_year", _strCollegeYear);
            intent.putExtra("course_code", _strCourseCode);
            _context.startActivity(intent);
//            Dialog dialogInput = new Dialog(v.getContext());
//            dialogInput.setContentView(R.layout.input_score);
//
//            TextView nim, name, teamName;
//            ImageView btnClose;
//            Button btnChangeScore;
//            RecyclerView assessmentScoreRecycler;
//            MPuskaDataService mPuskaDataService;
//
//            mPuskaDataService = new MPuskaDataService(_context);
//
//            nim = dialogInput.findViewById(R.id.txt_nim_input_score);
//            name = dialogInput.findViewById(R.id.txt_name_input_score);
//            teamName = dialogInput.findViewById(R.id.txt_team_name_input_score);
//            btnClose = dialogInput.findViewById(R.id.btn_close_input);
//            btnChangeScore = dialogInput.findViewById(R.id.btn_change_score);
//            assessmentScoreRecycler = dialogInput.findViewById(R.id.assessment_score_list);
//
//            nim.setText(model.get_nim());
//            name.setText(model.get_studentFirstName() + " " + model.get_studentMiddleName() + " " + model.get_studentLastName());
//            teamName.setText(model.get_teamName());
//
//            mPuskaDataService.getStudentScore(String.valueOf(model.get_idKrs()), new MPuskaDataService.StudentScoreListener() {
//                @Override
//                public void onResponse(ArrayList<KhsModel> khsModels) {
//                    AssessmentScoreListAdapter assessmentScoreListAdapter;
//                    assessmentScoreListAdapter = new AssessmentScoreListAdapter(khsModels, _context);
//                    assessmentScoreRecycler.setLayoutManager(new LinearLayoutManager(_context.getApplicationContext()));
//
//                    assessmentScoreRecycler.setAdapter(assessmentScoreListAdapter);
//                }
//
//                @Override
//                public void onError(String message) {
//
//                }
//            });
//
//            btnChangeScore.setOnClickListener(v1 -> {
//                Intent intent = new Intent(_context, ChangeScore.class);
//                intent.putExtra("ID_krs", String.valueOf(model.get_idKrs()));
//                intent.putExtra("college_year", _strCollegeYear);
//                intent.putExtra("nim", model.get_nim());
//                intent.putExtra("student_name", model.get_studentFirstName() + " " + model.get_studentMiddleName() + " " + model.get_studentLastName());
//                intent.putExtra("team_name", model.get_teamName());
//                _context.startActivity(intent);
//                dialogInput.dismiss();
//            });
//
//            btnClose.setOnClickListener(v2 -> {
//                dialogInput.dismiss();
//            });
//
//            dialogInput.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            dialogInput.show();
        });
    }

    @Override
    public int getItemCount() {
        return _krsModels.size();
    }

    public static class InputValueHolder extends RecyclerView.ViewHolder {

        TextView _nim, _studentName;
        ImageView _grade, _status;

        public InputValueHolder(@NonNull View itemView) {
            super(itemView);

            _nim = itemView.findViewById(R.id.txt_nim_list_student);
            _studentName = itemView.findViewById(R.id.txt_name_list_student);
            _grade = itemView.findViewById(R.id.grade);
            _status = itemView.findViewById(R.id.status);
        }
    }
}
