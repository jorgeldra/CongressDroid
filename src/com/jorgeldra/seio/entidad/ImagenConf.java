package com.jorgeldra.seio.entidad;

public class ImagenConf {

	private int id;
	private String url;
	private int idConf;
	
	public ImagenConf() {
		
	}
	public ImagenConf(String url) {
		this.url = url;
	}
	
	public ImagenConf(int idConf, String url) {
		this.idConf = idConf;
		this.url = url;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public int getIdConf() {
		return idConf;
	}
	public void setIdConf(int idConf) {
		this.idConf = idConf;
	}

}
