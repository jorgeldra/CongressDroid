package com.jorgeldra.seio.data;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;


public class ImagenesConfTabla {
	public static final String TABLE_NAME = "imagenesConferencia";

	public static class ImagenesConferenciaColumnas implements BaseColumns {
		
		public static final String URL = "url";
		public static final String ID_CONFERENCIA = "id_conferencia";
	}

	public static void onCreate(SQLiteDatabase db){
		
		Log.i("entra","entra en la creacion de tabla imagenes");
	
		db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s INTEGER NOT NULL, FOREIGN KEY ("+ImagenesConferenciaColumnas.ID_CONFERENCIA+") " +
				"REFERENCES "+ ConferenciaTabla.TABLE_NAME +"("+BaseColumns._ID+") )",  
				ImagenesConfTabla.TABLE_NAME, BaseColumns._ID, ImagenesConferenciaColumnas.URL, ImagenesConferenciaColumnas.ID_CONFERENCIA));  
		
		
		db.execSQL("CREATE TRIGGER fk_confima_imaId " +
				" BEFORE INSERT "+" ON "+ImagenesConfTabla.TABLE_NAME+
				" FOR EACH ROW BEGIN"+ " SELECT CASE WHEN ((SELECT "+BaseColumns._ID+" FROM "+ConferenciaTabla.TABLE_NAME+" " +
				"WHERE "+BaseColumns._ID+"=new."+ImagenesConferenciaColumnas.ID_CONFERENCIA+" ) IS NULL)"+
				" THEN RAISE (ABORT,'Foreign Key Violation') END;"+ " END;");
	
	}


	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + ImagenesConfTabla.TABLE_NAME);
		onCreate(db);
	}

}
