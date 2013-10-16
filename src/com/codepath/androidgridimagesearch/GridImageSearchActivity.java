package com.codepath.androidgridimagesearch;

import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.androidgridimagesearch.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class GridImageSearchActivity extends Activity {

	protected static final String TAG = "GRIDIMAGESEARCH";
	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	Settings settings;
	int page = 0;

	private static final int REQUEST_CODE = 100;

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

	private class ImageItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> adapter, View parent, int position,
				long rowId) {
			Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
			ImageResult imageResult = imageResults.get(position);
			i.putExtra("result", imageResult);
			startActivity(i);
		}

	}

	public void onSettingsAction(MenuItem mi) {
		Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
		if (settings != null) {

			i.putExtra("settings", settings);
		}
		startActivityForResult(i, REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				Toast.makeText(getApplicationContext(),
						"returned from settings activity", Toast.LENGTH_SHORT).show();
				settings = (Settings) data.getSerializableExtra("settings");
				Log.d(TAG, "In on activity result, settings is: " + settings);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
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
		System.out.println("sysouting");
		Log.i(TAG, "HOLA NEHA");
		String query = etQuery.getText().toString();
		Toast.makeText(getApplicationContext(), "Searching for " + query + "..",
				Toast.LENGTH_SHORT).show();
		
		String constructedQuery = constructQuery(query, 0);

		Log.i(TAG, constructedQuery.toString());
		imageResults.clear();
		makeRequest(constructedQuery.toString());
		gvResults.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				Toast.makeText(
						getApplicationContext(),
						"on loading more page : " + page + " totalItems : "
								+ totalItemsCount, Toast.LENGTH_SHORT).show();
				String query = ((EditText) findViewById(R.id.etSearchQuery)).getText()
						.toString();
				String constructedQuery = constructQuery(query, page);
				makeRequest(constructedQuery.toString());
			}
		});
	}

	public void makeRequest(String query) {
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		asyncHttpClient.get(query, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				JSONArray results = null;
				Log.i(TAG, "onsuccess!!");

				try {
					JSONObject responseData = response.getJSONObject("responseData");
					results = responseData.getJSONArray("results");

					imageAdapter.addAll(ImageResult.fromJSONArray(results));
					Log.i(TAG, imageResults.toString());
				} catch (JSONException e) {
					Log.e(TAG, e.getMessage());
					e.printStackTrace();
				}

			}
		});
	}

	public String constructQuery(String query, int page) {
		StringBuffer constructedQuery = new StringBuffer(
				"https://ajax.googleapis.com/ajax/services/search/images?rsz=8&start=");

		constructedQuery.append(page);
		constructedQuery.append("&v=1.0&q=");
		constructedQuery.append(Uri.encode(query));

		// get advanced search options if they exist
		if (settings != null) {
			constructedQuery = getRemainingConstructedQuery(constructedQuery);
		}
		return constructedQuery.toString();
	}

	private StringBuffer getRemainingConstructedQuery(
			StringBuffer constructedQuery) {
		if (!settings.getImageSize().equalsIgnoreCase("All")) {
			constructedQuery.append("&imgsz=");
			String imageSize = settings.getImageSize();
			if (imageSize.equalsIgnoreCase("small")) {
				constructedQuery.append("icon");
			} else if (imageSize.equalsIgnoreCase("medium")) {
				constructedQuery.append("large");
			} else if (imageSize.equalsIgnoreCase("large")) {
				constructedQuery.append("xxlarge");

			} else if (imageSize.equalsIgnoreCase("extralarge")) {
				constructedQuery.append("huge");
			}
		}

		if (!settings.getColorFilter().equalsIgnoreCase("None")) {
			constructedQuery.append("&imgcolor=");
			String colorFilter = settings.getColorFilter();
			constructedQuery.append(colorFilter.toLowerCase(Locale.US));
		}

		if (!settings.getImageType().equalsIgnoreCase("All")) {
			constructedQuery.append("&imgtype=");
			String imageType = settings.getImageType();
			constructedQuery.append(imageType.toLowerCase(Locale.US));

		}
		if (settings.getSiteFilter() != null
				&& !settings.getSiteFilter().equals("")) {
			constructedQuery.append("&as_sitesearch=" + settings.getSiteFilter());
		}

		return constructedQuery;

	}
}
