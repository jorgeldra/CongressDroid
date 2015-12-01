package com.jorgeldra.seio.data;

import java.util.ArrayList;
import java.util.List;

import com.jorgeldra.seio.data.ImagenesConfTabla.ImagenesConferenciaColumnas;
import com.jorgeldra.seio.entidad.ImagenConf;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

public class ImagenesConfDao implements Dao<ImagenConf> {

	private static final String INSERT = "insert into "
			+ ImagenesConfTabla.TABLE_NAME + "("
			+ ImagenesConferenciaColumnas.URL + ", "
			+ ImagenesConferenciaColumnas.ID_CONFERENCIA + ")"
			+ " values (?, ?)";

	private SQLiteDatabase db;
	private SQLiteStatement insertStatement;

	public ImagenesConfDao(SQLiteDatabase db) {
	      this.db = db;
	      insertStatement = db.compileStatement(ImagenesConfDao.INSERT);
	   }

	@Override
	public long save(ImagenConf imagenConf) {
		insertStatement.clearBindings();
		insertStatement.bindString(1, String.valueOf(imagenConf.getUrl()));
		insertStatement.bindString(2, String.valueOf(imagenConf.getIdConf()));
		return insertStatement.executeInsert();
	}

	@Override
	public void update(ImagenConf type) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(ImagenConf type) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<ImagenConf> getAllById(long id) {
		ArrayList<ImagenConf> list = new ArrayList<ImagenConf>();
		Cursor c = db.query(ImagenesConfTabla.TABLE_NAME, new String[] {
				BaseColumns._ID, ImagenesConferenciaColumnas.URL,
				ImagenesConferenciaColumnas.ID_CONFERENCIA}, ImagenesConferenciaColumnas.ID_CONFERENCIA + " = ?",
				new String[] { String.valueOf(id) }, null, null, null, "1");

		if (c.moveToFirst()) {
			do {
				ImagenConf imConf = this.pasarDeCursorAImConferencia(c);
				if (imConf != null)
					list.add(imConf);
			} while (c.moveToNext());
		}
		if (!c.isClosed()) {
			c.close();
		}
		return list;
	}
	
	private ImagenConf pasarDeCursorAImConferencia(Cursor c) {
		ImagenConf imConf = null;
		if (c != null) {
			imConf = new ImagenConf();
			imConf.setId(c.getInt(0));
			imConf.setUrl(c.getString(1));
			imConf.setIdConf(c.getInt(2));
		}
		return imConf;
	}

	@Override
	public List<ImagenConf> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImagenConf get(long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*ejemplo util
	 * SQLiteDatabase db = table.getWritableDatabase();
    String count = "SELECT count(*) FROM table";
    Cursor mcursor = db.rawQuery(count, null);
    mcursor.moveToFirst();
    int icount = mcursor.getInt(0);
    System.out.println("NUMBER IN DB: " + icount);
	 */

}
