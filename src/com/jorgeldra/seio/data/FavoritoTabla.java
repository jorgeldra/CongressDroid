package com.jorgeldra.seio.data;

import com.jorgeldra.seio.data.TrabajoTabla.TrabajoColumnas;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

public class FavoritoTabla {

public static final String TABLE_NAME = "favoritos";
	
	public static class FavoritoColumnas implements BaseColumns {
		
		public static final String ID_FAVORITO = "id_favorito";
		public static final String ID_TRABAJO = "id_trabajo";
	}
	
	public static void onCreate(SQLiteDatabase db){
		
		Log.i("entra","entra en la creacion de tabla favoritos");
	
		db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY,  %s INTEGER NOT NULL, FOREIGN KEY ("+FavoritoColumnas.ID_TRABAJO+") " +
				"REFERENCES "+ TrabajoTabla.TABLE_NAME +"("+TrabajoColumnas.ID_TRABAJO+") )",  
				FavoritoTabla.TABLE_NAME, BaseColumns._ID, FavoritoColumnas.ID_TRABAJO));  
		
		
		db.execSQL("CREATE TRIGGER IF NOT EXISTS fk_favorito_id " +
				" BEFORE INSERT "+" ON "+FavoritoTabla.TABLE_NAME+
				" FOR EACH ROW BEGIN"+ " SELECT CASE WHEN ((SELECT "+TrabajoColumnas.ID_TRABAJO+" FROM "+TrabajoTabla.TABLE_NAME+" " +
				"WHERE "+TrabajoColumnas.ID_TRABAJO+"=new."+FavoritoColumnas.ID_TRABAJO+" ) IS NULL)"+
				" THEN RAISE (ABORT,'Foreign Key Violation') END;"+ " END;");
	
	}


	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + FavoritoTabla.TABLE_NAME);
		onCreate(db);
	}
	

}
