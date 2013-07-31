package com.root.testproject.activities;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.root.testproject.adapters.ExtAdapter;
import com.root.testproject.model.Data;

public class MainActivity extends SherlockActivity implements
		OnItemClickListener {

	private static final String LOG = "MyTestLogs";
	private static final String URL = "http://buyersguide.caranddriver.com/api/feed/?mode=json&q=make";
	private static final String ERROR_MESSAGE = "You have no internet connection";
	private static final String DATA = "data";

	private ListView mListView;
	private Intent defaultBrowserIntent;
	private RequestQueue queue;
	private ArrayList<Data> arrData;
	private ActionBar ab;
	private ProgressDialog mProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		createUI();
		queue = Volley.newRequestQueue(this);
		refreshDatas();
		actionBarSet(ab);
		mProgress = ProgressDialog.show(this, "", "Loading data");
	}

	public void refreshDatas() {
		JsonObjectRequest jsonRequet = new JsonObjectRequest(Method.GET, URL,
				null, new Listener<JSONObject>() {
					public void onResponse(JSONObject result) {
						try {
							parseJson(result);
							mProgress.dismiss();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(getApplicationContext(), ERROR_MESSAGE,
								Toast.LENGTH_LONG).show();
					}
				});

		queue.add(jsonRequet);
	}

	private void parseJson(JSONObject root) throws JSONException {

		JSONArray array = root.getJSONArray(DATA);
		Log.d(LOG, array.toString());
		Gson gson = new Gson();
		arrData = new ArrayList<Data>();
		for (int i = 0; i < array.length(); i++) {
			Data data = gson.fromJson(array.get(i).toString(), Data.class);
			arrData.add(data);
		}

		Collections.sort(arrData, new Data());
		for (int j = 0; j < arrData.size(); j++) {
			Log.d(LOG, arrData.get(j).name);
			Log.d(LOG, String.valueOf(arrData.get(j).id));
		}

		ExtAdapter adapter = new ExtAdapter(this, arrData);
		mListView.setAdapter(adapter);

	}

	private void createUI() {
		mListView = (ListView) findViewById(R.id.listView_cars_and_ids);
		mListView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> data, View view, int position,
			long id) {
		defaultBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(arrData
				.get(position).url));
		startActivity(defaultBrowserIntent);
	}
	
	
	private void actionBarSet(ActionBar ab) {
		ab = getSupportActionBar();
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			ab.hide();
		} else {
			ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
			ab.setCustomView(R.layout.action_bar_layout);
		}
	}
}
