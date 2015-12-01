package com.jorgeldra.seio.entidad;

import java.util.ArrayList;

public class Conferencia {

	private int id;
	private String title;
	private String title2;
	private String presentation;
	private String home_text;
	private ArrayList<ImagenConf> listaImagenesConf;
	
	public Conferencia() {
		
	}
	
	public Conferencia(String title, String title2, String presentation, String home_text, ArrayList<ImagenConf> listaImagenesConf) {
		this.title = title;
		this.title2 = title2;
		this.presentation = presentation;
		this.home_text = home_text;
		this.listaImagenesConf = listaImagenesConf;
	}
	
	/**
	 *  Getters & setters
	 * 
	 */
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle2() {
		return title2;
	}

	public void setTitle2(String title2) {
		this.title2 = title2;
	}

	public String getPresentation() {
		return presentation;
	}

	public void setPresentation(String presentation) {
		this.presentation = presentation;
	}

	public String getHome_text() {
		return home_text;
	}

	public void setHome_text(String home_text) {
		this.home_text = home_text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<ImagenConf> getListaImagenesConf() {
		return listaImagenesConf;
	}

	public void setListaImagenesConf(ArrayList<ImagenConf> listaImagenesConf) {
		this.listaImagenesConf = listaImagenesConf;
	}
	
	public void inicializarListaImagenes(){
		this.listaImagenesConf = new ArrayList<ImagenConf>();
	}
	public void añadirImagenEnListaImagenes(String url){
		this.listaImagenesConf.add(new ImagenConf(url));
	}	

}
