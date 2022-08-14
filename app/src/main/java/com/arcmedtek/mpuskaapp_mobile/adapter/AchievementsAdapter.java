package com.arcmedtek.mpuskaapp_mobile.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.model.KhsModel;
import com.arcmedtek.mpuskaapp_mobile.service.MPuskaDataService;

import java.util.ArrayList;
import java.util.Arrays;

public class AchievementsAdapter extends RecyclerView.Adapter<AchievementsAdapter.AchievementsHolder> {

    ArrayList<KhsModel> _khsModels;
    Context _context;

    String _strIdTeacher, _strIdKrs;
    MPuskaDataService _mPuskaDataService;

    public AchievementsAdapter(ArrayList<KhsModel> _khsModels, Context _context, String _strIdTeacher, String _strIdKrs) {
        this._khsModels = _khsModels;
        this._context = _context;
        this._strIdTeacher = _strIdTeacher;
        this._strIdKrs = _strIdKrs;
    }

    @NonNull
    @Override
    public AchievementsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context).inflate(R.layout.score_detail_achievements_list_row_item, parent, false);

        return new AchievementsHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AchievementsHolder holder, int position) {
        KhsModel model = _khsModels.get(position);
        int num = position+1;
        holder._cpmk.setText("CPMK " + num);

        _mPuskaDataService = new MPuskaDataService(_context);

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

        _mPuskaDataService.getAchievementsScore(_strIdKrs, String.valueOf(model.get_idCpmk()), new MPuskaDataService.GetAchievementsScoreListener() {
            @Override
            public void onResponse(ArrayList<KhsModel> khsModels) {
                float totalPercent = 0, totalScore = 0, quantitativeScore;
                for (int i = 0; i < khsModels.size(); i++) {
                    totalPercent = totalPercent + khsModels.get(i).get_percent();
                    totalScore = totalScore + (khsModels.get(i).get_score() * khsModels.get(i).get_percent());
                    //Toast.makeText(_context, "asesmen : " + khsModels.get(i).get_assessment() + "\n" + "bobot : " + khsModels.get(i).get_percent() + "\n" + "nilai : " + khsModels.get(i).get_score(), Toast.LENGTH_SHORT).show();
                }

                quantitativeScore = totalScore/totalPercent;

//                Toast.makeText(_context, "Total Score : " + totalScore, Toast.LENGTH_SHORT).show();
//                Toast.makeText(_context, "Total Bobot : " + totalPercent, Toast.LENGTH_SHORT).show();
//                Toast.makeText(_context, "Nilai Kualitatif : " + quantitativeScore, Toast.LENGTH_SHORT).show();

                if (quantitativeScore >= 0 && quantitativeScore < 40.00) {
                    holder._achievementStatus.setImageResource(R.drawable.ic_failed);
                } else if (quantitativeScore >= 40 && quantitativeScore < 51.25) {
                    holder._achievementStatus.setImageResource(R.drawable.ic_failed);
                } else if (quantitativeScore >= 51.25 && quantitativeScore < 65.00) {
                    holder._achievementStatus.setImageResource(R.drawable.ic_medal);
                } else if (quantitativeScore >= 65.00 && quantitativeScore < 76.25) {
                    holder._achievementStatus.setImageResource(R.drawable.ic_medal);
                }
            }

            @Override
            public void onError(String message) {

            }
        });

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

    public class AchievementsHolder extends RecyclerView.ViewHolder {

        TextView _cpmk, _achievements;
        ImageView _icExpand, _achievementStatus;
        LinearLayout _achievementsTitle, _expandAchievements;

        public AchievementsHolder(@NonNull View itemView) {
            super(itemView);

            _cpmk = itemView.findViewById(R.id.txt_cpmk);
            _achievements = itemView.findViewById(R.id.txt_achievements);
            _icExpand = itemView.findViewById(R.id.icon_expand);
            _achievementStatus = itemView.findViewById(R.id.achievements_status);
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
