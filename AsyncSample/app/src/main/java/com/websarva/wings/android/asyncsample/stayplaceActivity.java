package com.websarva.wings.android.asyncsample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class stayplaceActivity extends AppCompatActivity {


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
    public void onStop() {
        Log.i("sight", "Sub onStop() called.");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.i("sight", "Sub onDestory() called.");
        super.onDestroy();
    }

    String searchWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stayplace);

        ActionBar actionBar = getSupportActionBar();
        // アクションバーがnullではなかったら…
        if(actionBar != null) {
            // アクションバーの［戻る］メニューを有効に設定。
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }




    /**
     * リストがタップされたときの処理が記述されたメンバクラス。
     */


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 地図検索ボタンがタップされたときの処理メソッド。
     */
    public void searchplace(View view) {
        // 入力欄に入力されたキーワード文字列を取得。
        EditText etSearchWord = findViewById(R.id.placename);
        searchWord = etSearchWord.getText().toString();

        try {
            // 入力されたキーワードをURLエンコード。
            searchWord = URLEncoder.encode(searchWord, "UTF-8");
            // マップアプリと連携するURI文字列を生成。
            String uriStr = "geo:0,0?q=" + searchWord;
            // URI文字列からURIオブジェクトを生成。
            Uri uri = Uri.parse(uriStr);
            // Intentオブジェクトを生成。
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            // アクティビティを起動。
            startActivity(intent);
        }
        catch(UnsupportedEncodingException ex) {
            Log.e("stayplaceActivity", "検索キーワード変換失敗", ex);
        }
    }

    /**
     * 現在地の地図表示ボタンがタップされたときの処理メソッド。
     */

    /**
     * 位置情報が変更された時の処理を行うコールバッククラス。
     */

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