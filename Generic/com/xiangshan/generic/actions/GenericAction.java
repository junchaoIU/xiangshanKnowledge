package com.xiangshan.generic.actions;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.xiangshan.history.entities.QueryHistory;
import com.xiangshan.history.service.interfaces.HistoryService;
import com.xiangshan.user.entities.UserInfo;

/**
 * 通用的Action类。该类是所有Action类的父类。一些Action类公共的属性或方法应在该类中声明和实现。
 * 
 * @author Rosahen
 * @version 1.0
 */
public class GenericAction implements SessionAware{

	/**
	 * Struts2封装的session对象。
	 */
	private Map<String,Object> sessionMap;
	
	protected HistoryService historyService;
	
	protected void addHistory(QueryHistory history) {
		
		UserInfo loginUser = (UserInfo) getSessionMap().get("login_user");
		
		if(loginUser!=null) {
			
			historyService.recordHistory(history);
		}
	}
	
	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		this.sessionMap = arg0;
	}

	public Map<String, Object> getSessionMap() {
		return sessionMap;
	}

	public void setSessionMap(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}

	public HistoryService getHistoryService() {
		return historyService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}
	
}
