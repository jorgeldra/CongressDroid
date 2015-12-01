package com.jorgeldra.seio.entidad;

import java.util.ArrayList;

public class AlmacenConferencia implements InterfazConferencia{

	private ArrayList<Conferencia> conferencias;
	
	public AlmacenConferencia()  {
		// TODO Auto-generated constructor stub
		conferencias = new ArrayList<Conferencia>();
	}

	@Override
	public void guardarConferencia(String title,String title2, String presentation, String home_text, ArrayList<ImagenConf> listaImagenesConf) {
		// TODO Auto-generated method stub
		Conferencia conferencia = new Conferencia(title,title2,presentation,home_text,listaImagenesConf);
		conferencias.add(conferencia);
		
	}

	@Override
	public void guardarConferencia(Conferencia conferencia) {
		// TODO Auto-generated method stub
		conferencias.add(conferencia);
	}

	@Override
	public ArrayList<Conferencia> listaConferencia() {
		// TODO Auto-generated method stub
		return this.conferencias;
	}

	@Override
	public void liberarListaConferencia() {
		// TODO Auto-generated method stub
		this.conferencias.clear();
		
	}

	@Override
	public void actualizarListaConferencia(ArrayList<Conferencia> listConferencia) {
		this.conferencias = listConferencia;
		
	}

}
