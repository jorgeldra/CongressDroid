package com.jorgeldra.seio.data;



import android.database.sqlite.SQLiteDatabase;

import android.provider.BaseColumns;


public class ConferenciaTabla{

	public static final String TABLE_NAME = "conferencia";

	public static class ConferenciaColumnas implements BaseColumns {
		
		public static final String TITLE = "title";
		public static final String TITLEDOS = "titleDos";
		public static final String PRESENTATION = "presentation";
		public static final String HOME_TEXT = "home_text";
	}


	public static void onCreate(SQLiteDatabase db){
		
		/*StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + ConferenciaTabla.TABLE_NAME  + " ("); 
		sb.append(BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
		sb.append(ConferenciaColumnas.TITLE + " TEXT, ");
		sb.append(ConferenciaColumnas.TITLEDOS + "TEXT, ");
		sb.append(ConferenciaColumnas.PRESENTATION + "TEXT, ");
		sb.append(ConferenciaColumnas.HOME_TEXT + "TEXT ");
		sb.append(");");
		db.execSQL(sb.toString());
		*/
		/*db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT,%s TEXT,%s TEXT)",  
				ConferenciaTabla.TABLE_NAME, BaseColumns._ID,  
				ConferenciaColumnas.TITLE, ConferenciaColumnas.TITLEDOS,ConferenciaColumnas.PRESENTATION,ConferenciaColumnas.HOME_TEXT));  
		db.endTransaction();*/
		
		//LOS ERORES QUE ME APARECEN ERA POR USAR LA TRANSACCION OJOOOOOOO
		
		db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT,%s TEXT,%s TEXT)",  
				ConferenciaTabla.TABLE_NAME, BaseColumns._ID,  
				ConferenciaColumnas.TITLE, ConferenciaColumnas.TITLEDOS,ConferenciaColumnas.PRESENTATION,ConferenciaColumnas.HOME_TEXT));
		
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + ConferenciaTabla.TABLE_NAME);
		onCreate(db);
	}
		
		

}
