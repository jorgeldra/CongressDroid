package com.jorgeldra.seio.adaptador;

import java.util.ArrayList;

import com.jorgeldra.seio.BusquedaActivity;
import com.jorgeldra.seio.PaperActivity;
import com.jorgeldra.seio.data.DataManager;
import com.jorgeldra.seio.data.DataManagerImpl;
import com.jorgeldra.seio.entidad.Autor;
import com.jorgeldra.seio.utils.ImageDownloader;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAutoresAdapter extends BaseAdapter {
	
	private final Activity actividad;
	private final ArrayList<Autor> lista;
	private DataManager dataManager;
	private final ImageDownloader imageDownloader = new ImageDownloader();

	public ListAutoresAdapter(Activity actividad,ArrayList<Autor> lista) {
		// TODO Auto-generated constructor stub
		super();
		this.actividad = actividad;
		this.lista = lista;
		dataManager = new DataManagerImpl(this.actividad);
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = actividad.getLayoutInflater();
		View view = inflater.inflate(com.jorgeldra.seio.R.layout.autores_detail_item, parent,false);
		ImageView image = (ImageView) view.findViewById(com.jorgeldra.seio.R.id.itemListAutorImage);
		imageDownloader.download(getImageUrl(position), image);
		
		TextView txtNombre = (TextView) view.findViewById(com.jorgeldra.seio.R.id.itemListAutorName);
		txtNombre.setTypeface(null, Typeface.BOLD);
        txtNombre.setText(lista.get(position).getLastname() + ", " + lista.get(position).getName()  );
        
        txtNombre.setOnClickListener(new View.OnClickListener(){

            @Override
			public void onClick(View v) {
                // TODO Auto-generated method stub
            	
            	Intent i;
        		i = new Intent(v.getContext(), BusquedaActivity.class);
        		i.putExtra("busqueda", lista.get(position).getLastname() );
        		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        		v.getContext().startActivity(i);

            	
            }
        });
        
		return view;
		
	}
	
	public String getImageUrl(int position){
		String imagen=null;
		switch (position){
		/*case 1: 
			imagen="http://www.jamsadr.com/files/Professional/1fb60f23-00d5-4c43-a552-63321c9ed969/Presentation/HighResPhoto/Person-Donald-900x1080.jpg";
			break;
		case 2:
			imagen="http://barretthonors.asu.edu/wp-content/uploads/2012/08/Nicole-Person-Rennell.jpg";
			break;
		case 3:
			imagen="http://blog.homerealestate.com/wp-content/uploads/2010/04/Person.Ashley.jpg";
			break;
		case 4:
			imagen="http://www.site-kconstructionzone.com/wp-content/uploads/2010/05/PR-Person-G-Smith1.jpg";
			break;
		case 5:
			imagen="http://www.piratealumni.com/s/722/images/editor/headshots/AshleyPerson.jpg";
			break;
		case 6:
			imagen="http://www.elblogdelinfo.com/wp-content/uploads/PERSON-CONSULTING.jpg";
			break;
		case 7:
			imagen="http://www.primelog.com/Files/Billeder/COM/Media/unitedlog_spokes-person_lena-ridstrom_download.jpg.jpg";
			break;
		case 8:
			imagen="http://media.bzresults.net/static/c/2169/uploads/salestestimonial-1493865232.jpg";
			break;
		case 9:
			imagen="http://www.braehler.com/tl_files/braehler.com/rental/08-contact%20person/tma_hp.jpg";
			break;
		case 10:
			imagen="http://www.tvchoicemagazine.co.uk/sites/default/files/imagecache/interview_image/intex/michael_emerson.png";
			break;
		case 11:
			imagen="http://static1.wikia.nocookie.net/__cb20131110170732/althistory/images/3/39/Person_2.jpg";
			break;
		case 12:
			imagen="http://bhaboise.com/wp-content/uploads/2013/04/Person-Melanie-2012-07-4x6-1-731x1024.jpg";
			break;
		case 13:
			imagen="http://www3.pictures.gi.zimbio.com/Missing+Person+2009+Sundance+Portrait+Session+EXajgE-BUfSl.jpg";
			break;
		case 14:
			imagen="http://medias.unifrance.org/medias/43/136/34859/format_page/the-beautiful-person.jpg";
			break;
		case 15:
			imagen="http://www4.pictures.gi.zimbio.com/2009+MusiCares+Person+Year+Gala+Arrivals+KeLTnV9gzSKl.jpg";
			break;
		case 16:
			imagen="http://www.labutaca.net/noticias/wp-content/uploads/2012/10/maria-bello-third-person.jpg";
			break;
		case 17:
			imagen="http://www.greatyarmouthmercury.co.uk/polopoly_fs/rickyroys_1_1486029!image/612816101.jpg_gen/derivatives/landscape_630/612816101.jpg";
			break;
		case 18:
			imagen="http://a.abcnews.com/images/Technology/gty_steve_jobs_ll_111214_wg.jpg";
			break;
		case 19:
			imagen="http://www.biografiasyvidas.com/reportaje/valentino_rossi/fotos/rossi340a.jpg";
			break;*/
		default:
			imagen="http://www.gmrv.es/~motaduy/logoUrjc.gif";
			break;
		}
		
		return imagen;
	}

}
