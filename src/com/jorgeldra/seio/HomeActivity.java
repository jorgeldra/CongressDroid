package com.jorgeldra.seio;



import java.sql.Date;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.jorgeldra.seio.R;
import com.devsmart.android.ui.HorizontalListView;
//import com.actionbarsherlock.view.MenuInflater;
//import com.darvds.ribbonmenu.RibbonMenuView;
import com.jorgeldra.seio.adaptador.ImageAdapter;
import com.jorgeldra.seio.adaptador.NavigationDrawerAdapter;
import com.jorgeldra.seio.data.DataManager;
import com.jorgeldra.seio.data.DataManagerImpl;
import com.jorgeldra.seio.utils.ForceUpdateBBDD;


//public class HomeActivity  extends Activity implements iRibbonMenuCallback {
public class HomeActivity  extends Activity implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener {

	//menu lateral
	private DrawerLayout mDrawer;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerList;
	private String[] mSeioTitles;
	
	
	//busqueda
	private SearchView mSearchView;

	
	//private RibbonMenuView rbmView; antiguo menu lateral
	
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
		      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		      StrictMode.setThreadPolicy(policy);
		}
		
		//menu lateral google
		mSeioTitles = getResources().getStringArray(R.array.seio_navigation_array);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);

		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer,R.drawable.ic_drawer,R.string.drawer_open,R.string.drawer_close) {
  
        };

        // Set the drawer toggle as the DrawerListener
        mDrawer.setDrawerListener(mDrawerToggle);
		
		
		//de esta manera utilizaria un adaptador propio
		NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(this,mSeioTitles);
		
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(this);
		
		//fin menu lateral google
		
        //este iba bien pero en vertical, listview de imagenes horizontales
        HorizontalListView listview = (HorizontalListView) findViewById(R.id.listview);
        listview.setAdapter(new ImageAdapter(MainActivity.almacenConf.listaConferencia().get(0).getListaImagenesConf()));
        
        TextView txtTituloPortada = (TextView) findViewById(R.id.txtTituloPortada);
        txtTituloPortada.setText(MainActivity.almacenConf.listaConferencia().get(0).getTitle() + " - "+ MainActivity.almacenConf.listaConferencia().get(0).getTitle2()); 
        
        
        String contenido = MainActivity.almacenConf.listaConferencia().get(0).getHome_text();
        //Log.i("contenido home",""+contenido);
        contenido = contenido.replace("<h3>SEIO2012, al minuto</h3>","");
        
		webView = (WebView) findViewById(R.id.webView1);
		//webView.setBackgroundColor(Color.parseColor("#585858"));
		//webView.reload();
		
		//webView.loadData(contenido,"text/html; charset=UTF-8", null);
		webView.loadData(MainActivity.almacenCustomSection.buscarPorTitulo("Fechas").getContent(),"text/html; charset=UTF-8", null);
		//String message ="<font color='white'>"+"<u>"+"text in white"+ "<br>" +"<font color='cyan'>"+"<font size='2'>"+" text in blue color "+"</font>"; 
		//webView.loadData(message, "text/html", "utf8"); 
		
		Button btnEstaPasando = (Button) findViewById(com.jorgeldra.seio.R.id.btnEstaPasando);
		Button btnTodo = (Button) findViewById(com.jorgeldra.seio.R.id.btnTodoPrograma);
        
		btnEstaPasando.setOnClickListener(new View.OnClickListener() {
			 
            @Override
            public void onClick(View arg0) {
               
            	Intent i;
        		i = new Intent(getApplicationContext(), BusquedaActivity.class);
        		i.putExtra("busqueda", "HomeEstaPasando");
        		i.putExtra("paginaProcedencia", "HomeEstaPasando");
        		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        		startActivity(i);
            }
        });
		
		btnTodo.setOnClickListener(new View.OnClickListener() {
			 
            @Override
            public void onClick(View arg0) {
            	Intent i = new Intent(getApplicationContext(), ProgramActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
            	
            }
        });
		
	}
	
	/* menu horizontal barra de herramientas */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity_menu, menu);
		//creaMenu(menu);
		//return true;
		//getMenuInflater().inflate(R.menu.activity_menu, menu);
		//android.view.MenuInflater inflater = getMenuInflater();
		//inflater.inflate(R.menu.menu_hor_sherlock, menu);
		

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.navigation_drawer, menu); //barra herramientas abajo
		
		MenuItem searchItem = menu.findItem(R.id.menu_search);
		mSearchView = (SearchView) searchItem.getActionView();
		mSearchView.setQueryHint("Buscar...");
		mSearchView.setOnQueryTextListener(this);
		
		
		//MenuItem refreshItem = menu.findItem(R.id.menu_refresh);
		
		
		// <!--  cambiar actionbar abajo (incluir dentro de apllication) android:uiOptions="splitActionBarWhenNarrow" -->
		return true;
	}

	/**
	 * Método que crea cada uno de los item del actionBar
	 * @param menu
	 * @return void
	 */
	/*private void creaMenu(Menu menu){
		MenuItem item1 = menu.add(0, 0, 0, "Programa");
		{
			item1.setIcon(android.R.drawable.ic_menu_today);
			item1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}
		
	}*/
	
	
	/**
	 * Método que controla el evento de pulsar la tecla volver, en este caso, no queremos que se vuelvan a recargar los datos en la BBDD
	 * nuevamente, por eso controlamos el evento onRestart en mainActivity. Se muestra un diálogo para cerrar la aplicación.
	 *
	 * @param keyCode, event
	 * @return boolean
	 */
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	  //Handle the back button
	  if (keyCode == KeyEvent.KEYCODE_BACK) {
	    //Ask the user if they want to quit
	    new AlertDialog.Builder(this)
	      .setIcon(R.drawable.ic_dialog_warning)
	      .setTitle("Salir")
	      .setMessage("¿Está seguro de querer salir?")
	      .setNegativeButton(android.R.string.cancel, null)
	      .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	        @Override
	        public void onClick(DialogInterface dialog, int which){
	          // Exit the activity
	        	HomeActivity.this.finish();
	        	
	        }
	      })
	      .show();

	    // Say that we've consumed the event
	    return true;
	  }

	  return super.onKeyDown(keyCode, event);
	}

	// métodos necesarios para implementar navbar lateral izquierda
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				if (mDrawer.isDrawerOpen(mDrawerList)) {
					mDrawer.closeDrawers();
				} else {
					mDrawer.openDrawer(mDrawerList);
				}
				return true;
			case R.id.menu_refresh:
				Intent i = new Intent(getApplicationContext(), ForceUpdateBBDD.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i); 
				break;
		}

		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int itemId, long l) {
		// TODO Auto-generated method stub
		//Toast.makeText(this, "Pulsado " + mSeioTitles[i], Toast.LENGTH_SHORT).show();
		
		Intent i;
		switch (itemId){
			case 0: 
					 i= new Intent(this, HomeActivity.class);
					//i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_INSTANCE);
					//i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					break;
			case 1:
					i = new Intent(this, ProgramActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					break;
			case 2:
					i = new Intent(this, MyProgramActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					break;
			case 3:
					i = new Intent(this, AutoresActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					break;
			case 4:
					i = new Intent(this, ConfPlenariasActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					break;
			case 5:
					i = new Intent(this, PremioRamiroActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					break;
			case 6:
					i = new Intent(this, FechasActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					break;
			case 7:
					//i = new Intent(this, SedesActivity.class);
					i = new Intent(this, LocalizacionesActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					break;
			case 8:
					i = new Intent(this, CuotasActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					break;
			case 9:
					i = new Intent(this, InscripcionActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					break;
			case 10:
					i = new Intent(this, ComoLlegarActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					break;
			case 11:
			
				i = new Intent(this, TwitterActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				break;
				
		}
	
		mDrawer.closeDrawers();
		
	}
	
	//estos dos metodos siguientes son necesarios para poder cambiar el icono de la action bar
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
	    super.onPostCreate(savedInstanceState);
	    // Sync the toggle state after onRestoreInstanceState has occurred.
	    mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	    mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	
	@Override
	public boolean onQueryTextChange(String newText) {
	 
	     //Toast.makeText(this, newText, Toast.LENGTH_SHORT).show();
	 
	     return false;
	 }
	@Override
	public boolean onQueryTextSubmit(String text) {
	
		//Toast.makeText(this, "Searching for " + text, Toast.LENGTH_LONG).show();
		
		Intent i;
		i = new Intent(this, BusquedaActivity.class);
		i.putExtra("busqueda", text);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	
	   return false;
	}

	
}
