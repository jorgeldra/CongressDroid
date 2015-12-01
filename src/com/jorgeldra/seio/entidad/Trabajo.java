package com.jorgeldra.seio.entidad;

import java.util.ArrayList;

public class Trabajo {
	
	private int id;
	private String title;
	private String text;
	private String keywords;
	private ArrayList <Autor> listaAutores;
	
	private boolean  favourite;

	public Trabajo() {
		// TODO Auto-generated constructor stub
	}
	
	public Trabajo(int id, String title, String text, String keywords, ArrayList<Autor> listaAutores){
		this.id = id;
		this.title = title;
		this.text = text;
		this.keywords = keywords;
		this.listaAutores = listaAutores;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	public boolean getFavourite() {
		return favourite;
	}

	public void setFavourite(boolean favourite) {
		this.favourite = favourite;
	}

	public ArrayList<Autor> getListaAutores() {
		return listaAutores;
	}

	public void setListaAutores(ArrayList<Autor> listaAutores) {
		this.listaAutores = listaAutores;
	}
	
	public void inicializarListaAutores(){
		this.listaAutores= new ArrayList<Autor>();
	}
	
	public void añadirAutorEnListaAutores(int id, String normalized_name, String name, String lastname, int submitter){
		this.listaAutores.add(new Autor(id,normalized_name,name,lastname,submitter));
	}	

}
