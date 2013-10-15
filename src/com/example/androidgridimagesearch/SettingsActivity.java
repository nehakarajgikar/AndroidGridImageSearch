package com.example.androidgridimagesearch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
//		Spinner imageSizeSpinner = (Spinner) findViewById(R.id.sp_settings_image_size);
//		imageSizeSpinner
	}
	
	public void onSaveSettings(View v){
		
		Toast.makeText(getApplicationContext(), "Saving ...", Toast.LENGTH_SHORT).show();
		Spinner imageSizeSpinner = (Spinner) findViewById(R.id.sp_settings_image_size);
		Spinner colorFilterSpinner = (Spinner) findViewById(R.id.sp_settings_color_filter);
		Spinner imageTypeSpinner = (Spinner) findViewById(R.id.sp_settings_image_type);
		EditText siteFilter = (EditText) findViewById(R.id.et_settings_site_filter);
		Intent i = new Intent(getApplicationContext(),GridImageSearchActivity.class);
		i.putExtra("imageSize", imageSizeSpinner.getSelectedItem().toString());
		i.putExtra("colorFilter", colorFilterSpinner.getSelectedItem().toString());
		i.putExtra("imageType", imageTypeSpinner.getSelectedItem().toString());
		i.putExtra("siteFilter", siteFilter.getText().toString());
//		Toast.makeText(getApplicationContext(), imageSizeSpinner.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

}
