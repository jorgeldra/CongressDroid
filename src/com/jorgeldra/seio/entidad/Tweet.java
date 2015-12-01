package com.jorgeldra.seio.entidad;

import java.util.Date;

public class Tweet {
	
	private String urlImage;
	private String name;
	private String identifier;
	private Date time;
	private String text;
	public Tweet(String urlImage,String name,String identifier,Date time,String text) {
		// TODO Auto-generated constructor stub
		this.urlImage = urlImage;
		this.name = name;
		this.identifier = identifier;
		this.time = time;
		this.text = text;
	}
	public String getUrlImage() {
		return urlImage;
	}
	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

}
