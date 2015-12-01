package com.jorgeldra.seio.entidad;

import java.util.ArrayList;


public interface InterfazPrograma {
	public void guardarPrograma(String fecha, ArrayList <Sesion> listaSesion);
	public void guardarPrograma(Programa programa);
	public ArrayList<Programa> listaPrograma() ;
	public void liberarListaPrograma() ;
	public void actualizarListaPrograma(ArrayList<Programa> listPrograma) ;
	public Trabajo getTrabajo(int fragmentPos, int groupPosition, int childPosition);
	public Sesion  buscarTrabajoEnSesion(Trabajo trabajo);
	public Programa  buscarSesionEnPrograma(Sesion sesion);
	public int obtenerPosicionTrabajo(Trabajo trabajo);
}
