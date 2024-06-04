package com.stepjump.goodjob;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity1 extends AppCompatActivity {

    int tColor,n=0;

    private TextView tv_id_temp, tv_name_temp;

    private EditText et_title;

    private LinearLayout container;

    private Bitmap bitmap;

    private String temp_image_str;  // mysql 에 넘겨주는 이미지 str, MYSQL 에서 LONG TEXT 로 저장해야함.

    String rv_id_temp;
    String rv_name_temp;

    private Bitmap out_bitmap1;



    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        getSupportActionBar().setTitle("출석체크 하기");   // 화면 제목 바꾸기


        // ========================================================================================================
        // LoginActivity 화면에서 MainActivity 화면으로 값넘기기
        // ========================================================================================================
        tv_id_temp = (TextView) findViewById(R.id.tv_id);
        tv_name_temp = (TextView) findViewById(R.id.tv_name);

        Intent intent = getIntent(); /*데이터 수신*/

        rv_id_temp = intent.getExtras().getString("userID");    // OpenActivity 에서 넘어온값
        tv_id_temp.setText(rv_id_temp.replace("null", ""));

        rv_name_temp = intent.getExtras().getString("userName");    // OpenActivity 에서 넘어온값
        tv_name_temp.setText(rv_name_temp.replace("null", ""));
        // ========================================================================================================1

    };

}