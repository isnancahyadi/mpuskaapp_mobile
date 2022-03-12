package com.arcmedtek.mpuskaapp_mobile.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.activity.user.lecture.LectureDashboard;
import com.arcmedtek.mpuskaapp_mobile.service.MPuskaDataService;
import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {

    TextInputEditText _username, _password;
    TextView _btnSignUp;
    Button _btnLogin;
    ImageView _web, _telegram, _instagram, _twitter, _youtube;
    LinearLayout _containerSecondContent;
    ProgressBar _loading;

    public MPuskaDataService mPuskaDataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _username = findViewById(R.id.txt_inet_login_username);
        _password = findViewById(R.id.txt_inet_login_pass);
        _btnSignUp = findViewById(R.id.btn_sign_up);
        _web = findViewById(R.id.btn_web);
        _telegram = findViewById(R.id.btn_telegram);
        _instagram = findViewById(R.id.btn_instagram);
        _twitter = findViewById(R.id.btn_twitter);
        _youtube = findViewById(R.id.btn_youtube);
        _btnLogin = findViewById(R.id.btn_login);
        _containerSecondContent = findViewById(R.id.container_second_content);
        _loading = findViewById(R.id.loading_login);

        mPuskaDataService = new MPuskaDataService(Login.this);

        _btnLogin.setOnClickListener(v -> {
            String mUsername = String.valueOf(_username.getText());
            String mPassword = String.valueOf(_password.getText());

            if (!mUsername.isEmpty() && !mPassword.isEmpty()) {
                isLogin(mUsername, mPassword);
            } else {
                if (mUsername.isEmpty() && !mPassword.isEmpty()) {
                    _username.setError("Masukkan NIY/NIM");
                } else if (!mUsername.isEmpty()) {
                    _password.setError("Masukkan password");
                } else {
                    _username.setError("Masukkan NIY/NIM");
                    _password.setError("Masukkan password");
                }
            }
        });

        _btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, SignUp.class);
            startActivity(intent);
        });

        _socmed();
    }

    private void isLogin(String mUsername, String mPassword) {
        _loading.setVisibility(View.VISIBLE);
        _containerSecondContent.setVisibility(View.GONE);

        mPuskaDataService.logIn(mUsername, mPassword, new MPuskaDataService.LoginListener() {
            @Override
            public void onResponse(String privilege) {
                if (privilege.equals("1")) {
                    moveToLectureDashboard();
                }
                _loading.setVisibility(View.GONE);
                _containerSecondContent.setVisibility(View.VISIBLE);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onError(String message) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this, R.style.AlertDialogStyle);
                View warningDialog = LayoutInflater.from(Login.this).inflate(R.layout.custom_warning_dialog, findViewById(R.id.confirm_warning_dialog));
                View notFoundDialog = LayoutInflater.from(Login.this).inflate(R.layout.custom_404_dialog, findViewById(R.id.confirm_404_dialog));

                TextView txtMessage;

                switch (message) {
                    case "400":
                        builder.setView(warningDialog);
                        txtMessage = warningDialog.findViewById(R.id.warning_message);
                        txtMessage.setText("NIY/NIM atau password salah");
                        break;
                    case "404":
                        builder.setView(notFoundDialog);
                        txtMessage = notFoundDialog.findViewById(R.id.notfound_message);
                        txtMessage.setText("NIY/NIM tidak ditemukan");
                        break;
                }

                final AlertDialog alertDialog = builder.create();

                warningDialog.findViewById(R.id.btn_confirm_warning).setOnClickListener(v -> alertDialog.dismiss());

                notFoundDialog.findViewById(R.id.btn_confirm_404).setOnClickListener(v -> alertDialog.dismiss());

                if (alertDialog.getWindow() != null) {
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }

                alertDialog.show();

                _loading.setVisibility(View.GONE);
                _containerSecondContent.setVisibility(View.VISIBLE);
            }
        });
    }

    private void moveToLectureDashboard() {
        Intent intent = new Intent(Login.this, LectureDashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void _socmed() {
        _web.setOnClickListener(v -> {
            Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("https://tif.uad.ac.id"));
            startActivity(web);
        });

        _telegram.setOnClickListener(v -> {
            Intent telegram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/infotifuad"));
            startActivity(telegram);
        });

        _instagram.setOnClickListener(v -> {
            Intent instagram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/informatika.uad/"));
            startActivity(instagram);
        });

        _twitter.setOnClickListener(v -> {
            Intent twitter = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/ProdiTIFUAD"));
            startActivity(twitter);
        });

        _youtube.setOnClickListener(v -> {
            Intent youtube = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCoE-ydbWBsV0OU-OXpocQIA/"));
            startActivity(youtube);
        });
    }
}