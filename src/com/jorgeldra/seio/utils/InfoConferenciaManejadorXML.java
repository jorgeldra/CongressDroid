package com.jorgeldra.seio.utils;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.jorgeldra.seio.entidad.AlmacenConferencia;
import com.jorgeldra.seio.entidad.Conferencia;

public class InfoConferenciaManejadorXML  extends DefaultHandler{
	
	//private String conferencia_url = "http://www.seio2012.com/es/api/conference_info.xml";
	//private String conferencia_url = "http://seio2012.site50.net/conference_info.xml";

	private AlmacenConferencia interfazConferencia;
	private Globals global = new Globals();
	
	private boolean in_root = false;
	private boolean in_title = false;
	private boolean in_title2 = false;
	private boolean in_presentation = false;
	private boolean in_home_text = false;
	private boolean in_cover_image = false;
	private boolean in_element = false;
	
	StringBuilder builder;

	private Conferencia DataSet;

	public InfoConferenciaManejadorXML() {
		// TODO Auto-generated constructor stub
		super();
		interfazConferencia = new AlmacenConferencia();
	}
	
	public void inicializarParser() {
		try {

			URL url = new URL(global.getURL_CONFERENCIA());

			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();

			XMLReader xr = sp.getXMLReader();
			
	        xr.setContentHandler(this);
			
			InputSource is = new InputSource(url.openStream());
	        is.setEncoding("UTF-8");
	        xr.parse(is);
		} catch (Exception e) {
			Log.e("Congress", "error", e);
		}
	}

	

	public ArrayList<Conferencia> getParsedCategoryDataSets() {
		return this.interfazConferencia.listaConferencia();
	}

	public ArrayList<Conferencia> getParsedData() {
		return this.interfazConferencia.listaConferencia();
	}

	@Override
	public void startDocument() throws SAXException {
		this.interfazConferencia = new AlmacenConferencia();
	}

	@Override
	public void endDocument() throws SAXException {
		// No hacemos nada
	}

	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		if (localName.equals("root")) {
			this.in_root = true;
			DataSet = new Conferencia();
		} else if (localName.equals("title")) {
			this.in_title = true;
			builder = new StringBuilder();
		} else if (localName.equals("title2")) {
			this.in_title2 = true;
			builder = new StringBuilder();
		}else if (localName.equals("presentation")) {
			this.in_presentation = true;
			builder = new StringBuilder();
		}else if (localName.equals("home_text")) {
			this.in_home_text = true;
			builder = new StringBuilder();
		}
		else if (localName.equals("cover_image")){
			DataSet.inicializarListaImagenes();
			this.in_cover_image = true;
		}else if ((this.in_cover_image) &&(localName.equals("element"))){
			builder = new StringBuilder();
			this.in_element = true;
		}
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		
		if (localName.equals("root")) {
			this.in_root = false;
			this.interfazConferencia.guardarConferencia(DataSet);
		} else if (localName.equals("title")) {
			this.in_title = false;
		} else if (localName.equals("title2")) {
			this.in_title2 = false;
		}else if (localName.equals("presentation")) {
			this.in_presentation = false;
		}else if (localName.equals("home_text")) {
			this.in_home_text = false;
			
		}else if(localName.equals("cover_image")){
			this.in_cover_image = false;	
		}else if ((this.in_cover_image) &&(localName.equals("element"))){
			//meter aqui el guardado de imagenes por si el buffer no pilla el length total de la etiqueta xml, me escrib�a dos veces en el array para una sola etiqueta, al partir el buffer
			DataSet.añadirImagenEnListaImagenes(builder.toString());
			this.in_element = false;
		}
	}

	@Override
	public void characters(char ch[], int start, int length) {

		if (this.in_title) {

			if (builder != null) {
				for (int i = start; i < start + length; i++) {
					builder.append(ch[i]);
				}
			}

			DataSet.setTitle(builder.toString());
		}
		if (this.in_title2) {

			if (builder != null) {
				for (int i = start; i < start + length; i++) {
					builder.append(ch[i]);
				}
			}

			DataSet.setTitle2(builder.toString());
		}
		
		if (this.in_presentation) {

			if (builder != null) {
				for (int i = start; i < start + length; i++) {
					builder.append(ch[i]);
				}
			}

			DataSet.setPresentation(builder.toString());
		}
		if (this.in_home_text) {

			if (builder != null) {
				for (int i = start; i < start + length; i++) {
					builder.append(ch[i]);
				}
			}

			DataSet.setHome_text(builder.toString());
		}
		
		if (this.in_cover_image) {
			if(this.in_element){
				if (builder != null) {
					for (int i = start; i < start + length; i++) {
						builder.append(ch[i]);
					}
				}
				
				
			}
		}
	}

}
