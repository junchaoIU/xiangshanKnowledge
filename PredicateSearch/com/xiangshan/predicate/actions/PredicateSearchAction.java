package com.xiangshan.predicate.actions;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import com.xiangshan.generic.actions.GenericAction;
import com.xiangshan.history.entities.QueryHistory;
import com.xiangshan.predicate.service.interfaces.PredicateSearchService;
import com.xiangshan.user.entities.UserInfo;
import com.xiangshan.utils.StreamUtil;

/**
 * 属性检索Action类。用来处理属性检索页面的请求。
 * 
 * @author Rosahen
 * @version 1.0
 */
public class PredicateSearchAction extends GenericAction{

	/**
	 * 属性。即三元组的谓语
	 */
	private String predicate;
	
	/**
	 * 页码
	 */
	private int pageNum;
	
	/**
	 * 每页的记录数
	 */
	private int pageSize;
	
	/**
	 * 检索历史类型。
	 */
	private String historyType;
	
	/**
	 * 检索结果的输入流。
	 */
	private InputStream result;
	
	/**
	 * 属性检索Service。
	 */
	private PredicateSearchService predicateSearchService;
	
	/**
	 * 获取所有本体属性的方法。
	 * 
	 * @return 本体属性结果对应的name值。
	 */
	public String getPredicates() {
		
		String predicates = predicateSearchService.getPredicates();
		
		result = StreamUtil.buildByteArrayInputStream(predicates);
		
		return "success";
	}
	
	/**
	 * 检索以该属性为谓语的所有三元组的方法。
	 * 
	 * @return 检索结果对应的name值。
	 */
	public String query(){
		
		//获取json格式的检索结果字符串
		String jsonData = predicateSearchService.query(predicate);
		
		//将结果转成输入流
		result = StreamUtil.buildByteArrayInputStream(jsonData);
		
		return "success";
	}
	
	/**
	 * 分页检索以该属性为谓语的所有三元组的方法。
	 * 
	 * @return 检索结果对应的name值。
	 */
	public String queryByPage(){
		
		//获取json格式的检索结果字符串
		String jsonData = predicateSearchService.queryByPage(predicate,pageNum,pageSize);
		
		//将结果转成输入流
		result = StreamUtil.buildByteArrayInputStream(jsonData);
		
		//jsonData等于null，不需要记录历史
		if(jsonData!=null&&historyType!=null) {
			UserInfo loginUser = (UserInfo) getSessionMap().get("login_user");
			
			QueryHistory history = new QueryHistory(UUID.randomUUID().toString(),null,predicate,null,
					null,null,historyType,new Date(),loginUser.getUserName());
			
			addHistory(history);
		}
		return "success";
	}

	public String getPredicate() {
		return predicate;
	}

	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}
	
	
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getHistoryType() {
		return historyType;
	}

	public void setHistoryType(String historyType) {
		this.historyType = historyType;
	}

	public InputStream getResult() {
		return result;
	}

	public void setResult(InputStream result) {
		this.result = result;
	}

	public PredicateSearchService getPredicateSearchService() {
		return predicateSearchService;
	}

	public void setPredicateSearchService(PredicateSearchService predicateSearchService) {
		this.predicateSearchService = predicateSearchService;
	}
	
}
