package com.xiangshan.predicate.service.impls;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import com.xiangshan.generic.service.impls.BaseService;
import com.xiangshan.ontology.models.Statement;
import com.xiangshan.predicate.service.interfaces.PredicateSearchService;
import com.xiangshan.utils.JsonUtil;


@SuppressWarnings("rawtypes")
public class PredicateSearchServiceImpl extends BaseService implements PredicateSearchService{

	@Override
	public String query(String predicate) {
		// TODO Auto-generated method stub
		
		String sparqlStr = 
				"SELECT ?subject ?predicate ?object " +
				"WHERE {" 
				+"  ?subject ?predicate ?object."
				+ "	FILTER (REGEX(STR(?predicate), '#"+predicate+"$'))."
				+ "	FILTER (!REGEX(STR(?subject), 'Relation'))."
				+ "	FILTER (!REGEX(STR(?object), 'Relation')).}";
		
		Collection<Statement> statements = getOntologyResolver().query(sparqlStr);
		
		String result = null;
		
		if(statements.size()>0) result = JsonUtil.formatAsJson(statements);
		
		return result;
	}

	@Override
	public String getPredicates() {
		// TODO Auto-generated method stub
		String sparqlStr = 	
				"SELECT DISTINCT ?subject ?predicate ?object " 
				+"WHERE {" 
				+" {?subject ?predicate ?object."
				+ " FILTER (!REGEX(STR(?predicate), 'rdf'))."
				+ " FILTER (!REGEX(STR(?predicate), 'imports'))."
				+ "	FILTER (!REGEX(STR(?predicate), 'versionInfo'))."
				+ "	FILTER (!REGEX(STR(?predicate), 'oneOf'))."
				+ "	FILTER (!REGEX(STR(?predicate), 'unionOf'))."
				+ " FILTER (!REGEX(STR(?predicate), 'Relation'))."
				+ "	FILTER (!REGEX(STR(?predicate), 'inverseOf')).}"
				+ "}";
		
		Collection<Statement> statements = getOntologyResolver().query(sparqlStr);
		
		Collection<String> predicates = new HashSet<String>();
		
		Iterator<Statement> iter = statements.iterator();
		
		while(iter.hasNext()) {
			
			predicates.add(iter.next().getPredicate());
		}
			
		String result = JsonUtil.formatAsJson(predicates);
		
		return result;
	}

	@Override
	public String queryByPage(String predicate,int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		String sparqlStr = 
				"SELECT ?subject ?predicate ?object " +
				"WHERE {" 
				+"  ?subject ?predicate ?object."
				+ "	FILTER (REGEX(STR(?predicate), '#"+predicate+"$'))."
				+ "	FILTER (!REGEX(STR(?subject), 'Relation'))."
				+ "	FILTER (!REGEX(STR(?object), 'Relation')).}";
		
		Collection<Statement> statements = getOntologyResolver().queryByPage(pageNum,pageSize,sparqlStr);
		
		String result = null;
		
		if(statements.size()>0) result = JsonUtil.formatAsJson(statements);
		
		return result;
	}

}
