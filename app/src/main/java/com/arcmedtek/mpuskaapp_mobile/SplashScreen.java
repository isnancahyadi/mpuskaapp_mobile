package com.arcmedtek.mpuskaapp_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.arcmedtek.mpuskaapp_mobile.activity.Login;
import com.arcmedtek.mpuskaapp_mobile.activity.user.lecture.LectureDashboard;
import com.arcmedtek.mpuskaapp_mobile.config.SessionManager;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        int TIME_SPLASH = 3000;
        new Handler().postDelayed(this::checkSession, TIME_SPLASH);
    }

    private void checkSession() {
        SessionManager sessionManager = new SessionManager(SplashScreen.this);
        String privilege = sessionManager.getSession();

        if (privilege == null) {
            Intent intent = new Intent(SplashScreen.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            if (privilege.equals("1")) {
                Intent intent = new Intent(SplashScreen.this, LectureDashboard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }
}