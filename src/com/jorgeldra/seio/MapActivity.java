package com.jorgeldra.seio;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;



import com.jorgeldra.seio.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jorgeldra.seio.adaptador.NavigationDrawerAdapter;
import com.jorgeldra.seio.utils.ForceUpdateBBDD;


public class MapActivity extends Activity implements AdapterView.OnItemClickListener {
	
	//static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	//static final LatLng KIEL = new LatLng(53.551, 9.993);
	private GoogleMap map;
	private LatLng SEIO;
	
	//menu lateral
	private DrawerLayout mDrawer;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerList;
	private String[] mSeioTitles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
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
		
		Bundle extras = getIntent().getExtras();
		String gps_coord = extras.getString("gps_coord");
	    String primeraCoord = (String) gps_coord.subSequence(0, gps_coord.lastIndexOf(",")-1);
	   
	    
	    String segundaCoord = (String) gps_coord.subSequence(gps_coord.lastIndexOf(",")+1, gps_coord.lastIndexOf(";")-1);
	    
	    String venue = (String) gps_coord.subSequence(gps_coord.lastIndexOf(";")+1, gps_coord.length());
	    
		float primera_coordenada = Float.parseFloat(primeraCoord);
		float segunda_coordenada = Float.parseFloat(segundaCoord);
		
		SEIO = new LatLng(primera_coordenada, segunda_coordenada);
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		
		//Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG).title("Hamburg"));
		//Marker kiel = map.addMarker(new MarkerOptions().position(KIEL).title("Kiel").snippet("Kiel is cool").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
		Marker seio = map.addMarker(new MarkerOptions().position(SEIO).title("SEIO").snippet(venue).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));

		// Move the camera instantly to hamburg with a zoom of 15.
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(SEIO, 15));

		// Zoom in, animating the camera.
		map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
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

}
