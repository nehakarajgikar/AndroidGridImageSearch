package com.codepath.androidgridimagesearch;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult implements Serializable{

	private static final long serialVersionUID = -2241925162894138784L;
	private String fullUrl;
	private String thumbUrl;
	private String title;
	
	public ImageResult(JSONObject imageJSON){
		try{
		this.fullUrl = imageJSON.getString("url");
		this.thumbUrl = imageJSON.getString("tbUrl");
		this.title = imageJSON.getString("titleNoFormatting");
		}catch(JSONException e){
			this.fullUrl = null;
			this.thumbUrl = null;
			this.title = null;
		}
	}
	
	public String getFullUrl(){
		return this.fullUrl;
	}
	
	public String getThumbUrl(){
		return this.thumbUrl;
	}

	public String getTitle(){
		return this.title;
	}
	public String toString(){
		return this.thumbUrl;
	}

	public static ArrayList<ImageResult> fromJSONArray(
			JSONArray imageJsonResults) {
		// TODO Auto-generated method stub
		ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
		for (int i = 0; i < imageJsonResults.length(); i++) {
			try {
				imageResults.add(new ImageResult(imageJsonResults.getJSONObject(i)));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		return imageResults;
	}
	
}
