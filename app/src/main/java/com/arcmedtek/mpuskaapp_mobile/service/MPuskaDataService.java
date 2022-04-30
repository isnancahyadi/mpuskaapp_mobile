package com.arcmedtek.mpuskaapp_mobile.service;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.arcmedtek.mpuskaapp_mobile.config.SessionManager;
import com.arcmedtek.mpuskaapp_mobile.config.SingletonReq;
import com.arcmedtek.mpuskaapp_mobile.model.LectureProfileModel;
import com.arcmedtek.mpuskaapp_mobile.model.StudentModel;
import com.arcmedtek.mpuskaapp_mobile.model.TeacherModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MPuskaDataService {
    public static final String QUERY_FOR_CREATE_ACCOUNT = "http://192.168.100.61/mpuska-server-side/mpuska-server/public/restapi/akun";
    public static final String QUERY_FOR_LOGIN = "http://192.168.100.61/mpuska-server-side/mpuska-server/public/restapi/auth/loginProcess";
    public static final String QUERY_FOR_LOGOUT = "http://192.168.100.61/mpuska-server-side/mpuska-server/public/restapi/auth/logoutProcess";
    public static final String QUERY_FOR_GET_PROFILE_LECTURE = "http://192.168.100.61/mpuska-server-side/mpuska-server/public/restapi/dosen/";
    public static final String QUERY_FOR_UPDATE_PROFILE_LECTURE = "http://192.168.100.61/mpuska-server-side/mpuska-server/public/restapi/dosen/";
    public static final String QUERY_FOR_UPDATE_PASS_LECTURE = "http://192.168.100.61/mpuska-server-side/mpuska-server/public/restapi/akun/";
    public static final String QUERY_FOR_GET_TEACHER_LIST_COURSE = "http://192.168.100.61/mpuska-server-side/mpuska-server/public/restapi/pengampu/";
    public static final String QUERY_FOR_GET_STUDENT_LIST = "http://192.168.100.61/mpuska-server-side/mpuska-server/public/restapi/mahasiswa";

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

                        lectureProfileModel.set_niy(data.getString("niy_nip"));
                        lectureProfileModel.set_frontDegree(data.getString("gelar_depan"));
                        lectureProfileModel.set_firstName(data.getString("nama_depan"));
                        lectureProfileModel.set_middleName(data.getString("nama_tengah"));
                        lectureProfileModel.set_lastName(data.getString("nama_belakang"));
                        lectureProfileModel.set_backDegree(data.getString("gelar_belakang"));
                        lectureProfileModel.set_gender(data.getString("gender"));
                        lectureProfileModel.set_phoneNumber(data.getString("no_hp"));
                        lectureProfileModel.set_email(data.getString("email"));
                        lectureProfileModel.set_photo(data.getString("foto"));

                        models.add(lectureProfileModel);
                    }
                    profileLectureListener.onResponse(models);
                } catch (JSONException e) {
                    //e.printStackTrace();
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    public interface UpdateProfileLectureListener {
        void onResponse(String message);

        void onError(String message);
    }

    public void updateProfileLecture(String firstName, String middleName, String lastName, String phoneNum, String email, String gender, UpdateProfileLectureListener updateProfileLectureListener) {
        StringRequest request = new StringRequest(Request.Method.PUT, QUERY_FOR_UPDATE_PROFILE_LECTURE + _userKey.get(SessionManager.USERNAME), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                updateProfileLectureListener.onResponse("Data berhasil diupdate");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                updateProfileLectureListener.onResponse("Error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("niy_nip", _userKey.get(SessionManager.USERNAME));
                params.put("nama_depan", firstName);
                params.put("nama_tengah", middleName);
                params.put("nama_belakang", lastName);
                params.put("no_hp", phoneNum);
                params.put("email", email);
                params.put("gender", gender);
                return params;
            }
        };
        SingletonReq.getInstance(context).addToRequestQueue(request);
    }

    public interface UpdatePassLecture {
        void onResponse(String message);

        void onError(String message);
    }

    public void updatePassLecture(String pass, UpdatePassLecture updatePassLecture) {
        StringRequest request = new StringRequest(Request.Method.PUT, QUERY_FOR_UPDATE_PASS_LECTURE + _userKey.get(SessionManager.USERNAME), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                updatePassLecture.onResponse("Data berhasil diupdate");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                updatePassLecture.onError("Error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("confirm_password", pass);
                return params;
            }
        };
        SingletonReq.getInstance(context).addToRequestQueue(request);
    }

    public interface TeacherListCourseListener {
        void onResponse(ArrayList<TeacherModel> teacherModels);

        void onError(String message);
    }

    public void getTeacherListCourse(TeacherListCourseListener teacherListCourseListener) {
        ArrayList<TeacherModel> models = new ArrayList<>();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, QUERY_FOR_GET_TEACHER_LIST_COURSE + _userKey.get(SessionManager.USERNAME), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        //JSONArray jsonArray = new JSONArray(response);
                        JSONObject data = response.getJSONObject(i);

                        String courseName = data.getString("nama");
                        String courseCode = data.getString("kode_matkul");
                        String semester = data.getString("semester");
                        String major = data.getString("prodi");
                        String firstName = data.getString("nama_depan");
                        String middleName = data.getString("nama_tengah");
                        String lastName = data.getString("nama_belakang");
                        String classRoom = data.getString("kelas");
                        String collegeYear = data.getString("thn_ajaran");
                        int sks = data.getInt("sks");

                        models.add(new TeacherModel(courseName, courseCode, semester, major, firstName, middleName, lastName, classRoom, collegeYear, sks));

                        teacherListCourseListener.onResponse(models);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                teacherListCourseListener.onError("Terjadi kesalahan sistem");
            }
        });
        SingletonReq.getInstance(context).addToRequestQueue(request);
    }
}
