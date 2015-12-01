package com.jorgeldra.seio.data;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

public class ModeradorTabla {

	public static final String TABLE_NAME = "moderador";
	
	public static class ModeradorColumnas implements BaseColumns {
		
		public static final String ID_MODERADOR = "id_moderador";
		public static final String NORMALIZED_NAME = "normalized_name";
		public static final String NAME = "name";
		public static final String LAST_NAME = "last_name";
	}
	
	public static void onCreate(SQLiteDatabase db){
		
		Log.i("entra","entra en la creacion de tabla moderador");
	
		db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT)",  
		ModeradorTabla.TABLE_NAME, ModeradorColumnas.ID_MODERADOR, ModeradorColumnas.NORMALIZED_NAME,ModeradorColumnas.NAME,ModeradorColumnas.LAST_NAME));  
		
		
		/*db.execSQL("CREATE TRIGGER fk_sesion_id " +
				" BEFORE INSERT "+" ON "+SesionTabla.TABLE_NAME+
				" FOR EACH ROW BEGIN"+ " SELECT CASE WHEN ((SELECT "+BaseColumns._ID+" FROM "+ProgramaTabla.TABLE_NAME+" " +
				"WHERE "+BaseColumns._ID+"=new."+SesionColumnas.ID_PROGRAMA+" ) IS NULL)"+
				" THEN RAISE (ABORT,'Foreign Key Violation') END;"+ " END;");*/
	
	}


	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + ModeradorTabla.TABLE_NAME);
		onCreate(db);
	}

}
