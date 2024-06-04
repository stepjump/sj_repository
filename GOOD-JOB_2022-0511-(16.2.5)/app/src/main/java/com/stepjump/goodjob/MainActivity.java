package com.stepjump.goodjob;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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


public class MainActivity extends AppCompatActivity {

    private TextView textView_date, textView_active, txt_today_money, txt_total_money;
    private DatePickerDialog.OnDateSetListener callbackMethod_date;
    int mYear, mMonth, mDay;
    int tYear, tMonth, tDay;    // 소팅을 위해 필요(REG_TIME 기록)
    int dYear, dMonth, dDay;    // 이력조회시 날짜선택을 위해

    String cvt_f_date, cvt_t_date;  // 조회기간 시작일시 구하기 (1달간 기록) - 형식 20200401

    String rv_id_temp;

    private Button btn_sel_activebutton, btn_register, btn_query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_sel_activebutton = findViewById(R.id.btn_sel_activebutton);
        btn_register = findViewById(R.id.btn_register);
        btn_query = findViewById(R.id.btn_query);   // 수입내역 보기
        txt_today_money = findViewById(R.id.txt_today_money);   // 오늘 착한일 수입내역
        txt_total_money = findViewById(R.id.txt_total_money);   // 결제해야할 착한일값

        textView_date = (TextView)findViewById(R.id.textView_date);     // 착한일한 날짜
        textView_active = (TextView)findViewById(R.id.textView_active); // 착한일 목록


        // =====================================================================================
        // 모든 페이지에서 사용할 수 있도록 LoginActivity.java 에서 저장한 로그인 정보(id) 불러오기
        SharedPreferences pref = getSharedPreferences("login_info",MODE_PRIVATE);
        rv_id_temp = pref.getString("user_id","");
        // =====================================================================================


        Intent intent = getIntent(); /*데이터 수신*/

        String rv_total_money = intent.getExtras().getString("total_money");    // OpenActivity 에서 넘어온값 - 결제해야할 착한일값
        txt_total_money.setText(rv_total_money.replace("null", "0") + " 원");

        String rv_today_money = intent.getExtras().getString("today_money");    // OpenActivity 에서 넘어온값 - 오늘 착한일 수입내역
        txt_today_money.setText(rv_today_money.replace("null", "0") + " 원");

        String rv_active_list = intent.getExtras().getString("active_list");    // OpenActivity 에서 넘어온값 - 오늘 착한일 수입내역
        textView_active.setText(rv_active_list.replace("null", "착한일 목록"));


        //현재 날짜와 시간을 가져오기위한 Calendar 인스턴스 선언
        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        tYear = mYear;       // 소팅을 위해 필요(REG_TIME 기록)
        tMonth = mMonth + 1;
        tDay = mDay;


        this.InitializeView();  // 오늘날짜 지정
        this.InitializeListener_date(); // 날짜 선택하기




        btn_sel_activebutton.setOnClickListener(new View.OnClickListener() {    // 착한일 선택하기
            @Override
            public void onClick(View v) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


//                        Intent intent = new Intent(MainActivity.this, ListActivity.class);
//                        intent.putExtra("active_list",textView_active.getText().toString()); // 송신 - 착한일 수입내역 항목
//                        startActivityForResult(intent, 0);

                        try {
                            Intent intent = new Intent(MainActivity.this, ListActivity.class);
                            intent.putExtra("active_list",textView_active.getText().toString()); // 송신 - 착한일 수입내역 항목
                            intent.putExtra("snd_message", response); // MainActivity 으로 DB에서 가져온 전체 데이타 listView 처리하는 페이지로 문자열 송신
                            startActivityForResult(intent, 0);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                };
                    ListRequest listRequest = new ListRequest(rv_id_temp, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    queue.add(listRequest);
                }
        });




        btn_query.setOnClickListener(new View.OnClickListener() {    // 수입 내역보기
            @Override
            public void onClick(View v) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            Intent intent = new Intent(MainActivity.this, ListActivity1.class);
                            intent.putExtra("cvt_f_date", cvt_f_date); // 조회시작일시(년월일)
                            intent.putExtra("cvt_t_date", cvt_t_date); // 조회종료일시(년월일)
                            intent.putExtra("snd_message", response); // ListActivity1 으로 DB에서 가져온 전체 데이타 listView 처리하는 페이지로 문자열 송신
                            startActivity(intent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                // 조회기간 시작일시 구하기 (1달간 기록) ===============================================================================
                //String cvt_f_date, cvt_t_date;

                dYear = mYear;  // 오늘 날짜를 이용
                dMonth = mMonth;
                dDay = mDay;

                cvt_t_date = Integer.toString(dYear) + String.format("%02d", dMonth) + String.format("%02d", dDay);   // 조회종료일시(년월일) - 현재

                if (dMonth == 1) {
                    dYear = dYear - 1;
                    dMonth = 12;
                }
                else {
                    dMonth = dMonth - 1;
                }
                // =============================================================================================================

                cvt_f_date = Integer.toString(dYear) + String.format("%02d", dMonth) + String.format("%02d", dDay);   // 조회시작일시(년월일) - 1달전


                List1Request list1Request = new List1Request(rv_id_temp, cvt_f_date, cvt_t_date, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(list1Request);
            }
        });



        btn_register.setOnClickListener(new View.OnClickListener() {     // 등록하기
            @Override
            public void onClick(View v) {

//                if ( (! textView_active.getText().toString().trim().equals("")) &&  (! textView_active.getText().toString().trim().equals("착한일 목록"))  ) {   // 착한일을 한개라도 등록했다면


                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    String  total_money = jsonObject.getString("total_money");

                                    if(success) {   // 등록에 성공한 경우
                                        Toast.makeText(getApplicationContext(), " 등록에 성공했습니다.", Toast.LENGTH_SHORT).show();

                                        txt_total_money.setText(total_money + " 원");
                                    }
                                    else { // 회원등록에 실패한 경우
                                        Toast.makeText(getApplicationContext(), "등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };


                        // value.replaceAll("(\r\n|\r|\n|\n\r)", " ");   // 줄바꿈문자 치환

                        String textView_cvt_date;

                                      textView_cvt_date = Integer.toString(tYear) + String.format("%02d", tMonth) + String.format("%02d", tDay);   // 20200422 형식으로 만들기


                MainRequest mainRequest = new MainRequest(rv_id_temp, textView_date.getText().toString(), txt_today_money.getText().toString().replace("원",""), textView_active.getText().toString().trim(), textView_cvt_date, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                        queue.add(mainRequest);

            }
        });




        // 돌아가기 버튼 클릭
        Button btn_home = (Button)findViewById(R.id.btn_home);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {     // ListActivity.java 에서 얻은 오늘 착한일값 전체금액 처리
        super.onActivityResult(requestCode, resultCode, data);




        switch (resultCode) {
            case 1:

                txt_today_money.setText(data.getStringExtra("today_money") + " 원");    // 오늘 착한일 수입내역
                textView_active.setText(data.getStringExtra("active_title"));    // 착한일 목록


                break;
            default:
                break;
        }
    }


    public void InitializeView()
    {
        mMonth = mMonth + 1;      // 월은 +1 해줘야한다
        textView_date.setText(mYear + "년" + mMonth  + "월" + mDay + "일");    // 오늘날짜 지정
    }

    public void InitializeListener_date()
    {

        callbackMethod_date = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                monthOfYear = monthOfYear + 1;      // 월은 +1 해줘야한다.

                textView_date.setText(year + "년" + monthOfYear  + "월" + dayOfMonth + "일");

                tYear = year;       // 소팅을 위해 필요(REG_TIME 기록)
                tMonth = monthOfYear;
                tDay = dayOfMonth;

                load_data(textView_date.getText().toString().trim());
            }
        };
    }

    public void OnClickHandler_date(View view)
    {
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod_date, mYear, mMonth-1, mDay);

        dialog.show();
    }


    public void load_data(String TODAY_STR)     // 선택한 날짜의 착한일 수입내역 불러오기
    {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    String total_money = jsonObject.getString("total_money");
                    String today_money = jsonObject.getString("today_money");
                    String active_list = jsonObject.getString("active_list");

                    if (success) {   // 수입내역 불러오기에 성공한 경우
//                                Toast.makeText(getApplicationContext(), " 수입내역 불러오기에 성공했습니다.", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(getApplicationContext(), total_money, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(getApplicationContext(), today_money, Toast.LENGTH_SHORT).show();
//                               Toast.makeText(getApplicationContext(), active_list, Toast.LENGTH_SHORT).show();

                        txt_total_money.setText(total_money.replace("null", "0") + " 원");   // OpenRequest 에서 넘어온값 - 해당날짜 수입

                        txt_today_money.setText(today_money.replace("null", "0") + " 원");    // OpenRequest 에서 넘어온값 - 결제해야할 금액

                        textView_active.setText(active_list.replace("null", "착한일 목록"));  // OpenRequest 에서 넘어온값 - 수입내역 항목


                    } else { // 수입내역 불러오기에 실패한 경우
                        Toast.makeText(getApplicationContext(), "수입내역 불러오기에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        OpenRequest openRequest = new OpenRequest(rv_id_temp, TODAY_STR, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(openRequest);
    }

}

