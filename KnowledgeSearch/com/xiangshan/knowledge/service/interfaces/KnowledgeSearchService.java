package com.xiangshan.knowledge.service.interfaces;

/**
 * 知识检索Service接口。
 * 
 * @author Rosahen
 * @version 1.0
 */
public interface KnowledgeSearchService {

	/**
	 * 检索知识关键词的方法。
	 * 
	 * @param keyword 关键词。
	 * @param scope 检索范围。
	 * @return json格式的检索结果字符串。
	 */
	String query(String keyword,String scope);
	
	/**
	 * 分页检索知识关键词的方法。
	 * 
	 * @param keyword 关键词。
	 * @param scope 检索范围。
	 * @param pageNum 页码
	 * @param pageSize 每页的记录数
	 * @return json格式的检索结果字符串。
	 */
	String queryByPage(String keyword,String scope,int pageNum,int pageSize);
	
	/**
	 * 检索知识详情的方法。
	 * 
	 * @param keyword 关键词。
	 * @return json格式的检索结果字符串。
	 */
	String getDetails(String keyword);
	
}
