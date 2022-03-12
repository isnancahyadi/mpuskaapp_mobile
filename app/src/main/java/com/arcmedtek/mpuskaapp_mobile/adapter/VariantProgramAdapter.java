package com.arcmedtek.mpuskaapp_mobile.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.activity.user.lecture.KampusMengajarActivity;
import com.arcmedtek.mpuskaapp_mobile.activity.user.lecture.KknTematikActivity;
import com.arcmedtek.mpuskaapp_mobile.activity.user.lecture.MagangActivity;
import com.arcmedtek.mpuskaapp_mobile.activity.user.lecture.PertukaranPelajarActivity;
import com.arcmedtek.mpuskaapp_mobile.activity.user.lecture.StudiProyekIndependenActivity;
import com.arcmedtek.mpuskaapp_mobile.model.VariantProgramModel;

import java.util.List;

public class VariantProgramAdapter extends RecyclerView.Adapter<VariantProgramAdapter.VariantProgramHolder> {
    List<VariantProgramModel> _variantProgramModels;
    ViewPager2 _viewPager2;

    public VariantProgramAdapter(List<VariantProgramModel> _variantProgramModels, ViewPager2 _viewPager2) {
        this._variantProgramModels = _variantProgramModels;
        this._viewPager2 = _viewPager2;
    }

    @NonNull
    @Override
    public VariantProgramHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VariantProgramHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_program, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VariantProgramHolder holder, int position) {
        holder.setDataItem(_variantProgramModels.get(position));
        holder._itemProgram.setOnClickListener(v -> {
            if (position == 0) {
                v.getContext().startActivity(new Intent(v.getContext(), MagangActivity.class));
            } else if (position == 1) {
                v.getContext().startActivity(new Intent(v.getContext(), PertukaranPelajarActivity.class));
            } else if (position == 2) {
                v.getContext().startActivity(new Intent(v.getContext(), StudiProyekIndependenActivity.class));
            } else if (position == 3) {
                v.getContext().startActivity(new Intent(v.getContext(), KampusMengajarActivity.class));
            } else if (position == 4) {
                v.getContext().startActivity(new Intent(v.getContext(), KknTematikActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return _variantProgramModels.size();
    }

    static class VariantProgramHolder extends RecyclerView.ViewHolder {

        ImageView _imgItemProgram;
        CardView _itemProgram;

        VariantProgramHolder(@NonNull View itemView) {
            super(itemView);

            _imgItemProgram = itemView.findViewById(R.id.img_item_program);
            _itemProgram = itemView.findViewById(R.id.item_program);
        }

        void setDataItem(VariantProgramModel variantProgramModel) {
            _imgItemProgram.setImageResource(variantProgramModel.get_img());
            _itemProgram.setCardBackgroundColor(Color.parseColor(variantProgramModel.get_color()));
        }
    }
}
