package com.jorgeldra.seio.adaptador;
import android.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class NavigationDrawerAdapter extends BaseAdapter {

	private final Activity actividad;
	//private final ArrayList<String> lista;
	private final String[] lista;

	
	//public NavigationDrawerAdapter(Activity actividad, ArrayList<String> lista) {
	public NavigationDrawerAdapter(Activity actividad, String[] lista) {
		
	// TODO Auto-generated constructor stub
		super();
		this.actividad = actividad;
		this.lista = lista;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//return lista.size();
		return lista.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		//return lista.get(position);
		return lista[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater = actividad.getLayoutInflater();

		View view = inflater.inflate(com.jorgeldra.seio.R.layout.drawer_list_item, parent,false);
	
		TextView textView = (TextView) view.findViewById(com.jorgeldra.seio.R.id.text1);
		textView.setText(lista[position]);
		
		ImageView imageView = (ImageView) view.findViewById(com.jorgeldra.seio.R.id.icono);
		//Toast.makeText(view.getContext(), "Seleccionado " + position , Toast.LENGTH_SHORT).show();
		
		
		//RelativeLayout relLayout = (RelativeLayout) view.findViewById(com.jorgeldra.seio.R.id.elemento_lista);
		
		
		//TextView textViewLinea = new TextView(view.getContext());
		//textViewLinea.setHeight(200);
		//textViewLinea.setWidth(200);
		//textViewLinea.setBackgroundResource(color.white);
		//relLayout.addView(textViewLinea);
		/*RelativeLayout relLayout = (RelativeLayout) view.findViewById(com.example.examplenavigationdrawer.R.id.elemento_lista);
		
		TextView txt1 = new TextView(view.getContext());
		txt1.setBackgroundResource(color.white);
		txt1.setHeight(200);
		txt1.setWidth(200);
		txt1.setText("prueba dos");
		relLayout.addView(txt1);*/
		
		/*View lineaHori = new View (view.getContext());
		lineaHori.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		lineaHori.setBackgroundResource(color.white);
		relLayout.addView(lineaHori);*/
		
		
		if ((position == 5)||(position == 6)||(position == 9)||(position == 10 )){
			TextView textViewLinea = (TextView) view.findViewById(com.jorgeldra.seio.R.id.lineaSeparacion2);
			textViewLinea.setVisibility(view.VISIBLE);
		}
		
		
		
		/*if (position == 2){
			//View lineaHori = (View) view.findViewById(com.example.examplenavigationdrawer.R.id.lineaSeparacion);
			
			//lineaHori.setBackgroundColor(color.holo_green_light);
			
			RelativeLayout relLayout = (RelativeLayout) actividad.findViewById(com.example.examplenavigationdrawer.R.id.elemento_lista);
					TextView txt1 = new TextView(actividad);
					txt1.setBackgroundColor(color.holo_green_light);
					txt1.setHeight(20);
					txt1.setWidth(100);
					txt1.setText("prueba dos");
					
					
					
				relLayout.addView(txt1);
					
			//View lineaHori = (View) view.findViewById(com.example.examplenavigationdrawer.R.id.lineaSeparacion);
			//View lineaHori = (View) view.findViewById(com.example.examplenavigationdrawer.R.id.lineaSeparacion);
			//lineaHori.setBackgroundColor(color.holo_green_light);
			//lineaHori.setLayoutParam(new LayoutParams(LayoutParams.,LayoutParams.WRAP_CONTENT));
		}*/
		/*
		android:layout_width="fill_parent"
		        android:layout_height="1dp"
		        android:background="@android:color/darker_gray"*/
		        
		
		switch (position){
		
		case 0:
			imageView.setImageResource(com.jorgeldra.seio.R.drawable.ic_menu_home);
			break;
		
		
		case 1:
			imageView.setImageResource(R.drawable.ic_menu_today);
			break;
			
		case 2:
			imageView.setImageResource(R.drawable.ic_menu_my_calendar);
			break;
			
		case 3:
			imageView.setImageResource(com.jorgeldra.seio.R.drawable.ic_authors);
			break;
		case 4:
			imageView.setImageResource(com.jorgeldra.seio.R.drawable.ic_conferencia_plen);
			break;
		case 5:
			imageView.setImageResource(com.jorgeldra.seio.R.drawable.ic_premio_ramiro);
			break;
		case 6:
			imageView.setImageResource(com.jorgeldra.seio.R.drawable.ic_fechas);
			break;
		case 7:
			imageView.setImageResource(com.jorgeldra.seio.R.drawable.location_place);
			break;
		case 8:
			imageView.setImageResource(com.jorgeldra.seio.R.drawable.ic_cuotas);
			break;
			
		case 9:
			imageView.setImageResource(com.jorgeldra.seio.R.drawable.ic_registration);
			break;
			
		case 10:
			imageView.setImageResource(R.drawable.ic_menu_myplaces);
			break;
			
		case 11:
			imageView.setImageResource(com.jorgeldra.seio.R.drawable.ic_twitter);
			break;
		
		
		
		default:
			//imageView.setImageResource(R.drawable.ic_launcher);
			break;
		
		
		}
		return view;
	}

}
