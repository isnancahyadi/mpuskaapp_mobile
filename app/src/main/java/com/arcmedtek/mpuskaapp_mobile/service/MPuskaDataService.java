package com.arcmedtek.mpuskaapp_mobile.service;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.arcmedtek.mpuskaapp_mobile.config.SessionManager;
import com.arcmedtek.mpuskaapp_mobile.config.SingletonReq;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MPuskaDataService {
    public static final String QUERY_FOR_CREATE_ACCOUNT = "http://100.100.1.2/mpuska-server-side/mpuska-server/public/restapi/akun";

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
        final String[] getStatus = new String[1];
        StringRequest request = new StringRequest(Request.Method.POST, QUERY_FOR_CREATE_ACCOUNT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Akun berhasil dibuat", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, String.valueOf(error.networkResponse.statusCode), Toast.LENGTH_SHORT).show();
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

//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, QUERY_FOR_CREATE_ACCOUNT, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        SingletonReq.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
