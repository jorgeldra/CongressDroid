package com.jorgeldra.seio.entidad;

import java.util.ArrayList;


public class Programa {

	private String fecha;
	private ArrayList <Sesion> listaSesion;
	
	
	public Programa() {
		// TODO Auto-generated constructor stub
	}
	
	public Programa(String fecha, ArrayList <Sesion> listaSesion){
		this.fecha= fecha;
		this.listaSesion = listaSesion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public ArrayList<Sesion> getListaSesion() {
		return listaSesion;
	}

	public void setListaSesion(ArrayList<Sesion> listaSesion) {
		this.listaSesion = listaSesion;
	}
	
	public void inicializarListaSesion(){
		this.listaSesion = new ArrayList<Sesion>();
	}
	public void añadirSesionEnListaSesion(int id,String identifier,String name, String description, String comments, String start,String end,Location location,Moderador chairperson,ArrayList <Trabajo> listaPapers){
		this.listaSesion.add(new Sesion(id,identifier,name,description,comments,start,end,location,chairperson,listaPapers));
	}
	public void añadirSesionEnListaSesion(Sesion sesion){
		this.listaSesion.add(new Sesion(sesion.getId(),sesion.getIdentifier(),sesion.getName(),sesion.getDescription(),sesion.getComments(),sesion.getStart(),sesion.getEnd(),sesion.getLocation(),sesion.getChairperson(),sesion.getListaPapers()));
		
	}

}
