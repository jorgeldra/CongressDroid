package com.jorgeldra.seio.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


public class JSONManager {

	public JSONManager() {
		// TODO Auto-generated constructor stub
	}

	public static JSONArray getJSONfromURL(String url) {

		InputStream is = null;
		//String result = "";
		//JSONObject json = null;
		JSONArray jsonarray = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			System.out.println("Error 1");
		}

		try {
			/*BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();*/
			//HttpResponse response; // some response object
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuilder builder = new StringBuilder();
			for (String line = null; (line = reader.readLine()) != null;) {
			    builder.append(line).append("\n");
			}
			JSONTokener tokener = new JSONTokener(builder.toString());
			jsonarray = new JSONArray(tokener);
		} catch (Exception e) {
			System.out.println("Error 2");
		}
		//System.out.println("result"+ result);
		return jsonarray;
	}
	
	
	public static JSONObject getJSONfromURLObject(String url) {
		//Log.i("url desde json manager", ""+ url);
		InputStream is = null;
		//String result = "";
		//JSONObject json = null;
		JSONObject jsonobject = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			System.out.println("Error 1");
		}

		try {
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuilder builder = new StringBuilder();
			for (String line = null; (line = reader.readLine()) != null;) {
			    builder.append(line).append("\n");
			}
			JSONTokener tokener = new JSONTokener(builder.toString());
			jsonobject = new JSONObject(tokener);
		} catch (Exception e) {
			System.out.println("Error 2");
		}
		//System.out.println("result"+ result);
		return jsonobject;
	}

}
