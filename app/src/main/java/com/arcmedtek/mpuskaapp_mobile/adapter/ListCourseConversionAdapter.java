package com.arcmedtek.mpuskaapp_mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.model.CourseModel;

import java.util.ArrayList;

public class ListCourseConversionAdapter extends ArrayAdapter<CourseModel> {

    public ListCourseConversionAdapter(ArrayList<CourseModel> listItem, Context ctx) {
        super(ctx, 0, listItem);
    }

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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dropdown_item_course_conversion, parent, false);
        }

        TextView courseCode = convertView.findViewById(R.id.course_code);
        TextView nameCourse = convertView.findViewById(R.id.name_course);
        CourseModel itemList = getItem(position);

        if (itemList != null) {
            courseCode.setText(String.valueOf(itemList.get_courseCode()));
            nameCourse.setText(String.valueOf(itemList.get_courseName()));
        }

        return convertView;
    }
}
