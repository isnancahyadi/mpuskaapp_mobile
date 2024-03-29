package com.arcmedtek.mpuskaapp_mobile.service;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.arcmedtek.mpuskaapp_mobile.R;

public class NetworkChangeListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!NetworkChange.isConnectedToInternet(context)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View layoutDialog = LayoutInflater.from(context).inflate(R.layout.check_connection_dialog, null);
            builder.setView(layoutDialog);

            Button btnRetry = layoutDialog.findViewById(R.id.btn_retry_connection);

            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.setCancelable(false);

            dialog.getWindow().setGravity(Gravity.CENTER);

            btnRetry.setOnClickListener(v -> {
                dialog.dismiss();
                onReceive(context, intent);
            });
        }
    }
}
