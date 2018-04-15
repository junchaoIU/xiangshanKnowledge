package com.xiangshan.relation.service.impls;

import java.util.Collection;

import com.xiangshan.generic.service.impls.BaseService;
import com.xiangshan.ontology.models.Statement;
import com.xiangshan.relation.service.interfaces.RelationSearchService;
import com.xiangshan.utils.JsonUtil;

/**
 * 关系检索Service类。
 * 
 * @author Rosahen
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public class RelationSearchServiceImpl extends BaseService implements RelationSearchService{

	@Override
	public String query(String subject, String object) {
		// TODO Auto-generated method stub
		
		String sparqlStr =
				
				"SELECT ?subject ?predicate ?object " 
				+"WHERE {" 
				+" {?subject ?predicate ?object."
				+" FILTER (REGEX(STR(?subject), '#"+subject+"$'))."
				+" FILTER (REGEX(STR(?object), '#"+object+"$'))."
				+" FILTER (!REGEX(STR(?subject), 'Relation'))."
				+" FILTER (!REGEX(STR(?object), 'Relation')).}"
				+" UNION {?subject ?predicate ?object."
				+" FILTER (REGEX(STR(?object), '#"+subject+"$'))."
				+" FILTER (REGEX(STR(?subject), '#"+object+"$'))."
				+" FILTER (!REGEX(STR(?subject), 'Relation'))."
				+" FILTER (!REGEX(STR(?object), 'Relation')).}"
				+ "}";
		
		Collection<Statement> statements = getOntologyResolver().query(sparqlStr);
		
		String result = null;
		
		if(statements.size()>0) result = JsonUtil.formatAsJson(statements);
		
		return result;
	}

	@Override
	public String queryByPage(String subject, String object, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		String sparqlStr =
				
				"SELECT ?subject ?predicate ?object " 
				+"WHERE {" 
				+" {?subject ?predicate ?object."
				+" FILTER (REGEX(STR(?subject), '#"+subject+"$'))."
				+" FILTER (REGEX(STR(?object), '#"+object+"$'))."
				+" FILTER (!REGEX(STR(?subject), 'Relation'))."
				+" FILTER (!REGEX(STR(?object), 'Relation')).}"
				+" UNION {?subject ?predicate ?object."
				+" FILTER (REGEX(STR(?object), '#"+subject+"$'))."
				+" FILTER (REGEX(STR(?subject), '#"+object+"$'))."
				+" FILTER (!REGEX(STR(?subject), 'Relation'))."
				+" FILTER (!REGEX(STR(?object), 'Relation')).}"
				+ "}";
		
		Collection<Statement> statements = getOntologyResolver().queryByPage(pageNum, pageSize, sparqlStr);
		
		String result = null;
		
		if(statements.size()>0) result = JsonUtil.formatAsJson(statements);
		
		return result;
	}

}
