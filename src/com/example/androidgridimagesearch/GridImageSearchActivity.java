package com.example.androidgridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class GridImageSearchActivity extends Activity {

	protected static final String TAG = "GRIDIMAGESEARCH";
	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_grid_image_search);
		setupViews();
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		ImageItemClickListener listener = new ImageItemClickListener();
		gvResults.setOnItemClickListener(listener);
	}
	
	private class ImageItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> adapter, View parent, int position, long rowId) {
			Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
			ImageResult imageResult = imageResults.get(position);
			i.putExtra("result", imageResult);
			startActivity(i);
		}
		
	}

	private void setupViews() {
		etQuery = (EditText) findViewById(R.id.etSearchQuery);
		gvResults = (GridView) findViewById(R.id.gvImages);
		btnSearch = (Button) findViewById(R.id.btnSearch);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.grid_image_search, menu);
		return true;
	}

	public void searchImages(View v) {
		String query = etQuery.getText().toString();
		Toast.makeText(getApplicationContext(), "Searching for " + query + "..",
				Toast.LENGTH_SHORT).show();
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		String constructedQuery = "https://ajax.googleapis.com/ajax/services/search/images?rsz=8&start=" +
				0+"&v=1.0&q=" + Uri.encode(query);
		asyncHttpClient.get(constructedQuery
				,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject response) {
						JSONArray results = null;
						Log.i(TAG, "onsuccess!!");
						Log.i(TAG,"what's repsponse: "+response.toString());
						
						try {
							JSONObject responseData = response.getJSONObject("responseData");
							Log.i(TAG, "response data: "+responseData);
							results =  responseData.getJSONArray("results");
							Log.i(TAG, "results: "+results);
									
							imageResults.clear();
							imageAdapter.addAll(ImageResult.fromJSONArray(results));
							Log.i(TAG, imageResults.toString());
						} catch (JSONException e) {
							Log.e(TAG, e.getMessage());
							e.printStackTrace();
						}

					}
				});
//		asyncHttpClient.get("http://www.google.com", new AsyncHttpResponseHandler(){
			
//			@Override
//			public void onSuccess(String response){
//				System.out.println("hellow");
//				System.out.println(response);
//				Log.i(TAG, response);
//			}
//		});

	}

}
