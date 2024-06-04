package com.stepjump.goodjob;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

        String active_title = "";   // 착한일 목록
        int today_money = 0;    // 하루 전체 수입금액

        String user_admin_pass;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_list);


            // =====================================================================================
            // 모든 페이지에서 사용할 수 있도록 LoginActivity.java 에서 저장한 로그인 정보(id) 불러오기
            SharedPreferences pref = getSharedPreferences("login_info",MODE_PRIVATE);
//            rv_id_temp = pref.getString("user_id","");
//            rv_name_temp = pref.getString("user_name","");
//            rv_userComment = pref.getString("user_comment","");
            user_admin_pass = pref.getString("user_admin_pass","");
            // =====================================================================================
//            tv_id_temp.setText(rv_id_temp.replace("null", "") );
//            tv_name_temp.setText(rv_name_temp.replace("null", "") );
            // ========================================================================================================1


//            // xml 에서 데이터 읽어서 ListView 에 띄우기
//            //    1. 다량의 데이터 (xml)
//            //    2. AdapterView
//            //    3. Adapter 선택 (ListView)
//
//            ArrayAdapter adapter =
//                    ArrayAdapter.createFromResource(
//                            getApplicationContext(), // 현재 화면의 제어권자
//                            R.array.data, // xml 에 작성한 array 항목을 지정
//                            android.R.layout.simple_list_item_multiple_choice); //row layout 지정,
//            // 안드로이드에 이미 지정된 리소스 사용시 android.R  사용.
//
//            final ListView lv = (ListView)findViewById(R.id.listview1);
//
//            lv.setAdapter(adapter);


            // MainActivity 에서 넘어온 착한일 수입내역 항목으로 ListView 항목값 선택하기
            Intent intent = getIntent(); /*데이터 수신*/
            String rv_active_list = intent.getExtras().getString("active_list");    // MainActivity 에서 넘어온값 - 착한일 수입내역 항목
            String rv_snd_message = intent.getExtras().getString("snd_message");    // MainActivity 에서 넘어온값 - 착한일 수입내역 DB에서 넘어온 착한일 관리메뉴 수입내역리스트 항목
//            Toast.makeText(getApplicationContext(), rv_active_list, Toast.LENGTH_SHORT).show();






            // =======================================================================================================================

            // 빈 데이터 리스트 생성.
            ArrayList<String> items = new ArrayList<String>() ;
            // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, items) ;


            // listview 생성 및 adapter 지정.
            final ListView lv = (ListView) findViewById(R.id.listview1) ;
            lv.setAdapter(adapter) ;



            // DB에서 착한일 수입내역 리스트를 가져오기
            List<String> list = new ArrayList<>();

            try {
                String result = "";

                JSONArray ja = new JSONArray(rv_snd_message);
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject order = ja.getJSONObject(i);

                    boolean success = order.getBoolean("success");
                    String active_no = order.getString("active_no");
                    String active_money = Integer.toString(order.getInt("active_money"));
                    String active_list = order.getString("active_list");

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

            } catch (
                    JSONException e) {
                e.printStackTrace();
            }

            // =======================================================================================================================






            //리스트뷰에서 선택된 아이템의 목록을 가져온다.
            SparseBooleanArray checkedItemPositions = lv.getCheckedItemPositions();
            int count = adapter.getCount() ;

            String s_key = "";

            // MainActivity 에서 넘어온값 - 착한일 수입내역 항목별로  ListView 체크하기
            for( int i=0; i< count; i++){

                s_key = "[" + Integer.toString(i+1) + "]-";      // 검색할 문자   (예) 1-, 2-, 3-, 4-, 5- ......... 17-, 18-

//                Toast.makeText(getApplicationContext(), s_key, Toast.LENGTH_SHORT).show();

                if (rv_active_list.contains(s_key)) {
                    lv.setItemChecked(i, true);
                }
                else {
                    lv.setItemChecked(i, false);
                }

            }

            adapter.notifyDataSetChanged();


            // ADMIN 페이지로 가기 버튼 클릭
            Button btn_addlist = (Button)findViewById(R.id.btn_addlist);

            btn_addlist.setOnClickListener(new View.OnClickListener()    {     // admin 페이지로 가기로 가기 위한 암호입력 페이지로 가기

                @Override
                public void onClick(View v) {

                    try {
                        // 관리자 비밀번호와 입력한 비밀번호 같다면 관리자 페이지로 이동
                        // 관리자 비밀번호 ===> user_admin_pass
                        AlertDialog.Builder adminpage = new AlertDialog.Builder(ListActivity.this);

                        adminpage.setTitle("관리자 페이지로 이동");
                        adminpage.setMessage("암호를 입력해주세요~!!");

                        final EditText adminpage_password = new EditText(ListActivity.this);
                        adminpage.setView(adminpage_password);

                        adminpage.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                if (adminpage_password.getText().toString().equals(user_admin_pass)) {
                                    Intent intent = new Intent(ListActivity.this,  AdminActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(ListActivity.this, "암호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
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




            // 착한일 추가하기 버튼 클릭
            Button btn_add = (Button)findViewById(R.id.btn_add);

            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final List<String> selectedItems = new ArrayList<>();

                    today_money = 0;
                    active_title = "";

                    //리스트뷰에서 선택된 아이템의 목록을 가져온다.
                    SparseBooleanArray checkedItemPositions = lv.getCheckedItemPositions();
                    for( int i=0; i<checkedItemPositions.size(); i++){
                        int pos = checkedItemPositions.keyAt(i);

                        if (checkedItemPositions.valueAt(i))
                        {
                            selectedItems.add(lv.getItemAtPosition(pos).toString());

                            String[] result = lv.getItemAtPosition(pos).toString().trim().split("-");   // 착한일 내용중 수입금액 구하기위해 금액부분 자름
                            int item_money = Integer.parseInt(result[2].replace("원",""));

                            today_money = today_money + item_money;

                            if (active_title == "")
                            {
                                active_title = "  " + result[0] + "-" + result[1] + "-" + result[2];;
                            }
                            else
                            {

                                // 착한일 3개마다 줄바꾸기
                                if (i % 2 == 0) {
                                    active_title = active_title + "\n" + "  " + result[0] + "-" + result[1] + "-" + result[2];
                                }
                                else
                                {
                                    active_title = active_title +  "  " + result[0] + "-" + result[1] + "-" + result[2];
                                }
                            }

                        }
                    }

                    Intent data = new Intent();
                    data.putExtra("today_money", String.valueOf(today_money));
                    data.putExtra("active_title", active_title);    // 착한일 목록
                    setResult(1, data);


                    finish();


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

