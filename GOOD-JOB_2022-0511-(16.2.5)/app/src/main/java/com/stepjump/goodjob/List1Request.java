package com.stepjump.goodjob;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class List1Request extends StringRequest {

    // 서버 URL 설정 (PHP 파일 연동)
    final static private  String URL = "http://stepjump73.dothome.co.kr/GOOD-JOB/List1.php";
    private Map<String, String> map;

    public List1Request(String USER_ID, String F_DATE, String T_DATE, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("USER_ID", USER_ID);        // 로그인 ID
        map.put("F_DATE", F_DATE);      // 조회시작일시(년월일)
        map.put("T_DATE", T_DATE);      // 조회끝일시(년월일)
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
