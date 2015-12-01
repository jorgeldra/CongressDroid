package com.jorgeldra.seio;

import com.jorgeldra.seio.R;
import com.jorgeldra.seio.adaptador.NavigationDrawerAdapter;
import com.jorgeldra.seio.utils.ForceUpdateBBDD;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

public class SedesActivity extends Activity implements AdapterView.OnItemClickListener,SearchView.OnQueryTextListener {
private WebView webView;
	
	private DrawerLayout mDrawer;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerList;
	private String[] mSeioTitles;
	
	private TextView titulo;
	//busqueda
	private SearchView mSearchView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sedes);
		
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
		
		String contenido =MainActivity.almacenCustomSection.buscarPorTitulo("Sedes").getContent();
		contenido = contenido.replace("<p>\r\n<iframe marginheight=\"0\" marginwidth=\"0\" src=\"http://maps.google.com/maps/ms?msa=0&amp;msid=204518921833856956526.0004ad6e95e2387f48ce5&amp;ie=UTF8&amp;t=m&amp;vpsrc=6&amp;ll=40.451389,-3.675613&amp;spn=0.125402,0.219727&amp;z=12&amp;output=embed\" frameborder=\"0\" height=\"480\" scrolling=\"no\" width=\"640\">\r\n</iframe>\r\n<br><small>Ver <a target=\"_blank\" href=\"http://maps.google.com/maps/ms?msa=0&amp;msid=204518921833856956526.0004ad6e95e2387f48ce5&amp;ie=UTF8&amp;t=m&amp;vpsrc=6&amp;ll=40.451389,-3.675613&amp;spn=0.125402,0.219727&amp;z=12&amp;source=embed\" style=\"color:#0000FF;text-align:left\">Congreso SEIO 2012</a> en un mapa más grande.</small><br></p>", ""); //eliminamos del contenido - Ver Más
		contenido = contenido.replace("<a href=\"sedes-2\">", "<a href=\"http://www.seio2012.com/es/c/sedes-2\">"); //eliminamos del contenido - Ver Más
		
		titulo = (TextView)findViewById(R.id.titleSedes);
		titulo.setText(MainActivity.almacenCustomSection.buscarPorTitulo("Sedes").getTitle());
		
		webView = (WebView) findViewById(R.id.webViewSedes);
		//Log.i("conferenciaplenaria", ""+MainActivity.almacenCustomSection.buscarPorTitulo("Conferencias plenarias").getContent());
		webView.loadData(contenido,"text/html; charset=UTF-8", null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.navigation_drawer, menu);
		MenuItem searchItem = menu.findItem(R.id.menu_search);
		mSearchView = (SearchView) searchItem.getActionView();
		mSearchView.setQueryHint("Buscar...");
		mSearchView.setOnQueryTextListener(this);
		return true;
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
		public boolean onKeyDown(int keyCode, KeyEvent event) {
		  //Handle the back button
		  if (keyCode == KeyEvent.KEYCODE_BACK) {
			  Intent i = new Intent(this, HomeActivity.class);
			
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
		    return true;
		  }

		  return super.onKeyDown(keyCode, event);
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
