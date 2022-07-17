package com.arcmedtek.mpuskaapp_mobile.config;

import android.widget.Spinner;

import com.arcmedtek.mpuskaapp_mobile.model.CourseModel;

import java.util.ArrayList;

public interface OnSpinnerChanged {
    void onItemChanged(int position, String selectedItem);
    void beforeItemChanged(int position, String selectedItem);
}
