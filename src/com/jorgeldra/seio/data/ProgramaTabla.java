package com.jorgeldra.seio.data;


import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

public class ProgramaTabla {

	public static final String TABLE_NAME = "programa";
	
	public static class ProgramaColumnas implements BaseColumns {
		
		public static final String FECHA = "fecha";
		public static final String ID_CONFERENCIA = "id_conferencia";
	}
	
	public static void onCreate(SQLiteDatabase db){
		
		Log.i("entra","entra en la creacion de tabla programa");
	
		db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s INTEGER NOT NULL, FOREIGN KEY ("+ProgramaColumnas.ID_CONFERENCIA+") " +
				"REFERENCES "+ ConferenciaTabla.TABLE_NAME +"("+BaseColumns._ID+") )",  
				ProgramaTabla.TABLE_NAME, BaseColumns._ID, ProgramaColumnas.FECHA, ProgramaColumnas.ID_CONFERENCIA));  
		
		
		db.execSQL("CREATE TRIGGER fk_programa_id " +
				" BEFORE INSERT "+" ON "+ProgramaTabla.TABLE_NAME+
				" FOR EACH ROW BEGIN"+ " SELECT CASE WHEN ((SELECT "+BaseColumns._ID+" FROM "+ConferenciaTabla.TABLE_NAME+" " +
				"WHERE "+BaseColumns._ID+"=new."+ProgramaColumnas.ID_CONFERENCIA+" ) IS NULL)"+
				" THEN RAISE (ABORT,'Foreign Key Violation') END;"+ " END;");
	
	}


	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + ProgramaTabla.TABLE_NAME);
		onCreate(db);
	}


}
