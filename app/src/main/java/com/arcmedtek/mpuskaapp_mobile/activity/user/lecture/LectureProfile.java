package com.arcmedtek.mpuskaapp_mobile.activity.user.lecture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.activity.Login;
import com.arcmedtek.mpuskaapp_mobile.config.SessionManager;
import com.arcmedtek.mpuskaapp_mobile.model.LectureProfileModel;
import com.arcmedtek.mpuskaapp_mobile.service.MPuskaDataService;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LectureProfile extends AppCompatActivity {

    MPuskaDataService _mPuskaDataService;

    ShapeableImageView _profilePict;
    ImageView _gender, _btnBack, _btnSetting, _btnSaveUpdate, _btnCancelUpdate;
    TextView _name, _niy, _birth, _phoneNumber, _email, _fullAddress;
    String _firstName, _middleName, _lastName, _birthPlace, _birthDate, _finalBirthDate, _address, _subDistrict, _district, _province, _postalCode, _txtGender, jk;
    RelativeLayout _btnChangePhotoProfile, _btnChangeGender, _containerChangePhotoProfile, _containerChangeGender;

    GridLayout _containerName, _containerAddress;

    TextInputLayout _inlayBirthPlace, _inlayBirthDate, _inlayPhoneNumber, _inlayEmail;
    TextInputEditText _inetBirthPlace, _inetBirthDate, _inetFirstName, _inetMiddleName, _inetLastName, _inetPhoneNumber, _inetEmail, _inetAddress, _inetSubDistrict, _inetDistrict, _inetProvince, _inetPostalCode;

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
        _btnChangePhotoProfile = findViewById(R.id.btn_update_lecture_photo_profile);
        _btnChangeGender = findViewById(R.id.btn_change_gender);

        _containerName = findViewById(R.id.container_lecture_profile_name);
        _containerAddress = findViewById(R.id.container_lecture_profile_address);
        _containerChangePhotoProfile = findViewById(R.id.change_photo_profile);
        _containerChangeGender = findViewById(R.id.change_gender);

        _inlayBirthPlace = findViewById(R.id.txt_inlay_update_lecture_profile_birth_place);
        _inlayBirthDate = findViewById(R.id.txt_inlay_update_lecture_profile_birth_date);
        _inlayPhoneNumber = findViewById(R.id.txt_inlay_update_lecture_profile_phone_number);
        _inlayEmail = findViewById(R.id.txt_inlay_update_lecture_profile_email);

        _inetBirthPlace = findViewById(R.id.txt_inet_update_lecture_profile_birth_place);
        _inetBirthDate = findViewById(R.id.txt_inet_update_lecture_profile_birth_date);
        _inetFirstName = findViewById(R.id.txt_inet_update_lecture_profile_firstname);
        _inetMiddleName = findViewById(R.id.txt_inet_update_lecture_profile_middlename);
        _inetLastName = findViewById(R.id.txt_inet_update_lecture_profile_lastname);
        _inetPhoneNumber = findViewById(R.id.txt_inet_update_lecture_profile_phone_number);
        _inetEmail = findViewById(R.id.txt_inet_update_lecture_profile_email);
        _inetAddress = findViewById(R.id.txt_inet_update_lecture_profile_address);
        _inetSubDistrict = findViewById(R.id.txt_inet_update_lecture_profile_subdistrict);
        _inetDistrict = findViewById(R.id.txt_inet_update_lecture_profile_district);
        _inetProvince = findViewById(R.id.txt_inet_update_lecture_profile_province);
        _inetPostalCode = findViewById(R.id.txt_inet_update_lecture_profile_postalcode);

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
        _btnSetting.setVisibility(View.GONE);
        _birth.setVisibility(View.GONE);
        _name.setVisibility(View.GONE);
        _phoneNumber.setVisibility(View.GONE);
        _email.setVisibility(View.GONE);
        _fullAddress.setVisibility(View.GONE);

        _btnSaveUpdate.setVisibility(View.VISIBLE);
        _btnCancelUpdate.setVisibility(View.VISIBLE);
        _containerChangePhotoProfile.setVisibility(View.VISIBLE);
        _containerChangeGender.setVisibility(View.VISIBLE);

        _inlayBirthPlace.setVisibility(View.VISIBLE);
        _inlayBirthDate.setVisibility(View.VISIBLE);
        _containerName.setVisibility(View.VISIBLE);
        _inlayPhoneNumber.setVisibility(View.VISIBLE);
        _inlayEmail.setVisibility(View.VISIBLE);
        _containerAddress.setVisibility(View.VISIBLE);

        _inetBirthPlace.setText(_birthPlace);
        _inetBirthDate.setText(_finalBirthDate);
        _inetFirstName.setText(_firstName);
        _inetMiddleName.setText(_middleName);
        _inetLastName.setText(_lastName);
        _inetPhoneNumber.setText(_phoneNumber.getText());
        _inetEmail.setText(_email.getText());
        _inetAddress.setText(_address);
        _inetSubDistrict.setText(_subDistrict);
        _inetDistrict.setText(_district);
        _inetProvince.setText(_province);
        _inetPostalCode.setText(_postalCode);

        SimpleDateFormat sdf = new SimpleDateFormat("dd LLLL yyyy", new Locale("id", "ID"));
        Calendar nCal = Calendar.getInstance();

        _inetBirthDate.setOnFocusChangeListener((view, b) -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(LectureProfile.this, R.style.cusCalendarStyle, (view1, year, month, dayOfMonth) -> {
                Calendar nDate = Calendar.getInstance();
                nDate.set(year, month, dayOfMonth);
                _inetBirthDate.setText(sdf.format(nDate.getTime()));
            }, nCal.get(Calendar.YEAR), nCal.get(Calendar.MONTH), nCal.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        _btnChangeGender.setOnClickListener(v -> {
            final AlertDialog.Builder alert = new AlertDialog.Builder(LectureProfile.this, R.style.AlertDialogStyle);
            View mView = LayoutInflater.from(LectureProfile.this).inflate(R.layout.dialog_change_gender, findViewById(R.id.dialog_change_gender));

            RadioGroup rGroupGender = mView.findViewById(R.id.rgroup_gender);

            alert.setView(mView);

            final AlertDialog alertDialog = alert.create();

            rGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.rbtn_male:
                            jk = "1";
                            break;
                        case R.id.rbtn_female:
                            jk = "0";
                            break;
                    }
                }
            });

            mView.findViewById(R.id.btn_save_change_gender).setOnClickListener(v1 -> {
                _txtGender = jk;
                alertDialog.dismiss();
            });

            mView.findViewById(R.id.btn_cancel_change_gender).setOnClickListener(v1 -> alertDialog.dismiss());

            if (alertDialog.getWindow() != null) {
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }

            alertDialog.show();
        });

        _btnSaveUpdate.setOnClickListener(v -> saveUpdateProfile());
        _btnCancelUpdate.setOnClickListener(v -> refreshActivity());
    }

    private void refreshActivity() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    private void saveUpdateProfile() {
        final String firstName = String.valueOf(_inetFirstName.getText());
        final String middleName = String.valueOf(_inetMiddleName.getText());
        final String lastName = String.valueOf(_inetLastName.getText());
        final String birthPlace = String.valueOf(_inetBirthPlace.getText());
        final String birthDate = String.valueOf(_inetBirthDate.getText());
        final String phoneNum = String.valueOf(_inetPhoneNumber.getText());
        final String email = String.valueOf(_inetEmail.getText());
        final String address = String.valueOf(_inetAddress.getText());
        final String subDistrict = String.valueOf(_inetSubDistrict.getText());
        final String district = String.valueOf(_inetDistrict.getText());
        final String province = String.valueOf(_inetProvince.getText());
        final String postalCode = String.valueOf(_inetPostalCode.getText());
        final String gender = _txtGender;

        final String cvtBirthDateToDBFormat = convertBirthDateToDBFormat(birthDate);

        _mPuskaDataService.updateProfileLecture(firstName, middleName, lastName, birthPlace, cvtBirthDateToDBFormat, phoneNum, email, address, subDistrict, district, province, postalCode, gender, new MPuskaDataService.UpdateProfileLectureListener() {
            @Override
            public void onResponse(String message) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LectureProfile.this, R.style.AlertDialogStyle);
                View doneDialog = LayoutInflater.from(LectureProfile.this).inflate(R.layout.custom_done_dialog, findViewById(R.id.confirm_done_dialog));
                builder.setView(doneDialog);

                TextView txtMessage = doneDialog.findViewById(R.id.done_message);
                txtMessage.setText(message);

                final AlertDialog alertDialog = builder.create();

                doneDialog.findViewById(R.id.btn_confirm_done).setOnClickListener(v -> refreshActivity());

                if (alertDialog.getWindow() != null) {
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }

                alertDialog.show();
            }

            @Override
            public void onError(String message) {
                refreshActivity();

                Toast.makeText(LectureProfile.this, message, Toast.LENGTH_SHORT).show();
            }
        });
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
                _txtGender = lectureProfileModels.get(0).get_gender();

                String fullname;
                if (_middleName.equals("")) {
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
                Glide.with(LectureProfile.this).load(lectureProfileModels.get(0).get_photo()).into(_profilePict);

                if (_txtGender.equals("0")) {
                    _gender.setImageResource(R.drawable.ic_gender_female);
                } else if (_txtGender.equals("1")) {
                    _gender.setImageResource(R.drawable.ic_gender_male);
                }
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