package com.arcmedtek.mpuskaapp_mobile.adapter;

import android.content.Context;
import android.content.RestrictionEntry;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

    public ListAssessmentAdapter(@NonNull Context context, @NonNull ArrayList<CourseModel> listAssessments) {
        super(context, 0, listAssessments);
        listItem = new ArrayList<>(listAssessments);
        ctx = context;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return listAssessmentFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dropdown_item_assessments, parent, false);
        }

        TextView idAssessments, nameAssessments;

        idAssessments = convertView.findViewById(R.id.id_assessments);
        nameAssessments = convertView.findViewById(R.id.name_assessments);

        CourseModel list = getItem(position);

        idAssessments.setText(String.valueOf(list.get_idAssessments()));
        nameAssessments.setText(list.get_assessments());

        return convertView;
    }

    private Filter listAssessmentFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            ArrayList<CourseModel> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(listItem);
            } else {
                String filterPattern = constraint.toString().trim();

                for (CourseModel item : listItem) {
                    if (item.get_assessments().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();
            return null;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((CourseModel) resultValue).get_assessments();
        }
    };
}
