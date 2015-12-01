package com.jorgeldra.seio.data;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;


public class AutorTabla {

public static final String TABLE_NAME = "autor";
	
	public static class AutorColumnas implements BaseColumns {
		
		public static final String ID_AUTOR = "id_autor";
		public static final String NORMALIZED_NAME = "normalized_name";
		public static final String NAME = "name";
		public static final String LAST_NAME = "last_name";
		public static final String SUBMITTER = "submitter";
	}
	
	public static void onCreate(SQLiteDatabase db){
		
		Log.i("entra","entra en la creacion de tabla autor");
	
		db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",  
		AutorTabla.TABLE_NAME, AutorColumnas.ID_AUTOR, AutorColumnas.NORMALIZED_NAME,AutorColumnas.NAME,AutorColumnas.LAST_NAME,AutorColumnas.SUBMITTER));  
	
	
	}


	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " +AutorTabla.TABLE_NAME);
		onCreate(db);
	}
	

}
