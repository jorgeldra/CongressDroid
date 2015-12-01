package com.jorgeldra.seio.entidad;

import java.util.ArrayList;


public class Sesion {

	private int id;
	private String identifier;
	private String name;
	private String description;
	private String comments;
	private String start;
	private String end;
	private Location location;
	private Moderador chairperson;
	private ArrayList <Trabajo> listaPapers;
	
	
	
	public Sesion() {
		// TODO Auto-generated constructor stub
	}
	
	public Sesion(int id,String identifier,String name, String description, String comments, String start,String end,Location location,Moderador chairperson,ArrayList <Trabajo> listaPapers){
		this.id = id;
		this.identifier = identifier;
		this.name = name;
		this.description = description;
		this.comments = comments;
		this.start = start;
		this.end = end;
		this.chairperson = chairperson;
		this.location = location;
		this.listaPapers = listaPapers;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Moderador getChairperson() {
		return chairperson;
	}

	public void setChairperson(Moderador chairperson) {
		this.chairperson = chairperson;
	}

	public ArrayList<Trabajo> getListaPapers() {
		return listaPapers;
	}

	public void setListaPapers(ArrayList<Trabajo> listaPapers) {
		this.listaPapers = listaPapers;
	}

	
	
	public void inicializarLocation(){
		this.location= new Location();
	}
	
	public void añadirLocalizacion(int id, String name, String gps_coord, String venue){
		this.location = new Location(id,name,gps_coord, venue);
	}
	
	public void inicializarChairperson(){
		this.chairperson= new Moderador();
	}
	
	public void añadirModerador(int id, String normalized_name, String name, String lastname){
		this.chairperson = new Moderador(id,normalized_name,name,lastname);
	}
	
	public void inicializarListaPapers(){
		this.listaPapers= new ArrayList<Trabajo>();
	}
	
	public void añadirTrabajoEnListaPapers(int id, String title,String text, String keywords, ArrayList<Autor> listaAutores){
		this.listaPapers.add(new Trabajo(id,title,text,keywords,listaAutores));
	}
	
	public void añadirTrabajoEnListaPapers(Trabajo trabajo){
		this.listaPapers.add(new Trabajo(trabajo.getId(),trabajo.getTitle(),trabajo.getText(),trabajo.getKeywords(),trabajo.getListaAutores()));
		
	}

}
