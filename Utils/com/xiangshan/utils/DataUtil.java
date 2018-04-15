package com.xiangshan.utils;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;

import com.xiangshan.data.interfaces.Data;
import com.xiangshan.data.models.EchartsData;
import com.xiangshan.data.models.EchartsLink;
import com.xiangshan.data.models.EchartsNode;
import com.xiangshan.ontology.models.Statement;

public class DataUtil {

	public static Data transferStatementToData(Statement statement){
		
		String classPath = OntDocumentManagerUtil.class.getResource("/").getPath();
		
		String applicationPath = classPath.substring(0,classPath.indexOf("WEB-INF"));

		String subjectImgPath = "images/"+statement.getSubject()+".jpg";
		
		String objectImgPath = "images/"+statement.getObject()+".jpg";
		
		File subjectImg = new File(applicationPath+subjectImgPath);
		
		File objectImg = new File(applicationPath+objectImgPath);
		
		if(!subjectImg.exists()) subjectImgPath = "images/"+statement.getSubjectClassName()+".jpg";
		
		if(!objectImg.exists()) objectImgPath = "images/"+statement.getObjectClassName()+".jpg";
		
		EchartsData data = new EchartsData();
		
		Collection<EchartsNode> nodes = new LinkedHashSet<EchartsNode>();
		
		Collection<EchartsLink> links = new LinkedHashSet<EchartsLink>();
		
		EchartsNode subject = new EchartsNode();
		
		EchartsNode object = new EchartsNode();
		
		EchartsLink link = new EchartsLink();
		
		subject.setName(statement.getSubject());
		subject.setSymbol("image://"+subjectImgPath);
		subject.setCategory(statement.getSubjectClassName());
		
		object.setName(statement.getObject());
		object.setSymbol("image://"+objectImgPath);
		object.setCategory(statement.getObjectClassName());
		
		link.setSource(statement.getSubject());
		link.setTarget(statement.getObject());
		link.setValue(statement.getPredicate());
		
		
		nodes.add(subject);
		nodes.add(object);
		
		links.add(link);
		
		data.setNodes(nodes);
		data.setLinks(links);
		
		return data;
		
	}
	
	public static Data transferStatementsToData(Collection<Statement> statements){
		
		Iterator<Statement> iter = statements.iterator();
		
		EchartsData data = new EchartsData();
		
		Collection<EchartsNode> nodes = new LinkedHashSet<EchartsNode>();
		
		Collection<EchartsLink> links = new LinkedHashSet<EchartsLink>();
		
		while(iter.hasNext()) {
			
			Statement statement = iter.next();
			
			EchartsData theData = (EchartsData) transferStatementToData(statement);
			
			Collection<EchartsNode> theNodes = theData.getNodes();
			
			Collection<EchartsLink> theLinks = theData.getLinks();
			
			Iterator<EchartsNode> nodeIter = theNodes.iterator();
			
			Iterator<EchartsLink> linkIter = theLinks.iterator();
			
			while(nodeIter.hasNext()) nodes.add(nodeIter.next());
			
			while(linkIter.hasNext()) links.add(linkIter.next());
			
		}
		
		data.setNodes(nodes);
		data.setLinks(links);
		
		return data;
	}
}
