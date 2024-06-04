package com.stepjump.goodjob;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MainRequest extends StringRequest {

    // 서버 URL 설정 (PHP 파일 연동)
    final static private  String URL = "http://stepjump73.dothome.co.kr/GOOD-JOB/Register.php";
    private Map<String, String> map;

    public MainRequest(String USER_ID, String EXE_DATE, String TODAY_MONEY, String ACTIVE_LIST, String CVT_DATE, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("USER_ID", USER_ID);        // 로그인 ID
        map.put("EXE_DATE", EXE_DATE);
        map.put("TODAY_MONEY", TODAY_MONEY);
        map.put("ACTIVE_LIST", ACTIVE_LIST);    // 착한일 목록
        map.put("CVT_DATE", CVT_DATE);  // 20200422 형식으로 만들기 - Sorting 조회을 위해
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
