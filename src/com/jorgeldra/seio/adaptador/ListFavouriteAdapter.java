package com.jorgeldra.seio.adaptador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jorgeldra.seio.MainActivity;
import com.jorgeldra.seio.MyProgramActivity;
import com.jorgeldra.seio.PaperActivity;
import com.jorgeldra.seio.data.DataManager;
import com.jorgeldra.seio.data.DataManagerImpl;
import com.jorgeldra.seio.entidad.Sesion;
import com.jorgeldra.seio.entidad.Trabajo;


@SuppressLint("SimpleDateFormat")
public class ListFavouriteAdapter extends BaseAdapter {
	
	private final Activity actividad;
	private final ArrayList<Trabajo> lista;
	private DataManager dataManager;
	
	public ListFavouriteAdapter(Activity actividad,ArrayList<Trabajo> lista)  {
		// TODO Auto-generated constructor stub
		super();
		this.actividad = actividad;
		this.lista = lista;
		dataManager = new DataManagerImpl(this.actividad);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lista.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lista.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = actividad.getLayoutInflater();

		View view = inflater.inflate(com.jorgeldra.seio.R.layout.program_detail_item_trabajo, parent,false);
		
		// caja de texto titulo
		
		TextView txtTitulo = (TextView) view.findViewById(com.jorgeldra.seio.R.id.itemTitleTrabajo);

        txtTitulo.setText(lista.get(position).getTitle());
        
        txtTitulo.setOnClickListener(new View.OnClickListener(){

            @Override
			public void onClick(View v) {
                // TODO Auto-generated method stub
             
            	Intent i = new Intent(v.getContext(), PaperActivity.class);
            	i.putExtra("posListaFavoritos", position);
            	i.putExtra("activity","favoritos");
		    	//Toast.makeText(getActivity(),pos +"+"+ childPosition +""+ groupPosition,Toast.LENGTH_SHORT).show();
		    	v.getContext().startActivity(i);
            	
            }
        });
        
        //boton alarma
        
      //controlamos el evento click del boton alarma
        ImageButton hijoBtnAlarm = (ImageButton) view.findViewById(com.jorgeldra.seio.R.id.itemCreateAlarm);
        //hijoBtn.setText("Alarm");
        hijoBtnAlarm.setOnClickListener(new View.OnClickListener(){

            @Override
			public void onClick(View v) {
                // TODO Auto-generated method stub
                //Calendar cal = Calendar.getInstance();
                String startDate = MainActivity.almacenPrograma.buscarSesionEnPrograma(MainActivity.almacenPrograma.buscarTrabajoEnSesion(lista.get(position))).getFecha();
                String startHour = MainActivity.almacenPrograma.buscarTrabajoEnSesion(lista.get(position)).getStart();
                String endHour = MainActivity.almacenPrograma.buscarTrabajoEnSesion(lista.get(position)).getEnd();
                Date initDate = null;
                Date endDate = null;

                try {
                   initDate = new SimpleDateFormat("yyyy-MM-dd-HH:mm").parse(startDate+"-"+startHour);
                   endDate = new SimpleDateFormat("yyyy-MM-dd-HH:mm").parse(startDate+"-"+endHour);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                
            
		        Intent intent = new Intent(Intent.ACTION_EDIT);
		        intent.setType("vnd.android.cursor.item/event");
		        intent.putExtra("eventLocation", MainActivity.almacenPrograma.buscarTrabajoEnSesion(lista.get(position)).getLocation().getName() + " "+ MainActivity.almacenPrograma.buscarTrabajoEnSesion(lista.get(position)).getLocation().getVenue() );
		        intent.putExtra("beginTime",  initDate.getTime()); 
		        // intent.putExtra("eventColor",Color.RED);  no funciona el paso del color al calendario
		        intent.putExtra("endTime",endDate.getTime());
		        intent.putExtra("description",lista.get(position).getText());
		        intent.putExtra("allDay", false);
		        //intent.putExtra("rrule", "FREQ=YEARLY");
		       
		        intent.putExtra("title", lista.get(position).getTitle());
		        v.getContext().startActivity(intent);
            }
        });
        
        
        
      //controlamos el evento click del boton alarma
        final ImageButton hijoBtnFavorito = (ImageButton) view.findViewById(com.jorgeldra.seio.R.id.itemAddFavourite);
        hijoBtnFavorito.setImageResource(com.jorgeldra.seio.R.drawable.rating_important); 
        //Toast.makeText(convertView.getContext(),"estado " + MainActivity.almacenPrograma.listaPrograma().get(fragmentPos).getListaSesion().get(groupPosition).getListaPapers().get(childPosition).getFavourite(), Toast.LENGTH_SHORT).show();
      	

        hijoBtnFavorito.setOnClickListener(new View.OnClickListener(){

            @Override
			public void onClick(View v) {
            	
            	Trabajo trabajo = new Trabajo();
            	trabajo = lista.get(position);
				
            	//TODO borrar el trabajo en el array de favoritos
            	MainActivity.almacenMisTrabajos.eliminarTrabajo(trabajo);
            	
            	notifyDataSetChanged();
            	
            	if (lista.isEmpty()){ //si la lista no tiene elementos , ocultamos el spinner
            		((MyProgramActivity) actividad).ocultarSpinner();
            	}
            	
            	//ponemos a false el campo favorito en array global
            	 Sesion sesion =  MainActivity.almacenPrograma.buscarTrabajoEnSesion(trabajo);
            	 int posTrabajo = MainActivity.almacenPrograma.obtenerPosicionTrabajo(trabajo);
            	 sesion.getListaPapers().get(posTrabajo).setFavourite(false);
            	 
            	//borramos de la tabla favorito en base de datos
            	 dataManager.deleteFavorito(trabajo.getId());
            	 //ponemos a false el campo favorito de tabla trabajo
            	 dataManager.updateFavoritoEnTrabajo("false", trabajo.getId());
            	 
            	 
            	//setNotifyOnChange(true);
            	//View parent = (View)v.getParent();
            	
            	
                  //Spinner spinner = (Spinner) v.findViewById(com.jorgeldra.seio.R.id.spinnger_ordenarFavorito);
                    //spinner.setVisibility(View.GONE);
                
            	
            	//this.setList(MainActivity.almacenMisTrabajos.listaTrabajo());
            	//this.notifyDataSetChanged();
            	
            	// Refresh main activity upon close of dialog box
            	//Intent i = new Intent(v.getContext(), MyProgramActivity.class);
            	
            	//v.getContext().startActivity(i);
            	
            	//lanza una nueva pero no borra
            	
            	//mirar refresh datos listview
            	
            		
            	//TODO borrar el trabajo en la base de datos de tabla favoritos
             		
            		 //Toast.makeText(v.getContext(),"desactivado " + MainActivity.almacenPrograma.listaPrograma().get(fragmentPos).getListaSesion().get(groupPosition).getListaPapers().get(childPosition).getFavourite(), Toast.LENGTH_SHORT).show();
                
            		//TODO tendria que poderse recargar la p‡gina cuando se borre y mostrar un cargando.
            	
            	}
            
            
        });

        
        
		return view;
	}

	

}
