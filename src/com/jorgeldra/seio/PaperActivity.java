package com.jorgeldra.seio;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.jorgeldra.seio.R;
import com.jorgeldra.seio.adaptador.NavigationDrawerAdapter;
import com.jorgeldra.seio.entidad.Autor;
import com.jorgeldra.seio.entidad.Sesion;
import com.jorgeldra.seio.entidad.Trabajo;
import com.jorgeldra.seio.utils.ForceUpdateBBDD;


public class PaperActivity extends Activity implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener  {

	//private RibbonMenuView rbmView;
	
	//menu lateral
	private DrawerLayout mDrawer;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerList;
	private String[] mSeioTitles;
	
	//busqueda
	private SearchView mSearchView;

	// DEFINING STRING ADAPTER WHICH WILL HANDLE DATA OF LISTVIEW
	//private ArrayAdapter<String> adapter;
	private ArrayList<String> listAuthors = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paper);

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

		int idSession = 0;
		int childPosition = 0;
		int groupPosition = 0;
		int posListaFavoritos = 0;
		String activity = null;

		// obtengo los id que le pasamos desde el fragment para saber que indice
		// estoy pasando en sessiones y trabajos
		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			idSession = extras.getInt("idSession");
			childPosition = extras.getInt("childPosition");
			groupPosition = extras.getInt("groupPosition");
			posListaFavoritos = extras.getInt("posListaFavoritos");
			activity = extras.getString("activity");
		}

		//Toast.makeText(this, activity, Toast.LENGTH_SHORT).show();
		Sesion sesion = new Sesion();
		sesion = MainActivity.almacenPrograma.listaPrograma().get(idSession).getListaSesion().get(groupPosition);
		
		if (activity == null) { // si venimos de programa pinchando en los hijos

			// obtenemos la lista de autores para el id de sesion, childposiiton
			// y groupposition correspondiente.
			ArrayList<Autor> listaAutores = new ArrayList<Autor>();
			
			
			listaAutores = sesion.getListaPapers().get(childPosition).getListaAutores();
			
			TextView txtSessionName = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionName);
			txtSessionName.setText(sesion.getName());

			TextView txtSessionDescription = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionDescription);
			txtSessionDescription.setText(sesion.getDescription());
			
			TextView txtSessionDate = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionDate);
			txtSessionDate.setText(MainActivity.almacenPrograma.buscarSesionEnPrograma(sesion).getFecha());

			TextView txtSessionHora = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionHour);
			txtSessionHora.setText(sesion.getStart().substring(0, 5) + "-" + sesion.getEnd().substring(0, 5));

			TextView txtSessionLocalizacion = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionLocation);
			txtSessionLocalizacion.setText(sesion.getLocation().getName());
			
			 ImageButton btnMap = (ImageButton) findViewById(com.jorgeldra.seio.R.id.sessionLocationMap);
			// btnMap.setImageResource(com.jorgeldra.seio.R.drawable.ic_location_map); 
			 btnMap.setTag(sesion.getLocation().getGps_coords()+";"+sesion.getLocation().getVenue());
			 btnMap.setOnClickListener(new View.OnClickListener(){

		            @Override
					public void onClick(View v) {
		            	
		            	
		            	/*Intent intent = new Intent (Intent.ACTION_VIEW,Uri.parse("geo:"+ v.getTag() ));
		            
						startActivity(intent);*/
		            	Intent i = new Intent(v.getContext(), MapActivity.class);
		    			
		    			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		    			i.putExtra("gps_coord", v.getTag().toString());
		    			startActivity(i);
		    			
		            }
		     });
			 

			TextView txtSessionModerador = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionModerador);
			txtSessionModerador.setText(sesion.getChairperson().getName() + " " + sesion.getChairperson().getLastname());

			TextView txtSessionComentarios = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionComentarios);
			txtSessionComentarios.setText(sesion.getComments());

			TextView txtTituloTrabajo = (TextView) findViewById(com.jorgeldra.seio.R.id.trabajoTitulo);
			txtTituloTrabajo.setText(sesion.getListaPapers().get(childPosition).getTitle());

			TextView txtTrabajoResumen = (TextView) findViewById(com.jorgeldra.seio.R.id.trabajoTexto);
			txtTrabajoResumen.setText(sesion.getListaPapers().get(childPosition).getText());

			TextView txtTrabajoKeywords = (TextView) findViewById(com.jorgeldra.seio.R.id.trabajoKeywords);
			txtTrabajoKeywords.setText(sesion.getListaPapers().get(childPosition).getKeywords());

			ListView lvAuthors = (ListView) findViewById(com.jorgeldra.seio.R.id.listaAutores);
			listAuthors = crearArrayAutores(listaAutores);
			ListAdapter myListAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.paper_list_author,listAuthors);
			lvAuthors.setAdapter(myListAdapter);
			setListViewHeightBasedOnChildren(lvAuthors); 

		} else { 
			
			if (activity.equals("programParent")){
				
				//venimos de la activity program pero pinchando en el padre "sesion"
				
				TextView txtSessionName = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionName);
				txtSessionName.setText(sesion.getName());

				TextView txtSessionDescription = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionDescription);
				txtSessionDescription.setText(sesion.getDescription());
				
				TextView txtSessionDate = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionDate);
				txtSessionDate.setText(MainActivity.almacenPrograma.buscarSesionEnPrograma(sesion).getFecha());

				TextView txtSessionHora = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionHour);
				txtSessionHora.setText(sesion.getStart().substring(0, 5) + "-" + sesion.getEnd().substring(0, 5));

				if (MainActivity.almacenPrograma.listaPrograma().get(idSession).getListaSesion().get(groupPosition).getLocation() != null){
					TextView txtSessionLocalizacion = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionLocation);
					txtSessionLocalizacion.setText(sesion.getLocation().getName());
					
					ImageButton btnMap = (ImageButton) findViewById(com.jorgeldra.seio.R.id.sessionLocationMap);
					// btnMap.setImageResource(com.jorgeldra.seio.R.drawable.ic_location_map); 
					btnMap.setTag(sesion.getLocation().getGps_coords()+";"+sesion.getLocation().getVenue());
					 btnMap.setOnClickListener(new View.OnClickListener(){

				            @Override
							public void onClick(View v) {
				            	Intent i = new Intent(v.getContext(), MapActivity.class);
				    			
				    			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				    			i.putExtra("gps_coord", v.getTag().toString());
				    			startActivity(i);
				            }
				     });
				}
				
				
				if (MainActivity.almacenPrograma.listaPrograma().get(idSession).getListaSesion().get(groupPosition).getChairperson() != null){
					TextView txtSessionModerador = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionModerador);
					txtSessionModerador.setText(sesion.getChairperson().getName() + " "+ sesion.getChairperson().getLastname());
				}
				
		
				TextView txtSessionComentarios = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionComentarios);
				txtSessionComentarios.setText(sesion.getComments());

				
			}else if (activity.equals("BusquedaActivity")){
				
				//venimos de la activity de buscar sesion pinchando en padre
				sesion = BusquedaActivity.arrayBusquedaSesion.get(groupPosition);
				TextView txtSessionName = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionName);
				txtSessionName.setText(sesion.getName());

				TextView txtSessionDescription = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionDescription);
				txtSessionDescription.setText(sesion.getDescription());
				
				TextView txtSessionDate = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionDate);
				txtSessionDate.setText(MainActivity.almacenPrograma.buscarSesionEnPrograma(sesion).getFecha());

				TextView txtSessionHora = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionHour);
				txtSessionHora.setText(sesion.getStart().substring(0, 5) + "-" + sesion.getEnd().substring(0, 5));

				if (sesion.getLocation() != null){
					TextView txtSessionLocalizacion = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionLocation);
					txtSessionLocalizacion.setText(sesion.getLocation().getName());
					ImageButton btnMap = (ImageButton) findViewById(com.jorgeldra.seio.R.id.sessionLocationMap);
					// btnMap.setImageResource(com.jorgeldra.seio.R.drawable.ic_location_map); 
					btnMap.setTag(sesion.getLocation().getGps_coords()+";"+sesion.getLocation().getVenue());
					 btnMap.setOnClickListener(new View.OnClickListener(){

				            @Override
							public void onClick(View v) {
				            	Intent i = new Intent(v.getContext(), MapActivity.class);
				    			
				    			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				    			i.putExtra("gps_coord", v.getTag().toString());
				    			startActivity(i);
				            }
				     });
				}
				
				
				if (sesion.getChairperson() != null){
					TextView txtSessionModerador = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionModerador);
					txtSessionModerador.setText(sesion.getChairperson().getName() + " "+ sesion.getChairperson().getLastname());
				}
				
		
				TextView txtSessionComentarios = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionComentarios);
				txtSessionComentarios.setText(sesion.getComments());
			
			
			}else if (activity.equals("BusquedaActivityChild")){ //venimos de busqueda pinchando en el hijo
				
				//Trabajo trabajo = BusquedaActivity.arrayBusquedaTrabajo.get(posListaFavoritos);
				//sesion = MainActivity.almacenPrograma.buscarTrabajoEnSesion(trabajo);
				sesion = BusquedaActivity.arrayBusquedaSesion.get(groupPosition);
				
				ArrayList<Autor> listaAutores = new ArrayList<Autor>();
				
				listaAutores = BusquedaActivity.arrayBusquedaSesion.get(groupPosition).getListaPapers().get(childPosition).getListaAutores();
				
				TextView txtSessionName = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionName);
				txtSessionName.setText(sesion.getName());

				TextView txtSessionDescription = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionDescription);
				txtSessionDescription.setText(sesion.getDescription());
				
				TextView txtSessionDate = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionDate);
				txtSessionDate.setText(MainActivity.almacenPrograma.buscarSesionEnPrograma(sesion).getFecha());

				TextView txtSessionHora = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionHour);
				txtSessionHora.setText(sesion.getStart().substring(0, 5) + "-" + sesion.getEnd().substring(0, 5));

				TextView txtSessionLocalizacion = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionLocation);
				txtSessionLocalizacion.setText(sesion.getLocation().getName());
				
				ImageButton btnMap = (ImageButton) findViewById(com.jorgeldra.seio.R.id.sessionLocationMap);
				// btnMap.setImageResource(com.jorgeldra.seio.R.drawable.ic_location_map); 
				btnMap.setTag(sesion.getLocation().getGps_coords()+";"+sesion.getLocation().getVenue());
				 btnMap.setOnClickListener(new View.OnClickListener(){

			            @Override
						public void onClick(View v) {
			            	Intent i = new Intent(v.getContext(), MapActivity.class);
			    			
			    			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			    			i.putExtra("gps_coord", v.getTag().toString());
			    			startActivity(i);
			            }
			     });

				TextView txtSessionModerador = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionModerador);
				txtSessionModerador.setText(sesion.getChairperson().getName() + " " + sesion.getChairperson().getLastname());

				TextView txtSessionComentarios = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionComentarios);
				txtSessionComentarios.setText(sesion.getComments());

				TextView txtTituloTrabajo = (TextView) findViewById(com.jorgeldra.seio.R.id.trabajoTitulo);
				txtTituloTrabajo.setText(BusquedaActivity.arrayBusquedaSesion.get(groupPosition).getListaPapers().get(childPosition).getTitle());

				TextView txtTrabajoResumen = (TextView) findViewById(com.jorgeldra.seio.R.id.trabajoTexto);
				txtTrabajoResumen.setText(BusquedaActivity.arrayBusquedaSesion.get(groupPosition).getListaPapers().get(childPosition).getText());

				TextView txtTrabajoKeywords = (TextView) findViewById(com.jorgeldra.seio.R.id.trabajoKeywords);
				txtTrabajoKeywords.setText(BusquedaActivity.arrayBusquedaSesion.get(groupPosition).getListaPapers().get(childPosition).getKeywords());

				ListView lvAuthors = (ListView) findViewById(com.jorgeldra.seio.R.id.listaAutores);
				listAuthors = crearArrayAutores(listaAutores);
				ListAdapter myListAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.paper_list_author,listAuthors);
				lvAuthors.setAdapter(myListAdapter);
				setListViewHeightBasedOnChildren(lvAuthors); 

			
			
			}else{
			// si venimos de favoritos
			// obtenemos la lista de autores para el id de sesion, childposiiton
						// y groupposition correspondiente.
						ArrayList<Autor> listaAutores = new ArrayList<Autor>();
						Trabajo trabajo = new Trabajo();
						trabajo = MainActivity.almacenMisTrabajos.listaTrabajo().get(posListaFavoritos);
						
						listaAutores = trabajo.getListaAutores();

						TextView txtSessionName = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionName);
						txtSessionName.setText(MainActivity.almacenPrograma.buscarTrabajoEnSesion(trabajo).getName());

						TextView txtSessionDescription = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionDescription);
						txtSessionDescription.setText(MainActivity.almacenPrograma.buscarTrabajoEnSesion(trabajo).getDescription());
						
						TextView txtSessionDate = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionDate);
						txtSessionDate.setText(MainActivity.almacenPrograma.buscarSesionEnPrograma(MainActivity.almacenPrograma.buscarTrabajoEnSesion(trabajo)).getFecha());
						
						TextView txtSessionHora = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionHour);
						txtSessionHora.setText(MainActivity.almacenPrograma.buscarTrabajoEnSesion(trabajo).getStart().substring(0, 5)
								+ "-"
								+ MainActivity.almacenPrograma.buscarTrabajoEnSesion(trabajo).getEnd().substring(0, 5));

						TextView txtSessionLocalizacion = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionLocation);
						txtSessionLocalizacion.setText(MainActivity.almacenPrograma.buscarTrabajoEnSesion(trabajo).getLocation().getName());
						
						ImageButton btnMap = (ImageButton) findViewById(com.jorgeldra.seio.R.id.sessionLocationMap);
						// btnMap.setImageResource(com.jorgeldra.seio.R.drawable.ic_location_map); 
						 btnMap.setTag(MainActivity.almacenPrograma.buscarTrabajoEnSesion(trabajo).getLocation().getGps_coords()+";"+MainActivity.almacenPrograma.buscarTrabajoEnSesion(trabajo).getLocation().getVenue());
						 
						 btnMap.setOnClickListener(new View.OnClickListener(){

					            @Override
								public void onClick(View v) {
					            	Intent i = new Intent(v.getContext(), MapActivity.class);
					    			
					    			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					    			i.putExtra("gps_coord", v.getTag().toString());
					    			startActivity(i);
					            }
					     });

						TextView txtSessionModerador = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionModerador);
						txtSessionModerador.setText(MainActivity.almacenPrograma.buscarTrabajoEnSesion(trabajo).getChairperson().getName()
								+ " "
								+ MainActivity.almacenPrograma.buscarTrabajoEnSesion(trabajo).getChairperson().getLastname());

						TextView txtSessionComentarios = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionComentarios);
						txtSessionComentarios.setText(MainActivity.almacenPrograma.buscarTrabajoEnSesion(trabajo).getComments());

						TextView txtTituloTrabajo = (TextView) findViewById(com.jorgeldra.seio.R.id.trabajoTitulo);
						txtTituloTrabajo.setText(trabajo.getTitle());

						TextView txtTrabajoResumen = (TextView) findViewById(com.jorgeldra.seio.R.id.trabajoTexto);
						txtTrabajoResumen.setText(trabajo.getText());

						TextView txtTrabajoKeywords = (TextView) findViewById(com.jorgeldra.seio.R.id.trabajoKeywords);
						txtTrabajoKeywords.setText(trabajo.getKeywords());

						ListView lvAuthors = (ListView) findViewById(com.jorgeldra.seio.R.id.listaAutores);
						listAuthors = crearArrayAutores(listaAutores);
						ListAdapter myListAdapter = new ArrayAdapter<String>(
								getApplicationContext(), R.layout.paper_list_author,
								listAuthors);
						lvAuthors.setAdapter(myListAdapter);
						setListViewHeightBasedOnChildren(lvAuthors);
			}
		}

		/*
		 * ListView lvAuthors = (ListView)
		 * findViewById(com.jorgeldra.seio.R.id.listaAutores); listAuthors =
		 * crearArrayAutores(listaAutores); lvAuthors.setAdapter(new
		 * ArrayAdapter<String>(this, R.layout.list_author, listAuthors));
		 */

	}

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
		
		
		
		
		MenuItem menuSocial = menu.findItem(R.id.menu_social_share);
		menuSocial.setVisible(true);
		
		
		// <!--  cambiar actionbar abajo (incluir dentro de apllication) android:uiOptions="splitActionBarWhenNarrow" -->
		return true;
	}

	// mŽtodos necesarios para implementar navbar lateral izquierda
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
				case R.id.menu_social_share:
					//create the send intent  
					Intent shareIntent =  new Intent(android.content.Intent.ACTION_SEND);   
					//set the type  
					shareIntent.setType("text/plain");   
					//add a subject  
					String titulo="";
					String shareMessage = "";
					
					TextView txtFechaSesion = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionDate);
					TextView txtHoraSesion = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionHour);
					TextView txtLocalizacionSesion = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionLocation);
					
					//mostramos el titulo de la sesi—n o del trabajo
					TextView txtTituloTrabajo = (TextView) findViewById(com.jorgeldra.seio.R.id.trabajoTitulo);
					if (txtTituloTrabajo.length()>0){
						titulo= (String) txtTituloTrabajo.getText();
						TextView txtTrabajoTexto = (TextView) findViewById(com.jorgeldra.seio.R.id.trabajoTexto);
						shareMessage = (String) txtTrabajoTexto.getText(); 
						shareMessage += " "+ txtFechaSesion.getText() + " " + txtHoraSesion.getText() + " " + txtLocalizacionSesion.getText();
					}else{
						TextView txtTituloSesion = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionName);
						titulo= (String) txtTituloSesion.getText();
						TextView txtDescripcionSesion = (TextView) findViewById(com.jorgeldra.seio.R.id.sessionDescription);
						shareMessage = (String) txtDescripcionSesion.getText(); 
						shareMessage += " "+ txtFechaSesion.getText() + " " + txtHoraSesion.getText() + " " + txtLocalizacionSesion.getText();
					}
					
					
					shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,   titulo);  
					  
					//build the body of the message to be shared 
					
					  
					//add the message  
					shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,shareMessage);  
					  
					//start the chooser for sharing  
					startActivity(Intent.createChooser(shareIntent,  "Congreso Nacional de Estad’stica SEIO 2012"));
					break;
			}

			return super.onOptionsItemSelected(item);
		}

	/*@Override
	public void RibbonMenuItemClick(int itemId) {
		// TODO Auto-generated method stub
		if (itemId == R.id.ribbon_menu_home) {
			Intent i = new Intent(this, HomeActivity.class);
			// i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
		}
		if (itemId == R.id.ribbon_menu_programa) {
			Intent i = new Intent(this, ProgramActivity.class);
			// i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
		}

	}
	*/
	public ArrayList<String> crearArrayAutores(ArrayList<Autor> listaAutores) {

		ArrayList<String> arrayAutores = new ArrayList<String>();
		for (Autor autor : listaAutores) {
			arrayAutores.add(autor.getName() + " " + autor.getLastname());
		}
		// arrayAutores.add("prueba");
		return arrayAutores;

	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}

	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
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
