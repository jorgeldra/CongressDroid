/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jorgeldra.seio.adaptador;

import java.util.ArrayList;

import com.jorgeldra.seio.MainActivity;
import com.jorgeldra.seio.entidad.ImagenConf;
import com.jorgeldra.seio.utils.ImageDownloader;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.ImageView;


public class ImageAdapter extends BaseAdapter {

	//private ArrayList<ImagenConf> arrayImagenes = MainActivity.almacenConf.listaConferencia().get(0).getListaImagenesConf();
	private ArrayList<ImagenConf> arrayImagenes;
    private final ImageDownloader imageDownloader = new ImageDownloader();
    
    
    public ImageAdapter(ArrayList<ImagenConf> imagenes){
    	this.arrayImagenes =   imagenes;
    }
    @Override
	public int getCount() {
        //return URLS.length;
    
    	return arrayImagenes.size();
    }

    @Override
	public String getItem(int position) {
        //return URLS[position];
        return arrayImagenes.get(position).getUrl();
    }

    @Override
	public long getItemId(int position) {
        //return URLS[position].hashCode();
    	
    	return arrayImagenes.get(position).hashCode();
    }

    @Override
	public View getView(int position, View view, ViewGroup parent) {
    	View retval = LayoutInflater.from(parent.getContext()).inflate(com.jorgeldra.seio.R.layout.home_list_image, null);
		ImageView imagen = (ImageView) retval.findViewById(com.jorgeldra.seio.R.id.imageHori);
        if (view == null) {
        	
        	
        	
            view = new ImageView(parent.getContext());
        
          
            //set layout options
    	    //view.setLayoutParams(new Gallery.LayoutParams(300, 150));
            //imageView.setLayoutParams(new Gallery.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            //view.setPadding(1, 1, 1, 1);
    	    
    	   //set color view
    	    view.setBackgroundColor(Color.WHITE);
        }
        //imageDownloader.download(URLS[position], (ImageView) view);
        imageDownloader.download(arrayImagenes.get(position).getUrl(), imagen);
        
        return retval;
    }
    /* ASI ESTABA ANTES DE INCLUIR EL HORIZONTALLISTVIEW
    public View getView(int position, View view, ViewGroup parent) {
    	
        if (view == null) {
       
        	
            view = new ImageView(parent.getContext());
        
    	    view.setBackgroundColor(Color.WHITE);
        }
        //imageDownloader.download(URLS[position], (ImageView) view);
        imageDownloader.download(arrayImagenes.get(position).getUrl(), (ImageView) view);
        
        return view;
    }*/

    public ImageDownloader getImageDownloader() {
        return imageDownloader;
    }
}
