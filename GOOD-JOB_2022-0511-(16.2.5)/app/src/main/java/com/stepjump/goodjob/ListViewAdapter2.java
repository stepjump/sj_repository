package com.stepjump.goodjob;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter2 extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem2> listViewItemList = new ArrayList<ListViewItem2>() ;

    // ListViewAdapter1의 생성자
    public ListViewAdapter2() {

    }





    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listviewitem2, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView txt_date = (TextView) convertView.findViewById(R.id.txt_date);
        TextView txt_temp1 = (TextView) convertView.findViewById(R.id.txt_temp1);
        TextView txt_temp2 = (TextView) convertView.findViewById(R.id.txt_temp2);
        TextView txt_temp3 = (TextView) convertView.findViewById(R.id.txt_temp3);
        TextView txt_height = (TextView) convertView.findViewById(R.id.txt_height);
        TextView txt_weight = (TextView) convertView.findViewById(R.id.txt_weight);
        TextView txt_etc_comment = (TextView) convertView.findViewById(R.id.txt_etc_comment);


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem2 listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        txt_date.setText(listViewItem.getTxt_date());
        txt_temp1.setText(listViewItem.getTxt_temp1());
        txt_temp2.setText(listViewItem.getTxt_temp2());
        txt_temp3.setText(listViewItem.getTxt_temp3());
        txt_height.setText(listViewItem.getTxt_height());
        txt_weight.setText(listViewItem.getTxt_weight());
        txt_etc_comment.setText(listViewItem.getTxt_etc_comment());










        // ==================================================================================
        // 열온도 문자 색상지정

//        txt_temp1.setTextColor(Color.parseColor("#4CAF50"));
//        txt_temp2.setTextColor(Color.parseColor("#4CAF50"));
//        txt_temp3.setTextColor(Color.parseColor("#4CAF50"));


        Double temp1, temp2, temp3;

//        if (txt_temp1.getText().toString() != null)
//        {
//            temp1 = Double.parseDouble(txt_temp1.getText().toString());
//        } else {
//            temp1 = 0.0;
//        }

        //String test_txt = txt_temp1.getText().toString();


        if ( (txt_temp1.getText().toString() != null) && !(txt_temp1.getText().toString().equals("")) )
        {
            temp1 = Double.parseDouble(txt_temp1.getText().toString());
        } else {
            temp1 = 0.0;
        }


        if ( (txt_temp2.getText().toString() != null) && !(txt_temp2.getText().toString().equals("")) )
        {
            temp2 = Double.parseDouble(txt_temp2.getText().toString());
        } else {
            temp2 = 0.0;
        }

        if ( (txt_temp3.getText().toString() != null) && !(txt_temp3.getText().toString().equals("")) )
        {
            temp3 = Double.parseDouble(txt_temp3.getText().toString());
        } else {
            temp3 = 0.0;
        }


        // 아침온도
        if (temp1 >= 37.3)
        {
            txt_temp1.setTextColor(Color.parseColor("#F44336"));
        }
        else if ( (temp1 >= 37.0) && (temp1 <= 37.2) )
        {
            txt_temp1.setTextColor(Color.parseColor("#FF9800"));
        }
        else
        {
            txt_temp1.setTextColor(Color.parseColor("#4CAF50"));
        }

        // 저녁온도
        if (temp2 >= 37.3)
        {
            txt_temp2.setTextColor(Color.parseColor("#F44336"));
        }
        else if ( (temp2 >= 37.0) && (temp2 <= 37.2) )
        {
            txt_temp2.setTextColor(Color.parseColor("#FF9800"));
        }
        else
        {
            txt_temp2.setTextColor(Color.parseColor("#4CAF50"));
        }

        // 밤온도
        if (temp3 >= 37.3)
        {
            txt_temp3.setTextColor(Color.parseColor("#F44336"));
        }
        else if ( (temp3 >= 37.0) && (temp3 <= 37.2) )
        {
            txt_temp3.setTextColor(Color.parseColor("#FF9800"));
        }
        else
        {
            txt_temp3.setTextColor(Color.parseColor("#4CAF50"));
        }
//         ==================================================================================



        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String txt_date, String txt_temp1, String txt_temp2, String txt_temp3, String txt_height, String txt_weight, String txt_etc_comment) {
        ListViewItem2 item = new ListViewItem2();

        item.setTxt_date(txt_date);
        item.setTxt_temp1(txt_temp1);
        item.setTxt_temp2(txt_temp2);
        item.setTxt_temp3(txt_temp3);
        item.setTxt_height(txt_height);
        item.setTxt_weight(txt_weight);
        item.setTxt_etc_comment(txt_etc_comment);


        listViewItemList.add(item);
    }
}