package com.stepjump.goodjob;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class ListModifyDeleteRequest extends StringRequest {

    // 서버 URL 설정 (PHP 파일 연동)
    final static private  String URL = "http://stepjump73.dothome.co.kr/GOOD-JOB/Register_List_Delete.php";
    private Map<String, String> map;

    public ListModifyDeleteRequest(String USER_ID, String ACTIVE_NO, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("USER_ID", USER_ID);        // 로그인 ID
        map.put("ACTIVE_NO", ACTIVE_NO);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
