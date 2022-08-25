package com.arcmedtek.mpuskaapp_mobile.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.service.MPuskaDataService;
import com.arcmedtek.mpuskaapp_mobile.service.NetworkChangeListener;
import com.google.android.material.textfield.TextInputEditText;

public class SignUp extends AppCompatActivity {

    ImageView _btnBack;
    TextInputEditText _niy, _password;
    Button _btnSave;

    MPuskaDataService _mpuskaDataService;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        _mpuskaDataService = new MPuskaDataService(SignUp.this);

        _btnBack = findViewById(R.id.btn_back_sign_up);
        _niy = findViewById(R.id.txt_inet_niy_sign_up);
        _password = findViewById(R.id.txt_inet_pass_sign_up);
        _btnSave = findViewById(R.id.btn_save_sign_up);

        _btnBack.setOnClickListener(v -> onBackPressed());

        _btnSave.setOnClickListener(v -> saveAccount());
    }

    private void saveAccount() {
        String username = String.valueOf(_niy.getText());
        String password = String.valueOf(_password.getText());

        _mpuskaDataService.signUp(username, password, new MPuskaDataService.SignUpListener() {
            @Override
            public void onResponse(String message) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this, R.style.AlertDialogStyle);
                View doneDialog = LayoutInflater.from(SignUp.this).inflate(R.layout.custom_done_dialog, findViewById(R.id.confirm_done_dialog));
                builder.setView(doneDialog);

                TextView txtMessage = doneDialog.findViewById(R.id.done_message);
                txtMessage.setText(message);

                final AlertDialog alertDialog = builder.create();

                doneDialog.findViewById(R.id.btn_confirm_done).setOnClickListener(v -> {
                    alertDialog.dismiss();

                    Intent intent = new Intent(SignUp.this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                });

                if (alertDialog.getWindow() != null) {
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }

                alertDialog.show();
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onError(String message) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this, R.style.AlertDialogStyle);
                View warningDialog = LayoutInflater.from(SignUp.this).inflate(R.layout.custom_warning_dialog, findViewById(R.id.confirm_warning_dialog));
                View notFoundDialog = LayoutInflater.from(SignUp.this).inflate(R.layout.custom_404_dialog, findViewById(R.id.confirm_404_dialog));
                View forbiddenDialog = LayoutInflater.from(SignUp.this).inflate(R.layout.custom_forbidden_dialog, findViewById(R.id.confirm_forbidden_dialog));

                TextView txtMessage;

                switch (message) {
                    case "400":
                        builder.setView(warningDialog);
                        txtMessage = warningDialog.findViewById(R.id.warning_message);
                        txtMessage.setText("Username tidak boleh kosong");
                        break;
                    case "403":
                        builder.setView(forbiddenDialog);
                        txtMessage = forbiddenDialog.findViewById(R.id.forbidden_message);
                        txtMessage.setText("Status anda tidak aktif di MBKM. Silahkan hubungi admin");
                        break;
                    case "404":
                        builder.setView(notFoundDialog);
                        txtMessage = notFoundDialog.findViewById(R.id.notfound_message);
                        txtMessage.setText("NIY/NIM tidak ditemukan");
                        break;
                    case "409":
                        builder.setView(warningDialog);
                        txtMessage = warningDialog.findViewById(R.id.warning_message);
                        txtMessage.setText("Username telah digunakan");
                        break;
                    default:
                        builder.setView(forbiddenDialog);
                        txtMessage = forbiddenDialog.findViewById(R.id.forbidden_message);
                        txtMessage.setText("Terjadi kesalahan sistem");
                        break;
                }

                final AlertDialog alertDialog = builder.create();

                warningDialog.findViewById(R.id.btn_confirm_warning).setOnClickListener(v -> alertDialog.dismiss());

                notFoundDialog.findViewById(R.id.btn_confirm_404).setOnClickListener(v -> alertDialog.dismiss());

                forbiddenDialog.findViewById(R.id.btn_confirm_forbidden).setOnClickListener(v -> alertDialog.dismiss());

                if (alertDialog.getWindow() != null) {
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }

                alertDialog.show();
            }
        });
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
}