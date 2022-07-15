package com.arcmedtek.mpuskaapp_mobile.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.arcmedtek.mpuskaapp_mobile.config.SessionManager;
import com.arcmedtek.mpuskaapp_mobile.config.SingletonReq;
import com.arcmedtek.mpuskaapp_mobile.model.CourseModel;
import com.arcmedtek.mpuskaapp_mobile.model.KrsModel;
import com.arcmedtek.mpuskaapp_mobile.model.LectureProfileModel;
import com.arcmedtek.mpuskaapp_mobile.model.KhsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MPuskaDataService {
    public static final String IPCONF = "192.168.43.26";
    public static final String BASE_URL = "http://" + IPCONF + "/mpuska-server-side/mpuska-server/public/restapi/";

    public static final String QUERY_FOR_LOGIN                      = BASE_URL + "auth/loginProcess";
    public static final String QUERY_FOR_LOGOUT                     = BASE_URL + "auth/logoutProcess";

    public static final String QUERY_FOR_CREATE_ACCOUNT             = BASE_URL + "akun";
    public static final String QUERY_FOR_CREATE_ASSESSMENT          = BASE_URL + "asesmen";

    public static final String QUERY_FOR_GET_STUDENT_LIST           = BASE_URL + "krs/";
    public static final String QUERY_FOR_GET_PROFILE_LECTURE        = BASE_URL + "dosen/";
    public static final String QUERY_FOR_GET_ALL_ASSESSMENTS        = BASE_URL + "asesmen";
    public static final String QUERY_FOR_GET_TEACHER_LIST_COURSE    = BASE_URL + "pengampu/";
    public static final String QUERY_FOR_GET_CPL                    = BASE_URL + "khs/getcpl/";
    public static final String QUERY_FOR_GET_CPMK                   = BASE_URL + "khs/getcpmk/";
    public static final String QUERY_FOR_GET_STUDENT_SCORE          = BASE_URL + "khs/getscoremhs/";
    public static final String QUERY_FOR_GET_ASSESSMENTS            = BASE_URL + "khs/getassessment/";
    public static final String QUERY_FOR_GET_KRS_STUDENT            = BASE_URL + "khs/getlistkhsmhs/";

    public static final String QUERY_FOR_UPDATE_PASS_LECTURE        = BASE_URL + "akun/";
    public static final String QUERY_FOR_UPDATE_PROFILE_LECTURE     = BASE_URL + "dosen/";
    public static final String QUERY_FOR_UPDATE_MHS                 = BASE_URL + "khs/updatescoremhs/";
    public static final String QUERY_FOR_UPDATE_ASSESSMENTS         = BASE_URL + "khs/updateassessments";

    public static final String QUERY_FOR_ADD_ASSESSMENT             = BASE_URL + "khs/addassessment/";

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
        void onResponse(ArrayList<CourseModel> courseModels);

        void onError(String message);
    }

    public void getTeacherListCourse(TeacherListCourseListener teacherListCourseListener) {
        ArrayList<CourseModel> models = new ArrayList<>();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, QUERY_FOR_GET_TEACHER_LIST_COURSE + _userKey.get(SessionManager.USERNAME), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        CourseModel courseModel = new CourseModel();
                        //JSONArray jsonArray = new JSONArray(response);
                        JSONObject data = response.getJSONObject(i);

                        courseModel.set_idTeacher(String.valueOf(data.getInt("ID_pengampu")));
                        courseModel.set_courseName(data.getString("nama"));
                        courseModel.set_courseCode(data.getString("kode_matkul"));
                        courseModel.set_classRoom(data.getString("kelas"));
                        courseModel.set_collegeYear(data.getString("thn_ajaran"));

                        models.add(courseModel);

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

    public interface StudentListListener {
        void onResponse(ArrayList<KrsModel> krsModels);

        void onError(String message);
    }

    public void getStudentList(String idTeacher, StudentListListener studentListListener) {
        ArrayList<KrsModel> models = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.GET, QUERY_FOR_GET_STUDENT_LIST + idTeacher, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        KrsModel krsModel = new KrsModel();
                        JSONObject data = jsonArray.getJSONObject(i);

                        krsModel.set_idKrs(data.getInt("ID_krs"));
                        krsModel.set_nim(data.getString("nim"));
                        krsModel.set_studentFirstName(data.getString("nama_depan_mahasiswa"));
                        krsModel.set_studentMiddleName(data.getString("nama_tengah_mahasiswa"));
                        krsModel.set_studentLastName(data.getString("nama_belakang_mahasiswa"));
                        krsModel.set_photo(data.getString("foto"));
                        krsModel.set_teamName(data.getString("nama_tim"));

                        models.add(krsModel);
                    }
                    studentListListener.onResponse(models);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                studentListListener.onError("Terjadi kesalahan sistem");
            }
        });
        SingletonReq.getInstance(context).addToRequestQueue(request);
    }

    public interface StudentScoreListener {
        void onResponse(ArrayList<KhsModel> khsModels);

        void onError(String message);
    }

    public void getStudentScore(String nim, String courseCode, String classRoom, String collegeYear, StudentScoreListener studentScoreListener) {
        ArrayList<KhsModel> models = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.POST, QUERY_FOR_GET_STUDENT_SCORE + nim, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        KhsModel khsModel = new KhsModel();
                        JSONObject data = jsonArray.getJSONObject(i);

                        khsModel.set_idAsesmen(data.getInt("ID_asesmen"));
                        khsModel.set_assessment(data.getString("nama"));
                        khsModel.set_percent(data.getInt("bobot"));
                        khsModel.set_score(data.getInt("nilai"));

                        models.add(khsModel);
                    }
                    studentScoreListener.onResponse(models);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.w("ssss", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                studentScoreListener.onError("Terjadi kesalahan sistem");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("kode_matkul", courseCode);
                params.put("kelas", classRoom);
                params.put("thn_ajaran", collegeYear);
                return params;
            }
        };
        SingletonReq.getInstance(context).addToRequestQueue(request);
    }

    public interface AssessmentsListener {
        void onResponse(ArrayList<CourseModel> courseModels);

        void onError(String message);
    }

    public void getAssessments(String idTeacher, AssessmentsListener assessmentsListener) {
        ArrayList<CourseModel> models = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.GET, QUERY_FOR_GET_ASSESSMENTS + idTeacher, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        CourseModel courseModel = new CourseModel();
                        JSONObject data = jsonArray.getJSONObject(i);

                        courseModel.set_idAssessments(data.getInt("ID_asesmen"));
                        courseModel.set_assessments(data.getString("nama"));
                        courseModel.set_assessmentsPercentage(data.getInt("bobot"));

                        models.add(courseModel);
                    }
                    assessmentsListener.onResponse(models);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                assessmentsListener.onError("Terjadi kesalahan sistem");
            }
        });
        SingletonReq.getInstance(context).addToRequestQueue(request);
    }

    public interface CplListener {
        void onResponse(ArrayList<KhsModel> khsModels);

        void onError(String message);
    }

    public void getCpl(String courseCode, CplListener cplListener) {
        ArrayList<KhsModel> models = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.GET, QUERY_FOR_GET_CPL + courseCode, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        KhsModel khsModel = new KhsModel();
                        JSONObject data = jsonArray.getJSONObject(i);

                        khsModel.set_cpl(data.getString("cpl"));

                        models.add(khsModel);
                    }
                    cplListener.onResponse(models);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                cplListener.onError("Terjadi kesalahan sistem");
            }
        });
        SingletonReq.getInstance(context).addToRequestQueue(request);
    }

    public interface CpmkListener {
        void onResponse(ArrayList<KhsModel> khsModels);

        void onError(String message);
    }

    public void getCpmk(String courseCode, CpmkListener cpmkListener) {
        ArrayList<KhsModel> models = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.GET, QUERY_FOR_GET_CPMK + courseCode, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        KhsModel khsModel = new KhsModel();
                        JSONObject data = jsonArray.getJSONObject(i);

                        khsModel.set_cpmk(data.getString("cpmk"));

                        models.add(khsModel);
                    }
                    cpmkListener.onResponse(models);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                cpmkListener.onError("Terjadi kesalahan sistem");
            }
        });
        SingletonReq.getInstance(context).addToRequestQueue(request);
    }

    public interface UpdateScoreMhs {
        void onResponse(String message);

        void onError(String message);
    }

    public void updateScoreMhs(String idKrs, String[] idAssessments, String[] score, UpdateScoreMhs updateScoreMhs) {
        StringRequest request = new StringRequest(Request.Method.PUT, QUERY_FOR_UPDATE_MHS + idKrs, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                updateScoreMhs.onResponse("Data berhasil diupdate");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                updateScoreMhs.onError("Error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                for (int i = 0; i < idAssessments.length; i++) {
                    params.put("ID_asesmen["+i+"]", idAssessments[i]);
                }
                for (int j = 0; j < score.length; j++) {
                    params.put("nilai["+j+"]", score[j]);
                }
                return params;
            }
        };
        SingletonReq.getInstance(context).addToRequestQueue(request);
    }

    public interface UpdateAssessments {
        void onResponse(String message);

        void onError(String message);
    }

    public void updateAssessments(String[] idAssessments, String[] assessments, String[] percent, UpdateAssessments updateAssessments) {
        StringRequest request = new StringRequest(Request.Method.PUT, QUERY_FOR_UPDATE_ASSESSMENTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                updateAssessments.onResponse("Data berhasil diupdate");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                updateAssessments.onError("Error");
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                for (int i = 0; i < idAssessments.length; i++) {
                    params.put("ID_asesmen["+i+"]", idAssessments[i]);
                }
                for (int j = 0; j < assessments.length; j++) {
                    params.put("nama["+j+"]", assessments[j]);
                }
                for (int k = 0; k < percent.length; k++) {
                    params.put("bobot["+k+"]", percent[k]);
                }
                return params;
            }
        };
        SingletonReq.getInstance(context).addToRequestQueue(request);
    }

    public interface GradeListener {
        void onResponse(ArrayList<KhsModel> khsModels);

        void onError(String message);
    }

    public void getGradeStudent(String idTeacher, String nim, GradeListener gradeListener) {
        ArrayList<KhsModel> models = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.POST, QUERY_FOR_GET_KRS_STUDENT + idTeacher, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        KhsModel khsModel = new KhsModel();
                        JSONObject data = jsonArray.getJSONObject(i);

                        khsModel.set_grade(data.getString("grade"));

                        models.add(khsModel);
                    }
                    gradeListener.onResponse(models);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                gradeListener.onError("Terjadi kesalahan sistem");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nim", nim);
                return params;
            }
        };
        SingletonReq.getInstance(context).addToRequestQueue(request);
    }

    public interface AllAssessmentsListener {
        void onResponse(ArrayList<CourseModel> courseModels);

        void onError(String message);
    }

    public void getAllAssessments(AllAssessmentsListener allAssessmentsListener) {
        ArrayList<CourseModel> models = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.GET, QUERY_FOR_GET_ALL_ASSESSMENTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        CourseModel courseModel = new CourseModel();
                        JSONObject data = jsonArray.getJSONObject(i);

                        courseModel.set_idAssessments(data.getInt("ID_asesmen"));
                        courseModel.set_assessments(data.getString("nama"));

                        models.add(courseModel);
                    }
                    allAssessmentsListener.onResponse(models);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                allAssessmentsListener.onError("Terjadi kesalahan sistem");
            }
        });
        SingletonReq.getInstance(context).addToRequestQueue(request);
    }

    public interface CreateAssessmentsListener {
        void onResponse(String message);

        void onError(String message);
    }

    public void createAssessments(String nameAssessment, CreateAssessmentsListener createAssessmentsListener) {
        StringRequest request = new StringRequest(Request.Method.POST, QUERY_FOR_CREATE_ASSESSMENT, response -> createAssessmentsListener.onResponse("Asesmen berhasil ditambahkan"), error -> createAssessmentsListener.onError(String.valueOf(error.networkResponse.statusCode))) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nama", nameAssessment);
                return params;
            }
        };
        SingletonReq.getInstance(context).addToRequestQueue(request);
    }

    public interface AddAssessmentsListener {
        void onResponse(String message);

        void onError(String message);
    }

    public void addAssessments(String idTeacher, String idAssessment, String percent, AddAssessmentsListener addAssessmentsListener) {
        StringRequest request = new StringRequest(Request.Method.POST, QUERY_FOR_ADD_ASSESSMENT + idTeacher, response -> addAssessmentsListener.onResponse("Asesmen berhasil ditambahkan"), error -> addAssessmentsListener.onError(String.valueOf(error.networkResponse.statusCode))) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("ID_asesmen", idAssessment);
                params.put("bobot", percent);
                return params;
            }
        };
        SingletonReq.getInstance(context).addToRequestQueue(request);
    }
}
