package com.jorgeldra.seio.data;

import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

import com.jorgeldra.seio.data.CustomSectionTabla.CustomSectionColumnas;
import com.jorgeldra.seio.entidad.CustomSection;




public class CustomSectionDao implements Dao<CustomSection> {

	private static final String INSERT = "insert into "
			+ CustomSectionTabla.TABLE_NAME + "("
			+ CustomSectionColumnas.TITLE + ", "
			+ CustomSectionColumnas.CONTENT + ")"
			+ " values (?, ?)";

	private SQLiteDatabase db;
	private SQLiteStatement insertStatement;
	
	public CustomSectionDao(SQLiteDatabase db) {
	      this.db = db;
	      insertStatement = db.compileStatement(CustomSectionDao.INSERT);
	   }


	@Override
	public long save(CustomSection customSection) {
		insertStatement.clearBindings();
		insertStatement.bindString(1, String.valueOf(customSection.getTitle()));
		insertStatement.bindString(2, String.valueOf(customSection.getContent()));
		return insertStatement.executeInsert();
	}

	@Override
	public void update(CustomSection type) {
		// TODO Auto-generated method stub

	}


	@Override
	public ArrayList<CustomSection> getAllById(long id) {
		ArrayList<CustomSection> list = new ArrayList<CustomSection>();
		Cursor c = db.query(CustomSectionTabla.TABLE_NAME, new String[] {
				BaseColumns._ID, CustomSectionColumnas.TITLE,
				CustomSectionColumnas.CONTENT}, CustomSectionColumnas._ID + " = ?",
				new String[] { String.valueOf(id) }, null, null, null, "1");

		if (c.moveToFirst()) {
			do {
				CustomSection customSection = this.pasarDeCursorACustom(c);
				if (customSection != null)
					list.add(customSection);
			} while (c.moveToNext());
		}
		if (!c.isClosed()) {
			c.close();
		}
		return list;
	}
	
	private CustomSection pasarDeCursorACustom(Cursor c) {
		CustomSection custom = null;
		if (c != null) {
			custom = new CustomSection();
			custom.setTitle(c.getString(1));
			custom.setContent(c.getString(2));
		}
		return custom;
	}

	@Override
	public ArrayList<CustomSection> getAll() {
		// TODO Auto-generated method stub
		ArrayList<CustomSection> list = new ArrayList<CustomSection>();
		Cursor c = db.query(CustomSectionTabla.TABLE_NAME, new String[] {
				BaseColumns._ID, CustomSectionColumnas.TITLE,
				CustomSectionColumnas.CONTENT}, null,null, null, null, null, null);

		if (c.moveToFirst()) {
			do {
				CustomSection customSection = this.pasarDeCursorACustom(c);
				if (customSection != null)
					list.add(customSection);
			} while (c.moveToNext());
		}
		if (!c.isClosed()) {
			c.close();
		}
		return list;
	}

	@Override
	public CustomSection get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void delete(CustomSection type) {
		// TODO Auto-generated method stub
		
	}
	

}
