package com.xiangshan.indistinct.service.interfaces;

/**
 * 模糊检索的Service接口。
 * 
 * @author Rosahen
 * @version 1.0
 */
public interface IndistinctSearchService {

	/**
	 * 模糊检索知识的方法。
	 * 
	 * @param keyword 关键词。
	 * @return 和关键词相关的知识。json格式的字符串。
	 */
	String queryKnowledge(String keyword);
	
	/**
	 * 模糊检索属性的方法。
	 * 
	 * @param keyword 关键词。
	 * @return 和关键词相关的属性。json格式的字符串。
	 */
	String queryPredicate(String keyword);
	
}
