package com.xiangshan.generic.dao.impls;

import java.util.Collection;

import com.xiangshan.generic.dao.interfaces.GenericDao;

public class GenericDaoImpl<T> extends BaseDao implements GenericDao<T>{

	@Override
	public void save(T object) {
		// TODO Auto-generated method stub
		getCurrentSession().save(object);
	}

	@Override
	public void update(T object) {
		// TODO Auto-generated method stub
		getCurrentSession().update(object);
	}
	

	@Override
	public void delete(T object) {
		// TODO Auto-generated method stub
		getCurrentSession().delete(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getById(String id, Class<T> clazz) {
		// TODO Auto-generated method stub
		return (T) getCurrentSession().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<T> list(Class<T> clazz) {
		// TODO Auto-generated method stub
		return getCurrentSession().createCriteria(clazz).list();
	}

	
}
