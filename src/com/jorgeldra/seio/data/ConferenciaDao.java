package com.jorgeldra.seio.data;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

import com.jorgeldra.seio.data.ConferenciaTabla.ConferenciaColumnas;
import com.jorgeldra.seio.entidad.Conferencia;

// ejemplo sacado de http://www.javahispano.org/android/2011/12/27/manejo-de-datos-en-android-sqlite.html

//¿¿¿¿¿ VERIFICAR QUE CREANDO UN NUEVO XML, SE INSERTAN NUEVOS REGISTROS
//EN LA BBDD 
public class ConferenciaDao implements Dao<Conferencia> {

	private static final String INSERT = "insert into "
			+ ConferenciaTabla.TABLE_NAME + "(" + ConferenciaColumnas.TITLE
			+ ", " + ConferenciaColumnas.TITLEDOS + ", "
			+ ConferenciaColumnas.PRESENTATION + ", "
			+ ConferenciaColumnas.HOME_TEXT + ")" + "values (?,?,?,?)";

	// database fields
	private SQLiteDatabase db;
	private SQLiteStatement insertStatement;

	public ConferenciaDao(SQLiteDatabase db) {
		this.db = db;
		insertStatement = db.compileStatement(ConferenciaDao.INSERT);
	}

	@Override
	public long save(Conferencia conferencia) {
		insertStatement.clearBindings();
		insertStatement.bindString(1, String.valueOf(conferencia.getTitle()));
		insertStatement.bindString(2, String.valueOf(conferencia.getTitle2()));
		insertStatement.bindString(3,
				String.valueOf(conferencia.getPresentation()));
		insertStatement.bindString(4,
				String.valueOf(conferencia.getHome_text()));

		return insertStatement.executeInsert();
	}

	@Override
	public void update(Conferencia conferencia) {
		final ContentValues values = new ContentValues();
		values.put(ConferenciaColumnas.TITLE, conferencia.getTitle());
		values.put(ConferenciaColumnas.TITLEDOS, conferencia.getTitle2());
		values.put(ConferenciaColumnas.PRESENTATION,
				conferencia.getPresentation());
		values.put(ConferenciaColumnas.HOME_TEXT, conferencia.getHome_text());
		db.update(ConferenciaTabla.TABLE_NAME, values, BaseColumns._ID
				+ " = ? ", new String[] { String.valueOf(conferencia.getId()) });
	}

	@Override
	public void delete(Conferencia conferencia) {
		if (conferencia.getId() > 0) {
			db.delete(ConferenciaTabla.TABLE_NAME, BaseColumns._ID + " = ?",
					new String[] { String.valueOf(conferencia.getId()) });
		}
	}

	@Override
	public Conferencia get(long id) {
		Conferencia conferencia = null;
		Cursor c = db.query(ConferenciaTabla.TABLE_NAME, new String[] {
				BaseColumns._ID, ConferenciaColumnas.TITLE,
				ConferenciaColumnas.TITLEDOS, ConferenciaColumnas.PRESENTATION,
				ConferenciaColumnas.HOME_TEXT }, BaseColumns._ID + " = ?",
				new String[] { String.valueOf(id) }, null, null, null, "1");

		if (c.moveToFirst()) {
			conferencia = this.pasarDeCursorAConferencia(c);
		}
		if (!c.isClosed()) {
			c.close();
		}
		return conferencia;
	}

	private Conferencia pasarDeCursorAConferencia(Cursor c) {
		Conferencia conferencia = null;
		if (c != null) {
			conferencia = new Conferencia();
			conferencia.setId(c.getInt(0));
			conferencia.setTitle(c.getString(1));
			conferencia.setTitle2(c.getString(2));
			conferencia.setPresentation(c.getString(3));
			conferencia.setHome_text(c.getString(4));
		}
		return conferencia;
	}

	@Override
	public ArrayList<Conferencia> getAll() {
		ArrayList<Conferencia> list = new ArrayList<Conferencia>();
		ImagenesConfDao imConfDao = new ImagenesConfDao(db);
		
		
		Cursor c = db.query(ConferenciaTabla.TABLE_NAME, new String[] {
				BaseColumns._ID, ConferenciaColumnas.TITLE,
				ConferenciaColumnas.TITLEDOS, ConferenciaColumnas.PRESENTATION,
				ConferenciaColumnas.HOME_TEXT }, null, null,
				ConferenciaColumnas.TITLE, null, null, null);
		
		if (c.moveToFirst()) {
			do {
				Conferencia conferencia = this.pasarDeCursorAConferencia(c);
				//recuperamos el array de imagenes y lo guardamos en el array de imagenes de conferencias
				conferencia.setListaImagenesConf(imConfDao.getAllById(conferencia.getId()));
				
				if (conferencia != null)
				
					list.add(conferencia);
			} while (c.moveToNext());
		}
		if (!c.isClosed()) {
			c.close();
		}
		return list;
	}

	// ejemplo de como buscar en la base de datos
	public Conferencia find(String name) {
		Conferencia conferencia = null;
		String sql = "select _id, name from " + ConferenciaTabla.TABLE_NAME
				+ " where upper(" + ConferenciaColumnas.TITLE + ") = ? limit 1";
		Cursor c = db.rawQuery(sql, new String[] { name.toUpperCase() });
		if (c.moveToFirst()) {
			conferencia = this.pasarDeCursorAConferencia(c);
		}
		if (!c.isClosed()) {
			c.close();
		}
		return conferencia;
	}

	@Override
	public ArrayList<Conferencia> getAllById(long id) {
		// TODO Auto-generated method stub
		return null;
	}


}
