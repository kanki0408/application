package com.websarva.wings.android.asyncsample;

import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 参考文献
 全てのアプリにおいて参考にしたもの
 Androidアプリ開発の教科書

 タイマーの設定方法
 Mysenser_2023

 現在の時刻情報を獲得する方法
 【Java入門】Calendarクラスで日付を操作する方法（getメソッド,setメソッド,addメソッド,rollメソッド) #Java - Qiita

 最高気温、最低気温の表示方法
 1.https://stackoverflow.com/questions/39022861/how-to-get-json-object-in-another-one-in-android
 2.https://qiita.com/key/items/aad73fd6057484f20731

 EditTextでヒント文字列を表示する方法
 EditTextで、ヒント文字列を設定する | mokelab tech sheets

 多言語化設定方法
 1.	https://developer.android.com/training/basics/supporting-devices/languages?hl=ja
 2.	https://developer.android.com/reference/java/util/Locale
 3.	https://developer.android.com/reference/android/content/res/Configuration
 4.	https://gunhansancar.com/change-language-programmatically-in-android/
 5.	https://dev.classmethod.jp/articles/android-app-localization/

 利用した効果音サイト
 フリーBGM（音楽素材）無料ダウンロード｜DOVA-SYNDROME

 */
public class MainActivity extends AppCompatActivity {
	/**
	 * ログに記載するタグ用の文字列。
	 */
	private static final String DEBUG_TAG = "AsyncSample";
	/**
	 * お天気情報のURL。
	 */
	private static final String WEATHERINFO_URL = "https://api.openweathermap.org/data/2.5/weather?lang=ja";
	/**
	 * お天気APIにアクセスすするためのAPIキー。
	 * ※※※※※この値は各自のものに書き換える!!※※※※※
	 */
	private static final String APP_ID = "69d3eb1f7df4727930cc1db2d3120afc";
	/**
	 * リストビューに表示させるリストデータ。
	 */
	private List<Map<String, String>> _list;

	// 都市名。
	String cityName = "";

	// 天気。
	String weather = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ActionBar actionBar = getSupportActionBar();
		// アクションバーがnullではなかったら…
		if(actionBar != null) {
			// アクションバーの［戻る］メニューを有効に設定。
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}






	/**
	 * お天気情報の取得処理を行うメソッド。
	 *
	 * @param urlFull お天気情報を取得するURL。
	 */
	@UiThread
	private void receiveWeatherInfo(final String urlFull) {
		WeatherInfoBackgroundReceiver backgroundReceiver = new WeatherInfoBackgroundReceiver(urlFull);
		ExecutorService executorService  = Executors.newSingleThreadExecutor();
		Future<String> future = executorService.submit(backgroundReceiver);
		String result = "";
		try {
			result = future.get();
		}
		catch(ExecutionException ex) {
			Log.w(DEBUG_TAG, "非同期処理結果の取得で例外発生: ", ex);
		}
		catch(InterruptedException ex) {
			Log.w(DEBUG_TAG, "非同期処理結果の取得で例外発生: ", ex);
		}
		showWeatherInfo(result);
	}

	/**
	 * 取得したお天気情報JSON文字列を解析の上、画面に表示させるメソッド。
	 *
	 * @param result 取得したお天気情報JSON文字列。
	 */
	@UiThread
	private void showWeatherInfo(String result) {

		double maxTemperature = 0;
		double minTemperature = 0;

		try {
			// ルートJSONオブジェクトを生成。
			JSONObject rootJSON = new JSONObject(result);
			// 都市名文字列を取得。
			cityName = rootJSON.getString("name");
			// 天気情報JSON配列オブジェクトを取得。
			JSONArray weatherJSONArray = rootJSON.getJSONArray("weather");
			// 現在の天気情報JSONオブジェクトを取得。
			JSONObject weatherJSON = weatherJSONArray.getJSONObject(0);
			// 現在の天気情報文字列を取得。
			weather = weatherJSON.getString("description");
			JSONObject mainObject = rootJSON.getJSONObject("main");
			maxTemperature = mainObject.getDouble("temp_max");
			//Kから℃に変換する
			maxTemperature -= 273;
			minTemperature = mainObject.getDouble("temp_min");
			minTemperature -= 273;
		}
		catch(JSONException ex) {
			Log.e(DEBUG_TAG, "JSON解析失敗", ex);
		}

		// 天気の詳細情報を表示する文字列を生成。
		int intmaxTemperature = (int) maxTemperature;
		int intminTemperature = (int) minTemperature;
		String strMaxTemperature2 = String.valueOf(intmaxTemperature);
		String strMinTemperature2 = String.valueOf(intminTemperature);

		// 天気情報を表示するTextViewを取得。

		TextView nowweather = findViewById(R.id.nowweather);
		TextView lowdegree = findViewById(R.id.lowdegree);
		TextView highdegree = findViewById(R.id.highdegree);
		TextView nowname = findViewById(R.id.nowname);
		TextView lowname= findViewById(R.id.lowname);
		TextView highname = findViewById(R.id.highname);
		// 天気情報を表示。
		strMinTemperature2 += "℃";
		strMaxTemperature2 += "℃";
		nowweather.setText(weather);
		String nweather = getString(R.string.nowweaher);
		nowname.setText(nweather);
		lowdegree.setText(strMinTemperature2);
		String lowweather = getString(R.string.lowweather);
		lowname.setText(lowweather);
		highdegree.setText(strMaxTemperature2);
		String highweather = getString(R.string.highweather);
		highname.setText(highweather);
	}

	/**
	 * 非同期でお天気情報APIにアクセスするためのクラス。
	 */
	private class WeatherInfoBackgroundReceiver implements Callable<String> {
		/**
		 * お天気情報を取得するURL。
		 */
		private final String _urlFull;

		/**
		 * コンストラクタ。
		 * 非同期でお天気情報Web APIにアクセスするのに必要な情報を取得する。
		 *
		 * @param urlFull お天気情報を取得するURL。
		 */
		public WeatherInfoBackgroundReceiver(String urlFull) {
			_urlFull = urlFull;
		}

		@WorkerThread
		@Override
		public String call() {
			// 天気情報サービスから取得したJSON文字列。天気情報が格納されている。
			String result = "";
			// HTTP接続を行うHttpURLConnectionオブジェクトを宣言。finallyで解放するためにtry外で宣言。
			HttpURLConnection con = null;
			// HTTP接続のレスポンスデータとして取得するInputStreamオブジェクトを宣言。同じくtry外で宣言。
			InputStream is = null;
			try {
				// URLオブジェクトを生成。
				URL url = new URL(_urlFull);
				// URLオブジェクトからHttpURLConnectionオブジェクトを取得。
				con = (HttpURLConnection) url.openConnection();
				// 接続に使ってもよい時間を設定。
				con.setConnectTimeout(1000);
				// データ取得に使ってもよい時間。
				con.setReadTimeout(1000);
				// HTTP接続メソッドをGETに設定。
				con.setRequestMethod("GET");
				// 接続。
				con.connect();
				// HttpURLConnectionオブジェクトからレスポンスデータを取得。
				is = con.getInputStream();
				// レスポンスデータであるInputStreamオブジェクトを文字列に変換。
				result = is2String(is);
			}
			catch(MalformedURLException ex) {
				Log.e(DEBUG_TAG, "URL変換失敗", ex);
			}
			// タイムアウトの場合の例外処理。
			catch(SocketTimeoutException ex) {
				Log.w(DEBUG_TAG, "通信タイムアウト", ex);
			}
			catch(IOException ex) {
				Log.e(DEBUG_TAG, "通信失敗", ex);
			}
			finally {
				// HttpURLConnectionオブジェクトがnullでないなら解放。
				if(con != null) {
					con.disconnect();
				}
				// InputStreamオブジェクトがnullでないなら解放。
				if(is != null) {
					try {
						is.close();
					}
					catch(IOException ex) {
						Log.e(DEBUG_TAG, "InputStream解放失敗", ex);
					}
				}
			}
			return result;
		}

		/**
		 * InputStreamオブジェクトを文字列に変換するメソッド。 変換文字コードはUTF-8。
		 *
		 * @param is 変換対象のInputStreamオブジェクト。
		 * @return 変換された文字列。
		 * @throws IOException 変換に失敗した時に発生。
		 */
		private String is2String(InputStream is) throws IOException {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
			StringBuffer sb = new StringBuffer();
			char[] b = new char[1024];
			int line;
			while(0 <= (line = reader.read(b))) {
				sb.append(b, 0, line);
			}
			return sb.toString();
		}
	}

	/**
	 * リストがタップされた時の処理が記述されたリスナクラス。
	 */

	public void serch(View view) {
		// 入力欄に入力されたキーワード文字列を取得。
		EditText serchweather = findViewById(R.id.weathereditText);
		String cityName = serchweather.getText().toString();
		String urlFull = WEATHERINFO_URL + "&q=" + cityName + "&appid=" + APP_ID;

		receiveWeatherInfo(urlFull);
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

	public void point(View view){
		try {
			if(cityName != null){
				String viewpoint = getString(R.string.viewpoint);
				// 入力されたキーワードをURLエンコード。
				cityName = URLEncoder.encode(cityName, "UTF-8");
				// マップアプリと連携するURI文字列を生成。
				String uriStr = "geo:0,0?q=" + cityName + weather+viewpoint;
				// URI文字列からURIオブジェクトを生成。
				Uri uri = Uri.parse(uriStr);
				// Intentオブジェクトを生成。
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				// アクティビティを起動。
				startActivity(intent);
			}
		}
		catch(UnsupportedEncodingException ex) {
			Log.e("stayplaceActivity", "検索キーワード変換失敗", ex);
		}		
	}

	public void food(View view){
		try {
			if(cityName != null){
				String shop = getString(R.string.shop);
				// 入力されたキーワードをURLエンコード。
				cityName = URLEncoder.encode(cityName, "UTF-8");
				// マップアプリと連携するURI文字列を生成。
				String uriStr = "geo:0,0?q=" + cityName +shop;
				// URI文字列からURIオブジェクトを生成。
				Uri uri = Uri.parse(uriStr);
				// Intentオブジェクトを生成。
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				// アクティビティを起動。
				startActivity(intent);
			}
		}
		catch(UnsupportedEncodingException ex) {
			Log.e("stayplaceActivity", "検索キーワード変換失敗", ex);
		}
	}

}
