package com.xiangshan.statement.service.interfaces;

/**
 * 处理三元组的Service接口。
 * 
 * @author Rosahen
 * @version 1.0
 */
public interface StatementService {

	/**
	 * 将三元组处理成可视化数据格式的函数。
	 * 
	 * @param subject 三元组的主语。
	 * @param predicate 三元组的谓语。
	 * @param object 三元组的宾语。
	 * @return 三元组可视化数据的json格式字符串。
	 */
	String processAsVisualData(String subject,String predicate,String object);
}
