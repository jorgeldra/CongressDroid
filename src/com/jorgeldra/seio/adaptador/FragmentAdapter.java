package com.jorgeldra.seio.adaptador;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jorgeldra.seio.MainActivity;

public class FragmentAdapter extends FragmentPagerAdapter {

	private String[] titles;

	public FragmentAdapter(FragmentManager fm) {
		super(fm);
		recuperarFechasPrograma();
	}

	public CharSequence getPageTitle(int position) {

		return titles[position];
	}

	@Override
	public Fragment getItem(int position) {
		System.out.println(position);
		//return MyFragment.newInstance("This is fragment " + position);
		
		return MyListFragmentProgram.newInstance(position);
	}

	@Override
	public int getCount() {

		return titles.length;
	}
	
	public void recuperarFechasPrograma(){
		titles  = new String[MainActivity.almacenPrograma.listaPrograma().size()];
		for(int i=0;i < MainActivity.almacenPrograma.listaPrograma().size();i++){
			String fecha = MainActivity.almacenPrograma.listaPrograma().get(i).getFecha();
			String desiredDateString="";
			SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat desiredFormat = new SimpleDateFormat("EEEE,dd MMMM",new Locale("es_ES"));
			
			Date dt =null;
			try {
				dt = sourceFormat.parse(fecha);
				desiredDateString = desiredFormat.format(dt);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			//titles.add(fecha);
			titles[i] = desiredDateString;
		}
		
	}

}
/*
 * 
 * public class MyAdapter extends FragmentPagerAdapter {
    	private static final int FRAGMENT_PROGRAMA = 0;
        private static final int FRAGMENT_SPACE_1 = 1;
        private static final int FRAGMENT_SPACE_2 = 2;
        private static final int FRAGMENT_SPACE_3 = 3;
        private static final int FRAGMENT_SPACE_4 = 4;
        private static final int FRAGMENT_SPACE_5 = 5;
        private static final int FRAGMENT_SPACE_6 = 6;
        
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_FRAGMENTS;
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(TAG, "getItem (Fragment:" + position);
            Fragment fragment = null;

            switch (position) {
            
            case FRAGMENT_PROGRAMA:
                fragment = new CalendarOpenSpaceFragment();
                ((CalendarOpenSpaceFragment)fragment).setSpaceId(position);
                break;
                
            case FRAGMENT_SPACE_1:
                fragment = new CalendarOpenSpaceFragment();
                ((CalendarOpenSpaceFragment)fragment).setSpaceId(position);
                break;
                
            case FRAGMENT_SPACE_2:
                fragment = new CalendarOpenSpaceFragment();
                ((CalendarOpenSpaceFragment)fragment).setSpaceId(position);
                break;
                
            case FRAGMENT_SPACE_3:
                fragment = new CalendarOpenSpaceFragment();
                ((CalendarOpenSpaceFragment)fragment).setSpaceId(position);
                break;
                
            case FRAGMENT_SPACE_4:
                fragment = new CalendarOpenSpaceFragment();
                ((CalendarOpenSpaceFragment)fragment).setSpaceId(position);
                break;
                
            case FRAGMENT_SPACE_5:
                fragment = new CalendarOpenSpaceFragment();
                ((CalendarOpenSpaceFragment)fragment).setSpaceId(position);
                break;
                
            case FRAGMENT_SPACE_6:
                fragment = new CalendarOpenSpaceFragment();
                ((CalendarOpenSpaceFragment)fragment).setSpaceId(position);
                break;

            default:
                break;
            }
            return fragment;
        }
        
        @Override
        public CharSequence getPageTitle(int position) {
            String fragment = null;

            switch (position) {
            
            case FRAGMENT_PROGRAMA:
                fragment = "Programa";
                break;
                
            case FRAGMENT_SPACE_1:
                fragment = "Open Space 1";
                break;
                
            case FRAGMENT_SPACE_2:
                fragment = "Open Space 2";
                break;
                
            case FRAGMENT_SPACE_3:
                fragment = "Open Space 3";
                break;
                
            case FRAGMENT_SPACE_4:
                fragment = "Open Space 4";
                break;
                
            case FRAGMENT_SPACE_5:
                fragment = "Open Space 5";
                break;
                
            case FRAGMENT_SPACE_6:
                fragment = "Open Space 6";
                break;

            default:
                break;
            }
            return fragment;
        }

    }*/
