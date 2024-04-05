package com.websarva.wings.android.asyncsample;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;


public class stay_time_activity extends AppCompatActivity implements Runnable{
    private boolean mAttached;
    private Thread mThread;
    int time;
    boolean start;

    int result;
    int count;

    String wait_hour;
    String wait_min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("sight", "Sub onCreate() called.");
        super.onCreate(savedInstanceState);
        mAttached = true;
        mThread = new Thread(this);
        mThread.start();//スレッドとしてスタート
        setContentView(R.layout.activity_stay_time);
        ActionBar actionBar = getSupportActionBar();
        // アクションバーがnullではなかったら…
        if(actionBar != null) {
            // アクションバーの［戻る］メニューを有効に設定。
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public void onStart() {
        Log.i("sight", "Sub onStart() called.");
        super.onStart();
    }

    @Override
    public void onRestart() {
        Log.i("sight", "Sub onRestart() called.");
        super.onRestart();
    }

    @Override
    public void onResume() {
        Log.i("sight", "Sub onResume() called.");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i("sight", "Sub onPause() called.");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i("sight", "Sub onStop() called.");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.i("sight", "Sub onDestory() called.");
        super.onDestroy();
    }

    /**
     * ［前の画面を表示］ボタンがタップされた時の処理。
     */

    public void save(View view) {
        // 入力欄に入力されたキーワード文字列を取得。
        EditText hour = findViewById(R.id.hour);
        EditText min = findViewById(R.id.min);
        TextView ok = findViewById(R.id.textView3);
        wait_hour = hour.getText().toString();
        wait_min = min.getText().toString();
        int num_hour = Integer.valueOf(wait_hour);
        int num_min = Integer.valueOf(wait_min);
        Calendar calendar = Calendar.getInstance();

        int nowhour = calendar.get(Calendar.HOUR_OF_DAY); // 24時間形式
        int nowminute = calendar.get(Calendar.MINUTE);//分
        int nowsecon = calendar.get(Calendar.SECOND);//秒

        int hour_time = num_hour - nowhour;
        int min_time = num_min - nowminute;
        int result_hour;
        int result_min;
        if(hour_time >= 24){
            result_hour = hour_time - 24;
        }
        if(min_time >= 60){
            result_min = min_time - 60;
        }


        result_hour = hour_time*60*60;
        result_min = min_time*60;
        result = result_hour + result_min + time - 60*10 -nowsecon;
        count++;
        Log.i("time"+nowhour, "time_count");
        Log.i("time"+nowminute, "time_count");
        Log.i("time"+result_hour, "time_count");
        Log.i("time"+result_hour, "time_count");
        ok.setText(getString(R.string.ok));
    }
    @Override
    public void run() {
        while (mAttached) {
            try {
                Thread.sleep(1000);
                time++;
                Log.i("time"+time, "time_count");
                Log.i("result"+result, "result_count");
                if(count >= 1) {
                    if (time == result) {
                        Intent timescreen = new Intent(stay_time_activity.this, time_screen_activity.class);
                        // アクティビティを起動。
                        startActivity(timescreen);
                        mAttached = false;
                        // スレッド終了待ち
                        while (mThread.isAlive());
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // 戻り値用の変数を初期値trueで用意。
        boolean returnVal = true;
        // 選択されたメニューのIDを取得。
        int itemId = item.getItemId();
        // 選択されたメニューが［戻る］の場合、アクティビティを終了。
        if(itemId == android.R.id.home) {
            finish();
        }
        // それ以外…
        else {
            // 親クラスの同名メソッドを呼び出し、その戻り値をreturnValとする。
            returnVal = super.onOptionsItemSelected(item);
        }
        return returnVal;
    }
}