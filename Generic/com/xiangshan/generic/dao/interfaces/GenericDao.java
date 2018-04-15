package com.xiangshan.generic.dao.interfaces;

import java.util.Collection;

public interface GenericDao<T> {

	void save(T object);
	
	void update(T object);
	
	void delete(T object);
	
	T getById(String id,Class<T> clazz);
	
	Collection<T> list(Class<T> clazz);
}
