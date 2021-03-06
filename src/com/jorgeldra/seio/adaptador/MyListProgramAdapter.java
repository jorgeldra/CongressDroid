package com.jorgeldra.seio.adaptador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jorgeldra.seio.MainActivity;
import com.jorgeldra.seio.PaperActivity;
import com.jorgeldra.seio.data.DataManager;
import com.jorgeldra.seio.data.DataManagerImpl;
import com.jorgeldra.seio.entidad.Sesion;
import com.jorgeldra.seio.entidad.Trabajo;

public class MyListProgramAdapter extends BaseExpandableListAdapter  {

	private final Activity actividad;
	private final ArrayList<Sesion> lista;
	private final int fragmentPos; // le paso el id del fragment para poder controlar el evento onclick de cada child
	private final String fecha;
	private DataManager dataManager;
	
	//private final ArrayList<Trabajo> listaTrabajo;
	
	
	public MyListProgramAdapter(Activity actividad, ArrayList<Sesion> lista, int fragmentPos, String fecha) {
		// TODO Auto-generated constructor stub
		super();
		this.actividad = actividad;
		this.lista = lista;
		this.fragmentPos = fragmentPos;
		this.fecha =fecha;
		
		dataManager = new DataManagerImpl(this.actividad);
	}
	
	
	@Override
    public boolean areAllItemsEnabled()
    {
        return true;
    }


	@Override
	public Trabajo getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return lista.get(groupPosition).getListaPapers().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return lista.get(groupPosition).getListaPapers().get(childPosition).getId();
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public View getChildView(final int groupPosition,final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		String  titulo = getChild(groupPosition, childPosition).getTitle();
		 
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) actividad.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(com.jorgeldra.seio.R.layout.program_detail_item_trabajo, null);
        }

        TextView hijotxt = (TextView) convertView.findViewById(com.jorgeldra.seio.R.id.itemTitleTrabajo);

        hijotxt.setText(titulo);
        
        
        //prueba boton en listView
        
        //hijotxt.setTag(groupPosition);
        //hijotxt.setTag(childPosition);
        //hijotxt.setTag();
        hijotxt.setOnClickListener(new View.OnClickListener(){

            @Override
			public void onClick(View v) {
                // TODO Auto-generated method stub
             
            	Intent i = new Intent(v.getContext(), PaperActivity.class);
		    	
            	i.putExtra("childPosition", childPosition);
		    	i.putExtra("groupPosition", groupPosition);
		    	i.putExtra("idSession", fragmentPos);
		    	
		    	//Toast.makeText(getActivity(),pos +"+"+ childPosition +""+ groupPosition,Toast.LENGTH_SHORT).show();
		    	v.getContext().startActivity(i);
            	
            }
        });
        
        
       //controlamos el evento click del boton alarma
        ImageButton hijoBtnAlarm = (ImageButton) convertView.findViewById(com.jorgeldra.seio.R.id.itemCreateAlarm);
        //hijoBtn.setText("Alarm");
        hijoBtnAlarm.setOnClickListener(new View.OnClickListener(){

            @SuppressLint("SimpleDateFormat")
			@Override
			public void onClick(View v) {
                // TODO Auto-generated method stub
                //Calendar cal = Calendar.getInstance();
                String startDate = fecha;
                String startHour = lista.get(groupPosition).getStart();
                String endHour = lista.get(groupPosition).getEnd();
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
		        intent.putExtra("eventLocation", lista.get(groupPosition).getLocation().getName() + " "+ lista.get(groupPosition).getLocation().getVenue() );
		        intent.putExtra("beginTime",  initDate.getTime());   
		        intent.putExtra("endTime",endDate.getTime());
		        intent.putExtra("description",getChild(groupPosition, childPosition).getText());
		        intent.putExtra("allDay", false);
		        //intent.putExtra("rrule", "FREQ=YEARLY");
		       
		        intent.putExtra("title", getChild(groupPosition, childPosition).getTitle());
		        v.getContext().startActivity(intent);
            }
        });
        
      //controlamos el evento click del boton alarma
        final ImageButton hijoBtnFavorito = (ImageButton) convertView.findViewById(com.jorgeldra.seio.R.id.itemAddFavourite);
        
        //Toast.makeText(convertView.getContext(),"estado " + MainActivity.almacenPrograma.listaPrograma().get(fragmentPos).getListaSesion().get(groupPosition).getListaPapers().get(childPosition).getFavourite(), Toast.LENGTH_SHORT).show();
      	
        
        if (MainActivity.almacenPrograma.listaPrograma().get(fragmentPos).getListaSesion().get(groupPosition).getListaPapers().get(childPosition).getFavourite()){
        	hijoBtnFavorito.setImageResource(com.jorgeldra.seio.R.drawable.rating_important); 
        	hijoBtnFavorito.setTag("activado"); //habria que recuperar si esta activo o no activo
        }else{
        	hijoBtnFavorito.setImageResource(com.jorgeldra.seio.R.drawable.rating_not_important);
    		
        	 hijoBtnFavorito.setTag("desactivado"); //habria que recuperar si esta activo o no activo
        }
       
        //hijoBtn.setText("Alarm");
        hijoBtnFavorito.setOnClickListener(new View.OnClickListener(){

            @Override
			public void onClick(View v) {
            	
            	Trabajo trabajo = new Trabajo();
            	trabajo = MainActivity.almacenPrograma.getTrabajo(fragmentPos, groupPosition, childPosition);
				//Toast.makeText(v.getContext(),groupPosition +""+childPosition+""+fragmentPos, Toast.LENGTH_SHORT).show();
            	if (hijoBtnFavorito.getTag().equals("desactivado")){
            		hijoBtnFavorito.setImageResource(com.jorgeldra.seio.R.drawable.rating_important);
            		trabajo.setFavourite(true); //ponemos a true favorito en array statico de sesiones 
            		
            		//TODO a�adir el trabajo en la base de datos de tabla favoritos y de trabajos a true
            		dataManager.saveFavorito(trabajo.getId());
            		dataManager.updateFavoritoEnTrabajo("true", trabajo.getId());
            		
            		//a�adir el trabajo en el array de favoritos
            		MainActivity.almacenMisTrabajos.guardarTrabajo(trabajo);
            		
            		hijoBtnFavorito.setTag("activado");
            		 //Toast.makeText(v.getContext(),"activado " + MainActivity.almacenPrograma.listaPrograma().get(fragmentPos).getListaSesion().get(groupPosition).getListaPapers().get(childPosition).getFavourite(), Toast.LENGTH_SHORT).show();
                 	
            	}else{
            		hijoBtnFavorito.setImageResource(com.jorgeldra.seio.R.drawable.rating_not_important);
            		trabajo.setFavourite(false); 
            		//TODO borrar el trabajo en el array de favoritos
            		MainActivity.almacenMisTrabajos.eliminarTrabajo(trabajo);
            		
            		hijoBtnFavorito.setTag("desactivado");
            		//TODO borrar el trabajo en la base de datos de tabla favoritos
            		dataManager.deleteFavorito(trabajo.getId());
            		dataManager.updateFavoritoEnTrabajo("false", trabajo.getId());
            		
            		 //Toast.makeText(v.getContext(),"desactivado " + MainActivity.almacenPrograma.listaPrograma().get(fragmentPos).getListaSesion().get(groupPosition).getListaPapers().get(childPosition).getFavourite(), Toast.LENGTH_SHORT).show();
                  	
            	}
            	
            
            }
        });

        
        //fin prueba listview

        return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return lista.get(groupPosition).getListaPapers().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return lista.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return lista.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return lista.get(groupPosition).getId();
	}

	@Override
	public View getGroupView(final int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = actividad.getLayoutInflater();
		int count = 0;
		View view = inflater.inflate(com.jorgeldra.seio.R.layout.program_detail_item, parent, false);

		if (groupPosition == 0) { // si estamos en la primera posicion de la
									// lista no comparamos con la hora anterior
			TextView textViewStart = (TextView) view.findViewById(com.jorgeldra.seio.R.id.itemListStart);
			textViewStart.setText(lista.get(groupPosition).getStart().subSequence(0, 5));

		} else {

			// si la hora actual es diferente que la anterior si a�adimos la
			// hora
			if ((!lista.get(groupPosition).getStart().subSequence(0, 5).toString().equals(lista.get(groupPosition - 1).getStart().subSequence(0, 5).toString()))) {

				TextView textViewStart = (TextView) view.findViewById(com.jorgeldra.seio.R.id.itemListStart);
				textViewStart.setText(lista.get(groupPosition).getStart().subSequence(0, 5));
			} else {
				TextView textViewStart = (TextView) view.findViewById(com.jorgeldra.seio.R.id.itemListStart);
				textViewStart.setVisibility(View.GONE);
			}
		}

		TextView textViewName = (TextView) view.findViewById(com.jorgeldra.seio.R.id.itemListName);
		textViewName.setText(lista.get(groupPosition).getName());

		TextView textViewIdentifier = (TextView) view.findViewById(com.jorgeldra.seio.R.id.itemListIdentifier);
		textViewIdentifier.setText(lista.get(groupPosition).getIdentifier());

		// no hace bien la cuenta
		if (lista.get(groupPosition).getListaPapers() != null) {
			// si se dispone de trabajos
			
			for (int i = 0; i < lista.get(groupPosition).getListaPapers().size(); i++) {
				count = count + 1; // acumulamos el numero de autores
			}

			// mostramos el numero total en el listview

			TextView textViewNumTrabajos = (TextView) view.findViewById(com.jorgeldra.seio.R.id.itemListNumTrabajos);
			// textViewNumTrabajos.setText(lista.get(position).getListaPapers().size()
			// +" Trabajos |");

			if (count == 1) {
				textViewNumTrabajos.setText(count + " Trabajo |");
			} else if (count > 1) {
				textViewNumTrabajos.setText(count + " Trabajos |");
			}

		}
		// si hay moderador
		if (lista.get(groupPosition).getChairperson() != null) {
			// String prueba = getString(com.jorgeldra.seio.R.string.chairperson);

			TextView textViewModerador = (TextView) view.findViewById(com.jorgeldra.seio.R.id.itemListModerador);
			textViewModerador.setText("Moderador: "+lista.get(groupPosition).getChairperson().getName()+" "+ lista.get(groupPosition).getChairperson().getLastname());
		}
		
		
		
		
		// comprobamos si dentro del padre no tiene hijos para llamar a la vista de trabajos donde podremos agregar como favorito
		//la sesion
		if (count == 0){
			view.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View v) {
					//Toast.makeText(v.getContext(),"pincha",Toast.LENGTH_SHORT).show();
					Intent i = new Intent(v.getContext(), PaperActivity.class);
			    	i.putExtra("groupPosition", groupPosition);
			    	i.putExtra("idSession", fragmentPos);
			    	i.putExtra("activity", "programParent");
			    	v.getContext().startActivity(i);
				}
			});
		}

		return view;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	
}
