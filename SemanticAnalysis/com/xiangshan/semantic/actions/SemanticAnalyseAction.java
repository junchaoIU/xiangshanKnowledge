package com.xiangshan.semantic.actions;

import java.io.InputStream;

import com.xiangshan.generic.actions.GenericAction;
import com.xiangshan.semantic.service.interfaces.SemanticAnalyseService;
import com.xiangshan.utils.StreamUtil;

public class SemanticAnalyseAction extends GenericAction{

	private String keyword;
	
	private InputStream result;
	
	private SemanticAnalyseService semanticAnalyseService;

	public String analyse() {
		
		//获取json格式的检索结果字符串
		String jsonData = semanticAnalyseService.analyse(keyword);
		
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

	public SemanticAnalyseService getSemanticAnalyseService() {
		return semanticAnalyseService;
	}

	public void setSemanticAnalyseService(SemanticAnalyseService semanticAnalyseService) {
		this.semanticAnalyseService = semanticAnalyseService;
	}
	
}
