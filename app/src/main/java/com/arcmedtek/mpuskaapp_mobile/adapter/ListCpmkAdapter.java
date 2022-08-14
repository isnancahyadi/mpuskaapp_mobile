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
import com.arcmedtek.mpuskaapp_mobile.model.KhsModel;

import java.util.ArrayList;
import java.util.List;

public class ListCpmkAdapter extends ArrayAdapter<KhsModel> {

    public ListCpmkAdapter(Context context, ArrayList<KhsModel> objects) {
        super(context, 0, objects);
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dropdown_item_cpmk, parent, false);
        }

        TextView idCpmk = convertView.findViewById(R.id.id_cpmk);
        TextView cpmk = convertView.findViewById(R.id.name_cpmk);
        KhsModel itemList = getItem(position);

        if (itemList != null) {
            idCpmk.setText(String.valueOf(itemList.get_idCpmk()));
            cpmk.setText(itemList.get_cpmk());
        }

        return convertView;
    }

}
