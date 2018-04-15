package com.xiangshan.utils;

import java.util.HashMap;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.util.iterator.ExtendedIterator;

import com.opensymphony.xwork2.ActionContext;

public class NameSpaceClassMapUtil {

	private static HashMap<String,String> nameSpaceClassMap = new HashMap<String,String>();
	
	static {
		
		OntModel ontModel = (OntModel) ActionContext.getContext().getApplication().get("defaultOntModel");
		
		ExtendedIterator<OntClass> iter = ontModel.listHierarchyRootClasses();
		
		while(iter.hasNext()) {
			
			OntClass aClass = iter.next();
			
			nameSpaceClassMap.put(aClass.getNameSpace(), aClass.getLocalName());
		}
	}
	
	public static String getClassName(String nameSpace) {
		
		return nameSpaceClassMap.get(nameSpace);
	}
}
