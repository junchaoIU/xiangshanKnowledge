package com.xiangshan.generic.service.impls;

import com.xiangshan.generic.dao.interfaces.GenericDao;
import com.xiangshan.ontology.resolve.interfaces.OntologyResolver;

public class BaseService<T>{

	private OntologyResolver ontologyResolver;
	
	protected GenericDao<T> dao;

	public OntologyResolver getOntologyResolver() {
		return ontologyResolver;
	}

	public void setOntologyResolver(OntologyResolver ontologyResolver) {
		this.ontologyResolver = ontologyResolver;
	}

	public GenericDao<T> getDao() {
		return dao;
	}

	public void setDao(GenericDao<T> dao) {
		this.dao = dao;
	}
	
}
