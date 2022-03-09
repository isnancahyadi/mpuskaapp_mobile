package com.arcmedtek.mpuskaapp_mobile.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.service.MPuskaDataService;
import com.google.android.material.textfield.TextInputEditText;

public class SignUp extends AppCompatActivity {

    ImageView _btnBack;
    TextInputEditText _niy, _password;
    Button _btnSave;

    MPuskaDataService _mpuskaDataService;

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
                Toast.makeText(SignUp.this, message, Toast.LENGTH_SHORT).show();
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onError(String message) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this, R.style.AlertDialogStyle);
                View warningDialog = LayoutInflater.from(SignUp.this).inflate(R.layout.custom_warning_dialog, findViewById(R.id.confirm_warning_dialog));
                View notFoundDialog = LayoutInflater.from(SignUp.this).inflate(R.layout.custom_404_dialog, findViewById(R.id.confirm_404_dialog));

                TextView txtMessage;

                switch (message) {
                    case "400":
                        builder.setView(warningDialog);
                        txtMessage = warningDialog.findViewById(R.id.warning_message);
                        txtMessage.setText("Username tidak boleh kosong");
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
                }

                final AlertDialog alertDialog = builder.create();

                warningDialog.findViewById(R.id.btn_confirm_warning).setOnClickListener(v -> alertDialog.dismiss());

                notFoundDialog.findViewById(R.id.btn_confirm_404).setOnClickListener(v -> alertDialog.dismiss());

                if (alertDialog.getWindow() != null) {
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }

                alertDialog.show();
            }
        });
    }
}