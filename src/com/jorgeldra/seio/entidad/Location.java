package com.jorgeldra.seio.entidad;

public class Location {

	private int id;
	private String name;
	private String gps_coords;
	private String venue;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGps_coords() {
		return gps_coords;
	}

	public void setGps_coords(String gps_coords) {
		this.gps_coords = gps_coords;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}
	
	public Location() {
		// TODO Auto-generated constructor stub
	}
	
	public Location(int id, String name, String gps_coords, String venue){
		this.id=id;
		this.name=name;
		this.gps_coords=gps_coords;
		this.venue = venue;
		
	}

}
