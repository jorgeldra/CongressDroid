package com.jorgeldra.seio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jorgeldra.seio.R;
import com.jorgeldra.seio.adaptador.FragmentAdapter;
import com.jorgeldra.seio.adaptador.FragmentBusquedaAdapter;
import com.jorgeldra.seio.adaptador.NavigationDrawerAdapter;
import com.jorgeldra.seio.data.DataManager;
import com.jorgeldra.seio.data.DataManagerImpl;
import com.jorgeldra.seio.entidad.AlmacenPrograma;
import com.jorgeldra.seio.entidad.Autor;
import com.jorgeldra.seio.entidad.Sesion;
import com.jorgeldra.seio.entidad.Trabajo;
import com.jorgeldra.seio.utils.ForceUpdateBBDD;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


public class BusquedaActivity extends SherlockFragmentActivity implements AdapterView.OnItemClickListener {
	
	private FragmentBusquedaAdapter mAdapter;
	private ViewPager mPager;
	private PageIndicator mIndicator;
	private AlmacenPrograma almacenPrograma = new AlmacenPrograma();
	
	//menu lateral
	private DrawerLayout mDrawer;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerList;
	private String[] mSeioTitles;
	
	public static ArrayList<Sesion> arrayBusquedaSesion;
	public static ArrayList<Trabajo> arrayBusquedaTrabajo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_busqueda);
		
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
				
		
		// obtengo los id que le pasamos desde el fragment para saber que indice
		// estoy pasando en sessiones y trabajos
		Bundle extras = getIntent().getExtras();
		String buscar = null;
		String paginaProcedencia = null;
		if (extras != null) {
			buscar = extras.getString("busqueda");
			paginaProcedencia = extras.getString("paginaProcedencia");
		}
		arrayBusquedaSesion = new  ArrayList<Sesion>();
		arrayBusquedaTrabajo = new  ArrayList<Trabajo>();
		arrayBusquedaSesion = almacenPrograma.obtenerBusquedaEnSesiones(buscar);
		arrayBusquedaTrabajo = almacenPrograma.obtenerBusquedaEnTrabajos(buscar);
		//almacenPrograma.obtenerBusquedaEnSesiones(buscar);
		
		if (paginaProcedencia != null){
			if (paginaProcedencia.equals("HomeEstaPasando")){
				DataManager dataManager = new DataManagerImpl(this);
				arrayBusquedaSesion = dataManager.obtenerListaSesionesPorFechaDia();
			}
		}else{
			//para cada uno de los item de trabajo, buscamos la sesi—n correspondiente
			for (int i=0; i <arrayBusquedaTrabajo.size();i++){
				Sesion sesion = new Sesion();
				sesion=	MainActivity.almacenPrograma.buscarTrabajoEnSesion(arrayBusquedaTrabajo.get(i)); //obtenemos la sesion del trabajo
			
				arrayBusquedaSesion.add(sesion);
			}

			// Filtramos trabajos que no tienen coincidencias en la busqueda
			ArrayList<Trabajo> arrayTrabajo = new ArrayList<Trabajo>();
			ArrayList<Autor> arrayAutor = new ArrayList<Autor>();
			for (int j=0; j < arrayBusquedaSesion.size(); j++){
				Sesion sesion = arrayBusquedaSesion.get(j);
				arrayTrabajo = sesion.getListaPapers();
				
					ArrayList<Integer> indices = new ArrayList<Integer>();
					for (int k=0; k< arrayTrabajo.size();k++){
						Trabajo trabajo = arrayTrabajo.get(k);
						arrayAutor = arrayTrabajo.get(k).getListaAutores();
						
						//primero buscamos en los campos de trabajo, sino encontramos se busca en autores
						if (containsIgnoreCase(trabajo.getTitle(),buscar) || (containsIgnoreCase(trabajo.getKeywords(),buscar)) ){
							
						}else{
							if (arrayAutor != null){
								boolean encontrado=false;
								for (int l=0 ; l < arrayAutor.size(); l++){
									Autor autor = arrayAutor.get(l);
									if (containsIgnoreCase(autor.getLastname(),buscar) || (containsIgnoreCase(autor.getName(),buscar))){
										encontrado=true;
									}
								}
								
								if (!encontrado){
									//arrayBusquedaSesion.get(j).getListaPapers().remove(trabajo);
									indices.add(k);
								}
								
							}else{
								//arrayBusquedaSesion.get(j).getListaPapers().remove(trabajo);
								indices.add(k);
							}
						}
						
						//borramos en el ultimo indice
						if(k == (arrayTrabajo.size()-1)){
							/*for(int m=indices.size()-1; m>=0;m--){
								arrayBusquedaSesion.get(j).getListaPapers().remove(indices.get(m));
							}*/
							/*Collection col = new Collection();*/
							Collections.sort(indices, Collections.reverseOrder());
							for (int i : indices)
								arrayBusquedaSesion.get(j).getListaPapers().remove(i);
							
						}

				}
			
				
			}
			
			//fin de filtrado
			
			//filtramos busquedas repetidas
			//Creamos un objeto HashSet
			HashSet hs = new HashSet();
			//Lo cargamos con los valores del array, esto hace quite los repetidos
			hs.addAll(arrayBusquedaSesion);
			//Limpiamos el array
			arrayBusquedaSesion.clear();
			//Agregamos los valores sin repetir
			arrayBusquedaSesion.addAll(hs);
			
		}
		
		
	
		// inicio de viewpageindicator with sherlock bar
		mAdapter = new FragmentBusquedaAdapter(getSupportFragmentManager());

		mPager = (ViewPager) findViewById(R.id.pagerSearch);
		mPager.setAdapter(mAdapter);

		mIndicator = (TitlePageIndicator) findViewById(R.id.indicatorSearch);
		mIndicator.setViewPager(mPager);

		// fin de viewpage
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		getSherlock().getMenuInflater().inflate(R.menu.busqueda, menu);
		return true;
	}
	
	public boolean containsIgnoreCase( String cadena, String buscado ) {
		  if(buscado.equals(""))
		    return true;
		  if(cadena == null || buscado == null || cadena .equals(""))
		    return false; 

		  Pattern p = Pattern.compile(buscado,Pattern.CASE_INSENSITIVE+Pattern.LITERAL);
		  Matcher m = p.matcher(cadena);
		  return m.find();
	}
	
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
