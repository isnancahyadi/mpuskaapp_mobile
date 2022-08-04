package com.arcmedtek.mpuskaapp_mobile.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.activity.user.lecture.CourseConversion;
import com.arcmedtek.mpuskaapp_mobile.model.CourseModel;
import com.arcmedtek.mpuskaapp_mobile.model.KrsModel;
import com.arcmedtek.mpuskaapp_mobile.service.MPuskaDataService;

import java.util.ArrayList;

public class CourseConversionAdapter extends RecyclerView.Adapter<CourseConversionAdapter.CourseConversionHolder> {

    ArrayList<KrsModel> _krsModels;
    Context _context;
    String _courseCode, _itemSelected;
    ListCourseConversionAdapter adapter;

    public CourseConversionAdapter(ArrayList<KrsModel> _krsModels, String _courseCode, Context _context) {
        this._krsModels = _krsModels;
        this._courseCode = _courseCode;
        this._context = _context;
    }

    @NonNull
    @Override
    public CourseConversionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context).inflate(R.layout.course_conversion_row_item, parent, false);

        return new CourseConversionHolder(view);
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull CourseConversionHolder holder, int position) {
        KrsModel model = _krsModels.get(position);
        holder._nim.setText(model.get_nim());
        holder._studentName.setText(model.get_studentFirstName() + " " + model.get_studentMiddleName() + " " + model.get_studentLastName());

        MPuskaDataService _mPuskaDataService;
        _mPuskaDataService = new MPuskaDataService(_context);

        _mPuskaDataService.searchStudentKhsCon(String.valueOf(model.get_idKrs()), new MPuskaDataService.SearchStudentKhsConListener() {
            @SuppressLint("UseCompatLoadingForColorStateLists")
            @Override
            public void onResponse(String message) {
                holder._btnConvert.setBackgroundTintList(_context.getResources().getColorStateList(R.color.color_6dff5d));
                holder._btnConvert.setText("Converted");
                holder._btnConvert.setEnabled(false);
            }

            @SuppressLint("UseCompatLoadingForColorStateLists")
            @Override
            public void onError(String message) {
                holder._btnConvert.setBackgroundTintList(_context.getResources().getColorStateList(R.color.color_ff5d5d));
                holder._btnConvert.setText("Convert");
                holder._btnConvert.setEnabled(true);
            }
        });

        holder._btnConvert.setOnClickListener(v -> {
            Dialog dialogConvert = new Dialog(v.getContext());
            dialogConvert.setContentView(R.layout.convert_course);

            ImageView btnClose;
            Spinner listCourseConversion;
            Button btnSaveConversion;
            MPuskaDataService mPuskaDataService;

            mPuskaDataService = new MPuskaDataService(_context);

            btnClose = dialogConvert.findViewById(R.id.btn_close_convert_course);
            listCourseConversion = dialogConvert.findViewById(R.id.list_course_conversion);
            btnSaveConversion = dialogConvert.findViewById(R.id.btn_convert_course);

            mPuskaDataService.getCourseConversion(_courseCode, new MPuskaDataService.GetCourseConversionListener() {
                @Override
                public void onResponse(ArrayList<CourseModel> courseModels) {
                    adapter = new ListCourseConversionAdapter(courseModels, _context);
                    listCourseConversion.setAdapter(adapter);
                }

                @Override
                public void onError(String message) {

                }
            });

            listCourseConversion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    CourseModel selected = (CourseModel) parent.getItemAtPosition(pos);
                    _itemSelected = String.valueOf(selected.get_courseCode());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            btnSaveConversion.setOnClickListener(v1 -> {
                mPuskaDataService.addKhsConversion(String.valueOf(model.get_idKrs()), _itemSelected, new MPuskaDataService.AddKhsConversionListener() {
                    @Override
                    public void onResponse(String message) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(_context, R.style.AlertDialogStyle);
                        View doneDialog = LayoutInflater.from(_context).inflate(R.layout.custom_done_dialog, v1.findViewById(R.id.confirm_done_dialog));
                        builder.setView(doneDialog);

                        TextView txtMessage = doneDialog.findViewById(R.id.done_message);
                        txtMessage.setText(message);

                        final AlertDialog alertDialog = builder.create();

                        doneDialog.findViewById(R.id.btn_confirm_done).setOnClickListener(v2 -> {
                            alertDialog.dismiss();
                        });

                        if (alertDialog.getWindow() != null) {
                            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                        }

                        alertDialog.show();
                    }

                    @Override
                    public void onError(String message) {

                    }
                });

                notifyDataSetChanged();
                dialogConvert.dismiss();
            });

            btnClose.setOnClickListener(v1 -> dialogConvert.dismiss());

            dialogConvert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogConvert.show();
        });
    }

    @Override
    public int getItemCount() {
        return _krsModels.size();
    }

    public static class CourseConversionHolder extends RecyclerView.ViewHolder {

        TextView _nim, _studentName;
        Button _btnConvert;

        public CourseConversionHolder(@NonNull View itemView) {
            super(itemView);

            _nim = itemView.findViewById(R.id.txt_nim_list_student);
            _studentName = itemView.findViewById(R.id.txt_name_list_student);
            _btnConvert = itemView.findViewById(R.id.btn_convert);
        }
    }
}
