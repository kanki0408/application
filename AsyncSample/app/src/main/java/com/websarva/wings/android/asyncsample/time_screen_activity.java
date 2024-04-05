package com.websarva.wings.android.asyncsample;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;

public class time_screen_activity extends AppCompatActivity {
    private MediaPlayer _player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("sight", "Sub onCreate() called.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_screen);
        _player = new MediaPlayer();
        // 音声ファイルのURI文字列を作成。
        String mediaFileUriStr = "android.resource://" + getPackageName() + "/" + R.raw.mountain_stream;
        // 音声ファイルのURI文字列を元にURIオブジェクトを生成。
        Uri mediaFileUri = Uri.parse(mediaFileUriStr);
        try {
            _player.setDataSource(time_screen_activity.this, mediaFileUri);
            _player.setOnPreparedListener(new PlayerPreparedListener());
            _player.prepareAsync();
        } catch (IOException e) {
        }
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

    public void stop(View view) {
        _player.stop();
        _player.release();
    }

    private class PlayerPreparedListener implements MediaPlayer.OnPreparedListener {
        public void onPrepared(MediaPlayer mp){
            _player.start();
            Log.i("start", "start");
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