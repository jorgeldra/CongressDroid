package com.jorgeldra.seio.utils;

import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

import com.jorgeldra.seio.MainActivity;
import com.jorgeldra.seio.entidad.AlmacenCustomSection;
import com.jorgeldra.seio.entidad.CustomSection;

import android.app.Activity;



public class JSONParserCustomSection {
	
	private Globals global = new Globals();
	private JSONObject jsonObject;
	private Activity activity;
	private AlmacenCustomSection almacenCustomSection = new AlmacenCustomSection();

	public JSONParserCustomSection(Activity a) {
		// TODO Auto-generated constructor stub
		this.activity = a;
	}
	
	public JSONParserCustomSection(){
	}
	
	public ArrayList<CustomSection> getParsedData() {
		return this.almacenCustomSection.listaCustomSection();
		
	}
	
	public void readAndParseJSONCustomSection() throws JSONException {

		readJSONCustomSection();
	}

	public void readJSONCustomSection() throws JSONException {
		
		try{
			
			for (int i=0; i < global.getArrayCustomSections().size();i++){
				
				//Log.i("url"+i, ""+global.getArrayCustomSections().get(i));
				jsonObject = JSONManager.getJSONfromURLObject(global.getArrayCustomSections().get(i));
				
				if (jsonObject != null){
					parseJSONCustomSection(jsonObject);
				}
			}
			MainActivity.almacenCustomSection = almacenCustomSection;
			
		}catch(Exception e){
			System.out.println("Error readJsonCustomSection");
		}
		
	}

	private void parseJSONCustomSection(JSONObject customSectionObject) throws JSONException {
		
		CustomSection customSection = new CustomSection();
		JSONObject object = (JSONObject) customSectionObject;
		String title =  object.getString("title"); ;
		String content =  object.getString("content");
		customSection.setTitle(title);
		customSection.setContent(content);
		almacenCustomSection.guardarCustomSection(customSection);
		
	}
}


