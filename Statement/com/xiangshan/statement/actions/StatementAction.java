package com.xiangshan.statement.actions;

import java.io.InputStream;

import com.xiangshan.generic.actions.GenericAction;
import com.xiangshan.statement.service.interfaces.StatementService;
import com.xiangshan.utils.StreamUtil;

/**
 * 处理三元组的Action。
 * 
 * @author Rosahen
 * @version 1.0
 */
public class StatementAction extends GenericAction{

	/**
	 * 三元组的主语。
	 */
	private String subject;
	
	/**
	 * 三元组的谓语。
	 */
	private String predicate;
	
	/**
	 * 三元组的宾语。
	 */
	private String object;
	
	/**
	 * 处理结果输入流。
	 */
	private InputStream result;
	
	/**
	 * 处理三元组的Service。
	 */
	private StatementService statementService;
	
	
	/**
	 * 获取三元组可视化数据的函数。
	 * 
	 * @return 数据结果对应的name值。
	 */
	public String visualize() {
		
		//将三元组处理成可视化数据格式
		String jsonData = statementService.processAsVisualData(subject, predicate, object);
		
		//将数据转成输入流
		result = StreamUtil.buildByteArrayInputStream(jsonData);
		
		return "success";
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPredicate() {
		return predicate;
	}

	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public InputStream getResult() {
		return result;
	}

	public void setResult(InputStream result) {
		this.result = result;
	}

	public StatementService getStatementService() {
		return statementService;
	}

	public void setStatementService(StatementService statementService) {
		this.statementService = statementService;
	}
	
	
}
