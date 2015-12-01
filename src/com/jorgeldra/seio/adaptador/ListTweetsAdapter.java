package com.jorgeldra.seio.adaptador;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import com.jorgeldra.seio.entidad.Trabajo;
import com.jorgeldra.seio.entidad.Tweet;
import com.jorgeldra.seio.utils.ImageDownloader;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListTweetsAdapter extends BaseAdapter {
	private final Activity actividad;
	private final ArrayList<Tweet> lista;
	 private final ImageDownloader imageDownloader = new ImageDownloader();
	
	public ListTweetsAdapter(Activity actividad,ArrayList<Tweet> lista)  {
		// TODO Auto-generated constructor stub
		super();
		this.actividad = actividad;
		this.lista = lista;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lista.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lista.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = actividad.getLayoutInflater();
		View view = inflater.inflate(com.jorgeldra.seio.R.layout.twitter_detail_item, parent,false);
		
		ImageView image = (ImageView) view.findViewById(com.jorgeldra.seio.R.id.itemListTweetImage);
		//image.setImageBitmap(getImageBitmap(lista.get(position).getUrlImage()));
		
		imageDownloader.download(lista.get(position).getUrlImage(), image);
		
		TextView txtNombre = (TextView) view.findViewById(com.jorgeldra.seio.R.id.itemListTweetName);
		txtNombre.setTypeface(null, Typeface.BOLD);
        txtNombre.setText(lista.get(position).getName());
        
        TextView txtIdentifier = (TextView) view.findViewById(com.jorgeldra.seio.R.id.itemListTweetIdentifier);
        txtIdentifier.setTextColor(parent.getContext().getResources().getColor(com.jorgeldra.seio.R.color.gris1));
        txtIdentifier.setText(lista.get(position).getIdentifier());
        
        TextView txtTiempo = (TextView) view.findViewById(com.jorgeldra.seio.R.id.itemListTweetTime);
        txtTiempo.setTextColor(parent.getContext().getResources().getColor(com.jorgeldra.seio.R.color.gris1));
        txtTiempo.setText(fechaDesde(lista.get(position).getTime().getTime()));
        
        TextView txtText = (TextView) view.findViewById(com.jorgeldra.seio.R.id.itemListTweetTexto);
       
        txtText.setText(lista.get(position).getText());
        

		return view;
	}
	
	
	
	public static String fechaDesde(Long time) {
		 int total = (int)((System.currentTimeMillis()/1000)-(time/1000));
		 if (total < 60) { // menos que un minuto (60 segundos)
		    return total+" seg";
		 } else if (total < 3600) { // menos que una hora (60*60 segundos)
		    //return (total/60)+" min y "+(total%60)+" seg";
			 return (total/60)+" mins";
		 } else if (total < 84600) { // menos que un dia (60*60*24 segundos)
		   // return (total/3600)+" horas, "+(total%3600)/60+" min y "+(total%60)+" seg";
			 int calculo = total/3600;
			 if (calculo == 1){
				 return calculo+" hora";
			 }else{
				 return calculo+" horas";
			 }
			
		 }
		// Mas de un dia
		 //return (total/84600)+" dias, "+(total%84600)/3600+" horas, "+(total%3600)/60+" min y "+(total%60)+" seg";
		 return (total/84600)+" d";
		}

}
