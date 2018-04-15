package com.xiangshan.indistinct.service.impls;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;

import com.xiangshan.generic.service.impls.BaseService;
import com.xiangshan.indistinct.service.interfaces.IndistinctSearchService;
import com.xiangshan.ontology.models.Statement;
import com.xiangshan.utils.JsonUtil;

@SuppressWarnings("rawtypes")
public class IndistinctSearchServiceImpl extends BaseService implements IndistinctSearchService{

	@Override
	public String queryKnowledge(String keyword) {
		// TODO Auto-generated method stub
		
		String result = null ;
		
		if(keyword!=null&&!keyword.equals("")) {
			
			String sparqlStr = 
					"SELECT DISTINCT ?subject ?predicate ?object " 
					+"WHERE {" 
					+" {?subject ?predicate ?object."
					+" FILTER (REGEX(STR(?subject), '"+keyword+"'))."
					+" FILTER (!REGEX(STR(?predicate), 'rdf'))."
					+" FILTER (!REGEX(STR(?subject), 'Relation'))."
					+" FILTER (!REGEX(STR(?object), 'Relation'))."
					+" FILTER (ISIRI(?subject))."
					+" FILTER (ISIRI(?object)).}"
					+" UNION {?subject ?predicate ?object."
					+" FILTER (REGEX(STR(?object), '"+keyword+"'))."
					+" FILTER (!REGEX(STR(?predicate), 'rdf'))."
					+" FILTER (!REGEX(STR(?subject), 'Relation'))."
					+" FILTER (!REGEX(STR(?object), 'Relation'))."
					+" FILTER (ISIRI(?subject))."
					+" FILTER (ISIRI(?object)).}"
					+ "}";
			
			Collection<Statement> statements = getOntologyResolver().query(sparqlStr);
			
			Collection<String> knowledges = new LinkedHashSet<String>();
			
			Iterator<Statement> iter = statements.iterator();
			
			while(iter.hasNext()) {
				
				Statement statement = iter.next();
				
				if(statement.getSubject().equals(keyword)) {
					knowledges.add(statement.getSubject());
					break;
				}else if(statement.getObject().equals(keyword)) {
					knowledges.add(statement.getObject());
					break;
				}
			}
			
			if(knowledges.size()<4) {
				
				iter = statements.iterator();
				
				while(iter.hasNext()&&knowledges.size()<4) {
					
					Statement statement = iter.next();
					
					if(statement.getSubject().contains(keyword)&&knowledges.size()<4) knowledges.add(statement.getSubject());
					
					if(statement.getObject().contains(keyword)&&knowledges.size()<4) knowledges.add(statement.getObject());
				}
			}
			
			result = JsonUtil.formatAsJson(knowledges);
		}
		
		return result;
		
	}

	@Override
	public String queryPredicate(String keyword) {
		// TODO Auto-generated method stub
		
		String result = "";
		
		if(keyword!=null&&!keyword.equals("")) {
			
			String sparqlStr = 	
					"SELECT DISTINCT ?subject ?predicate ?object " 
					+"WHERE {" 
					+" {?subject ?predicate ?object."
					+ "FILTER REGEX(STR(?predicate),'"+keyword+"')."
					+ " FILTER (!REGEX(STR(?predicate), 'rdf'))."
					+ " FILTER (!REGEX(STR(?predicate), 'imports'))."
					+ "	FILTER (!REGEX(STR(?predicate), 'versionInfo'))."
					+ "	FILTER (!REGEX(STR(?predicate), 'oneOf'))."
					+ "	FILTER (!REGEX(STR(?predicate), 'unionOf'))."
					+ " FILTER (!REGEX(STR(?predicate), 'Relation'))."
					+ "	FILTER (!REGEX(STR(?predicate), 'inverseOf')).}"
					+ "}";
			
			Collection<Statement> statements = getOntologyResolver().query(sparqlStr);
			
			Collection<String> predicates = new LinkedHashSet<String>();
			
			Iterator<Statement> iter = statements.iterator();
			
			while(iter.hasNext()) {
				
				Statement statement = iter.next();
				
				if(statement.getPredicate().equals(keyword)) {
					predicates.add(statement.getPredicate());
					break;
				}
			}
			
			if(predicates.size()<4) {
				
				iter = statements.iterator();
				
				while(iter.hasNext()&&predicates.size()<4) {
					
					Statement statement = iter.next();
					
					predicates.add(statement.getPredicate());
				}
			}
			
			result = JsonUtil.formatAsJson(predicates);
		
		}
		
		return result;
	}

}
