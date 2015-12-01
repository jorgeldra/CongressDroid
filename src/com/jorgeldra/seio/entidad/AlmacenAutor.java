package com.jorgeldra.seio.entidad;

import java.util.ArrayList;

public class AlmacenAutor implements InterfazAutor {
	private ArrayList<Autor> autores;
	
	public AlmacenAutor(){
		// TODO Auto-generated constructor stub
		autores = new ArrayList<Autor>();
	}

	@Override
	public ArrayList<Autor> listaAutor() {
		// TODO Auto-generated method stub
		return this.autores;
	}

	@Override
	public void liberarListaAutor() {
		// TODO Auto-generated method stub
		this.autores.clear();
	}

	
	@Override
	public void actualizarListaAutor(ArrayList<Autor> listAutor) {
		this.autores = listAutor;
		
	}

}
