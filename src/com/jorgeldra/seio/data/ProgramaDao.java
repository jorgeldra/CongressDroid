package com.jorgeldra.seio.data;

import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.jorgeldra.seio.data.ProgramaTabla.ProgramaColumnas;
import com.jorgeldra.seio.entidad.Programa;

public class ProgramaDao   implements Dao<Programa>{
	
	
	private static final String INSERT = "insert into "
			+ ProgramaTabla.TABLE_NAME + "(" + ProgramaColumnas.FECHA + ", "
			+ ProgramaColumnas.ID_CONFERENCIA + ")" + "values (?,?)";

	// database fields
	private SQLiteDatabase db;
	private SQLiteStatement insertStatement;

	public ProgramaDao(SQLiteDatabase db) {
		// TODO Auto-generated constructor stub
		  this.db = db;
	      insertStatement = db.compileStatement(ProgramaDao.INSERT);
	}

	
	public long save(Programa programa, int id_conferencia) {
		insertStatement.clearBindings();
		insertStatement.bindString(1, String.valueOf(programa.getFecha()));
		insertStatement.bindString(2, String.valueOf(id_conferencia));
		return insertStatement.executeInsert();
	}

	@Override
	public void update(Programa type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Programa type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Programa get(long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Programa pasarDeCursorAPrograma(Cursor c) {
		Programa programa = null;
		if (c != null) {
			programa = new Programa();
			programa.setFecha(c.getString(1));
			
		
		}
		return programa;
	}

	@Override
	public ArrayList<Programa> getAll() {
		ArrayList<Programa> list = new ArrayList<Programa>();		
		SesionDao sesionDao = new SesionDao(db);
		
		String[] campos = new String[] {"_ID","FECHA","ID_CONFERENCIA"}; //acceder a una columna que no esta definida en ProgramaTabla con _ID
		
		Cursor c = db.query(ProgramaTabla.TABLE_NAME, campos, null, null, null, null, null);
		
		if (c.moveToFirst()) {
			do {
				Programa programa = this.pasarDeCursorAPrograma(c);
				//recuperamos el array de imagenes y lo guardamos en el array de imagenes de conferencias
				int id_programa = c.getInt(0);
				
				programa.setListaSesion(sesionDao.getAllById(id_programa));
				
				
				if (programa != null)
				
					list.add(programa);
			} while (c.moveToNext());
		}
		
		if (!c.isClosed()) {
			c.close();
		}
		return list;
		
		
	}
	


	@Override
	public ArrayList<Programa> getAllById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long save(Programa type) {
		// TODO Auto-generated method stub
		return 0;
	}

}
