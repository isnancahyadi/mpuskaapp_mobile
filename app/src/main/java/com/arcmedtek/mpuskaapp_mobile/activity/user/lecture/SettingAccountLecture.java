package com.arcmedtek.mpuskaapp_mobile.activity.user.lecture;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arcmedtek.mpuskaapp_mobile.R;
import com.arcmedtek.mpuskaapp_mobile.model.LectureProfileModel;
import com.arcmedtek.mpuskaapp_mobile.service.MPuskaDataService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class SettingAccountLecture extends AppCompatActivity {

    ImageView _btnBack;
    TextView _lectureName, _lectureEmail;
    TextInputEditText _changePass, _confirmChangePass;
    TextInputLayout _inlayConfirmChangePass;
    Button _btnChangePass;

    MPuskaDataService _mPuskaDataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_account_lecture);

        _btnBack = findViewById(R.id.btn_back_setting_account_lecture);
        _lectureName = findViewById(R.id.setting_account_lecture_name);
        _lectureEmail = findViewById(R.id.setting_account_lecture_email);
        _changePass = findViewById(R.id.txt_inet_change_pass_account);
        _confirmChangePass = findViewById(R.id.txt_inet_confirm_change_pass_account);
        _btnChangePass = findViewById(R.id.btn_change_password_lecture);
        _inlayConfirmChangePass = findViewById(R.id.txt_inlay_confirm_change_pass_account);

        _mPuskaDataService = new MPuskaDataService(SettingAccountLecture.this);

        _btnBack.setOnClickListener(v -> onBackPressed());

        _mPuskaDataService.getProfileLecture(new MPuskaDataService.ProfileLectureListener() {
            @Override
            public void onResponse(List<LectureProfileModel> lectureProfileModels) {
                String fullName;
                if (lectureProfileModels.get(0).get_middleName().equals("")) {
                    fullName = lectureProfileModels.get(0).get_firstName()+" "+lectureProfileModels.get(0).get_lastName();
                } else {
                    fullName = lectureProfileModels.get(0).get_firstName()+" "+lectureProfileModels.get(0).get_middleName()+" "+lectureProfileModels.get(0).get_lastName();
                }
                _lectureName.setText(fullName);
                _lectureEmail.setText(lectureProfileModels.get(0).get_email());
            }

            @Override
            public void onError(String message) {

            }
        });

        _changePass.addTextChangedListener(passTextWatcher);
        _confirmChangePass.addTextChangedListener(passTextWatcher);

        _btnChangePass.setOnClickListener(v -> savePassword());
    }

    private void savePassword() {
        String pass = String.valueOf(_changePass.getText());
        String confirmPass = String.valueOf(_confirmChangePass.getText());

        if (!confirmPass.equals(pass)) {
            _inlayConfirmChangePass.setError("Password berbeda!");
        } else {
            _mPuskaDataService.updatePassLecture(confirmPass, new MPuskaDataService.UpdatePassLecture() {
                @Override
                public void onResponse(String message) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingAccountLecture.this, R.style.AlertDialogStyle);
                    View doneDialog = LayoutInflater.from(SettingAccountLecture.this).inflate(R.layout.custom_done_dialog, findViewById(R.id.confirm_done_dialog));
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

                    Toast.makeText(SettingAccountLecture.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void refreshActivity() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    private final TextWatcher passTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String passInput = String.valueOf(_changePass.getText());
            String confirmPassInput = String.valueOf(_confirmChangePass.getText());

            _btnChangePass.setEnabled(!passInput.isEmpty() && !confirmPassInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}