package com.jorgeldra.seio.data;

import java.util.ArrayList;
import java.util.List;


public interface Dao<T> {

	long save(T type);

	void update(T type);

	void delete(T type);

	T get(long id);

	List<T> getAll();
	
	ArrayList<T> getAllById(long id);

	
}
