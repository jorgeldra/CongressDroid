package com.jorgeldra.seio.data;

import com.jorgeldra.seio.data.SeComponeTrabajoAutorTabla.SeComponeTrabajoAutorColumnas;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;


public class SeComponeTrabajoAutorDao {

	private static final String INSERT = "insert into "
			+ SeComponeTrabajoAutorTabla.TABLE_NAME + "(" + SeComponeTrabajoAutorColumnas.ID_TRABAJO + ", " +SeComponeTrabajoAutorColumnas.ID_AUTOR+ ")" + "values (?,?)";

	// database fields
	private SQLiteDatabase db;
	private SQLiteStatement insertStatement;
	
	public SeComponeTrabajoAutorDao(SQLiteDatabase db) {
		// TODO Auto-generated constructor stub
		  this.db = db;
	      insertStatement = db.compileStatement(SeComponeTrabajoAutorDao.INSERT);
	}

	
	public long save(int id_trabajo, int id_autor) {
		// TODO Auto-generated method stub
		insertStatement.clearBindings();
		insertStatement.bindString(1, String.valueOf(id_trabajo));
		insertStatement.bindString(2, String.valueOf(id_autor));
		return insertStatement.executeInsert();
	}

}
