package com.stepjump.goodjob;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.List;

public class ListModifyActivity extends AppCompatActivity {

    private EditText edit_money, edit_active_name;
    private TextView listno_str;

    int count = 0;      // 현재 최종 list 항목 위치 구하기
    int checked = 0;   // 현재 리스트중 선택된 항목, db에 저장할 list 항목 순서
    int listno = 0;

    String rv_message = "";

    String rv_id_temp;

//    // 빈 데이터 리스트 생성.
//    ArrayList<String> items = new ArrayList<String>() ;
//    // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.
//    ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, items) ;
//
//    // listview 생성 및 adapter 지정.
//    ListView listview = (ListView) findViewById(R.id.listview1) ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_modify);


        edit_money = (EditText)findViewById(R.id.edit_money);   // 수입금액(원)
        edit_active_name = (EditText)findViewById(R.id.edit_active_name); // 착한일 이름

        listno_str = (TextView)findViewById(R.id.listno);

        // =====================================================================================
        // 모든 페이지에서 사용할 수 있도록 LoginActivity.java 에서 저장한 로그인 정보(id) 불러오기
        SharedPreferences pref = getSharedPreferences("login_info",MODE_PRIVATE);
        rv_id_temp = pref.getString("user_id","");
        // =====================================================================================



        Intent intent = getIntent(); /*데이터 수신*/

        rv_message = intent.getExtras().getString("snd_message");    // AdminActivity 에서 넘어온값 -  착한일 목록


        // 빈 데이터 리스트 생성.
        final ArrayList<String> items = new ArrayList<String>() ;
        // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, items) ;


        // listview 생성 및 adapter 지정.
        final ListView listview = (ListView) findViewById(R.id.listview1) ;
        listview.setAdapter(adapter) ;


        ListView(rv_message);   // ListView 보기


//        count = adapter.getCount() ;    // 현재 선택된 아이템의 position 획득.

//        listno_str.setText(Integer.toString(count + 1));    // 리스트 번호를 라스트 마지막 번호 + 1 로 세팅




        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {     // ListView 클릭시 리스트번호, 수입금액(원), 착한일 이름 입력폼에 클릭한 리스트 항목을 넣기
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //클릭한 아이템의 문자열을 가져옴
                String selected_item = (String)parent.getItemAtPosition(position);
                String[] array = selected_item.split("-");                   // [1]-착한일명-200원 '-' 구분자를 이용해 배열에 담기

                listno_str.setText(array[0].replace("[","").replace("]",""));   // [] 없애기
                edit_active_name.setText(array[1]);
                edit_money.setText(array[2].replace("원",""));           // '원' 없애기

//                Toast.makeText(getApplicationContext(), edit_active_name.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });





        // new button에 대한 이벤트 처리.
        Button newButton = (Button)findViewById(R.id.btn_new) ;
        newButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
//                int count, checked ;
//                count = adapter.getCount() ;

                listno_str.setText(Integer.toString(count + 1));
                edit_money.setText("");
                edit_active_name.setText("");

            }
        }) ;




        // delete button에 대한 이벤트 처리.
        Button deleteButton = (Button)findViewById(R.id.btn_delete) ;
        deleteButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if ( (! listno_str.getText().toString().trim().equals("")) && (! edit_money.getText().toString().trim().equals("")) && (! edit_active_name.getText().toString().trim().equals("")) ) {   // 필수 입력폼 체크 - 리스트 번호

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                if (  (response.replace("\"", "").contains("success:true"))  &&  !(response.replace("\"", "").contains("{\"success\":false}\""))   )
                                {       // 리턴 문자열에 "success":true 가 있고  "success":false 가 없다면

                                    edit_money.setText("");
                                    edit_active_name.setText("");

                                    String ts_n;
                                    int ti_n;

                                    ti_n = count - 1;
                                    if (ti_n < 1) ti_n = 1;

                                    ts_n = Integer.toString(ti_n);

                                    listno_str.setText(ts_n);
                                    edit_money.setText("");
                                    edit_active_name.setText("");

                                    Toast.makeText(getApplicationContext(), "삭제 성공.", Toast.LENGTH_SHORT).show();

                                }
                                else if (response.trim().equals("[]"))      // 마지막 1개 레코드를 삭제할 경우
                                {
                                    Toast.makeText(getApplicationContext(), "삭제 성공.", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "삭제 실패.", Toast.LENGTH_SHORT).show();

                                    //return;
                                }

                                ListView(response);   // ListView 새로 조회

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    ListModifyDeleteRequest commitRequest = new ListModifyDeleteRequest(rv_id_temp, listno_str.getText().toString().trim(), responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ListModifyActivity.this);
                    queue.add(commitRequest);

                }
//                else {
//                    Toast.makeText(getApplicationContext(), "리스트 번호가 없습니다.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
            }
        }) ;



        Button btn_save = (Button)findViewById(R.id.btn_save);

        btn_save.setOnClickListener(new View.OnClickListener() {     // DB에 저장하기

            @Override
            public void onClick(View v) {

                if ( (! listno_str.getText().toString().trim().equals("")) &&  (! edit_money.getText().toString().trim().equals("")) &&  (! edit_active_name.getText().toString().trim().equals(""))  ) {   // 필수 입력폼 체크

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {



                                    if(  (response.replace("\"", "").contains("success:true")) && !(response.replace("\"", "").contains("{\"success\":false}\"")) )
                                    {       // 리턴 문자열에 "success":true 가 있고  "success":false 가 없다면

                                        String ts_n;
                                        int ti_n;

                                        ti_n = count + 1;

                                        ts_n = Integer.toString(ti_n);

                                        listno_str.setText(ts_n);
                                        edit_money.setText("");
                                        edit_active_name.setText("");

                                        edit_money.requestFocus();  // 강제로 수입금액(원)에 포커스 주기

                                        Toast.makeText(getApplicationContext(), "저장 성공.", Toast.LENGTH_SHORT).show();

                                        ListView(response);   // ListView 새로 조회


                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "저장 실패.", Toast.LENGTH_SHORT).show();

                                        return;
                                    }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    ListModifyRequest commitRequest = new ListModifyRequest(rv_id_temp, listno_str.getText().toString().trim(), edit_money.getText().toString(), edit_active_name.getText().toString(), responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ListModifyActivity.this);
                    queue.add(commitRequest);

                }
                else {
                    Toast.makeText(getApplicationContext(), "리스트 번호, 수입금액(원), 착한일 이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
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






    public void ListView(String rv_message) {   // ListView 보기


        // 빈 데이터 리스트 생성.
        ArrayList<String> items = new ArrayList<String>() ;
        // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, items) ;


        // listview 생성 및 adapter 지정.
        ListView listview = (ListView) findViewById(R.id.listview1) ;
        listview.setAdapter(adapter) ;



        //데이터를 저장하게 되는 리스트
        List<String> list = new ArrayList<>();

        try {
            String result = "";

            JSONArray ja = new JSONArray(rv_message);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject order = ja.getJSONObject(i);

                boolean success = order.getBoolean("success");
                String active_no = order.getString("active_no");
                String active_money = Integer.toString(order.getInt("active_money"));
                String active_list = order.getString("active_list");

//                count = Integer.parseInt(active_no);


                if (success) {

                    // ===============================================================================================================================================
                    // 203.800 금액 3자리마다 , 찍고 '원' 붙이기
//                    int num;
//
//                    num = Integer.parseInt(active_money);
//
//                    DecimalFormat df = new DecimalFormat("#,###");
//                    active_money = df.format(num);
                    // ===============================================================================================================================================

                    String list_srt;

                    list_srt = "[" + active_no + "]-" + active_list + "-" +  active_money + "원";

                    //리스트뷰에 보여질 아이템을 추가
                    adapter.add(list_srt);


                } else { // 가져오기에 실패한 경우
                    Toast.makeText(getApplicationContext(), "착한일 목록 가져오기에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

            }

            count = adapter.getCount() ;    // 현재 ListView의 항목수 구하기

            String ts_n;
            int ti_n;

            ti_n = count + 1;

            ts_n = Integer.toString(ti_n);

            listno_str.setText(ts_n);
            edit_money.setText("");
            edit_active_name.setText("");

        } catch (
                JSONException e) {
            e.printStackTrace();
        }
    }




}

