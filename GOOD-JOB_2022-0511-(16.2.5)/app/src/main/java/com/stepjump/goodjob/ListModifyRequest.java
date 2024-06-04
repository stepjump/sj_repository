package com.stepjump.goodjob;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class ListModifyRequest extends StringRequest {

    // 서버 URL 설정 (PHP 파일 연동)
    final static private  String URL = "http://stepjump73.dothome.co.kr/GOOD-JOB/Register_List.php";
    private Map<String, String> map;

    public ListModifyRequest(String USER_ID, String ACTIVE_NO, String ACTIVE_MONEY, String ACTIVE_LIST, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("USER_ID", USER_ID);        // 로그인 ID
        map.put("ACTIVE_NO", ACTIVE_NO);
        map.put("ACTIVE_MONEY", ACTIVE_MONEY);
        map.put("ACTIVE_LIST", ACTIVE_LIST);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
