package com.jorgeldra.seio.entidad;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jorgeldra.seio.MainActivity;


public class AlmacenPrograma implements InterfazPrograma{
	private ArrayList<Programa> programas;

	public AlmacenPrograma()  {
		// TODO Auto-generated constructor stub
		programas = new ArrayList<Programa>();
	}
	
	@Override
	public void guardarPrograma(String fecha, ArrayList <Sesion> listaSesion) {
		// TODO Auto-generated method stub
		Programa programa = new Programa(fecha,listaSesion);
		programas.add(programa);
		
	}

	@Override
	public void guardarPrograma(Programa programa) {
		// TODO Auto-generated method stub
		programas.add(programa);
	}

	@Override
	public ArrayList<Programa> listaPrograma() {
		// TODO Auto-generated method stub
		return this.programas;
	}

	@Override
	public void liberarListaPrograma() {
		// TODO Auto-generated method stub
		this.programas.clear();
		
	}

	@Override
	public void actualizarListaPrograma(ArrayList<Programa> listPrograma) {
		this.programas = listPrograma;
		
	}
	
	@Override
	public Trabajo getTrabajo(int fragmentPos, int groupPosition, int childPosition){
		 return MainActivity.almacenPrograma.listaPrograma().get(fragmentPos).getListaSesion().get(groupPosition).getListaPapers().get(childPosition);
	}
	
	
	@Override
	public Sesion  buscarTrabajoEnSesion(Trabajo trabajo){
		
		ArrayList<Programa> arrayPrograma = new ArrayList<Programa>();
		ArrayList<Sesion> arraySesion = new ArrayList<Sesion>();
		ArrayList<Trabajo> arrayTrabajo = new ArrayList<Trabajo>();
		
		arrayPrograma = MainActivity.almacenPrograma.listaPrograma();
		for( int i = 0 ; i < arrayPrograma.size() ; i++ ){
			arraySesion = MainActivity.almacenPrograma.listaPrograma().get(i).getListaSesion();
			for(int j=0; j <arraySesion.size();j++){
				arrayTrabajo = MainActivity.almacenPrograma.listaPrograma().get(i).getListaSesion().get(j).getListaPapers();
				for(int k=0; k <arrayTrabajo.size();k++){
					
					//if ( arrayPrograma.get(i).getListaSesion().get(j).getListaPapers().get(k).equals(trabajo)){
						
					if	(arrayPrograma.get(i).getListaSesion().get(j).getListaPapers().get(k).getId() == trabajo.getId()){
						return arrayPrograma.get(i).getListaSesion().get(j);
					}
				}
			}
		}
		return null;
		
	}
	
	@Override
	public Programa  buscarSesionEnPrograma(Sesion sesion){
		
		ArrayList<Programa> arrayPrograma = new ArrayList<Programa>();
		ArrayList<Sesion> arraySesion = new ArrayList<Sesion>();
		
		arrayPrograma = MainActivity.almacenPrograma.listaPrograma();
		for( int i = 0 ; i < arrayPrograma.size() ; i++ ){
			arraySesion = MainActivity.almacenPrograma.listaPrograma().get(i).getListaSesion();
			for(int j=0; j <arraySesion.size();j++){
				/*if ( arrayPrograma.get(i).getListaSesion().get(j).equals(sesion)){
					return arrayPrograma.get(i);
				}*/
				if ( arrayPrograma.get(i).getListaSesion().get(j).getId() == sesion.getId()){
					return arrayPrograma.get(i);
				}
			}
			
		}
		return null;
	}
	
	@Override
	public int obtenerPosicionTrabajo(Trabajo trabajo){
		Sesion sesion = new Sesion();
		sesion = MainActivity.almacenPrograma.buscarTrabajoEnSesion(trabajo);
		ArrayList<Trabajo> arrayTrabajo = new ArrayList<Trabajo>();		
		arrayTrabajo = sesion.getListaPapers();
		for (int i= 0; i < arrayTrabajo.size(); i++){
			/*if (arrayTrabajo.get(i).equals(trabajo))
				return i;*/
			if (arrayTrabajo.get(i).getId() == trabajo.getId())
				return i;
			
		}
		return 0;
	}
	
	public ArrayList<Sesion> obtenerBusquedaEnSesiones(String busqueda){
		ArrayList<Programa> arrayPrograma = new ArrayList<Programa>();
		arrayPrograma = MainActivity.almacenPrograma.listaPrograma();
		ArrayList<Sesion> arrayBusqueda = new ArrayList<Sesion>();
		ArrayList<Sesion> arraySesiones = new ArrayList<Sesion>();
		//recorremos las 4 sesiones
		for(int i= 0; i < arrayPrograma.size();i++){
			arraySesiones = arrayPrograma.get(i).getListaSesion();
			
			for (int j=0; j < arraySesiones.size(); j++){
				Sesion sesion = arrayPrograma.get(i).getListaSesion().get(j);
				
				//Se buscan coincidencias en el nombre de sesion, en descripcion, en el nombre de localizacion y en el moderador
				
				if (sesion.getChairperson() != null){
					if (containsIgnoreCase(sesion.getName(),busqueda) || (containsIgnoreCase(sesion.getDescription(),busqueda)) || (containsIgnoreCase(sesion.getLocation().getName(),busqueda)) || (containsIgnoreCase(sesion.getChairperson().getLastname(),busqueda)) || (containsIgnoreCase(sesion.getChairperson().getName(),busqueda))  ){
						arrayBusqueda.add(sesion);
					}
				}else{
					if (containsIgnoreCase(sesion.getName(),busqueda) || (containsIgnoreCase(sesion.getDescription(),busqueda)) || (containsIgnoreCase(sesion.getLocation().getName(),busqueda))){
						arrayBusqueda.add(sesion);
					}
				}
			
			}
			
		}
		
		return arrayBusqueda;
		
	}
	
	public ArrayList<Trabajo> obtenerBusquedaEnTrabajos(String busqueda){
		ArrayList<Programa> arrayPrograma = new ArrayList<Programa>();
		ArrayList<Sesion> arraySesiones = new ArrayList<Sesion>();
		ArrayList<Trabajo> arrayTrabajo = new ArrayList<Trabajo>();
		ArrayList<Autor> arrayAutor = new ArrayList<Autor>();
		
		ArrayList<Trabajo> arrayBusqueda = new ArrayList<Trabajo>();
		
		
		arrayPrograma = MainActivity.almacenPrograma.listaPrograma();
		//recorremos las 4 sesiones
		for(int i= 0; i < arrayPrograma.size();i++){
			arraySesiones = arrayPrograma.get(i).getListaSesion();
			
			for (int j=0; j < arraySesiones.size(); j++){
				Sesion sesion = arrayPrograma.get(i).getListaSesion().get(j);
				arrayTrabajo = sesion.getListaPapers();
				
				if (arrayTrabajo != null){
					for (int k=0; k< arrayTrabajo.size();k++){
						Trabajo trabajo = arrayTrabajo.get(k);
						arrayAutor = arrayTrabajo.get(k).getListaAutores();
						
						//primero buscamos en los campos de trabajo, sino encontramos se busca en autores
						if (containsIgnoreCase(trabajo.getTitle(),busqueda) || (containsIgnoreCase(trabajo.getKeywords(),busqueda)) ){
							arrayBusqueda.add(trabajo);
						}else{
							if (arrayAutor != null){
								for (int l=0 ; l < arrayAutor.size(); l++){
									Autor autor = arrayAutor.get(l);
									if (containsIgnoreCase(autor.getLastname(),busqueda) || (containsIgnoreCase(autor.getName(),busqueda))){
										arrayBusqueda.add(trabajo);
									}
								}
								
							}
						}
						
					}
				}
			
			
			}
			
		}
		
		return arrayBusqueda;
		
	}
	
	public boolean containsIgnoreCase( String cadena, String buscado ) {
		  if(buscado.equals(""))
		    return true;
		  if(cadena == null || buscado == null || cadena .equals(""))
		    return false; 

		  Pattern p = Pattern.compile(buscado,Pattern.CASE_INSENSITIVE+Pattern.LITERAL);
		  Matcher m = p.matcher(cadena);
		  return m.find();
	}

}
