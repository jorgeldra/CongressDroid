package com.jorgeldra.seio.data;

import java.util.ArrayList;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.SystemClock;
import android.util.Log;

import com.jorgeldra.seio.entidad.Autor;
import com.jorgeldra.seio.entidad.Conferencia;
import com.jorgeldra.seio.entidad.CustomSection;
import com.jorgeldra.seio.entidad.ImagenConf;
import com.jorgeldra.seio.entidad.Location;
import com.jorgeldra.seio.entidad.Programa;
import com.jorgeldra.seio.entidad.Sesion;
import com.jorgeldra.seio.entidad.Trabajo;


/**
 * Android DataManagerImpl to encapsulate SQL and DB details. Includes
 * SQLiteOpenHelper, and uses Dao objects to create/update/delete and otherwise
 * manipulate data.
 * 
 * @author jorge diaz
 * 
 */



public class DataManagerImpl implements DataManager {

	private Context context;

	private SQLiteDatabase db;

	private ConferenciaDao conferenciaDao;
	
	private FavoritoDao favoritoDao;
	
	private ProgramaDao programaDao;
	
	private TrabajoDao trabajoDao;
	
	private CustomSectionDao customDao;
	
	private AutorDao autorDao;
	
	private SesionDao sesionDao;

	public DataManagerImpl(Context context) {
		this.context = context;

		SQLiteOpenHelper openHelper = new OpenHelper(this.context);
		db = openHelper.getWritableDatabase();
		
		
		Log.i("Estado base de datos","DataManagerImpl created, db open status: " + db.isOpen());

		conferenciaDao = new ConferenciaDao(db);
		favoritoDao = new FavoritoDao(db);
		programaDao = new ProgramaDao(db);
		trabajoDao = new TrabajoDao(db);
		customDao = new CustomSectionDao(db);
		autorDao = new AutorDao(db);
		sesionDao = new SesionDao(db);
	}

	public SQLiteDatabase getDb() {
		return db;
	}

	private void openDb() {
		if (!db.isOpen()) {
			db = SQLiteDatabase.openDatabase(DataConstants.DATABASE_PATH, null,SQLiteDatabase.OPEN_READWRITE);
			// since we pass db into DAO, have to recreate DAO if db is
			// re-opened
			conferenciaDao = new ConferenciaDao(db);
		}
	}

	@Override
	public void closeDb() { //CAMBIE ESTE METODO A PUBLIC  PORQUE NO SABIA COMO ACCEDER A ESTA CLASE PARA CERRAR LA BASE DE DATOS
		if (db.isOpen()) {
			db.close();
		}
	}

	private void resetDb() {
		Log.i("estado base de datos","Resetting database connection (close and re-open).");
		closeDb();
		SystemClock.sleep(500);
		openDb();
	}
	
	/**
	 * Check if the database exist
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	@Override
	public boolean checkDataBase() {
		
	    SQLiteDatabase checkDB = null;
	    try {
	        checkDB = SQLiteDatabase.openDatabase(DataConstants.DATABASE_PATH, null,SQLiteDatabase.OPEN_READONLY);
	        checkDB.close();
	    } catch (SQLiteException e) {
	        // database doesn't exist yet.
	    }
	    return checkDB != null ? true : false;
	}
	
	

	//
	// "Manager" data methods that wrap DAOs
	//
	// this lets us encapsulate usage of DAOs
	// we only expose methods app is actually using, and we can combine DAOs,
	// with logic in one place
	//

	//conferencia
	@Override
	public Conferencia getConferencia(long conferenciaId) {

		return conferenciaDao.get(conferenciaId);
	}

	@Override
	public ArrayList<Conferencia> getAllConferencias() {

		return conferenciaDao.getAll();
	}

	@Override
	public Conferencia findConferencia(String name) {

		return conferenciaDao.find(name);
	}

	@Override //no esta TESTEADO, porque lo hago todo por ahora en el onCreate
	public long saveConferencia(Conferencia conferencia) {

		long confId = 0L;

	      // put it in a transaction, since we're touching multiple tables
	      try {
	         db.beginTransaction();

	         // first save movie                                 
	         confId = conferenciaDao.save(conferencia);
	         Log.i("info id save",""+confId);
	         // second, make sure categories exist, and save movie/category association
	         // (this makes multiple queries, but usually not many cats, could just save and catch exception too, but that's ugly)
	         if (conferencia.getListaImagenesConf().size() > 0) {
	            for (ImagenConf ic : conferencia.getListaImagenesConf()) {
	               long confImaId = 0L;
	               
	            }
	         }

	         db.setTransactionSuccessful();
	      } catch (SQLException e) {
	         Log.e("Error", "Error saving conferences (transaction rolled back)", e);
	         confId = 0L;
	      } finally {
	         // an "alias" for commit
	         db.endTransaction();
	      }
		//empezamos transaccion
		
		//hacer el save en conferenciadao
		
		//recuperar el maximo indice en conferencia +1
		
		//bucle recorriendo conferencia imagenes con el id fijado, y variando la url
		
		
		//hacemos save de imagenCnf
		//cerramos transaccion
		
		return confId;
	}

	@Override
	public void deleteConferencia(Conferencia conferencia) {
		conferenciaDao.delete(conferencia);
	}

	@Override
	public long saveFavorito(int trabajo_id) {
		long favoId;
		favoId = favoritoDao.save(trabajo_id);		
		return favoId;
	}

	@Override
	public void deleteFavorito(int trabajo_id) {
		// TODO Auto-generated method stub
		favoritoDao.delete(trabajo_id);	
	}
	
	@Override
	public boolean updateFavoritoEnTrabajo(String valor, int id_trabajo){
		return trabajoDao.updateFavourite(id_trabajo, valor);	
	}
	

	@Override
	public ArrayList<Programa> obtenerListaPrograma() {
		// TODO Auto-generated method stub
		return programaDao.getAll();
	}
	
	@Override
	public ArrayList<Trabajo> getAllFavouritePapers(){
		return trabajoDao.getAllFavouritePapers();
	}
	
	@Override
	public ArrayList<CustomSection> obtenerListaCustomSection() {
		// TODO Auto-generated method stub
		return customDao.getAll();
	}
	
	@Override
	public ArrayList<Autor> obtenerListaAutores(){
		
		return autorDao.getAll();
	}

	//sesion
	public ArrayList<Sesion> obtenerListaSesionesPorFechaDia(){
		return sesionDao.getSessionsByCurrentDateTime();
	}
	
	public ArrayList<Location> obtenerSedes(){
		return sesionDao.getLocations();
	}
}
