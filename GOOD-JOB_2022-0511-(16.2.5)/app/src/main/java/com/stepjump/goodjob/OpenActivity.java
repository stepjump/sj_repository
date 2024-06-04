package com.stepjump.goodjob;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class OpenActivity extends AppCompatActivity {

    private Button btn_start, btn_start2, btn_admin;
    int mYear, mMonth, mDay;
    String TODAY_STR;

    private TextView tv_id_temp, tv_name_temp, tv_intro, tv_intro2;
    String rv_id_temp;
    String rv_name_temp;
    String rv_userComment;  // 사용자 수식어(ex.귀여운 우리아이)
    String user_admin_pass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open);

        btn_start = findViewById(R.id.btn_start);
        btn_start2 = findViewById(R.id.btn_start2);
        btn_admin = findViewById(R.id.btn_admin);   // admin 페이지로 가기


        // ========================================================================================================
        // LoginActivity 화면에서 OpenActivity 화면으로 로그인 계정값넘기기
        // ========================================================================================================
        tv_id_temp = (TextView)findViewById(R.id.tv_id);
        tv_name_temp = (TextView)findViewById(R.id.tv_name);

        tv_intro = (TextView)findViewById(R.id.txt_intro);
        tv_intro2 = (TextView)findViewById(R.id.txt_intro2);

//        Intent intent = getIntent(); /*데이터 수신*/

//        rv_id_temp = intent.getExtras().getString("userID");    // LoginActivity 에서 넘어온값

        // =====================================================================================
        // 모든 페이지에서 사용할 수 있도록 LoginActivity.java 에서 저장한 로그인 정보(id) 불러오기
        SharedPreferences pref = getSharedPreferences("login_info",MODE_PRIVATE);
        rv_id_temp = pref.getString("user_id","");
        rv_name_temp = pref.getString("user_name","");
        rv_userComment = pref.getString("user_comment","");
        user_admin_pass = pref.getString("user_admin_pass","");
        // =====================================================================================
        tv_id_temp.setText(rv_id_temp.replace("null", "") );
        tv_name_temp.setText(rv_name_temp.replace("null", "") );
        // ========================================================================================================1

        tv_intro.setText(rv_userComment + "\n 착한일 수입내역");
        tv_intro2.setText(rv_userComment + "\n 열온도 체크일지");



        //현재 날짜와 시간을 가져오기위한 Calendar 인스턴스 선언
        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        mMonth = mMonth + 1;      // 월은 +1 해줘야한다
        TODAY_STR = (mYear + "년" + mMonth  + "월" + mDay + "일");    // 오늘날짜 지정


            btn_start.setOnClickListener(new View.OnClickListener() {     // 시작하며 데이터 가져오기(결제해야할 심부름값, 해당하는 날짜 심부름값, 해당하는 날짜 착한일 리스트
            @Override
            public void onClick(View v) {


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {   // 착한일 등록
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            String total_money = jsonObject.getString("total_money");
                            String today_money = jsonObject.getString("today_money");
                            String active_list = jsonObject.getString("active_list");

                            if (success) {   // 시작에 성공한 경우
                               Toast.makeText(getApplicationContext(), " 시작에 성공했습니다.", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(getApplicationContext(), total_money, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(getApplicationContext(), today_money, Toast.LENGTH_SHORT).show();
//                               Toast.makeText(getApplicationContext(), active_list, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                                intent.putExtra("total_money",total_money); // 송신 - 결제해야할 심부름값
                                intent.putExtra("today_money",today_money); // 송신 - 오늘 심부름 수입내역
                                intent.putExtra("active_list",active_list); // 송신 - 수입내역 항목

                                startActivity(intent);

                            } else { // 시작에 실패한 경우
                                Toast.makeText(getApplicationContext(), "시작에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                OpenRequest openRequest = new OpenRequest(rv_id_temp, TODAY_STR, responseListener);
                RequestQueue queue = Volley.newRequestQueue(OpenActivity.this);
                queue.add(openRequest);

            }
        });




        btn_start2.setOnClickListener(new View.OnClickListener() {      // 열온도 체크
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
//
//                startActivity(intent);


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            String temp1 = jsonObject.getString("temp1");
                            String temp2 = jsonObject.getString("temp2");
                            String temp3 = jsonObject.getString("temp3");
                            String height = jsonObject.getString("height");
                            String weight = jsonObject.getString("weight");
                            String etc_comment = jsonObject.getString("etc_comment");

                            if (success) {   // 시작에 성공한 경우
//                                Toast.makeText(getApplicationContext(), " 시작에 성공했습니다.", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(getApplicationContext(), temp1, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(getApplicationContext(), temp2, Toast.LENGTH_SHORT).show();
//                               Toast.makeText(getApplicationContext(), temp3, Toast.LENGTH_SHORT).show();
//                               Toast.makeText(getApplicationContext(), etc_comment, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);

                                intent.putExtra("temp1",temp1); // 송신
                                intent.putExtra("temp2",temp2); // 송신
                                intent.putExtra("temp3",temp3); // 송신
                                intent.putExtra("height",height); // 송신
                                intent.putExtra("weight",weight); // 송신
                                intent.putExtra("etc_comment",etc_comment); // 송신

                                startActivity(intent);

                            } else { // 시작에 실패한 경우
                                Toast.makeText(getApplicationContext(), "시작에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                OpenRequest2 openRequest2 = new OpenRequest2(TODAY_STR, rv_id_temp, responseListener);
                RequestQueue queue = Volley.newRequestQueue(OpenActivity.this);
                queue.add(openRequest2);

            }
        });



        btn_admin.setOnClickListener(new View.OnClickListener()    {     // admin 페이지로 가기로 가기 위한 암호입력 페이지로 가기

            @Override
            public void onClick(View v) {

                try {
                        // 관리자 비밀번호와 입력한 비밀번호 같다면 관리자 페이지로 이동
                        // 관리자 비밀번호 ===> user_admin_pass
                        AlertDialog.Builder adminpage = new AlertDialog.Builder(OpenActivity.this);

                        adminpage.setTitle("관리자 페이지로 이동");
                        adminpage.setMessage("암호를 입력해주세요~!!");

                        final EditText adminpage_password = new EditText(OpenActivity.this);
                        adminpage.setView(adminpage_password);

                        adminpage.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                if (adminpage_password.getText().toString().equals(user_admin_pass)) {
                                    Intent intent = new Intent(OpenActivity.this,  AdminActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(OpenActivity.this, "암호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        adminpage.setNegativeButton("no",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // 취소처리
                            }
                        });

                        adminpage.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });



    }

}

