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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MPuskaDataService {
    public static final String QUERY_FOR_CREATE_ACCOUNT = "http://100.100.1.10/mpuska-server-side/mpuska-server/public/restapi/akun";
    public static final String QUERY_FOR_LOGIN = "http://100.100.1.10/mpuska-server-side/mpuska-server/public/restapi/auth/loginProcess";

    Context context;
    SessionManager _sessionManager;
    String _status, _privilege, _user;
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
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
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
}
