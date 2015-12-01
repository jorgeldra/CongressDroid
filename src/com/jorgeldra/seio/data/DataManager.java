package com.jorgeldra.seio.data;

import java.util.ArrayList;



import com.jorgeldra.seio.entidad.Autor;
import com.jorgeldra.seio.entidad.Conferencia;
import com.jorgeldra.seio.entidad.CustomSection;
import com.jorgeldra.seio.entidad.Location;
import com.jorgeldra.seio.entidad.Programa;
import com.jorgeldra.seio.entidad.Sesion;
import com.jorgeldra.seio.entidad.Trabajo;

/**
 * Android DataManager interface used to define data operations.
 * 
 * @author jorge diaz
 * 
 */
public interface DataManager {
	//db
	public void closeDb() ;
	public boolean checkDataBase();
	// conferencia
	
	public Conferencia getConferencia(long conferenciaId);
	public ArrayList<Conferencia> getAllConferencias();
	public Conferencia findConferencia(String name);
	public long saveConferencia(Conferencia conferencia);
	public void deleteConferencia(Conferencia conferencia);
	
	//favoritos
	public long saveFavorito(int trabajo_id);
	public void deleteFavorito(int trabajo_id);
	public boolean updateFavoritoEnTrabajo(String valor, int id_trabajo);
	
	//programa
	public ArrayList<Programa> obtenerListaPrograma();
	
	//trabajos
	public ArrayList<Trabajo> getAllFavouritePapers();
	
	//customSection
	public ArrayList<CustomSection> obtenerListaCustomSection();
	
	
	//autores
	public ArrayList<Autor> obtenerListaAutores();

	//sesion
	
	public ArrayList<Sesion> obtenerListaSesionesPorFechaDia();
	public ArrayList<Location> obtenerSedes();
}
