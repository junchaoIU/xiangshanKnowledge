package com.xiangshan.semantic.utils;

import java.util.Collection;

import com.xiangshan.ontology.models.Statement;
import com.xiangshan.ontology.resolve.impls.JenaResolver;
import com.xiangshan.ontology.resolve.interfaces.OntologyResolver;

public class Query {

	public static Collection<Statement> query(String subject, String predicate) {
		
		OntologyResolver resolver = new JenaResolver();

		String sparqlStr = "SELECT ?subject ?predicate ?object " + "WHERE {" + " {?subject ?predicate ?object."
				+ " FILTER (REGEX(STR(?subject), '#" + subject + "$'))." + " FILTER (REGEX(STR(?predicate), '"
				+ predicate + "'))." + " FILTER (!REGEX(STR(?subject), 'Relation'))."
				+ " FILTER (!REGEX(STR(?object), 'Relation')).}" + "}";

		return resolver.query(sparqlStr);
	}
}
