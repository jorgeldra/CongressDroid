package com.jorgeldra.seio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.json.JSONException;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;

import com.jorgeldra.seio.R;
import com.jorgeldra.seio.data.DataManager;
import com.jorgeldra.seio.data.DataManagerImpl;
import com.jorgeldra.seio.data.OpenHelper;
import com.jorgeldra.seio.entidad.AlmacenAutor;
import com.jorgeldra.seio.entidad.AlmacenConferencia;
import com.jorgeldra.seio.entidad.AlmacenCustomSection;
import com.jorgeldra.seio.entidad.AlmacenPrograma;
import com.jorgeldra.seio.entidad.AlmacenTrabajo;
import com.jorgeldra.seio.entidad.Conferencia;
import com.jorgeldra.seio.entidad.InterfazAutor;
import com.jorgeldra.seio.entidad.InterfazConferencia;
import com.jorgeldra.seio.entidad.InterfazCustomSection;
import com.jorgeldra.seio.entidad.InterfazPrograma;
import com.jorgeldra.seio.entidad.InterfazTrabajo;
import com.jorgeldra.seio.utils.InfoConferenciaManejadorXML;
import com.jorgeldra.seio.utils.JSONParserCustomSection;
import com.jorgeldra.seio.utils.JSONParserPrograma;

public class MainActivity extends Activity {

	
	private ArrayList<Conferencia> conferenciaParsed;
	public static InterfazConferencia almacenConf = new AlmacenConferencia();
	public static InterfazPrograma almacenPrograma = new AlmacenPrograma();
	public static InterfazTrabajo almacenMisTrabajos = new AlmacenTrabajo();
	public static InterfazCustomSection almacenCustomSection = new AlmacenCustomSection();
	public static InterfazAutor almacenAutor = new AlmacenAutor();
	
	private Thread t;
	private ProgressDialog dialog;
	private InfoConferenciaManejadorXML conferenciaManejador;
	private DataManager dataManager;
	
	

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ocultar actionBar, tambien se puede hacer desde el manifest diciendo
		// que el theme no tenga actionBar
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_main);

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
					throwMenu();
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
					// lanzamos el menu siguiente
					throwMenu();
				}else{
					Toast.makeText(getApplicationContext(),"Es necesario que tenga acceso a internet, la primera vez que arranque la aplicación",Toast.LENGTH_SHORT).show();
					
				}
				
			}
			
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0: {
			dialog = new ProgressDialog(this);
			dialog.getWindow().getAttributes().verticalMargin = 0.6F;
			dialog.getWindow().clearFlags(LayoutParams.FLAG_DIM_BEHIND);
			dialog.setMessage("Cargando, espere por favor...");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			return dialog;
		}
		}
		return null;
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
					almacenConf.guardarConferencia(new Conferencia(conferencia.getTitle(), conferencia.getTitle2(), conferencia.getPresentation(), conferencia.getHome_text(),conferencia.getListaImagenesConf()));
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
					
					almacenPrograma.actualizarListaPrograma(dataManager.obtenerListaPrograma());
					//TODO se recuperan los datos de la base de datos de mis trabajos y se actualiza el array estatico
					almacenMisTrabajos.actualizarListaTrabajo(dataManager.getAllFavouritePapers());
					
					almacenCustomSection.actualizarListaCustomSection(dataManager.obtenerListaCustomSection());
					
					almacenAutor.actualizarListaAutor(dataManager.obtenerListaAutores());
					
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
		//String fecha = year+""+month+"25";
		
		//Log.i("fecha",""+ fecha);
		
		oh.DATABASE_VERSION = Integer.parseInt(fecha); //obtenemos la fecha del sistema y actualizamos la base de datos con ese id.
		
		dataManager = new DataManagerImpl(this);
		
		//Toast.makeText(getApplicationContext(),"Title1 en array de base de datos"+dataManager.getAllConferencias().get(0).getId(),Toast.LENGTH_SHORT).show();
	}

	/**
	 * Metodo que carga la información de la BBDD actualizando el array estático
	 * almacenConf, utilizamos la interfaz dataManager.
	 * 
	 * @param
	 * @return void
	 */
	private void loadDataBBDD() {
		// dataManager.getAllConferencias();
		dataManager = new DataManagerImpl(this);
		almacenConf.actualizarListaConferencia(dataManager.getAllConferencias());
		
		
		//Toast.makeText(getApplicationContext(),"Title1 en array de base de datos"+ dataManager.getAllConferencias().get(0).getId(),Toast.LENGTH_SHORT).show();
		
		
		//TODO cargar datos del programa
		almacenPrograma.actualizarListaPrograma(dataManager.obtenerListaPrograma());
		
		//TODO se recuperan los datos de la base de datos de mi programa y se actualiza el array estatico
		almacenMisTrabajos.actualizarListaTrabajo(dataManager.getAllFavouritePapers());
		
		almacenCustomSection.actualizarListaCustomSection(dataManager.obtenerListaCustomSection());
		
		almacenAutor.actualizarListaAutor(dataManager.obtenerListaAutores());
		
		//Toast.makeText(getApplicationContext(),"Custom Section"+ almacenCustomSection.listaCustomSection().get(0).getTitle(),Toast.LENGTH_SHORT).show();
		// Toast.makeText(getApplicationContext(),"Title1 en array de base de datos"+
		// dataManager.getAllConferencias().get(0).getListaImagenesConf().get(0).getUrl(),Toast.LENGTH_SHORT).show();

	}



	/**
	 * Controlamos el evento reiniciar para cuando se vuelva a cargar la
	 * actividad en la pila, se cierre para no refrescar contenido nuevamente.
	 * 
	 * @param
	 * @return void
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
		this.finish();

	}


	/**
	 * Cerramos la conexión con la BBDD, liberamos variables estáticas.
	 * 
	 * @param
	 * @return void
	 */
	@Override
	protected void onDestroy() {
		dataManager.closeDb();
		super.onDestroy();
		
		//eliminamos array estáticos
		almacenConf.liberarListaConferencia();
		almacenPrograma.liberarListaPrograma();
		almacenMisTrabajos.liberarListaTrabajo();
		almacenCustomSection.liberarListaCustomSection();
		almacenAutor.liberarListaAutor();
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
	 * Metodo que lanza la actividad del menu principal
	 * 
	 * @param
	 * @return void
	 */
	private void throwMenu() {
		Intent i = new Intent(this, HomeActivity.class);
		startActivity(i);
	}

}
