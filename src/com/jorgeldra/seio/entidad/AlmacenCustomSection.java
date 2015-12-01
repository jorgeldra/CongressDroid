package com.jorgeldra.seio.entidad;

import java.util.ArrayList;

public class AlmacenCustomSection implements InterfazCustomSection {

	private ArrayList<CustomSection> customSections;
	
	public AlmacenCustomSection() {
		// TODO Auto-generated constructor stub
		customSections = new ArrayList<CustomSection>();
	}

	@Override
	public void guardarCustomSection(String title, String content) {
		// TODO Auto-generated method stub
		CustomSection customSection = new CustomSection(title,content);
		customSections.add(customSection);
		
	}

	@Override
	public void guardarCustomSection(CustomSection customSection) {
		// TODO Auto-generated method stub
		customSections.add(customSection);
		
	}

	@Override
	public ArrayList<CustomSection> listaCustomSection() {
		// TODO Auto-generated method stub
		return this.customSections;
	}

	@Override
	public void liberarListaCustomSection() {
		// TODO Auto-generated method stub
		this.customSections.clear();
		
	}

	@Override
	public void actualizarListaCustomSection(
			ArrayList<CustomSection> listCustomSection) {
		// TODO Auto-generated method stub
		this.customSections = listCustomSection;
		
	}
	
	
	@Override
	public CustomSection buscarPorTitulo(String title){
		
		for (int i=0; i< customSections.size();i++){
			if (customSections.get(i).getTitle().equals(title)){
				return customSections.get(i);
			}
		}
		
		return null;
		
	}

}
