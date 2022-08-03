package com.arcmedtek.mpuskaapp_mobile.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.model.KrsModel;
import com.arcmedtek.mpuskaapp_mobile.service.MPuskaDataService;

import java.util.ArrayList;

public class CourseConversionAdapter extends RecyclerView.Adapter<CourseConversionAdapter.CourseConversionHolder> {

    ArrayList<KrsModel> _krsModels;
    Context _context;

    public CourseConversionAdapter(ArrayList<KrsModel> _krsModels, Context _context) {
        this._krsModels = _krsModels;
        this._context = _context;
    }

    @NonNull
    @Override
    public CourseConversionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context).inflate(R.layout.course_conversion_row_item, parent, false);

        return new CourseConversionHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CourseConversionHolder holder, int position) {
        KrsModel model = _krsModels.get(position);
        holder._nim.setText(model.get_nim());
        holder._studentName.setText(model.get_studentFirstName() + " " + model.get_studentMiddleName() + " " + model.get_studentLastName());

        MPuskaDataService _mPuskaDataService;
        _mPuskaDataService = new MPuskaDataService(_context);

        _mPuskaDataService.searchStudentKhsCon(String.valueOf(model.get_idKrs()), new MPuskaDataService.SearchStudentKhsCon() {
            @SuppressLint("UseCompatLoadingForColorStateLists")
            @Override
            public void onResponse(String message) {
                holder._btnConvert.setBackgroundTintList(_context.getResources().getColorStateList(R.color.color_6dff5d));
                holder._btnConvert.setText("Converted");
            }

            @Override
            public void onError(String message) {

            }
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
