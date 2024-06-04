package com.stepjump.goodjob;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class AdminActivity extends AppCompatActivity {

    private Button btn_commit_button, btn_list_button, btnMchange;

    private EditText password_text;

    String rv_id_temp;
    String rv_name_temp;
    String user_admin_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btn_commit_button = findViewById(R.id.btn_commit_button);   // 정산하기 버튼
        btn_list_button = findViewById(R.id.btn_list_button);   // 착한일 목록 수정 버튼
        btnMchange = findViewById(R.id.btnMchange);   // 회원정보 수정 버튼

        password_text = findViewById(R.id.password_text);   // 정산하기 패스워드

        // =====================================================================================
        // 모든 페이지에서 사용할 수 있도록 LoginActivity.java 에서 저장한 로그인 정보(id) 불러오기
        SharedPreferences pref = getSharedPreferences("login_info",MODE_PRIVATE);
        rv_id_temp = pref.getString("user_id","");
        rv_name_temp = pref.getString("user_name","");
        user_admin_pass = pref.getString("user_admin_pass","");
        // ========================================================================================================




        btn_commit_button.setOnClickListener(new View.OnClickListener() {     // 정산하기

            @Override
            public void onClick(View v) {

                // =============================================================================
                // 관리자 암호입력 부분
                // =============================================================================

                // =============================================================================

//                if (password_text.getText().toString().equals(user_admin_pass))  {
//
//                    Response.Listener<String> responseListener = new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            try {
//
//
//                                        JSONObject jsonObject = new JSONObject(response);
//                                        boolean success = jsonObject.getBoolean("success");
//
//                                        if (success) {   // 정산에 성공한 경우
//
//                                            Toast.makeText(getApplicationContext(), " 정산에 성공했습니다.", Toast.LENGTH_LONG).show();
//
//                                            onBackPressed();
//                                        } else { // 정산에 실패한 경우
//
//                                            Toast.makeText(getApplicationContext(), "정산에 실패했습니다.", Toast.LENGTH_LONG).show();
//                                            return;
//                                        }
//
//
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    };
//
//                    CommitRequest commitRequest = new CommitRequest(rv_id_temp, responseListener);
//                    RequestQueue queue = Volley.newRequestQueue(AdminActivity.this);
//                    queue.add(commitRequest);
//
//                } else {
//                    password_text.setText("");
//                    Toast.makeText(getApplicationContext(), "password error~!!", Toast.LENGTH_SHORT).show();
//                }


                // 관리자 비밀번호와 입력한 비밀번호 같다면 관리자 페이지로 이동
                // 관리자 비밀번호 ===> user_admin_pass
                android.app.AlertDialog.Builder adminpage = new AlertDialog.Builder(AdminActivity.this);

                adminpage.setTitle("정산암호 입력");
                adminpage.setMessage("관리자 암호를 입력해주세요~!!");

                final EditText adminpage_password = new EditText(AdminActivity.this);
                adminpage.setView(adminpage_password);

                adminpage.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        if (adminpage_password.getText().toString().equals(user_admin_pass)) {

                                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {


                                                        JSONObject jsonObject = new JSONObject(response);
                                                        boolean success = jsonObject.getBoolean("success");

                                                        if (success) {   // 정산에 성공한 경우

                                                            Toast.makeText(getApplicationContext(), " 정산에 성공했습니다.", Toast.LENGTH_LONG).show();

                                                            onBackPressed();
                                                        } else { // 정산에 실패한 경우

                                                            Toast.makeText(getApplicationContext(), "정산에 실패했습니다.", Toast.LENGTH_LONG).show();
                                                            return;
                                                        }



                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };

                                    CommitRequest commitRequest = new CommitRequest(rv_id_temp, responseListener);
                                    RequestQueue queue = Volley.newRequestQueue(AdminActivity.this);
                                    queue.add(commitRequest);


                        }
                        else {
                            Toast.makeText(AdminActivity.this, "암호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                adminpage.setNegativeButton("no",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // 취소처리
                    }
                });

                adminpage.show();

            }
        });



        btn_list_button.setOnClickListener(new View.OnClickListener() {     // 착한일 목록 수정

            @Override
            public void onClick(View v) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Intent intent = new Intent(AdminActivity.this, ListModifyActivity.class);
                            intent.putExtra("snd_message", response); // ListModifyActivity 으로 DB에서 가져온 전체 데이타 listView 처리하는 페이지로 문자열 송신
                            startActivity(intent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                };

                ListRequest listRequest = new ListRequest(rv_id_temp, responseListener);
                RequestQueue queue = Volley.newRequestQueue(AdminActivity.this);
                queue.add(listRequest);

            }
        });

        btnMchange.setOnClickListener(new View.OnClickListener() {     // 회원정보 수정

            @Override
            public void onClick(View v) {
                        try {
                            Intent intent = new Intent(AdminActivity.this, RegisterUpdateActivity.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
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

