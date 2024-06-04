package com.stepjump.goodjob;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;


public class Main_PassActivity extends AppCompatActivity {

    private EditText password_text;
    private Button btn_commit_button, btnMchange;

    String rv_id_temp;
    String rv_name_temp;
    String user_admin_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pass);

        password_text = findViewById(R.id.password_text);   // 정산하기 패스워드
        btn_commit_button = findViewById(R.id.btn_commit_button);   // 관리자 페이지로 가기 버튼





        // =====================================================================================
        // 모든 페이지에서 사용할 수 있도록 LoginActivity.java 에서 저장한 로그인 정보(id) 불러오기
        SharedPreferences pref = getSharedPreferences("login_info",MODE_PRIVATE);
        rv_id_temp = pref.getString("user_id","");
        rv_name_temp = pref.getString("user_name","");
        user_admin_pass = pref.getString("user_admin_pass","");
        // =====================================================================================
//        tv_id_temp.setText(rv_id_temp.replace("null", "") );
//        tv_name_temp.setText(rv_name_temp.replace("null", "") );
        // ========================================================================================================


        btn_commit_button.setOnClickListener(new View.OnClickListener() {     // 관리자 페이지로 가기

            @Override
            public void onClick(View v) {

                if (password_text.getText().toString().equals(user_admin_pass)) {

                    try {
                        Intent intent = new Intent(Main_PassActivity.this,  AdminActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {

                    password_text.setText("");

                    Toast.makeText(getApplicationContext(), "password error~!!.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 돌아가기 버튼 클릭
        Button btn_back = (Button)findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

    }

}

