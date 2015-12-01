package com.jorgeldra.seio.entidad;

public class Autor {
	
	private int id;
	private String normalized_name;
	private String name;
	private String lastname;
	private int submitter;

	public Autor() {
		// TODO Auto-generated constructor stub
	}
	
	public Autor(int id, String normalized_name, String name, String lastname, int submitter){
		this.id = id;
		this.normalized_name = normalized_name;
		this.name = name;
		this.lastname = lastname;
		this.submitter = submitter;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNormalized_name() {
		return normalized_name;
	}

	public void setNormalized_name(String normalized_name) {
		this.normalized_name = normalized_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getSubmitter() {
		return submitter;
	}

	public void setSubmitter(int submitter) {
		this.submitter = submitter;
	}
	
	

}
