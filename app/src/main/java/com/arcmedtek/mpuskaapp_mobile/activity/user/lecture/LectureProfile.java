package com.arcmedtek.mpuskaapp_mobile.activity.user.lecture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.activity.Login;
import com.arcmedtek.mpuskaapp_mobile.config.SessionManager;
import com.arcmedtek.mpuskaapp_mobile.model.LectureProfileModel;
import com.arcmedtek.mpuskaapp_mobile.service.MPuskaDataService;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LectureProfile extends AppCompatActivity {

    MPuskaDataService _mPuskaDataService;

    ShapeableImageView _profilePict;
    ImageView _gender, _btnBack, _btnSetting, _btnSaveUpdate, _btnCancelUpdate;
    TextView _name, _niy, _birth, _phoneNumber, _email, _fullAddress;
    String _firstName, _middleName, _lastName, _birthPlace, _birthDate, _finalBirthDate, _address, _subDistrict, _district, _province, _postalCode;

    TextInputLayout _inlayBirthPlace, _inlayBirthDate, _inlayName, _inlayPhoneNumber, _inlayEmail, _inlayAddress;
    TextInputEditText _inetBirthPlace, _inetBirthDate, _inetName, _inetPhoneNumber, _inetEmail, _inetAddress;

    MenuBuilder _menuBuilder;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_profile);

        _profilePict = findViewById(R.id.lecture_profile_picture);
        _gender = findViewById(R.id.lecture_profile_gender);
        _name = findViewById(R.id.lecture_profile_name);
        _niy = findViewById(R.id.lecture_profile_niy);
        _birth = findViewById(R.id.lecture_profile_birth);
        _phoneNumber = findViewById(R.id.lecture_profile_phone_number);
        _email = findViewById(R.id.lecture_profile_email);
        _fullAddress = findViewById(R.id.lecture_profile_address);
        _btnBack = findViewById(R.id.btn_back_lecture_profile);
        _btnSetting = findViewById(R.id.btn_setting_lecture_profile);
        _btnSaveUpdate = findViewById(R.id.btn_save_update_lecture_profile);
        _btnCancelUpdate = findViewById(R.id.btn_cancel_update_lecture_profile);

        _inlayBirthPlace = findViewById(R.id.txt_inlay_update_lecture_profile_birth_place);
        _inlayBirthDate = findViewById(R.id.txt_inlay_update_lecture_profile_birth_date);
        _inlayName = findViewById(R.id.txt_inlay_update_lecture_profile_name);
        _inlayPhoneNumber = findViewById(R.id.txt_inlay_update_lecture_profile_phone_number);
        _inlayEmail = findViewById(R.id.txt_inlay_update_lecture_profile_email);
        _inlayAddress = findViewById(R.id.txt_inlay_update_lecture_profile_address);

        _inetBirthPlace = findViewById(R.id.txt_inet_update_lecture_profile_birth_place);
        _inetBirthDate = findViewById(R.id.txt_inet_update_lecture_profile_birth_date);
        _inetName = findViewById(R.id.txt_inet_update_lecture_profile_name);
        _inetPhoneNumber = findViewById(R.id.txt_inet_update_lecture_profile_phone_number);
        _inetEmail = findViewById(R.id.txt_inet_update_lecture_profile_email);
        _inetAddress = findViewById(R.id.txt_inet_update_lecture_profile_address);

        _mPuskaDataService = new MPuskaDataService(LectureProfile.this);
        _menuBuilder = new MenuBuilder(this);

        getProfileLecture();

        _btnBack.setOnClickListener(v -> onBackPressed());
        
        profileSetting();
    }

    @SuppressLint("RestrictedApi")
    private void profileSetting() {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.popup_menu_setting_lecture_profile, _menuBuilder);

        _btnSetting.setOnClickListener(v -> {
            MenuPopupHelper optionMenu = new MenuPopupHelper(LectureProfile.this, _menuBuilder, v);
            optionMenu.setForceShowIcon(true);

            _menuBuilder.setCallback(new MenuBuilder.Callback() {
                @Override
                public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                    if (item.getItemId() == R.id.profile_setting) {
                        editProfile();
                        return true;
                    } else if (item.getItemId() == R.id.logout_profile) {
                        _mPuskaDataService.logout(new MPuskaDataService.LogoutListener() {
                            @Override
                            public void onResponse(String message) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LectureProfile.this, R.style.AlertDialogStyle);
                                View dialog = LayoutInflater.from(LectureProfile.this).inflate(R.layout.confirm_logout_dialog, findViewById(R.id.confirm_logout_dialog));
                                builder.setView(dialog);

                                final AlertDialog alertDialog = builder.create();

                                dialog.findViewById(R.id.logout_yes).setOnClickListener(v -> {
                                    alertDialog.dismiss();
                                    SessionManager sessionManager = new SessionManager(LectureProfile.this);
                                    sessionManager.removeSession();

                                    Intent intent = new Intent(LectureProfile.this, Login.class);
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
                                Toast.makeText(LectureProfile.this, message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    return false;
                }

                @Override
                public void onMenuModeChange(@NonNull MenuBuilder menu) {

                }
            });
            optionMenu.show();
        });
    }

    private void editProfile() {
    }

    private void getProfileLecture() {
        _mPuskaDataService.getProfileLecture(new MPuskaDataService.ProfileLectureListener() {
            @Override
            public void onResponse(List<LectureProfileModel> lectureProfileModels) {
                _firstName = lectureProfileModels.get(0).get_firstName();
                _middleName = lectureProfileModels.get(0).get_middleName();
                _lastName = lectureProfileModels.get(0).get_lastName();
                _birthPlace = lectureProfileModels.get(0).get_birthPlace();
                _birthDate = lectureProfileModels.get(0).get_birthDate();
                _address = lectureProfileModels.get(0).get_address();
                _subDistrict = lectureProfileModels.get(0).get_subDistrict();
                _district = lectureProfileModels.get(0).get_district();
                _province = lectureProfileModels.get(0).get_province();
                _postalCode = lectureProfileModels.get(0).get_postalCode();

                String fullname;
                if (_middleName == null) {
                    fullname = _firstName + " " + _lastName;
                } else {
                    fullname = _firstName + " " + _middleName + " " + _lastName;
                }

                _finalBirthDate = convertBirthDateToIdFormat(_birthDate);
                String birthPlaceDate = _birthPlace + ", " + _finalBirthDate;

                String fullAddress = _address + ", " + _subDistrict + ", " + _district + ", " + _province + ", " + _postalCode;

                _name.setText(fullname);
                _niy.setText(lectureProfileModels.get(0).get_niy());
                _birth.setText(birthPlaceDate);
                _phoneNumber.setText(lectureProfileModels.get(0).get_phoneNumber());
                _email.setText(lectureProfileModels.get(0).get_email());
                _fullAddress.setText(fullAddress);
            }
            @Override
            public void onError(String message) {

            }
        });
    }

    private String convertBirthDateToDBFormat(String birthDate) {
        String finalDate = "";

        @SuppressLint("SimpleDateFormat") SimpleDateFormat inputDate = new SimpleDateFormat("dd LLLL yyyy");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputDate = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = inputDate.parse(birthDate);
            if (date != null) {
                finalDate = outputDate.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return finalDate;
    }

    private String convertBirthDateToIdFormat(String birthDate) {
        String finalDate = "";

        @SuppressLint("SimpleDateFormat") SimpleDateFormat inputDate = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputDate = new SimpleDateFormat("dd LLLL yyyy", new Locale("id", "ID"));

        try {
            Date date = inputDate.parse(birthDate);
            if (date != null) {
                finalDate = outputDate.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return finalDate;
    }
}