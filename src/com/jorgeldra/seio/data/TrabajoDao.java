package com.jorgeldra.seio.data;

import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.jorgeldra.seio.data.FavoritoTabla.FavoritoColumnas;
import com.jorgeldra.seio.data.TrabajoTabla.TrabajoColumnas;
import com.jorgeldra.seio.entidad.Trabajo;

public class TrabajoDao implements Dao<Trabajo> {

	
	private static final String INSERT = "insert into "
			+ TrabajoTabla.TABLE_NAME + "(" + TrabajoColumnas.ID_TRABAJO + ", " + TrabajoColumnas.TITLE + ", "
			+ TrabajoColumnas.TEXT + ", "
			+ TrabajoColumnas.KEYWORDS + ", "
			+ TrabajoColumnas.FAVOURITE + ", "
			+ TrabajoColumnas.ID_SESION + ")" + "values (?,?,?,?,?,?)";
	
	// database fields
	private SQLiteDatabase db;
	private SQLiteStatement insertStatement;
		
	public TrabajoDao(SQLiteDatabase db) {
		// TODO Auto-generated constructor stub
		// TODO Auto-generated constructor stub
		this.db = db;
	    insertStatement = db.compileStatement(TrabajoDao.INSERT);
	}

	@Override
	public long save(Trabajo type) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public long save(Trabajo trabajo, int id_sesion) {
		insertStatement.clearBindings();
		insertStatement.bindString(1, String.valueOf(trabajo.getId()));
		insertStatement.bindString(2, String.valueOf(trabajo.getTitle()));
		insertStatement.bindString(3, String.valueOf(trabajo.getText()));
		insertStatement.bindString(4, String.valueOf(trabajo.getKeywords()));
		insertStatement.bindString(5, String.valueOf(trabajo.getFavourite()));
		insertStatement.bindString(6, String.valueOf(id_sesion));
		return insertStatement.executeInsert();
	}

	@Override
	public void update(Trabajo type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Trabajo type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Trabajo get(long id) {
		// TODO Auto-generated method stub
		Trabajo trabajo = null;
		Cursor c = db.query(TrabajoTabla.TABLE_NAME, new String[] {
				TrabajoColumnas.ID_TRABAJO, TrabajoColumnas.TITLE,
				TrabajoColumnas.TEXT, TrabajoColumnas.KEYWORDS,TrabajoColumnas.FAVOURITE }, TrabajoColumnas.ID_TRABAJO + " = ?",
				new String[] { String.valueOf(id) }, null, null, null,"1");

		if (c.moveToFirst()) {
			trabajo = this.pasarDeCursorATrabajo(c);
		}
		if (!c.isClosed()) {
			c.close();
		}
		return trabajo;
	}

	@Override
	public List<Trabajo> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Trabajo pasarDeCursorATrabajo(Cursor c) {
		Trabajo trabajo = null;
		if (c != null) {
			trabajo = new Trabajo();
			trabajo.setId(c.getInt(0));
			trabajo.setTitle(c.getString(1));
			trabajo.setText(c.getString(2));
			trabajo.setKeywords(c.getString(3));
			trabajo.setFavourite(Boolean.valueOf(c.getString(4)));
		}
		return trabajo;
	}

	@Override
	public ArrayList<Trabajo> getAllById(long id) {
		// TODO Auto-generated method stub
		ArrayList<Trabajo> list = new ArrayList<Trabajo>();
		AutorDao autorDao = new AutorDao(db);
		
		Cursor c = db.query(TrabajoTabla.TABLE_NAME, new String[] {
				TrabajoColumnas.ID_TRABAJO, TrabajoColumnas.TITLE,TrabajoColumnas.TEXT, TrabajoColumnas.KEYWORDS,
				TrabajoColumnas.FAVOURITE}, TrabajoColumnas.ID_SESION + " = ?",
				new String[] { String.valueOf(id) },  null,null,null); //ordenar por dos campos

		if (c.moveToFirst()) {
			do {
				Trabajo trabajo = this.pasarDeCursorATrabajo(c);
				/*
				Log.i("trabajo id",""+ trabajo.getId());
				if (autorDao.obtenerListaAutoresPorIdTrabajo(trabajo.getId()).size()>0){
					for(int i=0;i<autorDao.obtenerListaAutoresPorIdTrabajo(trabajo.getId()).size();i++){
						Log.i("trabajo id",""+ trabajo.getId());
						Log.i("autor", ""+autorDao.obtenerListaAutoresPorIdTrabajo(trabajo.getId()).get(i).getLastname());
					}
				}*/
				
				trabajo.setListaAutores(autorDao.obtenerListaAutoresPorIdTrabajo(trabajo.getId())); 
			
				if (trabajo != null)
					list.add(trabajo);
			} while (c.moveToNext());
		}
		if (!c.isClosed()) {
			c.close();
		}
		return list;
	}
	
	/* mŽtodo que obtiene toda la lista de trabajos en favoritos */
	public ArrayList<Trabajo> getAllFavouritePapers() {
		// TODO Auto-generated method stub
		ArrayList<Trabajo> list = new ArrayList<Trabajo>();
		AutorDao autorDao = new AutorDao(db);
		
		Cursor c = db.query(FavoritoTabla.TABLE_NAME, new String[] {
				FavoritoColumnas._ID, FavoritoColumnas.ID_TRABAJO}, null,
				null,  null,null,null); //ordenar por dos campos

		if (c.moveToFirst()) {
			do {
				
				int id_trabajo = c.getInt(1);
				Trabajo trabajo = get(id_trabajo);
				
				//trabajo.setListaAutores(null); //TODO falta hacer set de lista autores
				trabajo.setListaAutores(autorDao.obtenerListaAutoresPorIdTrabajo(trabajo.getId())); //TODO falta hacer set de lista autores
				 
			
				if (trabajo != null)
					list.add(trabajo);
			} while (c.moveToNext());
		}
		if (!c.isClosed()) {
			c.close();
		}
		return list;
	}
	
	public boolean updateFavourite(int id_trabajo, String valor)
    {
        
        ContentValues contentvalue=new ContentValues();
        contentvalue.put("favourite", valor);
        return db.update(TrabajoTabla.TABLE_NAME, contentvalue, "id_trabajo='"+id_trabajo+"'", null) > 0;
    }

}
