package com.arcmedtek.mpuskaapp_mobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.service.MPuskaDataService;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

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

            @Override
            public void onError(String message) {
                Toast.makeText(SignUp.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}