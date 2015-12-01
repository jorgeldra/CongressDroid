package com.jorgeldra.seio.entidad;

import java.util.ArrayList;

public interface InterfazCustomSection {
	
	public void guardarCustomSection(String title, String content);
	public void guardarCustomSection(CustomSection customSection);
	public ArrayList<CustomSection> listaCustomSection() ;
	public void liberarListaCustomSection() ;
	public void actualizarListaCustomSection(ArrayList<CustomSection> listCustomSection) ;
	public CustomSection buscarPorTitulo(String title);

}
