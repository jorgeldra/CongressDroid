package com.jorgeldra.seio.utils;

import java.util.ArrayList;

public class Globals {
	
	//LOCAL CASA
	//private String URL_PROGRAMA = "http://192.168.1.17/seio/schedule.json";
	//private String URL_CONFERENCIA = "http://192.168.1.17/seio/conference_info.xml";
	
	
	//PRODUCCION
	//private String URL_PROGRAMA = "http://www.seio2012.com/es/api/schedule.json";
	//private String URL_CONFERENCIA = "http://www.seio2012.com/es/api/conference_info.xml";
	
	//private String URL_PROGRAMA = "http:// 89.141.28.98/seio2012/api/schedule.json";
	//private String URL_CONFERENCIA = "http://89.141.28.98/seio2012/api/conference_info.xml";
	
	//GITHUB
	private String URL_PROGRAMA = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/schedule.json";
	private String URL_CONFERENCIA = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/conference_info.xml";
	
	//CUSTOM SECTIONS
	
	/*private String URL_COMITE_ORGANIZADOR = "http://www.seio2012.com/es/api/custom_section.json?id=7";
	private String URL_COMITE_CIENTIFICO = "http://www.seio2012.com/es/api/custom_section.json?id=8";
	private String URL_PREMIO_RAMIRO_MELENDRERAS = "http://www.seio2012.com/es/api/custom_section.json?id=10";
	private String URL_PREMIO_RAMIRO_MELENDRERAS_INFO = "http://www.seio2012.com/es/api/custom_section.json?id=61";
	private String URL_PREMIO_RAMIRO_MELENDRERAS_INFO2 = "http://www.seio2012.com/es/api/custom_section.json?id=62";
	private String URL_PREMIO_RAMIRO_MELENDRERAS_INFO3 = "http://www.seio2012.com/es/api/custom_section.json?id=65";
	private String URL_PREMIO_RAMIRO_MELENDRERAS_INFO4 = "http://www.seio2012.com/es/api/custom_section.json?id=72";
	private String URL_INSCRIPCION = "http://www.seio2012.com/es/api/custom_section.json?id=11";
	private String URL_FECHAS = "http://www.seio2012.com/es/api/custom_section.json?id=15";
	private String URL_SEDES = "http://www.seio2012.com/es/api/custom_section.json?id=16";
	private String URL_SEDES_INFO = "http://www.seio2012.com/es/api/custom_section.json?id=43";
	private String URL_ENVIO_TRABAJOS = "http://www.seio2012.com/es/api/custom_section.json?id=18";
	private String URL_ALOJAMIENTO = "http://www.seio2012.com/es/api/custom_section.json?id=19";
	private String URL_PREMIO_ESTADISTICA_PUBLICA = "http://www.seio2012.com/es/api/custom_section.json?id=20";
	private String URL_PROGRAMA_SOCIAL = "http://www.seio2012.com/es/api/custom_section.json?id=21";
	private String URL_COMO_LLEGAR = "http://www.seio2012.com/es/api/custom_section.json?id=23";
	private String URL_MOVERSE_EN_MADRID = "http://www.seio2012.com/es/api/custom_section.json?id=24";
	private String URL_TURISMO = "http://www.seio2012.com/es/api/custom_section.json?id=25";
	private String URL_CUOTAS = "http://www.seio2012.com/es/api/custom_section.json?id=26";
	private String URL_INFO = "http://www.seio2012.com/es/api/custom_section.json?id=28";
	private String URL_SECRETARIA_TECNICA = "http://www.seio2012.com/es/api/custom_section.json?id=30";
	private String URL_50_ANIVERSARIO = "http://www.seio2012.com/es/api/custom_section.json?id=38";
	private String URL_PROGRAMA_CIENTIFICO = "http://www.seio2012.com/es/api/custom_section.json?id=40";
	private String URL_AGENCIA_VIAJES = "http://www.seio2012.com/es/api/custom_section.json?id=46";
	private String URL_DESCUENTOS = "http://www.seio2012.com/es/api/custom_section.json?id=48";
	private String URL_TEST_PLENARIAS = "http://www.seio2012.com/es/api/custom_section.json?id=49";
	private String URL_CONFERENCIAS_PLENARIAS = "http://www.seio2012.com/es/api/custom_section.json?id=50";
	private String URL_EDUARDO_BARREDO = "http://www.seio2012.com/es/api/custom_section.json?id=52";
	private String URL_MYUNIVERSITY = "http://www.seio2012.com/es/api/custom_section.json?id=73";*/
	
	private String URL_COMITE_ORGANIZADOR = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section7.json";
	private String URL_COMITE_CIENTIFICO = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section8.json";
	private String URL_PREMIO_RAMIRO_MELENDRERAS = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section10.json";
	private String URL_PREMIO_RAMIRO_MELENDRERAS_INFO = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section61.json";
	private String URL_PREMIO_RAMIRO_MELENDRERAS_INFO2 = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section62.json";
	private String URL_PREMIO_RAMIRO_MELENDRERAS_INFO3 = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section65.json";
	private String URL_PREMIO_RAMIRO_MELENDRERAS_INFO4 = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section72.json";
	private String URL_INSCRIPCION = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section11.json";
	private String URL_FECHAS = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section15.json";
	private String URL_SEDES = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section16.json";
	private String URL_SEDES_INFO = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section43.json";
	private String URL_ENVIO_TRABAJOS = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section18.json";
	private String URL_ALOJAMIENTO = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section19.json";
	private String URL_PREMIO_ESTADISTICA_PUBLICA = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section20.json";
	private String URL_PROGRAMA_SOCIAL = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section21.json";
	private String URL_COMO_LLEGAR = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section23.json";
	private String URL_MOVERSE_EN_MADRID = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section24.json";
	private String URL_TURISMO = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section25.json";
	private String URL_CUOTAS = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section26.json";
	private String URL_INFO = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section28.json";
	private String URL_SECRETARIA_TECNICA = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section30.json";
	private String URL_50_ANIVERSARIO = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section38.json";
	private String URL_PROGRAMA_CIENTIFICO = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section40.json";
	private String URL_AGENCIA_VIAJES = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section46.json";
	private String URL_DESCUENTOS = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section48.json";
	private String URL_TEST_PLENARIAS = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section49.json";
	private String URL_CONFERENCIAS_PLENARIAS = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section50.json";
	private String URL_EDUARDO_BARREDO = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section52.json";
	private String URL_MYUNIVERSITY = "https://raw.githubusercontent.com/jorgeldra/apiTesis/master/custom_section73.json";
	
	/*private String URL_COMITE_ORGANIZADOR = "http://89.141.28.98/seio2012/api/custom_section7.json";
	private String URL_COMITE_CIENTIFICO = "http://89.141.28.98/seio2012/api/custom_section8.json";
	private String URL_PREMIO_RAMIRO_MELENDRERAS = "http://89.141.28.98/seio2012/api/custom_section10.json";
	private String URL_PREMIO_RAMIRO_MELENDRERAS_INFO = "http://89.141.28.98/seio2012/api/custom_section61.json";
	private String URL_PREMIO_RAMIRO_MELENDRERAS_INFO2 = "http://89.141.28.98/seio2012/api/custom_section62.json";
	private String URL_PREMIO_RAMIRO_MELENDRERAS_INFO3 = "http://89.141.28.98/seio2012/api/custom_section65.json";
	private String URL_PREMIO_RAMIRO_MELENDRERAS_INFO4 = "http://89.141.28.98/seio2012/api/custom_section72.json";
	private String URL_INSCRIPCION = "http://89.141.28.98/seio2012/api/custom_section11.json";
	private String URL_FECHAS = "http://89.141.28.98/seio2012/api/custom_section15.json";
	private String URL_SEDES = "http://89.141.28.98/seio2012/api/custom_section16.json";
	private String URL_SEDES_INFO = "http://89.141.28.98/seio2012/api/custom_section43.json";
	private String URL_ENVIO_TRABAJOS = "http://89.141.28.98/seio2012/api/custom_section18.json";
	private String URL_ALOJAMIENTO = "http://89.141.28.98/seio2012/api/custom_section19.json";
	private String URL_PREMIO_ESTADISTICA_PUBLICA = "http://89.141.28.98/seio2012/api/custom_section20.json";
	private String URL_PROGRAMA_SOCIAL = "http://89.141.28.98/seio2012/api/custom_section21.json";
	private String URL_COMO_LLEGAR = "http://89.141.28.98/seio2012/api/custom_section23.json";
	private String URL_MOVERSE_EN_MADRID = "http://89.141.28.98/seio2012/api/custom_section24.json";
	private String URL_TURISMO = "http://89.141.28.98/seio2012/api/custom_section25.json";
	private String URL_CUOTAS = "http://89.141.28.98/seio2012/api/custom_section26.json";
	private String URL_INFO = "http://89.141.28.98/seio2012/api/custom_section28.json";
	private String URL_SECRETARIA_TECNICA = "http://89.141.28.98/seio2012/api/custom_section30.json";
	private String URL_50_ANIVERSARIO = "http://89.141.28.98/seio2012/api/custom_section38.json";
	private String URL_PROGRAMA_CIENTIFICO = "http://89.141.28.98/seio2012/api/custom_section40.json";
	private String URL_AGENCIA_VIAJES = "http://89.141.28.98/seio2012/api/custom_section46.json";
	private String URL_DESCUENTOS = "http://89.141.28.98/seio2012/api/custom_section48.json";
	private String URL_TEST_PLENARIAS = "http://89.141.28.98/seio2012/api/custom_section49.json";
	private String URL_CONFERENCIAS_PLENARIAS = "http://89.141.28.98/seio2012/api/custom_section50.json";
	private String URL_EDUARDO_BARREDO = "http://89.141.28.98/seio2012/api/custom_section52.json";
	private String URL_MYUNIVERSITY = "http://89.141.28.98/seio2012/api/custom_section73.json";*/
	
	/*private String URL_COMITE_ORGANIZADOR = "http://192.168.1.17/seio/custom_section7.json";
	private String URL_COMITE_CIENTIFICO = "http://192.168.1.17/seio/custom_section8.json";
	private String URL_PREMIO_RAMIRO_MELENDRERAS = "http://192.168.1.17/seio/custom_section10.json";
	private String URL_PREMIO_RAMIRO_MELENDRERAS_INFO = "http://192.168.1.17/seio/custom_section61.json";
	private String URL_PREMIO_RAMIRO_MELENDRERAS_INFO2 = "http://192.168.1.17/seio/custom_section62.json";
	private String URL_PREMIO_RAMIRO_MELENDRERAS_INFO3 = "http://192.168.1.17/seio/custom_section65.json";
	private String URL_PREMIO_RAMIRO_MELENDRERAS_INFO4 = "http://192.168.1.17/seio/custom_section72.json";
	private String URL_INSCRIPCION = "http://192.168.1.17/seio/custom_section11.json";
	private String URL_FECHAS = "http://192.168.1.17/seio/custom_section15.json";
	private String URL_SEDES = "http://192.168.1.17/seio/custom_section16.json";
	private String URL_SEDES_INFO = "http://192.168.1.17/seio/custom_section43.json";
	private String URL_ENVIO_TRABAJOS = "http://192.168.1.17/seio/custom_section18.json";
	private String URL_ALOJAMIENTO = "http://192.168.1.17/seio/custom_section19.json";
	private String URL_PREMIO_ESTADISTICA_PUBLICA = "http://192.168.1.17/seio/custom_section20.json";
	private String URL_PROGRAMA_SOCIAL = "http://192.168.1.17/seio/custom_section21.json";
	private String URL_COMO_LLEGAR = "http://192.168.1.17/seio/custom_section23.json";
	private String URL_MOVERSE_EN_MADRID = "http://192.168.1.17/seio/custom_section24.json";
	private String URL_TURISMO = "http://192.168.1.17/seio/custom_section25.json";
	private String URL_CUOTAS = "http://192.168.1.17/seio/custom_section26.json";
	private String URL_INFO = "http://192.168.1.17/seio/custom_section28.json";
	private String URL_SECRETARIA_TECNICA = "http://192.168.1.17/seio/custom_section30.json";
	private String URL_50_ANIVERSARIO = "http://192.168.1.17/seio/custom_section38.json";
	private String URL_PROGRAMA_CIENTIFICO = "http://192.168.1.17/seio/custom_section40.json";
	private String URL_AGENCIA_VIAJES = "http://192.168.1.17/seio/custom_section46.json";
	private String URL_DESCUENTOS = "http://192.168.1.17/seio/custom_section48.json";
	private String URL_TEST_PLENARIAS = "http://192.168.1.17/seio/custom_section49.json";
	private String URL_CONFERENCIAS_PLENARIAS = "http://192.168.1.17/seio/custom_section50.json";
	private String URL_EDUARDO_BARREDO = "http://192.168.1.17/seio/custom_section52.json";
	private String URL_MYUNIVERSITY = "http://192.168.1.17/seio/custom_section73.json";*/
	
	
	private ArrayList<String> arrayCustomSections;
	
	public Globals() {
		// TODO Auto-generated constructor stub
		
		this.arrayCustomSections = new ArrayList<String>();
		//arrayCustomSections.add(URL_50_ANIVERSARIO);
		//arrayCustomSections.add(URL_AGENCIA_VIAJES);
		//arrayCustomSections.add(URL_ALOJAMIENTO);
		//arrayCustomSections.add(URL_COMITE_CIENTIFICO);
		//arrayCustomSections.add(URL_COMITE_ORGANIZADOR);
		arrayCustomSections.add(URL_COMO_LLEGAR);
		arrayCustomSections.add(URL_CONFERENCIAS_PLENARIAS);
		arrayCustomSections.add(URL_CUOTAS);
		//arrayCustomSections.add(URL_DESCUENTOS);
		//arrayCustomSections.add(URL_EDUARDO_BARREDO);
		//arrayCustomSections.add(URL_ENVIO_TRABAJOS);
		arrayCustomSections.add(URL_FECHAS);
		//arrayCustomSections.add(URL_INFO);
		arrayCustomSections.add(URL_INSCRIPCION);
		//arrayCustomSections.add(URL_MOVERSE_EN_MADRID);
		//arrayCustomSections.add(URL_MYUNIVERSITY);
		//arrayCustomSections.add(URL_PREMIO_ESTADISTICA_PUBLICA);
		//arrayCustomSections.add(URL_PREMIO_RAMIRO_MELENDRERAS);
		arrayCustomSections.add(URL_PREMIO_RAMIRO_MELENDRERAS_INFO);
		//arrayCustomSections.add(URL_PREMIO_RAMIRO_MELENDRERAS_INFO2);
		//arrayCustomSections.add(URL_PREMIO_RAMIRO_MELENDRERAS_INFO3);
		//arrayCustomSections.add(URL_PREMIO_RAMIRO_MELENDRERAS_INFO4);
		//arrayCustomSections.add(URL_PROGRAMA_CIENTIFICO);
		//arrayCustomSections.add(URL_PROGRAMA_SOCIAL);
		//arrayCustomSections.add(URL_SECRETARIA_TECNICA);
		arrayCustomSections.add(URL_SEDES);
		//arrayCustomSections.add(URL_SEDES_INFO);
		//arrayCustomSections.add(URL_TEST_PLENARIAS);
		//arrayCustomSections.add(URL_TURISMO);
	}
	
	
	
	
	
	public ArrayList<String> getArrayCustomSections() {
		return arrayCustomSections;
	}





	public void setArrayCustomSections(ArrayList<String> arrayCustomSections) {
		this.arrayCustomSections = arrayCustomSections;
	}





	public String getURL_COMITE_ORGANIZADOR() {
		return URL_COMITE_ORGANIZADOR;
	}


	public void setURL_COMITE_ORGANIZADOR(String uRL_COMITE_ORGANIZADOR) {
		URL_COMITE_ORGANIZADOR = uRL_COMITE_ORGANIZADOR;
	}


	public String getURL_COMITE_CIENTIFICO() {
		return URL_COMITE_CIENTIFICO;
	}


	public void setURL_COMITE_CIENTIFICO(String uRL_COMITE_CIENTIFICO) {
		URL_COMITE_CIENTIFICO = uRL_COMITE_CIENTIFICO;
	}


	public String getURL_PREMIO_RAMIRO_MELENDRERAS() {
		return URL_PREMIO_RAMIRO_MELENDRERAS;
	}


	public void setURL_PREMIO_RAMIRO_MELENDRERAS(
			String uRL_PREMIO_RAMIRO_MELENDRERAS) {
		URL_PREMIO_RAMIRO_MELENDRERAS = uRL_PREMIO_RAMIRO_MELENDRERAS;
	}


	public String getURL_PREMIO_RAMIRO_MELENDRERAS_INFO() {
		return URL_PREMIO_RAMIRO_MELENDRERAS_INFO;
	}


	public void setURL_PREMIO_RAMIRO_MELENDRERAS_INFO(
			String uRL_PREMIO_RAMIRO_MELENDRERAS_INFO) {
		URL_PREMIO_RAMIRO_MELENDRERAS_INFO = uRL_PREMIO_RAMIRO_MELENDRERAS_INFO;
	}


	public String getURL_PREMIO_RAMIRO_MELENDRERAS_INFO2() {
		return URL_PREMIO_RAMIRO_MELENDRERAS_INFO2;
	}


	public void setURL_PREMIO_RAMIRO_MELENDRERAS_INFO2(
			String uRL_PREMIO_RAMIRO_MELENDRERAS_INFO2) {
		URL_PREMIO_RAMIRO_MELENDRERAS_INFO2 = uRL_PREMIO_RAMIRO_MELENDRERAS_INFO2;
	}


	public String getURL_PREMIO_RAMIRO_MELENDRERAS_INFO3() {
		return URL_PREMIO_RAMIRO_MELENDRERAS_INFO3;
	}


	public void setURL_PREMIO_RAMIRO_MELENDRERAS_INFO3(
			String uRL_PREMIO_RAMIRO_MELENDRERAS_INFO3) {
		URL_PREMIO_RAMIRO_MELENDRERAS_INFO3 = uRL_PREMIO_RAMIRO_MELENDRERAS_INFO3;
	}


	public String getURL_PREMIO_RAMIRO_MELENDRERAS_INFO4() {
		return URL_PREMIO_RAMIRO_MELENDRERAS_INFO4;
	}


	public void setURL_PREMIO_RAMIRO_MELENDRERAS_INFO4(
			String uRL_PREMIO_RAMIRO_MELENDRERAS_INFO4) {
		URL_PREMIO_RAMIRO_MELENDRERAS_INFO4 = uRL_PREMIO_RAMIRO_MELENDRERAS_INFO4;
	}


	public String getURL_INSCRIPCION() {
		return URL_INSCRIPCION;
	}


	public void setURL_INSCRIPCION(String uRL_INSCRIPCION) {
		URL_INSCRIPCION = uRL_INSCRIPCION;
	}


	public String getURL_FECHAS() {
		return URL_FECHAS;
	}


	public void setURL_FECHAS(String uRL_FECHAS) {
		URL_FECHAS = uRL_FECHAS;
	}


	public String getURL_SEDES() {
		return URL_SEDES;
	}


	public void setURL_SEDES(String uRL_SEDES) {
		URL_SEDES = uRL_SEDES;
	}


	public String getURL_SEDES_INFO() {
		return URL_SEDES_INFO;
	}


	public void setURL_SEDES_INFO(String uRL_SEDES_INFO) {
		URL_SEDES_INFO = uRL_SEDES_INFO;
	}


	public String getURL_ENVIO_TRABAJOS() {
		return URL_ENVIO_TRABAJOS;
	}


	public void setURL_ENVIO_TRABAJOS(String uRL_ENVIO_TRABAJOS) {
		URL_ENVIO_TRABAJOS = uRL_ENVIO_TRABAJOS;
	}


	public String getURL_ALOJAMIENTO() {
		return URL_ALOJAMIENTO;
	}


	public void setURL_ALOJAMIENTO(String uRL_ALOJAMIENTO) {
		URL_ALOJAMIENTO = uRL_ALOJAMIENTO;
	}


	public String getURL_PREMIO_ESTADISTICA_PUBLICA() {
		return URL_PREMIO_ESTADISTICA_PUBLICA;
	}


	public void setURL_PREMIO_ESTADISTICA_PUBLICA(
			String uRL_PREMIO_ESTADISTICA_PUBLICA) {
		URL_PREMIO_ESTADISTICA_PUBLICA = uRL_PREMIO_ESTADISTICA_PUBLICA;
	}


	public String getURL_PROGRAMA_SOCIAL() {
		return URL_PROGRAMA_SOCIAL;
	}


	public void setURL_PROGRAMA_SOCIAL(String uRL_PROGRAMA_SOCIAL) {
		URL_PROGRAMA_SOCIAL = uRL_PROGRAMA_SOCIAL;
	}


	public String getURL_COMO_LLEGAR() {
		return URL_COMO_LLEGAR;
	}


	public void setURL_COMO_LLEGAR(String uRL_COMO_LLEGAR) {
		URL_COMO_LLEGAR = uRL_COMO_LLEGAR;
	}


	public String getURL_MOVERSE_EN_MADRID() {
		return URL_MOVERSE_EN_MADRID;
	}


	public void setURL_MOVERSE_EN_MADRID(String uRL_MOVERSE_EN_MADRID) {
		URL_MOVERSE_EN_MADRID = uRL_MOVERSE_EN_MADRID;
	}


	public String getURL_TURISMO() {
		return URL_TURISMO;
	}


	public void setURL_TURISMO(String uRL_TURISMO) {
		URL_TURISMO = uRL_TURISMO;
	}


	public String getURL_CUOTAS() {
		return URL_CUOTAS;
	}


	public void setURL_CUOTAS(String uRL_CUOTAS) {
		URL_CUOTAS = uRL_CUOTAS;
	}


	public String getURL_INFO() {
		return URL_INFO;
	}


	public void setURL_INFO(String uRL_INFO) {
		URL_INFO = uRL_INFO;
	}


	public String getURL_SECRETARIA_TECNICA() {
		return URL_SECRETARIA_TECNICA;
	}


	public void setURL_SECRETARIA_TECNICA(String uRL_SECRETARIA_TECNICA) {
		URL_SECRETARIA_TECNICA = uRL_SECRETARIA_TECNICA;
	}


	public String getURL_50_ANIVERSARIO() {
		return URL_50_ANIVERSARIO;
	}


	public void setURL_50_ANIVERSARIO(String uRL_50_ANIVERSARIO) {
		URL_50_ANIVERSARIO = uRL_50_ANIVERSARIO;
	}


	public String getURL_PROGRAMA_CIENTIFICO() {
		return URL_PROGRAMA_CIENTIFICO;
	}


	public void setURL_PROGRAMA_CIENTIFICO(String uRL_PROGRAMA_CIENTIFICO) {
		URL_PROGRAMA_CIENTIFICO = uRL_PROGRAMA_CIENTIFICO;
	}


	public String getURL_AGENCIA_VIAJES() {
		return URL_AGENCIA_VIAJES;
	}


	public void setURL_AGENCIA_VIAJES(String uRL_AGENCIA_VIAJES) {
		URL_AGENCIA_VIAJES = uRL_AGENCIA_VIAJES;
	}


	public String getURL_DESCUENTOS() {
		return URL_DESCUENTOS;
	}


	public void setURL_DESCUENTOS(String uRL_DESCUENTOS) {
		URL_DESCUENTOS = uRL_DESCUENTOS;
	}


	public String getURL_TEST_PLENARIAS() {
		return URL_TEST_PLENARIAS;
	}


	public void setURL_TEST_PLENARIAS(String uRL_TEST_PLENARIAS) {
		URL_TEST_PLENARIAS = uRL_TEST_PLENARIAS;
	}


	public String getURL_CONFERENCIAS_PLENARIAS() {
		return URL_CONFERENCIAS_PLENARIAS;
	}


	public void setURL_CONFERENCIAS_PLENARIAS(String uRL_CONFERENCIAS_PLENARIAS) {
		URL_CONFERENCIAS_PLENARIAS = uRL_CONFERENCIAS_PLENARIAS;
	}


	public String getURL_EDUARDO_BARREDO() {
		return URL_EDUARDO_BARREDO;
	}


	public void setURL_EDUARDO_BARREDO(String uRL_EDUARDO_BARREDO) {
		URL_EDUARDO_BARREDO = uRL_EDUARDO_BARREDO;
	}


	public String getURL_MYUNIVERSITY() {
		return URL_MYUNIVERSITY;
	}


	public void setURL_MYUNIVERSITY(String uRL_MYUNIVERSITY) {
		URL_MYUNIVERSITY = uRL_MYUNIVERSITY;
	}


	


	public String getURL_PROGRAMA() {
		return URL_PROGRAMA;
	}


	public void setURL_PROGRAMA(String uRL_PROGRAMA) {
		URL_PROGRAMA = uRL_PROGRAMA;
	}


	public String getURL_CONFERENCIA() {
		return URL_CONFERENCIA;
	}


	public void setURL_CONFERENCIA(String uRL_CONFERENCIA) {
		URL_CONFERENCIA = uRL_CONFERENCIA;
	}

}
