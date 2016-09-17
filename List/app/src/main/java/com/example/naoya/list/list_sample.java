package com.example.naoya.list;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class list_sample extends ListActivity {

    private TextView title1;

    int []TeamId = {R.string.school1,R.string.school2,R.string.school3,R.string.school4,R.string.school5};
    int []ResultId = {R.string.result1,R.string.result2,R.string.result3,R.string.result4,R.string.result5};
    int []NoteId = {R.string.note1,R.string.note2,R.string.note3,R.string.note4,R.string.note5};
    int []DateId = {R.integer.date1,R.integer.date2,R.integer.date3,R.integer.date4,R.integer.date5};

    List<Data> dataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sample);

//ExpandAbleListViewの処理
        title1  =(TextView)findViewById(R.id.title1);
        ExpandableListView view = (ExpandableListView) findViewById(R.id.exlist);

        ArrayList<HashMap<String, String>> groupData = new ArrayList<HashMap<String, String>>();
        String parentName = "parent";
        // 子リスト
        ArrayList<ArrayList<HashMap<String, String>>> childData = new ArrayList<ArrayList<HashMap<String, String>>>();
        String childName = "child";

        //array.xmlのparentをint型からString型に変換する
        final int[] i_Parent = getResources().getIntArray(R.array.parent);
        String[] Parent = new String[i_Parent.length];

        for(int i=0;i<Parent.length;i++) {
            Parent[i]=String.valueOf(i_Parent[i])+"年度";
        }

        int size = Parent.length;
        for (int i = 0; i < size; i++) {
            HashMap<String, String> group = new HashMap<String, String>();
            String item = Parent[i];
            group.put(parentName, item);
            groupData.add(group);

            String childId = "child_" + i;
            int arrayId = getResources().getIdentifier(childId, "array", getPackageName());
            String[] child = getResources().getStringArray(arrayId);
            ArrayList<HashMap<String, String>> childList = new ArrayList<HashMap<String, String>>();
            for (String childItem : child) {
                HashMap<String, String> childHash = new HashMap<String, String>();
                childHash.put(parentName, item);
                childHash.put(childName, childItem+"月");
                childList.add(childHash);
            }
            childData.add(childList);
        }

        // 親リスト、子リストを含んだAdapterを生成
        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this, groupData,
                android.R.layout.simple_expandable_list_item_1,
                new String[]{parentName}, new int[]{android.R.id.text1},
                childData, android.R.layout.simple_expandable_list_item_1,
                new String[]{childName}, new int[]{
                android.R.id.text1});

        // ExpandableListViewにAdapterをセット
        view.setAdapter(adapter);
        //子がクリックされる時の処理
        view.setOnChildClickListener(new  ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                /*****ここにクリックされた時の処理を書く*****/
                //title1.setText(String.valueOf(i_Parent[groupPosition])+" "+String.valueOf(childPosition));
                //↑確認用、日付のところに表示される
                dataList.clear();
                int month=0;
                switch(childPosition) {
                    case 0:
                        month=4;
                        break;
                    case 1:
                        month=5;
                        break;
                    case 2:
                        month=6;
                        break;
                    case 3:
                        month=7;
                        break;
                    case 4:
                        month=8;
                        break;
                    case 5:
                        month=9;
                        break;
                    case 6:
                        month=10;
                        break;
                    case 7:
                        month=11;
                        break;
                    case 8:
                        month=12;
                        break;
                    case 9:
                        month=1;
                        break;
                    case 10:
                        month=2;
                        break;
                    case 11:
                        month=3;
                        break;

                }
                setAllData(i_Parent[groupPosition],month*100);
                return false;
            }
        });
    }

    void setAllData(int year,int m) {
        // データをstring.xmlから持ってくる
        for (int i = 0; i < TeamId.length; i++) {
            Data data = new Data();
            int day = getResources().getInteger(DateId[i]);
            if(day%10000>m&&day%10000<m+100&&day/10000==year){
                data.setDateData(day);
                data.setStringData(getResources().getString(TeamId[i]) , getResources().getString(ResultId[i]) , getResources().getString(NoteId[i]));
                dataList.add(data);
            }
        }

        // リストにサンプル用のデータを受け渡す
        ListAdapter adapter = new ListAdapter(this, dataList);
        setListAdapter(adapter);
    }
//LIstViewが押された時の処理
    @Override
    protected void onListItemClick(ListView listview, View view, int position,long id) {

        // ここに処理を記述します。
        Intent intent = new Intent();
        intent.setClassName("com.example.naoya.list","com.example.naoya.list.Activity_sub1");
        startActivity(intent);
        //title1.setText(String.valueOf(position));

    }
}

// データ格納用クラス
class Data {
    private int DateData;       //日付用
    private String teamData;    //文字用
    private String resultData;
    private String noteData;

    public void setDateData(int tmp) {
        this.DateData = tmp%100;
    }

    public int getDateData() {
        return DateData;
    }

    public void setStringData(String team, String result, String note) {
        this.teamData = team;
        this.resultData = result;
        this.noteData = note;
    }

    public String getTeamData() {
        return teamData;
    }

    public String getResultData() {
        return resultData;
    }

    public String getNoteData() {
        return noteData;
    }

}

// リスト表示制御用クラス
class ListAdapter extends ArrayAdapter<Data> {
    private LayoutInflater inflater;
    // values/colors.xmlより設定値を取得するために利用。
    private Resources r;

    public ListAdapter(Context context, List<Data> objects) {
        super(context, 0, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        r = context.getResources();
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        // layout/raw.xmlを紐付ける
        if (view == null) {
            view = inflater.inflate(R.layout.raw, parent, false);
        }
        final Data data = this.getItem(position);
        TextView tvData1 = (TextView) view.findViewById(R.id.raw1);
        TextView tvData2 = (TextView) view.findViewById(R.id.raw2);
        TextView tvData3 = (TextView) view.findViewById(R.id.raw3);
        TextView tvData4 = (TextView) view.findViewById(R.id.raw4);
        if (data != null) {
            //1列目は日付データとしてフォーマット変更の上、表示
            tvData1.setText(String.valueOf(data.getDateData()));
            //2,3,4列目は文字列なのでそのまま表示
            tvData2.setText(data.getTeamData());
            tvData3.setText(data.getResultData());
            tvData4.setText(data.getNoteData());
        }
        //偶数行の場合の背景色を設定
        if (position % 2 == 0) {
            tvData1.setBackgroundColor(r.getColor(R.color.data1));
            tvData2.setBackgroundColor(r.getColor(R.color.data1));
            tvData3.setBackgroundColor(r.getColor(R.color.data1));
            tvData4.setBackgroundColor(r.getColor(R.color.data1));
        }
        //奇数行の場合の背景色を設定
        else {
            tvData1.setBackgroundColor(r.getColor(R.color.data2));
            tvData2.setBackgroundColor(r.getColor(R.color.data2));
            tvData3.setBackgroundColor(r.getColor(R.color.data2));
            tvData4.setBackgroundColor(r.getColor(R.color.data2));
        }
        return view;
    }
}
