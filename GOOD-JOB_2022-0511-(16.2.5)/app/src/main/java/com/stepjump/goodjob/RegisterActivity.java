package com.stepjump.goodjob;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_id, et_pass, et_admin_pass, et_name, et_age, et_user_conmment;
    private Button btn_register, btn_agree_view;
    private CheckBox chkbox_join_agree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {    // 엑티비티 시작시 처음으로 실행되는 생명주기
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 아이디 값 찾아주기
        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        et_admin_pass = findViewById(R.id.et_admin_pass);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_user_conmment = findViewById(R.id.et_user_conmment);

        // 약관보기 버튼 클릭시 수행
        btn_agree_view = findViewById(R.id.btn_agree_view);
        btn_agree_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                onBackPressed();

                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                //다이얼로그 창의 제목 입력
                builder.setTitle("서비스 이용약관 ");
                //다이얼로그 창의 내용 입력
                builder.setMessage(R.string.app_arg); //이용약관 내용 추가  ,예시는 res-values-string 에 추가해서 사용
                //다이얼로그창에 취소 버튼 추가
                builder.setNegativeButton("닫기",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
//                                System.out.println(TAG + "이용약관 닫기");
                            }
                        });
                //다이얼로그 보여주기
                builder.show();
            }
        });

        
        // 회원가입 버튼 클릭시 수행
        chkbox_join_agree = findViewById(R.id.chkbox_join_agree);
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //약관 동의시
                if(chkbox_join_agree.isChecked()) {

                    // EditText 에 현재 입력되어있는 값을 get(가져온다)해온다.
                    String userID = et_id.getText().toString();
                    String userPass = et_pass.getText().toString();
                    String userAdminPass = et_admin_pass.getText().toString();
                    String userName = et_name.getText().toString();
                    //int userAge = Integer.parseInt(et_age.getText().toString());
                    String userAge = et_age.getText().toString();
                    String user_conmment = et_user_conmment.getText().toString();

                    if(userID.equals("") || userPass.equals("") || userAdminPass.equals("")) {
                        Toast.makeText(getApplicationContext(), "아이디, 로그인 비밀번호, 관리자 비밀번호 를 입력해주세요." +
                                ".", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    boolean exist = jsonObject.getBoolean("exist");

                                    if (exist) {   // 중복된ID 가 있을때
                                            Toast.makeText(getApplicationContext(), "동일한 ID 가 있습니다. 다른 ID로 등록해 주세요.", Toast.LENGTH_SHORT).show();
                                            return;
                                    }
                                    else {

                                        if (success) {   // 회원등록에 성공한 경우

                                            // =====================================================================================
                                            // 모든 페이지에서 사용할 수 있도록 로그인 정보(id) 저장
                                            SharedPreferences pref_login_enter = getSharedPreferences("login_enter_info",MODE_PRIVATE);
                                            SharedPreferences.Editor editor_login_enter = pref_login_enter.edit();
                                            editor_login_enter.putString("user_id", userID);
                                            editor_login_enter.putString("user_name", userName);
                                            editor_login_enter.putString("user_pass", userPass);
                                            editor_login_enter.putString("user_admin_pass", userAdminPass);
                                            editor_login_enter.putString("user_age", userAge);
                                            editor_login_enter.putString("user_comment", user_conmment);
                                            editor_login_enter.commit();
                                            // =====================================================================================




                                            Toast.makeText(getApplicationContext(), "회원 등록에 성공에 했습니다.", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                        } else { // 회원등록에 실패한 경우
                                            Toast.makeText(getApplicationContext(), "회원 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        // 서버로 Volley를 이용해서 요청을 함.
                        RegisterRequest registerRequest = new RegisterRequest(userID, userPass, userAdminPass, userName, userAge, user_conmment, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                        queue.add(registerRequest);

                    }


                }
                else {
                    Toast.makeText(getApplicationContext(), "약관동의가 필요합니다." +
                            ".", Toast.LENGTH_SHORT).show();
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
