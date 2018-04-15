package com.xiangshan.knowledge.actions;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import com.xiangshan.generic.actions.GenericAction;
import com.xiangshan.history.entities.QueryHistory;
import com.xiangshan.knowledge.service.interfaces.KnowledgeSearchService;
import com.xiangshan.user.entities.UserInfo;
import com.xiangshan.utils.StreamUtil;

/**
 * 知识检索的Action类。用来处理知识检索页面的请求。
 * 
 * @author Rosahen
 * @version 1.0
 */
public class KnowledgeSearchAction extends GenericAction{

	/**
	 * 检索的关键词。
	 */
	private String keyword;
	
	/**
	 * 检索范围。
	 */
	private String scope;
	
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
	 * 知识检索Service。
	 */
	private KnowledgeSearchService knowledgeSearchService;
	
	/**
	 * 知识检索方法。
	 * 
	 * @return 检索结果对应的name值。
	 */
	public String query() {
		
		//获取json格式的检索结果字符串
		String jsonData = knowledgeSearchService.query(keyword, scope);
		
		//将结果转成输入流
		result = StreamUtil.buildByteArrayInputStream(jsonData);

		return "success";
	}
	
	/**
	 * 知识分页检索方法。
	 * 
	 * @return 检索结果对应的name值。
	 */
	public String queryByPage() {
		
		//获取json格式的检索结果字符串
		String jsonData = knowledgeSearchService.queryByPage(keyword, scope, pageNum, pageSize);
		
		//将结果转成输入流
		result = StreamUtil.buildByteArrayInputStream(jsonData);
		
		//jsonData等于null,不需要记录历史
		if(jsonData!=null&&historyType!=null) {
			UserInfo loginUser = (UserInfo) getSessionMap().get("login_user");
			
			QueryHistory history = new QueryHistory(UUID.randomUUID().toString(),keyword,
					null,null,scope,null,historyType,new Date(),loginUser.getUserName());
			
			addHistory(history);
			
		}
		return "success";
	}
	
	/**
	 * 知识详情检索方法。
	 * 
	 * @return 检索结果对应的name值。
	 */
	public String getDetails() {
		
		//获取json格式的检索结果字符串
		String jsonData = knowledgeSearchService.getDetails(keyword);
		
		//将结果转成输入流
		result = StreamUtil.buildByteArrayInputStream(jsonData);
		
		return "success";
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public InputStream getResult() {
		return result;
	}

	public void setResult(InputStream result) {
		this.result = result;
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

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	
	public String getHistoryType() {
		return historyType;
	}

	public void setHistoryType(String historyType) {
		this.historyType = historyType;
	}

	public KnowledgeSearchService getKnowledgeSearchService() {
		return knowledgeSearchService;
	}

	public void setKnowledgeSearchService(KnowledgeSearchService knowledgeSearchService) {
		this.knowledgeSearchService = knowledgeSearchService;
	}

	
}
