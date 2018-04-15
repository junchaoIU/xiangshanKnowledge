package com.xiangshan.timespace.service.interfaces;

import java.util.Collection;

import com.xiangshan.ontology.models.Statement;

/**
 * 时空检索的Service接口。
 * 
 * @author Rosahen
 * @version 1.0
 */
public interface TimeSpaceSearchService {
	
	void filter(Collection<Statement> statements, String keyword);

	String query(String keyword, String mode);

	String queryByTimeAndPage(String keyword,String time,int pageNum,int pageSize);
	
	String queryByTime(String keyword,String time);
	
	String queryByPositionAndPage(String keyword,String position,int pageNum,int pageSize);
	
	String queryByPosition(String keyword,String position);
	
	String getTimeline(String keyword);
	
	String getPositionLine(String keyword);
}
