package com.jorgeldra.seio.data;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.text.format.Time;
import android.util.Log;

import com.jorgeldra.seio.data.SesionTabla.SesionColumnas;
import com.jorgeldra.seio.entidad.Location;
import com.jorgeldra.seio.entidad.Sesion;

public class SesionDao implements Dao<Sesion> {

	
	private static final String INSERT = "insert into "
			+ SesionTabla.TABLE_NAME + "(" + SesionColumnas.ID_SESION + ", " + SesionColumnas.IDENTIFIER + ", "
			+ SesionColumnas.NAME + ", "
			+ SesionColumnas.DESCRIPTION + ", "
			+ SesionColumnas.COMMENTS + ", "
			+ SesionColumnas.START + ", "
			+ SesionColumnas.END + ", "
			+ SesionColumnas.ID_LOCATION + ", "
			+ SesionColumnas.NAME_LOCATION + ", "
			+ SesionColumnas.GPS_COORDS + ", "
			+ SesionColumnas.VENUE + ", "
			+ SesionColumnas.ID_PROGRAMA + ")" + "values (?,?,?,?,?,?,?,?,?,?,?,?)";

	// database fields
	private SQLiteDatabase db;
	private SQLiteStatement insertStatement;
	
	public SesionDao(SQLiteDatabase db) {
		// TODO Auto-generated constructor stub
		  this.db = db;
	      insertStatement = db.compileStatement(SesionDao.INSERT);
	}

	@Override
	public long save(Sesion type) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public long save(Sesion sesion, Location location, int id_programa) {
		insertStatement.clearBindings();
		insertStatement.bindString(1, String.valueOf(sesion.getId()));
		insertStatement.bindString(2, String.valueOf(sesion.getIdentifier()));
		insertStatement.bindString(3, String.valueOf(sesion.getName()));
		insertStatement.bindString(4, String.valueOf(sesion.getDescription()));
		insertStatement.bindString(5, String.valueOf(sesion.getComments()));
		insertStatement.bindString(6, String.valueOf(sesion.getStart()));
		insertStatement.bindString(7, String.valueOf(sesion.getEnd()));
		if (location != null){
			insertStatement.bindString(8, String.valueOf(location.getId()));
			insertStatement.bindString(9, String.valueOf(location.getName()));
			insertStatement.bindString(10, String.valueOf(location.getGps_coords()));
			insertStatement.bindString(11, String.valueOf(location.getVenue()));
		}
		
		insertStatement.bindString(12, String.valueOf(id_programa));
		return insertStatement.executeInsert();
	}

	@Override
	public void update(Sesion type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Sesion type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Sesion get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sesion> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Sesion pasarDeCursorASesion(Cursor c) {
		Sesion sesion = null;
		if (c != null) {
			sesion = new Sesion();
			sesion.setId(c.getInt(0));
			sesion.setIdentifier(c.getString(1));
			sesion.setName(c.getString(2));
			sesion.setDescription(c.getString(3));
			sesion.setComments(c.getString(4));
			sesion.setStart(c.getString(5));
			sesion.setEnd(c.getString(6));
			
			Location location = new Location();
			location.setId(c.getInt(7));
			location.setName(c.getString(8));
			location.setGps_coords(c.getString(9));
			location.setVenue(c.getString(10));
			
			sesion.setLocation(location);
			
		}
		return sesion;
	}

	@Override
	public ArrayList<Sesion> getAllById(long id) {
		// TODO Auto-generated method stub
		ArrayList<Sesion> list = new ArrayList<Sesion>();
		ModeradorDao modDao = new ModeradorDao(db);
		TrabajoDao trabajoDao = new TrabajoDao(db);
		
		Cursor c = db.query(SesionTabla.TABLE_NAME, new String[] {
				SesionColumnas.ID_SESION, SesionColumnas.IDENTIFIER,SesionColumnas.NAME, SesionColumnas.DESCRIPTION,
				SesionColumnas.COMMENTS,SesionColumnas.START,SesionColumnas.END,SesionColumnas.ID_LOCATION,SesionColumnas.NAME_LOCATION,
				SesionColumnas.GPS_COORDS,SesionColumnas.VENUE}, SesionColumnas.ID_PROGRAMA + " = ?",
				new String[] { String.valueOf(id) },  null,null,"START ASC, IDENTIFIER ASC"); //ordenar por dos campos

		if (c.moveToFirst()) {
			do {
				Sesion sesion = this.pasarDeCursorASesion(c);
				sesion.setChairperson(modDao.get(modDao.obtenerIdModeradorPorIdSesion(sesion.getId()))); //a–adimos el moderador
				
				sesion.setListaPapers(trabajoDao.getAllById(sesion.getId())); //a–adimos la lista de trabajos
				
				if (sesion != null)
					list.add(sesion);
			} while (c.moveToNext());
		}
		if (!c.isClosed()) {
			c.close();
		}
		return list;
	}
	
	public ArrayList<Sesion> getSessionsByCurrentDateTime(){
		ArrayList<Sesion> list = new ArrayList<Sesion>();
		ModeradorDao modDao = new ModeradorDao(db);
		TrabajoDao trabajoDao = new TrabajoDao(db);
		
		//obtenemos dia actual y hora
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		String diaActual = today.year + "-" + today.month +"-"+today.monthDay;
		String horaActual = String.valueOf(today.hour);
		
		//Descomentar para buscar por hora real del dispositivo.
		//String sql = "select id_sesion,identifier,name,description,comments,start,end,id_location,name_location,gps_coords,venue from sesion,programa where (programa.fecha like '"+diaActual+"') and (programa._id=sesion.id_programa) and (sesion.start > "+horaActual+") order by sesion.start";;
		
		//para que muestre las de ejemplo
		String sql = "select id_sesion,identifier,name,description,comments,start,end,id_location,name_location,gps_coords,venue from sesion,programa where (programa.fecha like '2012-04-17') and (programa._id=sesion.id_programa) and (sesion.start > 12) order by sesion.start";;
		Cursor c = db.rawQuery(sql, null);
		
		
		
		if (c.moveToFirst()) {
			do {
				Sesion sesion = this.pasarDeCursorASesion(c);
				sesion.setChairperson(modDao.get(modDao.obtenerIdModeradorPorIdSesion(sesion.getId()))); //a–adimos el moderador
				
				sesion.setListaPapers(trabajoDao.getAllById(sesion.getId())); //a–adimos la lista de trabajos
				
				if (sesion != null)
					list.add(sesion);
			} while (c.moveToNext());
		}
		if (!c.isClosed()) {
			c.close();
		}
		return list;
	}
	
	
	public ArrayList<Location> getLocations(){
		ArrayList<Location> list = new ArrayList<Location>();
		
		
		//Descomentar para buscar por hora real del dispositivo.
		//String sql = "select id_sesion,identifier,name,description,comments,start,end,id_location,name_location,gps_coords,venue from sesion,programa where (programa.fecha like '"+diaActual+"') and (programa._id=sesion.id_programa) and (sesion.start > "+horaActual+") order by sesion.start";;
		
		//para que muestre las de ejemplo
		String sql = "select distinct id_location,name_location,gps_coords,venue from sesion where id_location !=0 order by id_location";
		Cursor c = db.rawQuery(sql, null);
		
		
		
		if (c.moveToFirst()) {
			do {
				Location location = this.pasarDeCursorALocation(c);
				//Log.d("location", "Value: " + location.getId());
				
				if (location != null)
					list.add(location);
			} while (c.moveToNext());
		}
		if (!c.isClosed()) {
			c.close();
		}
		return list;
	}
	
	private Location pasarDeCursorALocation(Cursor c) {
		Location location = null;
		if (c != null) {
			location = new Location();
			location.setId(c.getInt(0));
			location.setName(c.getString(1));
			location.setGps_coords(c.getString(2));
			location.setVenue(c.getString(3));
			
			
		}
		return location;
	}

}
