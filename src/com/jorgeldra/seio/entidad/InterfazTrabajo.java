package com.jorgeldra.seio.entidad;

import java.util.ArrayList;

public interface InterfazTrabajo {
	public void guardarTrabajo(int id, String title, String text, String keywords, ArrayList<Autor> listaAutores);
	public void guardarTrabajo(Trabajo trabajo);
	public ArrayList<Trabajo> listaTrabajo() ;
	public void liberarListaTrabajo() ;
	public void actualizarListaTrabajo(ArrayList<Trabajo> listTrabajo) ;
	public void eliminarTrabajo(Trabajo trabajo);
}
