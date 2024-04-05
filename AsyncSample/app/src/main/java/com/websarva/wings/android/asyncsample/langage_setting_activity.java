package com.websarva.wings.android.asyncsample;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.widget.Toast;

import java.util.Locale;

public class langage_setting_activity extends AppCompatActivity {
    String item;
    int num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("sight", "Sub onCreate() called.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_langage_setting);

        // ListViewオブジェクトを取得。
        ListView lvMenu = findViewById(R.id.langagelist);
        // ListViewにリスナを設定。
        lvMenu.setOnItemClickListener(new ListItemClickListener());
        // アクションバーを取得。
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
     * リストがタップされたときの処理が記述されたメンバクラス。
     */
    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // タップされた定食名を取得。
            item = (String) parent.getItemAtPosition(position);
            Log.i("postion"+position, "Sub onRestart() called.");
            num = position;
            // トーストで表示する文字列を生成。
            String show = item +getString(R.string.select);
            Toast.makeText(langage_setting_activity.this , show , Toast.LENGTH_LONG).show();
        }
    }

    public void langagesetting(View view) {
        String id;
        Log.i(item, "Sub onRestart() called.");
        switch (num) {
            case 0:
                id = "ja";
                setLocale(id);
                break;
            case 1:
                id = "zh";
                setLocale(id);
                break;
            case 2:
                id = "ko";
                setLocale(id);
                break;
            case 3:
                id = "fr";
                setLocale(id);
                break;
            case 4:
                id = "ar";
                setLocale(id);
                break;
            case 5:
                id = "de";
                setLocale(id);
                break;
            case 6:
                id = "it";
                setLocale(id);
                break;
            case 7:
                id = "en";
                setLocale(id);
                break;
        }
        recreate();
        TextView langageset = findViewById(R.id.langageset);
        String a = getString(R.string.langageset);
        String b = item + a;
        langageset.setText(b);
    }

    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Configuration configuration = new Configuration();
        configuration.setLocale(locale);

        Context context = getBaseContext();
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // 戻り値用の変数を初期値trueで用意。
        boolean returnVal = true;
        // 選択されたメニューのIDを取得。
        int itemId = item.getItemId();
        // 選択されたメニューが［戻る］の場合、アクティビティを終了。
        if(itemId == android.R.id.home) {
            Intent timeintent = new Intent(langage_setting_activity.this, SelectActivy.class);
            startActivity(timeintent);
        }
        // それ以外…
        else {
            // 親クラスの同名メソッドを呼び出し、その戻り値をreturnValとする。
            returnVal = super.onOptionsItemSelected(item);
        }
        return returnVal;
    }
}
