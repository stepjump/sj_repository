package com.stepjump.goodjob;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ListActivity2 extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener callbackMethod_date;
    private DatePickerDialog.OnDateSetListener callbackMethod_tdate;
    private TextView textView_fdate, textView_tdate;
    int mYear, mMonth, mDay;

    boolean link_flag = false;  // 조회날짜가 MainActivity1 에서 넘어온 날짜인지 체크

    String cvt_f_date = ""; // 조회시작일시(년월일) - // MainActivity1 에서 넘어온 조회시작일
    String cvt_t_date = "";

    String rv_id_temp;

    private Button btn_query;

    String rv_message = "";
    String list_item = "";
    ListView listview;
    ArrayAdapter<String> adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);

        // =====================================================================================
        // 모든 페이지에서 사용할 수 있도록 LoginActivity.java 에서 저장한 로그인 정보(id) 불러오기
        SharedPreferences pref = getSharedPreferences("login_info",MODE_PRIVATE);
        rv_id_temp = pref.getString("user_id","");
        // =====================================================================================

        btn_query = findViewById(R.id.btn_query);   // 수입내역 보기

        Intent intent = getIntent(); /*데이터 수신*/


        textView_fdate = (TextView) findViewById(R.id.textView_fdate);
        textView_tdate = (TextView) findViewById(R.id.textView_tdate);


        rv_message = intent.getExtras().getString("snd_message");    // MainActivity2 에서 넘어온값
        cvt_f_date = intent.getExtras().getString("cvt_f_date");     // 조회시작일시(년월일)
        cvt_t_date = intent.getExtras().getString("cvt_t_date");    // 조회종료일시(년월일)


        if (rv_message == "") {    // MainActivity1 에서 넘어온값이 없다면(캘린더 선택시)
            link_flag = false;  // 조회날짜가 MainActivity1 에서 넘어오지 않음
        } else {
            link_flag = true;   // 조회날짜가 MainActivity1 에서 넘어왔음
        }


        String tY1, tM1, tD1;
        String tY2, tM2, tD2;

        tY1 = cvt_f_date.substring(0, 4);
        tM1 = Integer.toString(Integer.parseInt(cvt_f_date.substring(4, 6)));      // 문자열을 정수로 변환후 다시 문자열로 변환
        tD1 = Integer.toString(Integer.parseInt(cvt_f_date.substring(6, 8)));      // 문자열을 정수로 변환후 다시 문자열로 변환
        tY2 = cvt_t_date.substring(0, 4);
        tM2 = Integer.toString(Integer.parseInt(cvt_t_date.substring(4, 6)));       // 문자열을 정수로 변환후 다시 문자열로 변환
        tD2 = Integer.toString(Integer.parseInt(cvt_t_date.substring(6, 8)));       // 문자열을 정수로 변환후 다시 문자열로 변환


        textView_fdate.setText(tY1 + "년" + tM1 + "월" + tD1 + "일");     // MainActivity1 에서 넘어온 조회시작일 세팅
        textView_tdate.setText(tY2 + "년" + tM2 + "월" + tD2 + "일");     // MainActivity1 에서 넘어온 조회종료일 세팅


        //현재 날짜와 시간을 가져오기위한 Calendar 인스턴스 선언
        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);


        this.InitializeView();  // 오늘날짜 지정
        this.InitializeListener_date(); // 날짜 선택하기 - 조회시작일자
        this.InitializeListener_tdate();   // 날짜 선택하기 - 조회종료일자

        ListView(rv_message);   // ListView 보기



        // 돌아가기 버튼 클릭
        Button btn_back = (Button) findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });





        btn_query.setOnClickListener(new View.OnClickListener() {    // 열온도 체크 내역보기
            @Override
            public void onClick(View v) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
//                            Intent intent = new Intent(ListActivity2.this, ListActivity2.class);
//                            intent.putExtra("cvt_f_date", cvt_f_date); // 조회시작일시(년월일)
//                            intent.putExtra("cvt_t_date", cvt_t_date); // 조회종료일시(년월일)
//                            intent.putExtra("snd_message", response); // ListActivity1 으로 DB에서 가져온 전체 데이타 listView 처리하는 페이지로 문자열 송신
//                            startActivity(intent);

                            ListView(response);   // ListView 보기

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                // 조회시작일 만들기 (2020년4월5일 ===> 20200405 로 만들기) ====================================================================
                String fDate, fsY, fsM, fsD;
                int fiM, fiD;

                fsY = textView_fdate.getText().toString().substring(0, 4).toString();    // 0번째 자리에서 4번째 자리까지 읽기
                fsM = textView_fdate.getText().toString().substring(5, 7).replace("월", "").toString();    // 5번째 자리에서 7번째 자리까지 읽기
                fsD = textView_fdate.getText().toString().substring(textView_fdate.getText().length() - 3, textView_fdate.getText().length()).replace("월", "").replace("일", "").toString();   // 뒤에서 3자리까지 읽기, 2020년4월2일 ===> 월2일, 2020년04월12일 ===> 12일

                fiM = Integer.parseInt(fsM);    // month 문자를 정수로 변환
                fiD = Integer.parseInt(fsD);    // day 문자를 정수로 변환

                cvt_f_date = fsY + String.format("%02d", fiM) + String.format("%02d", fiD);   // 20200422 형식으로 만들기
                // 조회시작일 만들기 (2020년4월5일 ===> 20200405 로 만들기) ====================================================================


                // 조회종료일 만들기 (2020년4월5일 ===> 20200405 로 만들기) ====================================================================
                String tDate, tsY, tsM, tsD;
                int tiM, tiD;

                tsY = textView_tdate.getText().toString().substring(0, 4).toString();    // 0번째 자리에서 4번째 자리까지 읽기
                tsM = textView_tdate.getText().toString().substring(5, 7).replace("월", "").toString();    // 5번째 자리에서 7번째 자리까지 읽기
                tsD = textView_tdate.getText().toString().substring(textView_tdate.getText().length() - 3, textView_tdate.getText().length()).replace("월", "").replace("일", "").toString();   // 뒤에서 3자리까지 읽기, 2020년4월2일 ===> 월2일, 2020년04월12일 ===> 12일

                tiM = Integer.parseInt(tsM);    // month 문자를 정수로 변환 - 오류??????
                tiD = Integer.parseInt(tsD);    // day 문자를 정수로 변환

                cvt_t_date = tsY + String.format("%02d", tiM) + String.format("%02d", tiD);   // 20200422 형식으로 만들기
                // 조회시작일 만들기 (2020년4월5일 ===> 20200405 로 만들기) ====================================================================


                List2Request list1Request = new List2Request(rv_id_temp, cvt_f_date, cvt_t_date, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ListActivity2.this);
                queue.add(list1Request);
            }
        });

    }




    public void ListView(String rv_message) {   // ListView 보기

        ListView listview ;
        ListViewAdapter2 adapter;

        // Adapter 생성
        adapter = new ListViewAdapter2() ;


        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);



        //데이터를 저장하게 되는 리스트
        List<String> list = new ArrayList<>();

        try
        {
                String result = "";

                JSONArray ja = new JSONArray(rv_message);
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject order = ja.getJSONObject(i);

        //                                        result += "exe_date: " + order.getString("exe_date") + ", temp1: " + order.getString("temp1") +
        //                                                ", temp2: " + order.getString("temp2") + ", temp3: " + order.getString("temp3") + ", etc_comment: " + order.getString("etc_comment") + "\n";

                    boolean success = order.getBoolean("success");
                    String exe_date = order.getString("exe_date");
                    String temp1 = order.getString("temp1");
                    String temp2 = order.getString("temp2");
                    String temp3 = order.getString("temp3");
                    String height = order.getString("height");
                    String weight = order.getString("weight");
                    String etc_comment = order.getString("etc_comment");


                    if (success) {

                        //리스트뷰에 보여질 아이템을 추가
//                        list.add(list_item);
                        adapter.addItem(exe_date, temp1, temp2, temp3, height, weight, etc_comment) ;


                    } else { // 가져오기에 실패한 경우
                        Toast.makeText(getApplicationContext(), "열온도 내역 가져오기에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
        } catch (
            JSONException e)  {
            e.printStackTrace();
        }




// 커스텀 리스트뷰 적용전
//        listview = (ListView) findViewById(R.id.listview1);
//
//        //데이터를 저장하게 되는 리스트
//        List<String> list = new ArrayList<>();
//
//        try
//        {
//            String result = "";
//
//            JSONArray ja = new JSONArray(rv_message);
//            for (int i = 0; i < ja.length(); i++) {
//                JSONObject order = ja.getJSONObject(i);
//
//                //                                        result += "exe_date: " + order.getString("exe_date") + ", temp1: " + order.getString("temp1") +
//                //                                                ", temp2: " + order.getString("temp2") + ", temp3: " + order.getString("temp3") + ", etc_comment: " + order.getString("etc_comment") + "\n";
//
//                boolean success = order.getBoolean("success");
//                String exe_date = order.getString("exe_date");
//                String temp1 = order.getString("temp1");
//                String temp2 = order.getString("temp2");
//                String temp3 = order.getString("temp3");
//                String etc_comment = order.getString("etc_comment");
//
//                list_item = "날짜[" + exe_date + "] - 아침[" + temp1 + "℃] - 저녁[" + temp2 + "℃] - 밤[" + temp3 + "℃] - [" + etc_comment + "]";
//
//                if (success) {
//
//                    //리스트뷰에 보여질 아이템을 추가
//                    list.add(list_item);
//
//
//                } else { // 가져오기에 실패한 경우
//                    Toast.makeText(getApplicationContext(), "열온도 내역 가져오기에 실패했습니다.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//            }
//        } catch (
//                JSONException e)  {
//            e.printStackTrace();
//        }
//
//        //리스트뷰와 리스트를 연결하기 위해 사용되는 어댑터
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
//
//        //리스트뷰의 어댑터를 지정해준다.
//        listview.setAdapter(adapter);

    }




    public void InitializeView()
    {
        mMonth = mMonth + 1;      // 월은 +1 해줘야한다

        if ( link_flag == false) {     // 조회날짜가 MainActivity1 에서 넘어오지 않았을때 날짜 캘린더 선택시 텍스트박스 세팅해줌.
            textView_fdate.setText(mYear + "년" + mMonth  + "월" + mDay + "일");    // 조회시작일자 오늘날짜 지정
            textView_tdate.setText(mYear + "년" + mMonth  + "월" + mDay + "일");    // 조회종료일자 오늘날짜 지정
        }
    }


    public void InitializeListener_date()   // 조회시작일자
    {

        callbackMethod_date = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                monthOfYear = monthOfYear + 1;      // 월은 +1 해줘야한다.

                if ( link_flag == false) {     // 조회날짜가 MainActivity1 에서 넘어오지 않았을때 날짜 캘린더 선택시 텍스트박스 세팅해줌.
                    textView_fdate.setText(year + "년" + monthOfYear  + "월" + dayOfMonth + "일");
                }


            }
        };
    }

    public void OnClickHandler_sel_fdate(View view)     // 조회시작일자
    {
        link_flag = false;   // 조회날짜가 MainActivity1 에서 넘어온후  날짜 캘린더 선택시 텍스트박스 채워주는 이벤트 활성화

        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod_date, mYear, mMonth-1, mDay);

        dialog.show();
    }


    public void InitializeListener_tdate()   // 조회종료일자
    {

        callbackMethod_tdate = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                monthOfYear = monthOfYear + 1;      // 월은 +1 해줘야한다.

                if ( link_flag == false) {     // 조회날짜가 MainActivity1 에서 넘어오지 않았을때 날짜 캘린더 선택시 텍스트박스 세팅해줌.
                    textView_tdate.setText(year + "년" + monthOfYear  + "월" + dayOfMonth + "일");
                }


            }
        };
    }

    public void OnClickHandler_sel_tdate(View view)    // 조회종료일자
    {
        link_flag = false;   // 조회날짜가 MainActivity1 에서 넘어온후  날짜 캘린더 선택시 텍스트박스 채워주는 이벤트 활성화

        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod_tdate, mYear, mMonth-1, mDay);

        dialog.show();
    }


}

