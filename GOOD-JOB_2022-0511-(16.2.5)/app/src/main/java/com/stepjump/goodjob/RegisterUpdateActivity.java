package com.stepjump.goodjob;

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

public class RegisterUpdateActivity extends AppCompatActivity {

    private EditText et_id, et_pass, et_admin_pass, et_name, et_age, et_user_conmment;
    private Button btn_register_update;

    String rv_id_temp;
    String rv_name_temp;
    String rv_password_temp;
    String rv_admin_pass_temp;
    String rv_age_temp;
    String rv_userComment;  // 사용자 수식어(ex.귀여운 우리아이)

    @Override
    protected void onCreate(Bundle savedInstanceState) {    // 엑티비티 시작시 처음으로 실행되는 생명주기
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_update);

        // 아이디 값 찾아주기
        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        et_admin_pass = findViewById(R.id.et_admin_pass);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_user_conmment = findViewById(R.id.et_user_conmment);


        // =====================================================================================
        // 모든 페이지에서 사용할 수 있도록 LoginActivity.java 에서 저장한 로그인 정보(id) 불러오기
        SharedPreferences pref = getSharedPreferences("login_info",MODE_PRIVATE);
        rv_id_temp = pref.getString("user_id","");
        rv_name_temp = pref.getString("user_name","");
        rv_password_temp = pref.getString("user_pass","");
        rv_admin_pass_temp = pref.getString("user_admin_pass","");
        rv_age_temp = pref.getString("user_age","");
        rv_userComment = pref.getString("user_comment","");
        // =====================================================================================
        et_id.setText(rv_id_temp.replace("null", "") );
        et_name.setText(rv_name_temp.replace("null", "") );
        et_pass.setText(rv_password_temp.replace("null", "") );
        et_admin_pass.setText(rv_admin_pass_temp.replace("null", "") );
        et_age.setText(rv_age_temp.replace("null", "") );
        et_user_conmment.setText(rv_userComment.replace("null", "") );
        // ========================================================================================================1




        // 회원 정보 변경 버튼 클릭시 수행
        btn_register_update = findViewById(R.id.btn_register_update);
        btn_register_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText 에 현재 입력되어있는 값을 get(가져온다)해온다.
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();
                String userAdminPass = et_admin_pass.getText().toString();
                String userName = et_name.getText().toString();
                //int userAge = Integer.parseInt(et_age.getText().toString());
                String userAge = et_age.getText().toString();
                String user_conmment = et_user_conmment.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if(success) {   // 회원 정보 변경에 성공한 경우

                                Toast.makeText(getApplicationContext(), "회원 정보 변경에 성공에 했습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterUpdateActivity.this, AdminActivity.class);
                                startActivity(intent);
                            }
                            else { // 회원 정보 변경에 실패한 경우
                                Toast.makeText(getApplicationContext(), "회원 정보 변경에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                // 서버로 Volley를 이용해서 요청을 함.
                RegisterUpdateRequest registerupdateRequest = new RegisterUpdateRequest(userID, userPass, userAdminPass, userName, userAge, user_conmment, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterUpdateActivity.this);
                queue.add(registerupdateRequest);
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
