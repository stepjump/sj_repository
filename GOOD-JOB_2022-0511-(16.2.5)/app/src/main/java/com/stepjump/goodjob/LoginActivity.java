package com.stepjump.goodjob;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText et_id, et_pass;
    private Button btn_login, btn_register;
    private CheckBox chkbox_login_save;

    String rv_id_temp;
    String rv_pass_temp;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // =====================================================================================
        // addmob 광고시작
        // =====================================================================================
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        // =====================================================================================
        // addmob 광고종료
        // =====================================================================================


        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        chkbox_login_save = findViewById(R.id.chkbox_login_save);

        // =====================================================================================
        // 모든 페이지에서 사용할 수 있도록 LoginActivity.java 에서 저장한 로그인 정보(id) 불러오기
        SharedPreferences pref_login_enter = getSharedPreferences("login_enter_info",MODE_PRIVATE);
        rv_id_temp = pref_login_enter.getString("user_id","");
        rv_pass_temp = pref_login_enter.getString("user_pass","");

        et_id.setText(rv_id_temp);  // 다음 로그인시 자동입력
        et_pass.setText(rv_pass_temp);
        // =====================================================================================

        // 회원가입 버튼을 클릭시 수행
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // EditText 에 현재 입력되어있는 값을 get(가져온다)해온다.
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if(success) {   // 로그인에 성공한 경우

                                String userID = jsonObject.getString("userID");
                                String userName = jsonObject.getString("userName");
                                String userPass = jsonObject.getString("userPassword");
                                String userAdminPass = jsonObject.getString("userAdminPass");
                                String userAge = jsonObject.getString("userAge");
                                String userComment = jsonObject.getString("userComment");

//                                Toast.makeText(getApplicationContext(), "userAdminPass =" + userAdminPass, Toast.LENGTH_SHORT).show();

                                Toast.makeText(getApplicationContext(), "로그인에 성공에 했습니다.", Toast.LENGTH_SHORT).show();

                                // =====================================================================================
                                // 모든 페이지에서 사용할 수 있도록 계정 정보(id) 저장
                                SharedPreferences pref = getSharedPreferences("login_info",MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("user_id", userID);
                                editor.putString("user_name", userName);
                                editor.putString("user_pass", userPass);
                                editor.putString("user_admin_pass", userAdminPass);
                                editor.putString("user_age", userAge);
                                editor.putString("user_comment", userComment);
                                editor.commit();
                                // =====================================================================================

                                if(chkbox_login_save.isChecked()) {     // 다음로그인시 자동 로그인 체크박스에 체크시 로그인 정보 저장
                                    // =====================================================================================
                                    // 모든 페이지에서 사용할 수 있도록 로그인 정보(id) 저장
                                    SharedPreferences pref_login_enter = getSharedPreferences("login_enter_info",MODE_PRIVATE);
                                    SharedPreferences.Editor editor_login_enter = pref_login_enter.edit();
                                    editor_login_enter.putString("user_id", userID);
                                    editor_login_enter.putString("user_name", userName);
                                    editor_login_enter.putString("user_pass", userPass);
                                    editor_login_enter.putString("user_admin_pass", userAdminPass);
                                    editor_login_enter.putString("user_age", userAge);
                                    editor_login_enter.putString("user_comment", userComment);
                                    editor_login_enter.commit();
                                    // =====================================================================================
                                }
                                else {
                                    // =====================================================================================
                                    // 모든 페이지에서 사용할 수 있도록 로그인 정보(id) 삭제
                                    SharedPreferences pref_login_enter = getSharedPreferences("login_enter_info",MODE_PRIVATE);
                                    SharedPreferences.Editor editor_login_enter = pref_login_enter.edit();
                                    editor_login_enter.remove("user_id");
                                    editor_login_enter.remove("user_name");
                                    editor_login_enter.remove("user_pass");
                                    editor_login_enter.remove("user_admin_pass");;
                                    editor_login_enter.remove("user_age");
                                    editor_login_enter.remove("user_comment");
                                    editor_login_enter.commit();
                                    // =====================================================================================
                                }


                                Intent intent = new Intent(LoginActivity.this, OpenActivity.class);

//                                intent.putExtra("userID", userID);
//                                intent.putExtra("userName", userName);
//                                intent.putExtra("userPass", userPass);

                                startActivity(intent);
                            }
                            else { // 로그인에 실패한 경우
                                Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                LoginRequest loginRequest = new LoginRequest(userID, userPass, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);

            }
        });

    }
}
