package com.stepjump.goodjob;


import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class OpenRequest2 extends StringRequest {

    // 서버 URL 설정 (PHP 파일 연동)
    final static private  String URL = "http://stepjump73.dothome.co.kr/GOOD-JOB/Open2.php";
    private Map<String, String> map;

    public OpenRequest2(String TODAY_STR, String USER_ID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("TODAY_STR", TODAY_STR);
        map.put("USER_ID", USER_ID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
