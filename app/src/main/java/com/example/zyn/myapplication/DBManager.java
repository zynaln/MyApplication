package com.example.zyn.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ZYN on 2016/3/20.
 */
public class DBManager {

    private final int BUFFER_SIZE = 400000;
    public static final String DB_NAME = "test.db"; //保存的数据库文件名
    public static final String PACKAGE_NAME = "com.example.zyn.myapplication";
    public static final String DB_PATH = "/data"
        + Environment.getDataDirectory().getAbsolutePath() + "/"
        + PACKAGE_NAME;



    private SQLiteDatabase database;
    private Context context;

        DBManager(Context context) {
            this.context = context;
        }

        public void openDatabase() {
            this.database = this.openDatabase(DB_PATH + "/"+ DB_NAME);
        }

        private SQLiteDatabase openDatabase(String dbfile) {
            try {
                if (!(new File(dbfile).exists())) {
                //判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
                    InputStream is = this.context.getResources().openRawResource(
                            R.raw.data); //欲导入的数据库
                    FileOutputStream fos = new FileOutputStream(dbfile);
                    byte[] buffer = new byte[BUFFER_SIZE];
                    int count = 0;
                    while ((count = is.read(buffer)) > 0) {
                        fos.write(buffer, 0, count);
                    }
                    fos.close();
                    is.close();
                }
                SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile,
                        null);
                return db;
            } catch (FileNotFoundException e) {
                Log.e("Database", "未找到文件");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("Database", "输入输出异常");
                e.printStackTrace();
            }
            return null;
        }


//do something else here<br>

public void closeDatabase() {
        this.database.close();
        }
}

