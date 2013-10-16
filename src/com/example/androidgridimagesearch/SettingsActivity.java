package com.example.androidgridimagesearch;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsActivity extends Activity {
	protected static final String TAG = "GRIDIMAGESEARCH";
	Spinner imageSizeSpinner;
	Spinner colorFilterSpinner;
	Spinner imageTypeSpinner;
	EditText siteFilter;
	Settings settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		settings = (Settings) getIntent().getSerializableExtra("settings");
		imageSizeSpinner = (Spinner) findViewById(R.id.sp_settings_image_size);
		colorFilterSpinner = (Spinner) findViewById(R.id.sp_settings_color_filter);
		imageTypeSpinner = (Spinner) findViewById(R.id.sp_settings_image_type);
		siteFilter = (EditText) findViewById(R.id.et_settings_site_filter);
		if (settings != null) {
			Log.d(TAG,"Settings is not null");
			ArrayList<String> list = new ArrayList<String>(
					Arrays
							.asList(getResources().getStringArray(R.array.image_size_array)));
			imageSizeSpinner.setSelection(list.indexOf(settings.getImageSize()));
			list = new ArrayList<String>(Arrays.asList(getResources().getStringArray(
					R.array.color_filter_array)));
			colorFilterSpinner.setSelection(list.indexOf(settings.getColorFilter()));
			list = new ArrayList<String>(Arrays.asList(getResources().getStringArray(
					R.array.image_type_array)));
			imageTypeSpinner.setSelection(list.indexOf(settings.getImageType()));
			siteFilter.setText(settings.getSiteFilter());
		}else{
			Log.d(TAG,"Settings is  null");
		}
		

	}

	public void onSaveSettings(View v) {

		Toast.makeText(getApplicationContext(), "Saving ...", Toast.LENGTH_SHORT)
				.show();

		Intent i = new Intent();
		settings = new Settings();
		settings.setImageSize(imageSizeSpinner.getSelectedItem().toString());
		settings.setColorFilter(colorFilterSpinner.getSelectedItem().toString());
		settings.setImageType(imageTypeSpinner.getSelectedItem().toString());
		settings.setSiteFilter(siteFilter.getText().toString());
		// i.putExtra("imageSize", imageSizeSpinner.getSelectedItem().toString());
		// i.putExtra("colorFilter",
		// colorFilterSpinner.getSelectedItem().toString());
		// i.putExtra("imageType", imageTypeSpinner.getSelectedItem().toString());
		// i.putExtra("siteFilter", siteFilter.getText().toString());
		i.putExtra("settings", settings);
		// Toast.makeText(getApplicationContext(),
		// imageSizeSpinner.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
		setResult(RESULT_OK, i);
		finish();
	}

	
	public void onCancelSettings(View v){
		Intent i = new Intent();
		i.putExtra("settings", settings);
		setResult(RESULT_OK,i);
		finish();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

}
