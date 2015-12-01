package com.jorgeldra.seio.entidad;

import java.util.ArrayList;

public interface InterfazConferencia {
	public void guardarConferencia(String title,String title2, String presentation, String home_text, ArrayList<ImagenConf> listaImagenesConf);
	public void guardarConferencia(Conferencia conferencia);
	public ArrayList<Conferencia> listaConferencia();
	public void liberarListaConferencia();
	public void actualizarListaConferencia(ArrayList<Conferencia> listConferencia);
}
