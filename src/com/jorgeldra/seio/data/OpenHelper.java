package com.jorgeldra.seio.data;


import java.io.IOException;
import java.util.ArrayList;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.jorgeldra.seio.entidad.Autor;
import com.jorgeldra.seio.entidad.Conferencia;
import com.jorgeldra.seio.entidad.CustomSection;
import com.jorgeldra.seio.entidad.ImagenConf;
import com.jorgeldra.seio.entidad.Programa;
import com.jorgeldra.seio.entidad.Sesion;
import com.jorgeldra.seio.entidad.Trabajo;


public class OpenHelper extends SQLiteOpenHelper {

	public static  int DATABASE_VERSION = 1;
	
	//private static String DB_PATH = "/data/data/com.android.seio/databases/";
	
	//private static final String DATABASE_NAME = "seio.db";

	private Context context;

    public OpenHelper(final Context context) {
    	
		super(context, DataConstants.DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onOpen(final SQLiteDatabase db) {
		super.onOpen(db);
		if (!db.isReadOnly()) {
			// versions of SQLite older than 3.6.19 don't support foreign keys
			// and neither do any version compiled with SQLITE_OMIT_FOREIGN_KEY
			// http://www.sqlite.org/foreignkeys.html#fk_enable
			//
			// make sure foreign key support is turned on if it's there (should
			// be already, just a double-checker)
			db.execSQL("PRAGMA foreign_keys=ON;");

			// then we check to make sure they're on
			// (if this returns no data they aren't even available, so we
			// shouldn't even TRY to use them)
			Cursor c = db.rawQuery("PRAGMA foreign_keys", null);
			if (c.moveToFirst()) {
				int result = c.getInt(0);
				Log.i("estado base de datos",
						"SQLite foreign key support (1 is on, 0 is off): "
								+ result);
			} else {
				// could use this approach in onCreate, and not rely on foreign
				// keys it not available, etc.
				Log.i("estado base de datos",
						"SQLite foreign key support NOT AVAILABLE");
				// if you had to here you could fall back to triggers
			}
			if (!c.isClosed()) {
				c.close();
			}
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i("estado base de datos","DataHelper.OpenHelper onCreate creating database "+ DataConstants.DATABASE_NAME);

		//creamos las tablas
		CustomSectionTabla.onCreate(db);
		
		ConferenciaTabla.onCreate(db);
		
		ImagenesConfTabla.onCreate(db);
		
		ProgramaTabla.onCreate(db);
		
		SesionTabla.onCreate(db);
		
		ModeradorTabla.onCreate(db);
		
		EsPresentadoTabla.onCreate(db);
		
		TrabajoTabla.onCreate(db);
		
		FavoritoTabla.onCreate(db);
		
		AutorTabla.onCreate(db);
		
		SeComponeTrabajoAutorTabla.onCreate(db);
		
		//insertamos en base de datos las secciones custom
		CustomSectionDao customDao = new CustomSectionDao(db);
		ArrayList<CustomSection> customSections = com.jorgeldra.seio.MainActivity.almacenCustomSection.listaCustomSection();
		for (CustomSection custom : customSections){
			customDao.save(custom);
		}
		
		//fin de custom
		
		ConferenciaDao conferenciaDao = new ConferenciaDao(db);
		
		ArrayList<Conferencia> conferencia = com.jorgeldra.seio.MainActivity.almacenConf.listaConferencia();
		
		ImagenesConfDao imDao = new ImagenesConfDao(db);
		
		//OJO deberia hacerse dentro de una transacción, ¡¡CORREGIR¡¡
		//guardamos en tabla conferencia y en imagenesConferencia
		int ultimoId=0;
		for (Conferencia conf : conferencia) {
			//guardamos en la base de datos y obtenemos el ultimo id insertado
			ultimoId = (int) conferenciaDao.save(new Conferencia(conf.getTitle(), conf.getTitle2(), conf.getPresentation(), conf.getHome_text(),conf.getListaImagenesConf()));
			//almacenamos las imagenes en la tabla de imagenes conferencia
			for (ImagenConf im: conf.getListaImagenesConf()){
				imDao.save(new ImagenConf(ultimoId,im.getUrl()));
			}
			
		}
		
		
		//guardamos en tabla programa
		ArrayList<Programa> programa = com.jorgeldra.seio.MainActivity.almacenPrograma.listaPrograma();
		ProgramaDao programaDao = new ProgramaDao(db);
		int ultimoIdPrograma = 0;
		
		for (Programa prog: programa){
			ArrayList<Sesion> sesion = prog.getListaSesion();
			SesionDao sesionDao = new SesionDao(db);
			//TODO aqui dentro sera necesario ir insertando en las bases de datos de sesion, moderador,trabajos, autor etc...
			ultimoIdPrograma = (int) programaDao.save(new Programa(prog.getFecha(),null),1); //insertamos un 1 por el id de conferenci/a (solo tenemos un congreso)
			
			
			for (Sesion ses: sesion){
				//Location location = new Location (ses.getLocation().getId(),ses.getLocation().getName(),ses.getLocation().getGps_coords(),ses.getLocation().getVenue());
				
				if (ses.getLocation()!= null){
					sesionDao.save(new Sesion(ses.getId(),ses.getIdentifier(),ses.getName(),ses.getDescription(),ses.getComments(),ses.getStart(),ses.getEnd(),null,null,null), ses.getLocation(), ultimoIdPrograma);
				}else{
					sesionDao.save(new Sesion(ses.getId(),ses.getIdentifier(),ses.getName(),ses.getDescription(),ses.getComments(),ses.getStart(),ses.getEnd(),null,null,null), null, ultimoIdPrograma);
				}
				
				//insertamos en tabla moderador y en la tabla es_presentado si no es nulo
				//NO NECESARIA, SOLO TENEMOS UN MODERADOR POR SESION, METER CAMPOS DENTRO DE TABLA SESION
				if (ses.getChairperson() != null){
					ModeradorDao moderadorDao = new ModeradorDao(db);
					
					//Log.i("aviso",""+ ses.getChairperson().getId())
					//si no hay un id de moderdor ya insertado en la tabla de moderador lo agregamos
					if (moderadorDao.get(ses.getChairperson().getId()) == null){ 
						moderadorDao.save(ses.getChairperson());
					}
					//siempre es necesario insertar el id actual de moderador + el id de sesion en la tabla que las relaciona
					EsPresentadoDao espresDao = new EsPresentadoDao(db);
					espresDao.save(ses.getId(), ses.getChairperson().getId());
				}
				
				//insertamos en tabla trabajos
				ArrayList<Trabajo> trabajos = ses.getListaPapers();
				TrabajoDao trabajoDao = new TrabajoDao(db);
				for (Trabajo trab: trabajos){
					//Log.i(" empieza inserta trabajo",""+ses.getId()+" " +trab.getTitle());
					trabajoDao.save(trab, ses.getId());
					//Log.i("insertado trabajo",""+ses.getId()+" " +trab.getId());
					
					//insertamos en tabla de autores
					ArrayList<Autor> autores = trab.getListaAutores();
					for (Autor aut: autores){
						AutorDao autorDao = new AutorDao(db);
						//long insertado = autorDao.save(aut,trab.getId());
						autorDao.save(aut,trab.getId());
						/*SeComponeTrabajoAutorDao secompone = new SeComponeTrabajoAutorDao(db);
						if (insertado == 0){
							if (ses.getId() != 0){
								secompone.save(trab.getId(),ses.getId());
								
							}else{
								secompone.save(trab.getId(),(int) insertado);
							}
							 //problema con los id que vienen nulos de autores
						}else{
							secompone.save(trab.getId(),(int) insertado);
							 //problema con los id que vienen nulos de autores
						}*/
						
					}
					
					//ejemplo de hacer volcado de favoritos
					if (trab.getFavourite()){
						FavoritoDao favoritoDao = new FavoritoDao(db);
						favoritoDao.save(trab.getId());
						//favoritoDao.delete(208); //ejemplo de borrar registro
					}
					
				}
				
				
				
				
			}
		}
	}
	
	/**
     * Copies database from the local assets folder to the system folder
     * @throws IOException
    */
    /*private void copyDataBase() throws IOException {

        // Open the app database file as the input stream
        InputStream myInput = context.getAssets().open(DATABASE_NAME);

        // Path to the empty temporary database to be replaced
        String outFileName = DB_PATH + DATABASE_NAME;

        // Open the empty database file as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // Transfer bytes from the input file to the output file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }*/

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		/*Log.i("estado base de datos", "SQLiteOpenHelper onUpgrade - oldVersion:"+ oldVersion + " newVersion:" + newVersion);
		
		if(newVersion > oldVersion){                    
            context.deleteDatabase(DataConstants.DATABASE_NAME);
            
		}*/
		
		Toast.makeText(context, "Base de datos actualizada correctamente.", Toast.LENGTH_LONG).show();  
	    /*if (oldVersion < newVersion) {  
	        Log.v("Database Upgrade", "Database version higher, upgrading");  
	        try { 
	            copyDataBase(); 
	        } catch (IOException e) { 
	            throw new Error("Error upgrading database"); 
	        } 
	    } */
		if (oldVersion < newVersion) {
			db.execSQL("DROP TABLE IF EXISTS autor");
			db.execSQL("DROP TABLE IF EXISTS trabajo");
			db.execSQL("DROP TABLE IF EXISTS conferencia");
			db.execSQL("DROP TABLE IF EXISTS imagenesConferencia");
			db.execSQL("DROP TABLE IF EXISTS programa");
			db.execSQL("DROP TABLE IF EXISTS sesion");
			db.execSQL("DROP TABLE IF EXISTS moderador");
			db.execSQL("DROP TABLE IF EXISTS se_compone_TrabajoAutor");
			db.execSQL("DROP TABLE IF EXISTS es_presentado");
			db.execSQL("DROP TABLE IF EXISTS custom_section");
			
		    
		    onCreate(db);
		}
		

		//ConferenciaTabla.onUpgrade(db, oldVersion, newVersion);
		
		//ImagenesConfTabla.onUpgrade(db, oldVersion, newVersion);
	}
	
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    onUpgrade(db, newVersion, newVersion);
	}

}
