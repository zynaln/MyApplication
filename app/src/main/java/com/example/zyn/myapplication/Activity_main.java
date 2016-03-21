package com.example.zyn.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ZYN on 2016/3/12.
 */
public class Activity_main extends Activity{
    GridView mGridView;
    AutoCompleteTextView actv;//MyGridView
    //定义图标数组
    private int[] imageRes = { R.drawable.pan1, R.drawable.pan1, R.drawable.pan1, R.drawable.pan1, R.drawable.pan1, R.drawable.pan1,
            R.drawable.pan1, R.drawable.pan1, R.drawable.pan1, R.drawable.pan1, R.drawable.pan1, R.drawable.pan1,
            R.drawable.pan1, R.drawable.pan1, R.drawable.pan1, R.drawable.pan1, R.drawable.pan1, R.drawable.pan1
    };

    private String[] itemName = { "文件1", "文件2", "文件3", "文件4", "文件5", "文件6",
            "文件7", "文件8", "文件9", "文件10", "文件11", "文件12",
            "文件13", "文件14", "文件15", "文件16", "文件17","文件18" };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        actv = (AutoCompleteTextView)findViewById(R.id.actv);
        actv.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        actv.getPaint().setAntiAlias(true);

        mGridView = (GridView) findViewById(R.id.MyGridView);
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        int length = itemName.length;
        for (int i = 0; i < length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImageView", imageRes[i]);
            map.put("ItemTextView", itemName[i]);
            data.add(map);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,itemName);
        actv.setAdapter(adapter);

        //为itme.xml添加适配器
        SimpleAdapter simpleAdapter = new SimpleAdapter(Activity_main.this,
                data, R.layout.main_item, new String[] { "ItemImageView","ItemTextView" }, new int[] { R.id.ItemImageView,R.id.ItemTextView });
        mGridView.setAdapter(simpleAdapter);
        //为mGridView添加点击事件监听器
        mGridView.setOnItemClickListener(new GridViewItemOnClick(){
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                Intent intent=new Intent(Activity_main.this,Activity_mainlist.class);
                startActivity(intent);
            }
        });
    }
    //定义点击事件监听器
    public class GridViewItemOnClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> arg0, View view, int position,long arg3) {
            Toast.makeText(getApplicationContext(), position + "",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
