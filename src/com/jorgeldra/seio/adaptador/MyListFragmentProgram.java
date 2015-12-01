package com.jorgeldra.seio.adaptador;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import com.actionbarsherlock.app.SherlockFragment;
import com.jorgeldra.seio.MainActivity;



public class MyListFragmentProgram extends SherlockFragment {

	

	private int pos;

	public static MyListFragmentProgram newInstance(int pos) {

		MyListFragmentProgram fragment = new MyListFragmentProgram();
		fragment.pos = pos;
		

		return fragment;
	}

	
	public int getPos(){
		return this.pos;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(com.jorgeldra.seio.R.layout.program_detail,container, false);
	}

	/*
	 * @Override public void onCreate(Bundle savedInstanceState) { // TODO
	 * Auto-generated method stub super.onCreate(savedInstanceState);
	 * 
	 * // Establecemos el Adapter a la Lista del Fragment //setListAdapter(new
	 * MyListProgramAdapter
	 * (getActivity(),MainActivity.almacenPrograma.listaPrograma
	 * ().get(pos).getListaSesion()));
	 * 
	 * ExpandableListView l =
	 * (ExpandableListView)getView().findViewById(com.android
	 * .seio.R.id.expandablelist); MyListProgramAdapter adaptador = new
	 * MyListProgramAdapter
	 * (getActivity(),MainActivity.almacenPrograma.listaPrograma
	 * ().get(pos).getListaSesion
	 * (),MainActivity.almacenPrograma.listaPrograma().
	 * get(pos).getListaSesion().get(0).getListaPapers());
	 * l.setAdapter(adaptador); }
	 */

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
		MyListProgramAdapter adaptador = new MyListProgramAdapter(getActivity(), MainActivity.almacenPrograma.listaPrograma().get(pos).getListaSesion(), pos,MainActivity.almacenPrograma.listaPrograma().get(pos).getFecha());

		l.setAdapter(adaptador);
		
		/* esta forma de realizar el evento onclick solo funcionaba si en el xml solo ponemos un elemento dentro del relativelayout
		 * 
		 */
		/*l.setOnChildClickListener( new OnChildClickListener()
		{
		    @Override
		    public boolean onChildClick (ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
		    {
		    
		    	Toast.makeText(getActivity(), "Ha pulsado " +MainActivity.almacenPrograma.listaPrograma().get(pos).getListaSesion().get(groupPosition).getListaPapers().get(childPosition).getTitle(),Toast.LENGTH_SHORT).show();
		    	//pasamos los valores id a la activity de trabajos
		    	
		    	Intent i = new Intent(getView().getContext(), PaperActivity.class);
		    	
		    	i.putExtra("childPosition", childPosition);
		    	i.putExtra("groupPosition", groupPosition);
		    	i.putExtra("idSession", pos);
		    	
		    	//Toast.makeText(getActivity(),pos +"+"+ childPosition +""+ groupPosition,Toast.LENGTH_SHORT).show();
		    	startActivity(i);
		    	
		        return true;
		    }
		});	*/	
		
	}
	
	

	public int GetDipsFromPixel(float pixels) {
		// Get the screen's density scale
		final float scale = getResources().getDisplayMetrics().density;
		// Convert the dps to pixels, based on density scale
		return (int) (pixels * scale + 0.5f);
	}
	
	

	

	/*
	 * @Override public void onListItemClick(ListView l, View v, int position,
	 * long id) { // TODO Auto-generated method stub super.onListItemClick(l, v,
	 * position, id);
	 * 
	 * // Mostramos un mensaje con el elemento pulsado
	 * Toast.makeText(getActivity(), "Ha pulsado " +
	 * MainActivity.almacenPrograma
	 * .listaPrograma().get(pos).getListaSesion().get(position).getName(),
	 * Toast.LENGTH_SHORT).show();
	 * 
	 * }
	 */

}
