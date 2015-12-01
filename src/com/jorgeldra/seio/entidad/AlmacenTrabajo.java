package com.jorgeldra.seio.entidad;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jorgeldra.seio.MainActivity;

public class AlmacenTrabajo implements InterfazTrabajo  {
	private ArrayList<Trabajo> trabajos;
	

		// TODO Auto-generated constructor stub
		
		
		public AlmacenTrabajo(){
			// TODO Auto-generated constructor stub
			trabajos = new ArrayList<Trabajo>();
		}
		
		@Override
		public void guardarTrabajo(int id, String title, String text, String keywords, ArrayList<Autor> listaAutores) {
			// TODO Auto-generated method stub
			Trabajo trabajo = new Trabajo(id,title,text,keywords,listaAutores);
			trabajos.add(trabajo);
			
		}

		@Override
		public void guardarTrabajo(Trabajo trabajo) {
			// TODO Auto-generated method stub
			trabajos.add(trabajo);
		}

		@Override
		public ArrayList<Trabajo> listaTrabajo() {
			// TODO Auto-generated method stub
			return this.trabajos;
		}

		@Override
		public void liberarListaTrabajo() {
			// TODO Auto-generated method stub
			this.trabajos.clear();
			
		}

		@Override
		public void actualizarListaTrabajo(ArrayList<Trabajo> listTrabajo) {
			this.trabajos = listTrabajo;
			
		}
		@Override
		public void eliminarTrabajo(Trabajo trabajo){
			//trabajos.remove(trabajo); //no borra despues de hacer el volcado debido a que no es el mismo objeto al cambiar el id de autor.
			trabajos.remove(getTrabajoIndex(trabajo));
		}
		
		
		public int getTrabajoIndex(Trabajo trabajo){
			int indiceBuscado = 0;
			
			for(int i=0; i < trabajos.size();i++){
				if (trabajos.get(i).getId() == trabajo.getId())
					indiceBuscado = i;
			}
			
			return indiceBuscado;
		}
		
		

}
