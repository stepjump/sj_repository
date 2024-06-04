package com.stepjump.goodjob;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    // 서버 URL 설정 (PHP 파일 연동)
    final static private  String URL = "http://stepjump73.dothome.co.kr/GOOD-JOB/Register_user.php";
    private Map<String, String> map;

    public RegisterRequest(String userID, String userPassword, String userAdminPass, String userName, String userAge, String user_conmment, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("userPassword", userPassword);
        map.put("userAdminPass", userAdminPass);
        map.put("userName", userName);
        map.put("userAge", userAge);
        map.put("user_conmment", user_conmment);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
