package com.xiangshan.timespace.service.impls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.xiangshan.data.interfaces.Data;
import com.xiangshan.data.models.TimeSpaceData;
import com.xiangshan.generic.service.impls.BaseService;
import com.xiangshan.ontology.models.Statement;
import com.xiangshan.timespace.service.interfaces.TimeSpaceSearchService;
import com.xiangshan.utils.DataUtil;
import com.xiangshan.utils.JsonUtil;

@SuppressWarnings("rawtypes")
public class TimeSpaceSearchServiceImpl extends BaseService implements TimeSpaceSearchService{

	@Override
	public String query(String keyword, String mode) {
		// TODO Auto-generated method stub
		
		Collection<Data> datas = new ArrayList<Data>();
		
		if(mode.equals("time")) {
		
			Collection<Integer> times = getTimes(keyword);
			
			Iterator<Integer> iter = times.iterator();
			
			while(iter.hasNext()) {
				
				Integer time = iter.next();
				
				Data temp = getDataByTime(keyword, time+"");
				
				datas.add(temp);
			}
			
		}else {
			
			Collection<String> positions = getPositions(keyword);
			
			Iterator<String> iter = positions.iterator();
			
			while(iter.hasNext()) {
				
				String position = iter.next();
				
				Data temp = getDataByPosition(keyword, position);
				
				datas.add(temp);
			}
		}
		
		String result = null;
		
		if(datas.size()>0) result = JsonUtil.formatAsJson(datas);
		
		return result;
	}

	@Override
	public void filter(Collection<Statement> statements, String keyword) {
		// TODO Auto-generated method stub
		
		for(int i=0;i<statements.size();i++) {
			
			Statement stat = ((ArrayList<Statement>) statements).get(i);
			
			if(!(stat.getSubject().equals(keyword)||stat.getObject().equals(keyword))){
				
				statements.remove(stat);
				
				i--;
			
			}else if(stat.getSubject().equals(keyword)&&stat.getSubject().equals(stat.getObject())) {
				
				statements.remove(stat);
				
				i--;
			}
		}
	}

	@Override
	public String getTimeline(String keyword) {
		// TODO Auto-generated method stub
		
		Collection<Integer> times = getTimes(keyword);
		
		String result = JsonUtil.formatAsJson(times);
		
		return result;
	}
	

	private List<Integer> getTimes(String keyword){
		
		String sparqlStr = 
					"SELECT ?subject ?predicate ?object "+
					"WHERE { ?subject ?relation ?composite "+
					"FILTER (REGEX(STR(?subject),'"+keyword+"'))."+
					"FILTER (REGEX(STR(?relation),'Relation'))."+
					"FILTER (!REGEX(STR(?subject),'Relation'))."+
					"{"+
					"SELECT ?composite ?predicate ?object "+
					"WHERE { ?composite ?predicate ?object "+
					"FILTER (REGEX(STR(?predicate),'年份'))."+
					"FILTER (!ISIRI(?object))."+
					"}"+
					"}"+
					"}";
		
		Collection<Statement> statements = getOntologyResolver().query(sparqlStr);

		List<Integer> times = new ArrayList<Integer>();
		
		Iterator<Statement> iter = statements.iterator();
		
		while(iter.hasNext()) {
		
			Statement stat = iter.next();
			
			if(stat.getSubject().equals(keyword)) {
				
				String object = stat.getObject();
				
				int time = Integer.parseInt(object);
				
				if(!times.contains(time)) times.add(time);
			}
		}
		
		for(int i=0;i<times.size();i++) {
			
			for(int j=0;j<times.size()-1-i;j++) {
				
				if(times.get(j)>times.get(j+1)) {
					
					int temp = times.get(j);
					
					times.set(j, times.get(j+1));
					
					times.set(j+1, temp);
				}
			}
		}
		
		return times;
		
	}

	private Data getDataByTime(String keyword, String time) {
		// TODO Auto-generated method stub

		String sparqlStr = 
				"SELECT ?subject ?predicate ?object "+
				"WHERE { ?subject ?relation ?composite "+
				"FILTER (REGEX(STR(?subject),'"+keyword+"$'))."+
				"{ SELECT ?composite ?attribute1 ?value1 "+
				"WHERE { ?composite ?attribute1 ?value1. "+
				"FILTER (REGEX(STR(?attribute1),'开始年份'))."+
				"FILTER (?value1<="+time+")."+
				"}"+
				"}"+
				"{ SELECT ?composite ?attribute2 ?value2 "+
				"WHERE { ?composite ?attribute2 ?value2. "+
				"FILTER (REGEX(STR(?attribute2),'结束年份'))."+
				"FILTER (?value2>="+time+")."+
				"}"+
				"}"+
				"{ SELECT ?composite ?predicate ?object "+
				"WHERE { ?composite ?predicate ?object. "+
				"FILTER (!REGEX(STR(?predicate), 'rdf'))."+
				"FILTER (!REGEX(STR(?predicate),'Relation'))."+
				"FILTER (!REGEX(STR(?predicate),'相关地点'))."+
				"FILTER (!REGEX(STR(?object),'"+keyword+"$'))."+
				"FILTER (ISIRI(?composite))."+
				"FILTER (ISIRI(?object))."+
				"}"+
				"}"+
				"}";
		
		Collection<Statement> statements = getOntologyResolver().query(sparqlStr);

		TimeSpaceData data = new TimeSpaceData();
		
		data.setKey(time);
		
		Data stmts = DataUtil.transferStatementsToData(statements);
		
		data.setData(stmts);
		
		return data;
	}

	@Override
	public String queryByTimeAndPage(String keyword, String time, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		String sparqlStr = 
				"SELECT ?subject ?predicate ?object "+
				"WHERE { ?subject ?relation ?composite "+
				"FILTER (REGEX(STR(?subject),'"+keyword+"$'))."+
				"{ SELECT ?composite ?attribute1 ?value1 "+
				"WHERE { ?composite ?attribute1 ?value1. "+
				"FILTER (REGEX(STR(?attribute1),'开始年份'))."+
				"FILTER (?value1<="+time+")."+
				"}"+
				"}"+
				"{ SELECT ?composite ?attribute2 ?value2 "+
				"WHERE { ?composite ?attribute2 ?value2. "+
				"FILTER (REGEX(STR(?attribute2),'结束年份'))."+
				"FILTER (?value2>="+time+")."+
				"}"+
				"}"+
				"{ SELECT ?composite ?predicate ?object "+
				"WHERE { ?composite ?predicate ?object. "+
				"FILTER (!REGEX(STR(?predicate), 'rdf'))."+
				"FILTER (!REGEX(STR(?predicate),'Relation'))."+
				"FILTER (!REGEX(STR(?predicate),'相关地点'))."+
				"FILTER (!REGEX(STR(?object),'"+keyword+"$'))."+
				"FILTER (ISIRI(?composite))."+
				"FILTER (ISIRI(?object))."+
				"}"+
				"}"+
				"}";
		
		Collection<Statement> statements = getOntologyResolver().queryByPage(pageNum,pageSize,sparqlStr);

		String result = null;
		
		if(statements.size()>0) {
			
			Data data = DataUtil.transferStatementsToData(statements);
		
			result = JsonUtil.formatAsJson(data);
		}
		
		return result;
	}

	@Override
	public String queryByTime(String keyword, String time) {
		// TODO Auto-generated method stub
		Data data = getDataByTime(keyword,time);

		return JsonUtil.formatAsJson(data);
	}

	@Override
	public String getPositionLine(String keyword) {
		// TODO Auto-generated method stub
		Set<String> positions = getPositions(keyword);
		String result = JsonUtil.formatAsJson(positions);
		
		return result;
	}
	
	private Set<String> getPositions(String keyword){
		String sparqlStr = 
				"SELECT ?subject ?predicate ?object "+
				"WHERE { ?subject ?relation ?composite "+
				"FILTER (REGEX(STR(?subject),'"+keyword+"'))."+
				"FILTER (REGEX(STR(?relation),'Relation'))."+
				"FILTER (!REGEX(STR(?subject),'Relation'))."+
				"{"+
				"SELECT ?composite ?predicate ?object "+
				"WHERE { ?composite ?predicate ?object "+
				"FILTER (REGEX(STR(?predicate),'相关地点'))."+
				"}"+
				"}"+
				"}";
	
		Collection<Statement> statements = getOntologyResolver().query(sparqlStr);
	
		Set<String> positions = new HashSet<String>();
		
		Iterator<Statement> iter = statements.iterator();
		
		while(iter.hasNext()) {
		
			Statement stat = iter.next();
			
			if(stat.getSubject().equals(keyword)) {
				
				String position = stat.getObject();
				
				positions.add(position);
			}
		}
		
		return positions;
	}

	@Override
	public String queryByPositionAndPage(String keyword, String position, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		String sparqlStr = 
				"SELECT ?subject ?predicate ?object "+
				"WHERE { ?subject ?relation ?composite "+
				"FILTER (REGEX(STR(?subject),'"+keyword+"$'))."+
				"{ SELECT ?composite ?attribute ?value"+
				"WHERE { ?composite ?attribute ?value. "+
				"FILTER (REGEX(STR(?attribute),'相关地点'))."+
				"FILTER (REGEX(STR(?value),'"+position+"$'))."+
				"}"+
				"}"+
				"{ SELECT ?composite ?predicate ?object "+
				"WHERE { ?composite ?predicate ?object. "+
				"FILTER (!REGEX(STR(?predicate), 'rdf'))."+
				"FILTER (!REGEX(STR(?predicate),'Relation'))."+
				"FILTER (!REGEX(STR(?predicate),'相关地点'))."+
				"FILTER (!REGEX(STR(?object),'"+keyword+"$'))."+
				"FILTER (ISIRI(?composite))."+
				"FILTER (ISIRI(?object))."+
				"}"+
				"}"+
				"}";
		
		Collection<Statement> statements = getOntologyResolver().queryByPage(pageNum,pageSize,sparqlStr);

		String result = null;
		
		if(statements.size()>0) {
			
			Data data = DataUtil.transferStatementsToData(statements);
		
			result = JsonUtil.formatAsJson(data);
		}
		
		return result;
	}

	@Override
	public String queryByPosition(String keyword, String position) {
		// TODO Auto-generated method stub
		
		Data data = getDataByPosition(keyword, position);
		
		return JsonUtil.formatAsJson(data);
	}
	
	private Data getDataByPosition(String keyword, String position) {
		String sparqlStr = 
				"SELECT ?subject ?predicate ?object "+
				"WHERE { ?subject ?relation ?composite "+
				"FILTER (REGEX(STR(?subject),'"+keyword+"$'))."+
				"{ SELECT ?composite ?attribute ?value"+
				"WHERE { ?composite ?attribute ?value. "+
				"FILTER (REGEX(STR(?attribute),'相关地点'))."+
				"FILTER (REGEX(STR(?value),'"+position+"$'))."+
				"}"+
				"}"+
				"{ SELECT ?composite ?predicate ?object "+
				"WHERE { ?composite ?predicate ?object. "+
				"FILTER (!REGEX(STR(?predicate), 'rdf'))."+
				"FILTER (!REGEX(STR(?predicate),'Relation'))."+
				"FILTER (!REGEX(STR(?predicate),'相关地点'))."+
				"FILTER (!REGEX(STR(?object),'"+keyword+"$'))."+
				"FILTER (!REGEX(STR(?object),'"+position+"$'))."+
				"FILTER (ISIRI(?composite))."+
				"FILTER (ISIRI(?object))."+
				"}"+
				"}"+
				"}";
		
		Collection<Statement> statements = getOntologyResolver().query(sparqlStr);

		
		TimeSpaceData data = new TimeSpaceData();
		
		data.setKey(position);
		
		Data stmts = DataUtil.transferStatementsToData(statements);
		
		data.setData(stmts);
		
		return data;
	}

}
