package com.jorgeldra.seio.data;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

public class SeComponeTrabajoAutorTabla {

public static final String TABLE_NAME = "se_compone_trabajoAutor";
	
	public static class SeComponeTrabajoAutorColumnas implements BaseColumns {
		
		public static final String ID_TRABAJO = "id_trabajo";
		public static final String ID_AUTOR = "id_autor";
	}
	
	public static void onCreate(SQLiteDatabase db){
		
		Log.i("entra","entra en la creacion de tabla se compone trabajo autor");
	
		String crearTabla ="CREATE TABLE se_compone_trabajoAutor (id_trabajo  INTEGER,id_autor INTEGER,FOREIGN KEY (id_trabajo) REFERENCES trabajo(id_trabajo),FOREIGN KEY (id_autor) REFERENCES autor(id_autor))";
		 
		 db.execSQL(String.format(crearTabla));
		
	
	}


	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + SeComponeTrabajoAutorTabla.TABLE_NAME);
		onCreate(db);
	}


}
