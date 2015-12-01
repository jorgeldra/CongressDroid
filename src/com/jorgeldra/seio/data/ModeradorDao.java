package com.jorgeldra.seio.data;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.jorgeldra.seio.data.EsPresentadoTabla.EsPresentadoColumnas;
import com.jorgeldra.seio.data.ModeradorTabla.ModeradorColumnas;
import com.jorgeldra.seio.entidad.Moderador;

public class ModeradorDao implements Dao<Moderador>  {

	private static final String INSERT = "insert into "
			+ ModeradorTabla.TABLE_NAME + "(" + ModeradorColumnas.ID_MODERADOR + ", " + ModeradorColumnas.NORMALIZED_NAME + ", "
			+ ModeradorColumnas.NAME + ", "
			+ ModeradorColumnas.LAST_NAME + ")" + "values (?,?,?,?)";

	// database fields
	private SQLiteDatabase db;
	private SQLiteStatement insertStatement;
	
	public ModeradorDao(SQLiteDatabase db) {
		// TODO Auto-generated constructor stub
		  this.db = db;
	      insertStatement = db.compileStatement(ModeradorDao.INSERT);
	}

	@Override
	public long save(Moderador moderador) {
		// TODO Auto-generated method stub
		insertStatement.clearBindings();
		insertStatement.bindString(1, String.valueOf(moderador.getId()));
		insertStatement.bindString(2, String.valueOf(moderador.getNormalized_name()));
		insertStatement.bindString(3, String.valueOf(moderador.getName()));
		insertStatement.bindString(4, String.valueOf(moderador.getLastname()));
		return insertStatement.executeInsert();
	}

	@Override
	public void update(Moderador type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Moderador type) {
		// TODO Auto-generated method stub
		
	}
	
	private Moderador pasarDeCursorAModerador(Cursor c) {
		Moderador moderador = null;
		if (c != null) {
			moderador = new Moderador();
			moderador.setId(c.getInt(0));
			moderador.setNormalized_name(c.getString(1));
			moderador.setName(c.getString(2));
			moderador.setLastname(c.getString(3));
		}
		return moderador;
	}

	@Override
	public Moderador get(long id) {
		Moderador moderador = null;
		Cursor c = db.query(ModeradorTabla.TABLE_NAME, new String[] {
				ModeradorColumnas.ID_MODERADOR, ModeradorColumnas.NORMALIZED_NAME,
				ModeradorColumnas.NAME, ModeradorColumnas.LAST_NAME }, ModeradorColumnas.ID_MODERADOR + " = ?",
				new String[] { String.valueOf(id) }, null, null, null,"1");

		if (c.moveToFirst()) {
			moderador = this.pasarDeCursorAModerador(c);
		}
		if (!c.isClosed()) {
			c.close();
		}
		return moderador;
	}
	
	public long obtenerIdModeradorPorIdSesion(long id) {
		int id_moderador = 0;
		Cursor c = db.query(EsPresentadoTabla.TABLE_NAME, new String[] {
				EsPresentadoColumnas.ID_SESION, EsPresentadoColumnas.ID_MODERADOR }, EsPresentadoColumnas.ID_SESION + " = ?",
				new String[] { String.valueOf(id) }, null, null, null,"1");

		if (c.moveToFirst()) {
			id_moderador = c.getInt(1);
		}
		if (!c.isClosed()) {
			c.close();
		}
		return id_moderador;
	}

	@Override
	public List<Moderador> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Moderador> getAllById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
