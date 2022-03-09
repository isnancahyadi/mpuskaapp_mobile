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

import java.util.HashMap;
import java.util.Map;

public class MPuskaDataService {
    public static final String QUERY_FOR_CREATE_ACCOUNT = "http://100.100.1.10/mpuska-server-side/mpuska-server/public/restapi/akun";

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
        StringRequest request = new StringRequest(Request.Method.POST, QUERY_FOR_CREATE_ACCOUNT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                signUpListener.onResponse("Berhasil Membuat Akun");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                signUpListener.onError(error.getMessage());
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
