package cn.itcast.notepad;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;


import java.util.List;

import cn.itcast.notepad.adapter.NotepadAdapter;
import cn.itcast.notepad.bean.NotepadBean;
import cn.itcast.notepad.database.SQLiteHelper;

public class NotepadActivity extends AppCompatActivity {
    ListView listView;
    List<NotepadBean> list;
    SQLiteHelper mSQLiteHelper;
    NotepadAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);
        //用于显示记录的列表
        listView = (ListView) findViewById(R.id.listview);
        ImageView add = (ImageView) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotepadActivity.this,RecordActivity.class);
                startActivityForResult(intent,1);
            }
        });
        initData();
    }
    protected void initData() {
        mSQLiteHelper = new SQLiteHelper(this);
        showQueryData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NotepadBean notepadBean = list.get(position);
                Intent intent = new Intent(NotepadActivity.this, RecordActivity.class);
                intent.putExtra("id", notepadBean.getId());
                intent.putExtra("time", notepadBean.getNotepadTime());
                intent.putExtra("content", notepadBean.getNotepadContent());
                NotepadActivity.this.startActivityForResult(intent, 1);
            }
        });
    }
    private void showQueryData(){
        if (list!=null){
            list.clear();
        }
        list = mSQLiteHelper.query();
        adapter = new NotepadAdapter(this,list);
        listView.setAdapter(adapter);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode==1&&resultCode==2){
            showQueryData();
        }
    }


}
