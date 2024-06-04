package com.stepjump.goodjob;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;
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


public class MainActivity2 extends AppCompatActivity {

    private TextView textView_date;
    private EditText edit_temp1, edit_temp2, edit_temp3, edit_height, edit_weight, edit_comment;    // 등교전(아침) 온도(℃), 등교후(저녁) 온도(℃), 자기전(밤) 온도(℃), 다른 적을 내용
    private DatePickerDialog.OnDateSetListener callbackMethod_date;
    int mYear, mMonth, mDay;
    int tYear, tMonth, tDay;    // 소팅을 위해 필요(REG_TIME 기록)
    int dYear, dMonth, dDay;    // 이력조회시 날짜선택을 위해

    String cvt_f_date, cvt_t_date;  // 조회기간 시작일시 구하기 (1달간 기록) - 형식 20200401

    private Button btn_register, btn_query; // 등록하기 버튼, 열온도 내역보기 버튼

    String rv_id_temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btn_register = findViewById(R.id.btn_register);
        btn_query = findViewById(R.id.btn_query);

        textView_date = (TextView)findViewById(R.id.textView_date);     // 열온드 체크날짜

        edit_temp1 = (EditText)findViewById(R.id.edit_temp1);   // 등교전(아침) 온도(℃)
        edit_temp2 = (EditText)findViewById(R.id.edit_temp2);   // 등교후(저녁) 온도(℃)
        edit_temp3 = (EditText)findViewById(R.id.edit_temp3);   // 자기전(밤) 온도(℃)
        edit_height = (EditText)findViewById(R.id.edit_height);   // 키,신장
        edit_weight = (EditText)findViewById(R.id.edit_weight);   // 몸무게
        edit_comment = (EditText)findViewById(R.id.edit_comment);   // 다른 적을 내용


        Intent intent = getIntent(); /*데이터 수신*/

        String rv_temp1 = intent.getExtras().getString("temp1");    // OpenActivity 에서 넘어온값
        edit_temp1.setText(rv_temp1.replace("null", "") );

        String rv_temp2 = intent.getExtras().getString("temp2");    // OpenActivity 에서 넘어온값
        edit_temp2.setText(rv_temp2.replace("null", "") );

        String rv_temp3 = intent.getExtras().getString("temp3");    // OpenActivity 에서 넘어온값
        edit_temp3.setText(rv_temp3.replace("null", "") );

        String rv_height = intent.getExtras().getString("height");    // OpenActivity 에서 넘어온값
        edit_height.setText(rv_height.replace("null", "") );

        String rv_weight = intent.getExtras().getString("weight");    // OpenActivity 에서 넘어온값
        edit_weight.setText(rv_weight.replace("null", "") );

        String rv_etc_comment = intent.getExtras().getString("etc_comment");    // OpenActivity 에서 넘어온값
        edit_comment.setText(rv_etc_comment.replace("null", "") );


        // =====================================================================================
        // 모든 페이지에서 사용할 수 있도록 LoginActivity.java 에서 저장한 로그인 정보(id) 불러오기
        SharedPreferences pref = getSharedPreferences("login_info",MODE_PRIVATE);
        rv_id_temp = pref.getString("user_id","");
        // =====================================================================================



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



        EditText edit_comment = (EditText)findViewById(R.id.edit_comment);
        Button btn_reg_comment = (Button)findViewById(R.id.btn_reg_comment);


        btn_reg_comment.setOnClickListener(new View.OnClickListener() {    // 다른 적을내용 입력 팝업 띄우기(Button 클릭시)
            @Override
            public void onClick(View v) {

                    try {
                        // 관리자 비밀번호와 입력한 비밀번호 같다면 관리자 페이지로 이동
                        // 관리자 비밀번호 ===> user_admin_pass
                        AlertDialog.Builder comment_page = new AlertDialog.Builder(MainActivity2.this);

                        comment_page.setTitle("다른 적을내용 입력");
                        comment_page.setMessage("기타 내용을 입력해주세요~!!");

                        final EditText comment_page_text = new EditText(MainActivity2.this);

                        comment_page_text.setText(edit_comment.getText());   // EditText edit_comment 에 있는 내용 입력폼에 세팅
                        comment_page.setView(comment_page_text);


                        comment_page.setPositiveButton("작성완료", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                edit_comment.setText(comment_page_text.getText().toString());

                                //키보드 내리기
                                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(comment_page_text.getWindowToken(), 0);
                            }
                        });

                        comment_page.setNegativeButton("취소",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // 취소처리

                                //키보드 내리기
                                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(comment_page_text.getWindowToken(), 0);
                            }
                        });

                        comment_page.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


            }
        });

        btn_query.setOnClickListener(new View.OnClickListener() {    // 열온도 내역보기
            @Override
            public void onClick(View v) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                                    Intent intent = new Intent(MainActivity2.this, ListActivity2.class);
                                    intent.putExtra("cvt_f_date", cvt_f_date); // 조회시작일시(년월일)
                                    intent.putExtra("cvt_t_date", cvt_t_date); // 조회종료일시(년월일)
                                    intent.putExtra("snd_message", response); // ListActivity2  으로 DB에서 가져온 전체 데이타 listView 처리하는 페이지로 문자열 송신
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


                List2Request list2Request = new List2Request(rv_id_temp, cvt_f_date, cvt_t_date, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity2.this);
                queue.add(list2Request);

            }
        });




        btn_register.setOnClickListener(new View.OnClickListener() {     // 등록하기
            @Override
            public void onClick(View v) {

//                if ( (! textView_date.getText().toString().trim().equals("")) && (! edit_temp1.getText().toString().trim().equals("")) &&  (! edit_temp2.getText().toString().trim().equals(""))  &&  (! edit_temp3.getText().toString().trim().equals("")) ) {   // 입력체크



                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        boolean success = jsonObject.getBoolean("success");

                                        if(success) {   // 등록에 성공한 경우
                                            Toast.makeText(getApplicationContext(), " 등록에 성공했습니다.", Toast.LENGTH_SHORT).show();

                                            //키보드 내리기
                                            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                                            imm.hideSoftInputFromWindow(btn_register.getWindowToken(), 0);

//                                            // 입력된 온도값 지우기
//                                            edit_temp1.setText("");
//                                            edit_temp2.setText("");
//                                            edit_temp3.setText("");
//                                            edit_comment.setText("");
                                        }
                                        else { // 등록에 실패한 경우
                                            Toast.makeText(getApplicationContext(), "등록에 실패했습니다.", Toast.LENGTH_SHORT).show();

                                            //키보드 내리기
                                            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                                            imm.hideSoftInputFromWindow(btn_register.getWindowToken(), 0);
//                                            // 입력된 온도값 지우기
//                                            edit_temp1.setText("");
//                                            edit_temp2.setText("");
//                                            edit_temp3.setText("");
//                                            edit_comment.setText("");

                                            return;
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };

                            String textView_cvt_date;

                            textView_cvt_date = Integer.toString(tYear) + String.format("%02d", tMonth) + String.format("%02d", tDay);   // 20200422 형식으로 만들기

                            MainRequest2 mainRequest2 = new MainRequest2(rv_id_temp,textView_date.getText().toString(), edit_temp1.getText().toString().trim(), edit_temp2.getText().toString().trim(), edit_temp3.getText().toString().trim(), edit_height.getText().toString().trim(), edit_weight.getText().toString().trim(), edit_comment.getText().toString().trim(), textView_cvt_date, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(MainActivity2.this);
                            queue.add(mainRequest2);

            }
        });


        // 돌아가기 버튼 클릭
        Button btn_home = (Button)findViewById(R.id.btn_home);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(MainActivity2.this, OpenActivity.class);
//                startActivity(intent);
//                finish();

                onBackPressed();
            }
        });


    }



    public void InitializeView()
    {
        mMonth = mMonth + 1;      // 월은 +1 해줘야한다

        textView_date.setText(mYear + "년" +  mMonth + "월" + mDay + "일");    // 오늘날짜 지정
    }

    public void InitializeListener_date()
    {
        callbackMethod_date = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                monthOfYear = monthOfYear + 1;      // 월은 +1 해줘야한다.

                tYear = year;       // 소팅을 위해 필요(REG_TIME 기록)
                tMonth = monthOfYear;
                tDay = dayOfMonth;

                textView_date.setText(year + "년" + monthOfYear  + "월" + dayOfMonth + "일");

                load_data(textView_date.getText().toString().trim());
            }
        };

    }

    public void OnClickHandler_date(View view)
    {
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod_date, mYear, mMonth-1, mDay);

        dialog.show();

        //키보드 내리기
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edit_comment.getWindowToken(), 0);

        //Toast.makeText(getApplicationContext(), "test~!!", Toast.LENGTH_SHORT).show();
    }


    public void load_data(String TODAY_STR)     // 선택한 날짜의 온도내역 불러오기
    {
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

                        edit_temp1.setText(temp1.replace("null", ""));      // OpenRequest 에서 넘어온값

                        edit_temp2.setText(temp2.replace("null", ""));

                        edit_temp3.setText(temp3.replace("null", ""));

                        edit_height.setText(height.replace("null", ""));

                        edit_weight.setText(weight.replace("null", ""));

                        edit_comment.setText(etc_comment.replace("null", ""));

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
        RequestQueue queue = Volley.newRequestQueue(MainActivity2.this);
        queue.add(openRequest2);
    }


}



