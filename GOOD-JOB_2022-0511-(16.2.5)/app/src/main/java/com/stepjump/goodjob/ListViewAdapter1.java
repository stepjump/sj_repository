package com.stepjump.goodjob;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter1 extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem1> listViewItemList = new ArrayList<ListViewItem1>() ;

    // ListViewAdapter1의 생성자
    public ListViewAdapter1() {

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
            convertView = inflater.inflate(R.layout.listviewitem1, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView txt_date = (TextView) convertView.findViewById(R.id.txt_date);
        TextView txt_money = (TextView) convertView.findViewById(R.id.txt_money);
        TextView txt_end_yn = (TextView) convertView.findViewById(R.id.txt_end_yn);
        TextView txt_reg_time = (TextView) convertView.findViewById(R.id.txt_reg_time);
        TextView txt_active_list = (TextView) convertView.findViewById(R.id.txt_active_list);


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem1 listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        txt_date.setText(listViewItem.getTxt_date());
        txt_money.setText(listViewItem.getTxt_money());
        txt_end_yn.setText(listViewItem.getTxt_end_yn());
        txt_reg_time.setText(listViewItem.getTxt_reg_time());
        txt_active_list.setText(listViewItem.getTxt_active_list());

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
    public void addItem(String txt_date, String txt_money, String txt_end_yn, String txt_reg_time, String txt_active_list) {
        ListViewItem1 item = new ListViewItem1();

        item.setTxt_date(txt_date);
        item.setTxt_money(txt_money);
        item.setTxt_end_yn(txt_end_yn);
        item.setTxt_reg_time(txt_reg_time);
        item.setTxt_active_list(txt_active_list);

        listViewItemList.add(item);
    }
}