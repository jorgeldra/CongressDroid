package com.jorgeldra.seio.adaptador;

import java.util.ArrayList;

import com.jorgeldra.seio.BusquedaActivity;
import com.jorgeldra.seio.HomeActivity;
import com.jorgeldra.seio.MapActivity;
import com.jorgeldra.seio.data.DataManager;
import com.jorgeldra.seio.data.DataManagerImpl;
import com.jorgeldra.seio.entidad.Autor;
import com.jorgeldra.seio.entidad.Location;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListLocalizacionesAdapter extends BaseAdapter  {
	private final Activity actividad;
	private final ArrayList<Location> lista;
	
	public ListLocalizacionesAdapter(Activity actividad,ArrayList<Location> lista) {
		// TODO Auto-generated constructor stub
		super();
		this.actividad = actividad;
		this.lista = lista;
		//dataManager = new DataManagerImpl(this.actividad);
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
		LayoutInflater inflater = actividad.getLayoutInflater();
		View view = inflater.inflate(com.jorgeldra.seio.R.layout.localizaciones_detail_item, parent,false);
		ImageView image = (ImageView) view.findViewById(com.jorgeldra.seio.R.id.itemListLocalizacionImage);
		image.setOnClickListener(new View.OnClickListener(){

            @Override
			public void onClick(View v) {
                // TODO Auto-generated method stub
            
            	Intent i = new Intent(v.getContext(), MapActivity.class);
            	i.putExtra("gps_coord",lista.get(position).getGps_coords()+";"+lista.get(position).getVenue());
    			v.getContext().startActivity(i);
	
            }
            
        });
        
		
		if (lista.get(position).getVenue().equals("Hotel NH Eurobuilding")){
			image.setImageResource(com.jorgeldra.seio.R.drawable.nheurobuilding);
		}else if (lista.get(position).getName().equals("Real Academia de Ciencias")){
			image.setImageResource(com.jorgeldra.seio.R.drawable.realacademia);
		}else if(lista.get(position).getVenue().equals("Hotel Meli‡ Princesa")){
			image.setImageResource(com.jorgeldra.seio.R.drawable.melia);
		}else if(lista.get(position).getVenue().equals("Restaurante Samarkanda Puerta de Atocha")){
			image.setImageResource(com.jorgeldra.seio.R.drawable.samarkanda);
		}
		
		//imageDownloader.download(getImageUrl(position), image);
		
		TextView txtNombre = (TextView) view.findViewById(com.jorgeldra.seio.R.id.itemListLocalizacionName);
		txtNombre.setTypeface(null, Typeface.BOLD);
        txtNombre.setText(lista.get(position).getName());
        txtNombre.setOnClickListener(new View.OnClickListener(){

            @Override
			public void onClick(View v) {
                // TODO Auto-generated method stub
            
            	Intent i = new Intent(v.getContext(), MapActivity.class);
            	i.putExtra("gps_coord",lista.get(position).getGps_coords()+";"+lista.get(position).getVenue());
    			v.getContext().startActivity(i);
	
            }
            
        });
        
        TextView txtVenue = (TextView) view.findViewById(com.jorgeldra.seio.R.id.itemListLocalizacionVenue);
        txtVenue.setOnClickListener(new View.OnClickListener(){

            @Override
			public void onClick(View v) {
                // TODO Auto-generated method stub
            
            	Intent i = new Intent(v.getContext(), MapActivity.class);
            	i.putExtra("gps_coord",lista.get(position).getGps_coords()+";"+lista.get(position).getVenue());
    			v.getContext().startActivity(i);
	
            }
            
        });
        
		txtVenue.setTypeface(null, Typeface.BOLD);
        txtVenue.setText("("+lista.get(position).getVenue() + ")");
        
        TextView txtVerMapa = (TextView) view.findViewById(com.jorgeldra.seio.R.id.itemListLocalizacionVerMapa);
        txtVerMapa.setOnClickListener(new View.OnClickListener(){

            @Override
			public void onClick(View v) {
                // TODO Auto-generated method stub
            
            	Intent i = new Intent(v.getContext(), MapActivity.class);
            	i.putExtra("gps_coord",lista.get(position).getGps_coords()+";"+lista.get(position).getVenue());
    			v.getContext().startActivity(i);
	
            }
            
        });
        
		return view;
		
	}
	
}
