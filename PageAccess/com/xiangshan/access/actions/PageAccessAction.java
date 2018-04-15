package com.xiangshan.access.actions;

/**
 * 页面访问Action类
 * 
 * @author Rosahen
 * @version 1.0
 */
public class PageAccessAction {

	/**
	 * 访问首页的方法。
	 * 
	 * @return 首页对应的name属性值。
	 */
	public String homepage() {
		
		return "homepage";
	}
	
	/**
	 * 访问图谱展示页面的方法。
	 * 
	 * @return 图谱展示页面对应的name属性值。
	 */
	public String showGraph() {
		
		return "showGraphPage";
	}
	
	/**
	 * 访问知识检索页面的方法。
	 * 
	 * @return 知识检索页面对应的name属性值。
	 */
	public String knowledgeSearch() {
		
		return "knowledgeSearchPage";
	}
	
	/**
	 * 访问属性检索页面的方法。
	 * 
	 * @return 属性检索页面对应的name属性值。
	 */
	public String predicateSearch() {
		
		return "predicateSearchPage";
	}
	
	/**
	 * 访问关系检索页面的方法。
	 * 
	 * @return 关系检索页面对应的name属性值。
	 */
	public String relationSearch() {
		
		return "relationSearchPage";
	}
	
	/**
	 * 访问时空检索页面的方法。
	 * 
	 * @return 时空检索页面对应的name属性值。
	 */
	public String timeSpaceSearch() {
		
		return "timeSpaceSearchPage";
	}

	
}
