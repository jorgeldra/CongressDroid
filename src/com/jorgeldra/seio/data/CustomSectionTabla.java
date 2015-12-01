package com.jorgeldra.seio.data;


import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

public class CustomSectionTabla {
	public static final String TABLE_NAME = "custom_section";
	
	public static class CustomSectionColumnas implements BaseColumns {
		
		public static final String TITLE = "title";
		public static final String CONTENT = "content";
	}
	
	public static void onCreate(SQLiteDatabase db){
		
		Log.i("entra","entra en la creacion de tabla custom section");
	
		db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT,  %s TEXT)",  
				CustomSectionTabla.TABLE_NAME, BaseColumns._ID, CustomSectionColumnas.TITLE, CustomSectionColumnas.CONTENT));  
		
	
	}


	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + CustomSectionTabla.TABLE_NAME);
		onCreate(db);
	}

}
