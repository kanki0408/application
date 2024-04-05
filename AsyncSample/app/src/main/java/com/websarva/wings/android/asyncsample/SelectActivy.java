package com.websarva.wings.android.asyncsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Locale;

public class SelectActivy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("sight" , "Main onCreate() called.");
        //onCreateした時にログ上に表示するメッセージ
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_activy);
    }

    @Override
    public void onStart() {
        Log.i("sight", "Main onStart() called.");
        super.onStart();
    }

    @Override
    public void onRestart() {
        Log.i("sight", "Main onRestart() called.");
        super.onRestart();
    }

    @Override
    public void onResume() {
        Log.i("s", "Main onResume() called.");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i("sight", "Main onPause() called.");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i("sight", "Main onStop() called.");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.i("s", "Main onDestory() called.");
        super.onDestroy();
    }



    /**
     ボタンがタップされた時の処理。
     */
    public void weather(View view) {
        Intent weatherintent = new Intent(SelectActivy.this, MainActivity.class);
        // アクティビティを起動。
        startActivity(weatherintent);
    }

    public void wait(View view) {
        Intent waitintent = new Intent(SelectActivy.this, stayplaceActivity.class);
        // アクティビティを起動。
        startActivity(waitintent);
    }

    public void time(View view) {
        Intent langageintent = new Intent(SelectActivy.this, stay_time_activity.class);
        // アクティビティを起動。
        startActivity(langageintent);
    }

    public void langage(View view) {
        Intent timeintent = new Intent(SelectActivy.this, langage_setting_activity.class);
        // アクティビティを起動。
        startActivity(timeintent);
        finish();
    }
}