package com.xiangshan.indistinct.actions;

import java.io.InputStream;

import com.xiangshan.indistinct.service.interfaces.IndistinctSearchService;
import com.xiangshan.utils.StreamUtil;

/**
 * 模糊检索的Action类。
 * 
 * @author Rosahen
 * @version 1.0
 */
public class IndistinctSearchAction {

	/**
	 * 关键词。
	 */
	private String keyword;
	
	/**
	 * 检索结果的输入流。
	 */
	private InputStream result;

	/**
	 * 模糊检索的Service。
	 */
	private IndistinctSearchService indistinctSearchService;
	
	/**
	 * 模糊检索知识的方法。
	 * 
	 * @return 检索结果对应的name值。
	 */
	public String queryKnowledge() {
		
		String jsonData = indistinctSearchService.queryKnowledge(keyword);
		
		result = StreamUtil.buildByteArrayInputStream(jsonData);
		
		return "success";
	}
	
	public String queryPredicate() {
		
		String jsonData = indistinctSearchService.queryPredicate(keyword);
		
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

	public IndistinctSearchService getIndistinctSearchService() {
		return indistinctSearchService;
	}

	public void setIndistinctSearchService(IndistinctSearchService indistinctSearchService) {
		this.indistinctSearchService = indistinctSearchService;
	}
	
}
