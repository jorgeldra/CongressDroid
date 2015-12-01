package com.jorgeldra.seio.data;

import com.jorgeldra.seio.data.SesionTabla.SesionColumnas;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;



public class TrabajoTabla {

	public static final String TABLE_NAME = "trabajo";
	
	public static class TrabajoColumnas implements BaseColumns {
		
		public static final String ID_TRABAJO = "id_trabajo";
		public static final String TITLE = "title";
		public static final String TEXT = "text";
		public static final String KEYWORDS = "keywords";
		public static final String FAVOURITE = "favourite";
		public static final String ID_SESION = "id_sesion";
	}
	
	public static void onCreate(SQLiteDatabase db){
		
		Log.i("entra","entra en la creacion de tabla trabajo");
	
		db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER NOT NULL, FOREIGN KEY ("+TrabajoColumnas.ID_SESION+") " +
				"REFERENCES "+ SesionTabla.TABLE_NAME +"("+SesionColumnas.ID_SESION+") )",  
				TrabajoTabla.TABLE_NAME, TrabajoColumnas.ID_TRABAJO, TrabajoColumnas.TITLE,TrabajoColumnas.TEXT,TrabajoColumnas.KEYWORDS,TrabajoColumnas.FAVOURITE,TrabajoColumnas.ID_SESION));  
		
		
		db.execSQL("CREATE TRIGGER fk_trabajo_id " +
				" BEFORE INSERT "+" ON "+TrabajoTabla.TABLE_NAME+
				" FOR EACH ROW BEGIN"+ " SELECT CASE WHEN ((SELECT "+SesionColumnas.ID_SESION+" FROM "+SesionTabla.TABLE_NAME+" " +
				"WHERE "+SesionColumnas.ID_SESION+"=new."+TrabajoColumnas.ID_SESION+" ) IS NULL)"+
				" THEN RAISE (ABORT,'Foreign Key Violation') END;"+ " END;");
		
		db.execSQL("CREATE TRIGGER trabajo_actualizar_favorito "+ 
					"AFTER INSERT ON trabajo FOR EACH ROW "+
					"BEGIN "+
					"UPDATE trabajo SET favourite = 'true' WHERE id_trabajo IN (SELECT id_Trabajo FROM favoritos); "+
					"END;");
		
		
		
	
	}


	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + TrabajoTabla.TABLE_NAME);
		onCreate(db);
	}

}
