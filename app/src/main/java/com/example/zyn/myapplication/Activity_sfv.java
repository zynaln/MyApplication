package com.example.zyn.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Administrator on 2016/3/14.
 */
public class Activity_sfv extends Activity{
    private EditText filenamEditText;
    private PopupWindow popupWindow;
    private MediaPlayer mediaPlayer;
    private String filename= "test.avi";
    private SurfaceView surfaceView;
    private final static String TAG="VodeoPlayActivity";
    private int prosition=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_sfv);

        surfaceView=(SurfaceView)this.findViewById(R.id.surfaceview);
        surfaceView.getHolder().setFixedSize(176, 144);//设置分辨率
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//设置surfaceview不维护自己的缓冲区，而是等待屏幕的渲染引擎将内容推送到用户面前
        surfaceView.getHolder().addCallback(new SurceCallBack());//对surface对象的状态进行监听
        mediaPlayer=new MediaPlayer();

        ButtonOnClikListiner buttonOnClikListinero=new ButtonOnClikListiner();
        Button btmenu=(Button) this.findViewById(R.id.menu);
        Button bttext=(Button) this.findViewById(R.id.text);
        Button start=(Button) this.findViewById(R.id.play);
        Button pause=(Button) this.findViewById(R.id.pause);
        Button stop=(Button) this.findViewById(R.id.stop);
        Button replay=(Button) this.findViewById(R.id.reset);
        start.setOnClickListener(buttonOnClikListinero);
        pause.setOnClickListener(buttonOnClikListinero);
        stop.setOnClickListener(buttonOnClikListinero);
        replay.setOnClickListener(buttonOnClikListinero);

        btmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View  Menu =getLayoutInflater().inflate(R.layout.surfaceview_liem,null,false);
                popupWindow=new PopupWindow(Menu,550, ActionBar.LayoutParams.MATCH_PARENT,true);
                popupWindow.setAnimationStyle(R.style.AnimationFade);
                popupWindow.showAtLocation(v, Gravity.LEFT, 0, 0);

                // 点击其他地方消失
                Menu.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // TODO Auto-generated method stub
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                            popupWindow = null;

                        }
                        return false;
                    }
                });
            }
        });
        bttext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Activity_sfv.this,Activity_text.class);
                startActivity(intent);
            }
        });
    }

   @Override
    protected void onResume() {
        /**
         * 设置为横屏
*/
        if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }

    private final class ButtonOnClikListiner implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(Environment.getExternalStorageState()==Environment.MEDIA_UNMOUNTED){
                Toast.makeText(Activity_sfv.this, "sd卡不存在", Toast.LENGTH_SHORT).show();
                return;
            }
            switch (v.getId()) {
                case R.id.play:
                    play();
                    break;
                case R.id.pause:
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                    }else{
                        mediaPlayer.start();
                    }
                    break;
                case R.id.reset:
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.seekTo(0);
                    }else{
                        play();
                    }
                    break;
                case R.id.stop:
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.stop();
                    }
                    break;
            }
        }
    }

    private void play() {
        try {
            File file=new File(Environment.getExternalStorageDirectory(),filename);
            mediaPlayer.reset();//重置为初始状态
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);//设置音乐流的类型
            mediaPlayer.setDisplay(surfaceView.getHolder());//设置video影片以surfaceviewholder播放
            mediaPlayer.setDataSource(file.getAbsolutePath());//设置路径
            mediaPlayer.prepare();//缓冲
            mediaPlayer.start();//播放
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
        }
    }

    private final class SurceCallBack implements SurfaceHolder.Callback{
        /**
         * 画面修改
         */
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            // TODO Auto-generated method stub

        }

        /**
         * 画面创建
         */
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if(prosition>0&&filename!=null){
                play();
                mediaPlayer.seekTo(prosition);
                prosition=0;
            }

        }

        /**
         * 画面销毁
         */
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if(mediaPlayer.isPlaying()){
                prosition=mediaPlayer.getCurrentPosition();
                mediaPlayer.stop();
            }
        }
    }
}