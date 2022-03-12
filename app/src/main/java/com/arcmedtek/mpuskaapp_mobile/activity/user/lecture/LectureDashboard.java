package com.arcmedtek.mpuskaapp_mobile.activity.user.lecture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.activity.Login;
import com.arcmedtek.mpuskaapp_mobile.config.SessionManager;
import com.arcmedtek.mpuskaapp_mobile.service.MPuskaDataService;
import com.google.android.material.navigation.NavigationView;

public class LectureDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static final float END_SCALE = 0.7f;

    MPuskaDataService mPuskaDataService;
    TextClock dateTime;

    TextView _lectureNameSideMenu, _lectureNiySideMenu;
    DrawerLayout _drawerLayout;
    NavigationView _navigationView;
    ImageView _btnSideMenu, _btnSideMenuBack1, _btnSideMenuBack2;
    ConstraintLayout _mainContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_dashboard);

        mPuskaDataService = new MPuskaDataService(LectureDashboard.this);
        dateTime = findViewById(R.id.date_time_lecture_dashboard);

        _drawerLayout = findViewById(R.id.drawer_layout_side_menu_lecture);
        _navigationView = findViewById(R.id.nav_view_side_menu_lecture);
        _btnSideMenu = findViewById(R.id.btn_side_menu_lecture_dashboard);
        _mainContent = findViewById(R.id.main_content_lecture_dashboard);

        _lectureNameSideMenu = _navigationView.getHeaderView(0).findViewById(R.id.name_side_menu_lecture);
        _lectureNiySideMenu = _navigationView.getHeaderView(0).findViewById(R.id.niy_side_menu_lecture);
        _btnSideMenuBack1 = _navigationView.getHeaderView(0).findViewById(R.id.btn_side_menu_back_lecture_1);
        _btnSideMenuBack2 = _navigationView.getHeaderView(0).findViewById(R.id.btn_side_menu_back_lecture_2);

        Typeface tf = ResourcesCompat.getFont(LectureDashboard.this, R.font.roboto_black);
        dateTime.setTypeface(tf);

        navDrawerSideMenu();
    }

    private void navDrawerSideMenu() {
        _navigationView.bringToFront();
        _navigationView.setNavigationItemSelectedListener(this);

        _btnSideMenu.setOnClickListener(v -> {
            if (_drawerLayout.isDrawerVisible(GravityCompat.END)) {
                _drawerLayout.closeDrawer(GravityCompat.END);
            } else {
                _drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        _btnSideMenuBack1.setOnClickListener(v -> _drawerLayout.closeDrawer(GravityCompat.END));
        _btnSideMenuBack2.setOnClickListener(v -> _drawerLayout.closeDrawer(GravityCompat.END));

        animateNavDrawerSideMenu();
    }

    private void animateNavDrawerSideMenu() {
        _drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // Scale the view based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                _mainContent.setScaleX(offsetScale);
                _mainContent.setScaleY(offsetScale);

                // translate the view, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = _mainContent.getWidth() * diffScaledOffset / 2;
                final float xTranslation = -(xOffset - xOffsetDiff);
                _mainContent.setTranslationX(xTranslation);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (_drawerLayout.isDrawerVisible(GravityCompat.END)) {
            _drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_my_profile_lecture) {
            Intent intent = new Intent(LectureDashboard.this, LectureProfile.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_account_setting_lecture) {
            Intent intent = new Intent(LectureDashboard.this, SettingAccountLecture.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_logout_lecture) {
            mPuskaDataService.logout(new MPuskaDataService.LogoutListener() {
                @Override
                public void onResponse(String message) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LectureDashboard.this, R.style.AlertDialogStyle);
                    View dialog = LayoutInflater.from(LectureDashboard.this).inflate(R.layout.confirm_logout_dialog, findViewById(R.id.confirm_logout_dialog));
                    builder.setView(dialog);

                    final AlertDialog alertDialog = builder.create();

                    dialog.findViewById(R.id.logout_yes).setOnClickListener(v -> {
                        alertDialog.dismiss();
                        SessionManager sessionManager = new SessionManager(LectureDashboard.this);
                        sessionManager.removeSession();

                        Intent intent = new Intent(LectureDashboard.this, Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    });

                    dialog.findViewById(R.id.logout_no).setOnClickListener(v -> alertDialog.dismiss());

                    if (alertDialog.getWindow() != null) {
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }

                    alertDialog.show();
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(LectureDashboard.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
        return true;
    }
}