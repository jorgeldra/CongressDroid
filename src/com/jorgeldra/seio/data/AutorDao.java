package com.jorgeldra.seio.data;

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

import com.jorgeldra.seio.data.AutorTabla.AutorColumnas;
import com.jorgeldra.seio.data.CustomSectionTabla.CustomSectionColumnas;
import com.jorgeldra.seio.data.SeComponeTrabajoAutorTabla.SeComponeTrabajoAutorColumnas;
import com.jorgeldra.seio.entidad.Autor;
import com.jorgeldra.seio.entidad.CustomSection;

public class AutorDao implements Dao<Autor>  {

	private static final String INSERT = "insert into "
			+ AutorTabla.TABLE_NAME + "(" + AutorColumnas.ID_AUTOR + ", " + AutorColumnas.NORMALIZED_NAME + ", "
			+ AutorColumnas.NAME + ", "
			+ AutorColumnas.LAST_NAME + ","+ AutorColumnas.SUBMITTER +")" + "values (?,?,?,?,?)";

	// database fields
	private SQLiteDatabase db;
	private SQLiteStatement insertStatement;
	
	public AutorDao(SQLiteDatabase db) {
		// TODO Auto-generated constructor stub
		  this.db = db;
	      insertStatement = db.compileStatement(AutorDao.INSERT);
	}
	
	public long save(Autor autor, int id_trabajo) {
		// TODO Auto-generated method stub
		
		SeComponeTrabajoAutorDao secompone = new SeComponeTrabajoAutorDao(db);
		//si el id de autor es nulo, buscamos el autor en la base de datos para recuperar el id, si no se encuentra, insertamos con el ultimo id mas alto utilizado concatenando con un 0 detras
		
		/*Log.i("id autor", ""+ autor.getId());
		if (autor.getId() != 0){
			
			//int buscarAutor = obtenerAutorPorNormalizedName(autor.getNormalized_name());
			int existeId = (int) existeId (autor.getId());
			if (existeId == 0){
				insertStatement.clearBindings();
				insertStatement.bindString(1, String.valueOf(autor.getId()));
				insertStatement.bindString(2, String.valueOf(autor.getNormalized_name()));
				insertStatement.bindString(3, String.valueOf(autor.getName()));
				insertStatement.bindString(4, String.valueOf(autor.getLastname()));
				insertStatement.bindString(5, String.valueOf(autor.getSubmitter()));
				Log.i("primera rama", "entra en uno no  encontrado");
				secompone.save(id_trabajo, autor.getId());
				
				return insertStatement.executeInsert();	
				
			}else{
				Log.i("primera rama", "entra en uno encontrado");
				secompone.save(id_trabajo, autor.getId());
			}
			
			
			
			
		}else{
			int buscarAutor = obtenerAutorPorNormalizedName(autor.getNormalized_name());
			if (buscarAutor == 0){
				//recuperamos el ultimo id y concatenamos con 00
				//insertStatement.bindString(1, String.valueOf());
				long maximoId = getMaxId();
				Log.i("segunda rama", "entra en autor no encontrado");
				//Log.i("maximo id", ""+maximoId);
				maximoId = maximoId +1000;
				insertStatement.clearBindings();
				insertStatement.bindString(1, String.valueOf(maximoId));
				insertStatement.bindString(2, String.valueOf(autor.getNormalized_name()));
				insertStatement.bindString(3, String.valueOf(autor.getName()));
				insertStatement.bindString(4, String.valueOf(autor.getLastname()));
				insertStatement.bindString(5, String.valueOf(autor.getSubmitter()));
				
				secompone.save(id_trabajo, (int) maximoId);
				
				return insertStatement.executeInsert();	
			}else{
				//el autor ya se encuentra en la tabla de autores
				Log.i("tercera rama", "entra en autor  encontrado");
			}
		}
		
		
		return 0;*/
		
		insertStatement.bindString(2, String.valueOf(autor.getNormalized_name()));
		insertStatement.bindString(3, String.valueOf(autor.getName()));
		insertStatement.bindString(4, String.valueOf(autor.getLastname()));
		insertStatement.bindString(5, String.valueOf(autor.getSubmitter()));
		long insertado = insertStatement.executeInsert();
		secompone.save(id_trabajo, (int) insertado);
		return insertado;
	}

	@Override
	public void update(Autor type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Autor type) {
		// TODO Auto-generated method stub
		
	}
	
	private Autor pasarDeCursorAAutor(Cursor c) {
		Autor autor = null;
		if (c != null) {
			autor = new Autor();
			autor.setId(c.getInt(0));
			autor.setNormalized_name(c.getString(1));
			autor.setName(c.getString(2));
			autor.setLastname(c.getString(3));
			autor.setSubmitter(c.getInt(4));
		}
		return autor;
	}

	@Override
	public Autor get(long id) {
		Autor autor = null;
		Cursor c = db.query(AutorTabla.TABLE_NAME, new String[] {
				AutorColumnas.ID_AUTOR, AutorColumnas.NORMALIZED_NAME,
				AutorColumnas.NAME, AutorColumnas.LAST_NAME, AutorColumnas.SUBMITTER }, AutorColumnas.ID_AUTOR + " = ?",
				new String[] { String.valueOf(id) }, null, null, null,"1");

		if (c.moveToFirst()) {
			autor = this.pasarDeCursorAAutor(c);
		}
		if (!c.isClosed()) {
			c.close();
		}
		return autor;
	}

	@Override
	public ArrayList<Autor> getAll() {
		// TODO Auto-generated method stub
		ArrayList<Autor> list = new ArrayList<Autor>();
		/*Cursor c = db.query(AutorTabla.TABLE_NAME, new String[] {
				AutorColumnas.ID_AUTOR, AutorColumnas.NORMALIZED_NAME,
				AutorColumnas.NAME, AutorColumnas.LAST_NAME, AutorColumnas.SUBMITTER },null, null, null, null,AutorColumnas.LAST_NAME+" ASC");*/
		
		Cursor c = db.query(true,AutorTabla.TABLE_NAME, new String[] {
				AutorColumnas.ID_AUTOR, AutorColumnas.NORMALIZED_NAME,
				AutorColumnas.NAME, AutorColumnas.LAST_NAME, AutorColumnas.SUBMITTER }, null, null, AutorColumnas.LAST_NAME, null, null,null);


		if (c.moveToFirst()) {
			do {
				Autor autor = this.pasarDeCursorAAutor(c);
				if (autor != null)
					list.add(autor);
			} while (c.moveToNext());
		}
		if (!c.isClosed()) {
			c.close();
		}
		return list;
	}

	@Override
	public ArrayList<Autor> getAllById(long id) {
		/*ArrayList<Autor> list = new ArrayList<Autor>();

		
		Cursor c = db.query(AutorTabla.TABLE_NAME, new String[] {
				AutorColumnas.ID_AUTOR, AutorColumnas.NORMALIZED_NAME,AutorColumnas.NAME, AutorColumnas.LAST_NAME,
				AutorColumnas.SUBMITTER}, AutorColumnas.ID_AUTOR + " = ?",
				new String[] { String.valueOf(id) },  null,null,null); //ordenar por dos campos

		if (c.moveToFirst()) {
			do {
				//Autor autor = this.pasarDeCursorAAutor(c);
				
				
				if (autor != null)
					list.add(autor);
			} while (c.moveToNext());
		}
		if (!c.isClosed()) {
			c.close();
		}
		return list;*/
		return null;
	}
	
	public ArrayList<Autor> obtenerListaAutoresPorIdTrabajo(long id) {
		int id_autor = 0;
		ArrayList<Autor> list = new ArrayList<Autor>();
		
		Cursor c = db.query(SeComponeTrabajoAutorTabla.TABLE_NAME, new String[] {
				SeComponeTrabajoAutorColumnas.ID_TRABAJO, SeComponeTrabajoAutorColumnas.ID_AUTOR }, SeComponeTrabajoAutorColumnas.ID_TRABAJO + " = ?",
				new String[] { String.valueOf(id) }, null, null, null,null);

		if (c.moveToFirst()) {
			do {
				id_autor = c.getInt(1);
				//Log.i("encuentra autor", ""+ id_autor);
				
				list.add(get(id_autor));
			}while(c.moveToNext());
			
		}
		if (!c.isClosed()) {
			c.close();
		}
		return list;
	}
	
	public int obtenerAutorPorNormalizedName(String normalized_name) {
		int id_autor_buscado = 0;
		Cursor c = db.query(AutorTabla.TABLE_NAME, new String[] {
				AutorColumnas.ID_AUTOR }, AutorColumnas.NORMALIZED_NAME + " = ?",
				new String[] { String.valueOf(normalized_name) }, null, null, null,"1");

		if (c.moveToFirst()) {
			id_autor_buscado = c.getInt(0);
		}
		if (!c.isClosed()) {
			c.close();
		}
		return id_autor_buscado;
	}
	
	private long getMaxId()
	{
	    String query = "SELECT MAX(id_autor) AS max_id FROM autor";
	    //Cursor cursor = db.rawQuery(query, new String[] {"max_id"});
	    Cursor cursor = db.rawQuery(query, null);
	    int id = 0;     
	    if (cursor.moveToFirst())
	    {
	        do
	        {           
	            id = cursor.getInt(0);                  
	        } while(cursor.moveToNext());           
	    }
	    return id;
	}
	
	private long existeId(int id)
	{
	    String query = "SELECT id_autor FROM autor WHERE id_autor LIKE " + id ;
	    //Cursor cursor = db.rawQuery(query, new String[] {"max_id"});
	    Cursor cursor = db.rawQuery(query, null);
	    int buscado = 0;     
	    if (cursor.moveToFirst())
	    {
	        do
	        {           
	            buscado = cursor.getInt(0);                  
	        } while(cursor.moveToNext());           
	    }
	    return buscado;
	}

	@Override
	public long save(Autor type) {
		// TODO Auto-generated method stub
		return 0;
	}

}
