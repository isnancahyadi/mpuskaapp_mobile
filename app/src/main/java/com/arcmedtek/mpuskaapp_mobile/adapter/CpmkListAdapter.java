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
import com.arcmedtek.mpuskaapp_mobile.service.MPuskaDataService;

import java.util.ArrayList;
import java.util.Arrays;

public class CpmkListAdapter extends RecyclerView.Adapter<CpmkListAdapter.CpmkListHolder> {

    ArrayList<KhsModel> _khsModels;
    Context _context;
    String _strIdTeacher;
    boolean _isAchievementsActived;

    MPuskaDataService _mPuskaDataService;

    public CpmkListAdapter(ArrayList<KhsModel> _khsModels, Context _context, String _strIdTeacher, boolean _isAchievementsActived) {
        this._khsModels = _khsModels;
        this._context = _context;
        this._strIdTeacher = _strIdTeacher;
        this._isAchievementsActived = _isAchievementsActived;
    }

    @NonNull
    @Override
    public CpmkListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context).inflate(R.layout.achievements_list_row_item, parent, false);

        return new CpmkListHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CpmkListHolder holder, int position) {
        KhsModel model = _khsModels.get(position);
        int num = position+1;
        holder._cplCpmk.setText("CPMK " + num);

        _mPuskaDataService = new MPuskaDataService(_context);

        boolean isExpand = _khsModels.get(position).is_expand();
        if (isExpand) {
            holder._expandAchievements.setVisibility(View.VISIBLE);
            holder._icExpand.setImageResource(R.drawable.ic_expand_less);
        } else {
            holder._expandAchievements.setVisibility(View.GONE);
            holder._icExpand.setImageResource(R.drawable.ic_expand_more);
        }

        if (_isAchievementsActived) {
            _mPuskaDataService.getAchievements(_strIdTeacher, String.valueOf(model.get_idCpmk()), new MPuskaDataService.GetAchievementsListener() {
                @Override
                public void onResponse(ArrayList<KhsModel> khsModels) {
                    String achievement[] = new String[khsModels.size()];
                    for (int i = 0; i < khsModels.size(); i++) {
                        achievement[i] = khsModels.get(i).get_assessment();
                    }
                    //Toast.makeText(_context, Arrays.toString(achievement), Toast.LENGTH_SHORT).show();
                    holder._achievements.setText(Arrays.toString(achievement));
                }

                @Override
                public void onError(String message) {

                }
            });
        } else {
            holder._achievements.setText(model.get_cpmk());
        }
    }

    @Override
    public int getItemCount() {
        return _khsModels.size();
    }

    public class CpmkListHolder extends RecyclerView.ViewHolder {

        TextView _cplCpmk, _achievements;
        ImageView _icExpand;
        LinearLayout _achievementsTitle, _expandAchievements;

        public CpmkListHolder(@NonNull View itemView) {
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
