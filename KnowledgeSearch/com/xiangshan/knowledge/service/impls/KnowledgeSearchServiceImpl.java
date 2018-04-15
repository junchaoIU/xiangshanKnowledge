package com.xiangshan.knowledge.service.impls;

import java.util.Collection;

import com.xiangshan.knowledge.service.interfaces.KnowledgeSearchService;
import com.xiangshan.ontology.models.Statement;
import com.xiangshan.data.interfaces.Data;
import com.xiangshan.generic.service.impls.BaseService;
import com.xiangshan.utils.DataUtil;
import com.xiangshan.utils.JsonUtil;

/**
 * 知识检索Service类。
 * 
 * @author Rosahen
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public class KnowledgeSearchServiceImpl extends BaseService implements KnowledgeSearchService{

	@Override
	public String query(String keyword, String scope) {
		// TODO Auto-generated method stub
		
		String nameSpace = getOntologyResolver().getNameSpace(scope);
		String sparqlStr = 
				"SELECT DISTINCT ?subject ?predicate ?object " 
				+"WHERE {" 
				+" {?subject ?predicate ?object."
				+" FILTER (REGEX(STR(?subject), '#"+keyword+"$'))."
				+" FILTER (REGEX(STR(?object), '"+nameSpace+"'))."
				+" FILTER (!REGEX(STR(?predicate), 'rdf'))."
				+" FILTER (!REGEX(STR(?subject), 'Relation'))."
				+" FILTER (!REGEX(STR(?object), 'Relation'))."
				+" FILTER (ISIRI(?subject))."
				+" FILTER (ISIRI(?object)).}"
				+ "}";
		
		Collection<Statement> statements = getOntologyResolver().query(sparqlStr);
		
		String result = null;
		
		if(statements.size()>0) {
		
			Data data = DataUtil.transferStatementsToData(statements);
		
			result = JsonUtil.formatAsJson(data);
		}
		
		return result;
	}

	@Override
	public String queryByPage(String keyword, String scope, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		
		String nameSpace = getOntologyResolver().getNameSpace(scope);
		String sparqlStr = 
				"SELECT DISTINCT ?subject ?predicate ?object " 
				+"WHERE {" 
				+" {?subject ?predicate ?object."
				+" FILTER (REGEX(STR(?subject), '#"+keyword+"$'))."
				+" FILTER (REGEX(STR(?object), '"+nameSpace+"'))."
				+" FILTER (!REGEX(STR(?predicate), 'rdf'))."
				+" FILTER (!REGEX(STR(?subject), 'Relation'))."
				+" FILTER (!REGEX(STR(?object), 'Relation'))."
				+" FILTER (ISIRI(?subject))."
				+" FILTER (ISIRI(?object)).}"
				+ "}";
		
		Collection<Statement> statements = getOntologyResolver().queryByPage(pageNum, pageSize, sparqlStr);
		
		String result = null;
		
		if(statements.size()>0) {
		
			Data data = DataUtil.transferStatementsToData(statements);
		
			result = JsonUtil.formatAsJson(data);
		}
		
		return result;
	}

	@Override
	public String getDetails(String keyword) {
		// TODO Auto-generated method stub
		String sparqlStr = 
				"SELECT DISTINCT ?subject ?predicate ?object " 
				+"WHERE {" 
				+" {?subject ?predicate ?object."
				+" FILTER (REGEX(STR(?subject), '#"+keyword+"$'))."
				+" FILTER (!REGEX(STR(?subject), 'Relation'))."
				+" FILTER (!REGEX(STR(?object), 'Relation'))."
				+" FILTER (!ISIRI(?object)).}"
				+ "}";
		
		Collection<Statement> statements = getOntologyResolver().query(sparqlStr);
		
		String result = null;
		
		if(statements.size()<=0){
			Statement statement = new Statement(keyword,"名称",keyword);
			statement.setSubjectClassName(getOntologyResolver().getClassName(keyword));
			statements.add(statement);
		}
		
		Data data = DataUtil.transferStatementsToData(statements);
		
		result = JsonUtil.formatAsJson(data);
		
		return result;
	}
	
}
