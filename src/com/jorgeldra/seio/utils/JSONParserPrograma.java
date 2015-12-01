package com.jorgeldra.seio.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;

import com.jorgeldra.seio.MainActivity;
import com.jorgeldra.seio.entidad.AlmacenPrograma;
import com.jorgeldra.seio.entidad.Programa;
import com.jorgeldra.seio.entidad.Sesion;
import com.jorgeldra.seio.entidad.Trabajo;

public class JSONParserPrograma {

	private Activity activity;
	private JSONArray jsonArray;
	private ProgressDialog progressDialog = null;
	//private Runnable runReadAndParseJSON;
	private AlmacenPrograma interfazPrograma;
	private Globals global = new Globals();
	
	public JSONParserPrograma(Activity a) {
		activity = a;
	}
	public JSONParserPrograma(){
		//interfazPrograma = new AlmacenPrograma();
	}
	
	public ArrayList<Programa> getParsedData() {
		return this.interfazPrograma.listaPrograma();
		
	}

	public void readAndParseJSONPrograma() throws JSONException {
		/*
		 * crear hilo con runnable, descomentar para ejecutar hilo con runnable
		 * 
		 * runReadAndParseJSON = new Runnable() {
			@Override
			public void run() {
				try {
					readJSONPrograma();
				} catch (Exception e) {
				}
			}
		};
		Thread thread = new Thread(null, runReadAndParseJSON,"bgReadJSONPrograma");
		thread.start();
		progressDialog = ProgressDialog.show(activity,"Descargando informaci�n", "Por favor espere", true);*/
		
		readJSONPrograma();
	}

	public void readJSONPrograma() throws JSONException {
		
		try{
			//jsonArray = JSONManager.getJSONfromURL("http://www.seio2012.com/es/api/schedule.json");
			jsonArray = JSONManager.getJSONfromURL(global.getURL_PROGRAMA());
			
			if (jsonArray != null){
				parseJSONPrograma(jsonArray);
			}
				
			//activity.runOnUiThread(returnRes); descomentar para runnable
			
		}catch(Exception e){
			System.out.println("Error 4");
		}
		
	}

	private void parseJSONPrograma(JSONArray programaArray) throws JSONException {
		interfazPrograma = new AlmacenPrograma();
		//recorre array de programas
		for (int i = 0; i < programaArray.length(); i++) {
			Programa programa = new Programa();
			programa.inicializarListaSesion();
			 JSONObject object = (JSONObject) programaArray.get(i);
			 String date = object.getString("date");
			 programa.setFecha(date);
			
			
			 JSONArray arraySessions = object.getJSONArray("sessions");
			 
			 //recorre array de sesiones
			 for (int j=0;j < arraySessions.length();j++){
				 
				 Sesion sesion = new Sesion();
				 
				 JSONObject objectSessions = (JSONObject) arraySessions.get(j);
				 int id = Integer.parseInt(objectSessions.getString("id"));
				 sesion.setId(id);
				 String identifier = objectSessions.getString("identifier");
				 sesion.setIdentifier(identifier);
				 String name = objectSessions.getString("name");
				 sesion.setName(name);
				 String description = objectSessions.getString("description");
				 sesion.setDescription(description);
				 String comments = objectSessions.getString("comments");
				 sesion.setComments(comments);
				 String start = objectSessions.getString("start");
				 sesion.setStart(start);
				 String end = objectSessions.getString("end");
				 sesion.setEnd(end);
				
				 if (!objectSessions.isNull("location")){
					 JSONObject objLocation = objectSessions.getJSONObject("location");
					 int idLocation = Integer.parseInt(objLocation.getString("id"));
					 String nameLocation = objLocation.getString("name");
					 String gps_coords = objLocation.getString("gps_coords");
					 String venue = objLocation.getString("venue");
					 sesion.añadirLocalizacion(idLocation, nameLocation, gps_coords, venue);
				 }
				 
				 if (!objectSessions.isNull("chairperson")){
					 JSONObject objChairperson = objectSessions.getJSONObject("chairperson");
					 int idChairperson = Integer.parseInt(objChairperson.getString("id"));
					 String normalized_name = objChairperson.getString("normalized_name");
					 String nameChairperson = objChairperson.getString("name");
					 String lastname = objChairperson.getString("lastname");
					 sesion.añadirModerador(idChairperson, normalized_name, nameChairperson, lastname);
				 }
				 //recorremos string de trabajos
				 if (!objectSessions.isNull("papers")){
					 JSONArray arrayPapers = objectSessions.getJSONArray("papers");
					 //JSONArray arrayPapers = object.getJSONArray("papers");
					 //recorre array de sesiones
					 sesion.inicializarListaPapers();
					 
					 for (int k=0;k < arrayPapers.length();k++){
						 JSONObject objectPapers = (JSONObject) arrayPapers.get(k);
						 
						 Trabajo trabajo = new Trabajo();
						 int idTrabajo = Integer.parseInt(objectPapers.getString("id"));
						 
						 trabajo.setId(idTrabajo);
						 String title = objectPapers.getString("title");
						 trabajo.setTitle(title);
						 String text = objectPapers.getString("text");
						 trabajo.setText(text);
						 String keywords = objectPapers.getString("keywords");
						 trabajo.setKeywords(keywords);
					
						 if (!objectPapers.isNull("authors")){
							 JSONArray arrayAuthors = objectPapers.getJSONArray("authors");
							 trabajo.inicializarListaAutores();
							 for (int l=0;l < arrayAuthors.length();l++){ 
								 JSONObject objectAuthors = (JSONObject) arrayAuthors.get(l);
								 int attende_id;
								 if (objectAuthors.isNull("attendee_id")){
									 attende_id =0; 
								 }else{
									 attende_id = Integer.parseInt(objectAuthors.getString("attendee_id"));
								 }
								 
								 String normalized_name = objectAuthors.getString("normalized_name");
								 String nameAuthor = objectAuthors.getString("name");
								 String lastnameAuthor = objectAuthors.getString("lastname");
								 int submitter = Integer.parseInt(objectAuthors.getString("submitter"));
								 //se a�aden los autores dentro de los trabajos
								 trabajo.añadirAutorEnListaAutores(attende_id, normalized_name, nameAuthor, lastnameAuthor, submitter);
							 }
						 }
						 //se a�ade la lista de trabajos en sesiones
						 sesion.añadirTrabajoEnListaPapers(idTrabajo, title, text, keywords, trabajo.getListaAutores());
						 //sesion.a�adirTrabajoEnListaPapers(trabajo);
					 }
				 }
				//programa.a�adirSesionEnListaSesion(1,"prueba","uno", "desc", "prueba", "fecha", "fin", null,null,null);
				programa.añadirSesionEnListaSesion(sesion);
				 
			 }
			interfazPrograma.guardarPrograma(programa); //insertamos objeto programa
			MainActivity.almacenPrograma = interfazPrograma;
		}
	}

	private Runnable returnRes = new Runnable() {
		@Override
		public void run() {
			progressDialog.dismiss();
			//actualizamos el array statico de programas
			
			//MainActivity.almacenPrograma = interfazPrograma; descomentar para runnable
		}
	};

}
