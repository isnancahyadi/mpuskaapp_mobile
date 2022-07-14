package com.arcmedtek.mpuskaapp_mobile.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.RestrictionEntry;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.model.CourseModel;

import java.util.ArrayList;
import java.util.List;

public class ListAssessmentAdapter extends ArrayAdapter<CourseModel> {

    ArrayList<CourseModel> listItem;
    Context ctx;

    public ListAssessmentAdapter(ArrayList<CourseModel> listItem, Context ctx) {
//        this.listItem = listItem;
//        this.ctx = ctx;
        super(ctx, 0, listItem);
    }

//    @Override
//    public int getCount() {
//        return listItem != null ? listItem.size() : 0;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return position;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        @SuppressLint("ViewHolder") View view = LayoutInflater.from(ctx).inflate(R.layout.dropdown_item_assessments, parent, false);
//
//        TextView idAssessment = view.findViewById(R.id.id_assessments);
//        TextView nameAssessment = view.findViewById(R.id.name_assessments);
//
//        idAssessment.setText(String.valueOf(listItem.get(position).get_idAssessments()));
//        nameAssessment.setText(listItem.get(position).get_assessments());
//
//        return view;
//    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dropdown_item_assessments, parent, false);
        }

        TextView idAssessment = convertView.findViewById(R.id.id_assessments);
        TextView nameAssessment = convertView.findViewById(R.id.name_assessments);
        CourseModel itemList = getItem(position);

        if (itemList != null) {
            idAssessment.setText(String.valueOf(itemList.get_idAssessments()));
            nameAssessment.setText(itemList.get_assessments());
        }

        return convertView;
    }
}
