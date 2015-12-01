package com.jorgeldra.seio.data;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.jorgeldra.seio.data.FavoritoTabla.FavoritoColumnas;

public class FavoritoDao   {
	
	private static final String INSERT = "insert into "
			+ FavoritoTabla.TABLE_NAME + "(" + FavoritoColumnas.ID_TRABAJO + ")" + "values (?)";
	
	// database fields
	private SQLiteDatabase db;
	private SQLiteStatement insertStatement;

	public FavoritoDao(SQLiteDatabase db) {
		// TODO Auto-generated constructor stub
		this.db = db;
	    insertStatement = db.compileStatement(FavoritoDao.INSERT);
	}

	
	public long save(int trabajo_id) {
		insertStatement.clearBindings();
		insertStatement.bindString(1, String.valueOf(trabajo_id));
		return insertStatement.executeInsert();
	}

	
	public void update(Object type) {
		// TODO Auto-generated method stub
		
	}

	
	public void delete(int trabajo_id) {
		if (trabajo_id > 0) {
			db.delete(FavoritoTabla.TABLE_NAME, FavoritoColumnas.ID_TRABAJO + " = " + trabajo_id, null);
		}
		
	}

	
	public Object get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public ArrayList getAllById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
