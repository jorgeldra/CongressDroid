package com.jorgeldra.seio.adaptador;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.jorgeldra.seio.BusquedaActivity;
import com.jorgeldra.seio.MainActivity;

public class MyListFragmentProgramBusqueda extends SherlockFragment {

	
	
	private int pos;
	
	public static MyListFragmentProgramBusqueda newInstance(int pos) {

		MyListFragmentProgramBusqueda fragment = new MyListFragmentProgramBusqueda();
		fragment.pos = pos;
		
		return fragment;
	}
	
	public int getPos(){
		return this.pos;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(com.jorgeldra.seio.R.layout.program_detail,container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//calcular el ancho de la pantalla para colocar a la derecha el boton de desplegar
		
		
			DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
			int width;
			width = metrics.widthPixels;

			ExpandableListView l = (ExpandableListView) getView().findViewById(com.jorgeldra.seio.R.id.expandablelist);
			if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT) {
				l.setIndicatorBounds(width - GetDipsFromPixel(50), width - GetDipsFromPixel(10));
				
			 } else {
				l.setIndicatorBoundsRelative(width - GetDipsFromPixel(50), width - GetDipsFromPixel(10)); //Solo compatible a partir de 4.3 , poner icono a la derecha
					
			 }
			//MyListProgramBusquedaAdapter adaptador = new MyListProgramBusquedaAdapter(getActivity(), BusquedaActivity.arrayBusquedaSesion,BusquedaActivity.arrayBusquedaTrabajo, pos,MainActivity.almacenPrograma.listaPrograma().get(0).getFecha());
			MyListProgramBusquedaAdapter adaptador = new MyListProgramBusquedaAdapter(getActivity(), BusquedaActivity.arrayBusquedaSesion, pos,MainActivity.almacenPrograma.listaPrograma().get(0).getFecha());
			l.setAdapter(adaptador);
	
		
	}
	
	

	public int GetDipsFromPixel(float pixels) {
		// Get the screen's density scale
		final float scale = getResources().getDisplayMetrics().density;
		// Convert the dps to pixels, based on density scale
		return (int) (pixels * scale + 0.5f);
	}

}
