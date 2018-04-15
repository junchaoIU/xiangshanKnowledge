package com.xiangshan.relation.service.interfaces;


/**
 * 关系检索Service接口。
 * 
 * @author Rosahen
 * @version 1.0
 */
public interface RelationSearchService {

	/**
	 * 检索主语和宾语之间的关系的方法。
	 * 
	 * @param subject 主语。
	 * @param object 宾语。
	 * @return 检索结果的json格式字符串。
	 */
	String query(String subject,String object);
	
	/**
	 * 分页检索主语和宾语之间的关系的方法。
	 * 
	 * @param subject 主语。
	 * @param object 宾语。
	 * @param pageNum 页码
	 * @param pageSize 每页的记录数
	 * @return json格式的检索结果字符串。
	 */
	String queryByPage(String subject,String object,int pageNum,int pageSize);
}
