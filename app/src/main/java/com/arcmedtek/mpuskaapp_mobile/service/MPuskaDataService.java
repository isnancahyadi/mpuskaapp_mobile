package com.arcmedtek.mpuskaapp_mobile.service;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.arcmedtek.mpuskaapp_mobile.config.SessionManager;
import com.arcmedtek.mpuskaapp_mobile.config.SingletonReq;
import com.arcmedtek.mpuskaapp_mobile.model.LectureProfileModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MPuskaDataService {
    public static final String QUERY_FOR_CREATE_ACCOUNT = "http://100.100.1.15/mpuska-server-side/mpuska-server/public/restapi/akun";
    public static final String QUERY_FOR_LOGIN = "http://100.100.1.15/mpuska-server-side/mpuska-server/public/restapi/auth/loginProcess";
    public static final String QUERY_FOR_LOGOUT = "http://100.100.1.15/mpuska-server-side/mpuska-server/public/restapi/auth/logoutProcess";
    public static final String QUERY_FOR_GET_PROFILE_LECTURE = "http://100.100.1.15/mpuska-server-side/mpuska-server/public/restapi/dosen/";

    Context context;
    SessionManager _sessionManager;
    String _privilege, _user;
    HashMap<String, String> _userKey;

    public MPuskaDataService(Context context) {
        this.context = context;

        _sessionManager = new SessionManager(context);
        _userKey = _sessionManager.getUserDetail();
    }

    public interface SignUpListener {
        void onResponse(String message);
        void onError(String message);
    }

    public void signUp(String username, String password, SignUpListener signUpListener) {
        StringRequest request = new StringRequest(Request.Method.POST, QUERY_FOR_CREATE_ACCOUNT, response -> signUpListener.onResponse("Akun berhasil dibuat"), error -> signUpListener.onError(String.valueOf(error.networkResponse.statusCode))) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        SingletonReq.getInstance(context).addToRequestQueue(request);
    }

    public interface LoginListener {
        void onResponse(String privilege);
        void onError(String message);
    }

    public void logIn(String username, String password, LoginListener loginListener) {
        StringRequest request = new StringRequest(Request.Method.POST, QUERY_FOR_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                _privilege = "";
                _user = "";
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    _user = jsonObject.getString("username");
                    _privilege = jsonObject.getString("hak_akses");
                    _sessionManager.createSession(_user, _privilege);
                } catch (JSONException e) {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
                loginListener.onResponse(_privilege);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loginListener.onError(String.valueOf(error.networkResponse.statusCode));
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        SingletonReq.getInstance(context).addToRequestQueue(request);
    }

    public interface LogoutListener {
        void onResponse(String message);
        void onError(String message);
    }

    public void logout(LogoutListener logoutListener) {
        StringRequest request = new StringRequest(Request.Method.GET, QUERY_FOR_LOGOUT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                logoutListener.onResponse("Berhasil logout");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                logoutListener.onError("Gagal logout");
            }
        });
        SingletonReq.getInstance(context).addToRequestQueue(request);
    }

    public interface ProfileLectureListener {
        void onResponse(List<LectureProfileModel> lectureProfileModels);
        void onError(String message);
    }

    public void getProfileLecture(ProfileLectureListener profileLectureListener) {
        List<LectureProfileModel> models = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.GET, QUERY_FOR_GET_PROFILE_LECTURE + _userKey.get(SessionManager.USERNAME), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    LectureProfileModel lectureProfileModel = new LectureProfileModel();
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = jsonArray.getJSONObject(i);

                        lectureProfileModel.set_niy(data.getString("niy"));
                        lectureProfileModel.set_firstName(data.getString("nama_depan"));
                        lectureProfileModel.set_middleName(data.getString("nama_tengah"));
                        lectureProfileModel.set_lastName(data.getString("nama_belakang"));
                        lectureProfileModel.set_gender(data.getString("gender"));
                        lectureProfileModel.set_birthPlace(data.getString("tempat_lahir"));
                        lectureProfileModel.set_birthDate(data.getString("tgl_lahir"));
                        lectureProfileModel.set_phoneNumber(data.getString("no_hp"));
                        lectureProfileModel.set_email(data.getString("email"));
                        lectureProfileModel.set_address(data.getString("alamat"));
                        lectureProfileModel.set_subDistrict(data.getString("kecamatan"));
                        lectureProfileModel.set_district(data.getString("kabupaten"));
                        lectureProfileModel.set_province(data.getString("provinsi"));
                        lectureProfileModel.set_postalCode(data.getString("kode_pos"));

                        models.add(lectureProfileModel);
                    }
                    profileLectureListener.onResponse(models);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                profileLectureListener.onError("Terjadi kesalahan sistem");
            }
        });
        SingletonReq.getInstance(context).addToRequestQueue(request);
    }
}
