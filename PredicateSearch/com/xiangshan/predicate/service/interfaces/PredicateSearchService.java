package com.xiangshan.predicate.service.interfaces;


/**
 * 属性检索的Service接口。
 * 
 * @author Rosahen
 * @version 1.0
 */
public interface PredicateSearchService {

	/**
	 * 检索和该属性相关的三元组方法。
	 * 
	 * @param predicate 属性。即三元组的谓语。
	 * @return 检索结果的json格式字符串。
	 */
	String query(String predicate);
	
	/**
	 * 分页检索和该属性相关的三元组方法。
	 * 
	 * @param predicate 属性。即三元组的谓语。
	 * @param pageNum 页码
	 * @param pageSize 每页的记录数
	 * @return 检索结果的json格式字符串。
	 */
	String queryByPage(String predicate,int pageNum,int pageSize);
	
	/**
	 * 获取所有本体属性的方法。
	 * 
	 * @return 所有属性的json格式字符串。
	 */
	String getPredicates();
}
