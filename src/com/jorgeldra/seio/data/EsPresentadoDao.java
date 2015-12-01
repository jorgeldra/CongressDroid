package com.jorgeldra.seio.data;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.jorgeldra.seio.data.EsPresentadoTabla.EsPresentadoColumnas;




public class EsPresentadoDao  {

	private static final String INSERT = "insert into "
			+ EsPresentadoTabla.TABLE_NAME + "(" + EsPresentadoColumnas.ID_SESION + ", " + EsPresentadoColumnas.ID_MODERADOR + ")" + "values (?,?)";

	// database fields
	private SQLiteDatabase db;
	private SQLiteStatement insertStatement;
	
	public EsPresentadoDao(SQLiteDatabase db) {
		// TODO Auto-generated constructor stub
		  this.db = db;
	      insertStatement = db.compileStatement(EsPresentadoDao.INSERT);
	}

	
	public long save(int id_sesion, int id_moderador) {
		// TODO Auto-generated method stub
		insertStatement.clearBindings();
		insertStatement.bindString(1, String.valueOf(id_sesion));
		insertStatement.bindString(2, String.valueOf(id_moderador));
		return insertStatement.executeInsert();
	}

}
