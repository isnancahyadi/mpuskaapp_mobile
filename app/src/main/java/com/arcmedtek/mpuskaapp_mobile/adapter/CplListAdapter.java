package com.arcmedtek.mpuskaapp_mobile.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.model.KhsModel;

import java.util.ArrayList;

public class CplListAdapter extends RecyclerView.Adapter<CplListAdapter.CplListHolder> {

    ArrayList<KhsModel> _khsModels;
    Context _context;

    public CplListAdapter(ArrayList<KhsModel> _khsModels, Context _context) {
        this._khsModels = _khsModels;
        this._context = _context;
    }

    @NonNull
    @Override
    public CplListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context).inflate(R.layout.achievements_list_row_item, parent, false);

        return new CplListHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CplListHolder holder, int position) {
        KhsModel model = _khsModels.get(position);
        holder._cplCpmk.setText("CPL " + model.get_idCpl());
        holder._achievements.setText(model.get_cpl());

        boolean isExpand = _khsModels.get(position).is_expand();
        if (isExpand) {
            holder._expandAchievements.setVisibility(View.VISIBLE);
            holder._icExpand.setImageResource(R.drawable.ic_expand_less);
        } else {
            holder._expandAchievements.setVisibility(View.GONE);
            holder._icExpand.setImageResource(R.drawable.ic_expand_more);
        }
    }

    @Override
    public int getItemCount() {
        return _khsModels.size();
    }

    public class CplListHolder extends RecyclerView.ViewHolder {

        TextView _cplCpmk, _achievements;
        ImageView _icExpand;
        LinearLayout _achievementsTitle, _expandAchievements;

        public CplListHolder(@NonNull View itemView) {
            super(itemView);

            _cplCpmk = itemView.findViewById(R.id.txt_cpl_cpmk);
            _achievements = itemView.findViewById(R.id.txt_achievements);
            _icExpand = itemView.findViewById(R.id.icon_expand);
            _achievementsTitle = itemView.findViewById(R.id.achievements_content);
            _expandAchievements = itemView.findViewById(R.id.expandable_achievements_content);

            _achievementsTitle.setOnClickListener(v -> {
                KhsModel model = _khsModels.get(getAdapterPosition());
                model.set_expand(!model.is_expand());
                notifyItemChanged(getAdapterPosition());
            });
        }
    }
}
