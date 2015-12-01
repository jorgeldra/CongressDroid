package com.jorgeldra.seio.data;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

public class SesionTabla {

	public static final String TABLE_NAME = "sesion";
	
	public static class SesionColumnas implements BaseColumns {
		
		public static final String ID_SESION = "id_sesion";
		public static final String IDENTIFIER = "identifier";
		public static final String NAME = "name";
		public static final String DESCRIPTION = "description";
		public static final String COMMENTS = "comments";
		public static final String START = "start";
		public static final String END = "end";
		public static final String ID_LOCATION = "id_location";
		public static final String NAME_LOCATION = "name_location";
		public static final String GPS_COORDS = "gps_coords";
		public static final String VENUE = "venue";
		public static final String ID_PROGRAMA = "id_programa";
	}
	
	public static void onCreate(SQLiteDatabase db){
		
		Log.i("entra","entra en la creacion de tabla sesion");
	
		db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER, %s TEXT, %s TEXT, %s TEXT, %s INTEGER NOT NULL, FOREIGN KEY ("+SesionColumnas.ID_PROGRAMA+") " +
				"REFERENCES "+ ProgramaTabla.TABLE_NAME +"("+BaseColumns._ID+") )",  
				SesionTabla.TABLE_NAME, SesionColumnas.ID_SESION, SesionColumnas.IDENTIFIER,SesionColumnas.NAME,SesionColumnas.DESCRIPTION,SesionColumnas.COMMENTS,SesionColumnas.START,SesionColumnas.END,SesionColumnas.ID_LOCATION,SesionColumnas.NAME_LOCATION,SesionColumnas.GPS_COORDS,SesionColumnas.VENUE,SesionColumnas.ID_PROGRAMA));  
		
		
		db.execSQL("CREATE TRIGGER fk_sesion_id " +
				" BEFORE INSERT "+" ON "+SesionTabla.TABLE_NAME+
				" FOR EACH ROW BEGIN"+ " SELECT CASE WHEN ((SELECT "+BaseColumns._ID+" FROM "+ProgramaTabla.TABLE_NAME+" " +
				"WHERE "+BaseColumns._ID+"=new."+SesionColumnas.ID_PROGRAMA+" ) IS NULL)"+
				" THEN RAISE (ABORT,'Foreign Key Violation') END;"+ " END;");
	
	}


	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + SesionTabla.TABLE_NAME);
		onCreate(db);
	}

}
