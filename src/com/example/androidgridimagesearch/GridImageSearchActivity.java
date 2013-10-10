package com.example.androidgridimagesearch;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class GridImageSearchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grid_image_search);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.grid_image_search, menu);
		return true;
	}

}
