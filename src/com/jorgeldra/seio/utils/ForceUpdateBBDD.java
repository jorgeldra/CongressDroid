package com.jorgeldra.seio.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.json.JSONException;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;

import com.jorgeldra.seio.R;
import com.jorgeldra.seio.MainActivity;
import com.jorgeldra.seio.data.DataManager;
import com.jorgeldra.seio.data.DataManagerImpl;
import com.jorgeldra.seio.data.OpenHelper;
import com.jorgeldra.seio.entidad.Conferencia;

public class ForceUpdateBBDD extends Activity {

	
	private Thread t;
	private ArrayList<Conferencia> conferenciaParsed;
	private ProgressDialog dialog;
	private DataManager dataManager;
	private MainActivity mainActivity;
	private InfoConferenciaManejadorXML conferenciaManejador;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.activity_forzar_actualizacion);
		if (hasInternet(this)) {

			showDialog(0);
			t = new Thread() {
				@Override
				public void run() {
					// parseo json array de programa
					JSONParserPrograma json = new JSONParserPrograma();
					try {
						json.readAndParseJSONPrograma();
						/*
						 * Si quisieramos ejecutar el parseo json con runnable,
						 * sacar este codigo fuera del thread e incluir esta
						 * linea json.readAndParseJSONPrograma(this);
						 */
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// fin parseo json
					
					// parseo json array de customSections
					JSONParserCustomSection jsonCustom = new JSONParserCustomSection();
					try {
						jsonCustom.readAndParseJSONCustomSection();
					
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// fin parseo json custom
					
					
					loadData(); // parseo informacion congreso
				
				}
			};
			t.start();

		} else {
			
			// poner un timer con un tiempo determinado para que se vea el logo
			// del congreso
			
			// cargamos desde la base de datos si existe la base de datos,
			// ¡¡CREO QUE NO FUNCIONABA BIEN
			dataManager = new DataManagerImpl(this);
			if (dataManager.checkDataBase()){ //problema a la hora de chequear si existe la base de datos, esto siempre devuelve true. debido a que en el constructor de datamanagerimpl genera la base de datos sin tablas
				if (dataManager.getAllConferencias().size() > 0){ //si hay contenido en la base de datos
					loadDataBBDD();
					
				}else{
					Toast.makeText(getApplicationContext(),"Es necesario que tenga acceso a internet, la primera vez que arranque la aplicación",Toast.LENGTH_SHORT).show();
					
				}
				
			}
			
		}
	}
	

	/**
	 * Metodo que comprueba si disponemos de una conexion wifi o 3g activa
	 * 
	 * @param actividad
	 * @return boolean
	 */
	public static boolean hasInternet(Activity actividad) {
		boolean hasConnectedWifi = false;
		boolean hasConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) actividad.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("wifi"))
				if (ni.isConnected())
					hasConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("mobile"))
				if (ni.isConnected())
					hasConnectedMobile = true;
		}
		return hasConnectedWifi || hasConnectedMobile;
	}
	
	/**
	 * Metodo que se encarga de inicializar el parser y obtiene en un nuevo
	 * array los datos parseados mediante Sax. Si no salta una excepción se
	 * envia al manejador el mensaje "success".
	 * 
	 * @param
	 * @return void
	 */
	private void loadData() {

		try {

			conferenciaManejador = new InfoConferenciaManejadorXML();
			conferenciaManejador.inicializarParser();
			conferenciaParsed = new ArrayList<Conferencia>();
			conferenciaParsed = conferenciaManejador.getParsedData();

			Message myMessage = new Message();
			myMessage.obj = "SUCCESS";
			handler.sendMessage(myMessage);

		} catch (Exception e) {
			Log.e("Congress", "error", e);
		}
	}
	
	private void loadDataBBDD() {
		// dataManager.getAllConferencias();
		dataManager = new DataManagerImpl(this);
		mainActivity.almacenConf.actualizarListaConferencia(dataManager.getAllConferencias());
		
		
		//Toast.makeText(getApplicationContext(),"Title1 en array de base de datos"+ dataManager.getAllConferencias().get(0).getId(),Toast.LENGTH_SHORT).show();
		
		
		//TODO cargar datos del programa
		mainActivity.almacenPrograma.actualizarListaPrograma(dataManager.obtenerListaPrograma());
		
		//TODO se recuperan los datos de la base de datos de mi programa y se actualiza el array estatico
		mainActivity.almacenMisTrabajos.actualizarListaTrabajo(dataManager.getAllFavouritePapers());
		
		mainActivity.almacenCustomSection.actualizarListaCustomSection(dataManager.obtenerListaCustomSection());
		
		mainActivity.almacenAutor.actualizarListaAutor(dataManager.obtenerListaAutores());
		
	}
	
	/**
	 * Metodo que muestra un mensaje un mensaje de aviso si el parseo de datos
	 * se ha realizado correctamente (almacena los datos en array estático). Si
	 * el array esta vacio (no se tiene acceso al servidor), se carga la
	 * información de la BBDD. En caso contrario, actualizamos la información en
	 * la base de datos.
	 * 
	 * @param msg
	 * @return void
	 */

	private Handler handler = new Handler() {
		@SuppressWarnings("deprecation")
		@Override
		public void handleMessage(Message msg) {
			String successmsg = (String) msg.obj;
			if (successmsg.equals("SUCCESS")) {

				int i = 0;

				for (Conferencia conferencia : conferenciaParsed) {
					mainActivity.almacenConf.guardarConferencia(new Conferencia(conferencia.getTitle(), conferencia.getTitle2(), conferencia.getPresentation(), conferencia.getHome_text(),conferencia.getListaImagenesConf()));
					i++;

				}

				if (i == 0) {
					Toast.makeText(getApplicationContext(),"La web de SEIO no responde", Toast.LENGTH_SHORT).show();
					// recuperamos el contenido de la BBDD si existe la Base de
					// datos, ¡¡FALLARA EN EL CASO DE QUE LA BBDD ESTE VACIA
					// Y SE INTENTE CARGAR ALGO

					loadDataBBDD();
				} else {
					// Se debe actualizar la base de datos al finalizar el
					// thread

					updateDataBBDD();
					
					mainActivity.almacenPrograma.actualizarListaPrograma(dataManager.obtenerListaPrograma());
					//TODO se recuperan los datos de la base de datos de mis trabajos y se actualiza el array estatico
					mainActivity.almacenMisTrabajos.actualizarListaTrabajo(dataManager.getAllFavouritePapers());
					
					mainActivity.almacenCustomSection.actualizarListaCustomSection(dataManager.obtenerListaCustomSection());
					
					mainActivity.almacenAutor.actualizarListaAutor(dataManager.obtenerListaAutores());
					
					//Toast.makeText(getApplicationContext(),"Custom Section"+ almacenAutor.listaAutor().get(0).getName(),Toast.LENGTH_SHORT).show();
					
				}

				removeDialog(0);
			}
		}
	};
	
	/**
	 * Metodo que actualiza la información de la BBDD, utilizamos la interfaz
	 * dataManager
	 * 
	 * @param
	 * @return void
	 */

	@SuppressWarnings("static-access")
	private void updateDataBBDD() {
		OpenHelper oh = new OpenHelper(this);
		Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT")); 
		int year = cal.get(Calendar.YEAR);  // 2012
		int month = cal.get(Calendar.MONTH);  // 9 - October!!!
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		String fecha = year+""+month+""+day;
	
		oh.DATABASE_VERSION = Integer.parseInt(fecha); //obtenemos la fecha del sistema y actualizamos la base de datos con ese id.
		
		dataManager = new DataManagerImpl(this);
		
		Toast.makeText(getApplicationContext(), "Congreso actualizado correctamente.", Toast.LENGTH_LONG).show();
		super.finish();
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0: {
			dialog = new ProgressDialog(this);
			dialog.getWindow().getAttributes().verticalMargin = 0.6F;
			dialog.getWindow().clearFlags(LayoutParams.FLAG_DIM_BEHIND);
			dialog.setMessage("Actualizando congreso, espere por favor...");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			return dialog;
		}
		}
		return null;
	}


}
