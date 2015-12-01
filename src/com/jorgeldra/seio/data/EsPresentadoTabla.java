package com.jorgeldra.seio.data;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

public class EsPresentadoTabla {

	public static final String TABLE_NAME = "es_presentado";
	
	public static class EsPresentadoColumnas implements BaseColumns {
		
		public static final String ID_SESION = "id_ses";
		public static final String ID_MODERADOR = "id_mod";
	}
	
	public static void onCreate(SQLiteDatabase db){
		
		Log.i("entra","entra en la creacion de tabla es_presentado");
	
		/*db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,%s INTEGER, %s INTEGER NOT NULL, FOREIGN KEY ("+EsPresentadoColumnas.ID_MODERADOR+") " +
				"REFERENCES "+ ModeradorTabla.TABLE_NAME +"("+BaseColumns._ID+"))",  
		EsPresentadoTabla.TABLE_NAME,BaseColumns._ID, EsPresentadoColumnas.ID_SESION, EsPresentadoColumnas.ID_MODERADOR)); */
		
		/*db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,%s INTEGER NOT NULL,  FOREIGN KEY ("+EsPresentadoColumnas.ID_SESION+") " +
				"REFERENCES "+ SesionTabla.TABLE_NAME +"("+BaseColumns._ID+"), %s INTEGER NOT NULL, FOREIGN KEY ("+EsPresentadoColumnas.ID_MODERADOR+") " +
				"REFERENCES "+ ModeradorTabla.TABLE_NAME +"("+BaseColumns._ID+"))",  
		EsPresentadoTabla.TABLE_NAME,BaseColumns._ID, EsPresentadoColumnas.ID_SESION, EsPresentadoColumnas.ID_MODERADOR));  */
		
		
		 /*String create_table = "create table "
	            + EsPresentadoTabla.TABLE_NAME + " ("
	            + EsPresentadoColumnas.ID_SESION + " integer FOREIGN KEY ("+ EsPresentadoColumnas.ID_SESION +") REFERENCES "
	            + SesionTabla.TABLE_NAME + " ("+ BaseColumns._ID +"), " 
	            + EsPresentadoColumnas.ID_MODERADOR + " integer FOREIGN KEY ("+ EsPresentadoColumnas.ID_MODERADOR  +") REFERENCES "
	            + ModeradorTabla.TABLE_NAME + " ("+ BaseColumns._ID +"));";*/
		 
		// create table es_presentado (id_ses integer FOREIGN KEY (id_ses) REFERENCES sesion(id_sesion), id_mod integer FOREIGN KEY (id_mod) REFERENCES moderador (id_moderador));
		
		 //String crearTabla ="CREATE TABLE es_presentado (espresentado_id  INTEGER PRIMARY KEY NOT NULL,id_ses  INTEGER REFERENCES sesion(id_sesion),id_mod INTEGER REFERENCES STUDENTS(id_moderador))";
		 //String crearTabla ="CREATE TABLE es_presentado (id_ses  INTEGER REFERENCES sesion(id_sesion),id_mod INTEGER REFERENCES STUDENTS(id_moderador))";
		String crearTabla ="CREATE TABLE es_presentado (id_ses  INTEGER,id_mod INTEGER,FOREIGN KEY (id_ses) REFERENCES sesion(id_sesion),FOREIGN KEY (id_mod) REFERENCES moderador(id_moderador))";
		 
		 db.execSQL(String.format(crearTabla));
		/*db.execSQL("CREATE TRIGGER fk_es_presentado_id " +
				" BEFORE INSERT "+" ON "+EsPresentadoTabla.TABLE_NAME+
				" FOR EACH ROW BEGIN"+ " SELECT CASE WHEN ((SELECT "+BaseColumns._ID+" FROM "+ProgramaTabla.TABLE_NAME+" " +
				"WHERE "+BaseColumns._ID+"=new."+SesionColumnas.ID_PROGRAMA+" ) IS NULL)"+
				" THEN RAISE (ABORT,'Foreign Key Violation') END;"+ " END;");*/
	
	}


	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + EsPresentadoTabla.TABLE_NAME);
		onCreate(db);
	}

}
