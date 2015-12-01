package com.jorgeldra.seio.adaptador;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class FragmentBusquedaAdapter extends FragmentPagerAdapter  {
	
	private String[] titles;


	public FragmentBusquedaAdapter(FragmentManager fm) {
		// TODO Auto-generated constructor stub
		super(fm);
		titles = new String[1];
		titles[0] = "Bœsqueda en sesiones y trabajos";
		//titles[1] = "Bœsqueda en trabajos";
		
		
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		//Dependiendo de si se est‡ en sesi—n o en trabajo devolvemos una lista de cada tipo.
		/*if (position==0){
			return MyListFragmentProgramBusqueda.newInstance(position);
		}else{
			return MyListFragmentProgramBusqueda.newInstance(position);
		
		}*/
		return MyListFragmentProgramBusqueda.newInstance(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return titles.length;
	}
	
	public CharSequence getPageTitle(int position) {

		return titles[position];
	}

}
