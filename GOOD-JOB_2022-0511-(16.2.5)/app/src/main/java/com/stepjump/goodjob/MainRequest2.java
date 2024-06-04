package com.stepjump.goodjob;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MainRequest2 extends StringRequest {

    // 서버 URL 설정 (PHP 파일 연동)
    final static private  String URL = "http://stepjump73.dothome.co.kr/GOOD-JOB/Register2.php";
    private Map<String, String> map;

    public MainRequest2(String USER_ID, String EXE_DATE, String EDIT_TEMP1, String EDIT_TEMP2, String EDIT_TEMP3, String EDIT_HEIGHT, String EDIT_WEIGHT, String EDIT_COMMENT, String CVT_DATE, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("USER_ID", USER_ID);        // 로그인 ID
        map.put("EXE_DATE", EXE_DATE);      // 열온드 체크날짜
        map.put("EDIT_TEMP1", EDIT_TEMP1);  // 등교전(아침) 온도(℃)
        map.put("EDIT_TEMP2", EDIT_TEMP2);  // 등교후(저녁) 온도(℃)
        map.put("EDIT_TEMP3", EDIT_TEMP3);  // 자기전(밤) 온도(℃)
        map.put("HEIGHT", EDIT_HEIGHT);  // 키, 신장(cm)
        map.put("WEIGHT", EDIT_WEIGHT);  // 몸무게(kg)
        map.put("EDIT_COMMENT", EDIT_COMMENT);  // 기타 내용
        map.put("CVT_DATE", CVT_DATE);  // 20200422 형식으로 만들기 - Sorting 조회을 위해
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
